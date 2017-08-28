package com.d1m.wechat.dto;

import java.text.DecimalFormat;

public class ReportMenuBaseDto {

    private int id;
    private String name;
    private int groupId;
    private String groupName;
    private int count;
    private String date;
    private int parentId;
    private int userCount;
    private double peruser;
    private String parentName;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public double getPeruser() {
		return peruser;
	}
	public void setPeruser(double peruser) {
		DecimalFormat df = new DecimalFormat("######0.00");
		this.peruser = Double.parseDouble(df.format(peruser));
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
    
    
}
