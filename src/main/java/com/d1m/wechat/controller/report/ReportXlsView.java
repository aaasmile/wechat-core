package com.d1m.wechat.controller.report;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.d1m.wechat.service.ReportConfigService;

/**
 * ReportXlsView
 *
 * @author f0rb on 2017-01-24.
 */
@Slf4j
public class ReportXlsView extends AbstractExcelView {
    public static final String REPORT_KEY = "report_key";
    public static final String WECHAT_ID = "wechat_id";
    public static final String PARAMS = "params";

    @Resource
    private ReportConfigService reportConfigService;

    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String reportKey = (String) model.get(REPORT_KEY);
        Integer wechatId = model.containsKey(WECHAT_ID) ? (Integer) model.get(WECHAT_ID) : 0;
        Map<String, Object> params = (Map<String, Object>) model.get("params");
        if (params == null) {
            params = new HashMap();
        }

        String filename = reportConfigService.fetchAndWrite(workbook, wechatId, reportKey, params, RequestContextUtils.getLocale(request));
        configFilename(filename, request, response);
    }

    private void configFilename(String filename, HttpServletRequest request, HttpServletResponse response) {
        //String filename = String.valueOf(model.get(FILENAME));
        if (filename != null && filename.length() > 0) {
            if (!filename.endsWith(".xls")) {
                filename = filename + ".xls";
            }
            try {
                String userAgent = request.getHeader("User-Agent");
                byte[] bytes = userAgent.contains("MSIE") ? filename.getBytes() : filename.getBytes("UTF-8"); // filename.getBytes("UTF-8")处理safari的乱码问题
                filename = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
                response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename)); // 文件名外的双引号处理firefox的空格截断问题
            } catch (UnsupportedEncodingException e) {//忽略
            }
        }
    }
}

