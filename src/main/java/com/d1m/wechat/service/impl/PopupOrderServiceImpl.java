
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.PopupCountryAreaMapper;
import com.d1m.wechat.mapper.PopupOrderExpressMapper;
import com.d1m.wechat.mapper.PopupOrderGoodsRelMapper;
import com.d1m.wechat.mapper.PopupOrderMapper;
import com.d1m.wechat.model.popup.PopupOrderFilter;
import com.d1m.wechat.model.popup.PopupOrderList;
import com.d1m.wechat.model.popup.dao.PopupOrder;
import com.d1m.wechat.model.popup.dao.PopupOrderExpress;
import com.d1m.wechat.model.popup.dao.PopupOrderGoodsRel;
import com.d1m.wechat.service.IPopupOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PopupOrderServiceImpl implements IPopupOrderService {
    private static final Logger log = LoggerFactory.getLogger(PopupOrderServiceImpl.class);
    @Autowired
    PopupOrderMapper popupOrderMapper;
    @Autowired
    PopupOrderGoodsRelMapper orderGoodsRelMapper;
    @Autowired
    PopupOrderExpressMapper orderExpressMapper;
    @Autowired
    PopupCountryAreaMapper countryAreaMapper;

    @Override
    public Page<PopupOrderList> selectOrderList(PopupOrderFilter orderFilter) {
        List<HashMap<String,String>> listCodesMap = countryAreaMapper.selectAllAreaMap(null);
        HashMap<Integer,String> addrMap = new HashMap<>(listCodesMap.size());
        Integer areaCode = 0;
        String areaName = "";
        for (HashMap<String,String> map : listCodesMap) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey().equals("code"))
                    areaCode = Integer.parseInt(entry.getValue());
                else if (entry.getKey().equals("name_zh"))
                    areaName = entry.getValue();
            }
            addrMap.put(areaCode, areaName);
        }
        PageHelper.startPage(orderFilter.getPageNum(),orderFilter.getPageSize(),true);
        List<PopupOrderList> listOrderList = popupOrderMapper.selectPopupOrderList(orderFilter);
        int i = 0;
        for (PopupOrderList orderList: listOrderList ) {
            listOrderList.get(i).setProvinceName(addrMap.get(orderList.getProvince()));
            listOrderList.get(i).setCityName(addrMap.get(orderList.getCity()));
            listOrderList.get(i).setAreaName(addrMap.get(orderList.getArea()));
            i++;
        }
        return (Page<PopupOrderList>) listOrderList;
    }

    @Override
    public PopupOrderGoodsRel queryOrderGoodsByOrderId(Long orderId) {
        return orderGoodsRelMapper.selectByOrderId(orderId);
    }

    @Override
    public void updateTrackNo(String trackNo, Long orderId){
        PopupOrderExpress orderExpress = new PopupOrderExpress();
        orderExpress.setOrderId(orderId);
        PopupOrderExpress orderExpressNew = orderExpressMapper.selectPopupExpressByOrderId(orderId);
        if (orderExpressNew != null) {
            orderExpressMapper.updateTrackNo(trackNo, orderId);
        } else {
            orderExpress.setStatus((byte)1);
            orderExpress.setTrackNo(trackNo);
            orderExpressMapper.insertSelective(orderExpress);
        }
        PopupOrder orderBase = new PopupOrder();
        orderBase.setPayStatus((byte)4);
        orderBase.setId(orderId);
        popupOrderMapper.updateByPrimaryKeySelective(orderBase);
    }

}
