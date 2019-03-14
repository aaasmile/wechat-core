package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.MemberProfileDto;
import com.d1m.wechat.model.MemberExcel;
import com.d1m.wechat.model.MemberProfile;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberProfileMapper extends MyMapper<MemberProfile> {

    MemberProfile getByMemberId(@Param("wechatId") Integer wechatId,
                                @Param("memberId") Integer memberId);

    MemberProfileDto getMemberProfile(@Param("wechatId") Integer wechatId,
                                      @Param("memberId") Integer memberId);

    int getBindNumber(@Param("wechatId") Integer wechatId, @Param("start") Date start,
                      @Param("end") Date end);

    int getUnBindNumber(@Param("wechatId") Integer wechatId, @Param("start") Date start,
                        @Param("end") Date end);

    Integer getMemberBindStatus(@Param("id") Integer id, @Param("wechatId") Integer wechatId);

    List<MemberExcel> findMemberExcelByParams(Map<String, Object> params);

    Integer countByParams(@Param("wechatId") Integer wechatId,
                          @Param("openId") String openId, @Param("nickname") String nickname,
                          @Param("sex") Byte sex, @Param("country") Integer country,
                          @Param("province") Integer province, @Param("city") Integer city,
                          @Param("subscribe") Integer subscribe,
                          @Param("activityStartAt") Integer activityStartAt,
                          @Param("activityEndAt") Integer activityEndAt,
                          @Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt,
                          @Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt,
                          @Param("attentionStartAt") Date attentionStartAt,
                          @Param("attentionEndAt") Date attentionEndAt,
                          @Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt,
                          @Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt,
                          @Param("isOnline") Boolean isOnline,
                          @Param("mobile") String mobile,
                          @Param("memberTagIds") Integer[] memberTags,
                          @Param("bindStatus") Integer bindStatus,
                          @Param("fuzzyRemarks") String fuzzyRemarks);

    List<MemberExcel> findMemberExcelByParamsNew(@Param("wechatId") Integer wechatId,
                                                 @Param("maxId") Integer maxId, @Param("rows") Integer rows, @Param("offset") Integer offset,
                                                 @Param("openId") String openId, @Param("nickname") String nickname,
                                                 @Param("sex") Byte sex, @Param("country") Integer country,
                                                 @Param("province") Integer province, @Param("city") Integer city,
                                                 @Param("subscribe") Integer subscribe,
                                                 @Param("activityStartAt") Integer activityStartAt,
                                                 @Param("activityEndAt") Integer activityEndAt,
                                                 @Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt,
                                                 @Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt,
                                                 @Param("attentionStartAt") Date attentionStartAt,
                                                 @Param("attentionEndAt") Date attentionEndAt,
                                                 @Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt,
                                                 @Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt,
                                                 @Param("isOnline") Boolean isOnline,
                                                 @Param("mobile") String mobile,
                                                 @Param("memberTagIds") Integer[] memberTags,
                                                 @Param("bindStatus") Integer bindStatus,
                                                 @Param("fuzzyRemarks") String fuzzyRemarks);

    List<MemberProfile> getByWechatId(@Param("wechatId") Integer wechatId);
}