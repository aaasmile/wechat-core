package com.d1m.wechat.dto;

public class MemberTagDto {
	/**
	 * 主键ID
	 */
	private Integer id;

	private Integer memberMemberTagId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 会员标签类型
	 */
	private Integer memberTagTypeId;

	/**
	 * 创建用户ID
	 */
	private Integer creatorId;

	/**
	 * 创建时间
	 */
	private String createdAt;

	/**
	 * 公众号ID
	 */
	private Integer wechatId;

	private Integer ownedMemberCount;
	
	private Integer memberTagTypeParentId;
	
	private String memberTagTypeName;
	
	/**
	 * 获取创建时间
	 *
	 * @return created_at - 创建时间
	 */
	public String getCreatedAt() {
		return createdAt;
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
	 * 获取主键ID
	 *
	 * @return id - 主键ID
	 */
	public Integer getId() {
		return id;
	}

	public Integer getMemberMemberTagId() {
		return memberMemberTagId;
	}

	/**
	 * 获取会员标签类型
	 *
	 * @return member_tag_type_id - 会员标签类型
	 */
	public Integer getMemberTagTypeId() {
		return memberTagTypeId;
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
	 * 获取公众号ID
	 *
	 * @return wechat_id - 公众号ID
	 */
	public Integer getWechatId() {
		return wechatId;
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
	 * 设置创建用户ID
	 *
	 * @param creatorId
	 *            创建用户ID
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

	public void setMemberMemberTagId(Integer memberMemberTagId) {
		this.memberMemberTagId = memberMemberTagId;
	}

	/**
	 * 设置会员标签类型
	 *
	 * @param memberTagTypeId
	 *            会员标签类型
	 */
	public void setMemberTagTypeId(Integer memberTagTypeId) {
		this.memberTagTypeId = memberTagTypeId;
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
	 * 设置公众号ID
	 *
	 * @param wechatId
	 *            公众号ID
	 */
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Integer getOwnedMemberCount() {
		return ownedMemberCount;
	}

	public void setOwnedMemberCount(Integer ownedMemberCount) {
		this.ownedMemberCount = ownedMemberCount;
	}

	public Integer getMemberTagTypeParentId() {
		return memberTagTypeParentId;
	}

	public void setMemberTagTypeParentId(Integer memberTagTypeParentId) {
		this.memberTagTypeParentId = memberTagTypeParentId;
	}

	public String getMemberTagTypeName() {
		return memberTagTypeName;
	}

	public void setMemberTagTypeName(String memberTagTypeName) {
		this.memberTagTypeName = memberTagTypeName;
	}
	
	
}