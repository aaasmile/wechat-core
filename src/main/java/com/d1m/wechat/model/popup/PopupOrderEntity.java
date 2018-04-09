package com.d1m.wechat.model.popup;

import com.d1m.wechat.model.popup.dao.*;

import java.util.List;

/**
 * Created by Jovi gu on 2017/9/6.
 */
public class PopupOrderEntity {

    private PopupOrder orderBase;

    private List<PopupOrderGoodsRel> liOrderGoods;

    private PopupOrderExpress orderExpress;

    private PopupOrderDeliveryAddr orderDeliveryAddr;

    private PopupOrderExtraAttr orderExtraAttr;

    private PopupOrderInvoice orderInvoice;

	public PopupOrder getOrderBase() {
		return orderBase;
	}

	public void setOrderBase(PopupOrder orderBase) {
		this.orderBase = orderBase;
	}

	public List<PopupOrderGoodsRel> getLiOrderGoods() {
		return liOrderGoods;
	}

	public void setLiOrderGoods(List<PopupOrderGoodsRel> liOrderGoods) {
		this.liOrderGoods = liOrderGoods;
	}

	public PopupOrderExpress getOrderExpress() {
		return orderExpress;
	}

	public void setOrderExpress(PopupOrderExpress orderExpress) {
		this.orderExpress = orderExpress;
	}

	public PopupOrderDeliveryAddr getOrderDeliveryAddr() {
		return orderDeliveryAddr;
	}

	public void setOrderDeliveryAddr(PopupOrderDeliveryAddr orderDeliveryAddr) {
		this.orderDeliveryAddr = orderDeliveryAddr;
	}

	public PopupOrderExtraAttr getOrderExtraAttr() {
		return orderExtraAttr;
	}

	public void setOrderExtraAttr(PopupOrderExtraAttr orderExtraAttr) {
		this.orderExtraAttr = orderExtraAttr;
	}

	public PopupOrderInvoice getOrderInvoice() {
		return orderInvoice;
	}

	public void setOrderInvoice(PopupOrderInvoice orderInvoice) {
		this.orderInvoice = orderInvoice;
	}
}
