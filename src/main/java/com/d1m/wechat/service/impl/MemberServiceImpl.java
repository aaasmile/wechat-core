package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxTag;
import cn.d1m.wechat.client.model.WxUser;
import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.dto.*;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.lock.RedisLock;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.Language;
import com.d1m.wechat.model.enums.MemberSource;
import com.d1m.wechat.model.enums.MemberTagStatus;
import com.d1m.wechat.model.enums.Sex;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.pamametermodel.ExcelMember;
import com.d1m.wechat.pamametermodel.MemberModel;
import com.d1m.wechat.pamametermodel.MemberTagModel;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MemberTagTypeService;
import com.d1m.wechat.util.*;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Service
public class MemberServiceImpl extends BaseService<Member> implements
        MemberService {

    private Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
    private static String defaultMedium = "qrcode";
    //默认每批次处理数量
    private static final Integer BATCHSIZE = 100;

    @Autowired
    private ConfigService configService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberTagMapper memberTagMapper;

    @Autowired
    private MemberMemberTagMapper memberMemberTagMapper;

    @Autowired
    private AreaInfoMapper areaInfoMapper;

    @Autowired
    private MemberTagTypeMapper memberTagTypeMapper;

    @Autowired
    private MemberProfileMapper memberProfileMapper;

    @Autowired
    private MemberTagTypeService memberTagTypeService;

    @Autowired
    private ConversationMapper conversationMapper;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public void setMemberTagMapper(MemberTagMapper memberTagMapper) {
        this.memberTagMapper = memberTagMapper;
    }

    public void setMemberMemberTagMapper(
            MemberMemberTagMapper memberMemberTagMapper) {
        this.memberMemberTagMapper = memberMemberTagMapper;
    }

    @Override
    public Mapper<Member> getGenericMapper() {
        return memberMapper;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public MemberDto getMemberDto(Integer wechatId, Integer id) {
        List<MemberDto> list = memberMapper.selectByMemberId(
                new Integer[]{id}, wechatId, null);

        if (!list.isEmpty()) {
            MemberDto dto = list.get(0);
            return dto;
        }
        return null;
    }

    @Override
    public Member getMember(Integer wechatId, Integer id) {
        Member record = new Member();
        record.setWechatId(wechatId);
        record.setId(id);
        return memberMapper.selectOne(record);
    }

    @Override
    public Member getMemberByOpenId(Integer wechatId, String openId) {
        Member record = new Member();
        record.setWechatId(wechatId);
        record.setOpenId(openId);
        return memberMapper.selectOne(record);
    }

    @Override
    public List<MemberTagDto> getMemberMemberTag(Integer wechatId, Integer id) {
        return memberMapper.getMemberMemberTags(wechatId, id);
    }

    @Override
    public Page<MemberDto> search(Integer wechatId,
                                  AddMemberTagModel addMemberTagModel, boolean queryCount) {
        if (addMemberTagModel == null) {
            addMemberTagModel = new AddMemberTagModel();
        }

        MemberModel memberModel = addMemberTagModel.getMemberModel();
        memberModel.setNickname(StringUtils.isBlank(memberModel.getNickname()) ? null : memberModel.getNickname());
        memberModel.setMobile(StringUtils.isBlank(memberModel.getMobile()) ? null : memberModel.getMobile());
        long total = memberMapper.count(wechatId, memberModel.getOpenId(),
                memberModel.getNickname(), memberModel.getSex(), memberModel
                        .getCountry(), memberModel.getProvince(), memberModel
                        .getCity(), addMemberTagModel.getSubscribe(), memberModel
                        .getActivityStartAt(), memberModel.getActivityEndAt(),
                memberModel.getBatchSendOfMonthStartAt(), memberModel
                        .getBatchSendOfMonthEndAt(), DateUtil
                        .getDateBegin(DateUtil.parse(memberModel
                                .getAttentionStartAt())), DateUtil
                        .getDateEnd(DateUtil.parse(memberModel
                                .getAttentionEndAt())), DateUtil
                        .getDateBegin(DateUtil.parse(memberModel
                                .getCancelSubscribeStartAt())), DateUtil
                        .getDateEnd(DateUtil.parse(memberModel
                                .getCancelSubscribeEndAt())), memberModel
                        .getIsOnline(), null, memberModel.getMobile(),
                memberModel.getMemberTags(), addMemberTagModel.getSortName(),
                addMemberTagModel.getSortDir(), addMemberTagModel
                        .getBindStatus(), DateUtil.getDate(-2),
                addMemberTagModel.getFuzzyRemarks());

        Integer pageSize = addMemberTagModel.getPageSize();
        Integer offset = (addMemberTagModel.getPageNum() - 1) * pageSize;
        if (addMemberTagModel.pagable() && offset < 1000) {
            PageHelper.startPage(addMemberTagModel.getPageNum(),
                    addMemberTagModel.getPageSize(), false);
        }
        Page<MemberDto> list = null;

        if (offset < 1000) {
            list = memberMapper.search(wechatId, memberModel.getOpenId(),
                    memberModel.getNickname(), memberModel.getSex(), memberModel
                            .getCountry(), memberModel.getProvince(), memberModel
                            .getCity(), addMemberTagModel.getSubscribe(), memberModel
                            .getActivityStartAt(), memberModel.getActivityEndAt(),
                    memberModel.getBatchSendOfMonthStartAt(), memberModel
                            .getBatchSendOfMonthEndAt(), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getAttentionStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getAttentionEndAt())), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getCancelSubscribeStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getCancelSubscribeEndAt())), memberModel
                            .getIsOnline(), null, memberModel.getMobile(),
                    memberModel.getMemberTags(), addMemberTagModel.getSortName(),
                    addMemberTagModel.getSortDir(), addMemberTagModel
                            .getBindStatus(), DateUtil.getDate(-2),
                    addMemberTagModel.getFuzzyRemarks());
        } else {
            // 优化数据表过大，limit的offset值过大，查询缓慢
            List<Integer> ids = memberMapper.searchIds(wechatId, memberModel
                            .getOpenId(), memberModel.getNickname(), memberModel.getSex(),
                    memberModel.getCountry(), memberModel.getProvince(),
                    memberModel.getCity(), addMemberTagModel.getSubscribe(), memberModel
                            .getActivityStartAt(), memberModel.getActivityEndAt(),
                    memberModel.getBatchSendOfMonthStartAt(), memberModel
                            .getBatchSendOfMonthEndAt(), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getAttentionStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getAttentionEndAt())), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getCancelSubscribeStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getCancelSubscribeEndAt())), memberModel
                            .getIsOnline(), null, memberModel.getMobile(),
                    memberModel.getMemberTags(), addMemberTagModel.getSortName(),
                    addMemberTagModel.getSortDir(), addMemberTagModel
                            .getBindStatus(), offset, pageSize, addMemberTagModel.getFuzzyRemarks());
            list = memberMapper.searchByIds(ids, ids.size());
        }
        list.setTotal(total);
        return list;
    }

    @Override
    public List<MemberDto> getMemberList(AddMemberTagModel addMemberTagModel,
                                         Integer wechatId) {
        return memberMapper.selectByMemberId(addMemberTagModel.getMemberIds(),
                wechatId, null);
    }

    @Override
    public List<MemberDto> getAll(Integer wechatId) {
        return memberMapper.selectByWechat(wechatId);
    }

    @Override
    public List<MemberDto> searchBySql(Integer wechatId, String sql) {
        return memberMapper.searchBySql(wechatId, sql);
    }

    /**
     * 查询需要添加标签的members
     *
     * @param wechatId
     * @param addMemberTagModel
     * @param tenant
     * @return
     */
    @Async("callerRunsExecutor")
    public List<MemberDto> queryMember(Integer wechatId, AddMemberTagModel addMemberTagModel, String tenant) {
        log.info("执行查询开始时间：" + DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
        TenantContext.setCurrentTenant(tenant);
        List<MemberDto> members = Collections.synchronizedList(new ArrayList<>());
        if (ObjectUtils.isEmpty(addMemberTagModel.getMemberIds())) {
            MemberModel memberModel = addMemberTagModel.getMemberModel();
            members = memberMapper.search(wechatId, memberModel.getOpenId(),
                    memberModel.getNickname(), memberModel.getSex(),
                    memberModel.getCountry(), memberModel.getProvince(),
                    memberModel.getCity(), addMemberTagModel.getSubscribe(),
                    memberModel.getActivityStartAt(), memberModel
                            .getActivityEndAt(), memberModel
                            .getBatchSendOfMonthStartAt(), memberModel
                            .getBatchSendOfMonthEndAt(), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getAttentionStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getAttentionEndAt())), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getCancelSubscribeStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getCancelSubscribeEndAt())), null, null,
                    memberModel.getMobile(), memberModel.getMemberTags(), null,
                    null, null, DateUtil.getDate(-2),
                    addMemberTagModel.getFuzzyRemarks());
        } else {
            members = memberMapper.selectByMemberId(
                    addMemberTagModel.getMemberIds(), wechatId, null);
        }
        log.info("执行查询结束时间：" + DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
        return members;

    }

    private void processAddMemberTag(Integer wechatId, AddMemberTagModel addMemberTagModel) {
        MemberModel memberModel = addMemberTagModel.getMemberModel();
        List<MemberTag> memberTagsIn = getMemberTags(wechatId, addMemberTagModel.getTags());

        //String tenant = TenantContext.getCurrentTenant();
        if(ObjectUtils.isEmpty(addMemberTagModel.getMemberIds())) {
            Long count = memberMapper.count(wechatId, memberModel.getOpenId(),
                    memberModel.getNickname(), memberModel.getSex(), memberModel
                            .getCountry(), memberModel.getProvince(), memberModel
                            .getCity(), addMemberTagModel.getSubscribe(), memberModel
                            .getActivityStartAt(), memberModel.getActivityEndAt(),
                    memberModel.getBatchSendOfMonthStartAt(), memberModel
                            .getBatchSendOfMonthEndAt(), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getAttentionStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getAttentionEndAt())), DateUtil
                            .getDateBegin(DateUtil.parse(memberModel
                                    .getCancelSubscribeStartAt())), DateUtil
                            .getDateEnd(DateUtil.parse(memberModel
                                    .getCancelSubscribeEndAt())), memberModel
                            .getIsOnline(), null, memberModel.getMobile(),
                    memberModel.getMemberTags(), addMemberTagModel.getSortName(),
                    addMemberTagModel.getSortDir(), addMemberTagModel
                            .getBindStatus(), DateUtil.getDate(-2),
                    addMemberTagModel.getFuzzyRemarks());

            if(count == null || count == 0) {
                throw new WechatException(Message.MEMBER_NOT_BLANK);
            }

            int threadID = 20;
            int rows = 0;
            int more = 0;
            if(count < 5000) {
                threadID = 1;
                rows = count.intValue();
                more = count.intValue();
            } else {
                rows = count.intValue()/ (threadID-1);
                more = count.intValue()% (threadID-1);
            }
            int offset = 0;
            Date createdAt = new Date();

            for (int i = 0; i < threadID; i++) {
                if(i == threadID - 1) rows = more;
                List<MemberUseTagDto> members = memberMapper.findMemberTagsByWechatIdForSubLimit(wechatId, offset, rows,
                        memberModel.getMemberTags(),
                        memberModel.getNickname(),
                        memberModel.getMobile(),
                        addMemberTagModel.getSubscribe(),
                        memberModel.getSex(),
                        memberModel.getCountry(),
                        memberModel.getProvince(),
                        memberModel.getCity(),
                        memberModel.getIsOnline(),
                        addMemberTagModel.getBindStatus(),
                        memberModel.getActivityStartAt(),
                        memberModel.getActivityEndAt(),
                        memberModel.getBatchSendOfMonthStartAt(),
                        memberModel.getBatchSendOfMonthEndAt(),
                        DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())),
                        DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionEndAt())),
                        DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())),
                        DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeEndAt())),
                        addMemberTagModel.getFuzzyRemarks());
                offset = members.get(members.size() - 1).getId();

                List<MemberMemberTag> memberMemberTags = new ArrayList<>();
                Map<Integer, List<MemberUseTagDto>> memberMap = members.stream().collect(Collectors.groupingBy(MemberUseTagDto::getId));
                for (Integer key : memberMap.keySet()) {
                    List<Integer> memberTagDtoIds = memberMap.get(key).stream().map(memberUseTagDto -> memberUseTagDto.getTagId()).collect(Collectors.toList());
                    List<MemberTag> addTags = memberTagsIn.stream().filter((MemberTag t) -> !memberTagDtoIds.contains(t.getId())).collect(Collectors.toList());
                    for (MemberTag tag : addTags) {
                        memberMemberTags.add(new MemberMemberTag(key, tag.getId(), wechatId, memberMap.get(key).get(0).getOpenId(), createdAt));
                        if(memberMemberTags.size() > 0 && memberMemberTags.size() == 1000){
                            memberMemberTagMapper.insertList(memberMemberTags);
                            memberMemberTags = new ArrayList<>();
                        }
                    }
                }
                if(memberMemberTags.size() > 0){
                    memberMemberTagMapper.insertList(memberMemberTags);
                }
            }
        } else {
            List<Member> members = memberMapper.selectByMemberIdsAndWechatId(addMemberTagModel.getMemberIds(), wechatId);
            if (members.isEmpty()) {
                throw new WechatException(Message.MEMBER_NOT_BLANK);
            }

            Date createdAt = new Date();
            List<MemberMemberTag> memberMemberTags = new ArrayList<>();
            members.forEach( member -> {
                List<Integer> memberTagDtoIds = memberMapper.getMemberMemberTagsByMemberId(member.getId());
                List<MemberTag> addTags = memberTagsIn.stream().filter((MemberTag t) -> !memberTagDtoIds.contains(t.getId())).collect(Collectors.toList());
                addTags.forEach(tag -> memberMemberTags.add(new MemberMemberTag(member.getId(), tag.getId(), wechatId, member.getOpenId(), createdAt)));
            });
            if(memberMemberTags.size() > 0){
                memberMemberTagMapper.insertList(memberMemberTags);
            }
        }
    }

    private List<MemberTagDto> processAddMemberTag(Integer wechatId, User user,
                                                   AddMemberTagModel addMemberTagModel) {
        List<MemberTag> memberTagsIn = getMemberTags(wechatId, user,
                addMemberTagModel.getTags());

        List<MemberDto> members = queryMember(wechatId, addMemberTagModel, TenantContext.getCurrentTenant());

        if (members.isEmpty()) {
            throw new WechatException(Message.MEMBER_NOT_BLANK);
        }

        MemberMemberTagDTO dto = getAddBatchMemberTagList(members, memberTagsIn, wechatId);
        List<MemberMemberTag> memberMemberTagList = dto.getMemberTagList();
        List<MemberTag> memberTags = dto.getMemberTags();
        try {
            //存入redis
            String memberTagsList = MD5.MD5Encode("MEMBERTAGSLIST_" + System.currentTimeMillis());
            redisTemplate.opsForValue().set(memberTagsList, memberMemberTagList);
            //获取redis中的数据
            List<MemberMemberTag> RedisDataList = (List<MemberMemberTag>) redisTemplate.opsForValue().get(memberTagsList);
            List<MemberMemberTag> list = CollectionUtils.isNotEmpty(RedisDataList) ? RedisDataList : memberMemberTagList;
            if (CollectionUtils.isNotEmpty(list)) {
                if (list.size() >= BATCHSIZE) {
                    //调用批量处理
                    this.asyncBatch(list);
                } else {
                    memberMemberTagMapper.insertList(list);
                }
            }
            //删除redis
            redisTemplate.delete(memberTagsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<MemberTagDto> memberTagDtos = new ArrayList<MemberTagDto>();
        MemberTagDto memberTagDto = null;
        for (MemberTag memberTag : memberTags) {
            memberTagDto = new MemberTagDto();
            memberTagDto.setId(memberTag.getId());
            memberTagDto.setName(memberTag.getName());
            memberTagDtos.add(memberTagDto);
        }
        return memberTagDtos;
    }

    @Override
    public List<MemberTagDto> addMemberTag(Integer wechatId, User user,
                                           AddMemberTagModel addMemberTagModel) throws WechatException {
        if (addMemberTagModel.emptyQuery()) {
            throw new WechatException(Message.MEMBER_NOT_BLANK);
        }

        RedisLock redisLock = new RedisLock(stringRedisTemplate, "addMemberTag", 60 * 60 * 1000, 2 * 1000);

        try {
            if(!redisLock.lock()) {
                throw new WechatException(Message.MEMBER_ADD_TAG_OPERATOR_ONLY);
            }

            processAddMemberTag(wechatId, addMemberTagModel);

            List<MemberTagDto> memberTagDtos = new ArrayList<>();
            addMemberTagModel.getTags().forEach( memberTagModel -> {
                memberTagDtos.add(new MemberTagDto(memberTagModel.getId(), memberTagModel.getName()));
            });
            return memberTagDtos;
        } catch (WechatException e) {
            throw e;
        } catch (Exception e) {
            throw new WechatException(Message.SYSTEM_ERROR);
        } finally {
            redisLock.unlock();
        }
    }


    /**
     * 批量插入
     *
     * @param memberMemberAddTags
     */
    private void asyncBatch(List<MemberMemberTag> memberMemberAddTags) throws WechatException {
        Integer amount = memberMemberAddTags.size();
        Integer batchSize = getBatchSize(memberMemberAddTags.get(0).getWechatId()) != null ? getBatchSize(memberMemberAddTags.get(0).getWechatId()) : BATCHSIZE;
        //1、判断将要插入的数量是否大于等于每批次执行的数量，若大于或等于就执行批量处理方法，否则直接插入。
        Map<String, Integer> map = BatchUtils.getTimes(batchSize, amount);
        Integer times = map.get("times");
        Integer remainAmount = map.get("remainAmount");
        //2、调用线程处理
        TagsBatchDto batchDto = new TagsBatchDto();
        batchDto.setTimes(times);
        batchDto.setRemainAmount(remainAmount);
        batchDto.setTagsList(memberMemberAddTags);
        batchDto.setAmount(amount);
        batchDto.setBatchSize(batchSize);
        //获取租户标识
        batchDto.setTenant(TenantContext.getCurrentTenant());
        try {
            Future<Integer> future = execute(batchDto);
            if (future.get().equals(amount)) {
                log.info("===================执行成功，总数量：" + amount + "===============");
            }
        } catch (Exception e) {
            throw new WechatException(Message.MEMBER_TAG_BATCH_FAIL);
        }

    }

    /**
     * 执行批量插入
     *
     * @param batchDto
     * @return
     */
    @Async("callerRunsExecutor")
    public Future<Integer> execute(TagsBatchDto batchDto) {
        log.info("执行开始时间：" + DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
        TenantContext.setCurrentTenant(batchDto.getTenant());
        Integer operatedCount = 0;//已执行数量
        Integer remainCompletedCount = 0;//剩余数据已执行数量
        //批量数据处理
        for (int i = 0; i < batchDto.getTimes(); i++) {
            List<MemberMemberTag> memberTagList = batchDto.getTagsList().subList((batchDto.getBatchSize() * i)
                    , (batchDto.getBatchSize() * i + batchDto.getBatchSize()));
            Integer exeCount = memberMemberTagMapper.insertList(memberTagList);
            operatedCount = exeCount * (i + 1);
            log.info("线程：" + Thread.currentThread().getName() + ",已完成数量：" + exeCount * (i + 1));
        }

        //剩余数据处理
        if (batchDto.getRemainAmount() > 0) {
            List<MemberMemberTag> memberTagList = batchDto.getTagsList().subList((batchDto.getBatchSize() * batchDto.getTimes())
                    , batchDto.getAmount());
            remainCompletedCount = memberMemberTagMapper.insertList(memberTagList);
            log.info("线程：" + Thread.currentThread().getName() + ",剩余已完成数量：" + remainCompletedCount);
        }

        //已完成总数量
        Integer completedCount = operatedCount + remainCompletedCount;
        log.info("已完成总数量：" + completedCount);
        log.info("执行结束时间：" + DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
        return new AsyncResult<>(completedCount);
    }


    /**
     * 获取批量参数
     *
     * @param wechatId
     * @return
     */
    public Integer getBatchSize(Integer wechatId) {
        Integer value = null;
        ConfigDto configDto = configService.getConfigDto(wechatId, "INIT", "TAG_BATCH_SIZE");
        if (configDto != null) {
            value = Integer.parseInt(configDto.getValue());
        }
        log.info("从数据库获取每批次的数量：" + value);
        return value;
    }

    @Override
    public List<MemberExcel> findMemberExcelByParams(Map<String, Object> params) {
        return memberProfileMapper.findMemberExcelByParams(params);

    }

    @Override
    public Integer countByParams(Integer wechatId, AddMemberTagModel addMemberTagModel) {
        MemberModel memberModel = addMemberTagModel.getMemberModel();
        return memberProfileMapper.countByParams(wechatId,
                memberModel.getOpenId(), memberModel.getNickname(),
                memberModel.getSex(), memberModel.getCountry(),
                memberModel.getProvince(), memberModel.getCity(),
                addMemberTagModel.getSubscribe(),
                memberModel.getActivityStartAt(),
                memberModel.getActivityEndAt(),
                memberModel.getBatchSendOfMonthStartAt(),
                memberModel.getBatchSendOfMonthEndAt(),
                DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())),
                DateUtil.getDateEnd(DateUtil.parse(memberModel.getAttentionEndAt())),
                DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())),
                DateUtil.getDateEnd(DateUtil.parse(memberModel.getCancelSubscribeEndAt())), memberModel.getIsOnline(),
                memberModel.getMobile(),
                memberModel.getMemberTags(),
                addMemberTagModel.getBindStatus(),
                addMemberTagModel.getFuzzyRemarks());
    }

    @Override
    public List<MemberExcel> findMemberExcelByParamsNew(Integer wechatId, AddMemberTagModel addMemberTagModel,
                                                        Integer maxId, Integer rows, Integer offset) {
        MemberModel memberModel = addMemberTagModel.getMemberModel();
        return memberProfileMapper.findMemberExcelByParamsNew(wechatId,
                maxId, rows, offset,
                memberModel.getOpenId(), memberModel.getNickname(),
                memberModel.getSex(), memberModel.getCountry(),
                memberModel.getProvince(), memberModel.getCity(),
                addMemberTagModel.getSubscribe(),
                memberModel.getActivityStartAt(),
                memberModel.getActivityEndAt(),
                memberModel.getBatchSendOfMonthStartAt(),
                memberModel.getBatchSendOfMonthEndAt(),
                DateUtil.getDateBegin(DateUtil.parse(memberModel.getAttentionStartAt())),
                DateUtil.getDateEnd(DateUtil.parse(memberModel.getAttentionEndAt())),
                DateUtil.getDateBegin(DateUtil.parse(memberModel.getCancelSubscribeStartAt())),
                DateUtil.getDateEnd(DateUtil.parse(memberModel.getCancelSubscribeEndAt())), memberModel.getIsOnline(),
                memberModel.getMobile(),
                memberModel.getMemberTags(),
                addMemberTagModel.getBindStatus(),
                addMemberTagModel.getFuzzyRemarks());
    }

    /**
     * 获取需要加标签的批量数据
     *
     * @param members
     * @param memberTagsIn
     * @param wechatId
     * @return
     */
    public MemberMemberTagDTO getAddBatchMemberTagList(List<MemberDto> members, List<MemberTag> memberTagsIn, Integer wechatId) {
        Date current = new Date();
        List<MemberMemberTag> memberMemberAddTags = null;
        List<MemberTagDto> memberMemberDeleteTags = null;
        MemberMemberTag memberMemberTag = null;
        List<MemberTagDto> existMemberTags = null;
        List<MemberTag> memberTags = null;
        MemberMemberTagDTO dto = new MemberMemberTagDTO();
        List<MemberMemberTag> memberTagList = new ArrayList<MemberMemberTag>();
        for (MemberDto memberDto : members) {
            try {
                memberTags = BeanUtil.copyTo(memberTagsIn, MemberTag.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            memberMemberAddTags = new ArrayList<MemberMemberTag>();
            memberMemberDeleteTags = new ArrayList<MemberTagDto>();
            existMemberTags = memberDto.getMemberTags();
            // 补全批量删除时用户已有标签
            if (null != existMemberTags) {
                for (MemberTagDto existMemberTag : existMemberTags) {
                    if (!contains(memberTags, existMemberTag)) {
                        MemberTag tag = new MemberTag();
                        tag.setId(existMemberTag.getId());
                        tag.setName(existMemberTag.getName());
                        tag.setWechatId(existMemberTag.getWechatId());
                        memberTags.add(tag);
                    }
                }
                for (MemberTag memberTag : memberTags) {
                    if (!contains(existMemberTags, memberTag)) {
                        memberMemberTag = new MemberMemberTag();
                        memberMemberTag.setMemberId(memberDto.getId());
                        memberMemberTag.setMemberTagId(memberTag.getId());
                        memberMemberTag.setWechatId(wechatId);
                        memberMemberTag.setCreatedAt(current);
                        memberMemberTag.setOpenId(memberDto.getOpenId());
                        memberMemberAddTags.add(memberMemberTag);
                    }
                }
                memberTagList.addAll(memberMemberAddTags);
                for (MemberTagDto existMemberTag : existMemberTags) {
                    if (!contains(memberTags, existMemberTag)) {
                        memberMemberDeleteTags.add(existMemberTag);
                    }
                }
            }

            if (!memberMemberDeleteTags.isEmpty()) {
                for (MemberTagDto memberTagDto : memberMemberDeleteTags) {
                    memberMemberTagMapper.deleteByPrimaryKey(memberTagDto
                            .getMemberMemberTagId());
                }
            }
        }
        dto.setMemberTagList(memberTagList);
        dto.setMemberTags(memberTags);
        return dto;
    }

    private boolean contains(List<MemberTagDto> list, MemberTag memberTag) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (MemberTagDto memberTagDto : list) {
            if (memberTagDto.getId().equals(memberTag.getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean contains(List<MemberTag> list, MemberTagDto memberTagDto) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (MemberTag memberTag : list) {
            if (memberTag.getId().equals(memberTagDto.getId())) {
                return true;
            }
        }
        return false;
    }

    private List<MemberTag> getMemberTags(Integer wechatId, List<MemberTagModel> memberTagModels) {
        List<MemberTag> memberTags = new ArrayList<>();
        memberTagModels.forEach( memberTagModel -> {
            if(memberTagModel.getId() == null) {
                throw new WechatException(Message.MEMBER_TAG_TYPE_NOT_BLANK);
            }
            MemberTag memberTag = new MemberTag(memberTagModel.getId(), wechatId);
            memberTag = memberTagMapper.selectOne(memberTag);
            notBlank(memberTag, Message.MEMBER_TAG_NOT_EXIST);
            memberTags.add(memberTag);
        });
        return memberTags;
    }


    private List<MemberTag> getMemberTags(Integer wechatId, User user,
                                          List<MemberTagModel> memberTagModels) throws WechatException {
        MemberTag memberTag = null;
        MemberTagType memberTagType = null;
        List<MemberTag> memberTags = new ArrayList<MemberTag>();
        Date current = new Date();
        for (MemberTagModel memberTagModel : memberTagModels) {
            if (memberTagModel.getId() == null
                    && StringUtils.isBlank(memberTagModel.getName())) {
                throw new WechatException(Message.MEMBER_TAG_TYPE_NOT_BLANK);
            }
            memberTag = new MemberTag();
            if (memberTagModel.getId() != null) {
                memberTag.setWechatId(wechatId);
                memberTag.setId(memberTagModel.getId());
                memberTag = memberTagMapper.selectOne(memberTag);
                notBlank(memberTag, Message.MEMBER_TAG_NOT_EXIST);
                memberTags.add(memberTag);
            } else {
                notBlank(memberTagModel.getMemberTagTypeId(),
                        Message.MEMBER_TAG_TYPE_ID_NOT_EMPTY);
                memberTagType = new MemberTagType();
                memberTagType.setWechatId(wechatId);
                memberTagType.setId(memberTagModel.getMemberTagTypeId());
                memberTagType = memberTagTypeMapper.selectOne(memberTagType);
                notBlank(memberTagType, Message.MEMBER_TAG_TYPE_NOT_EXIST);
                MemberTag exist = memberTagMapper.getDuplicateName(wechatId,
                        null, memberTagModel.getName());
                if (exist != null) {
                    memberTags.add(exist);
                } else {
                    memberTag.setWechatId(wechatId);
                    memberTag.setName(memberTagModel.getName());
                    memberTag.setCreatedAt(current);
                    memberTag.setCreatorId(user.getId());
                    memberTag.setStatus(MemberTagStatus.INUSED.getValue());
                    memberTag.setWechatId(wechatId);
                    memberTag.setMemberTagTypeId(memberTagModel
                            .getMemberTagTypeId());
                    memberTagMapper.insert(memberTag);
                    memberTags.add(memberTag);
                }
            }

        }
        return memberTags;
    }

    @Override
    public void pullWxMember(Integer wechatId, String nextOpenId)
            throws WechatException {
        throw new UnsupportedOperationException("请使用[/migrate/member.json]接口");
    }

    public static void updateMemberByWxMember(Member member, Member newMember) {
        if (newMember.getProvince() != null) {
            member.setProvince(newMember.getProvince());
        }
        if (newMember.getCity() != null) {
            member.setCity(newMember.getCity());
        }
        if (newMember.getCountry() != null) {
            member.setCountry(newMember.getCountry());
        }
        if (StringUtils.isNotBlank(newMember.getLocalHeadImgUrl())) {
            member.setLocalHeadImgUrl(newMember.getLocalHeadImgUrl());
        }
        if (StringUtils.isNotBlank(newMember.getHeadImgUrl())) {
            member.setHeadImgUrl(newMember.getHeadImgUrl());
        }
        if (newMember.getLanguage() != null) {
            member.setLanguage(newMember.getLanguage());
        }
        if (StringUtils.isNotBlank(newMember.getNickname())) {
            member.setNickname(newMember.getNickname());
        }
        if (StringUtils.isNotBlank(newMember.getRemark())) {
            member.setRemark(newMember.getRemark());
        }
        if (newMember.getSex() != null) {
            member.setSex(newMember.getSex());
        } else {
            member.setSex((byte) 0);
        }
        if (StringUtils.isNotBlank(newMember.getUnionId())) {
            member.setUnionId(newMember.getUnionId());
        }
        member.setSubscribeAt(newMember.getSubscribeAt());
        member.setIsSubscribe(newMember.getIsSubscribe());
		/*member.setQrSceneStr(StringUtils.isNotBlank(newMember.getQrSceneStr()) ? newMember.getQrSceneStr():null);
		member.setQrScene(newMember.getQrScene());
		member.setSubscribeScene(newMember.getSubscribeScene());*/
    }

    @Override
    public Member getMemberByWxUser(WxUser wxuser, Integer wechatId, Date current) {

        Member member = new Member();
        if (1 == wxuser.getSubscribe()) {
            AreaInfo areaInfo = getAreaInfo(null, wxuser.getCountry());
            if (areaInfo != null) {
                member.setCountry(areaInfo.getId());
            }
            areaInfo = getAreaInfo((areaInfo != null ? areaInfo.getId() : null),
                    wxuser.getProvince());
            if (areaInfo != null) {
                member.setProvince(areaInfo.getId());
            }
            areaInfo = getAreaInfo((areaInfo != null ? areaInfo.getId() : null),
                    wxuser.getCity());
            if (areaInfo != null) {
                member.setCity(areaInfo.getId());
            }

            Language language = Language.getValueByName(wxuser.getLanguage());
            if (language != null) {
                member.setLanguage(language.getValue());
            }
            member.setNickname(wxuser.getNickname());
            member.setRemark(wxuser.getRemark());
            Sex sex = Sex.getByValue(Byte.parseByte(wxuser.getSex()));
            if (sex != null) {
                member.setSex(sex.getValue());
            }
            member.setUnionId(wxuser.getUnionid());
            member.setSubscribeAt(wxuser.getSubscribeTime());
        } else {
            member.setNickname("");
        }
        if (StringUtils.isBlank(wxuser.getHeadimgurl())) {
            String memberDefaultAvatarPath = FileUploadConfigUtil.getInstance()
                    .getValue(wechatId, "member_default_avatar_path");
            if (StringUtils.isNotBlank(memberDefaultAvatarPath)) {
                member.setLocalHeadImgUrl(memberDefaultAvatarPath);
            }
        } else {
            member.setHeadImgUrl(wxuser.getHeadimgurl());
            member.setLocalHeadImgUrl(wxuser.getHeadimgurl());
			/*
			String format = DateUtil.yyyyMMddHHmmss.format(new Date());
			String type = Constants.IMAGE + File.separator + Constants.NORMAL;
			File root = FileUtils.getUploadPathRoot(type);
			File dir = new File(root, format.substring(0, 6));
			String suffix = FileUtils.getRemoteImageSuffix(
					wxuser.getHeadimgurl(), Constants.JPG);
			String newFileName = FileUtils.generateNewFileName(
					MD5.MD5Encode(""), suffix);
			FileUtils.copyRemoteFile(wxuser.getHeadimgurl(),
					dir.getAbsolutePath(), newFileName);
			member.setLocalHeadImgUrl(FileUploadConfigUtil.getInstance()
					.getValue("upload_url_base")
					+ File.separator
					+ type
					+ File.separator
					+ dir.getName()
					+ File.separator
					+ newFileName);
			*/
        }
        member.setCreatedAt(current);
        member.setOpenId(wxuser.getOpenid());
        member.setWechatId(wechatId);
        member.setIsSubscribe(wxuser.getSubscribe() == 1);
		/*member.setSubscribeScene(wxuser.getSubscribe_scene());
		member.setQrScene(wxuser.getQr_scene());
		member.setQrSceneStr(wxuser.getQr_scene_str());*/
        return member;
    }

    private AreaInfo getAreaInfo(Integer parentId, String name) {
        AreaInfo areaInfo = new AreaInfo();
        if (parentId != null) {
            areaInfo.setParentId(parentId);
        }
        if (StringUtils.isBlank(name)) {
            return null;
        }
        areaInfo.setcName(name);
        areaInfo = areaInfoMapper.selectOne(areaInfo);
        return areaInfo;
    }

    private List<TrendBaseDto> findDates(Date begin, Date end) {
        List<TrendBaseDto> list = new ArrayList<TrendBaseDto>();
        TrendBaseDto dto = new TrendBaseDto();
        dto.setTime(DateUtil.formatYYYYMMDD(begin));
        dto.setCount(0);
        list.add(dto);

        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        calBegin.set(Calendar.HOUR_OF_DAY, 0);
        calBegin.set(Calendar.MINUTE, 0);
        calBegin.set(Calendar.SECOND, 0);
        calBegin.set(Calendar.MILLISECOND, 0);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dto = new TrendBaseDto();
            dto.setTime(DateUtil.formatYYYYMMDD(calBegin.getTime()));
            dto.setCount(0);
            list.add(dto);
        }
        return list;
    }

    @Override
    public TrendDto trend(Integer wechatId, Date start, Date end) {
        // TODO Auto-generated method stub
        notBlank(wechatId, Message.REPORT_ARGS_INVALID);
        notBlank(start, Message.REPORT_ARGS_INVALID);
        notBlank(end, Message.REPORT_ARGS_INVALID);
        Integer total = memberMapper.trendReportTotal(wechatId, end);

        List<TrendBaseDto> attentionList = findDates(start, end);
        List<TrendBaseDto> attentionResult = memberMapper.trendReportAttention(
                wechatId, start, end);
        List<TrendBaseDto> attentionTimesList = findDates(start, end);
        List<TrendBaseDto> attentionTimesResult = memberMapper.trendReportAttentionTimes(
                wechatId, start, end);
        for (TrendBaseDto dto : attentionList) {
            for (TrendBaseDto temp : attentionResult) {
                if (dto.getTime().equals(temp.getTime())) {
                    dto.setCount(temp.getCount());
                    break;
                }
            }
        }
        for (TrendBaseDto dto : attentionTimesList) {
            for (TrendBaseDto temp : attentionTimesResult) {
                if (dto.getTime().equals(temp.getTime())) {
                    dto.setCount(temp.getCount());
                    break;
                }
            }
        }

        List<TrendBaseDto> cancelList = findDates(start, end);
        List<TrendBaseDto> cancelResult = memberMapper.trendReportCancel(
                wechatId, start, end);
        List<TrendBaseDto> cancelTimesList = findDates(start, end);
        List<TrendBaseDto> cancelTimesResult = memberMapper.trendReportCancelTimes(
                wechatId, start, end);
        for (TrendBaseDto dto : cancelList) {
            for (TrendBaseDto temp : cancelResult) {
                if (dto.getTime().equals(temp.getTime())) {
                    dto.setCount(temp.getCount());
                    break;
                }
            }
        }
        for (TrendBaseDto dto : cancelTimesList) {
            for (TrendBaseDto temp : cancelTimesResult) {
                if (dto.getTime().equals(temp.getTime())) {
                    dto.setCount(temp.getCount());
                    break;
                }
            }
        }

        TrendDto dto = new TrendDto();
        dto.setAttentionList(attentionList);
        dto.setCancelList(cancelList);
        dto.setAttentionTimesList(attentionTimesList);
        dto.setCancelTimesList(cancelTimesList);
        int attention = 0;
        int cancel = 0;
        int attentionTimes = 0;
        int cancelTimes = 0;

        if (attentionList != null && attentionList.size() > 0) {
            for (int i = 0; i < attentionList.size(); i++) {
                attention += attentionList.get(i).getCount();
            }
        }
        dto.setAttention(attention);

        if (cancelList != null && cancelList.size() > 0) {
            for (int i = 0; i < cancelList.size(); i++) {
                cancel += cancelList.get(i).getCount();
            }
        }
        dto.setCancel(cancel);

        if (attentionTimesList != null && attentionTimesList.size() > 0) {
            for (int i = 0; i < attentionTimesList.size(); i++) {
                attentionTimes += attentionTimesList.get(i).getCount();
            }
        }
        dto.setAttentionTimes(attentionTimes);

        if (cancelTimesList != null && cancelTimesList.size() > 0) {
            for (int i = 0; i < cancelTimesList.size(); i++) {
                cancelTimes += cancelTimesList.get(i).getCount();
            }
        }
        dto.setCancelTimes(cancelTimes);

        dto.setTotal(total);
        if (total > 0) {
            dto.setNewRate((double) attention / dto.getTotal());
            dto.setCancleRate((double) cancel / dto.getTotal());
        }

        int bind = memberProfileMapper.getBindNumber(wechatId, start, end);
        int unBind = memberProfileMapper.getUnBindNumber(wechatId, start, end);
        dto.setBind(bind);
        dto.setUnBind(unBind);

        return dto;
    }

    @Override
    public PieDto pie(Integer wechatId, Date start, Date end, String type) {
        // TODO Auto-generated method stub
        notBlank(wechatId, Message.REPORT_ARGS_INVALID);
        notBlank(start, Message.REPORT_ARGS_INVALID);
        notBlank(end, Message.REPORT_ARGS_INVALID);
        notBlank(type, Message.REPORT_ARGS_INVALID);

        PieDto pieDto = new PieDto();
        String sqlType = type;
        if ("gender".equalsIgnoreCase(type)) {
            sqlType = "sex";
        }
        List<PieBaseDto> result = memberMapper.pieReport(wechatId, start, end,
                sqlType);
        if (result != null && result.size() > 0) {
            if (type.contains("gender")) {// 性别
                int total = 0;
                for (PieBaseDto data : result) {
                    total += data.getCount();
                }
                for (PieBaseDto data : result) {
                    data.setRate((double) data.getCount() / total);
                }
            } else if (type.contains("language")) {// 语言
                int total = 0;
                for (PieBaseDto data : result) {
                    total += data.getCount();
                }
                for (PieBaseDto data : result) {
                    data.setRate((double) data.getCount() / total);
                }
            }
            pieDto.setList(result);
        }
        pieDto.setType(type);
        return pieDto;
    }

    @Override
    public Page<ReportActivityUserDto> activityUser(Integer wechatId,
                                                    Date start, Date end, Integer top) {
        // TODO Auto-generated method stub
        notBlank(wechatId, Message.REPORT_ARGS_INVALID);
        notBlank(start, Message.REPORT_ARGS_INVALID);
        notBlank(end, Message.REPORT_ARGS_INVALID);
        PageHelper.startPage(1, top, false);
        return memberMapper.activityUser(wechatId, start, end, top);
    }

    private List<ReportUserSourceDto> findDates4UserSource(Date begin, Date end) {
        List<ReportUserSourceDto> list = new ArrayList<ReportUserSourceDto>();
        ReportUserSourceDto dto = new ReportUserSourceDto();
        dto.setDate(DateUtil.formatYYYYMMDD(begin));
        list.add(dto);

        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(begin);
        calBegin.set(Calendar.HOUR_OF_DAY, 0);
        calBegin.set(Calendar.MINUTE, 0);
        calBegin.set(Calendar.SECOND, 0);
        calBegin.set(Calendar.MILLISECOND, 0);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dto = new ReportUserSourceDto();
            dto.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
            list.add(dto);
        }
        return list;
    }

    @Override
    public ReportUserSourceDto sourceUser(Integer wechatId, Date start, Date end) {
        // TODO Auto-generated method stub
        notBlank(wechatId, Message.REPORT_ARGS_INVALID);
        notBlank(start, Message.REPORT_ARGS_INVALID);
        notBlank(end, Message.REPORT_ARGS_INVALID);

        List<ReportUserSourceDto> allList = findDates4UserSource(start, end);
        List<ReportUserSourceDto> list = memberMapper.sourceUser(wechatId,
                start, end);
        for (ReportUserSourceDto dto : allList) {
            for (ReportUserSourceDto temp : list) {
                if (dto.getDate().equals(temp.getDate())) {
                    dto.setBusinessCard(temp.getBusinessCard());
                    dto.setMomentsAd(temp.getMomentsAd());
                    dto.setOther(temp.getOther());
                    dto.setPayAttentionAfter(temp.getPayAttentionAfter());
                    dto.setQrCode(temp.getQrCode());
                    dto.setTopRightMenu(temp.getTopRightMenu());
                    dto.setWechatInArticleAd(temp.getWechatInArticleAd());
                    dto.setWechatInImageText(temp.getWechatInImageText());
                    dto.setWechatSearch(temp.getWechatSearch());
                    break;
                }
            }
        }
        ReportUserSourceDto dto = new ReportUserSourceDto();

        if (allList != null && allList.size() > 0) {
            dto.setList(allList);
            for (ReportUserSourceDto param : allList) {
                dto.setBusinessCard(dto.getBusinessCard()
                        + param.getBusinessCard());
                dto.setWechatSearch(dto.getWechatSearch()
                        + param.getWechatSearch());
                dto.setQrCode(dto.getQrCode() + param.getQrCode());
                dto.setTopRightMenu(dto.getTopRightMenu()
                        + param.getTopRightMenu());
                dto.setWechatInImageText(dto.getWechatInImageText()
                        + param.getWechatInImageText());
                dto.setWechatInArticleAd(dto.getWechatInArticleAd()
                        + param.getWechatInArticleAd());
                dto.setMomentsAd(dto.getMomentsAd() + param.getMomentsAd());
                dto.setPayAttentionAfter(dto.getPayAttentionAfter()
                        + param.getPayAttentionAfter());
                dto.setOther(dto.getOther() + param.getOther());
            }
        }
        return dto;
    }

    @Override
    public List<ReportAreaBaseDto> pieArea(Integer wechatId, Date start,
                                           Date end, String type) {
        // TODO Auto-generated method stub
        notBlank(wechatId, Message.REPORT_ARGS_INVALID);
        notBlank(start, Message.REPORT_ARGS_INVALID);
        notBlank(end, Message.REPORT_ARGS_INVALID);
        notBlank(type, Message.REPORT_ARGS_INVALID);

        List<ReportAreaBaseDto> country = memberMapper.pieAreaReport(wechatId,
                start, end, type);
		/*if (country != null && country.size() > 0) {
			for (ReportAreaBaseDto dto : country) {
				List<ReportAreaBaseDto> provice = memberMapper.getProvice(
						wechatId, start, end, dto.getId());
				if (provice != null && provice.size() > 0) {
					for (ReportAreaBaseDto proviceDto : provice) {
						List<ReportAreaBaseDto> city = memberMapper
								.getCity(wechatId, start, end,
										dto.getId(), proviceDto.getId());
						proviceDto.setCity(city);
					}
				}
				dto.setProvince(provice);
			}
		}*/

        return country;
    }

    @Override
    public void deleteMemberTag(Integer wechatId, Integer memberId,
                                Integer memberTagId) {
        memberMapper.deleteMemberTag(wechatId, memberId, memberTagId);
    }

    public List<MemberStatusDto> getMemberStatus(Integer wechatId, Date end) {
        List<MemberStatusDto> list = memberMapper.memberStatus(wechatId, end);
        return list;
    }

    @Override
    public void updateMemberActivity(MemberStatusDto memberStatusDto, Date endDate) {
        Integer memberId = null;
        Boolean isSubscribe = null;
        Date menuClickLast = null;
        Date conversationLast = null;
        int days = 0;
        isSubscribe = memberStatusDto.getIsSubscribe();
        memberId = memberStatusDto.getMemberId();
        menuClickLast = memberStatusDto.getMenuClickLast();
        conversationLast = memberStatusDto.getConversationLast();

        Member member = memberMapper.selectByPrimaryKey(memberId);
        byte activity = 0;
        double weight = 0;
        if (isSubscribe) {
            activity = 5;
            // 计算活跃度 - 菜单点击维度
            if (menuClickLast != null) {
                days = DateUtil.daysBetween(menuClickLast, endDate);
                weight = 1 - days * 0.1;
                weight = weight < 0 ? 0 : weight;
                activity = (byte) (activity + 15 * weight);
            }
            // 计算活跃度 - 客服聊天维度
            if (conversationLast != null) {
                days = DateUtil.daysBetween(conversationLast, endDate);
                weight = 1 - days * 0.1;
                weight = weight < 0 ? 0 : weight;
                activity = (byte) (activity + 20 * weight);
            }
            member.setActivity(activity);
        } else {
            // 活跃度清零
            member.setActivity((byte) 0);
        }
        memberMapper.updateByPrimaryKey(member);
    }

    @Override
    public List<MemberDto> searchAll(Integer wechatId,
                                     AddMemberTagModel addMemberTagModel, boolean queryCount) {
        if (addMemberTagModel == null) {
            addMemberTagModel = new AddMemberTagModel();
        }
        addMemberTagModel.setPageSize(0);
        if (addMemberTagModel.pagable()) {
            PageHelper.startPage(addMemberTagModel.getPageNum(),
                    addMemberTagModel.getPageSize(), queryCount, null, true);
        }
        MemberModel memberModel = addMemberTagModel.getMemberModel();
        return memberMapper.search(wechatId, memberModel.getOpenId(),
                memberModel.getNickname(), memberModel.getSex(), memberModel
                        .getCountry(), memberModel.getProvince(), memberModel
                        .getCity(), addMemberTagModel.getSubscribe(), memberModel
                        .getActivityStartAt(), memberModel.getActivityEndAt(),
                memberModel.getBatchSendOfMonthStartAt(), memberModel
                        .getBatchSendOfMonthEndAt(), DateUtil
                        .getDateBegin(DateUtil.parse(memberModel
                                .getAttentionStartAt())), DateUtil
                        .getDateEnd(DateUtil.parse(memberModel
                                .getAttentionEndAt())), DateUtil
                        .getDateBegin(DateUtil.parse(memberModel
                                .getCancelSubscribeStartAt())), DateUtil
                        .getDateEnd(DateUtil.parse(memberModel
                                .getCancelSubscribeEndAt())), memberModel
                        .getIsOnline(), null, memberModel.getMobile(),
                memberModel.getMemberTags(), addMemberTagModel.getSortName(),
                addMemberTagModel.getSortDir(), addMemberTagModel
                        .getBindStatus(), DateUtil.getDate(-2),
                addMemberTagModel.getFuzzyRemarks());
    }

    @Override
    public MemberDto selectByOpenId(String openId, Integer wechatId) {
        return memberMapper.selectByOpenId(openId, wechatId);
    }

    @Override
    public MemberLevelDto selectMemberProfile(Integer id, Integer wechatId) {
        MemberLevelDto memberLevelDto = memberMapper.selectMemberProfile(id,
                wechatId);
        if (memberLevelDto == null) {
            memberLevelDto = new MemberLevelDto();
        }
        if (memberLevelDto.getCredits() == null) {
            memberLevelDto.setCredits(0);
        }
        if (memberLevelDto.getLevel() == null) {
            memberLevelDto.setLevel("V1");
        }
        return memberLevelDto;
    }

    @Override
    public synchronized void syncWxTag(Integer wechatId, User user) {
        log.info("sync wechat {} start pull wx member tag.", wechatId);


        List<WxTag> wxTags = WechatClientDelegate.getTags(wechatId).get();
        Integer memberTagTypeDefaultId = memberTagTypeService.getDefaultId();
        for (WxTag wxTag : wxTags) {
            List<String> openIdList = WechatClientDelegate.getOpenidByTag(wechatId, wxTag.getId(), null).getData();
            if (openIdList == null) {
                MemberTag record1 = new MemberTag();
                MemberTag memberTag1 = new MemberTag();
                record1.setName(wxTag.getName());
                record1 = memberTagMapper.selectOne(record1);
                if (record1 == null) {
                    memberTag1.setName(wxTag.getName());
                    memberTag1.setCreatedAt(new Date());
                    memberTag1.setWechatId(wechatId);
                    memberTag1.setCreatorId(user.getId());
                    memberTag1.setStatus((byte) 1);
                    memberTag1.setMemberTagTypeId(memberTagTypeDefaultId);
                    memberTagMapper.insert(memberTag1);
                }
                continue;
            }

            List<List<String>> openIdListArr = new ArrayList<List<String>>();
            int arrSize = openIdList.size() % 100 == 0 ? openIdList.size() / 100 : openIdList.size() / 100 + 1;
            for (int i = 0; i < arrSize; i++) {
                List<String> sub = new ArrayList<String>();
                for (int j = i * 100; j <= 100 * (i + 1) - 1; j++) {
                    if (j <= openIdList.size() - 1) {
                        sub.add(openIdList.get(j));
                    }
                }
                openIdListArr.add(sub);
            }
            for (List<String> openIdArr : openIdListArr) {
                List<Integer> memberIdList = memberMapper.getMemberIdsByOpenIds(openIdArr);
                if (memberIdList == null || memberIdList.size() == 0) {
                    continue;
                }
                int size = memberIdList.size();
                Integer[] memberIds = (Integer[]) memberIdList.toArray(new Integer[size]);
                AddMemberTagModel addMemberTagModel = new AddMemberTagModel();
                List<MemberTagModel> tags = new ArrayList<MemberTagModel>();

                MemberTag record = new MemberTag();
                MemberTag memberTag = new MemberTag();
                record.setName(wxTag.getName());
                record.setStatus(MemberTagStatus.INUSED.getValue());
                record = memberTagMapper.selectOne(record);
                if (record == null) {
                    memberTag.setName(wxTag.getName());
                    memberTag.setCreatedAt(new Date());
                    memberTag.setWechatId(wechatId);
                    memberTag.setCreatorId(user.getId());
                    memberTag.setStatus(MemberTagStatus.INUSED.getValue());
                    memberTag.setMemberTagTypeId(memberTagTypeDefaultId);
                    memberTagMapper.insert(memberTag);
                }

                MemberTagModel memberTagModel = new MemberTagModel();
                memberTagModel.setName(wxTag.getName());
                if (record != null) {
                    memberTagModel.setId(record.getId());
                    memberTagModel.setMemberTagTypeId(record.getMemberTagTypeId());
                } else {
                    memberTagModel.setId(memberTag.getId());
                    memberTagModel.setMemberTagTypeId(memberTag.getMemberTagTypeId());
                }
                tags.add(memberTagModel);
                addMemberTagModel.setMemberIds(memberIds);
                addMemberTagModel.setTags(tags);
                addMemberTag(wechatId, user, addMemberTagModel);
            }
        }

    }

    @Override
    public synchronized Member createMember(Integer wechatId, String openId, Date current) {
        Member member = getMemberByOpenId(wechatId, openId);
        if (member == null) {
            member = new Member();
            String memberDefaultAvatarPath = FileUploadConfigUtil.getInstance().getValue(wechatId, "member_default_avatar_path");
            if (StringUtils.isNotBlank(memberDefaultAvatarPath)) {
                member.setLocalHeadImgUrl(memberDefaultAvatarPath);
            }
            if (current == null) {
                current = new Date();
            }
            member.setOpenId(openId);
            member.setIsSubscribe(true);
            member.setLastConversationAt(current);
            member.setWechatId(wechatId);
            member.setActivity((byte) 5);
            member.setBatchsendMonth(0);
            member.setCreatedAt(current);
            save(member);
        } else {
            if (!member.getIsSubscribe()) {
                Member update = new Member();
                update.setId(member.getId());
                update.setIsSubscribe(true);
                updateNotNull(update);
            }
        }

        return member;
    }

    /**
     * 加了Async注解, 将此方法提交到spring管理的executor异步执行
     */
    //@Async("callerRunsExecutor")
    public void updateWxuser(Member member, Qrcode qrcode, MemberSource memberSource) {
        Integer wechatId = member.getWechatId();
        WxUser wxuser = WechatClientDelegate.getUser(wechatId, member.getOpenId());
        Member newMember = getMemberByWxUser(wxuser, wechatId, new Date());

        member.setIsSubscribe(true);
        member.setFromwhere(memberSource.getValue());
        if (qrcode != null && StringUtils.isBlank(member.getSource()) && member.getUnsubscribeAt() == null) {
            member.setSource(qrcode.getScene());
        }
        MemberServiceImpl.updateMemberByWxMember(member, newMember);
        log.info("SubscribeAt {}", member.getSubscribeAt());
        updateAll(member);

        // 更新会话头像
        conversationMapper.updateMemberPhoto(member.getId(), member.getLocalHeadImgUrl());
        log.info("update member.");
    }

    @Override
    public int updateAll(Member entity) {
        return super.updateAll(entity);
    }

    @Override
    public int updateNotNull(Member entity) {
        return super.updateNotNull(entity);
    }

    @Override
    public MemberDto searchMember(MemberDto member) {
        return memberMapper.searchMember(member);
    }

    @Override
    public List<ExcelMember> totalMember(Integer wechatId,
                                         AddMemberTagModel addMemberTagModel, boolean queryCount) {
        if (addMemberTagModel == null) {
            addMemberTagModel = new AddMemberTagModel();
        }
        MemberModel memberModel = addMemberTagModel.getMemberModel();
        return memberMapper.totalMember(wechatId, memberModel.getOpenId(),
                memberModel.getNickname(), memberModel.getSex(), memberModel
                        .getCountry(), memberModel.getProvince(), memberModel
                        .getCity(), memberModel.getSubscribe(), memberModel
                        .getActivityStartAt(), memberModel.getActivityEndAt(),
                memberModel.getBatchSendOfMonthStartAt(), memberModel
                        .getBatchSendOfMonthEndAt(), DateUtil
                        .getDateBegin(DateUtil.parse(memberModel
                                .getAttentionStartAt())), DateUtil
                        .getDateEnd(DateUtil.parse(memberModel
                                .getAttentionEndAt())), DateUtil
                        .getDateBegin(DateUtil.parse(memberModel
                                .getCancelSubscribeStartAt())), DateUtil
                        .getDateEnd(DateUtil.parse(memberModel
                                .getCancelSubscribeEndAt())), memberModel
                        .getIsOnline(), null, memberModel.getMobile(),
                memberModel.getMemberTags(), addMemberTagModel.getSortName(),
                addMemberTagModel.getSortDir(), addMemberTagModel
                        .getBindStatus(), DateUtil.getDate(-2));
    }

    @Autowired
    public RabbitTemplate rabbitTemplate;
    @Override
    public int loadMember(Integer wechatId) {
        int pageNum = 1;
        int pageSize = 1000;
        int current = pageSize * pageNum;
        int totalCount = memberMapper.selectCount(null);
//        while (current < totalCount) {
//            pageNum = pageNum ++;
//            current = pageSize * pageNum;
        fetchMember(wechatId, pageNum, pageSize, current);
//        }
//        if(current != totalCount) {
//            pageNum ++;
//            fetchMember(wechatId, pageNum, pageSize, current);
//        }
        return totalCount;
    }

    private void fetchMember(Integer wechatId, int pageNum, int pageSize, int current) {
        log.info("current...", current);
        PageHelper.startPage(pageNum, pageSize, true);
        List<MemberDto> memberDtos = memberMapper.selectByWechat(wechatId);
        JsonArray jsonArray = new JsonArray();
        JsonParser jsonParser = new JsonParser();
        ObjectMapper objectMapper = new ObjectMapper();
        memberDtos.forEach(member -> {
            try {
                member.setMemberTags(null);
                String memberStr = objectMapper.writeValueAsString(member);
                JsonObject jsonObject = jsonParser.parse(memberStr).getAsJsonObject();
                jsonArray.add(jsonObject);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        log.info("jsonArray..send..." + jsonArray.size());
        rabbitTemplate.convertAndSend("elas.exchange", "elas.queue", jsonArray.toString());
        log.info("jsonArray..end send..." + jsonArray.size());
    }


}