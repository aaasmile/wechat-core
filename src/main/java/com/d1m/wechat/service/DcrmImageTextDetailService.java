package com.d1m.wechat.service;

import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.QueryDto;
import com.d1m.wechat.model.Qrcode;
import com.github.pagehelper.PageInfo;

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


    /**
     * 发送图文
     * @param detailDto
     */
     void previewMaterial(DcrmImageTextDetailDto detailDto);

    /**
     * 生成二维码
     * @param dto
     * @return
     */
    Map<String, Object> createQrcode(DcrmImageTextDetailDto dto);

    /**
     * 更新发送数量
     * @param id
     * @return
     */
    int updateSendTimes(Integer id);
}
