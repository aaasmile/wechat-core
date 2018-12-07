package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.MaterialCategoryMapper;
import com.d1m.wechat.model.MaterialCategory;
import com.d1m.wechat.pamametermodel.MaterialCategoryEntity;
import com.d1m.wechat.service.MaterialCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:26
 * @Author: Liu weilin
 * @Description:
 */
@Service
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;

    @Override
    public int save(MaterialCategoryEntity materialCategoryEntity) {
        materialCategoryEntity.setCreatedAt(new Date());
        return materialCategoryMapper.insert(materialCategoryEntity);
    }

    @Override
    public MaterialCategoryEntity queryObject(String id) {
        MaterialCategoryEntity materialCategoryEntity = new MaterialCategoryEntity();
        MaterialCategory mc = materialCategoryMapper.queryObject(id);
        BeanUtils.copyProperties(mc, materialCategoryEntity);
        return materialCategoryEntity;
    }


    public int update(MaterialCategoryEntity materialCategoryEntity) {
        return materialCategoryMapper.update(materialCategoryEntity);
    }

    /**
     * 查询素材名称是否存在
     * @param name
     * @return
     */
    public MaterialCategory exitsName(String name){
        MaterialCategory materialCategory = new MaterialCategory();
        materialCategory.setName(name);
         return materialCategoryMapper.selectOne(materialCategory);
    }

    /**
     * 删除素材分类
     * @param id
     */
    public void delete(String id){

        materialCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<MaterialCategory> queryList(Map<String, Object> map) {
        return materialCategoryMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return materialCategoryMapper.queryTotal(map);
    }

}
