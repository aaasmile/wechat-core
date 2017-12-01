
package com.d1m.wechat.service;


import com.d1m.wechat.pamametermodel.EstoreOrderEntity;
import com.d1m.wechat.pamametermodel.EstoreOrderSearch;
import com.github.pagehelper.Page;

import java.util.List;

public interface IEstoreOrderService {


    Page<EstoreOrderEntity> selectOrderList(EstoreOrderSearch estoreOrderSearch);

    void addEstoreOrder(EstoreOrderEntity orderEntity);

//    void deletePopupGoods(Long id);
//
    EstoreOrderEntity getEstoreOrder(Long orderId, Long wechatId);

    void updateTrackNo(Long orderId, String trackNo);
}
