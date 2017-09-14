package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.PopupGoodsFilter;
import com.d1m.wechat.model.popup.PopupGoodsList;
import com.d1m.wechat.model.popup.dao.PopupGoods;
import com.d1m.wechat.util.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PopupGoodsMapper extends MyMapper<PopupGoods> {

    List<PopupGoodsList> selectPopupGoodsList(PopupGoodsFilter goodsFilter);

    void updatePopupGoods(PopupGoods popupGoods, String action);
}