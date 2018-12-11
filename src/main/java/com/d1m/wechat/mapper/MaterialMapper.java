package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.NofSendDto;
import com.d1m.wechat.model.MaterialCategory;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.pamametermodel.MiniProgramModel;
import com.d1m.wechat.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface MaterialMapper extends MyMapper<Material> {

    MaterialDto getImageText(@Param("wechatId") Integer wechatId,
                             @Param("id") Integer id);

    Page<MaterialDto> searchImage(@Param("wechatId") Integer wechatId,
                                  @Param("materialImageTypeId") Integer materialImageTypeId,
                                  @Param("query") String query, @Param("pushed") Boolean pushed,
                                  @Param("materialType") Integer materialType);

    Page<MaterialDto> searchImageText(@Param("wechatId") Integer wechatId,
                                      @Param("query") String query, @Param("pushed") Boolean pushed);

    Page<MiniProgramDto> searchMiniProgram(MiniProgramModel miniProgramModel);

    MiniProgramDto getMiniProgramByMaterialId(@Param("wechatId") Integer wechatId, @Param("materialId") Integer materialId);

	List<NofSendDto> queryList(Map<String, Object> map);

    /**
     * 获取使用的图文信息
     *
     * @param wechatId
     * @param id
     * @return
     */
    MaterialDto getUsedImageText(@Param("wechatId") Integer wechatId, @Param("id") Integer id);

    int update(Material material);

    List<String> selectExistName(@Param("wechatId") Integer wechatId,@Param("id") String id);


}