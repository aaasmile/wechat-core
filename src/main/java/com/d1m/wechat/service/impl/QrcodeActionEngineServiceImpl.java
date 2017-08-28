package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.dto.QrcodeActionEngineDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.ActionEngineMapper;
import com.d1m.wechat.mapper.QrcodeActionEngineMapper;
import com.d1m.wechat.mapper.QrcodeMapper;
import com.d1m.wechat.model.ActionEngine;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.model.QrcodeActionEngine;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.ActionEngineStatus;
import com.d1m.wechat.model.enums.Language;
import com.d1m.wechat.model.enums.Sex;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.pamametermodel.ActionEngineEffect;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.QrcodeActionEngineService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class QrcodeActionEngineServiceImpl extends
		BaseService<QrcodeActionEngine> implements QrcodeActionEngineService {

	@Autowired
	private QrcodeMapper qrcodeMapper;

	@Autowired
	private ActionEngineMapper actionEngineMapper;

	@Autowired
	private QrcodeActionEngineMapper qrcodeActionEngineMapper;

	public void setQrcodeMapper(QrcodeMapper qrcodeMapper) {
		this.qrcodeMapper = qrcodeMapper;
	}

	public void setActionEngineMapper(ActionEngineMapper actionEngineMapper) {
		this.actionEngineMapper = actionEngineMapper;
	}

	public void setQrcodeActionEngineMapper(
			QrcodeActionEngineMapper qrcodeActionEngineMapper) {
		this.qrcodeActionEngineMapper = qrcodeActionEngineMapper;
	}

	@Override
	public Mapper<QrcodeActionEngine> getGenericMapper() {
		return qrcodeActionEngineMapper;
	}

	@Override
	public QrcodeActionEngine create(Integer wechatId, User user,
			QrcodeModel qrcodeModel) {
		notBlank(qrcodeModel.getQrcodeId(), Message.QRCODE_ID_NOT_BLANK);
		notBlank(qrcodeModel.getRules(), Message.QRCODE_ACTION_ENGINE_NOT_BLANK);
		Qrcode qrcode = getQrcode(wechatId, qrcodeModel.getQrcodeId());
		notBlank(qrcode, Message.QRCODE_NOT_EXIST);

		Date current = new Date();
		ActionEngine actionEngine = getRule(wechatId, user,
				qrcodeModel.getRules(), current);
		actionEngineMapper.insert(actionEngine);
		QrcodeActionEngine qae = new QrcodeActionEngine();
		qae.setActionEngineId(actionEngine.getId());
		qae.setCreatedAt(current);
		qae.setCreatorId(user.getId());
		qae.setQrcodeId(qrcodeModel.getQrcodeId());
		qae.setWechatId(wechatId);
		qrcodeActionEngineMapper.insert(qae);
		return qae;
	}

	private Qrcode getQrcode(Integer wechatId, Integer qrcodeId) {
		Qrcode qrcode = new Qrcode();
		qrcode.setWechatId(wechatId);
		qrcode.setId(qrcodeId);
		qrcode = qrcodeMapper.selectOne(qrcode);
		return qrcode;
	}

	public static ActionEngine getRule(Integer wechatId, User user,
			ActionEngineModel actionEngineModel, Date current) {
		notBlank(actionEngineModel.getName(),
				Message.ACTION_ENGINE_NAME_NOT_BLANK);

		ActionEngine actionEngine = new ActionEngine();
		actionEngine.setId(actionEngineModel.getId());
		ActionEngineCondition condition = actionEngineModel.getCondition();
		if (condition != null) {
			actionEngine.setConditions(JSONObject.toJSONString(condition));
			StringBuilder conditionSql = new StringBuilder();
			if (condition.getDateOfBirthStart() != null) {
				Date dateOfBirthStart = DateUtil
						.parse(DateUtil.utc2DefaultLocal(
								condition.getDateOfBirthStart(), true));
				if (dateOfBirthStart == null) {
					throw new WechatException(null);
				}
				conditionSql
						.append(" and birthday &gt;= ")
						.append("'")
						.append(DateUtil.formatYYYYMMDDHHMMSS(dateOfBirthStart))
						.append("'");
			}
			if (condition.getDateOfBirthEnd() != null) {
				Date dateOfBirthEnd = DateUtil.parse(DateUtil
						.utc2DefaultLocal(condition.getDateOfBirthEnd()));
				if (dateOfBirthEnd == null) {
					throw new WechatException(null);
				}
				conditionSql.append(" and birthday &lt;= ").append("'")
						.append(DateUtil.formatYYYYMMDDHHMMSS(dateOfBirthEnd))
						.append("'");
			}
			if (condition.getHasMobile() != null) {
				if (condition.getHasMobile() == 0) {
					conditionSql.append(" and mobile is null");
				} else {
					conditionSql.append(" and mobile is not null");
				}
			}
			if (condition.getLanguage() != null
					&& condition.getLanguage().length > 0) {
				Language la = null;
				StringBuilder las = new StringBuilder("(");
				int laSize = condition.getLanguage().length;
				for (int i = 0; i < laSize; i++) {
					la = Language.getByValue(condition.getLanguage()[i]);
					if (la == null) {
						throw new WechatException(null);
					}
					las.append(la.getValue());
					if (i < laSize - 1) {
						las.append(",");
					}
				}
				las.append(")");
				if (las.length() > 2) {
					conditionSql.append(" and language in").append(las);
				}
			}
			if (condition.getMobileStatus() != null) {
				// TODO
			}
			if (condition.getGender() != null
					&& condition.getGender().length > 0) {
				Sex sex = null;
				StringBuilder sexStr = new StringBuilder("(");
				int sexSize = condition.getGender().length;
				for (int i = 0; i < sexSize; i++) {
					sex = Sex.getByValue(condition.getGender()[i].byteValue());
					if (sex == null) {
						throw new WechatException(null);
					}
					sexStr.append(sex.getValue());
					if (i < sexSize - 1) {
						sexStr.append(",");
					}
				}
				sexStr.append(")");
				if (sexStr.length() > 2) {
					conditionSql.append(" and sex in").append(sexStr);
				}
			}
			if (condition.getMemberTagIds() != null
					&& condition.getMemberTagIds().length > 0) {
				// TODO member tag
			}
			if (conditionSql.length() > 0) {
				actionEngine.setConditionSql(conditionSql.substring(5));
			}
		}
		actionEngine.setCreatedAt(current);
		actionEngine.setCreatorId(user.getId());
		List<ActionEngineEffect> effect = actionEngineModel.getEffect();
		if (effect != null && !effect.isEmpty()) {
			for (ActionEngineEffect qrcodeActionEngineEffect : effect) {
				Integer[] values = qrcodeActionEngineEffect.getValue();
				for (Integer value : values) {
					if (value == null) {
						throw new WechatException(null);
					}
				}
			}
			actionEngine.setEffect(JSONObject.toJSONString(effect));
		}
		Date startAt = DateUtil.parse(DateUtil
				.utc2DefaultLocal(actionEngineModel.getStart_at()));
		if (startAt == null) {
			startAt = DateUtil.parse(actionEngineModel.getStart_at());
		}
		Date endAt = DateUtil.parse(DateUtil.utc2DefaultLocal(actionEngineModel
				.getEnd_at()));
		if (startAt == null) {
			endAt = DateUtil.parse(actionEngineModel.getEnd_at());
		}
		actionEngine.setName(actionEngineModel.getName());
		actionEngine.setStartAt(DateUtil.getDateBegin(startAt));
		actionEngine.setEndAt(DateUtil.getDateEnd(endAt));
		actionEngine.setWechatId(wechatId);
		actionEngine.setStatus((byte) 1);
		actionEngine.setRunType((byte) 1);
		actionEngine.setStatus(ActionEngineStatus.ON.getValue());
		return actionEngine;
	}

	@Override
	public void update(Integer wechatId, User user, QrcodeModel qrcodeModel) {
		ActionEngineModel actionEngineModel = qrcodeModel.getRules();
		notBlank(actionEngineModel.getId(),
				Message.QRCODE_ACTION_ENGINE_ID_NOT_BLANK);
		ActionEngine actionEngine = getRule(wechatId, user, actionEngineModel,
				new Date());
		QrcodeActionEngine qrcodeActionEngine = getQrcodeActionEngine(wechatId,
				actionEngineModel.getId());
		notBlank(qrcodeActionEngine, Message.QRCODE_ACTION_ENGINE_NOT_EXIST);
		ActionEngine exist = actionEngineMapper
				.selectByPrimaryKey(qrcodeActionEngine.getActionEngineId());
		exist.setConditions(actionEngine.getConditions());
		exist.setConditionSql(actionEngine.getConditionSql());
		exist.setEffect(actionEngine.getEffect());
		exist.setEndAt(actionEngine.getEndAt());
		exist.setName(actionEngine.getName());
		exist.setRunType(actionEngine.getRunType());
		exist.setStartAt(actionEngine.getStartAt());
		actionEngineMapper.updateByPrimaryKey(exist);
	}

	private QrcodeActionEngine getQrcodeActionEngine(Integer wechatId,
			Integer qrcodeActionEngineId) {
		QrcodeActionEngine record = new QrcodeActionEngine();
		record.setWechatId(wechatId);
		record.setId(qrcodeActionEngineId);
		return qrcodeActionEngineMapper.selectOne(record);
	}

	@Override
	public void delete(Integer wechatId, User user, Integer qrcodeActionEngineId) {
		QrcodeActionEngine exist = getQrcodeActionEngine(wechatId,
				qrcodeActionEngineId);
		notBlank(exist, Message.QRCODE_ACTION_ENGINE_NOT_EXIST);
		qrcodeActionEngineMapper.delete(exist);
	}

	@Override
	public Page<QrcodeActionEngineDto> search(Integer wechatId,
			ActionEngineModel actionEngineModel, boolean queryCount) {
		PageHelper.startPage(actionEngineModel.getPageNum(),
				actionEngineModel.getPageSize(), queryCount);
		return qrcodeActionEngineMapper
				.search(wechatId, actionEngineModel.getQrcodeId(),
						actionEngineModel.getSortName(),
						actionEngineModel.getSortDir());
	}
}
