package com.d1m.wechat.dto;

public class MassConversationResultDto {

	private Byte status;

	private Integer totalCount;

	private Integer filterCount;

	private Integer sendCount;

	private Integer errorCount;

	private String conditions;

	public String getConditions() {
		return conditions;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public Integer getFilterCount() {
		return filterCount;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public Byte getStatus() {
		return status;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public void setFilterCount(Integer filterCount) {
		this.filterCount = filterCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}