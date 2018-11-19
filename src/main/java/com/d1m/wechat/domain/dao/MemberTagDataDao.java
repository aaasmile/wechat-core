package com.d1m.wechat.domain.dao;

import com.d1m.wechat.domain.entity.MemberTagData;

import java.util.Collection;

/**
 * Created by jone.wang on 2018/11/17.
 * Description:
 */
public interface MemberTagDataDao {

    int updateBatch(Collection<MemberTagData> records);

    int updateBatch(Collection<MemberTagData> records,String method);

}
