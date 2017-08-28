package com.d1m.wechat.pamametermodel;

import java.util.List;

public class ActivityModel extends BaseModel {

	private String query;

	private Integer id;

	private String name;

	private String startDate;

	private String endDate;

	private String description;

	private String code;

	private String sharePic;

	private String shareTitle;

	private String shareDescription;

	private String actyH5;

	private String actyOfflineH5;

	private List<Integer> couponSettings;

	private Byte activityType;

	private Byte activityStatus;

	public Byte getActivityStatus() {
		return activityStatus;
	}

	public Byte getActivityType() {
		return activityType;
	}

	public String getActyH5() {
		return actyH5;
	}

	public String getActyOfflineH5() {
		return actyOfflineH5;
	}

	public String getCode() {
		return code;
	}

	public List<Integer> getCouponSettings() {
		return couponSettings;
	}

	public String getDescription() {
		return description;
	}

	public String getEndDate() {
		return endDate;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getQuery() {
		return query;
	}

	public String getShareDescription() {
		return shareDescription;
	}

	public String getSharePic() {
		return sharePic;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setActivityStatus(Byte activityStatus) {
		this.activityStatus = activityStatus;
	}

	public void setActivityType(Byte activityType) {
		this.activityType = activityType;
	}

	public void setActyH5(String actyH5) {
		this.actyH5 = actyH5;
	}

	public void setActyOfflineH5(String actyOfflineH5) {
		this.actyOfflineH5 = actyOfflineH5;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCouponSettings(List<Integer> couponSettings) {
		this.couponSettings = couponSettings;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setShareDescription(String shareDescription) {
		this.shareDescription = shareDescription;
	}

	public void setSharePic(String sharePic) {
		this.sharePic = sharePic;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
