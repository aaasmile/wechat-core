package com.d1m.wechat.domain.dao.impl;

import com.d1m.wechat.domain.dao.MemberTagDataDao;
import com.d1m.wechat.domain.entity.MemberTagData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

/**
 * Created by jone.wang on 2018/11/17.
 * Description: 批量更新
 */
@Repository
@Slf4j
public class MemberTagDataDaoImpl implements MemberTagDataDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long updateBatch(Collection<MemberTagData> records) {
        if (CollectionUtils.isEmpty(records)) {
            log.warn("Collection is empty");
            return 0;
        }
        //批量模式
        final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        final IntSummaryStatistics collect = records
                .stream()
                .map(r -> sqlSession
                        .update("com.d1m.wechat.mapper.MemberTagDataMapper.updateByPrimaryKeySelective", r))
                .collect(Collectors.summarizingInt(value -> value));
        sqlSession.commit();
        return collect.getSum();

    }
}
