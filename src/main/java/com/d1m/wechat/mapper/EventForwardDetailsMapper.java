package com.d1m.wechat.mapper;

import com.d1m.wechat.model.EventForwardDetails;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

public interface EventForwardDetailsMapper extends MyMapper<EventForwardDetails> {

    List<Integer> getByEventForwardId(Integer eventForwardId);

}
