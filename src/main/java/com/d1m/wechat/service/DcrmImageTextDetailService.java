package com.d1m.wechat.service;

import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.model.Material;
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
public interface DcrmImageTextDetailService {

    /**
     * 保存实体
     *
     * @param dto 实体
     * @return 保存条数
     *
     * */

    int save(DcrmImageTextDetailDto dto);

    /**
    *
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     * */

    DcrmImageTextDetailDto queryObject(Integer id);

    /**
     * 更新
     * @param
     * @return
     */
    int update(DcrmImageTextDetailDto dto);



    /**
     * 分页查询
     *
     * @param dto 参数
     * @return list
     */
    PageInfo<DcrmImageTextDetailDto> queryList(QueryDto dto);


}
