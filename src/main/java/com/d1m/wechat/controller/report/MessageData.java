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

import com.d1m.wechat.dto.ReportMessageDto;
import com.d1m.wechat.dto.ReportMessageItemDto;
import com.d1m.wechat.util.I18nUtil;

public class MessageData extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ReportMessageDto data = (ReportMessageDto)model.get("dto");
		String type = (String)model.get("type");
		List<ReportMessageItemDto> dtos = data.getList();
		Locale locale = RequestContextUtils.getLocale(request);
		String name = I18nUtil.getMessage("report.message", locale);
		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultColumnWidth(9);
		HSSFRow title = sheet.createRow(0);
		String[] titleVal = null;
		String[] keys = null;
		if(type!=null && type.equals("day")){
			keys = new String[]{"no", "date", "article", "image", "text", "audio", "video", "menu.click", "qrcode.scan"};
		}else if(type!=null && type.equals("hour")){
			keys = new String[]{"no", "hour", "article", "image", "text", "audio", "video", "menu.click", "qrcode.scan"};
		}
		titleVal = I18nUtil.getMessage(keys, locale);
		for (int i = 0; i < titleVal.length; i++) {
			title.createCell(i).setCellValue(titleVal[i]);
		}

		if(dtos!=null) {
			int rowId = 1;
			for (int i = 0; i < dtos.size(); i++) {
				ReportMessageItemDto dto = dtos.get(i);
				HSSFRow dataRow = sheet.createRow(rowId);
				dataRow.setHeight((short) 300);
				dataRow.createCell(0).setCellValue(rowId);
				dataRow.createCell(1).setCellValue(dto.getDate());
				dataRow.createCell(2).setCellValue(dto.getImagetext());
				dataRow.createCell(3).setCellValue(dto.getImage());
				dataRow.createCell(4).setCellValue(dto.getText());
				dataRow.createCell(5).setCellValue(dto.getVoice());
				dataRow.createCell(6).setCellValue(dto.getVideo());
				dataRow.createCell(7).setCellValue(dto.getClick());
				dataRow.createCell(8).setCellValue(dto.getScan());
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
