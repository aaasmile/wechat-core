package com.d1m.wechat.service.impl;

import java.io.File;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.util.WeixinLocationUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import cn.d1m.wechat.client.core.WxResponse;
import cn.d1m.wechat.client.model.WxBusiness;
import cn.d1m.wechat.client.model.WxBusinessPhoto;
import cn.d1m.wechat.client.model.common.WxHolder;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.enums.BusinessStatus;
import com.d1m.wechat.pamametermodel.BusinessModel;
import com.d1m.wechat.service.AreaInfoService;
import com.d1m.wechat.service.BusinessService;
import com.d1m.wechat.util.BaiduLocationUtil;
import com.d1m.wechat.util.Message;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Service
public class BusinessServiceImpl extends BaseService<Business> implements
		BusinessService {

	@Autowired
	private BusinessMapper businessMapper;

	@Autowired
	private BusinessPhotoMapper businessPhotoMapper;
	
	@Autowired
	private AreaInfoService areaInfoService;
	
	@Autowired
	private BusinessResultMapper businessResultMapper;
	
	@Autowired
	private AreaInfoMapper areaInfoMapper;

	@Autowired
	private CouponBusinessMapper couponBusinessMapper;

	@Autowired
	private OfflineActivityBusinessMapper offlineActivityBusinessMapper;

	public void setBusinessMapper(BusinessMapper businessMapper) {
		this.businessMapper = businessMapper;
	}

	@Override
	public Mapper<Business> getGenericMapper() {
		return businessMapper;
	}

	@Override
	public Business create(Integer wechatId, User user, BusinessModel model)
			throws WechatException {
		notBlank(model.getBusinessCode(), Message.BUSINESS_CODE_NOT_BLANK);
		notBlank(model.getBusinessName(), Message.BUSINESS_NAME_NOT_BLANK);
		notBlank(model.getBranchName(), Message.BUSINESS_BRANCH_NAME_NOT_BLANK);
		notBlank(model.getAddress(), Message.BUSINESS_ADDRESS_NOT_BLANK);
		notBlank(model.getTelephone(), Message.BUSINESS_TELEPHONE_NOT_BLANK);
		notBlank(model.getCategories(), Message.BUSINESS_CATEGORY_NAME_NOT_BLANK);

		if (StringUtils.isNotBlank(model.getBranchName())
				&& StringUtils.equals(model.getBusinessName(),
						model.getBranchName())) {
			throw new WechatException(
					Message.BUSINESS_NAME_NOT_EQUALS_BRANCH_NAME);
		}
//		Business exist = getByBusinessName(wechatId, model.getBusinessName());
//		if (exist != null) {
//			throw new WechatException(Message.BUSINESS_NAME_EXIST);
//		}

//		Business business = new Business();
//		business.setWechatId(wechatId);
//		business.setBusinessName(model.getBusinessName());
//		business.setBranchName(model.getBranchName());
//		business.setProvince(model.getProvince());
//		business.setCity(model.getCity());
//		business.setDistrict(model.getDistrict());
//		business.setAddress(model.getAddress());
//		business.setTelephone(model.getTelephone());
//		business.setLongitude(model.getLongitude());
//		business.setLatitude(model.getLatitude());
//		business.setRecommend(model.getRecommend());
//		business.setSpecial(model.getSpecial());
//		business.setIntroduction(model.getIntroduction());
//		business.setOpenTime(model.getOpenStartTime() + "-"
//				+ model.getOpenEndTime());
//		business.setAvgPrice(model.getAvgPrice());
//		business.setCreatedAt(new Date());
//		business.setCreatorId(user.getId());
//		business.setStatus(BusinessStatus.INUSED.getValue());
//
//		checkBusinessCodeRepeat(model.getBusinessCode());
//		business.setBusinessCode(model.getBusinessCode());
//		business.setBus(model.getBus());
//		business.setIsPush(BusinessStatus.NOTPUSHED.getValue());
//
//		businessMapper.insertSelective(business);
//
//		createBusinessPhoto(wechatId, model, business);
//
//		return business;
		Business business = new Business();
		business.setWechatId(wechatId);
		business.setBusinessName(model.getBusinessName());
		business.setBranchName(model.getBranchName());
		business.setProvince(model.getProvince());
		business.setCity(model.getCity());
		business.setDistrict(model.getDistrict());
		if(model.getAddress().contains("区")){
			business.setAddress(model.getAddress().split("区")[1]);
		}else if(model.getAddress().contains("县")){
			business.setAddress(model.getAddress().split("县")[1]);
		}else{
			business.setAddress(model.getAddress());
		}

		business.setTelephone(model.getTelephone());
		business.setLongitude(model.getLongitude());
		business.setLatitude(model.getLatitude());

		/** get weixin location */
		Map<String, Double> wxMap = WeixinLocationUtil.
				getWxLatAndLngByBaiduLocation(model.getLatitude().toString(),
						model.getLongitude().toString());
		if(wxMap != null){
			business.setWxlat(wxMap.get("wxlat"));
			business.setWxlng(wxMap.get("wxlng"));
		}

		business.setRecommend(model.getRecommend());
		business.setSpecial(model.getSpecial());
		business.setIntroduction(model.getIntroduction());
		business.setOpenTime(model.getOpenStartTime() + "-"
				+ model.getOpenEndTime());
		business.setAvgPrice(model.getAvgPrice());
		business.setCreatedAt(new Date());
		business.setCreatorId(user.getId());
		business.setStatus(BusinessStatus.INUSED.getValue());

		checkBusinessCodeRepeat(model.getBusinessCode());
		business.setBusinessCode(model.getBusinessCode());
		business.setBus(model.getBus());
		business.setIsPush(BusinessStatus.NOTPUSHED.getValue());
		business.setUpdateStatus(BusinessStatus.NOTUPDATE.getValue());
		business.setCheckStatus(BusinessStatus.NOTUPLOAD.getValue());
		business.setCheckMsg("微信未上传");
		business.setCategories(model.getCategories());

		businessMapper.insertSelective(business);

		createBusinessPhoto(wechatId, model, business);

		return business;
	}

	private void checkBusinessCodeRepeat(String businessCode) {
		Business record = new Business();
		record.setBusinessCode(businessCode);
		record.setStatus((byte)1);
		record = businessMapper.selectOne(record);
		if (record != null) {
			throw new WechatException(Message.BUSINESS_CODE_EXIST);
		}
	}

	private void checkBusinessCodeRepeat(BusinessModel model) {
		Business record = new Business();
		record.setBusinessCode(model.getBusinessCode());
		record.setStatus((byte)1);
		record = businessMapper.selectOne(record);
		if (record != null) {
			if (!record.getId().equals(model.getId())) {
				throw new WechatException(Message.BUSINESS_CODE_EXIST);
			}
		}
	}

	private Business getByBusinessName(Integer wechatId, String businessName) {
		Business record = new Business();
		record.setBusinessName(businessName);
		record.setWechatId(wechatId);
		record.setStatus((byte)1);
		return businessMapper.selectOne(record);
	}

//	@Override
//	public Page<BusinessDto> search(Integer wechatId,
//			BusinessModel businessModel, boolean queryCount)
//			throws WechatException {
//		if (businessModel.pagable()) {
//			PageHelper.startPage(businessModel.getPageNum(),
//					businessModel.getPageSize(), queryCount);
//		}
//		return businessMapper.search(wechatId,
//				BusinessStatus.INUSED.getValue(), businessModel.getProvince(),
//				businessModel.getCity(), businessModel.getLng(),
//				businessModel.getLat(), businessModel.getQuery(),
//				businessModel.getSortName(), businessModel.getSortDir());
//	}

	@Override
	public Page<BusinessDto> search(Integer wechatId,
									BusinessModel businessModel, boolean queryCount)
			throws WechatException {
		if (businessModel.pagable()) {
			PageHelper.startPage(businessModel.getPageNum(),
					businessModel.getPageSize(), queryCount);
		}
		if(businessModel.getProvince() != null){
			List<String> places = Arrays.asList("北京", "天津", "上海", "重庆");
			String place = areaInfoMapper.selectNameById(businessModel.getProvince());
			if(places.contains(place)){
				return businessMapper.searchDirect(wechatId, businessModel.getProvince(),
						businessModel.getCity(), businessModel.getSortName(), businessModel.getSortDir());
			}
		}
		return businessMapper.search(wechatId,
				BusinessStatus.INUSED.getValue(), businessModel.getProvince(),
				businessModel.getCity(), businessModel.getLng(),
				businessModel.getLat(), businessModel.getQuery(),
				businessModel.getSortName(), businessModel.getSortDir());
	}

	@Override
	public BusinessDto get(Integer wechatId, Integer id) throws WechatException {
		notBlank(id, Message.BUSINESS_ID_NOT_BLANK);
		return businessMapper.get(wechatId, id);
	}

	@Override
	public void delete(Integer wechatId, BusinessModel model) throws WechatException {
//		notBlank(model.getId(), Message.BUSINESS_ID_NOT_BLANK);
//		Business record = getBusiness(wechatId, model.getId());
//		record.setStatus(BusinessStatus.DELETED.getValue());
//		businessMapper.updateByPrimaryKeySelective(record);
//		if(record.getIsPush() == 1){
//			String poiId = businessMapper.searchByBusinessId(model.getId());
//
//            WxResponse result = WechatClientDelegate.deletePOI(wechatId, poiId);
//
//			if(result.fail()){
//				throw new WechatException(Message.BUSINESS_WEXIN_DELETE_FAIL, result.getErrmsg());
//			}
//		}
		notBlank(model.getId(), Message.BUSINESS_ID_NOT_BLANK);
		Business record = getBusiness(wechatId, model.getId());
		if(record.getCheckStatus() == 0){
			throw new WechatException(Message.BUSINESS_CHECKING_NOT_DELETE);
		}

		//关联优惠券不能删除
		CouponBusiness couponBusiness = new CouponBusiness();
		couponBusiness.setBusinessId(model.getId());
		couponBusiness.setWechatId(wechatId);
		if(couponBusinessMapper.selectCount(couponBusiness)>0){
			throw new WechatException(Message.BUSINESS_COUPON_NOT_DELETE);
		}

		//关联线下活动不能删除
		OfflineActivityBusiness offlineActivityBusiness = new OfflineActivityBusiness();
		offlineActivityBusiness.setBusinessId(model.getId());
		offlineActivityBusiness.setWechatId(wechatId);
		if(offlineActivityBusinessMapper.selectCount(offlineActivityBusiness)>0){
			throw new WechatException(Message.BUSINESS_OFFLINE_ACTIVITY_NOT_DELETE);
		}

		record.setStatus(BusinessStatus.DELETED.getValue());
		businessMapper.updateByPrimaryKeySelective(record);
		if(record.getIsPush() == 1){
			String poiId = record.getPoiId();
			if(poiId != null){
				WxResponse result = WechatClientDelegate.deletePOI(wechatId, poiId);
				if(!result.getErrmsg().equals("ok")){
					throw new WechatException(Message.BUSINESS_WEXIN_DELETE_FAIL);
				}
			}
		}
	}

	private Business getBusiness(Integer wechatId, Integer id) {
		Business record = new Business();
		record.setId(id);
		record.setWechatId(wechatId);
		record.setStatus(BusinessStatus.INUSED.getValue());
		record = businessMapper.selectOne(record);
		if (record == null) {
			throw new WechatException(Message.BUSINESS_NOT_EXIST);
		}
		return record;
	}

//	@Override
//	public Business update(Integer wechatId, BusinessModel model)
//			throws WechatException {
//		notBlank(model.getId(), Message.BUSINESS_ID_NOT_BLANK);
//		notBlank(model.getBusinessName(), Message.BUSINESS_NAME_NOT_BLANK);
//		notBlank(model.getAddress(), Message.BUSINESS_ADDRESS_NOT_BLANK);
//		notBlank(model.getTelephone(), Message.BUSINESS_TELEPHONE_NOT_BLANK);
//
//		if (StringUtils.isNotBlank(model.getBranchName())
//				&& StringUtils.equals(model.getBusinessName(),
//						model.getBranchName())) {
//			throw new WechatException(
//					Message.BUSINESS_NAME_NOT_EQUALS_BRANCH_NAME);
//		}
//
//		Business business = getBusiness(wechatId, model.getId());
//		if (business == null) {
//			throw new WechatException(Message.BUSINESS_NOT_EXIST);
//		}
//		Business exist = getByBusinessName(wechatId, model.getBusinessName());
//		if (exist != null && !exist.getId().equals(model.getId())) {
//			throw new WechatException(Message.BUSINESS_NAME_EXIST);
//		}
//
//		business.setBusinessName(model.getBusinessName());
//		business.setBranchName(model.getBranchName());
//		business.setProvince(model.getProvince());
//		business.setCity(model.getCity());
//		business.setDistrict(model.getDistrict());
//		business.setAddress(model.getAddress());
//		business.setTelephone(model.getTelephone());
//		business.setLongitude(model.getLongitude());
//		business.setLatitude(model.getLatitude());
//		business.setRecommend(model.getRecommend());
//		business.setSpecial(model.getSpecial());
//		business.setIntroduction(model.getIntroduction());
//		business.setOpenTime(model.getOpenStartTime() + "-"
//				+ model.getOpenEndTime());
//		business.setAvgPrice(model.getAvgPrice());
//
//		checkBusinessCodeRepeat(model);
//		business.setBusinessCode(model.getBusinessCode());
//		business.setBus(model.getBus());
//
//		businessMapper.updateByPrimaryKeySelective(business);
//
//		BusinessPhoto record = new BusinessPhoto();
//		record.setBusinessId(business.getId());
//		record.setWechatId(wechatId);
//		businessPhotoMapper.delete(record);
//		createBusinessPhoto(wechatId, model, business);
//
//		if(model.getIsPush() == 1){
//			if(model.getPush()!=null){
//				updateBusinessToWx(wechatId, business, model);
//			}
//		}else{
//			if(model.getPush()!=null){
//				pushBusinessToWx(wechatId, business, model);
//			}
//		}
//
//		return business;
//	}

	@Override
	public Business update(Integer wechatId, BusinessModel model)
			throws WechatException {
		notBlank(model.getId(), Message.BUSINESS_ID_NOT_BLANK);
		Business business = getBusiness(wechatId, model.getId());
		if (business == null) {
			throw new WechatException(Message.BUSINESS_NOT_EXIST);
		}

		if(model.getIsPush() == 0){
			notBlank(model.getBusinessName(), Message.BUSINESS_NAME_NOT_BLANK);
			notBlank(model.getAddress(), Message.BUSINESS_ADDRESS_NOT_BLANK);
			notBlank(model.getTelephone(), Message.BUSINESS_TELEPHONE_NOT_BLANK);
			notBlank(model.getBranchName(), Message.BUSINESS_BRANCH_NAME_NOT_BLANK);

			if (org.apache.commons.lang.StringUtils.isNotBlank(model.getBranchName())
					&& org.apache.commons.lang.StringUtils.equals(model.getBusinessName(),
					model.getBranchName())) {
				throw new WechatException(
						Message.BUSINESS_NAME_NOT_EQUALS_BRANCH_NAME);
			}
			/*Business exist = getByBusinessName(wechatId, model.getBusinessName());
			if (exist != null && !exist.getId().equals(model.getId())) {
				throw new WechatException(Message.BUSINESS_NAME_EXIST);
			}*/

			business.setBusinessName(model.getBusinessName());
			business.setBranchName(model.getBranchName());
			business.setProvince(model.getProvince());
			business.setCity(model.getCity());
			business.setDistrict(model.getDistrict());
//			if(model.getAddress().contains("区")){
//				business.setAddress(model.getAddress().split("区")[1]);
//			}else if(model.getAddress().contains("县")){
//				business.setAddress(model.getAddress().split("县")[1]);
//			}else{
//				business.setAddress(model.getAddress());
//			}
			business.setAddress(model.getAddress());
			business.setTelephone(model.getTelephone());
			business.setLongitude(model.getLongitude());
			business.setLatitude(model.getLatitude());
			business.setCategories(model.getCategories());

			/** get weixin location */
			Map<String, Double> wxMap = WeixinLocationUtil.
					getWxLatAndLngByBaiduLocation(model.getLatitude().toString(),
							model.getLongitude().toString());
			if(wxMap != null){
				business.setWxlat(wxMap.get("wxlat"));
				business.setWxlng(wxMap.get("wxlng"));
			}
		}

		business.setRecommend(model.getRecommend());
		business.setSpecial(model.getSpecial());
		business.setIntroduction(model.getIntroduction());
		business.setOpenTime(model.getOpenStartTime() + "-"
				+ model.getOpenEndTime());
		business.setAvgPrice(model.getAvgPrice());
		checkBusinessCodeRepeat(model);
		business.setBusinessCode(model.getBusinessCode());
		business.setBus(model.getBus());

		businessMapper.updateByPrimaryKeySelective(business);

		BusinessPhoto record = new BusinessPhoto();
		record.setBusinessId(business.getId());
		record.setWechatId(wechatId);
		businessPhotoMapper.delete(record);
		createBusinessPhoto(wechatId, model, business);

		if(model.getIsPush() == 1){
			if(model.getPush()){
				business = updateBusinessToWx(wechatId, business, model);
			}
		}else{
			if(model.getPush()){
				business = pushBusinessToWx(wechatId, business, model);
			}
		}

		return business;
	}

	private void createBusinessPhoto(Integer wechatId, BusinessModel model,
			Business business) {
		List<String> photoList = model.getPhotoList();
		if (photoList == null || photoList.isEmpty()) {
			return;
		}
		List<BusinessPhoto> businessPhotos = new ArrayList<BusinessPhoto>();
		for (String photpUrl : photoList) {
            BusinessPhoto businessPhoto = new BusinessPhoto();
			businessPhoto.setBusinessId(business.getId());
			businessPhoto.setPhotoUrl(photpUrl);
			businessPhoto.setWechatId(wechatId);
			businessPhotos.add(businessPhoto);
		}
		businessPhotoMapper.insertList(businessPhotos);
	}

	@Override
	public Business getBusinessByCode(Integer wechatId, String code) {
		Business record = new Business();
		record.setWechatId(wechatId);
		record.setBusinessCode(code);
		return businessMapper.selectOne(record);
	}

	@Override
	public List<BusinessDto> searchByLngLat(Integer wechatId, Double lng,
			Double lat, Integer size) throws WechatException {
		return businessMapper.searchByLngLat(wechatId, lng, lat, size);
	}

	@Override
	public Business pushBusinessToWx(Integer wechatId, Business business, BusinessModel model) {
        WxBusiness baseInfo = new WxBusiness();
//		List<WxBusinessPhoto> photoList = new ArrayList<>();
//		List<String> absolutePhotoList = model.getAbsolutePhotoList();

//		if(absolutePhotoList != null && !absolutePhotoList.isEmpty()){
//			for(String absolutePhoto : absolutePhotoList){
//				WxHolder<String> uploadUrl = WechatClientDelegate.uploadImg(wechatId, new File(absolutePhoto));
//				if(uploadUrl.fail()){
//					throw new WechatException(Message.BUSINESS_WEIXIN_PHOTO_UPLOAD_FAIL);
//				}
//				photoList.add(new WxBusinessPhoto(uploadUrl.get()));
//				String[] strs = absolutePhoto.split("/");
//				String str = strs[strs.length-1];
//				BusinessPhoto businessPhoto = businessPhotoMapper.searchLike(str);
//				businessPhoto.setWxUrl(uploadUrl.get());
//				businessPhotoMapper.updateByPrimaryKeySelective(businessPhoto);
//			}
//		}

		List<MaterialDto> materialList = model.getMaterialList();
		List<WxBusinessPhoto> photoList = new ArrayList<WxBusinessPhoto>();
		if(materialList != null){
			for(MaterialDto materialDto : materialList){
				WxBusinessPhoto photo = new WxBusinessPhoto();
				photo.setPhotoUrl(materialDto.getWxPicUrl());
				photoList.add(photo);
			}
		}

		if(business.getBusinessCode() != null){
			baseInfo.setSid(business.getBusinessCode());
		}
		baseInfo.setBusinessName(business.getBusinessName());
		baseInfo.setBranchName(business.getBranchName());
		String province = areaInfoService.selectNameById(business.getProvince());
		String city =  areaInfoService.selectNameById(business.getCity());
		baseInfo.setProvince(province);
		baseInfo.setCity(city);
		baseInfo.setDistrict(business.getDistrict());
		baseInfo.setAddress(business.getAddress());
		baseInfo.setTelephone(business.getTelephone());
		baseInfo.setOffsetType(1);
		baseInfo.setLongitude(business.getLongitude());
		baseInfo.setLatitude(business.getLatitude());

		if(!photoList.isEmpty()){
			baseInfo.setPhotoList(photoList);
		}
		if(business.getRecommend()!=null){
			baseInfo.setRecommend(business.getRecommend());
		}
		if(business.getSpecial()!=null){
			baseInfo.setSpecial(business.getSpecial());
		}
		if(business.getIntroduction()!=null){
			baseInfo.setIntroduction(business.getIntroduction());
		}
		if(business.getOpenTime()!=null){
			baseInfo.setOpenTime(business.getOpenTime());
		}
		if(business.getAvgPrice()!=null){
			baseInfo.setAvgPrice(business.getAvgPrice());
		}

//		List<String> categories = model.getCategories();
//		baseInfo.setCategories(categories);
		JSONArray categoryArray = JSONObject.parseArray(model.getCategories());
		String categoriesStr = categoryArray.getString(0) + "," + categoryArray.getString(1);
		List<String> categories  = new ArrayList<String>();
		categories.add(categoriesStr);
		baseInfo.setCategories(categories);

        WxResponse result = WechatClientDelegate.addPOI(wechatId, baseInfo);

//        if(result.fail()){
//            throw new WechatException(Message.BUSINESS_WEIXIN_PUBLISE_FAIL, result.getErrmsg());
//        }
		if(result.getErrmsg().equals("ok")){
			business.setCheckStatus(BusinessStatus.CHECKING.getValue());
			business.setCheckMsg("发布审核中");
			business.setUpdateStatus(BusinessStatus.UPDATE.getValue());
		}else {
			business.setCheckStatus(BusinessStatus.CHECKFAIL.getValue());
			business.setCheckMsg(result.getErrmsg());
		}
		return business;
	}

	private Business updateBusinessToWx(Integer wechatId, Business business,
			BusinessModel model) {
		WxBusiness baseInfo = new WxBusiness();
//		List<WxBusinessPhoto> photo_list = new ArrayList<>();
//		List<String> absolutePhotoList = model.getAbsolutePhotoList();
//		if(absolutePhotoList != null && !absolutePhotoList.isEmpty()){
//			for(String absolutePhoto : absolutePhotoList){
//				String uploadUrl = null;
//				String[] strs = absolutePhoto.split("/");
//				String str = strs[strs.length-1];
//				BusinessPhoto businessPhoto = businessPhotoMapper.searchLike(str);
//				if(businessPhoto.getWxUrl() == null){
//                    WxHolder<String> wxURL = WechatClientDelegate.uploadImg(wechatId, new File(absolutePhoto));
//                    if(wxURL.fail()){
//						throw new WechatException(Message.BUSINESS_WEIXIN_PHOTO_UPLOAD_FAIL);
//					}
//                    uploadUrl = wxURL.get();
//					businessPhoto.setWxUrl(uploadUrl);
//					businessPhotoMapper.updateByPrimaryKeySelective(businessPhoto);
//				}else{
//					uploadUrl = businessPhoto.getWxUrl();
//				}
//
//                photo_list.add(new WxBusinessPhoto(uploadUrl));
//			}
//		}
		List<MaterialDto> materialList = model.getMaterialList();
		List<WxBusinessPhoto> photoList = new ArrayList<WxBusinessPhoto>();
		for(MaterialDto materialDto : materialList){
			WxBusinessPhoto photo = new WxBusinessPhoto();
			photo.setPhotoUrl(materialDto.getWxPicUrl());
			photoList.add(photo);
		}

		baseInfo.setTelephone(business.getTelephone());
		baseInfo.setPoiId(business.getPoiId());
		baseInfo.setSid(business.getBusinessCode());
		if(!photoList.isEmpty()){
			baseInfo.setPhotoList(photoList);
		}
		if(business.getRecommend()!=null){
			baseInfo.setRecommend(business.getRecommend());
		}
		if(business.getSpecial()!=null){
			baseInfo.setSpecial(business.getSpecial());
		}
		if(business.getIntroduction()!=null){
			baseInfo.setIntroduction(business.getIntroduction());
		}
		if(business.getOpenTime()!=null){
			baseInfo.setOpenTime(business.getOpenTime());
		}
		if(business.getAvgPrice()!=null){
			baseInfo.setAvgPrice(business.getAvgPrice());
		}

        WxResponse result = WechatClientDelegate.updatePOI(wechatId, baseInfo);

//        if(result.fail()){
//            throw new WechatException(Message.BUSINESS_WEXIN_UPDATE_FAIL, result.getErrmsg());
//        }
		if(!result.getErrmsg().equals("ok")){
			business.setCheckStatus(BusinessStatus.CHECKFAIL.getValue());
			business.setCheckMsg(result.getErrmsg());
			business.setUpdateStatus(BusinessStatus.NOTUPDATE.getValue());
		}else{
			business.setCheckStatus(BusinessStatus.CHECKING.getValue());
			business.setUpdateStatus(BusinessStatus.UPDATE.getValue());
			business.setCheckMsg("修改审核中");
		}
		businessMapper.updateByPrimaryKeySelective(business);
		return business;
	}

	@Override
	public synchronized void initBusinessLatAndLng(Integer wechatId, User user) {
		List<Business> list = businessMapper.getAll();
		String[] directCity = {"北京市", "上海市", "重庆市", "天津市"};
		List<String> directCityList = Arrays.asList(directCity);
		for(Business business:list){
			if(business.getAddress()!=null){
				Map<String, Double> map = BaiduLocationUtil.getLatAndLngByAddress(
						business.getAddress());
				if(map!=null){
					Map<String, String> mapAddress = BaiduLocationUtil.getAddressByLatAndLng(
							map.get("lat").toString(), map.get("lng").toString());
					if(mapAddress!=null){
						business.setLatitude(map.get("lat"));
						business.setLongitude(map.get("lng"));
						String country = mapAddress.get("country");
						String province = mapAddress.get("province");
						String city = mapAddress.get("city");
						String district = mapAddress.get("district");
						Integer countrycode = areaInfoMapper.selectIdByName(country, null);
						Integer provincecode = areaInfoMapper.selectIdByName(province, countrycode);
						business.setProvince(provincecode);
						business.setDistrict(district);
						if(directCityList.contains(mapAddress.get("province"))){
							business.setCity(areaInfoMapper.selectIdByName(district, provincecode));
						}else{
							business.setCity(areaInfoMapper.selectIdByName(city, provincecode));
						}
						
						businessMapper.updateByPrimaryKeySelective(business);
					}
				}
			}
		}
		
	}

}
