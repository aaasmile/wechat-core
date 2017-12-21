
package com.d1m.wechat.service;


import com.d1m.wechat.model.EstoreOrder;
import com.d1m.wechat.pamametermodel.EstoreOrderEntity;
import com.d1m.wechat.pamametermodel.EstoreOrderSearch;
import com.github.pagehelper.Page;

public interface IEstoreOrderService extends IService<EstoreOrder>{

    Page<EstoreOrderEntity> selectOrderList(Integer wechatId, EstoreOrderSearch estoreOrderSearch, boolean queryCount);

    void addEstoreOrder(EstoreOrderEntity orderEntity);

//    void deletePopupGoods(Long id);
//
    EstoreOrderEntity getEstoreOrder(Long orderId, Integer wechatId);

    void updateTrackNo(Long orderId, String trackNo);
}
