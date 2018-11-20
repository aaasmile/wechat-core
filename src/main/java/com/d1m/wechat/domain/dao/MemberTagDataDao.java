package com.d1m.wechat.domain.dao;

import java.util.Collection;
import java.util.List;

import com.d1m.wechat.domain.entity.MemberTagData;

/**
 * Created by jone.wang on 2018/11/17.
 * Description:
 */
public interface MemberTagDataDao {

    int updateBatch(Collection<MemberTagData> records);

    int updateBatch(Collection<MemberTagData> records,String method);
    /**
	 * 批量插入
	 * @param records
	 */
	public void batchInsert(List<MemberTagData> memberTagDataList);
}
