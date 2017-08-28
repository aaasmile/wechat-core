package com.d1m.wechat.model.enums;

public enum TaskStatus {
	//任务状态：
	//None：Trigger已经完成，且不会在执行，或者找不到该触发器，或者Trigger已经被删除
	//NORMAL:正常状态 
	//PAUSED：暂停状态 
	//COMPLETE：触发器完成，但是任务可能还正在执行中 
	//BLOCKED：线程阻塞状态 
	//ERROR：出现错误 
	
	//数据库状态：
	//STARTUP：启动状态
	//SHUTDOWN: 停止状态
	None("None", "None"),
	
	NORMAL("NORMAL", "正常"),
	
	PAUSED("PAUSED", "暂停"),

	COMPLETE("COMPLETE", "完成"), 
	
	BLOCKED("BLOCKED", "阻塞"), 
	
	ERROR("ERROR", "错误"), 
	
	STARTUP("STARTUP", "启动"), 
	
	SHUTDOWN("SHUTDOWN", "停止"), 
	;

	private String value;

	private String name;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param value
	 * @param name
	 */
	private TaskStatus(String value, String name) {
		this.value = value;
		this.name = name;
	}
}
