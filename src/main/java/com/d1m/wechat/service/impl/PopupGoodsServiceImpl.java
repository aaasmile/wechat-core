
package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.PopupGoodsMapper;
import com.d1m.wechat.mapper.PopupGoodsSkuMapper;
import com.d1m.wechat.model.popup.PopupGoodsEntity;
import com.d1m.wechat.model.popup.PopupGoodsFilter;
import com.d1m.wechat.model.popup.PopupGoodsList;
import com.d1m.wechat.model.popup.dao.PopupGoods;
import com.d1m.wechat.model.popup.dao.PopupGoodsSku;
import com.d1m.wechat.service.IPopupGoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PopupGoodsServiceImpl implements IPopupGoodsService {
    private static final Logger log = LoggerFactory.getLogger(PopupGoodsServiceImpl.class);
    @Autowired
    PopupGoodsMapper popupGoodsMapper;
    @Autowired
    private PopupGoodsSkuMapper popupGoodsSkuMapper;


    @Override
    public Page<PopupGoodsList> selectGoodsList(PopupGoodsFilter goodsFilter) {
        PageHelper.startPage(goodsFilter.getPageNum(),goodsFilter.getPageSize(),true);
        return (Page<PopupGoodsList>) popupGoodsMapper.selectPopupGoodsList(goodsFilter);
    }

    @Override
    public void updatePopupGoods(PopupGoodsEntity goodsEntity, String action) {
        PopupGoods popupGoodsDao = goodsEntity.getGoods();
        popupGoodsDao.setUpdateTime(new Date());
        if (action.equals("add")) {
            popupGoodsDao.setCreateTime(new Date());
            popupGoodsMapper.insert(popupGoodsDao);
        } else {
            popupGoodsMapper.updateByPrimaryKey(popupGoodsDao);
        }
        List<PopupGoodsSku> list = goodsEntity.getLiGoodsSku();
        for (PopupGoodsSku popupGoodsSku: list) {
            if (action.equals("add")) {
                popupGoodsSku.setGoodsId(popupGoodsDao.getId());
                popupGoodsSkuMapper.insert(popupGoodsSku);
            } else {
                popupGoodsSkuMapper.updateByPrimaryKey(popupGoodsSku);
            }
        }
    }

    @Override
    public void deletePopupGoods(Long id) {
        popupGoodsMapper.deleteByPrimaryKey(id);
        popupGoodsSkuMapper.deletePopupGoodsSkuByGoodsId(id);
    }

    @Override
    public PopupGoodsEntity getPopupGoods(Long id) {
        PopupGoodsEntity goodsEntity = new PopupGoodsEntity();
        PopupGoods goods = popupGoodsMapper.selectByPrimaryKey(id);
        List<PopupGoodsSku> list = popupGoodsSkuMapper.selectPopupGoodsSkuByGoodsId(id);
        goodsEntity.setGoods(goods);
        goodsEntity.setLiGoodsSku(list);
        return goodsEntity;
    }
}
