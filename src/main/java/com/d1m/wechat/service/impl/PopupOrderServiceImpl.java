
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.PopupOrderExpressMapper;
import com.d1m.wechat.mapper.PopupOrderGoodsRelMapper;
import com.d1m.wechat.mapper.PopupOrderMapper;
import com.d1m.wechat.model.popup.PopupOrderFilter;
import com.d1m.wechat.model.popup.PopupOrderList;
import com.d1m.wechat.model.popup.dao.PopupOrderGoodsRel;
import com.d1m.wechat.service.IPopupOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PopupOrderServiceImpl implements IPopupOrderService {
    private static final Logger log = LoggerFactory.getLogger(PopupOrderServiceImpl.class);
    @Autowired
    PopupOrderMapper popupOrderMapper;
    @Autowired
    PopupOrderGoodsRelMapper orderGoodsRelMapper;
    @Autowired
    PopupOrderExpressMapper orderExpressMapper;

    @Override
    public Page<PopupOrderList> selectOrderList(PopupOrderFilter orderFilter) {
        PageHelper.startPage(orderFilter.getPageNum(),orderFilter.getPageSize(),true);
        return (Page<PopupOrderList>) popupOrderMapper.selectPopupOrderList(orderFilter);
    }

    @Override
    public PopupOrderGoodsRel queryOrderGoodsByOrderId(Long orderId) {
        return orderGoodsRelMapper.selectByOrderId(orderId);
    }

    @Override
    public void updateTrackNo(String trackNo, String orderId){
        orderExpressMapper.updateTrackNo(trackNo, orderId);
    }
}
