package com.d1m.wechat.pamametermodel;

import java.util.Date;

public class AddMemberTagTaskModel extends BaseModel{
	
	private String task;
	
	private Date start;
	
	private Date end;
	
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
