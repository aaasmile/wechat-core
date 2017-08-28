package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Reply {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 回复类型
	 */
	@Column(name = "reply_type")
	private Byte replyType;

	/**
	 * 匹配模式
	 */
	@Column(name = "match_mode")
	private Byte matchMode;

	/**
	 * 回复关键字
	 */
	@Column(name = "reply_key")
	private String replyKey;

	/**
	 * 状态(0:删除,1:使用)
	 */
	private Byte status;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 创建者
	 */
	@Column(name = "creator_id")
	private Integer creatorId;

	/**
	 * 公众号ID
	 */
	@Column(name = "wechat_id")
	private Integer wechatId;

	/**
	 * 权重
	 */
	private Integer weight;

	/**
	 * 获取创建时间
	 *
	 * @return created_at - 创建时间
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * 获取创建者
	 *
	 * @return creator_id - 创建者
	 */
	public Integer getCreatorId() {
		return creatorId;
	}

	/**
	 * 获取主键ID
	 *
	 * @return id - 主键ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 获取匹配模式
	 *
	 * @return matchMode - 匹配模式
	 */
	public Byte getMatchMode() {
		return matchMode;
	}

	/**
	 * 获取回复关键字
	 *
	 * @return reply_key - 回复关键字
	 */
	public String getReplyKey() {
		return replyKey;
	}

	/**
	 * 获取回复类型
	 *
	 * @return reply_type - 回复类型
	 */
	public Byte getReplyType() {
		return replyType;
	}

	/**
	 * 获取状态(0:删除,1:使用)
	 *
	 * @return status - 状态(0:删除,1:使用)
	 */
	public Byte getStatus() {
		return status;
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
	 * 获取权重
	 *
	 * @return weight - 权重
	 */
	public Integer getWeight() {
		return weight;
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
	 * 设置创建者
	 *
	 * @param creatorId
	 *            创建者
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
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
	 * 设置匹配模式
	 *
	 * @return matchMode - 匹配模式
	 */
	public void setMatchMode(Byte matchMode) {
		this.matchMode = matchMode;
	}

	/**
	 * 设置回复关键字
	 *
	 * @param replyKey
	 *            回复关键字
	 */
	public void setReplyKey(String replyKey) {
		this.replyKey = replyKey == null ? null : replyKey.trim();
	}

	/**
	 * 设置回复类型
	 *
	 * @param replyType
	 *            回复类型
	 */
	public void setReplyType(Byte replyType) {
		this.replyType = replyType;
	}

	/**
	 * 设置状态(0:删除,1:使用)
	 *
	 * @return status - 状态(0:删除,1:使用)
	 */
	public void setStatus(Byte status) {
		this.status = status;
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

	/**
	 * 设置权重
	 *
	 * @param weight
	 *            权重
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}