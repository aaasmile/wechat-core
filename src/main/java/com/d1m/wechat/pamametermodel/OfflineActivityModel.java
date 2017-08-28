package com.d1m.wechat.pamametermodel;

import java.util.Date;
import java.util.List;

public class OfflineActivityModel extends BaseModel{
	
	private String query;
	private String code;
	private String name;
	private Integer status;
	private Integer activityStatus;
	private Date start;
	private Date end;
	private String sharePic;
	private List<Integer> businessIds;
	private List<Integer> sessionIds;
	
	public String getQuery() {
		return query;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public Date getStart() {
		return start;
	}
	public Date getEnd() {
		return end;
	}
	public String getSharePic() {
		return sharePic;
	}
	public List<Integer> getBusinessIds() {
		return businessIds;
	}
	public List<Integer> getSessionIds() {
		return sessionIds;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public void setSharePic(String sharePic) {
		this.sharePic = sharePic;
	}
	public void setBusinessIds(List<Integer> businessIds) {
		this.businessIds = businessIds;
	}
	public void setSessionIds(List<Integer> sessionIds) {
		this.sessionIds = sessionIds;
	}
	
}
