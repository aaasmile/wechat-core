package com.d1m.wechat.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import cn.d1m.wechat.client.core.WechatClient;
import cn.d1m.wechat.client.model.WxQRCode;
import cn.d1m.wechat.client.model.common.WxFile;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.ReportQrcodeDto;
import com.d1m.wechat.dto.ReportQrcodeItemDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.QrcodeMapper;
import com.d1m.wechat.mapper.QrcodeTypeMapper;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.QrcodeAction;
import com.d1m.wechat.model.enums.QrcodeStatus;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.QrcodeService;
import com.d1m.wechat.util.*;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Slf4j
@Service
public class QrcodeServiceImpl extends BaseService<Qrcode> implements
		QrcodeService {

	@Resource
	private QrcodeMapper qrcodeMapper;

	@Resource
	private QrcodeTypeMapper qrcodeTypeMapper;

	private void checkQrcodeTypeExist(Integer wechatId, Integer qrcodeTypeId)
			throws WechatException {
		if (qrcodeTypeId == null) {
			return;
		}
		QrcodeType record = new QrcodeType();
		record.setId(qrcodeTypeId);
		record.setWechatId(wechatId);
		record = qrcodeTypeMapper.selectOne(record);
		notBlank(record, Message.QRCODE_QRCODE_TYPE_NOT_EXIST);
	}

	@Override
	public Qrcode create(Integer wechatId, User user, QrcodeModel qrcodeModel)
			throws WechatException {
		if (qrcodeModel == null) {
			qrcodeModel = new QrcodeModel();
		}
		notBlank(qrcodeModel.getName(), Message.QRCODE_NAME_NOT_BLANK);
		notBlank(qrcodeModel.getScene(), Message.QRCODE_SCENE_NOT_BLANK);

		checkQrcodeTypeExist(wechatId, qrcodeModel.getQrcodeTypeId());
		Integer count = qrcodeMapper.countDuplicateName(wechatId, null,
				qrcodeModel.getName(), QrcodeStatus.INUSED.getValue());
		if (count > 0) {
			throw new WechatException(Message.QRCODE_NAME_EXIST);
		}

		String sceneStr = qrcodeModel.getScene();
		checkSceneNameRepeate(sceneStr);

		// String sceneStr = Rand.getRandom(64);

		QrcodeAction qrLimitStrScene = QrcodeAction.QR_LIMIT_STR_SCENE;
		WxQRCode wxQrcode = WechatClientDelegate.createQRCode(wechatId, sceneStr);

		Qrcode qrcode = new Qrcode();
		qrcode.setWechatId(wechatId);
		qrcode.setQrcodeTypeId(qrcodeModel.getQrcodeTypeId());
		qrcode.setName(qrcodeModel.getName());
		qrcode.setTicket(wxQrcode.getTicket());
		qrcode.setQrcodeUrl(wxQrcode.getUrl());
		qrcode.setCreatedAt(new Date());
		qrcode.setCreatorId(user.getId());
		qrcode.setStatus(QrcodeStatus.INUSED.getValue());
		qrcode.setActionName(qrLimitStrScene.getValue());
		qrcode.setScene(sceneStr);
		qrcode.setSummary(qrcodeModel.getSummary());
		qrcode.setSopportSubscribeReply(qrcodeModel.getSopportSubscribeReply());
		String format = DateUtil.yyyyMMddHHmmss.format(new Date());
		String type = Constants.IMAGE + File.separator + Constants.QRCODE;
		File root = FileUtils.getUploadPathRoot(wechatId, type);
		File dir = new File(root, format.substring(0, 6));
        WxFile wxFile = WechatClientDelegate.showQRCode(wechatId, qrcode.getTicket());
        if (!wxFile.moveFileTo(dir)) {
            log.error("文件移动失败! ticket = {}",qrcode.getTicket());
        }
        qrcode.setQrcodeImgUrl(FileUploadConfigUtil.getInstance().getValue(wechatId, "upload_url_base")
				+ File.separator
				+ type
				+ File.separator
				+ dir.getName()
				+ File.separator + wxFile.getFilename());

		qrcode.setScene(qrcodeModel.getScene());
		qrcodeMapper.insert(qrcode);
		return qrcode;
	}

	public void checkSceneNameRepeate(String scene) {
		Qrcode qrcode = new Qrcode();
		qrcode.setScene(scene);
		qrcode = qrcodeMapper.selectOne(qrcode);
		if (qrcode != null) {
			throw new WechatException(Message.QRCODE_SCENE_NOT_REPEAT);
		}

	}

	@Override
	public QrcodeDto get(Integer wechatId, Integer id) throws WechatException {
		notBlank(id, Message.QRCODE_ID_NOT_BLANK);
		QrcodeDto qrcode = qrcodeMapper.get(wechatId, id);
		notBlank(qrcode, Message.QRCODE_NOT_EXIST);
		return qrcode;
	}

	@Override
	public Qrcode getByInuseSceneId(Integer wechatId, String sceneId) {
		Qrcode qrcode = new Qrcode();
		qrcode.setWechatId(wechatId);
		qrcode.setScene(sceneId);
		qrcode.setStatus(QrcodeStatus.INUSED.getValue());
		qrcode = qrcodeMapper.selectOne(qrcode);

		return qrcode;
	}

	@Override
	public Qrcode getBySceneId(Integer wechatId, String sceneId) {
		Qrcode qrcode = new Qrcode();
		qrcode.setWechatId(wechatId);
		qrcode.setScene(sceneId);
		qrcode.setStatus(QrcodeStatus.INUSED.getValue());
		qrcode = qrcodeMapper.selectOne(qrcode);

		return qrcode;
	}

	@Override
	public Qrcode getByTicket(Integer wechatId, String ticket) {
		Qrcode qrcode = new Qrcode();
		qrcode.setWechatId(wechatId);
		qrcode.setTicket(ticket);
		qrcode.setStatus(QrcodeStatus.INUSED.getValue());
		qrcode = qrcodeMapper.selectOne(qrcode);
		notBlank(qrcode, Message.QRCODE_NOT_EXIST);
		return qrcode;
	}

	@Override
	public void delete(Integer wechatId, Integer id) throws WechatException {
		notBlank(id, Message.QRCODE_ID_NOT_BLANK);
		Qrcode qrcode = getQrcode(wechatId, id);
		notBlank(qrcode, Message.QRCODE_NOT_EXIST);
		if (qrcode.getStatus() == QrcodeStatus.DELETED.getValue()) {
			throw new WechatException(Message.QRCODE_NOT_EXIST);
		}
		qrcode.setStatus(QrcodeStatus.DELETED.getValue());
		qrcodeMapper.updateByPrimaryKey(qrcode);
	}

	@Override
	public Mapper<Qrcode> getGenericMapper() {
		return qrcodeMapper;
	}

	@Override
	public Page<QrcodeDto> list(Integer wechatId, QrcodeModel qrcodeModel) {
		PageHelper.startPage(qrcodeModel.getPageNum(),
				qrcodeModel.getPageSize(), true);
		return qrcodeMapper.list(wechatId, qrcodeModel.getQuery(),
				qrcodeModel.getQrcodeTypeId(), QrcodeStatus.INUSED.getValue(),
				qrcodeModel.getSortName(), qrcodeModel.getSortDir());
	}

	public void setQrcodeMapper(QrcodeMapper qrcodeMapper) {
		this.qrcodeMapper = qrcodeMapper;
	}

	public void setQrcodeTypeMapper(QrcodeTypeMapper qrcodeTypeMapper) {
		this.qrcodeTypeMapper = qrcodeTypeMapper;
	}

	@Override
	public Qrcode update(Integer wechatId, Integer id, QrcodeModel qrcodeModel)
			throws WechatException {
		notBlank(id, Message.QRCODE_ID_NOT_BLANK);
		Qrcode record = getQrcode(wechatId, id);
		if (record.getStatus() == QrcodeStatus.DELETED.getValue()) {
			throw new WechatException(Message.QRCODE_NOT_EXIST);
		}
		Integer qrcodeTypeId = qrcodeModel.getQrcodeTypeId();
		checkQrcodeTypeExist(wechatId, qrcodeTypeId);
		String name = qrcodeModel.getName();
		Integer count = qrcodeMapper.countDuplicateName(wechatId, id, name,
				QrcodeStatus.INUSED.getValue());
		if (count > 0) {
			throw new WechatException(Message.QRCODE_NAME_EXIST);
		}
		record.setName(name);
		record.setSummary(qrcodeModel.getSummary());
		record.setSopportSubscribeReply(qrcodeModel.getSopportSubscribeReply());
		if (qrcodeTypeId != null) {
			record.setQrcodeTypeId(qrcodeTypeId);
		}
		qrcodeMapper.updateByPrimaryKey(record);
		return record;
	}

	private Qrcode getQrcode(Integer wechatId, Integer id)
			throws WechatException {
		Qrcode record = new Qrcode();
		record.setWechatId(wechatId);
		record.setId(id);
		record = qrcodeMapper.selectOne(record);
		notBlank(record, Message.QRCODE_NOT_EXIST);
		return record;
	}

	private List<ReportQrcodeItemDto> findDates(Date begin, Date end) {
		List<ReportQrcodeItemDto> list = new ArrayList<ReportQrcodeItemDto>();
		ReportQrcodeItemDto dto = new ReportQrcodeItemDto();
		dto.setDate(DateUtil.formatYYYYMMDD(begin));
		list.add(dto);

		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(begin);
		calBegin.set(Calendar.HOUR_OF_DAY, 0);
		calBegin.set(Calendar.MINUTE, 0);
		calBegin.set(Calendar.SECOND, 0);
		calBegin.set(Calendar.MILLISECOND, 0);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		while (calEnd.after(calBegin)) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			dto = new ReportQrcodeItemDto();
			dto.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
			list.add(dto);
		}
		return list;
	}

	@Override
	public ReportQrcodeDto qrcode(Integer wechatId, Date start, Date end,
			Integer qrcodeId, String scene, Integer status) {
		// TODO Auto-generated method stub
		notBlank(wechatId, null);
		notBlank(start, null);
		notBlank(end, null);

		ReportQrcodeDto dto = new ReportQrcodeDto();
		int currentTotal = qrcodeMapper.qrcodeCurrentTotalCount(wechatId,
				start, end, status, qrcodeId, scene);
		dto.setCurrentTotal(currentTotal);

		List<ReportQrcodeItemDto> trendList = findDates(start, end);
		List<ReportQrcodeItemDto> list = qrcodeMapper.qrcodeReport(wechatId,
				start, end, status, qrcodeId, scene);
		List<ReportQrcodeItemDto> qrcodeList = qrcodeMapper.qrcodeNameList(wechatId,
				start, end, status, qrcodeId, scene);

		for (ReportQrcodeItemDto ddto : trendList) {
			for (ReportQrcodeItemDto temp : list) {
				if (ddto.getDate().equals(temp.getDate())) {
					ddto.setScanNum(ddto.getScanNum() + temp.getScanNum());
					ddto.setAttentionByScan(ddto.getAttentionByScan()
							+ temp.getAttentionByScan());
					ddto.setUserByScan(ddto.getUserByScan() + temp.getUserByScan());
				}
			}
		}

		for(ReportQrcodeItemDto ddto : qrcodeList){
			for(ReportQrcodeItemDto temp : list){
				if (ddto.getQrCodeName().equals(temp.getQrCodeName())) {
					ddto.setScanNum(ddto.getScanNum() + temp.getScanNum());
					ddto.setAttentionByScan(ddto.getAttentionByScan()
							+ temp.getAttentionByScan());
					ddto.setUserByScan(ddto.getUserByScan() + temp.getUserByScan());
				}
			}
		}
		if (trendList.size() > 0) {
			for (ReportQrcodeItemDto item : trendList) {
				dto.setScanNumTotal(dto.getScanNumTotal()
						+ item.getScanNum());
				dto.setAttentionByScanTotal(dto.getAttentionByScanTotal()
						+ item.getAttentionByScan());
				dto.setUserByScanTotal(dto.getUserByScanTotal()
						+ item.getUserByScan());
			}
		}
		if (qrcodeList.size() > 0) {
			dto.setQrcodeList(qrcodeList);
		}
		dto.setTrendList(trendList);
		dto.setList(list);
		return dto;
	}


	@Override
	public List<QrcodeDto> qrcodeList(Integer wechatId, Integer status) {

		return qrcodeMapper.qrcodeList(wechatId, status);
	}

	@Override
	public synchronized void init(Integer wechatId, User user) {

        WechatClient wechatClient = WechatClientDelegate.get(wechatId);
        List<Qrcode> list = qrcodeMapper.getLackWxQrcode();
		for (Qrcode qrcode : list){
			if(StringUtils.isBlank(qrcode.getTicket())){
				WxQRCode wxQrcode = wechatClient.createQRCode(qrcode.getScene());
				qrcode.setTicket(wxQrcode.getTicket());
				qrcode.setQrcodeUrl(wxQrcode.getUrl());
			}
			String format = DateUtil.yyyyMMddHHmmss.format(new Date());
			String type = Constants.IMAGE + File.separator + Constants.QRCODE;
			File root = FileUtils.getUploadPathRoot(wechatId, type);
			File dir = new File(root, format.substring(0, 6));
            WxFile wxFile = wechatClient.showQRCode(qrcode.getTicket());
            if (!wxFile.moveFileTo(dir)) {
                log.error("文件移动失败! ticket = {}",qrcode.getTicket());
            }
			qrcode.setQrcodeImgUrl(FileUploadConfigUtil.getInstance().getValue(
					wechatId,
					"upload_url_base")
					+ File.separator
					+ type
					+ File.separator
					+ dir.getName()
					+ File.separator + wxFile.getFilename());

			qrcodeMapper.updateByPrimaryKeySelective(qrcode);
		}

	}

	@Override
	public List<ReportQrcodeItemDto> qrcodeList(Integer wechatId, Date start,
			Date end, Integer qrcodeId, String scene, Integer status) {
		List<ReportQrcodeItemDto> list = qrcodeMapper.qrcodeReport(wechatId,
				start, end, status, qrcodeId, scene);
		List<ReportQrcodeItemDto> qrcodeList = qrcodeMapper.qrcodeNameList(wechatId,
				start, end, status, qrcodeId, scene);
		for(ReportQrcodeItemDto ddto : qrcodeList){
			for(ReportQrcodeItemDto temp : list){
				if (ddto.getQrCodeName().equals(temp.getQrCodeName())) {
					ddto.setScanNum(ddto.getScanNum() + temp.getScanNum());
					ddto.setAttentionByScan(ddto.getAttentionByScan()
							+ temp.getAttentionByScan());
					ddto.setUserByScan(ddto.getUserByScan() + temp.getUserByScan());
				}
			}
		}
		return qrcodeList;
	}

	@Override
	public List<ReportQrcodeItemDto> dateQrcodeList(Integer wechatId,
			Date start, Date end, Integer qrcodeId, String scene, Integer status) {
		return qrcodeMapper.qrcodeReport(wechatId, start, end, status, qrcodeId, scene);
	}

}
