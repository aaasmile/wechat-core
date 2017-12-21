package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.GiftCardOrderDto;
import com.d1m.wechat.mapper.WxGiftcardOrderMapper;
import com.d1m.wechat.model.WxGiftcardOrder;
import com.d1m.wechat.pamametermodel.GiftCardOrderSearch;
import com.d1m.wechat.service.IGiftCardOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Stoney.Liu on 2017/12/19.
 */
@Service
public class GiftCardOrderServiceImpl extends BaseService<WxGiftcardOrder> implements IGiftCardOrderService {
    @Autowired
    WxGiftcardOrderMapper wxGiftcardOrderMapper;

    @Override
    public Page<GiftCardOrderDto> selectOrderList(Integer wechatId, GiftCardOrderSearch giftCardOrderSearch, boolean queryCount) {
        if(giftCardOrderSearch.pagable()){
            PageHelper.startPage(giftCardOrderSearch.getPageNum(), giftCardOrderSearch.getPageSize(), queryCount);
        }
        return wxGiftcardOrderMapper.search(wechatId, giftCardOrderSearch);
    }

    @Override
    public Mapper<WxGiftcardOrder> getGenericMapper() {
        return wxGiftcardOrderMapper;
    }
}
