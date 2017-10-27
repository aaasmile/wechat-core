package com.d1m.wechat.pamametermodel;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("增加会员标签任务参数")
public class AddMemberTagTaskModel extends BaseModel{
	
	@ApiModelProperty("任务")
	private String task;
	
	@ApiModelProperty("开始时间")
	private Date start;
	
	@ApiModelProperty("结束时间")
	private Date end;
	
	@ApiModelProperty("状态")
	private Integer status;

	public String getTask() {
		return task;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public Integer getStatus() {
		return status;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
