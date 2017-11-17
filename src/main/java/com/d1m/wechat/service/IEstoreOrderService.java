
package com.d1m.wechat.service;


import com.d1m.wechat.pamametermodel.EstoreOrderEntity;
import com.d1m.wechat.pamametermodel.EstoreOrderSearch;

import java.util.List;

public interface IEstoreOrderService {


    List<EstoreOrderEntity> selectOrderList(EstoreOrderSearch estoreOrderSearch);

    void addEstoreOrder(EstoreOrderEntity orderEntity);

//    void deletePopupGoods(Long id);
//
    EstoreOrderEntity getEstoreOrder(Long orderId, Long wechatId);
}
