package com.d1m.wechat.pamametermodel;

import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.ParamUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@ApiModel("增加用户标签参数")
public class AddMemberTagModel extends BaseModel {

    @ApiModelProperty("活动")
    private Integer[] activity;

    @ApiModelProperty("关注时间不早于")
    private String attentionStartTime;

    @ApiModelProperty("关注时间不晚于")
    private String attentionEndTime;

    @ApiModelProperty("本月群发数量")
    private Integer[] batchSendOfMonth;

    @ApiModelProperty("取消关注时间不早于")
    private String cancelStartTime;

    @ApiModelProperty("取消关注时间不晚于")
    private String cancelEndTime;

    @ApiModelProperty("国家ID")
    private Integer country;

    @ApiModelProperty("省份ID")
    private Integer province;

    @ApiModelProperty("城市ID")
    private Integer city;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("是否关注")
    @Getter
    @Setter
    private Integer subscribe;

    private Integer[] memberIds;

    private List<MemberTagModel> memberTags;

    private List<MemberTagModel> encludeMemberTags;

    public List<MemberTagModel> getEncludeMemberTags() {
        return encludeMemberTags;
    }

    public void setEncludeMemberTags(List<MemberTagModel> encludeMemberTags) {
        this.encludeMemberTags = encludeMemberTags;
    }

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("用户标签")
    private List<MemberTagModel> tags;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("是否发送给所有人")
    private Boolean sendToAll;

    @ApiModelProperty("绑定状态")
    private Integer bindStatus;

    @ApiModelProperty("备注模糊匹配")
    private String fuzzyRemarks;

    /**
     * 1:不活跃,2:活跃
     */
    private Boolean isOnline;

    public boolean emptyQuery() {
        return activity == null && StringUtils.isBlank(attentionStartTime)
                && StringUtils.isBlank(attentionEndTime)
                && batchSendOfMonth == null
                && StringUtils.isBlank(cancelStartTime)
                && StringUtils.isBlank(cancelEndTime) && country == null
                && province == null && city == null
                && StringUtils.isBlank(nickname) && StringUtils.isBlank(sex)
                && subscribe == null && memberIds == null
                && (memberTags == null || memberTags.isEmpty())
                && StringUtils.isBlank(mobile) && isOnline == null
                && bindStatus == null;
    }

    public Integer[] getActivity() {
        return activity;
    }

    public String getAttentionEndTime() {
        return attentionEndTime;
    }

    public String getAttentionStartTime() {
        return attentionStartTime;
    }

    public Integer[] getBatchSendOfMonth() {
        return batchSendOfMonth;
    }

    public String getCancelEndTime() {
        return cancelEndTime;
    }

    public String getCancelStartTime() {
        return cancelStartTime;
    }

    public Integer getCity() {
        return city;
    }

    public Integer getCountry() {
        return country;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public Integer[] getMemberIds() {
        return memberIds;
    }

    public MemberModel getMemberModel() {
        AddMemberTagModel addMemberTagModel = this;
        MemberModel mm = new MemberModel();
        if (addMemberTagModel.getActivity() != null) {
            if (addMemberTagModel.getActivity().length > 0) {
                mm.setActivityStartAt(addMemberTagModel.getActivity()[0]);
            }
            if (addMemberTagModel.getActivity().length > 1) {
                mm.setActivityEndAt(addMemberTagModel.getActivity()[1]);
            }
        }
        mm.setAttentionStartAt(DateUtil.utc2DefaultLocal(addMemberTagModel
                .getAttentionStartTime()));
        mm.setAttentionEndAt(DateUtil.utc2DefaultLocal(addMemberTagModel
                .getAttentionEndTime()));
        if (addMemberTagModel.getBatchSendOfMonth() != null) {
            if (addMemberTagModel.getBatchSendOfMonth().length > 0) {
                mm.setBatchSendOfMonthStartAt(addMemberTagModel
                        .getBatchSendOfMonth()[0]);
            }
            if (addMemberTagModel.getBatchSendOfMonth().length > 1) {
                mm.setBatchSendOfMonthEndAt(addMemberTagModel
                        .getBatchSendOfMonth()[1]);
            }
        }
        mm.setCancelSubscribeStartAt(DateUtil
                .utc2DefaultLocal(addMemberTagModel.getCancelStartTime()));
        mm.setCancelSubscribeEndAt(DateUtil.utc2DefaultLocal(addMemberTagModel
                .getCancelEndTime()));
        if (addMemberTagModel.getCountry() != null) {
            mm.setCountry(addMemberTagModel.getCountry());
        }
        if (addMemberTagModel.getProvince() != null) {
            mm.setProvince(addMemberTagModel.getProvince());
        }
        if (addMemberTagModel.getCity() != null) {
            mm.setCity(addMemberTagModel.getCity());
        }
        mm.setNickname(addMemberTagModel.getNickname());
        if (StringUtils.isNotBlank(addMemberTagModel.getSex())) {
            mm.setSex(ParamUtil.getByte(addMemberTagModel.getSex(), null));
        }
        List<MemberTagModel> memberTags = addMemberTagModel.getMemberTags();
        if (memberTags != null && !memberTags.isEmpty()) {
            Integer[] memberTagsArray = new Integer[memberTags.size()];
            for (int i = 0; i < memberTags.size(); i++) {
                memberTagsArray[i] = memberTags.get(i).getId();
            }
            mm.setMemberTags(memberTagsArray);
        }

        List<MemberTagModel> encludeMemberTags = addMemberTagModel.getEncludeMemberTags();
        if (encludeMemberTags != null && !encludeMemberTags.isEmpty()) {
            Integer[] memberTagsArray = new Integer[encludeMemberTags.size()];
            for (int i = 0; i < encludeMemberTags.size(); i++) {
                memberTagsArray[i] =encludeMemberTags.get(i).getId();
            }
            mm.setEncludeMemberTags(memberTagsArray);
        }



        mm.setMobile(addMemberTagModel.getMobile());
        mm.setIsOnline(addMemberTagModel.getIsOnline());
        mm.setSubscribe(Integer.valueOf(1).equals(addMemberTagModel.getSubscribe()));
        return mm;
    }

    public List<MemberTagModel> getMemberTags() {
        return memberTags;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOpenId() {
        return openId;
    }

    public Integer getProvince() {
        return province;
    }

    public Boolean getSendToAll() {
        return sendToAll;
    }

    public String getSex() {
        return sex;
    }

    public List<MemberTagModel> getTags() {
        return tags;
    }

    public void setActivity(Integer[] activity) {
        this.activity = activity;
    }

    public void setAttentionEndTime(String attentionEndTime) {
        this.attentionEndTime = attentionEndTime;
    }

    public void setAttentionStartTime(String attentionStartTime) {
        this.attentionStartTime = attentionStartTime;
    }

    public void setBatchSendOfMonth(Integer[] batchSendOfMonth) {
        this.batchSendOfMonth = batchSendOfMonth;
    }

    public void setCancelEndTime(String cancelEndTime) {
        this.cancelEndTime = cancelEndTime;
    }

    public void setCancelStartTime(String cancelStartTime) {
        this.cancelStartTime = cancelStartTime;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setMemberIds(Integer[] memberIds) {
        this.memberIds = memberIds;
    }

    public void setMemberTags(List<MemberTagModel> memberTags) {
        this.memberTags = memberTags;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public void setSendToAll(Boolean sendToAll) {
        this.sendToAll = sendToAll;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTags(List<MemberTagModel> tags) {
        this.tags = tags;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getFuzzyRemarks() {
        return fuzzyRemarks;
    }

    public void setFuzzyRemarks(String fuzzyRemarks) {
        this.fuzzyRemarks = fuzzyRemarks;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
