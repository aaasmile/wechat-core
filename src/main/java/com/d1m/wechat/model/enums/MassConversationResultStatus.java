package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

public enum MassConversationResultStatus {

	WAIT_AUDIT((byte) 13, "", "待审核"),

	AUDIT_PASS((byte) 14, "", "审核通过"),

	AUDIT_NOT_PASS((byte) 15, "", "审核不通过"),

	WAIT_SEND((byte) 16, "", "待发送"),

	SENDING((byte) 1, "", "发送中"),

	SEND_SUCCESS((byte) 2, "send success", "发送成功"),

	ERR_10001((byte) 3, "err(10001)", "涉嫌广告"),

	ERR_20001((byte) 4, "err(20001)", "涉嫌政治"),

	ERR_20002((byte) 5, "err(20002)", "涉嫌色情"),

	ERR_20004((byte) 6, "err(20004)", "涉嫌社会"),

	ERR_20006((byte) 7, "err(20006)", "涉嫌违法犯罪"),

	ERR_20008((byte) 8, "err(20008)", "涉嫌欺诈"),

	ERR_20013((byte) 9, "err(20013)", "涉嫌版权 "),

	ERR_21000((byte) 10, "err(21000)", "涉嫌其他"),

	ERR_22000((byte) 11, "err(22000)", "涉嫌互推(互相宣传)"),

	SEND_FAIL((byte) 12, "send fail", "发送失败"),

	;

	private byte value;

	private String desc;

	private String name;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public void setCode(byte value) {
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
	 * @param desc
	 * @param name
	 */
	private MassConversationResultStatus(byte value, String desc, String name) {
		this.value = value;
		this.desc = desc;
		this.name = name;
	}

	public static MassConversationResultStatus getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (MassConversationResultStatus status : MassConversationResultStatus
				.values()) {
			if (StringUtils.equalsIgnoreCase(status.name(), name)) {
				return status;
			}
		}
		return null;
	}

	public static MassConversationResultStatus getByDesc(String desc) {
		if (StringUtils.isBlank(desc)) {
			return null;
		}
		for (MassConversationResultStatus status : MassConversationResultStatus
				.values()) {
			if (StringUtils.equalsIgnoreCase(status.getDesc(), desc)) {
				return status;
			}
		}
		return null;
	}

}
