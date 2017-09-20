
package com.d1m.wechat.service;


import com.d1m.wechat.model.popup.PopupOrderFilter;
import com.d1m.wechat.model.popup.PopupOrderList;
import com.d1m.wechat.model.popup.dao.PopupOrderGoodsRel;
import com.github.pagehelper.Page;

public interface
IPopupOrderService {

    Page<PopupOrderList> selectOrderList(PopupOrderFilter orderFilter);

    PopupOrderGoodsRel queryOrderGoodsByOrderId(Long orderId);

    void updateTrackNo(String trackNo, Long orderId);
}
