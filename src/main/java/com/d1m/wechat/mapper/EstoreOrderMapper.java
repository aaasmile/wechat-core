package com.d1m.wechat.mapper;

import com.d1m.wechat.model.EstoreOrder;
import com.d1m.wechat.model.EstoreOrderListResult;
import com.d1m.wechat.pamametermodel.EstoreOrderSearch;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

public interface EstoreOrderMapper extends MyMapper<EstoreOrder> {
    List<EstoreOrderListResult> selectOrderList(EstoreOrderSearch estoreOrderSearch);
}