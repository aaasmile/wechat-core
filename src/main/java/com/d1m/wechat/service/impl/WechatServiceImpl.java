package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.UserWechatMapper;
import com.d1m.wechat.mapper.WechatMapper;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserWechat;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.pamametermodel.WechatModel;
import com.d1m.wechat.pamametermodel.WechatTagModel;
import com.d1m.wechat.service.UserService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class WechatServiceImpl extends BaseService<Wechat> implements
		WechatService {

	@Autowired
	private WechatMapper wechatMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserWechatMapper userWechatMapper;
	
	private void checkWechatNameRepeat(String wechatName){		
		Wechat record = new Wechat();
		record.setName(wechatName);
		record = wechatMapper.selectOne(record);
		if (record != null){
			throw new WechatException(Message.WECHAT_NAME_NOT_REPEAT);
		}
	}
	
	private void checkWechatNameRepeat(WechatTagModel wechatTagModel){
		Wechat record = new Wechat();
		record.setName(wechatTagModel.getName());
		record = wechatMapper.selectOne(record);
		if (record != null){
			if (record.getId() != wechatTagModel.getId()){
				throw new WechatException(Message.WECHAT_NAME_NOT_REPEAT);
			}
		}
	}
	
	@Override
	public Mapper<Wechat> getGenericMapper() {
		return wechatMapper;
	}

	@Override
	public Wechat getByOpenId(String openId) {
		if (StringUtils.isBlank(openId)) {
			return null;
		}
		
		Wechat record = new Wechat();
		record.setOpenId(openId);
		return wechatMapper.selectOne(record);
	}

	@Override
	public Page<Wechat> search(WechatModel wechatModel, boolean queryCount) {
		if (wechatModel.pagable()) {
			PageHelper.startPage(wechatModel.getPageNum(),
					wechatModel.getPageSize(), queryCount);
		}
		return wechatMapper.search(wechatModel.getName(),
				wechatModel.getStatus(), wechatModel.getSortName(),
				wechatModel.getSortDir(), wechatModel.getCompanyId());
	}

	/**
	 * 新增公众号(Name, Appid, Appscret, Token, Url, EncodingAesKey, Openid, Remark)
	 */
	@Override
	public int insert(WechatTagModel wechatTagModel, User user) {
		if (wechatTagModel == null) {
			wechatTagModel = new WechatTagModel();
		}
		
		notBlank(wechatTagModel.getName(), Message.WECHAT_NAME_NOT_BLANK);
		notBlank(wechatTagModel.getAppid(), Message.WECHAT_APP_ID_NOT_BLANK);
		notBlank(wechatTagModel.getAppscret(), Message.WECHAT_APP_SECRET_NOT_BLANK);
		notBlank(wechatTagModel.getToken(), Message.WECHAT_TOKEN_NOT_BLANK);
		notBlank(wechatTagModel.getUrl(), Message.WECHAT_URL_NOT_BLANK);
		notBlank(wechatTagModel.getEncodingAesKey(), Message.WECHAT_ENCODING_AES_KEY_NOT_BLANK);
		notBlank(wechatTagModel.getOpenId(), Message.WECHAT_OPEN_ID_NOT_BLANK);
		
		checkWechatNameRepeat(wechatTagModel.getName());
		
		Wechat wechat = new Wechat();
		wechat.setName(wechatTagModel.getName());
		wechat.setAppid(wechatTagModel.getAppid());
		wechat.setAppscret(wechatTagModel.getAppscret());
		wechat.setToken(wechatTagModel.getToken());
		wechat.setUrl(wechatTagModel.getUrl());
		wechat.setEncodingAesKey(wechatTagModel.getEncodingAesKey());
		wechat.setOpenId(wechatTagModel.getOpenId());
		wechat.setRemark(wechatTagModel.getRemark());
		wechat.setCreatedAt(new Date());
		wechat.setUserId(user.getId());
		wechat.setCompanyId(user.getCompanyId());
		wechat.setStatus((byte)1);
		if(wechatTagModel.getHeadImgUrl() != null){
			wechat.setHeadImgUrl(wechatTagModel.getHeadImgUrl());
		}
		
		return wechatMapper.insert(wechat);
		
	}
	
	@Override
	public Wechat getById(Integer id) {
		notBlank(id, Message.WECHAT_NOT_EXIST);
		
		Wechat wechat = new Wechat();
		wechat.setId(id);	
		return wechatMapper.selectOne(wechat);
		
	}

	@Override
	public int update(Integer id, WechatTagModel wechatTagModel, User user) {
		notBlank(id, Message.WECHAT_NOT_EXIST);
		
		if (wechatTagModel == null) {
			wechatTagModel = new WechatTagModel();
		}
		
		notBlank(wechatTagModel.getName(), Message.WECHAT_NAME_NOT_BLANK);
		notBlank(wechatTagModel.getAppid(), Message.WECHAT_APP_ID_NOT_BLANK);
		notBlank(wechatTagModel.getAppscret(), Message.WECHAT_APP_SECRET_NOT_BLANK);
		notBlank(wechatTagModel.getToken(), Message.WECHAT_TOKEN_NOT_BLANK);
		notBlank(wechatTagModel.getUrl(), Message.WECHAT_URL_NOT_BLANK);
		notBlank(wechatTagModel.getEncodingAesKey(), Message.WECHAT_ENCODING_AES_KEY_NOT_BLANK);
		notBlank(wechatTagModel.getOpenId(), Message.WECHAT_OPEN_ID_NOT_BLANK);
		
		checkWechatNameRepeat(wechatTagModel);
		
		Wechat wechat = new Wechat();
		wechat.setId(id);
		Wechat record = wechatMapper.selectOne(wechat);
		wechat.setName(wechatTagModel.getName());
		wechat.setAppid(wechatTagModel.getAppid());
		wechat.setAppscret(wechatTagModel.getAppscret());
		wechat.setToken(wechatTagModel.getToken());
		wechat.setUrl(wechatTagModel.getUrl());
		wechat.setEncodingAesKey(wechatTagModel.getEncodingAesKey());
		wechat.setOpenId(wechatTagModel.getOpenId());
		wechat.setRemark(wechatTagModel.getRemark());
		wechat.setCreatedAt(record.getCreatedAt());
		wechat.setUserId(user.getId());
		if(wechatTagModel.getHeadImgUrl() != null){
			wechat.setHeadImgUrl(wechatTagModel.getHeadImgUrl());
		}
		
		return wechatMapper.updateByPrimaryKeySelective(wechat);
	}

	@Override
	public int updateStatus(Integer id, Byte statusId) {
		notBlank(id, Message.WECHAT_NOT_EXIST);
		
		Wechat record = new Wechat();
		record.setId(id);
		record.setStatus(statusId);
		return wechatMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Integer getIsSystemRole(User user) {
		return wechatMapper.getIsSystemRole(user.getRoleId());
	}

	@Override
	public void updateIsUse(User user, Integer wechatId) {
		List<UserWechat> userWechats = userWechatMapper.search(user.getId(), null, user.getCompanyId());
		UserWechat check = new UserWechat();
		check.setUserId(user.getId());
		check.setWechatId(wechatId);
		UserWechat checkCode = userWechatMapper.selectOne(check);
		if(checkCode == null){
			throw new WechatException(Message.USER_NOT_RALATE_CHANGE_WECHAT);
		}
		for(UserWechat userwechat : userWechats){
			UserWechat record = new UserWechat();
			record.setUserId(user.getId());
			if(wechatId.equals(userwechat.getWechatId().intValue())){
				record.setWechatId(wechatId);
				record = userWechatMapper.selectOne(record);
				record.setIsUse((byte)1);
			}else{
				record.setWechatId(userwechat.getWechatId());
				record = userWechatMapper.selectOne(record);
				record.setIsUse((byte)0);
			}
			userWechatMapper.updateByPrimaryKeySelective(record);
		}
	}

	@Override
	public List<Wechat> getWechatList(){
		return wechatMapper.getWechatList();
	}

}
