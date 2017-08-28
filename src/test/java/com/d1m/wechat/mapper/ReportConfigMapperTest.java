package com.d1m.wechat.mapper;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.d1m.wechat.test.SpringTest;

/**
 * ReportConfigMapperTest
 *
 * @author f0rb on 2017-01-22.
 */
@Slf4j
public class ReportConfigMapperTest extends SpringTest {
    @Resource
    ReportConfigMapper reportConfigMapper;

    String sql = StringUtils.join(new String[]{
            "SELECT",
            "    date_format(mp.bind_at,'%Y-%m-%d') as 绑定日期,",
            "    m.open_id, m.nickname, mp.name, m.mobile,",
            "    date_format(mp.birth_date,'%Y-%m') as birth_date,",
            "    (SELECT c.cfg_key_label FROM config c WHERE c.cfg_key = mp.brand) as 品牌,",
            "    (SELECT c.cfg_key_label FROM config c WHERE c.cfg_key = mp.model) as 机型,",
            "    mp.contact_channel, mp.purchase_channel, mp.monthly_income,",
            "    (SELECT c.cfg_key_label FROM config c WHERE c.cfg_key = mp.province) as province,",
            "    (SELECT c.cfg_key_label FROM config c WHERE c.cfg_key = mp.city) as city,",
            "    mp.address",
            "FROM member m",
            "    LEFT JOIN member_profile mp ON m.id = mp.member_id",
            "WHERE m.wechat_id = #{wechat_id}"
            //"LIMIT #{param.offset}, #{param.limit}"
    }, ' ');

    @Test
    public void report() throws Exception {

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("wechat_id", 2);
        params.put("sql", sql);
        LinkedHashMap<String, Object> reportTitles = reportConfigMapper.getNullRowForTitle(params);

        if (reportTitles != null) {
            log.info("titles: {}", reportTitles.keySet());
        }
        Iterable<LinkedHashMap<String, Object>> reports = reportConfigMapper.getData(params);

        for (LinkedHashMap<String, Object> report : reports) {
            log.info("values: {}", report.values());
        }

        params.put(ReportConfigMapper.SHOW_ROW_NUMBER, "no");
        Iterable<LinkedHashMap<String, Object>> reportsWithRN = reportConfigMapper.getDataWithRowNum(params);

        for (LinkedHashMap<String, Object> report : reportsWithRN) {
            log.info("values: {}", report.values());
        }
    }

}