package com.d1m.wechat.model.enums;

public enum BusinessStatus {

	DELETED((byte) 0, "删除"),

	INUSED((byte) 1, "使用"),

	PUSHED((byte) 1, "已发布"),

	NOTPUSHED((byte) 0, "未发布"),

	UPDATE((byte) 1, "更新中"),

	NOTUPDATE((byte) 0, "未更新"),

	CHECKSUCCESS((byte) 1, "审核成功"),

	CHECKFAIL((byte) 2, "审核失败"),

	CHECKING((byte) 0, "审核中"),

	NOTUPLOAD((byte) 3, "未上传"),

	;

	private byte value;

	private String name;

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
	 * @param code
	 * @param name
	 */
	private BusinessStatus(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
