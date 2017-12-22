package com.d1m.wechat.controller.estore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.GiftCardOrderDto;
import com.d1m.wechat.pamametermodel.GiftCardOrderSearch;
import com.d1m.wechat.service.IGiftCardOrderService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 礼品卡 订单管理接口
 * Created by Stoney.Liu on 2017/12/19.
 */
@RequestMapping("giftcard/order")
@Controller
@Slf4j
public class GiftCardOrderController extends BaseController {
    @Autowired
    private IGiftCardOrderService giftCardOrderService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    //@RequiresPermissions("giftcard:order-list")
    public JSONObject orderList(@RequestBody(required = false) GiftCardOrderSearch giftCardOrderSearch){
        try {
            Page<GiftCardOrderDto> list = giftCardOrderService.selectOrderList(getWechatId(), giftCardOrderSearch,true);
            return representation(Message.SUCCESS, list.getResult(), giftCardOrderSearch.getPageNum(), giftCardOrderSearch.getPageSize(), list.getTotal());
        } catch (Exception e) {
            log.error("get gift card order list error: ", e);
            return wrapException(e);
        }
    }

    @RequestMapping(value = "export", method = RequestMethod.POST)
    //@RequiresPermissions("giftcard:order-list")
    public void exportOrder(@RequestParam String data,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        OutputStream os = null;
        try {
            GiftCardOrderSearch giftCardOrderSearch = null;
            if(StringUtils.isNotBlank(data)){
                giftCardOrderSearch = JSON.parseObject(data, GiftCardOrderSearch.class);
            }
            if(giftCardOrderSearch == null){
                giftCardOrderSearch = new GiftCardOrderSearch();
            }
            giftCardOrderSearch.disablePage();
            Page<GiftCardOrderDto> list = giftCardOrderService.selectOrderList(getWechatId(), giftCardOrderSearch,true);
            String[] titles = {"订单号", "交易流水号", "支付时间", "购买人OPENID", "领取人OPENID", "领取时间", "总金额", "卡号", "券号", "卡面背景"};
            Workbook wb = new SXSSFWorkbook(1000);
            Sheet sheet = wb.createSheet("sheet1");
            Row titleRow = sheet.createRow(0);
            for (int i=0; i<titles.length; i++){
                titleRow.createCell(i).setCellValue(titles[i]);
            }
            if(list!=null){
                int j = 1;
                for(GiftCardOrderDto dto: list){
                    Row dataRow = sheet.createRow(j);
                    dataRow.createCell(0).setCellValue(dto.getOrderId());
                    dataRow.createCell(1).setCellValue(dto.getTransId());
                    dataRow.createCell(2).setCellValue(DateUtil.formatYYYYMMDDHHMMSS(dto.getPayFinishTime()));
                    dataRow.createCell(3).setCellValue(dto.getOpenId());
                    dataRow.createCell(4).setCellValue(dto.getAccepterOpenId());
                    dataRow.createCell(5).setCellValue(DateUtil.formatYYYYMMDDHHMMSS(dto.getCreateTime()));
                    dataRow.createCell(6).setCellValue(dto.getTotalPrice()==null?"":dto.getTotalPrice().toString());
                    dataRow.createCell(7).setCellValue(dto.getCardId());
                    dataRow.createCell(8).setCellValue(dto.getCode());
                    dataRow.createCell(9).setCellValue(dto.getBackgroundPicUrl());
                    j++;
                }
            }
            // 设置文件名
            String filename = "礼品卡购买订单" + "_" + DateUtil.getCurrentyyyyMMddHHmmss() + ".xlsx";
            if (httpServletRequest.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                filename = new String(filename.toString().getBytes("utf-8"),"iso-8859-1");
            } else {
                filename = URLEncoder.encode(filename.toString(), "UTF-8");
            }
            httpServletResponse.reset();
            httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + filename);
            os = httpServletResponse.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("get gift card export error: ", e);
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
                log.error("get gift card export error: ", e);
            }
        }
    }
}
