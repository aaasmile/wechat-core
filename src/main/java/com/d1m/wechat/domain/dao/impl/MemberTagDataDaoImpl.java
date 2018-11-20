package com.d1m.wechat.domain.dao.impl;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.d1m.wechat.domain.dao.MemberTagDataDao;
import com.d1m.wechat.domain.entity.MemberTagData;

/**
 * Created by jone.wang on 2018/11/17.
 * Description: 批量更新
 */
@Repository
public class MemberTagDataDaoImpl implements MemberTagDataDao {

	private static final Logger log = LoggerFactory.getLogger(MemberTagDataDaoImpl.class);
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(Collection<MemberTagData> records) {
        if (CollectionUtils.isEmpty(records)) {
            log.warn("Collection is empty");
            return 0;
        }
        //批量模式
        final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        final int sum = records
                .stream()
                .map(r -> sqlSession
                        .update("com.d1m.wechat.mapper.MemberTagDataMapper.updateByPrimaryKeySelective", r))
                .mapToInt(Integer::valueOf)
                .sum();
        sqlSession.commit();
        return sum;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(Collection<MemberTagData> records,String method) {
        if (CollectionUtils.isEmpty(records)) {
            log.warn("Collection is empty");
            return 0;
        }
        //批量模式
        final SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        final int sum = records
         .stream()
         .map(r -> sqlSession
          .update(method, r))
         .mapToInt(Integer::valueOf)
         .sum();
        sqlSession.commit();
        return sum;

    }

}
