package com.d1m.wechat.controller.report;

import com.d1m.wechat.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 通用基于流式处理的Excel导出类
 * Created by Stoney.Liu on 2017/12/20.
 */
public class ReportXlsxStreamView extends AbstractXlsxView {
    private CellProcessor cellProcessor = null;
    private String filename = null;

    public ReportXlsxStreamView(String filename,CellProcessor processor) {
        this.filename = filename;
        this.cellProcessor = processor;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        if(cellProcessor!=null){
            cellProcessor.process(map,workbook,httpServletRequest,httpServletResponse);

            // 设置文件名
            filename = filename + "_" + DateUtil.getCurrentyyyyMMddHHmmss() + ".xlsx";
            if (httpServletRequest.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                filename = new String(filename.toString().getBytes("utf-8"),"iso-8859-1");
            } else {
                filename = URLEncoder.encode(filename.toString(), "UTF-8");
            }
            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + filename);
        }
    }

    public static interface CellProcessor {
        public void process(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    }
}
