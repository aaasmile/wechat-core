package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.dto.MemberLevelDto;
import com.d1m.wechat.dto.MemberStatusDto;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.PieBaseDto;
import com.d1m.wechat.dto.ReportActivityUserDto;
import com.d1m.wechat.dto.ReportAreaBaseDto;
import com.d1m.wechat.dto.ReportUserSourceDto;
import com.d1m.wechat.dto.TrendBaseDto;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MemberMapper extends MyMapper<Member> {

	Page<MemberDto> search(@Param("wechatId") Integer wechatId,
			@Param("openId") String openId, @Param("nickname") String nickname,
			@Param("sex") Byte sex, @Param("country") Integer country,
			@Param("province") Integer province, @Param("city") Integer city,
			@Param("subscribe") Boolean subscribe,
			@Param("activityStartAt") Integer activityStartAt,
			@Param("activityEndAt") Integer activityEndAt,
			@Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt,
			@Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt,
			@Param("attentionStartAt") Date attentionStartAt,
			@Param("attentionEndAt") Date attentionEndAt,
			@Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt,
			@Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt,
			@Param("isOnline") Boolean isOnline,
			@Param("fromWhere") String fromWhere,
			@Param("mobile") String mobile,
			@Param("memberTags") Integer[] memberTags,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir,
			@Param("bindStatus") Integer bindStatus, @Param("daytime") Date daytime);

	Page<MemberDto> massMembersSearch(@Param("wechatId") Integer wechatId,
			@Param("openId") String openId, @Param("nickname") String nickname,
			@Param("sex") Byte sex, @Param("country") Integer country,
			@Param("province") Integer province, @Param("city") Integer city,
			@Param("subscribe") Boolean subscribe,
			@Param("activityStartAt") Integer activityStartAt,
			@Param("activityEndAt") Integer activityEndAt,
			@Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt,
			@Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt,
			@Param("attentionStartAt") Date attentionStartAt,
			@Param("attentionEndAt") Date attentionEndAt,
			@Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt,
			@Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt,
			@Param("isOnline") Boolean isOnline,
			@Param("fromWhere") String fromWhere,
			@Param("mobile") String mobile,
			@Param("memberTags") Integer[] memberTags,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir,
			@Param("bindStatus") Integer bindStatus);

	Long count(@Param("wechatId") Integer wechatId,
						   @Param("openId") String openId, @Param("nickname") String nickname,
						   @Param("sex") Byte sex, @Param("country") Integer country,
						   @Param("province") Integer province, @Param("city") Integer city,
						   @Param("subscribe") Boolean subscribe,
						   @Param("activityStartAt") Integer activityStartAt,
						   @Param("activityEndAt") Integer activityEndAt,
						   @Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt,
						   @Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt,
						   @Param("attentionStartAt") Date attentionStartAt,
						   @Param("attentionEndAt") Date attentionEndAt,
						   @Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt,
						   @Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt,
						   @Param("isOnline") Boolean isOnline,
						   @Param("fromWhere") String fromWhere,
						   @Param("mobile") String mobile,
						   @Param("memberTags") Integer[] memberTags,
						   @Param("sortName") String sortName, @Param("sortDir") String sortDir,
						   @Param("bindStatus") Integer bindStatus, @Param("daytime") Date daytime);

	List<MemberDto> selectByWechat(@Param("wechatId") Integer wechatId);

	List<MemberDto> selectByMemberId(@Param("ids") Integer[] memberId,
			@Param("wechatId") Integer wechatId, @Param("isForce") Boolean isForce);

	Long countByMemberId(@Param("ids") Integer[] memberId,
									 @Param("wechatId") Integer wechatId, @Param("isForce") Boolean isForce);

	List<MemberTagDto> getMemberMemberTags(@Param("wechatId") Integer wechatId,
			@Param("memberId") Integer memberId);

	List<MemberDto> searchBySql(@Param("wechatId") Integer wechatId,
			@Param("sql") String sql);

	Integer trendReportTotal(@Param("wechatId") Integer wechatId, @Param("end") Date end);

	List<TrendBaseDto> trendReportAttention(
			@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);

	List<TrendBaseDto> trendReportCancel(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);

	List<PieBaseDto> pieReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("type") String type);

	Page<ReportActivityUserDto> activityUser(
			@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end, @Param("top") Integer top);

	List<ReportUserSourceDto> sourceUser(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);

	List<ReportAreaBaseDto> pieAreaReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("type") String type);

	List<ReportAreaBaseDto> getProvice(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("country") Integer country);

	List<ReportAreaBaseDto> getCity(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("country") Integer country,
			@Param("province") Integer province);

	void deleteMemberTag(@Param("wechatId") Integer wechatId,
			@Param("memberId") Integer memberId,
			@Param("memberTagId") Integer memberTagId);

	List<MemberStatusDto> memberStatus(@Param("wechatId") Integer wechatId,
			@Param("end") Date end);

	MemberLevelDto selectMemberProfile(@Param("id") Integer id, @Param("wechatId") Integer wechatId);

	MemberDto selectByOpenId(@Param("openId") String openId, @Param("wechatId") Integer wechatId);

	Integer selectWechatIdByOpenId(@Param("openId") String openId);

	List<Integer> getMemberIdsByOpenIds(@Param("openIdList") List<String> openIdList);

	List<TrendBaseDto> trendReportAttentionTimes(@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);

	List<TrendBaseDto> trendReportCancelTimes(@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);

	void resetMonthSend();

    @SelectProvider(type = MemberSqlProvider.class, method = "query")
    List<Member> query(Member record);

    //@SelectProvider(type = MemberSqlProvider.class, method = "count")
    //int count(Member record);

    @InsertProvider(type = MemberSqlProvider.class, method = "batchInsertOpenId")
    int batchInsertOpenId(@Param("wechatId") Integer wechatId, @Param("list") List<String> openidList);

    String TABLE_NAME = "member";

    class MemberSqlProvider {
        private String queryOrCount(final Member record, final boolean query) {
            return new SQL() {{
                SELECT(query ? "*" : "COUNT(*)");
                FROM(TABLE_NAME);
                if (record.getId() != null) {
                    WHERE("id = #{id}");
                }
                //if (record.getName() != null) {
                //    WHERE("name like CONCAT('%',#{name},'%')");
                //}
                //if (query) {
                //    ORDER_BY("createTime desc");
                //}
            }}.toString();
        }

        public String query(Member record) {
            return queryOrCount(record, true);
        }

        public String count(Member record) {
            return queryOrCount(record, false);
        }

        @SuppressWarnings("unchecked")
        public String batchInsertOpenId(Map<String, ?> map) {
            List<String> openidList = (List<String>) map.get("list");
            Integer wechatId = (Integer) map.get("wechatId");
            String createAt = DateUtil.formatYYYYMMDDHHMMSS(new Date());

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT IGNORE INTO ").append(TABLE_NAME);
            sb.append("(wechat_id, open_id, activity, batchsend_month, is_subscribe, created_at) ");
            sb.append("VALUES ");
            sb.append("(")
              .append(wechatId)
              .append(", #{list[0]}")
              .append(", 5, 0, false, '")
              .append(createAt)
              .append("')");
            for (int i = 1; i < openidList.size(); i++) {
                sb.append(",")
                  .append("(")
                  .append(wechatId)
                  .append(", #{list[").append(i).append("]}")
                  .append(", 5, 0, false, '")
                  .append(createAt)
                  .append("')");
            }
            return sb.toString();
        }
    }

	List<Integer> searchIds(@Param("wechatId") Integer wechatId,
			@Param("openId") String openId, @Param("nickname") String nickname,
			@Param("sex") Byte sex, @Param("country") Integer country,
			@Param("province") Integer province, @Param("city") Integer city,
			@Param("subscribe") Boolean subscribe,
			@Param("activityStartAt") Integer activityStartAt,
			@Param("activityEndAt") Integer activityEndAt,
			@Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt,
			@Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt,
			@Param("attentionStartAt") Date attentionStartAt,
			@Param("attentionEndAt") Date attentionEndAt,
			@Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt,
			@Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt,
			@Param("isOnline") Boolean isOnline,
			@Param("fromWhere") String fromWhere,
			@Param("mobile") String mobile,
			@Param("memberTags") Integer[] memberTags,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir,
			@Param("bindStatus") Integer bindStatus, @Param("offset") Integer offset,
			@Param("pageSize") Integer pageSize);

	Page<MemberDto> searchByIds(@Param("ids") List<Integer> ids, @Param("size") int size);
}
