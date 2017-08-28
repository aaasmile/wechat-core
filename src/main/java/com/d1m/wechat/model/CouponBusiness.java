package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "coupon_business")
public class CouponBusiness {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 活动ID
	 */
	@Column(name = "coupon_setting_id")
	private Integer couponSettingId;

	/**
	 * 门店ID
	 */
	@Column(name = "business_id")
	private Integer businessId;

	/**
	 * 0：领礼门店；1：核销门店
	 */
	private Byte type;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 创建用户ID
	 */
	@Column(name = "creator_id")
	private Integer creatorId;

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
	 * 获取门店ID
	 *
	 * @return business_id - 门店ID
	 */
	public Integer getBusinessId() {
		return businessId;
	}

	/**
	 * 设置门店ID
	 *
	 * @param businessId
	 *            门店ID
	 */
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	/**
	 * 获取0：领礼门店；1：核销门店
	 *
	 * @return type - 0：领礼门店；1：核销门店
	 */
	public Byte getType() {
		return type;
	}

	/**
	 * 设置0：领礼门店；1：核销门店
	 *
	 * @param type
	 *            0：领礼门店；1：核销门店
	 */
	public void setType(Byte type) {
		this.type = type;
	}

	/**
	 * 获取创建时间
	 *
	 * @return created_at - 创建时间
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createdAt
	 *            创建时间
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 获取创建用户ID
	 *
	 * @return creator_id - 创建用户ID
	 */
	public Integer getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置创建用户ID
	 *
	 * @param creatorId
	 *            创建用户ID
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
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