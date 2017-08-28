package com.d1m.wechat.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cursor.Cursor;

import com.d1m.wechat.model.ReportConfig;
import com.d1m.wechat.util.MyMapper;

public interface ReportConfigMapper extends MyMapper<ReportConfig> {
    String Table = "report_config";
    String SQL_KEY = "sql";
    String SHOW_ROW_NUMBER = "showRN";

    @Select("SELECT COUNT(*) > 0 FROM report_config WHERE report_key = #{key}")
    boolean checkKey(String key);

    @Select({
            "SELECT * FROM report_config WHERE id in (",
            "    SELECT max(id) FROM report_config",
            "    WHERE report_key = #{report_key} AND wechat_id in (0, #{wechat_id})",
            "    GROUP BY sheet_key",
            ")"
    })
    @ResultMap("BaseResultMap")
    List<ReportConfig> listByKey(@Param("wechat_id") Integer wechatId, @Param("report_key") String key);

    @Select("${" + SQL_KEY + "}")
    @Options(useCache = false, fetchSize = 100)
    Cursor<LinkedHashMap<String, Object>> getData(Map<String, ?> map);

    @Select("SELECT @rn:=@rn+1 as ${" + SHOW_ROW_NUMBER + "}, t.* FROM (${" + SQL_KEY + "}) t, (Select (@rn :=0) ) r")
    @Options(useCache = false, fetchSize = 100)
    //@ResultMap({
    //        @Result
    //})
    Cursor<LinkedHashMap<String, Object>> getDataWithRowNum(Map<String, ?> map);

    @Select("SELECT t.* FROM (SELECT 0) AS d LEFT JOIN (${" + SQL_KEY + "} limit 0) AS t ON 1 = 1;")
    @Options(useCache = false)
    LinkedHashMap<String, Object> getNullRowForTitle(Map<String, ?> map);

}