package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 消息类型
 * 
 */
public enum MsgType {

	/**
	 * 接收普通消息、被动回复用户消息
	 */
	TEXT((byte) 6, "文本"),

	IMAGE((byte) 2, "图片"),

	VOICE((byte) 3, "语音"),

	VIDEO((byte) 4, "视频"),
	/**
	 * 接收普通消息、被动回复用户消息
	 */

	/**
	 * 接收普通消息
	 */
	SHORTVIDEO((byte) 5, "小视频"),

	LOCATION((byte) 7, "地理位置"),

	LINK((byte) 8, "链接消息"),
	/**
	 * 接收普通消息
	 */

	/**
	 * 被动回复用户消息
	 */
	MUSIC((byte) 9, "音乐"),

	MPNEWS((byte) 1, "图文"),
	
	NEWS((byte) 1, "图文"),

	WXCARD((byte) 11, "微信卡券"),
	/**
	 * 被动回复用户消息
	 */

	/**
	 * 接收事件推送
	 */
	EVENT((byte) 10, "事件"),
	WECHATNEWS((byte) 21, "微信图文"),
	DCRMNEWS((byte) 22, "非群发图文"),
	/**
	 * 接收事件推送
	 */
	;

	private byte value;

	private String name;

	/**
	 * @param value
	 * @param name
	 */
	private MsgType(byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public byte getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public static MsgType getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (MsgType msgType : MsgType.values()) {
			if (msgType.name().equalsIgnoreCase(name)) {
				return msgType;
			}
		}
		return null;
	}

	public static MsgType getByValue(Byte value) {
		if (value!=null) {
			for (MsgType msgType : MsgType.values()) {
				if (value.byteValue() == msgType.getValue()) {
					return msgType;
				}
			}
		}
		return null;
	}

}
