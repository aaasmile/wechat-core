package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.model.DcrmImageTextDetail;
import com.d1m.wechat.util.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 15:57
 * @Author: Liu weilin
 * @Description:
 */
public interface DcrmImageTextDetailMapper extends MyMapper<DcrmImageTextDetail> {

    List<DcrmImageTextDetailDto> queryList(Map<String, Object> map);
}
