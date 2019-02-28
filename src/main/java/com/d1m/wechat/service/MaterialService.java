package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ImageModel;
import com.d1m.wechat.pamametermodel.ImageTextModel;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.pamametermodel.MiniProgramModel;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;

public interface MaterialService extends IService<Material> {

    Material createMaterialImage(Integer wechatId, User user, Upload upload)
            throws WechatException;

    Material createImage(Integer wechatId, User user, Upload upload)
            throws WechatException;

    Material createMaterialVideo(Integer wechatId, User user, Upload upload)
            throws WechatException;

    Material getMaterial(Integer wechatId, Integer id);

    MaterialDto getImageText(Integer wechatId, Integer id, Boolean isSub);

    Material createImageText(Integer wechatId, User user,
                             MaterialModel materialModel) throws WechatException;

    Material updateImageText(Integer wechatId, User user, Integer id,
                             MaterialModel materialModel) throws WechatException;

    void updateMaterialAndImageText(Material material, List<MaterialImageTextDetail> imageTextDetail);

    @Deprecated
    MaterialImageTextDetail updateImageTextDetail(Integer wechatId, User user,
                                                  Integer imageTextDetailId,
                                                  MaterialImageTextDetail materialImageTextDetail, boolean pushToWx)
            throws WechatException;

    /**
     * 删除图片
     *
     * @param wechatId
     * @param materialId
     * @return
     * @throws WechatException
     */
    JSONObject deleteImage(Integer wechatId, Integer materialId);

    void deleteImageText(Integer wechatId, User user, Integer id)
            throws WechatException;

    @Deprecated
    void deleteImageTextDetail(Integer wechatId, User user, Integer id)
            throws WechatException;

    void renameImage(Integer wechatId, User user, Integer id, String name)
            throws WechatException;

    Page<MaterialDto> searchImageText(Integer wechatId,
                                      ImageTextModel imageTextModel, boolean queryCount);

    Page<MaterialDto> searchImage(Integer wechatId, ImageModel imageModel,
                                  boolean queryCount);

    Page<MaterialDto> searchVoice(Integer wechatId, Integer materialType,
                                  String sortName, String sortDir, Integer pageNum, Integer pageSize,
                                  boolean queryCount);

    Page<MaterialDto> searchVideo(Integer wechatId, Integer materialType,
                                  String sortName, String sortDir, Integer pageNum, Integer pageSize,
                                  boolean queryCount);

    Page<MaterialDto> searchLittleVideo(Integer wechatId, Integer materialType,
                                        String sortName, String sortDir, Integer pageNum, Integer pageSize,
                                        boolean queryCount);

    void pushMaterialImageTextToWx(Integer wechatId, User user,
                                   Integer materialId);

    void checkMaterialInvalidInWeiXin(Integer wechatId, String mediaId);

    void previewMaterial(Integer wechatId, MaterialModel materialModel);

    Material createMediaImage(Integer wechatId, User user, Upload upload);

    Material createOutletImage(Integer wechatId, User user, Upload upload);

    //Material createOfflineImage(Integer wechatId, User user, Upload upload);

    Material createMiniProgram(Integer userId, Integer wechatId, MaterialModel materialModel);

    int updateMiniProgram(Integer userId, Integer wechatId, MaterialModel materialModel);

    int deleteMiniProgram(Integer userId, Integer wechatId, Integer miniProgramId);

    Page<MiniProgramDto> searchMiniProgram(MiniProgramModel miniProgramModel, boolean queryCount);

    MiniProgramDto getMiniProgramByMaterialId(Integer wechatId, Integer materialId);

    List<MaterialImageTextDetail> getMaterialImageTextDetails(
            Integer wechatId, Integer materialId,
            List<ImageTextModel> imageTexts, Date current)
            throws WechatException;


    /**
     * 根据sn获取微信图文详情
     *
     * @param sn
     * @return
     */
    MaterialDto getInfoBySn(String sn);

    /**
     * 检查素材是否存在
     * @param id
     * @return
     */
    Material checkMaterialExist(Integer id);
}
