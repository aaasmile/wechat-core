package com.d1m.wechat.service;

import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ImageModel;
import com.d1m.wechat.pamametermodel.ImageTextModel;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.github.pagehelper.Page;

public interface MaterialService extends IService<Material> {

	Material createMaterialImage(Integer wechatId, User user, Upload upload)
			throws WechatException;

	Material createImage(Integer wechatId, User user, Upload upload)
			throws WechatException;

	Material createMaterialVideo(Integer wechatId, User user, Upload upload)
			throws WechatException;

	Material getMaterial(Integer wechatId, Integer id);

	MaterialDto getImageText(Integer wechatId, Integer id);

	Material createImageText(Integer wechatId, User user,
			MaterialModel materialModel) throws WechatException;

	Material updateImageText(Integer wechatId, User user, Integer id,
			MaterialModel materialModel) throws WechatException;

	@Deprecated
	MaterialImageTextDetail updateImageTextDetail(Integer wechatId, User user,
			Integer imageTextDetailId,
			MaterialImageTextDetail materialImageTextDetail, boolean pushToWx)
			throws WechatException;

	void deleteImage(Integer wechatId, User user, Integer id)
			throws WechatException;

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

}
