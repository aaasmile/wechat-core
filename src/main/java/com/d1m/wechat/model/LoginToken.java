package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "login_token")
public class LoginToken {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * token
	 */
	private String token;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 创建时间
	 */
	@Column(name = "created_at")
	private Date createdAt;

	/**
	 * 公众号ID
	 */
	@Column(name = "wechat_id")
	private Integer wechatId;

	/**
	 * 失效时间
	 */
	@Column(name = "expired_at")
	private Date expiredAt;

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
	 * 获取token
	 *
	 * @return token - token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置token
	 *
	 * @param token
	 *            token
	 */
	public void setToken(String token) {
		this.token = token == null ? null : token.trim();
	}

	/**
	 * 获取用户ID
	 *
	 * @return user_id - 用户ID
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 设置用户ID
	 *
	 * @param userId
	 *            用户ID
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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

	/**
	 * 获取失效时间
	 *
	 * @return expired_at - 失效时间
	 */
	public Date getExpiredAt() {
		return expiredAt;
	}

	/**
	 * 设置失效时间
	 *
	 * @param expiredAt
	 *            失效时间
	 */
	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}
}