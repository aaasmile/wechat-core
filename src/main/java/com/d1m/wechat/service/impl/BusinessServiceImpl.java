package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.io.File;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import cn.d1m.wechat.client.core.WxResponse;
import cn.d1m.wechat.client.model.WxBusiness;
import cn.d1m.wechat.client.model.WxBusinessPhoto;
import cn.d1m.wechat.client.model.common.WxHolder;

import com.d1m.wechat.dto.BusinessAreaListDto;
import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.AreaInfoMapper;
import com.d1m.wechat.mapper.BusinessMapper;
import com.d1m.wechat.mapper.BusinessPhotoMapper;
import com.d1m.wechat.mapper.BusinessResultMapper;
import com.d1m.wechat.model.Business;
import com.d1m.wechat.model.BusinessPhoto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.BusinessStatus;
import com.d1m.wechat.pamametermodel.BusinessModel;
import com.d1m.wechat.service.AreaInfoService;
import com.d1m.wechat.service.BusinessService;
import com.d1m.wechat.util.BaiduLocationUtil;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

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
	private BaiduLocationUtil baiduLocationUtil;

	public void setBusinessMapper(BusinessMapper businessMapper) {
		this.businessMapper = businessMapper;
	}

	private Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

	@Override
	public Mapper<Business> getGenericMapper() {
		return businessMapper;
	}

	@Override
	public Business create(Integer wechatId, User user, BusinessModel model)
			throws WechatException {
		notBlank(model.getBusinessName(), Message.BUSINESS_NAME_NOT_BLANK);
		notBlank(model.getAddress(), Message.BUSINESS_ADDRESS_NOT_BLANK);
		notBlank(model.getTelephone(), Message.BUSINESS_TELEPHONE_NOT_BLANK);

		if (StringUtils.isNotBlank(model.getBranchName())
				&& StringUtils.equals(model.getBusinessName(),
						model.getBranchName())) {
			throw new WechatException(
					Message.BUSINESS_NAME_NOT_EQUALS_BRANCH_NAME);
		}
		Business exist = getByBusinessName(wechatId, model.getBusinessName());
		if (exist != null) {
			throw new WechatException(Message.BUSINESS_NAME_EXIST);
		}

		Business business = new Business();
		business.setWechatId(wechatId);
		business.setBusinessName(model.getBusinessName());
		business.setBranchName(model.getBranchName());
		business.setProvince(model.getProvince());
		business.setCity(model.getCity());
		business.setDistrict(model.getDistrict());
		business.setAddress(model.getAddress());
		business.setTelephone(model.getTelephone());
		business.setLongitude(model.getLongitude());
		business.setLatitude(model.getLatitude());
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

		businessMapper.insertSelective(business);

		createBusinessPhoto(wechatId, model, business);

		return business;
	}

	private void checkBusinessCodeRepeat(String businessCode) {
		Business record = new Business();
		record.setBusinessCode(businessCode);
		record.setStatus((byte) 1);
		record = businessMapper.selectOne(record);
		if (record != null) {
			throw new WechatException(Message.BUSINESS_CODE_EXIST);
		}
	}

	private void checkBusinessCodeRepeat(BusinessModel model) {
		Business record = new Business();
		record.setBusinessCode(model.getBusinessCode());
		record.setStatus((byte) 1);
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
		record.setStatus((byte) 1);
		return businessMapper.selectOne(record);
	}

	@Override
	public Page<BusinessDto> search(Integer wechatId,
			BusinessModel businessModel, boolean queryCount)
			throws WechatException {
		if (businessModel.pagable()) {
			PageHelper.startPage(businessModel.getPageNum(),
					businessModel.getPageSize(), queryCount);
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
	public void delete(Integer wechatId, BusinessModel model)
			throws WechatException {
		notBlank(model.getId(), Message.BUSINESS_ID_NOT_BLANK);
		Business record = getBusiness(wechatId, model.getId());
		record.setStatus(BusinessStatus.DELETED.getValue());
		businessMapper.updateByPrimaryKeySelective(record);
		if (record.getIsPush() == 1) {
			String poiId = businessMapper.searchByBusinessId(model.getId());

			WxResponse result = WechatClientDelegate.deletePOI(wechatId, poiId);

			if (result.fail()) {
				throw new WechatException(Message.BUSINESS_WEXIN_DELETE_FAIL,
						result.getErrmsg());
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

	@Override
	public Business update(Integer wechatId, BusinessModel model)
			throws WechatException {
		notBlank(model.getId(), Message.BUSINESS_ID_NOT_BLANK);
		notBlank(model.getBusinessName(), Message.BUSINESS_NAME_NOT_BLANK);
		notBlank(model.getAddress(), Message.BUSINESS_ADDRESS_NOT_BLANK);
		notBlank(model.getTelephone(), Message.BUSINESS_TELEPHONE_NOT_BLANK);

		if (StringUtils.isNotBlank(model.getBranchName())
				&& StringUtils.equals(model.getBusinessName(),
						model.getBranchName())) {
			throw new WechatException(
					Message.BUSINESS_NAME_NOT_EQUALS_BRANCH_NAME);
		}

		Business business = getBusiness(wechatId, model.getId());
		if (business == null) {
			throw new WechatException(Message.BUSINESS_NOT_EXIST);
		}
		Business exist = getByBusinessName(wechatId, model.getBusinessName());
		if (exist != null && !exist.getId().equals(model.getId())) {
			throw new WechatException(Message.BUSINESS_NAME_EXIST);
		}

		business.setBusinessName(model.getBusinessName());
		business.setBranchName(model.getBranchName());
		business.setProvince(model.getProvince());
		business.setCity(model.getCity());
		business.setDistrict(model.getDistrict());
		business.setAddress(model.getAddress());
		business.setTelephone(model.getTelephone());
		business.setLongitude(model.getLongitude());
		business.setLatitude(model.getLatitude());
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

		if (model.getIsPush() == 1) {
			if (model.getPush() != null) {
				updateBusinessToWx(wechatId, business, model);
			}
		} else {
			if (model.getPush() != null) {
				pushBusinessToWx(wechatId, business, model);
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
	public void pushBusinessToWx(Integer wechatId, Business business,
			BusinessModel model) {
		WxBusiness baseInfo = new WxBusiness();
		List<WxBusinessPhoto> photoList = new ArrayList<>();
		List<String> absolutePhotoList = model.getAbsolutePhotoList();

		if (absolutePhotoList != null && !absolutePhotoList.isEmpty()) {
			for (String absolutePhoto : absolutePhotoList) {
				WxHolder<String> uploadUrl = WechatClientDelegate.uploadImg(
						wechatId, new File(absolutePhoto));
				if (uploadUrl.fail()) {
					throw new WechatException(
							Message.BUSINESS_WEIXIN_PHOTO_UPLOAD_FAIL);
				}
				photoList.add(new WxBusinessPhoto(uploadUrl.get()));
				String[] strs = absolutePhoto.split("/");
				String str = strs[strs.length - 1];
				BusinessPhoto businessPhoto = businessPhotoMapper
						.searchLike(str);
				businessPhoto.setWxUrl(uploadUrl.get());
				businessPhotoMapper.updateByPrimaryKeySelective(businessPhoto);
			}
		}

		if (business.getBusinessCode() != null) {
			baseInfo.setSid(business.getBusinessCode());
		}
		baseInfo.setBusinessName(business.getBusinessName());
		baseInfo.setBranchName(business.getBranchName());
		String province = areaInfoService
				.selectNameById(business.getProvince());
		String city = areaInfoService.selectNameById(business.getCity());
		baseInfo.setProvince(province);
		baseInfo.setCity(city);
		baseInfo.setDistrict(business.getDistrict());
		baseInfo.setAddress(business.getAddress());
		baseInfo.setTelephone(business.getTelephone());
		baseInfo.setOffsetType(1);
		baseInfo.setLongitude(business.getLongitude());
		baseInfo.setLatitude(business.getLatitude());

		if (!photoList.isEmpty()) {
			baseInfo.setPhotoList(photoList);
		}
		if (business.getRecommend() != null) {
			baseInfo.setRecommend(business.getRecommend());
		}
		if (business.getSpecial() != null) {
			baseInfo.setSpecial(business.getSpecial());
		}
		if (business.getIntroduction() != null) {
			baseInfo.setIntroduction(business.getIntroduction());
		}
		if (business.getOpenTime() != null) {
			baseInfo.setOpenTime(business.getOpenTime());
		}
		if (business.getAvgPrice() != null) {
			baseInfo.setAvgPrice(business.getAvgPrice());
		}

		List<String> categories = model.getCategories();
		baseInfo.setCategories(categories);

		WxResponse result = WechatClientDelegate.addPOI(wechatId, baseInfo);

		if (result.fail()) {
			throw new WechatException(Message.BUSINESS_WEIXIN_PUBLISE_FAIL,
					result.getErrmsg());
		}

	}

	private void updateBusinessToWx(Integer wechatId, Business business,
			BusinessModel model) {
		WxBusiness baseInfo = new WxBusiness();
		List<WxBusinessPhoto> photo_list = new ArrayList<>();
		List<String> absolutePhotoList = model.getAbsolutePhotoList();

		if (absolutePhotoList != null && !absolutePhotoList.isEmpty()) {
			for (String absolutePhoto : absolutePhotoList) {
				String uploadUrl = null;
				String[] strs = absolutePhoto.split("/");
				String str = strs[strs.length - 1];
				BusinessPhoto businessPhoto = businessPhotoMapper
						.searchLike(str);
				if (businessPhoto.getWxUrl() == null) {
					WxHolder<String> wxURL = WechatClientDelegate.uploadImg(
							wechatId, new File(absolutePhoto));
					if (wxURL.fail()) {
						throw new WechatException(
								Message.BUSINESS_WEIXIN_PHOTO_UPLOAD_FAIL);
					}
					uploadUrl = wxURL.get();
					businessPhoto.setWxUrl(uploadUrl);
					businessPhotoMapper
							.updateByPrimaryKeySelective(businessPhoto);
				} else {
					uploadUrl = businessPhoto.getWxUrl();
				}

				photo_list.add(new WxBusinessPhoto(uploadUrl));
			}
		}
		baseInfo.setTelephone(business.getTelephone());
		if (!photo_list.isEmpty()) {
			baseInfo.setPhotoList(photo_list);
		}
		if (business.getRecommend() != null) {
			baseInfo.setRecommend(business.getRecommend());
		}
		if (business.getSpecial() != null) {
			baseInfo.setSpecial(business.getSpecial());
		}
		if (business.getIntroduction() != null) {
			baseInfo.setIntroduction(business.getIntroduction());
		}
		if (business.getOpenTime() != null) {
			baseInfo.setOpenTime(business.getOpenTime());
		}
		if (business.getAvgPrice() != null) {
			baseInfo.setAvgPrice(business.getAvgPrice());
		}

		WxResponse result = WechatClientDelegate.updatePOI(wechatId, baseInfo);

		if (result.fail()) {
			throw new WechatException(Message.BUSINESS_WEXIN_UPDATE_FAIL,
					result.getErrmsg());
		}

	}

	@Override
	public synchronized void initBusinessLatAndLng(Integer wechatId, User user) {
		List<Business> list = businessMapper.getAll();
		String[] directCity = {"北京市", "上海市", "重庆市", "天津市"};
		List<String> directCityList = Arrays.asList(directCity);
		Boolean flagBaidu;
		Boolean flagQQ;
		for (Business business : list) {
			if (business.getAddress() != null) {
				flagBaidu = false;
				flagQQ = false;
				if (business.getLatitude() != null && business.getLongitude() != null) {
					flagBaidu = true;
				}
				if (business.getWxlat() != null && business.getWxlng() != null) {
					flagQQ = true;
				}
				if (flagBaidu && flagQQ) {
					continue;
				}
				if (!flagBaidu) {
					Map<String, Double> map = baiduLocationUtil.getLatAndLngByAddress(wechatId,
							business.getAddress());
					if (map != null) {
						log.debug(JSONObject.toJSONString(map));
						Map<String, String> mapAddress = baiduLocationUtil.getAddressByLatAndLng(wechatId,
								map.get("lat").toString(), map.get("lng").toString());
						log.debug(JSONObject.toJSONString(mapAddress));
						if (mapAddress != null) {
							if (map.get("lat") != null && !"".equals(map.get("lat")))
								business.setLatitude(map.get("lat"));
							if (map.get("lng") != null && !"".equals(map.get("lng")))
								business.setLongitude(map.get("lng"));
						} else {
							throw new WechatException(Message.BUSINESS_FETCH_BAIDU_ADDRAPI_FAIL);
						}
						String country = mapAddress.get("country");
						String province = mapAddress.get("province");
						String city = mapAddress.get("city");
						String district = mapAddress.get("district");
						Integer countrycode = areaInfoMapper.selectIdByName(country, null);
						Integer provincecode = areaInfoMapper.selectIdByName(province, countrycode);
						business.setProvince(provincecode);
						business.setDistrict(district);
						if (directCityList.contains(mapAddress.get("province"))) {
							business.setCity(areaInfoMapper.selectIdByName(district, provincecode));
						} else {
							business.setCity(areaInfoMapper.selectIdByName(city, provincecode));
						}
					} else {
						throw new WechatException(Message.BUSINESS_FETCH_BAIDU_GEOAPI_FAIL);
					}
				}
				if (!flagQQ) {
					Double lat = business.getLatitude();
					Double lng = business.getLongitude();
					JSONObject object = baiduLocationUtil.transforLatAndLng(wechatId, lat + "," + lng);
					log.debug(JSONObject.toJSONString(object));
					if (object != null) {
						lat = object.getJSONArray("locations").getJSONObject(0).getDouble("lat");
						lng = object.getJSONArray("locations").getJSONObject(0).getDouble("lng");
					} else {
						throw new WechatException(Message.BUSINESS_TRANSFER_QQ_GEOAPI_FAIL);
					}
					business.setWxlat(lat);
					business.setWxlng(lng);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				log.info(JSONObject.toJSONString(business));
				businessMapper.updateByPrimaryKeySelective(business);

			}
		}
	}
//	public synchronized void initBusinessLatAndLng(Integer wechatId, User user) {
//		List<Business> list = businessMapper.getAll();
//		String[] directCity = { "北京市", "上海市", "重庆市", "天津市" };
//		List<String> directCityList = Arrays.asList(directCity);
//		for (Business business : list) {
//			if (business.getAddress() != null) {
//				Map<String, Double> map = BaiduLocationUtil
//						.getLatAndLngByAddress(business.getAddress());
//				if (map != null) {
//					Map<String, String> mapAddress = BaiduLocationUtil
//							.getAddressByLatAndLng(map.get("lat").toString(),
//									map.get("lng").toString());
//					if (mapAddress != null) {
//						business.setLatitude(map.get("lat"));
//						business.setLongitude(map.get("lng"));
//						String country = mapAddress.get("country");
//						String province = mapAddress.get("province");
//						String city = mapAddress.get("city");
//						String district = mapAddress.get("district");
//						Integer countrycode = areaInfoMapper.selectIdByName(
//								country, null);
//						Integer provincecode = areaInfoMapper.selectIdByName(
//								province, countrycode);
//						business.setProvince(provincecode);
//						business.setDistrict(district);
//						if (directCityList.contains(mapAddress.get("province"))) {
//							business.setCity(areaInfoMapper.selectIdByName(
//									district, provincecode));
//						} else {
//							business.setCity(areaInfoMapper.selectIdByName(
//									city, provincecode));
//						}
//
//						businessMapper.updateByPrimaryKeySelective(business);
//					}
//				}
//			}
//		}
//
//	}

	@Override
	public List<BusinessAreaListDto> getProvinceList(Integer wechatId) {
		return businessMapper.getProvinceList(wechatId);
	}

	@Override
	public List<BusinessAreaListDto> getCityList(Integer wechatId) {
		return businessMapper.getCityList(wechatId);
	}

}
