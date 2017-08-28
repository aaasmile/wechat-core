package com.d1m.wechat.dto;

import java.util.List;

public class MemberTagTypeDto {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private String createdAt;

	/**
	 * 公众号ID
	 */
	private Integer wechatId;

	/**
	 * 创建用户ID
	 */
	private Integer creatorId;

	private int parentId;
	
	private List<MemberTagTypeDto> childTagTypeList;
	
	private List<MemberTagDto> tagList;
	
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
	 * 获取名称
	 *
	 * @return name - 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 *
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<MemberTagTypeDto> getChildTagTypeList() {
		return childTagTypeList;
	}

	public void setChildTagTypeList(List<MemberTagTypeDto> childTagTypeList) {
		this.childTagTypeList = childTagTypeList;
	}

	public List<MemberTagDto> getTagList() {
		return tagList;
	}

	public void setTagList(List<MemberTagDto> tagList) {
		this.tagList = tagList;
	}
	
	
}