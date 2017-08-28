package com.d1m.wechat.dto;

import java.util.Date;
import java.util.List;

public class OfflineActivityDto {
	
	private Integer id;
	private String code;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private String sharePic;
	private String shareTitle;
	private String shareDescription;
	private Date createdAt;
	private Integer status;
	private List<BusinessItemDto> businessList;
	private List<SessionDto> sessionList;
	private List<Integer> businessIds;
	
	public Integer getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getSharePic() {
		return sharePic;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public String getShareDescription() {
		return shareDescription;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Integer getStatus() {
		return status;
	}
	public List<BusinessItemDto> getBusinessList() {
		return businessList;
	}
	public List<SessionDto> getSessionList() {
		return sessionList;
	}
	public List<Integer> getBusinessIds() {
		return businessIds;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setSharePic(String sharePic) {
		this.sharePic = sharePic;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setBusinessList(List<BusinessItemDto> businessList) {
		this.businessList = businessList;
	}
	public void setSessionList(List<SessionDto> sessionList) {
		this.sessionList = sessionList;
	}
	public void setBusinessIds(List<Integer> businessIds) {
		this.businessIds = businessIds;
	}
	
}
