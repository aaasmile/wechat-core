package com.d1m.wechat.service;

import com.d1m.wechat.exception.WithVersionException;
import com.d1m.wechat.util.MyMapper;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BaseService<T> {

    MyMapper<T> getMapper();

    default T selectByKey(Object key) {
        return getMapper().selectByPrimaryKey(key);
    }

    @Transactional(rollbackFor = Exception.class)
    default int insert(T entity) {
        return getMapper().insertSelective(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    default int insertFull(T entity) {
        return getMapper().insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    default int insertList(List<T> collection) {
        return getMapper().insertList(collection);
    }

    @Transactional(rollbackFor = Exception.class)
    default int updateByPrimaryKeySelective(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    default int updateAll(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    default T selectOne(T entity) {
        return getMapper().selectOne(entity);
    }

    default List<T> select(T entity) {
        return getMapper().select(entity);
    }

    default List<T> selectAll() {
        return getMapper().selectAll();
    }

    default List<T> selectByExample(Example example) {
        return getMapper().selectByExample(example);
    }

    default int selectCountByExample(Example example) {
        return getMapper().selectCountByExample(example);
    }


    @Transactional(rollbackFor = Exception.class)
    default int updateByExampleSelective(T record, Object example) {
        return getMapper().updateByExampleSelective(record, example);
    }

    @Transactional(rollbackFor = Exception.class)
    default int updateWithVersion(T t) {

        int result = updateByPrimaryKeySelective(t);
        if (result == 0) {
            throw new WithVersionException("更新失败!");
        }
        return result;
    }

}
