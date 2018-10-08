package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.ReportMemberTagDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.MemberMemberTagMapper;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.mapper.MemberTagTypeMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.MemberTagCsv;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.model.MemberTagTypeInput;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagStatus;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.pamametermodel.MemberTagInfoModel;
import com.d1m.wechat.pamametermodel.MemberTagModel;
import com.d1m.wechat.pamametermodel.MemberTagTypeInfoModel;
import com.d1m.wechat.pamametermodel.MemberTagTypeModel;
import com.d1m.wechat.schedule.SchedulerRestService;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xxl.job.core.biz.model.ReturnT;

import tk.mybatis.mapper.common.Mapper;

@Service
public class MemberTagServiceImpl extends BaseService<MemberTag> implements
		MemberTagService {
	
	private static Logger log = LoggerFactory.getLogger(MemberTagServiceImpl.class);

	@Autowired
	private MemberTagMapper memberTagMapper;

	@Autowired
	private MemberTagTypeMapper memberTagTypeMapper;
	
	@Autowired
	private MemberTagCsvService memberTagCsvService;

	@Autowired
	private SchedulerRestService schedulerRestService;
	
	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private MemberMemberTagMapper memberMemberTagMapper;

	@Override
	public Mapper<MemberTag> getGenericMapper() {
		return memberTagMapper;
	}

	public void setMemberTagMapper(MemberTagMapper memberTagMapper) {
		this.memberTagMapper = memberTagMapper;
	}

	public void setMemberTagTypeMapper(MemberTagTypeMapper memberTagTypeMapper) {
		this.memberTagTypeMapper = memberTagTypeMapper;
	}

	@Override
	public List<MemberTagDto> create(User user, Integer wechatId,
			AddMemberTagModel tags) throws WechatException {
		notBlank(tags, Message.MEMBER_TAG_NOT_BLANK);
		List<MemberTagModel> memberTags = tags.getTags();
		notBlank(memberTags, Message.MEMBER_TAG_NOT_BLANK);
		List<MemberTagDto> memberTagDtos = new ArrayList<MemberTagDto>();
		MemberTagDto memberTagDto = null;
		for (MemberTagModel memberTagModel : memberTags) {
			if (memberTagModel.getId() != null) {
				memberTagDto = new MemberTagDto();
				memberTagDto.setId(memberTagModel.getId());
				memberTagDto.setName(memberTagModel.getName());
				memberTagDto.setMemberTagTypeId(memberTagModel.getMemberTagTypeId());
				memberTagDtos.add(memberTagDto);
				continue;
			}
			notBlank(memberTagModel.getName(),
					Message.MEMBER_TAG_TYPE_NAME_NOT_EMPTY);
			MemberTagType memberTagType = getMemberTagType(wechatId,
					memberTagModel.getMemberTagTypeId());
			MemberTag record = new MemberTag();
			record.setWechatId(wechatId);
			record.setName(memberTagModel.getName());
			if (memberTagMapper.selectCount(record) > 0) {
				throw new WechatException(Message.MEMBER_TAG_NAME_EXIST);
			}
			record.setCreatedAt(new Date());
			record.setCreatorId(user.getId());
			record.setStatus(MemberTagStatus.INUSED.getValue());
			if (memberTagType != null) {
				record.setMemberTagTypeId(memberTagModel.getMemberTagTypeId());
			}
			memberTagMapper.insert(record);
			memberTagDto = new MemberTagDto();
			memberTagDto.setId(record.getId());
			memberTagDto.setName(record.getName());
			memberTagDtos.add(memberTagDto);
		}
		return memberTagDtos;
	}

	@Override
	public MemberTag update(User user, Integer wechatId, Integer id,
			Integer memberTagTypeId, String name) throws WechatException {
		notBlank(id, Message.MEMBER_TAG_ID_NOT_EMPTY);
		notBlank(name, Message.MEMBER_TAG_NAME_NOT_EMPTY);
		MemberTagType memberTagType = getMemberTagType(wechatId,
				memberTagTypeId);
		MemberTag record = get(wechatId, id);
		notBlank(record, Message.MEMBER_TAG_NOT_EXIST);
		checkUpdateTagDuplicate(wechatId, id, name);
		record.setName(name);
		if (memberTagType != null) {
			record.setMemberTagTypeId(memberTagTypeId);
		}
		memberTagMapper.updateByPrimaryKey(record);
		return record;
	}

	private void checkUpdateTagDuplicate(Integer wechatId, Integer id,
			String name) {
		MemberTag memberTag = new MemberTag();
		memberTag.setName(name);
		memberTag.setWechatId(wechatId);
		if(memberTagMapper.selectCount(memberTag)>1){
			throw new WechatException(Message.MEMBER_TAG_NAME_EXIST);
		}
		memberTag = memberTagMapper.selectOne(memberTag);
		if(memberTag!=null && memberTag.getId()!=id){
			throw new WechatException(Message.MEMBER_TAG_NAME_EXIST);
		}
	}

	@Override
	public MemberTag get(Integer wechatId, Integer id) {
		MemberTag record = new MemberTag();
		record.setId(id);
		record.setWechatId(wechatId);
		return memberTagMapper.selectOne(record);
	}

	private MemberTagType getMemberTagType(Integer wechatId,
			Integer memberTagTypeId) throws WechatException {
		if (memberTagTypeId == null) {
			return null;
		}
		MemberTagType memberTagType = null;
		memberTagType = new MemberTagType();
		memberTagType.setId(memberTagTypeId);
		memberTagType.setWechatId(wechatId);
		memberTagType = memberTagTypeMapper.selectOne(memberTagType);
		notBlank(memberTagType, Message.MEMBER_TAG_TYPE_NOT_EXIST);
		return memberTagType;
	}

	@Override
	public void delete(User user, Integer wechatId, Integer id)
			throws WechatException {
		notBlank(id, Message.MEMBER_TAG_ID_NOT_EMPTY);
		MemberTag record = get(wechatId, id);
		notBlank(record, Message.MEMBER_TAG_NOT_EXIST);

		// don't delete when existed in member_member_tag
		MemberMemberTag memberMemberTag = new MemberMemberTag();
		memberMemberTag.setMemberTagId(id);
		memberMemberTag.setWechatId(wechatId);
		int existCount = memberMemberTagMapper.selectCount(memberMemberTag);
		if(existCount > 0){
			throw new WechatException(Message.MEMBER_TAG_OWN_MEMBER);
		}

		/*record.setStatus(MemberTagStatus.DELETED.getValue());
		memberTagMapper.updateByPrimaryKey(record);*/
		memberTagMapper.delete(record);
	}

	@Override
	public Page<MemberTag> search(Integer wechatId, Integer memberTagTypeId,
			String name, String sortName, String sortDir, Integer pageNum,
			Integer pageSize, boolean queryCount) {
		PageHelper.startPage(pageNum, pageSize, queryCount);
		List<Integer> memberTagTypeIds = memberTagTypeChildren(wechatId, memberTagTypeId);
		memberTagTypeIds.add(memberTagTypeId);
		return memberTagMapper.search(wechatId, name, memberTagTypeIds, null,
				sortName, sortDir);
	}

	private List<Integer> memberTagTypeChildren(Integer wechatId,
			Integer memberTagTypeId) {
		List<Integer> memberTagTypeIds = new ArrayList<Integer>();
		MemberTagType rec = new MemberTagType();
		rec.setWechatId(wechatId);
		rec.setParentId(memberTagTypeId);
		rec.setStatus((byte)1);
		List<MemberTagType> recList = memberTagTypeMapper.select(rec);
		if(recList!=null){
			for(MemberTagType tagType:recList){
				memberTagTypeIds.add(tagType.getId());
				List<Integer> tagTypeIds = memberTagTypeChildren(wechatId, tagType.getId());
				if(tagTypeIds.isEmpty()){
					continue;
				}else{
					memberTagTypeIds.addAll(tagTypeIds);
				}
			}
		}
		return memberTagTypeIds;
	}

	@Override
	public Page<ReportMemberTagDto> tagUser(Integer wechatId, Date start,
			Date end, Integer top) {
		// TODO Auto-generated method stub
		notBlank(wechatId, null);
		notBlank(start, null);
		notBlank(end, null);
		PageHelper.startPage(1, top, true);
		return memberTagMapper.tagUser(wechatId, start, end, top);
	}

	@Override
	public synchronized void csvAddMemberTag(Integer wechatId, String uploadPath, 
			Integer userId, String oriFileName, String csv, String csvName) {
		try {
			// produce exception file and check OpenId and TagType Not Blank
			log.info("wechatId>>" + wechatId + ">>uploadPath>>" + uploadPath + ">>userId>>" + userId + ">>oriFileName>>" + csv + ">>csvName>>" + csvName);
			CsvReader r = new CsvReader(uploadPath, ',', Charset.forName("UTF-8"));
			JSONObject json = new JSONObject();
			r.readHeaders();
			String[] except = createExceptionFile(uploadPath, csv, csvName);
			CsvWriter wr = new CsvWriter(except[0], ',', Charset.forName("UTF-8"));
			String[] head = {"OPEN_ID","TYPE","TAG","Fail_Reason"};
			wr.writeRecord(head);
			while (r.readRecord()) {
				log.debug("csvAddMemberTag>>" + r.getRawRecord());
				String openId = r.getRawRecord().split(",")[0];
				if(!openId.equals("")){
					Member member = new Member();
					member.setOpenId(openId);
					if (memberMapper.selectCount(member)>0){
						if(r.get("TYPE")!=""){
							MemberTagType memberTagType = new MemberTagType();
							memberTagType.setName(r.get("TYPE"));
							memberTagType.setWechatId(wechatId);
							int result = memberTagTypeMapper.selectCount(memberTagType);
							if (result == 0){
								wr.writeRecord(produceException(r, "不存在此类标签类型"));
							}else{
								if(r.get("TAG")!=""){
									JSONObject subjson = new JSONObject();
									subjson.put(r.get("TYPE"), r.get("TAG"));
									json.put(openId, subjson);
								}
							}
						}else{
							wr.writeRecord(produceException(r, "标签类型不能为空"));
						}
					}else{
						wr.writeRecord(produceException(r, "不存在此会员OpenID"));
					}
				}else{
					wr.writeRecord(produceException(r, "会员OpenID不能为空"));
				}
			}
			wr.close();
			r.close();
			
			Date current = new Date();
			long currentTime=System.currentTimeMillis(); 
			long m = 1L * 60L * 1000L;
			long runAt = currentTime + m;
			Date runTask = new Date(runAt);
			String dateTask = DateUtil.formatYYYYMMDDHHMMSS(runTask);
			String taskName = "MemberAddTagCSV_" + dateTask;
			String data = JSON.toJSONString(json);
			
			// save csv and exception to DB
			MemberTagCsv record = new MemberTagCsv();
			record.setCsv(csv);
			record.setException(except[1]);
			record.setStatus(MemberTagCsvStatus.PREPARE.getValue());
			record.setWechatId(wechatId);
			record.setCreatorId(userId);
			record.setTask(taskName);
			record.setData(data);
			record.setCreatedAt(current);
			record.setOriFile(oriFileName);
			int result = memberTagCsvService.save(record);
			log.info("save csv&exception: {}", result);
			
			// handle task
			try {
				Map<String,Object> jobMap = new HashMap<String,Object>();
				jobMap.put("jobGroup",1);
				jobMap.put("jobDesc",taskName);
				jobMap.put("jobCron",DateUtil.cron.format(runTask));
				jobMap.put("executorHandler","memberTagCsvJob");
				jobMap.put("executorParam", "-d"+ TenantContext.getCurrentTenant()+","+record.getId());

				ReturnT<String> returnT = schedulerRestService.addJob(jobMap);
				log.info("jobMap:"+JSON.toJSON(jobMap));
				log.info("returnT执行结果:"+JSON.toJSON(returnT));
				if (ReturnT.FAIL_CODE == returnT.getCode()){
					throw new WechatException(
							Message.MEMBER_ADD_TAG_BY_CSV_ERROR);
				}
			} catch (Exception e) {
				log.error(e.getLocalizedMessage(), e);
				throw new WechatException(
						Message.MEMBER_ADD_TAG_BY_CSV_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return;
		}
	}

	private String[] produceException(CsvReader r, String error) throws IOException {
		String[] exception = new String[4];
		exception[0] = r.getRawRecord().split(",")[0];
		exception[1] = r.get("TYPE");
		exception[2] = r.get("TAG");
		exception[3] = error;
		return exception;
	}

	private String[] createExceptionFile(String uploadPath, String csv, String csvName) throws IOException {
		File f = new File(uploadPath);
		String uuid = UUID.randomUUID().toString().replace("-", "_");
		String[] content = new String[2];
		content[0] = f.getParent()+ "//" + uuid + ".csv";
		String exceptionPath = csv.replace(csvName, "");
		content[1] = exceptionPath + uuid + ".csv";
		return content;
	}

	@Override
	public MemberTag selectOne(MemberTag memberTag) {
		return memberTagMapper.selectOne(memberTag);
	}

	@Override
	public List<MemberTagDto> getAllMemberTags(Integer wechatId, Integer memberTagTypeId) {
		return memberTagMapper.getAllMemberTags(wechatId, memberTagTypeId);
	}

	@Override
	public void moveTag(Integer wechatId, MemberTagModel model) {
		if(model.getIds()==null||model.getIds().isEmpty()){
			throw new WechatException(Message.MEMBER_TAG_NOT_BLANK);
		}
		notBlank(model.getMemberTagTypeId(), Message.MEMBER_TAG_TYPE_NOT_BLANK);
		for(Integer id:model.getIds()){
			MemberTag record = new MemberTag();
			record.setId(id);
			record.setMemberTagTypeId(model.getMemberTagTypeId());
			memberTagMapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	public List<MemberTagDto> searchName(Integer wechatId, MemberTagModel model) {
		if(model.getName()==null || model.getName().length()==0){
			throw new WechatException(Message.MEMBER_TAG_NAME_NOT_EMPTY);
		}
		List<MemberTagDto> dtos =  memberTagMapper.searchName(wechatId, model.getName());
		return dtos;
	}

	@Override
	public List<MemberTag> exportList(Integer wechatId, MemberTagModel model) {
		return memberTagMapper.search(wechatId, null, null, model.getIds(),
				null, null);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveMemberTagTypeInfo(MemberTagTypeInput memberTagTypeInput) {

		MemberTagTypeInfoModel memberTagTypeModel = new MemberTagTypeInfoModel();
		memberTagTypeModel.setName(memberTagTypeInput.getMemberTgTypeName());
		memberTagTypeModel.setWechat_id(memberTagTypeInput.getWechatId());
		memberTagTypeModel.setParentName(memberTagTypeInput.getParentMemberTgTypeName());
		memberTagTypeMapper.saveMemberTagTypeInfo(memberTagTypeModel);
		Long id = memberTagTypeMapper.selectID(memberTagTypeInput.getMemberTgTypeName());
		MemberTagInfoModel memberTagModel = new MemberTagInfoModel();
		
		memberTagModel.setName(memberTagTypeInput.getMemberTgName());
		memberTagModel.setMemberTagTypeId(id.intValue());
		memberTagModel.setWechat_id(memberTagTypeInput.getWechatId());
		memberTagMapper.saveMemberTagInfo(memberTagModel);
		
		
		
	}
}
