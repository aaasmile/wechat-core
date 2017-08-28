package com.d1m.wechat.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "activity_coupon_setting")
public class ActivityCouponSetting {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 电子券设置ID
	 */
	@Column(name = "coupon_setting_id")
	private Integer couponSettingId;

	/**
	 * 活动ID
	 */
	@Column(name = "activity_id")
	private Integer activityId;

	/**
	 * 公众号ID
	 */
	@Column(name = "wechat_id")
	private Integer wechatId;

	/**
	 * 获取主键ID
	 *
	 * @return id - 主键ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置主键ID
	 *
	 * @param id
	 *            主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取电子券设置ID
	 *
	 * @return coupon_setting_id - 电子券设置ID
	 */
	public Integer getCouponSettingId() {
		return couponSettingId;
	}

	/**
	 * 设置电子券设置ID
	 *
	 * @param couponSettingId
	 *            电子券设置ID
	 */
	public void setCouponSettingId(Integer couponSettingId) {
		this.couponSettingId = couponSettingId;
	}

	/**
	 * 获取活动ID
	 *
	 * @return activity_id - 活动ID
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * 设置活动ID
	 *
	 * @param activityId
	 *            活动ID
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * 获取公众号ID
	 *
	 * @return wechat_id - 公众号ID
	 */
	public Integer getWechatId() {
		return wechatId;
	}

	/**
	 * 设置公众号ID
	 *
	 * @param wechatId
	 *            公众号ID
	 */
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}
}