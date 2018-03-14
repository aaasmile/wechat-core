package com.d1m.wechat.controller.estore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.report.ReportXlsxStreamView;
import com.d1m.wechat.pamametermodel.EstoreOrderEntity;
import com.d1m.wechat.pamametermodel.EstoreOrderProductEntity;
import com.d1m.wechat.pamametermodel.EstoreOrderSearch;
import com.d1m.wechat.service.IEstoreOrderService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * EStore 订单管理接口
 * Created by Stoney.Liu on 2017/12/19.
 */
@RequestMapping("estore/order")
@Controller
@Slf4j
public class EstoreOrderController extends BaseController {
    @Autowired
    private IEstoreOrderService estoreOrderService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    //@RequiresPermissions("estore:order-list")
    public JSONObject orderList(@RequestBody(required = false) EstoreOrderSearch estoreOrderSearch){
        try {
            Integer wechatId=estoreOrderSearch.getWechatId()==null?getWechatId():estoreOrderSearch.getWechatId();
            Page<EstoreOrderEntity> list = estoreOrderService.selectOrderList(wechatId, estoreOrderSearch, true);
            return representation(Message.SUCCESS, list.getResult(), estoreOrderSearch.getPageNum(),estoreOrderSearch.getPageNum(),list.getTotal() );
        } catch (Exception e) {
            log.error("get order list error: ", e);
            return wrapException(e);
        }
    }

    @RequestMapping(value = "export", method = RequestMethod.POST)
    //@RequiresPermissions("estore:order-list")
    public ModelAndView exportOrder(@RequestParam String data){
        ReportXlsxStreamView view = null;
        try {
            EstoreOrderSearch estoreOrderSearch = null;
            if(StringUtils.isNotBlank(data)){
                estoreOrderSearch = JSON.parseObject(data, EstoreOrderSearch.class);
            }
            if(estoreOrderSearch == null){
                estoreOrderSearch = new EstoreOrderSearch();
            }
            estoreOrderSearch.disablePage();
            Integer wechatId=estoreOrderSearch.getWechatId()==null?getWechatId():estoreOrderSearch.getWechatId();
            Page<EstoreOrderEntity> list = estoreOrderService.selectOrderList(wechatId, estoreOrderSearch,true);
            String[] titles = {"订单号", "支付状态", "物流状态", "时间", "配送人", "配送电话", "省", "市", "区" , "地址" ,"总金额", "产品", "产品编码", "SKU", "市场价", "实际价", "数量"};
            view = new ReportXlsxStreamView("订单",
                    new ReportXlsxStreamView.CellProcessor() {
                        @Override
                        public void process(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
                            Sheet sheet = workbook.createSheet();
                            Row titleRow = sheet.createRow(0);
                            for (int i=0; i<titles.length; i++){
                                titleRow.createCell(i).setCellValue(titles[i]);
                            }
                            if(list!=null){
                                int j = 1;
                                for(EstoreOrderEntity dto: list){
                                    Row dataRow = sheet.createRow(j);
                                    dataRow.createCell(0).setCellValue(dto.getOrderNo());
                                    dataRow.createCell(1).setCellValue(getPayStatus(dto.getPayStatus()));
                                    dataRow.createCell(2).setCellValue(getStatus(dto.getStatus()));
                                    dataRow.createCell(3).setCellValue(dto.getCreateAt());
                                    dataRow.createCell(4).setCellValue(dto.getDeliveryName());
                                    dataRow.createCell(5).setCellValue(dto.getDeliveryPhone());
                                    dataRow.createCell(6).setCellValue(dto.getDeliveryProvince());
                                    dataRow.createCell(7).setCellValue(dto.getDeliveryCity());
                                    dataRow.createCell(8).setCellValue(dto.getDeliveryDistrict());
                                    dataRow.createCell(9).setCellValue(dto.getDeliveryAddress());
                                    dataRow.createCell(10).setCellValue(dto.getTotalAmount().doubleValue());

                                    //有购物车时，产品列表可能为多个
                                    //"产品", "产品编码", "SKU", "市场价", "实际价", "数量"
                                    List<EstoreOrderProductEntity> products = dto.getListOrderProduct();
                                    int k = 0;
                                    for(EstoreOrderProductEntity product: products){
                                        if(k>0){
                                            j++;
                                            dataRow = sheet.createRow(j);
                                        }
                                        dataRow.createCell(11).setCellValue(product.getProductName());
                                        dataRow.createCell(12).setCellValue(product.getProductCode());
                                        dataRow.createCell(13).setCellValue(product.getSku());
                                        dataRow.createCell(14).setCellValue(product.getMarketPrice()==null?"":product.getMarketPrice().toString());
                                        dataRow.createCell(15).setCellValue(product.getPrice()==null?"":product.getPrice().toString());
                                        dataRow.createCell(16).setCellValue(product.getQuantity());
                                        k++;
                                    }
                                    j++;
                                }
                            }
                        }
                    }
            );
        } catch (Exception e) {
            log.error("order export error: ", e);
        }
        return new ModelAndView(view);
    }

    private String getPayStatus(Byte status){
        switch (status){
            case 0:
                return "未支付";
            case 1:
                return "已支付";
        }
        return null;
    }

    private String getStatus(Byte status){
        switch (status){
            case 0:
                return "待支付";
            case 1:
                return "待发货";
            case 2:
                return "已发货";
            case 3:
                return "已签收";
        }
        return null;
    }
}
