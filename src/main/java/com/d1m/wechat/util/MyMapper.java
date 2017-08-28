package com.d1m.wechat.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
 *
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
