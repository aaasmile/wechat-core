package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.ReportQrcodeDto;
import com.d1m.wechat.dto.ReportQrcodeItemDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.github.pagehelper.Page;

public interface QrcodeService extends IService<Qrcode> {

	public Qrcode create(Integer wechatId, User user, QrcodeModel qrcodeModel)
			throws WechatException;

	public Qrcode update(Integer wechatId, Integer id, QrcodeModel qrcodeModel)
			throws WechatException;

	public QrcodeDto get(Integer wechatId, Integer id) throws WechatException;

	public Qrcode getByInuseSceneId(Integer wechatId, String sceneId);

	public Qrcode getBySceneId(Integer wechatId, String sceneId);

	public Qrcode getByTicket(Integer wechatId, String ticket);

	public void delete(Integer wechatId, Integer id) throws WechatException;

	public Page<QrcodeDto> list(Integer wechatId, QrcodeModel qrcodeModel);

	ReportQrcodeDto qrcode(Integer wechatId, Date start, Date end, Integer qrcodeId, String scene, Integer status);

	public List<QrcodeDto> qrcodeList(Integer wechatId, Integer status);

	public void init(Integer wechatId, User user);

	public List<ReportQrcodeItemDto> qrcodeList(Integer wechatId, Date start, Date end, 
			Integer qrcodeId, String scene, Integer status);

	public List<ReportQrcodeItemDto> dateQrcodeList(Integer wechatId, Date start, Date end, 
			Integer qrcodeId, String scene, Integer status);

}
