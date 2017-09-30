package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 菜单的响应动作类型
 * 1、click：点击推事件
 * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event	的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
 * 2、view：跳转URL
 * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
 * 3、scancode_push：扫码推事件
 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
 * 4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
 * 5、pic_sysphoto：弹出系统拍照发图
 * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
 * 6、pic_photo_or_album：弹出拍照或者相册发图
 * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
 * 7、pic_weixin：弹出微信相册发图器
 * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
 * 8、location_select：弹出地理位置选择器
 * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
 * 9、media_id：下发消息（除文本消息）
 * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 * 10、view_limited：跳转图文消息URL
 * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 */
public enum MenuType {

    PARENT((byte) 0),
    CLICK((byte) 1),
    VIEW((byte) 2),
    SCANCODE_PUSH((byte) 3),
    SCANCODE_WAITMSG((byte) 4),
    PIC_SYSPHOTO((byte) 5),
    PIC_PHOTO_OR_ALBUM((byte) 6),
    PIC_WEIXIN((byte) 7),
    LOCATION_SELECT((byte) 8),
    MEDIA_ID((byte) 9),
    VIEW_LIMITED((byte) 10),
	MINIPROGRAM((byte) 11),

    UNSUPPORTED((byte) -1);

	private final byte value;

	/**
	 * @param value
	 */
    MenuType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static MenuType getByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (MenuType menuType : MenuType.values()) {
			if (value.equals(menuType.getValue())) {
				return menuType;
			}
		}
		return null;
	}

	public static MenuType getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (MenuType menuType : MenuType.values()) {
			if (StringUtils.equalsIgnoreCase(name, menuType.name())) {
				return menuType;
			}
		}
		return null;
	}

    public static byte valueByName(String name) {
        try {
            return valueOf(name.toUpperCase()).value;
        } catch (NullPointerException e) {//把name为null的参数解析为父菜单(0)
            return PARENT.getValue();
        } catch (IllegalArgumentException e) {//把其他参数解析为UNSUPPORTED(-1)
            return UNSUPPORTED.getValue();
        }
    }

    //public static void main(String[] args) {
    //    System.out.println(MenuType.valueByName("click"));
    //    System.out.println(MenuType.valueByName("none"));
    //    System.out.println(MenuType.valueByName(null));
    //    System.out.println(MenuType.valueByName(""));
    //    System.out.println(MenuType.valueByName("view_limited"));
    //}
}
