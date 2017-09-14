package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.dao.PopupGoodsSku;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PopupGoodsSkuMapper extends MyMapper<PopupGoodsSku> {

    List<PopupGoodsSku> selectPopupGoodsSkuByGoodsId(@Param("goodsId") Long goodsId);

    void deletePopupGoodsSkuByGoodsId(@Param("goodsId") Long goodsId);
}