package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.EventForwardDetailsDto;
import com.d1m.wechat.dto.EventForwardDto;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EventForwardMapper extends MyMapper<EventForward> {

    Page<EventForwardDto> selectEventForwardItems(Map<String, String> query);



    int updateStatusById(@Param("id") Integer id, @Param("status") InterfaceStatus status, @Param("updatedAt") String  updatedAt);



    EventForwardDetailsDto queryEventForwardDetailsById(Integer id);

    List<String> queryEventForwardByInterfaceId(String id);

    boolean deleteEventForward(Integer id);
}
