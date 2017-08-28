package com.d1m.wechat.service.impl;

import java.util.*;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.ReportConfigMapper;
import com.d1m.wechat.model.ReportConfig;
import com.d1m.wechat.service.ReportConfigService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.I18nUtil;

/**
 * DefaultReportDataProvider
 *
 * @author f0rb on 2017-01-25.
 */
@Slf4j
@Service
public class ReportConfigServiceImpl extends BaseService<ReportConfig> implements ReportConfigService {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static final Integer SHEET_MAX_ROW = 10000;
    public static final Integer TITLE_ROW_IDX = 0;
    @Resource
    private ReportConfigMapper reportConfigMapper;

    @Override
    public String fetchAndWrite(HSSFWorkbook workbook, Integer wechatId, String reportKey, Map params) {
        return fetchAndWrite(workbook, wechatId, reportKey, params, Locale.CHINA);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String fetchAndWrite(HSSFWorkbook workbook, Integer wechatId, String reportKey, Map params, Locale locale) {
        List<ReportConfig> reportConfigList = reportConfigMapper.listByKey(wechatId, reportKey);
        if (reportConfigList.isEmpty()) {
            log.warn("报表未配置: wechatId:{}, reportKey:{}", wechatId, reportKey);
            final HSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultRowHeight((short) 300);
            sheet.createRow(TITLE_ROW_IDX)
                 .createCell(0)
                 .setCellValue("报表未配置: " + reportKey);
            sheet.autoSizeColumn(0);
            return null;
        }

        for (ReportConfig reportConfig : reportConfigList) {
            //创建sheet
            String sheetname = reportConfig.getSheetName();
            HSSFSheet sheet = workbook.createSheet(sheetname);
            int sheetIdx = 1;
            sheet.setDefaultColumnWidth(10);
            sheet.setDefaultRowHeight((short) 300);
            params.put(ReportConfigMapper.SQL_KEY, reportConfig.getReportSql());
            Iterable<LinkedHashMap<String, Object>> data;
            if (params.containsKey(ReportConfigMapper.SHOW_ROW_NUMBER)) {
                data = reportConfigMapper.getDataWithRowNum(params);
            } else {
                data = reportConfigMapper.getData(params);
            }
            String[] titles = null;
            int row = 0;
            for (LinkedHashMap<String, Object> result : data) {
                if (row % 500 == 0 && log.isDebugEnabled()) {
                    log.debug("已处理{}行数据", row + (sheetIdx - 1) * SHEET_MAX_ROW);
                }
                if (row == 0) {
                    titles = I18nUtil.getMessage(result.keySet().toArray(EMPTY_STRING_ARRAY), locale);
                }
                row++;//数据从第一行开始填充
                HSSFRow hssfRow = sheet.createRow(row);
                Object[] dataRow = result.values().toArray();
                for (int col = 0; col < dataRow.length; col++) {
                    if (dataRow[col] != null) {
                        if (dataRow[col] instanceof Number) {
                            if (dataRow[col] instanceof Double) {
                                hssfRow.createCell(col).setCellValue((Double) dataRow[col]);
                            } else {
                                hssfRow.createCell(col).setCellValue(Double.valueOf(String.valueOf(dataRow[col])));
                            }
                        } else {
                            hssfRow.createCell(col).setCellValue(String.valueOf(dataRow[col]));
                        }

                    }
                }

                if (row >= SHEET_MAX_ROW) {//每个sheet最多SHEET_MAX_ROW行数据, xls最多可以有65535行
                    //填充前一个sheet的标题
                    sheet.createRow(TITLE_ROW_IDX);
                    for (int col = 0; col < titles.length; col++) {
                        sheet.getRow(TITLE_ROW_IDX).createCell(col).setCellValue(titles[col]);
                        sheet.autoSizeColumn(col);
                    }
                    sheet = workbook.createSheet(sheetname + "_" + sheetIdx);
                    sheetIdx++;
                    row = 0;
                }
            }
            log.debug("总计处理{}行数据!", row + (sheetIdx - 1) * SHEET_MAX_ROW);

            //没有数据时再从数据库查一行空数据
            if (titles == null) {
                LinkedHashMap<String, Object> empty = reportConfigMapper.getNullRowForTitle(params);
                if (empty != null) {
                    titles = I18nUtil.getMessage(empty.keySet().toArray(EMPTY_STRING_ARRAY), locale);
                } else {
                    titles = new String[]{"未查询到有效数据"};
                }
            }

            //填充最后一个sheet的标题
            sheet.createRow(TITLE_ROW_IDX);
            for (int col = 0; col < titles.length; col++) {
                sheet.getRow(TITLE_ROW_IDX).createCell(col).setCellValue(titles[col]);
                sheet.autoSizeColumn(col);
            }
            log.info("{}共有{}列", sheet.getSheetName(), sheet.getRow(TITLE_ROW_IDX).getLastCellNum());
        }
        String filename = reportConfigList.get(0).getReportName();
        //文件名格式化
        filename = filename.replace("%D", DateUtil.yyyyMMddHHmmss.format(new Date()));
        return filename;
    }

    @Override
    public Mapper<ReportConfig> getGenericMapper() {
        return reportConfigMapper;
    }
}