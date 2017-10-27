package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("优惠券参数")
public class CouponModel extends BaseModel {
	
	@ApiModelProperty("优惠券配置ID")
	private Integer couponSettingId;
	
	@ApiModelProperty("活动ID")
	private Integer activityId;
	
	@ApiModelProperty("状态(0:删除,1:使用)")
	private Byte status;
	
	@ApiModelProperty("门店ID")
	private Integer businessId;
	
	@ApiModelProperty("会员ID")
	private Integer memberId;
	
	@ApiModelProperty("赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折")
	private String giftType;
	
	@ApiModelProperty("公众号ID")
	private Integer wechatId;
	
	@ApiModelProperty("活动编号")
	private List<String> grnos;
	
	@ApiModelProperty("状态不是以下类型")
	private Byte except;

	public Integer getActivityId() {
		return activityId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public Integer getCouponSettingId() {
		return couponSettingId;
	}

	public Byte getExcept() {
		return except;
	}

	public String getGiftType() {
		return giftType;
	}

	public List<String> getGrnos() {
		return grnos;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public Byte getStatus() {
		return status;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public void setCouponSettingId(Integer couponSettingId) {
		this.couponSettingId = couponSettingId;
	}

	public void setExcept(Byte except) {
		this.except = except;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public void setGrnos(List<String> grnos) {
		this.grnos = grnos;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

}
