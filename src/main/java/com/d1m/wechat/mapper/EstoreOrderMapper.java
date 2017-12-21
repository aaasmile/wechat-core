package com.d1m.wechat.mapper;

import com.d1m.wechat.model.EstoreOrder;
import com.d1m.wechat.model.EstoreOrderListResult;
import com.d1m.wechat.pamametermodel.EstoreOrderSearch;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface EstoreOrderMapper extends MyMapper<EstoreOrder> {
    Page<EstoreOrderListResult> selectOrderList(@Param("wechatId") Integer wechatId, @Param("searchParam") EstoreOrderSearch estoreOrderSearch);
}