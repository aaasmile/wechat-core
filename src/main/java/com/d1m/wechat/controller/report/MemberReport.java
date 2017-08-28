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

import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.model.enums.Sex;
import com.d1m.wechat.util.I18nUtil;

public class MemberReport extends AbstractExcelView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<MemberDto> memberDtoList = (List<MemberDto>) model
				.get("memberDto");
		Locale locale = RequestContextUtils.getLocale(request);
		String name = I18nUtil.getMessage("follwer.list", locale);
		HSSFSheet sheet = workbook.createSheet(name);
		sheet.setDefaultColumnWidth(11);
		HSSFRow title = sheet.createRow(0);
		String[] keys={"no", "nickname", "gender", "mobile", "province", "city", "subscribe.status", "bind.status",
				"subscribe.time", "group.message.sent", "tag"};
		String[] titleVal = I18nUtil.getMessage(keys, locale);
		for (int i = 0; i < titleVal.length; i++) {
			title.createCell(i).setCellValue(titleVal[i]);
		}
		SimpleDateFormat df = null;

		if(memberDtoList!=null) {
			int rowId = 1;
			for (int i = 0; i < memberDtoList.size(); i++) {
				MemberDto temp = memberDtoList.get(i);
				HSSFRow dataRow = sheet.createRow(rowId);
				dataRow.setHeight((short) 600);
				dataRow.createCell(0).setCellValue(rowId);
				dataRow.createCell(1).setCellValue(temp.getNickname());
				if(temp.getSex() != null){
					dataRow.createCell(2).setCellValue(I18nUtil.getMessage(Sex.getByValue(temp.getSex()).name().toLowerCase(), locale));
				}
				dataRow.createCell(3).setCellValue(temp.getMobile());
				dataRow.createCell(4).setCellValue(temp.getProvince());
				dataRow.createCell(5).setCellValue(temp.getCity());

				String attentionStatus = "subscribe";
				if (!temp.getIsSubscribe()) {
					if (temp.getUnsubscribeAt() != null) {
						attentionStatus = "cancel.subscribe";
					} else {
						attentionStatus = "unsubscribe";
					}
				}
				dataRow.createCell(6).setCellValue(I18nUtil.getMessage(attentionStatus, locale));
				
				if(temp.getBindStatus() != null && temp.getBindStatus() == 1){
					dataRow.createCell(7).setCellValue(I18nUtil.getMessage("bind", locale));
				}else{
					dataRow.createCell(7).setCellValue(I18nUtil.getMessage("unbind", locale));
				}

				df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				if(temp.getIsSubscribe() && temp.getSubscribeAt() != null){
					String attentionTime = df.format(temp.getSubscribeAt());
					dataRow.createCell(8).setCellValue(attentionTime);
				}
				dataRow.createCell(9).setCellValue(temp.getBatchsendMonth()==null?0:temp.getBatchsendMonth());
				StringBuffer tags = new StringBuffer();
				for (MemberTagDto mt : temp.getMemberTags()) {
					tags.append(mt.getName()).append(" | ");
				}
				dataRow.createCell(10).setCellValue(tags.toString());
				/*String unsubscribeTime = null;
				if (!temp.getIsSubscribe() && temp.getUnsubscribeAt() != null) {
					unsubscribeTime = df.format(temp.getUnsubscribeAt());
				}
				dataRow.createCell(8).setCellValue(unsubscribeTime);
				dataRow.createCell(11).setCellValue(temp.getFromwhere());
				dataRow.createCell(12).setCellValue(temp.getActivity());*/
				rowId++;
			}
		}
		df = new SimpleDateFormat("yyyyMMddHHmmss");
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
