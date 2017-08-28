package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "activity_share")
public class ActivityShare {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 会员ID
	 */
	@Column(name = "member_id")
	private Integer memberId;

	/**
	 * 活动ID
	 */
	@Column(name = "activity_id")
	private Integer activityId;

	/**
	 * 分享时间
	 */
	@Column(name = "share_at")
	private Date shareAt;

	/**
	 * 类型(1:朋友圈,2:朋友,3:QQ,4:腾讯博客,5:QQ空间)
	 */
	private Byte type;

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
	 * 获取会员ID
	 *
	 * @return member_id - 会员ID
	 */
	public Integer getMemberId() {
		return memberId;
	}

	/**
	 * 设置会员ID
	 *
	 * @param memberId
	 *            会员ID
	 */
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
	 * 获取分享时间
	 *
	 * @return share_at - 分享时间
	 */
	public Date getShareAt() {
		return shareAt;
	}

	/**
	 * 设置分享时间
	 *
	 * @param shareAt
	 *            分享时间
	 */
	public void setShareAt(Date shareAt) {
		this.shareAt = shareAt;
	}

	/**
	 * 获取类型(1:朋友圈,2:朋友,3:QQ,4:腾讯博客,5:QQ空间)
	 *
	 * @return type - 类型(1:朋友圈,2:朋友,3:QQ,4:腾讯博客,5:QQ空间)
	 */
	public Byte getType() {
		return type;
	}

	/**
	 * 设置类型(1:朋友圈,2:朋友,3:QQ,4:腾讯博客,5:QQ空间)
	 *
	 * @param type
	 *            类型(1:朋友圈,2:朋友,3:QQ,4:腾讯博客,5:QQ空间)
	 */
	public void setType(Byte type) {
		this.type = type;
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