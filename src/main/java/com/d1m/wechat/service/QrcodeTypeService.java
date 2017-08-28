package com.d1m.wechat.service;

import com.d1m.wechat.dto.QrcodeTypeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.QrcodeTypeModel;
import com.github.pagehelper.Page;

public interface QrcodeTypeService extends IService<QrcodeType> {

	public QrcodeType create(Integer wechatId, User user,
			QrcodeTypeModel qrcodeTypeModel) throws WechatException;

	public QrcodeType update(Integer wechatId, QrcodeTypeModel qrcodeTypeModel)
			throws WechatException;

	public void delete(Integer wechatId, Integer id) throws WechatException;

	public Page<QrcodeTypeDto> list(Integer wechatId,
			QrcodeTypeModel qrcodeTypeModel);

}
