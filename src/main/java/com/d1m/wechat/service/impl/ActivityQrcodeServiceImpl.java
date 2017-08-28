package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ActivityQrcodeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.ActivityMapper;
import com.d1m.wechat.mapper.ActivityQrcodeMapper;
import com.d1m.wechat.model.ActivityQrcode;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.ActivityQrcodeStatus;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.service.ActivityQrcodeService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ActivityQrcodeServiceImpl extends BaseService<ActivityQrcode>
		implements ActivityQrcodeService {

	@Autowired
	private ActivityMapper activityMapper;

	@Autowired
	private ActivityQrcodeMapper activityQrcodeMapper;

	@Override
	public Mapper<ActivityQrcode> getGenericMapper() {
		return activityQrcodeMapper;
	}

	@Override
	public ActivityQrcode get(Integer wechatId, Integer id) {
		ActivityQrcode record = new ActivityQrcode();
		record.setWechatId(wechatId);
		record.setId(id);
		return activityQrcodeMapper.selectOne(record);
	}

	@Override
	public Page<ActivityQrcodeDto> search(Integer wechatId,
			ActivityModel activityModel, boolean queryCount)
			throws WechatException {
		if (activityModel.pagable()) {
			PageHelper.startPage(activityModel.getPageNum(),
					activityModel.getPageSize(), queryCount);
		}
		return activityQrcodeMapper.search(wechatId, activityModel.getId(),
				activityModel.getSortName(), activityModel.getSortDir());
	}

	@Override
	public void delete(Integer wechatId, User user, Integer id)
			throws WechatException {
		ActivityQrcode activityQrcode = new ActivityQrcode();
		activityQrcode.setId(id);
		activityQrcode.setWechatId(wechatId);
		if (activityQrcodeMapper.selectOne(activityQrcode) == null) {
			throw new WechatException(Message.ACTIVITY_QRCODE_NOT_EXIST);
		}
		activityQrcode.setStatus(ActivityQrcodeStatus.DELETED.getValue());
		activityQrcodeMapper.updateByPrimaryKey(activityQrcode);
	}

}
