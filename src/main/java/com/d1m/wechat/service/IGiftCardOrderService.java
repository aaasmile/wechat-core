package com.d1m.wechat.service;

import com.d1m.wechat.dto.GiftCardOrderDto;
import com.d1m.wechat.model.WxGiftcardOrder;
import com.d1m.wechat.pamametermodel.GiftCardOrderSearch;
import com.github.pagehelper.Page;

public interface IGiftCardOrderService extends IService<WxGiftcardOrder>{

    Page<GiftCardOrderDto> selectOrderList(Integer wechatId, GiftCardOrderSearch giftCardOrderSearch, boolean queryCount);
}
