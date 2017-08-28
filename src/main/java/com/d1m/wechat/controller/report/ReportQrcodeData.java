package com.d1m.wechat.controller.report;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.d1m.wechat.dto.ReportQrcodeItemDto;
import com.d1m.wechat.util.I18nUtil;

public class ReportQrcodeData extends AbstractExcelView{

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<ReportQrcodeItemDto> dtos = (List<ReportQrcodeItemDto>)model.get("dtos");
		Locale locale = RequestContextUtils.getLocale(request);
		String name = I18nUtil.getMessage("report.qrcode", locale);
		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultColumnWidth(6);
		HSSFRow title = sheet.createRow(0);
		String[] keys = {"no", "report.qrcode.title", "report.qrcode.category", "report.qrcode.total.scan",
				"report.qrcode.total.scan.follower", "report.qrcode.new.scan.follower"};
		String[] titleVal = I18nUtil.getMessage(keys, locale);
		for (int i = 0; i < titleVal.length; i++) {
			title.createCell(i).setCellValue(titleVal[i]);
		}

		if(dtos!=null) {
			int rowId = 1;
			for (int i = 0; i < dtos.size(); i++) {
				ReportQrcodeItemDto dto = dtos.get(i);
				HSSFRow dataRow = sheet.createRow(rowId);
				dataRow.setHeight((short) 300);
				dataRow.createCell(0).setCellValue(rowId);
				dataRow.createCell(1).setCellValue(dto.getQrCodeName());
				dataRow.createCell(2).setCellValue(dto.getQrCodeType());
				dataRow.createCell(3).setCellValue(dto.getScanNum());
				dataRow.createCell(4).setCellValue(dto.getUserByScan());
				dataRow.createCell(5).setCellValue(dto.getAttentionByScan());
				rowId++;
			}
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = df.format(new Date());
		String filename = name + "_" + date + ".xls";

		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			filename = new String(filename.toString().getBytes("utf-8"),
					"iso-8859-1");
		} else {
			filename = URLEncoder.encode(filename.toString(), "UTF-8");
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename);
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
	
	

}
