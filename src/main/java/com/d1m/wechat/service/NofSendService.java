package com.d1m.wechat.service;

import com.d1m.wechat.dto.NofSendDto;
import com.d1m.wechat.model.MaterialCategory;


import java.util.List;
import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2018/12/6 16:23
 * @Author: Liu weilin
 * @Description:
 */
public interface NofSendService {

    /**
     * 保存实体
     *
     * @param dto 实体
     * @return 保存条数
     *
     * */

    int save(NofSendDto dto);

    /**
    *
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     * */

    NofSendDto queryObject(String id);

    /**
     * 更新
     * @param
     * @return
     */
    void update(NofSendDto dto);


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
    List<NofSendDto> queryList(Map<String, Object> map);


}
