package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("菜单报表参数")
public class MenuReportModel {
	@ApiModelProperty("组ID")
	private Integer groupId;
	@ApiModelProperty("状态")
	private Integer status;
	@ApiModelProperty("开始时间")
	private String start;
	@ApiModelProperty("结束时间")
	private String end;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
