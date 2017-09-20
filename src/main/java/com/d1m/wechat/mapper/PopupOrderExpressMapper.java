package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.dao.PopupOrderExpress;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface PopupOrderExpressMapper extends MyMapper<PopupOrderExpress> {

    void updateTrackNo(@Param("trackNo") String trackNo,@Param("orderId") Long orderId);

    PopupOrderExpress selectPopupExpressByOrderId(@Param("orderId") Long orderId);
}