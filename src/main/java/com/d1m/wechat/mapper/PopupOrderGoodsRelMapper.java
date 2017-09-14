package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.dao.PopupOrderGoodsRel;
import com.d1m.wechat.util.MyMapper;
import org.springframework.stereotype.Component;

@Component
public interface PopupOrderGoodsRelMapper extends MyMapper<PopupOrderGoodsRel> {
    PopupOrderGoodsRel selectByOrderId(Long orderId);
}