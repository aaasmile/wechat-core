package com.d1m.wechat.mapper;

import com.d1m.wechat.model.EstoreProduct;
import com.d1m.wechat.model.EstoreProductListResult;
import com.d1m.wechat.pamametermodel.EstoreProductSearch;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

public interface EstoreProductMapper extends MyMapper<EstoreProduct> {
    List<EstoreProductListResult> selectProductList(EstoreProductSearch estoreProductSearch);
}