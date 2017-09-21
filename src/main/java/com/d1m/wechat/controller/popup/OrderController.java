package com.d1m.wechat.controller.popup;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.mapper.PopupOrderMapper;
import com.d1m.wechat.model.popup.PopupOrderFilter;
import com.d1m.wechat.model.popup.PopupOrderList;
import com.d1m.wechat.model.popup.dao.PopupCountryArea;
import com.d1m.wechat.service.IPopupOrderService;
import com.d1m.wechat.service.IPopupPayService;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.model.enums.OrderEnum;
import com.d1m.wechat.service.MemberService;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.d1m.wechat.service.IWechatPopupMApiService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;

/**
 * Created by Owen Jia on 2017/7/5.
 */
@RequestMapping("order")
@Controller
public class OrderController extends BaseController {

    private Logger log = LoggerFactory.getLogger(OrderController.class);

    private static boolean debug = false;

    @Autowired
    IWechatPopupMApiService wechatPopupMApiService;
    @Autowired
    MemberService memberService;
    @Autowired
    IPopupPayService popupPayService;
    @Autowired
    IPopupOrderService popupOrderService;
    @Autowired
    PopupOrderMapper popupOrderMapper;

//    public OrderEnum getEnumByName(String name) {
//        if (StringUtils.isBlank(name)) {
//            return null;
//        }
//        for (OrderEnum order : OrderEnum.values()) {
//            if (StringUtils.equals(order.toString(), name)) {
//                return order;
//            }
//        }
//        return null;
//    }

    /**
     * 导出订单列表数据
     */
    @ResponseBody
    @RequestMapping(value = "export", method = {RequestMethod.POST})
    @RequiresPermissions("popup-store:order-manage")
    public void exportOrderList(@RequestBody(required = true) PopupOrderFilter orderFilter, HttpServletRequest request, HttpServletResponse response) {

        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=OrderList-" + DateUtil.getCurrentyyyyMMdd() + ".xls");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            orderFilter.setWechatId(debug ? 32 : getWechatId(request.getSession()));
            List<PopupOrderList> listOrderList = popupOrderMapper.selectPopupOrderList(orderFilter);
            HSSFWorkbook xwb = new HSSFWorkbook();

            HSSFSheet sheet = xwb.createSheet("订单");
            HSSFRow row = sheet.createRow(0);
            String[] tabs = {"序号", "日期", "订单编号", "姓名", "手机号码", "配送地址", "配送信息发送至另一个手机",
                    "礼品卡", "礼品卡内容", "发票", "发票类型", "发票抬头", "税号", "支付方式"};
            for (int i = 0; i < tabs.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(tabs[i]);
            }

            HashMap<Integer, String> hmArea = new HashMap<>();
            List<PopupCountryArea> liCountryArea = popupPayService.queryCountryArea();
            for (PopupCountryArea area : liCountryArea) {
                hmArea.put(Integer.parseInt(area.getCode()), area.getNameZh());
            }

            int i = 0;
            for (PopupOrderList orderList : listOrderList) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(i + 1 + "");
                arrayList.add(DateUtil.formatDate(orderList.getOrderCreateTime(), "yyyy/MM/dd"));
                arrayList.add(String.valueOf(orderList.getOrderId()));
                arrayList.add(orderList.getReceiverName());
                arrayList.add(orderList.getReceiverPhone());
                String province = hmArea.get(orderList.getProvince());
                String city = hmArea.get(orderList.getCity());
                String area = hmArea.get(orderList.getArea());
                String addr = orderList.getAddress();
                arrayList.add(province + " " + city + " " + area + " " + addr);
                String msmPhone = StringUtils.isNotEmpty(orderList.getMsmPhone()) ? orderList.getMsmPhone() : "";
                String anotherPhone = msmPhone.equals(orderList.getReceiverPhone()) ? "NO" : msmPhone;
                arrayList.add(anotherPhone);
                String hasGift = StringUtils.isNotEmpty(orderList.getGiftContent()) ? "YES" : "NO";
                arrayList.add(hasGift);
                arrayList.add(orderList.getGiftContent());
                String hasInvoice = orderList.getInvoiceType() != null && orderList.getInvoiceType() > 0 ? "YES" : "NO";
                arrayList.add(hasInvoice);
                if (hasInvoice.equals("YES")) {
                    String invoiceType = orderList.getInvoiceType() == 1 ? OrderEnum.PERSONAL.getDesc() : OrderEnum.COMPANY.getDesc();
                    arrayList.add(invoiceType);
                    arrayList.add(orderList.getInvoiceTitle());
                    String creditCode = StringUtils.isEmpty(orderList.getCreditCode()) ? orderList.getCreditCode() : "";
                    String personNo = StringUtils.isNotEmpty(orderList.getPersonNo()) ? orderList.getPersonNo() : "";
                    String taxCode = "";
                    if (!"".equals(creditCode)) {
                        taxCode = "creditCode:" + creditCode;
                    } else if (!"".equals(personNo)) {
                        taxCode = "personNo:" + personNo;
                    }
                    arrayList.add(taxCode);
                } else {
                    arrayList.add("");
                    arrayList.add("");
                    arrayList.add("");
                }
                for (OrderEnum m : OrderEnum.values()) {
                    if (m.getDesc().startsWith("ORDER_") && m.getCode().equals(orderList.getPayStatus())) {
                        arrayList.add(m.getDesc());
                        break;
                    }
                }
                HSSFRow rowi = sheet.createRow(i + 1);
                for (int k = 0; k < arrayList.size(); k++) {
                    HSSFCell cell = rowi.createCell(k);
                    cell.setCellValue(arrayList.get(k));
                }
                i++;
            }
            OutputStream os = response.getOutputStream();
            xwb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getOutputStream().write(Message.SYSTEM_ERROR.toString().getBytes(Charset.forName("UTF-8")));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

//    @RequestMapping(value = "export",method = {RequestMethod.GET})
//    public void exportOrderList(@RequestParam(name = "startDate") String start, @RequestParam(name = "endDate") String end,
//                                @RequestParam(name = "orderStatus", required=false) String orderStatus,
//                                HttpServletRequest request, HttpServletResponse response){
//
//            response.reset();
//        response.setHeader("Content-disposition","attachment; filename=OrderList-"+ DateUtil.getCurrentyyyyMMdd()+".xls");
//        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//        try {
//            log.debug("[exportOrderList] params:"+start+","+end);
//            PopupOrderList PopupOrderList = new PopupOrderList();
//            PopupOrderList.setWechatId(debug?29:getWechatId(request.getSession()));
////            PopupOrderList.setStartDate(start);
////            PopupOrderList.setEndDate(end);
////            if (StringUtils.isNotBlank(orderStatus))
////                PopupOrderList.setOrderStatus(getEnumByName(orderStatus));
//            List<PopupOrder> data = wechatPopupMApiService.exportOrderList(PopupOrderList);
////            orderStatus=ORDER_PAY_OFF"
//            HSSFWorkbook xwb = new HSSFWorkbook();
//
//            HSSFSheet sheet = xwb.createSheet("订单");
//            HSSFRow row = sheet.createRow(0);
////            String[] tabs = {"日期","订单号","收货人姓名","收货人手机号","订单时间","支付网关","订单状态","商品简述","商品数量","商品价格"};
//            String[] tabs = {"序号", "日期", "订单编号", "姓名", "手机号码", "配送地址", "配送信息发送至另一个手机", "礼品卡", "礼品卡内容", "发票", "发票类型", "发票抬头", "税号", "支付方式"};
//            for(int i = 0; i< tabs.length; i++){
//                HSSFCell cell = row.createCell(i);
//                cell.setCellValue(tabs[i]);
//            }
//
//            HashMap<String,String> hmArea = new HashMap<String,String>();
//            List<PopupCountryArea> liCountryArea = popupPayService.queryCountryArea();
//            for (PopupCountryArea area : liCountryArea) {
//                hmArea.put(area.getCode(),area.getNameZh());
//            }
//
//            if(data.size() > 0){
//                for(int i =0; i < data.size(); i++){
//                    PopupOrder orderDao = data.get(i);
//                    log.debug(orderDao.toString());
////                    JSONObject addressJSON = JSONObject.parseObject(orderDao.getAddress());
////                    JSONObject invoiceJSON = JSONObject.parseObject(orderDao.getInvoiceInfo());
//
////                    Member member = memberService.getMember(wechatId, orderDao.getMemberId());
//                    ArrayList<String> al = new ArrayList<String>();
//                    al.add(i+1+"");
//                    al.add(DateUtil.formatDate(orderDao.getCreateTime(),"yyyy/MM/dd"));
//                    al.add(orderDao.getId().toString());
////                    "area":"310110","city":"310100","province":"310000",
////                    al.add(addressJSON.getString("receiverName"));
////                    String receiverPhone = addressJSON.getString("receiverPhone");
////                    al.add(receiverPhone);
////                    String province = addressJSON.getString("province");
////                    String city = addressJSON.getString("city");
////                    city = (hmArea.containsKey(city)) ? hmArea.get(city) : "";
////                    String area = addressJSON.getString("area");
////                    String desc = addressJSON.getString("desc");
////                    al.add(hmArea.get(province) +" "+ city +" "+ hmArea.get(area) +" "+ desc);
////                    String msmPhone = orderDao.getMsmPhone();
////                    String anotherPhone = msmPhone.equals(receiverPhone) ? "NO" : msmPhone;
////                    al.add(anotherPhone);
////                    String hasGift = orderDao.getGiftContent().equals("") ? "NO" : "YES";
////                    al.add(hasGift);
////                    al.add(orderDao.getGiftContent());
////                    String hasInvoice = orderDao.getInvoiceOpen() == 0 ? "NO" : "YES";
////                    al.add(hasInvoice);
////                    if(hasInvoice.equals("YES")) {
////                        al.add(orderDao.getInvoiceType().getDesc());
////                        al.add(invoiceJSON.getString("title"));
////                        String creditCode = invoiceJSON.getString("creditCode");
////                        if (!creditCode.equals("")) {
////                            creditCode = "creditCode:" + creditCode;
////                        }
////                        String personNo = invoiceJSON.getString("personNo");
////                        if (!personNo.equals("")) {
////                            personNo = "personNo:" + personNo;
////                        }
////                        if (!creditCode.equals("") || !personNo.equals(""))
////                            al.add(creditCode + personNo);
////                        else
////                            al.add("");
////                    } else {
////                        al.add("");
////                        al.add("");
////                        al.add("");
////                    }
////                    PopupOrderGoodsDao orderGoodsDao = popupOrderService.queryOrderGoodsByOrderId(orderDao.getId().intValue());
////                    al.add(orderGoodsDao.getPayType()==null?"":orderGoodsDao.getPayType().getDesc());
////                    HSSFRow rowi = sheet.createRow(i + 1);
////                    for (int k=0; k<al.size(); k++) {
////                        HSSFCell cell = rowi.createCell(k);
////                        cell.setCellValue(al.get(k));
////                    }
//                }
//            }
//
//            OutputStream os = response.getOutputStream();
//            xwb.write(os);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                response.getOutputStream().write(Message.SYSTEM_ERROR.toString().getBytes(Charset.forName("UTF-8")));
//                response.getOutputStream().flush();
//                response.getOutputStream().close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//    }
//    /**
//     * 后台查看订单列表
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "search",method = RequestMethod.POST)
//    public JSONObject queryOrderList(@RequestBody(required = true) PopupOrderList orderListModel, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
//        wechatId = debug?29:getWechatId(request.getSession());
//        if(orderListModel.getPageNum() < 0 || orderListModel.getPageSize() < 0) return representation(Message.ILLEGAL_REQUEST);
//        try{
//            orderListModel.setWechatId(wechatId);
//            log.debug("--------------------");
//            JSONObject jsonObject = wechatPopupMApiService.queryOrderList(orderListModel);
//
//            log.debug("[order][queryOrderList]" + jsonObject.toJSONString());
//
//            JSONObject data = jsonObject.getJSONObject("data");
//            return representation(Message. SUCCESS,data);
//        } catch (Exception e){
//            log.error(e.getMessage());
//            return wrapException(e);
//        }
//    }
//    /**
//     * 查询超卖订单列表
//     *
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(value = "oversold",method = RequestMethod.POST)
//    public JSONObject queryOversoldOrderList(@RequestBody(required = true) PopupOrderList orderListModel, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
//        wechatId = debug?29:getWechatId(request.getSession());
//        try{
//            orderListModel.setWechatId(wechatId);
////            orderListModel.setOrderStatus(OrderEnum.ORDER_PAY_OFF);
//            log.debug("--------------------");
//            JSONObject jsonObject = wechatPopupMApiService.queryOversoldOrderList(orderListModel);
//            log.debug("[order][queryOversoldOrderList]" + jsonObject.toJSONString());
//
//            JSONObject data = jsonObject.getJSONObject("data");
//            return representation(Message.SUCCESS, data);
//        } catch (Exception e){
//            log.error(e.getMessage());
//            return wrapException(e);
//        }
//    }

    @ResponseBody
    @RequestMapping(value = "list")
    @RequiresPermissions("popup-store:order-manage")
    public JSONObject listOrder(@CookieValue(name = "wechatId", required = false) Integer wechatId, HttpServletRequest request) {
        wechatId = debug ? 32 : getWechatId(request.getSession());
        try {
            PopupOrderFilter orderFilter = new PopupOrderFilter();
            orderFilter.setWechatId(wechatId);
            Page<PopupOrderList> data = popupOrderService.selectOrderList(orderFilter);
            return representation(Message.SUCCESS, data, data.getPageSize(), data.getPageNum(), data.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public JSONObject searchOrder(@RequestBody(required = true) PopupOrderFilter orderFilter, @CookieValue(name = "wechatId", required = false) Integer wechatId, HttpServletRequest request) {
        wechatId = debug ? 32 : getWechatId(request.getSession());
        if (orderFilter.getPageNum() < 0 || orderFilter.getPageSize() < 0)
            return representation(Message.ILLEGAL_REQUEST);
        try {
            orderFilter.setWechatId(wechatId);
            Page<PopupOrderList> data = popupOrderService.selectOrderList(orderFilter);
            return representation(Message.SUCCESS, data, data.getPageSize(), data.getPageNum(), data.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "trackno/update", method = RequestMethod.POST)
    @RequiresPermissions("popup-store:order-manage")
    public JSONObject updateTrackNo(@RequestBody(required = true) JSONObject params, @CookieValue(name = "wechatId", required = false) Integer wechatId, HttpServletRequest request) {
        try {
            Long orderId = params.getLong("orderId");
            String trackNo = params.getString("trackNo");

            if (StringUtils.isBlank(trackNo) || StringUtils.isBlank(String.valueOf(orderId)))
                return representation(Message.ILLEGAL_REQUEST);
            popupOrderService.updateTrackNo(trackNo, orderId);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
