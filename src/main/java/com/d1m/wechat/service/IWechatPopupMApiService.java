package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.configure.FeignClientConfiguration;
import com.d1m.wechat.model.popup.OrderGoodsDto;
import com.d1m.wechat.model.popup.PopupOrderListModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Owen Jia on 2017/7/17.
 */
@FeignClient(name = "wechat-popup",configuration = FeignClientConfiguration.class)
public interface IWechatPopupMApiService {

    @RequestMapping(value = "mapi/order/export", method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    List<OrderGoodsDto> exportOrderList(PopupOrderListModel popupOrderListModel);

    @RequestMapping(value = "mapi/order/search", method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    JSONObject queryOrderList(PopupOrderListModel popupOrderListModel);

    @RequestMapping(value = "mapi/order/oversold", method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    JSONObject queryOversoldOrderList(PopupOrderListModel popupOrderListModel);
}
