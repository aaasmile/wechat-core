package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.GiftCardOrderDto;
import com.d1m.wechat.model.WxGiftcardOrder;
import com.d1m.wechat.pamametermodel.GiftCardOrderSearch;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface WxGiftcardOrderMapper extends MyMapper<WxGiftcardOrder> {
    Page<GiftCardOrderDto> search(@Param("wechatId") Integer wechatId,@Param("searchParam") GiftCardOrderSearch giftCardOrderSearch);
}