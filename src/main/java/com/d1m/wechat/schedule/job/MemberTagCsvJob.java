package com.d1m.wechat.schedule.job;

import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.enums.MemberTagStatus;
import com.d1m.wechat.pamametermodel.MemberTagModel;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.*;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JobHander(value = "memberTagCsvJob")
@Component
public class MemberTagCsvJob extends BaseJobHandler {

    @Resource
    private MemberTagCsvService memberTagCsvService;

    @Resource
    private MemberService memberService;

    @Resource
    private MemberTagService memberTagService;

    @Resource
    private MemberTagTypeService memberTagTypeService;

    @Resource
    private MemberMemberTagService memberMemberTagService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
//        try {
//            if (strings != null) {
//                // 参数1为CSV导入ID
//                Integer tagCsvId = ParamUtil.getInt(strings[0], null);
//                XxlJobLogger.log("tagCsvId : " + tagCsvId);
//                MemberTagCsv dto = memberTagCsvService.selectByKey(tagCsvId);
//                String data = dto.getData();
//                Integer wechatId = dto.getWechatId();
//                Integer creatorId = dto.getCreatorId();
//                dto.setStatus((byte) 1);
//                memberTagCsvService.updateNotNull(dto);
//
//                JSONObject json = JSON.parseObject(data);
//                for (String openId : json.keySet()) {
//                    JSONArray tagsJson = JSON.parseArray(json.getString(openId));
//                    List<MemberTagModel> memberTagModels = new ArrayList<>();
//                    for (Iterator iterator = tagsJson.iterator(); iterator.hasNext(); ) {
//                        JSONObject jsonObject = (JSONObject) iterator.next();
//                        for (String tagType : jsonObject.keySet()) {
//                            MemberTagType memberTagType = new MemberTagType();
//                            memberTagType.setName(tagType);
//                            memberTagType.setWechatId(wechatId);
//                            memberTagType = memberTagTypeService.selectOne(memberTagType);
//                            String[] tags = jsonObject.getString(tagType).split("\\|");
//                            for (String tag : tags) {
//                                if (StringUtils.isNotBlank(tag)) {
//                                    MemberTagModel memberTagModel = new MemberTagModel();
//                                    memberTagModel.setMemberTagTypeId(memberTagType.getId());
//                                    memberTagModel.setName(tag);
//                                    MemberTag memberTag = new MemberTag();
//                                    memberTag.setName(tag);
//                                    memberTag.setWechatId(wechatId);
//                                    memberTag = memberTagService.selectOne(memberTag);
//                                    if (memberTag != null) {
//                                        memberTagModel.setId(memberTag.getId());
//                                    }
//                                    if (!contains(memberTagModels, memberTagModel)) {
//                                        memberTagModels.add(memberTagModel);
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    List<MemberTag> memberTags = getMemberTags(wechatId, creatorId,
//                     memberTagModels);
//                    List<MemberDto> members = new ArrayList<MemberDto>();
//                    MemberDto memberDto = memberService.selectByOpenId(openId, wechatId);
//                    members.add(memberDto);
//                    MemberMemberTagDTO memberTagDTO = memberService.getAddBatchMemberTagList(members, memberTags, wechatId);
//                    List<MemberMemberTag> memberMemberAddTags = memberTagDTO.getMemberTagList();
//                    if (!memberMemberAddTags.isEmpty()) {
//                        memberMemberTagService.insertList(memberMemberAddTags);
//                    }
//                }
//                dto.setStatus((byte) 2);
//                memberTagCsvService.updateNotNull(dto);
//            }
//            return ReturnT.SUCCESS;
//        } catch (Exception e) {
//            e.printStackTrace();
//            XxlJobLogger.log("会员导入批量加标签失败：" + e.getMessage());
//            return ReturnT.FAIL;
//        }
        return ReturnT.SUCCESS;
    }

    private List<MemberTag> getMemberTags(Integer wechatId, Integer userId,
                                          List<MemberTagModel> memberTagModels) throws WechatException {
        List<MemberTag> memberTags = new ArrayList<>();
        Date current = new Date();
        for (MemberTagModel memberTagModel : memberTagModels) {
            MemberTag memberTag = new MemberTag();
            if (memberTagModel.getId() != null) {
                memberTag.setWechatId(wechatId);
                memberTag.setId(memberTagModel.getId());
                memberTag = memberTagService.selectOne(memberTag);
                memberTags.add(memberTag);
            } else {
                memberTag.setWechatId(wechatId);
                memberTag.setName(memberTagModel.getName());
                memberTag.setCreatedAt(current);
                memberTag.setCreatorId(userId);
                memberTag.setStatus(MemberTagStatus.INUSED.getValue());
                memberTag.setWechatId(wechatId);
                memberTag.setMemberTagTypeId(memberTagModel
                        .getMemberTagTypeId());
                memberTagService.save(memberTag);
                memberTags.add(memberTag);
            }

        }
        return memberTags;
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

    private boolean contains(List<MemberTagModel> list, MemberTagModel memberTagModel) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (MemberTagModel tagModel : list) {
            if (tagModel.getId().equals(memberTagModel.getId())
                    && tagModel.getMemberTagTypeId().equals(memberTagModel.getMemberTagTypeId())) {
                return true;
            }
        }
        return false;
    }
}
