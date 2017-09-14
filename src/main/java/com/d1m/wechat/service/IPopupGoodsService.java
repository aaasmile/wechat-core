
package com.d1m.wechat.service;


import com.d1m.wechat.model.popup.PopupGoodsEntity;
import com.d1m.wechat.model.popup.PopupGoodsFilter;
import com.d1m.wechat.model.popup.PopupGoodsList;
import com.github.pagehelper.Page;

import java.util.List;

public interface
IPopupGoodsService {


    Page<PopupGoodsList> selectGoodsList(PopupGoodsFilter goodsFilter);

    void updatePopupGoods(PopupGoodsEntity goodsEntity, String action);

    void deletePopupGoods(Long id);

    PopupGoodsEntity getPopupGoods(Long id);
}
