package com.d1m.wechat.service;

import com.d1m.wechat.model.MaterialCategory;
import com.d1m.wechat.pamametermodel.MaterialCategoryEntity;

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
     * @param materialCategoryEntity 实体
     * @return 保存条数
     *
     * */

    int save(MaterialCategoryEntity materialCategoryEntity);

    /**
    *
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     * */

    MaterialCategoryEntity queryObject(String id);

    /**
     * 更新
     * @param materialCategoryEntity
     * @return
     */
    int update(MaterialCategoryEntity materialCategoryEntity);

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
    void delete(String id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<MaterialCategory> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);

}
