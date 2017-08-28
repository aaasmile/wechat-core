package com.d1m.wechat.util;

public interface Constants {

	String JPG = "jpg";

	String GIF = "gif";

	String PNG = "png";

	String BMP = "bmp";

	Integer SUBSCRIBE = 1;

	Integer UNSUBSCRIBE = 0;

	String IMAGE = "image";

	/**
	 * 图文图片：mediaUploadImg
	 */
	String MEDIAIMAGE = "mediaimage";

	/**
	 * 门店图片：outletUploadImg
	 */
	String OUTLETIMAGE = "outletimage";

	/**
	 * 线下活动图片：offlineActivityUploadImg
	 */
	String OFFLINEACTIVITYIMAGE = "offlineactivityimage";

	String NORMAL = "normal";

	String BUSINESS = "business";

	String USER = "user";

	String MATERIAL = "material";

	String VIDEO = "video";

	String VOICE = "voice";

	String QRCODE = "qrcode";

	String ACTIVITY = "activity";

	Integer MAX_UPLOAD_WX_ARTICLES_SIEZ = 8;

	Integer MAX_MASS_SIZE_PER_MONTH = 4;

	String SUPPORT_MULTIPLE_PEOPLE_LOGIN = "1";

	String TOKEN = "token";

	String HOLATOKEN = "holatoken";

	String LOGIN_URL = "/login";

	String WX_NOTIFY = "/notify";

	String WS_SOCKET = "/websocket";

	Integer CONVERSATION_HOUR_LIMIT = 48;

	String WECHAT = "wechat";


	/**
	 * Action Engine Effecct Code
	 */
	/** 加会员标签 */
	String ADD_MEMBER_TAG = "200";
	
	String MEMBER = "member";
	
	String ADD_MEMBER_TAG_BY_CSV = "csv";
	
	String OFFLINE_ACTIVITY = "offline_activity";
	
	String OFFLINEACTIVITYTOKEN = "offlineactivitytoken";

	interface Status {

        byte INVALID = 0;//状态:无效

        byte VALID = 1;//状态:有效
    }

    String UTF_8 = "UTF-8";

}
