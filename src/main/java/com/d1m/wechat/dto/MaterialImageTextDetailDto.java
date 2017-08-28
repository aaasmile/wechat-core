package com.d1m.wechat.dto;

public class MaterialImageTextDetailDto {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 图片分组名称
	 */
	private String title;

	/**
	 * 创建时间
	 */
	private String createdAt;

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
	 * 获取标题
	 *
	 * @return title - 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 *
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

}