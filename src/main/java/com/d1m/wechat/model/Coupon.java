package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Coupon {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 电子券编码
	 */
	private String code;

	/**
	 * 来源(0:D1M,1:CRM)
	 */
	private Byte source;

	/**
	 * 使用状态(0:未领用,1:已领用,2:已核销)
	 */
	private Byte status;

	/**
	 * 生成时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 活动ID
	 */
	@Column(name = "coupon_setting_id")
	private Integer couponSettingId;

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
	 * 获取电子券编码
	 *
	 * @return code - 电子券编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置电子券编码
	 *
	 * @param code
	 *            电子券编码
	 */
	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	/**
	 * 获取来源(0:D1M,1:CRM)
	 *
	 * @return source - 来源(0:D1M,1:CRM)
	 */
	public Byte getSource() {
		return source;
	}

	/**
	 * 设置来源(0:D1M,1:CRM)
	 *
	 * @param source
	 *            来源(0:D1M,1:CRM)
	 */
	public void setSource(Byte source) {
		this.source = source;
	}

	/**
	 * 获取使用状态(0:未领用,1:已领用)
	 *
	 * @return status - 使用状态(0:未领用,1:已领用)
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置使用状态(0:未领用,1:已领用)
	 *
	 * @param status
	 *            使用状态(0:未领用,1:已领用)
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取生成时间
	 *
	 * @return created_at - 生成时间
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * 设置生成时间
	 *
	 * @param createdAt
	 *            生成时间
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 获取活动ID
	 *
	 * @return coupon_setting_id - 活动ID
	 */
	public Integer getCouponSettingId() {
		return couponSettingId;
	}

	/**
	 * 设置活动ID
	 *
	 * @param couponSettingId
	 *            活动ID
	 */
	public void setCouponSettingId(Integer couponSettingId) {
		this.couponSettingId = couponSettingId;
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