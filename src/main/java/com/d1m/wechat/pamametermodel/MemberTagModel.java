package com.d1m.wechat.pamametermodel;

import java.util.List;

public class MemberTagModel extends BaseModel{

	private Integer id;

	private String name;

	private Integer memberTagTypeId;
	
	private List<Integer> ids;

	public Integer getId() {
		return id;
	}

	public Integer getMemberTagTypeId() {
		return memberTagTypeId;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMemberTagTypeId(Integer memberTagTypeId) {
		this.memberTagTypeId = memberTagTypeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}
