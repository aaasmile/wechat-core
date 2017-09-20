package com.d1m.wechat.model.popup;

import com.d1m.wechat.model.popup.dao.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
public class PopupOrderEntity {

    private PopupOrder orderBase;

    private List<PopupOrderGoodsRel> liOrderGoods;

    private PopupOrderExpress orderExpress;

    private PopupOrderDeliveryAddr orderDeliveryAddr;

    private PopupOrderExtraAttr orderExtraAttr;

    private PopupOrderInvoice orderInvoice;
}
