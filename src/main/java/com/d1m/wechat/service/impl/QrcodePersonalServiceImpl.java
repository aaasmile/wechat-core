package com.d1m.wechat.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.QrcodePersonalMapper;
import com.d1m.wechat.model.QrcodePersonal;
import com.d1m.wechat.model.enums.QrcodeStatus;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.QrcodePersonalService;
import com.d1m.wechat.wechatclient.WechatClientDelegate;

import cn.d1m.wechat.client.model.WxQRCode;
import tk.mybatis.mapper.common.Mapper;

@Service
public class QrcodePersonalServiceImpl extends BaseService<QrcodePersonal> implements QrcodePersonalService {
	
	private static final Logger log = LoggerFactory.getLogger(QrcodePersonalServiceImpl.class);

	@Autowired
	private QrcodePersonalMapper qrcodeMapper;

	private final Integer EXPIRE_SECONDS = 2592000;
	private final String DEFAULT_NAME = "邀请二维码";
	private final String DEFAULT_SUMMARY = "用户邀请二维码(有效期30天)";
	private final String DEFAULT_SCENE_STR = "MemberId_";


	public QrcodePersonal create(Integer wechatId, Integer memberId, String sceneStr)
			throws WechatException {
		if (StringUtils.isBlank(sceneStr)){
			return null;
		}
		QrcodeModel qrcodeModel = new QrcodeModel();
		qrcodeModel.setName(memberId + DEFAULT_NAME);
		qrcodeModel.setSummary(DEFAULT_SUMMARY);
		qrcodeModel.setScene(DEFAULT_SCENE_STR + memberId);
		return create(wechatId, memberId, EXPIRE_SECONDS, qrcodeModel);
	}

	private boolean isExpire(Date createAt,int expireSeconds){
		if ((new Date().getTime() - createAt.getTime())/1000 > expireSeconds) {
			return true;
		}
		return false;
	}

	public QrcodePersonal create(Integer wechatId, Integer memberId,
								 int expireSeconds,QrcodeModel qrcodeModel) throws WechatException {
		QrcodePersonal qrcodeByScene = null;
		try {
			if (expireSeconds > EXPIRE_SECONDS) {
                expireSeconds = EXPIRE_SECONDS;
            }

			qrcodeByScene = qrcodeMapper.getQrcodeByScene(wechatId, qrcodeModel.getScene());

			if (qrcodeByScene == null || isExpire(qrcodeByScene.getCreatedAt(), expireSeconds)) {

                String scenestr = DEFAULT_SCENE_STR + memberId;
                if (StringUtils.isNotBlank(qrcodeModel.getScene())) {
                    scenestr = qrcodeModel.getScene();
                }

                WxQRCode wxQrcode = WechatClientDelegate.createQRCode(wechatId,expireSeconds, scenestr);
                QrcodePersonal qrcode = new QrcodePersonal();
                qrcode.setWechatId(wechatId);
                qrcode.setName(qrcodeModel.getName());
                qrcode.setTicket(wxQrcode.getTicket());
                qrcode.setQrcodeUrl(wxQrcode.getUrl());
                qrcode.setCreatedAt(new Date());
                qrcode.setCreatorId(memberId);
                qrcode.setStatus(QrcodeStatus.INUSED.getValue());
                qrcode.setScene(qrcodeModel.getScene());
                qrcode.setSummary(qrcodeModel.getSummary());
                qrcode.setScene(scenestr);
                qrcode.setExpireSeconds(expireSeconds);
                if (qrcodeByScene != null && qrcodeByScene.getId() != null) {
                	qrcode.setId(qrcodeByScene.getId());
                    qrcodeMapper.updateByPrimaryKey(qrcode);
                } else {
                    qrcodeMapper.insert(qrcode);
                }
                return qrcode;
            }
		} catch (Exception e) {
			log.error("Create Personal QrCode exception, " +
					"wechatId:{}, memberId: {}, scene: {}, expireSeconds: {}",
					wechatId, memberId, qrcodeModel.getScene(), expireSeconds);
			return qrcodeByScene;
		}
		return qrcodeByScene;
	}

	public QrcodePersonal getBySceneId(Integer wechatId, String sceneId) {
		QrcodePersonal qrcode = new QrcodePersonal();
		qrcode.setWechatId(wechatId);
		qrcode.setScene(sceneId);
		qrcode.setStatus(QrcodeStatus.INUSED.getValue());
		qrcode = qrcodeMapper.selectOne(qrcode);

		return qrcode;
	}

	@Override
	public Mapper<QrcodePersonal> getGenericMapper() {
		return qrcodeMapper;
	}
}
