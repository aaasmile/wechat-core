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

import com.d1m.wechat.dto.ReportArticleDto;
import com.d1m.wechat.dto.ReportArticleItemDto;
import com.d1m.wechat.util.I18nUtil;

public class ArticleData extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Locale locale = RequestContextUtils.getLocale(request);
		ReportArticleDto data = (ReportArticleDto)model.get("dto");
		List<ReportArticleItemDto> dtos = data.getArticleList();
		String name = I18nUtil.getMessage("report.article", locale);
		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultColumnWidth(10);
		HSSFRow title = sheet.createRow(0);
		String[] keys = { "no", "report.article.title", "report.article.read.uv", "report.article.read.pv", 
				"report.article.sourceread.uv", "report.article.sourceread.pv", "report.article.share.uv", 
				"report.article.share.pv", "report.article.favorite.uv", "report.article.favorite.pv"};
		String[] titleVal = I18nUtil.getMessage(keys, locale);
		for (int i = 0; i < titleVal.length; i++) {
			title.createCell(i).setCellValue(titleVal[i]);
		}

		if(dtos!=null){
			int rowId = 1;
			for (int i = 0; i < dtos.size(); i++) {
				ReportArticleItemDto dto = dtos.get(i);
				HSSFRow dataRow = sheet.createRow(rowId);
				dataRow.setHeight((short) 300);
				dataRow.createCell(0).setCellValue(rowId);
				dataRow.createCell(1).setCellValue(dto.getTitle());
				dataRow.createCell(2).setCellValue(dto.getPageUser());
				dataRow.createCell(3).setCellValue(dto.getPageCount());
				dataRow.createCell(4).setCellValue(dto.getOriPageUser());
				dataRow.createCell(5).setCellValue(dto.getOriPageCount());
				dataRow.createCell(6).setCellValue(dto.getShareUser());
				dataRow.createCell(7).setCellValue(dto.getShareCount());
				dataRow.createCell(8).setCellValue(dto.getAddFavUser());
				dataRow.createCell(9).setCellValue(dto.getAddFavCount());
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
