package com.d1m.wechat.dto;

import java.util.Date;

import com.d1m.wechat.model.enums.ActivityStatus;

public class ActivityDto {

	private Integer id;

	private String name;

	private String sharePic;

	private Date createdAt;

	private Date startDate;

	private Date endDate;

	private String code;

	private String description;

	private String shareTitle;

	private String shareDescription;

	private String actyH5;

	private String actyOfflineH5;

	private Byte status;

	private String h5;

	private Byte activityType;

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getDescription() {
		return description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getH5() {
		return h5;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
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

	public Date getStartDate() {
		return startDate;
	}

	public Byte getStatus() {
		Byte status = null;
		Date current = new Date();
		if (current.compareTo(startDate) < 0) {
			status = ActivityStatus.NOT_START.getValue();
		} else if (current.compareTo(endDate) > 0) {
			status = ActivityStatus.END.getValue();
		} else {
			status = ActivityStatus.START.getValue();
		}
		return status;
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

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setH5(String h5) {
		this.h5 = h5;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
