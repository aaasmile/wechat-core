
package com.d1m.wechat.service;


import com.d1m.wechat.model.popup.PopupGoodsEntity;
import com.d1m.wechat.model.popup.PopupGoodsFilter;
import com.d1m.wechat.model.popup.PopupGoodsList;
import com.d1m.wechat.pamametermodel.EstoreProductEntity;
import com.d1m.wechat.pamametermodel.EstoreProductSearch;
import com.github.pagehelper.Page;

import java.util.List;

public interface
IEstoreProductService {


    List<EstoreProductEntity> selectProductList(EstoreProductSearch estoreProductSearch);

    void updateEstoreProduct(EstoreProductEntity productEntity, String action);

//    void deletePopupGoods(Long id);
//
    EstoreProductEntity getEstoreProduct(Long pid, Long wechatId);
}
