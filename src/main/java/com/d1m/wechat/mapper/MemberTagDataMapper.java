package com.d1m.wechat.mapper;


import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.dto.MemberStatsCounts;
import com.d1m.wechat.util.MyMapper;

public interface MemberTagDataMapper extends MyMapper<MemberTagData> {

//    @Override
//    int updateByPrimaryKeySelective(MemberTagData memberTagData);

    CopyOnWriteArrayList<MemberTagData> getMembertagCsvData(@Param("fileId") Integer fileId, @Param("pageNum") int pageNum, @Param("batchSize") int batchSize);

    MemberStatsCounts getCount (Integer fileId);

    int updateDataStatus(@Param("fileId") Integer fileId, @Param("status") Integer status);
}