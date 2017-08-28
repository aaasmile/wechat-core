package com.d1m.wechat.service;

import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.d1m.wechat.model.ReportConfig;

/**
 * ReportConfigService
 *
 * @author f0rb on 2017-01-25.
 */
public interface ReportConfigService extends IService<ReportConfig> {

    String fetchAndWrite(HSSFWorkbook workbook, Integer wechatId, String reportKey, Map params);

    String fetchAndWrite(HSSFWorkbook workbook, Integer wechatId, String reportKey, Map params, Locale locale);

}
