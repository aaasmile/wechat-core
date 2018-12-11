package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.dto.MaterialCategoryDto;
import com.d1m.wechat.model.MaterialCategory;
import com.github.pagehelper.PageInfo;


import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:23
 * @Author: Liu weilin
 * @Description:
 */
public interface MaterialCategoryService {

    /**
     * 保存实体
     *
     * @param dto 实体
     * @return 保存条数
     *
     * */

    int save(MaterialCategoryDto dto);

    /**
    *
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     * */

    MaterialCategoryDto queryObject(String id);

    /**
     * 更新
     * @param MaterialCategoryDto
     * @return
     */
    int update(MaterialCategoryDto MaterialCategoryDto);

    /**
     * 查询素材名称是否存在
     * @param name
     * @return
     */
    MaterialCategory exitsName(String name);

    /**
     * 删除素材分类
     * @param id
     */
    JSONObject delete(Integer wechatId, String id);

    /**
     * 分页查询
     *
     * @param dto
     * @return list
     */
    PageInfo<MaterialCategory> queryList(MaterialCategoryDto dto);


}
