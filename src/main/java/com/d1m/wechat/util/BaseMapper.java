package com.d1m.wechat.util;

import java.util.List;
import java.util.Map;

/**
 * 基础Mapper
 * @param <T>
 */
public interface BaseMapper<T> {

    int save(T t);

    void save(Map<String, Object> map);

    void saveBatch(List<T> list);

    int update(T t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    T queryObject(Object id);

    List<T> queryList(Map<String, Object> map);

    List<T> queryList(Object id);

    int queryTotal(Map<String, Object> map);

    int queryTotal();
}
