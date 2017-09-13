
package com.d1m.wechat.service;


import com.d1m.wechat.model.popup.PopupOrderList;
import com.d1m.wechat.model.popup.dao.PopupOrderGoodsRel;
import com.github.pagehelper.Page;

public interface
IPopupOrderService {

    Page<PopupOrderList> selectOrderList(PopupOrderList popupOrderList);

    PopupOrderGoodsRel queryOrderGoodsByOrderId(Long orderId);
}
