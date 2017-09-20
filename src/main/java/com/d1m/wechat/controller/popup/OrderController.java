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
import com.d1m.wechat.model.popup.PopupCountryArea;
import com.d1m.wechat.service.IPopupPayService;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.popup.OrderEnum;
import com.d1m.wechat.model.popup.PayTypeEnum;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.d1m.wechat.model.popup.OrderGoodsDto;
import com.d1m.wechat.model.popup.PopupOrderListModel;
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

    public OrderEnum getEnumByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (OrderEnum order : OrderEnum.values()) {
            if (StringUtils.equals(order.toString(), name)) {
                return order;
            }
        }
        return null;
    }
    /**
     * 导出订单列表数据
     */
    @RequestMapping(value = "export",method = {RequestMethod.GET})
    public void exportOrderList(@RequestParam(name = "startDate") String start, @RequestParam(name = "endDate") String end,
                                @RequestParam(name = "orderStatus", required=false) String orderStatus,
                                HttpServletRequest request, HttpServletResponse response){

            response.reset();
        response.setHeader("Content-disposition","attachment; filename=OrderList-"+ DateUtil.getCurrentyyyyMMdd()+".xls");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            log.debug("[exportOrderList] params:"+start+","+end);
            PopupOrderListModel popupOrderListModel = new PopupOrderListModel();
            popupOrderListModel.setWechatId(debug?29:getWechatId(request.getSession()));
            popupOrderListModel.setStartDate(start);
            popupOrderListModel.setEndDate(end);
            if (StringUtils.isNotBlank(orderStatus))
                popupOrderListModel.setOrderStatus(getEnumByName(orderStatus));
            List<OrderGoodsDto> data = wechatPopupMApiService.exportOrderList(popupOrderListModel);
//            orderStatus=ORDER_PAY_OFF"
            HSSFWorkbook xwb = new HSSFWorkbook();

            HSSFSheet sheet = xwb.createSheet("订单");
            HSSFRow row = sheet.createRow(0);
//            String[] tabs = {"日期","订单号","收货人姓名","收货人手机号","订单时间","支付网关","订单状态","商品简述","商品数量","商品价格"};
            String[] tabs = {"序号", "日期", "订单编号", "姓名", "手机号码", "配送地址", "配送信息发送至另一个手机", "礼品卡", "礼品卡内容", "发票", "发票类型", "发票抬头", "税号", "支付方式"};
            for(int i = 0; i< tabs.length; i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(tabs[i]);
            }

            HashMap<String,String> hmArea = new HashMap<String,String>();
            List<PopupCountryArea> liCountryArea = popupPayService.queryCountryArea();
            for (PopupCountryArea area : liCountryArea) {
                hmArea.put(area.getCode(),area.getNameZh());
            }

            if(data.size() > 0){
                for(int i =0; i < data.size(); i++){
                    OrderGoodsDto orderGoodsDto = data.get(i);
                    log.debug(orderGoodsDto.toString());
//                    Member member = memberService.getMember(wechatId, orderGoodsDto.getMemberId());
                    ArrayList<String> al = new ArrayList<String>();
                    al.add(i+1+"");
                    al.add(DateUtil.formatDate(orderGoodsDto.getCreateTime(),"yyyy/MM/dd"));
                    al.add(orderGoodsDto.getId().toString());
//                    "area":"310110","city":"310100","province":"310000",
                    al.add(orderGoodsDto.getAddress().getString("receiverName"));
                    String receiverPhone = orderGoodsDto.getAddress().getString("receiverPhone");
                    al.add(receiverPhone);
                    String province = orderGoodsDto.getAddress().getString("province");
                    String city = orderGoodsDto.getAddress().getString("city");
                    city = (hmArea.containsKey(city)) ? hmArea.get(city) : "";
                    String area = orderGoodsDto.getAddress().getString("area");
                    String desc = orderGoodsDto.getAddress().getString("desc");
                    al.add(hmArea.get(province) +" "+ city +" "+ hmArea.get(area) +" "+ desc);
                    String msmPhone = orderGoodsDto.getMsmPhone();
                    String anotherPhone = msmPhone.equals(receiverPhone) ? "NO" : msmPhone;
                    al.add(anotherPhone);
                    String hasGift = orderGoodsDto.getGiftContent().equals("") ? "NO" : "YES";
                    al.add(hasGift);
                    al.add(orderGoodsDto.getGiftContent());
                    String hasInvoice = orderGoodsDto.getInvoiceOpen() == 0 ? "NO" : "YES";
                    al.add(hasInvoice);
                    if(hasInvoice.equals("YES")) {
                        al.add(orderGoodsDto.getInvoiceType().getDesc());
                        al.add(orderGoodsDto.getInvoiceInfo().getString("title"));
                        String creditCode = orderGoodsDto.getInvoiceInfo().getString("creditCode");
                        if (!creditCode.equals("")) {
                            creditCode = "creditCode:" + creditCode;
                        }
                        String personNo = orderGoodsDto.getInvoiceInfo().getString("personNo");
                        if (!personNo.equals("")) {
                            personNo = "personNo:" + personNo;
                        }
                        if (!creditCode.equals("") || !personNo.equals(""))
                            al.add(creditCode + personNo);
                        else
                            al.add("");
                    } else {
                        al.add("");
                        al.add("");
                        al.add("");
                    }
                    al.add(orderGoodsDto.getPayType()==null?"":orderGoodsDto.getPayType().getDesc());
                    HSSFRow rowi = sheet.createRow(i + 1);
                    for (int k=0; k<al.size(); k++) {
                        HSSFCell cell = rowi.createCell(k);
                        cell.setCellValue(al.get(k));
                    }
//                    HSSFRow rowi = sheet.createRow(i + 1);
//                    HSSFCell cell = rowi.createCell(0);
//                    cell.setCellValue(i+1);
//                    cell = rowi.createCell(1);
//                    cell.setCellValue(DateUtil.formatDate(orderGoodsDto.getCreateTime(),null));
//                    cell = rowi.createCell(2);
//                    cell.setCellValue(orderGoodsDto.getId());
//                    HSSFCell receiverName = rowi.createCell(1);
//                    receiverName.setCellValue(orderGoodsDto.getAddress().getString("receiverName"));
//                    HSSFCell receiverPhone = rowi.createCell(2);
//                    receiverPhone.setCellValue(orderGoodsDto.getAddress().getString("receiverPhone"));
//                    HSSFCell payType = rowi.createCell(4);
//                    payType.setCellValue(orderGoodsDto.getPayType()==null?"":orderGoodsDto.getPayType().getDesc());
//                    HSSFCell payStatus = rowi.createCell(5);
//                    payStatus.setCellValue(orderGoodsDto.getPayStatus().getDesc());
//                    HSSFCell goodsName = rowi.createCell(6);
//                    goodsName.setCellValue(orderGoodsDto.getGoodsName());
//                    HSSFCell goodsSum = rowi.createCell(7);
//                    goodsSum.setCellValue(orderGoodsDto.getGoodsSum());
//                    HSSFCell goodsPrice = rowi.createCell(8);
//                    goodsPrice.setCellValue(orderGoodsDto.getGoodsPrice());
                }
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

    /**
     * 后台查看订单列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "search",method = RequestMethod.POST)
    public JSONObject queryOrderList(@RequestBody(required = true) PopupOrderListModel orderListModel, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        wechatId = debug?29:getWechatId(request.getSession());
        if(orderListModel.getPageNum() < 0 || orderListModel.getPageSize() < 0) return representation(Message.ILLEGAL_REQUEST);
        try{
            orderListModel.setWechatId(wechatId);
            log.debug("--------------------");
            JSONObject jsonObject = wechatPopupMApiService.queryOrderList(orderListModel);

            log.debug("[order][queryOrderList]" + jsonObject.toJSONString());

            JSONObject data = jsonObject.getJSONObject("data");
            return representation(Message. SUCCESS,data);
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 查询超卖订单列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "oversold",method = RequestMethod.POST)
    public JSONObject queryOversoldOrderList(@RequestBody(required = true) PopupOrderListModel orderListModel, @CookieValue(name="wechatId",required = false) Integer wechatId, HttpServletRequest request){
        wechatId = debug?29:getWechatId(request.getSession());
        try{
            orderListModel.setWechatId(wechatId);
//            orderListModel.setOrderStatus(OrderEnum.ORDER_PAY_OFF);
            log.debug("--------------------");
            JSONObject jsonObject = wechatPopupMApiService.queryOversoldOrderList(orderListModel);
            log.debug("[order][queryOversoldOrderList]" + jsonObject.toJSONString());

            JSONObject data = jsonObject.getJSONObject("data");
            return representation(Message.SUCCESS, data);
        } catch (Exception e){
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

}
