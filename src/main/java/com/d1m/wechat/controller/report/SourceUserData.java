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

import com.d1m.wechat.dto.ReportUserSourceDto;
import com.d1m.wechat.util.I18nUtil;

public class SourceUserData extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ReportUserSourceDto data = (ReportUserSourceDto)model.get("dto");
		List<ReportUserSourceDto> dtos = data.getList();
		Locale locale = RequestContextUtils.getLocale(request);
		String name = I18nUtil.getMessage("report.follower.source", locale);
		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultColumnWidth(11);
		HSSFRow title = sheet.createRow(0);
		String[] keys = {"no", "date", "public.account.search", "name.card.share", "qrcode.scan", 
				"top.right.menu.of.article.page", "public.account.name.of.article.page", "wechat.in.article.ad", 
				"wechat.moment.ad", "subscription.after.wechat.payment", "others"};
		String[] titleVal = I18nUtil.getMessage(keys, locale);
		for (int i = 0; i < titleVal.length; i++) {
			title.createCell(i).setCellValue(titleVal[i]);
		}

		if(dtos!=null) {
			int rowId = 1;
			for (int i = 0; i < dtos.size(); i++) {
				ReportUserSourceDto dto = dtos.get(i);
				HSSFRow dataRow = sheet.createRow(rowId);
				dataRow.setHeight((short) 300);
				dataRow.createCell(0).setCellValue(rowId);
				dataRow.createCell(1).setCellValue(dto.getDate());
				dataRow.createCell(2).setCellValue(dto.getWechatSearch());
				dataRow.createCell(3).setCellValue(dto.getBusinessCard());
				dataRow.createCell(4).setCellValue(dto.getQrCode());
				dataRow.createCell(5).setCellValue(dto.getTopRightMenu());
				dataRow.createCell(6).setCellValue(dto.getWechatInImageText());
				dataRow.createCell(7).setCellValue(dto.getWechatInArticleAd());
				dataRow.createCell(8).setCellValue(dto.getMomentsAd());
				dataRow.createCell(9).setCellValue(dto.getPayAttentionAfter());
				dataRow.createCell(10).setCellValue(dto.getOther());
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
