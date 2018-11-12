package com.d1m.wechat.mapper;


import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.dto.MemberStatsCounts;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface MemberTagDataMapper extends MyMapper<MemberTagData> {

    CopyOnWriteArrayList<MemberTagData> getMembertagCsvData(Integer fileId);

    MemberStatsCounts getCount (Integer fileId);

    int updateDataStatus(@Param("fileId") Integer fileId, @Param("status") Integer status);
}