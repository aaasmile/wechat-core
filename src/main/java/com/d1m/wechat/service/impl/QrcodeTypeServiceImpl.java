package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.QrcodeTypeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.QrcodeTypeMapper;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.QrcodeTypeStatus;
import com.d1m.wechat.pamametermodel.QrcodeTypeModel;
import com.d1m.wechat.service.QrcodeTypeService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class QrcodeTypeServiceImpl extends BaseService<QrcodeType> implements
		QrcodeTypeService {

	@Autowired
	private QrcodeTypeMapper qrcodeTypeMapper;

	public void setQrcodeTypeMapper(QrcodeTypeMapper qrcodeTypeMapper) {
		this.qrcodeTypeMapper = qrcodeTypeMapper;
	}

	@Override
	public Mapper<QrcodeType> getGenericMapper() {
		return qrcodeTypeMapper;
	}

	@Override
	public QrcodeType create(Integer wechatId, User user,
			QrcodeTypeModel qrcodeTypeModel) throws WechatException {
		notBlank(qrcodeTypeModel.getName(), Message.QRCODE_TYPE_NAME_NOT_BLANK);
		Integer count = qrcodeTypeMapper.countDuplicateName(wechatId, null,
				qrcodeTypeModel.getName(), qrcodeTypeModel.getParentId(),
				QrcodeTypeStatus.INUSED.getValue());
		if (count > 0) {
			throw new WechatException(Message.QRCODE_TYPE_NAME_EXIST);
		}
		checkParentExist(wechatId, qrcodeTypeModel.getParentId());
		QrcodeType qrcodeType = new QrcodeType();
		qrcodeType.setName(qrcodeTypeModel.getName());
		qrcodeType.setParentId(qrcodeTypeModel.getParentId());
		qrcodeType.setStatus(QrcodeTypeStatus.INUSED.getValue());
		qrcodeType.setCreatedAt(new Date());
		qrcodeType.setCreatorId(user.getId());
		qrcodeType.setWechatId(wechatId);
		qrcodeTypeMapper.insert(qrcodeType);
		return qrcodeType;
	}

	private void checkParentExist(Integer wechatId, Integer parentId)
			throws WechatException {
		if (parentId == null) {
			return;
		}
		QrcodeType record = new QrcodeType();
		record.setWechatId(wechatId);
		record.setId(parentId);
		QrcodeType parent = qrcodeTypeMapper.selectOne(record);
		notBlank(parent, Message.QRCODE_TYPE_PARENT_NOT_EXIST);
	}

	@Override
	public QrcodeType update(Integer wechatId, QrcodeTypeModel qrcodeTypeModel)
			throws WechatException {
		notBlank(qrcodeTypeModel.getId(), Message.QRCODE_TYPE_ID_NOT_BLANK);
		notBlank(qrcodeTypeModel.getName(), Message.QRCODE_TYPE_NAME_NOT_BLANK);

		QrcodeType record = new QrcodeType();
		record.setId(qrcodeTypeModel.getId());
		record.setWechatId(wechatId);
		record = qrcodeTypeMapper.selectOne(record);
		notBlank(record, Message.QRCODE_TYPE_NOT_EXIST);

		if (record.getStatus() == QrcodeTypeStatus.DELETED.getValue()) {
			throw new WechatException(Message.QRCODE_TYPE_NOT_EXIST);
		}
		Integer count = qrcodeTypeMapper.countDuplicateName(wechatId,
				qrcodeTypeModel.getId(), qrcodeTypeModel.getName(),
				record.getParentId(), QrcodeTypeStatus.INUSED.getValue());
		if (count > 0) {
			throw new WechatException(Message.QRCODE_TYPE_NAME_EXIST);
		}

		record.setName(qrcodeTypeModel.getName());
		qrcodeTypeMapper.updateByPrimaryKey(record);
		return record;
	}

	@Override
	public void delete(Integer wechatId, Integer id) throws WechatException {
		notBlank(id, Message.QRCODE_ID_NOT_BLANK);
		QrcodeType record = new QrcodeType();
		record.setWechatId(wechatId);
		record.setId(id);
		record = qrcodeTypeMapper.selectOne(record);
		notBlank(record, Message.QRCODE_NOT_EXIST);
		if (record.getStatus() == QrcodeTypeStatus.DELETED.getValue()) {
			throw new WechatException(Message.QRCODE_TYPE_NOT_EXIST);
		}
		record.setStatus(QrcodeTypeStatus.DELETED.getValue());
		qrcodeTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public Page<QrcodeTypeDto> list(Integer wechatId,
			QrcodeTypeModel qrcodeTypeModel) {
		if (qrcodeTypeModel == null) {
			qrcodeTypeModel = new QrcodeTypeModel();
		}
		PageHelper.startPage(qrcodeTypeModel.getPageNum(),
				qrcodeTypeModel.getPageSize(), true);
		return qrcodeTypeMapper.list(wechatId, qrcodeTypeModel.getName(),
				qrcodeTypeModel.getParentId(), qrcodeTypeModel.getSortName(),
				qrcodeTypeModel.getSortDir());
	}
}
