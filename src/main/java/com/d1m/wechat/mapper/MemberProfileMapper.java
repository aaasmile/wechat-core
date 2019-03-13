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

    @Deprecated
    List<MemberExcel> findMemberExcelByParams(Map<String, Object> params);

    Integer countByParams(Map<String, Object> params);

    List<MemberExcel> findMemberExcelByParamsNew(Map<String, Object> params);

    List<MemberProfile> getByWechatId(@Param("wechatId") Integer wechatId);
}