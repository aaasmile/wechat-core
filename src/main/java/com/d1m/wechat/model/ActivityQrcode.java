package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "activity_qrcode")
public class ActivityQrcode {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 活动ID
	 */
	@Column(name = "activity_id")
	private Integer activityId;

	/**
	 * 渠道
	 */
	private String channel;

	/**
	 * 活动url
	 */
	@Column(name = "acty_url")
	private String actyUrl;

	/**
	 * 二维码图片url
	 */
	@Column(name = "qrcode_img_url")
	private String qrcodeImgUrl;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 状态(1:使用,0:删除)
	 */
	private Byte status;

	/**
	 * 创建人
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
	 * 获取渠道
	 *
	 * @return channel - 渠道
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * 设置渠道
	 *
	 * @param channel
	 *            渠道
	 */
	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	/**
	 * 获取活动url
	 *
	 * @return acty_url - 活动url
	 */
	public String getActyUrl() {
		return actyUrl;
	}

	/**
	 * 设置活动url
	 *
	 * @param acty_url
	 *            活动url
	 */
	public void setActyUrl(String actyUrl) {
		this.actyUrl = actyUrl == null ? null : actyUrl.trim();
	}

	/**
	 * 获取二维码图片url
	 *
	 * @return qrcode_img_url - 二维码图片url
	 */
	public String getQrcodeImgUrl() {
		return qrcodeImgUrl;
	}

	/**
	 * 设置二维码图片url
	 *
	 * @param qrcodeImgUrl
	 *            二维码图片url
	 */
	public void setQrcodeImgUrl(String qrcodeImgUrl) {
		this.qrcodeImgUrl = qrcodeImgUrl == null ? null : qrcodeImgUrl.trim();
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
	 * 获取状态(1:使用,0:删除)
	 *
	 * @return status - 状态(1:使用,0:删除)
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置状态(1:使用,0:删除)
	 *
	 * @param status
	 *            状态(1:使用,0:删除)
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取创建人
	 *
	 * @return creator_id - 创建人
	 */
	public Integer getCreatorId() {
		return creatorId;
	}

	/**
	 * 设置创建人
	 *
	 * @param creatorId
	 *            创建人
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