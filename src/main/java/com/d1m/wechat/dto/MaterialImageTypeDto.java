package com.d1m.wechat.dto;


public class MaterialImageTypeDto {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 图片分组名称
	 */
	private String name;

	/**
	 * 父级ID
	 */
	private Integer parentId;

	/**
	 * 创建时间
	 */
	private String createdAt;

	/**
	 * 创建人
	 */
	private Integer creatorId;

	/**
	 * 公众号ID
	 */
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
	 * 获取图片分组名称
	 *
	 * @return name - 图片分组名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置图片分组名称
	 *
	 * @param name
	 *            图片分组名称
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * 获取父级ID
	 *
	 * @return parent_id - 父级ID
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置父级ID
	 *
	 * @param parentId
	 *            父级ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取创建时间
	 *
	 * @return created_at - 创建时间
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createdAt
	 *            创建时间
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
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