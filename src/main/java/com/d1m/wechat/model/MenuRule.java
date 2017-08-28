package com.d1m.wechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_rule")
public class MenuRule {
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户组ID
	 */
	@Column(name = "group_id")
	private Integer groupId;

	/**
	 * 性别(1:男性，2:女性，0:未知)
	 */
	private Byte sex;

	/**
	 * 客户端版本
	 */
	@Column(name = "client_platform_type")
	private Byte clientPlatformType;

	/**
	 * 国家
	 */
	private Integer country;

	/**
	 * 省份
	 */
	private Integer province;

	/**
	 * 城市
	 */
	private Integer city;

	/**
	 * 语言
	 */
	private Byte language;

	/**
	 * 微信标签ID
	 */
	@Column(name = "tag_id")
	private Integer tagId;

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
	 * 菜单组ID
	 */
	@Column(name = "menu_group_id")
	private Integer menuGroupId;

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
	 * 获取用户组ID
	 *
	 * @return group_id - 用户组ID
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * 设置用户组ID
	 *
	 * @param groupId
	 *            用户组ID
	 */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	/**
	 * 获取性别(1:男性，2:女性，0:未知)
	 *
	 * @return sex - 性别(1:男性，2:女性，0:未知)
	 */
	public Byte getSex() {
		return sex;
	}

	/**
	 * 设置性别(1:男性，2:女性，0:未知)
	 *
	 * @param sex
	 *            性别(1:男性，2:女性，0:未知)
	 */
	public void setSex(Byte sex) {
		this.sex = sex;
	}

	/**
	 * 获取客户端版本
	 *
	 * @return client_platform_type - 客户端版本
	 */
	public Byte getClientPlatformType() {
		return clientPlatformType;
	}

	/**
	 * 设置客户端版本
	 *
	 * @param clientPlatformType
	 *            客户端版本
	 */
	public void setClientPlatformType(Byte clientPlatformType) {
		this.clientPlatformType = clientPlatformType;
	}

	/**
	 * 获取国家
	 *
	 * @return country - 国家
	 */
	public Integer getCountry() {
		return country;
	}

	/**
	 * 设置国家
	 *
	 * @param country
	 *            国家
	 */
	public void setCountry(Integer country) {
		this.country = country;
	}

	/**
	 * 获取省份
	 *
	 * @return province - 省份
	 */
	public Integer getProvince() {
		return province;
	}

	/**
	 * 设置省份
	 *
	 * @param province
	 *            省份
	 */
	public void setProvince(Integer province) {
		this.province = province;
	}

	/**
	 * 获取城市
	 *
	 * @return city - 城市
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * 设置城市
	 *
	 * @param city
	 *            城市
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	/**
	 * 获取语言
	 *
	 * @return language - 语言
	 */
	public Byte getLanguage() {
		return language;
	}

	/**
	 * 设置语言
	 *
	 * @param language
	 *            语言
	 */
	public void setLanguage(Byte language) {
		this.language = language;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
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

	/**
	 * 获取菜单组ID
	 *
	 * @return menu_group_id - 菜单组ID
	 */
	public Integer getMenuGroupId() {
		return menuGroupId;
	}

	/**
	 * 设置菜单组ID
	 *
	 * @param menuGroupId
	 *            菜单组ID
	 */
	public void setMenuGroupId(Integer menuGroupId) {
		this.menuGroupId = menuGroupId;
	}
}