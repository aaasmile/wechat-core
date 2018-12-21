package com.d1m.wechat.mapper;

import com.d1m.wechat.model.MaterialCategory;

import org.apache.ibatis.annotations.Param;
import com.d1m.wechat.util.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 15:57
 * @Author: Liu weilin
 * @Description:
 */
public interface MaterialCategoryMapper extends MyMapper<MaterialCategory> {

    int add(MaterialCategory t);

    MaterialCategory queryObject(String id);

    List<MaterialCategory> queryList(Map<String, Object> map);


    int update(MaterialCategory t);

    int del(@Param("id") String id);

}
