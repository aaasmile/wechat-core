package com.d1m.wechat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MemberScanQrcodeMapper;
import com.d1m.wechat.model.MemberScanQrcode;
import com.d1m.wechat.service.MemberScanQrcodeService;

@Service
public class MemberScanQrcodeServiceImpl extends BaseService<MemberScanQrcode>
		implements MemberScanQrcodeService {

	private static Logger log = LoggerFactory
			.getLogger(MemberScanQrcodeServiceImpl.class);

	@Autowired
	private MemberScanQrcodeMapper memberScanQrcodeMapper;

	@Override
	public Mapper<MemberScanQrcode> getGenericMapper() {
		return memberScanQrcodeMapper;
	}

}
