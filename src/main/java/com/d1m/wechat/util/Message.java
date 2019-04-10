package com.d1m.wechat.util;

import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;

public enum Message {

    /**
     * common
     */
    SYSTEM_ERROR(0, "系统异常"),

    SUCCESS(1, "操作成功"),

    NOT_LOGGED_IN(401, "未登录"),

    NO_PERMISSION(403, "无权限"),

    LOGOUT_SUCCESS(1, "退出成功"),

    ILLEGAL_REQUEST(105, "非法请求"),

    MISSING_PARAMTERS(106, "缺少参数"),

    JOB_RUNNING(107, "任务执行中，请稍候在试"),

    /**
     * member
     */
    MEMBER_LIST_SUCCESS(1, "获取会员列表成功"),

    MEMBER_GET_SUCCESS(1, "获取会员详情成功"),

    MEMBER_NOT_EXIST(10001, "会员不存在"),

    MEMBER_PULL_WX_SUCCESS(1, "拉取微信会员信息成功"),

    MEMBER_ADD_TAG_SUCCESS(1, "添加会员标签成功"),

    MEMBER_ID_NOT_EMPTY(10002, "会员ID不能为空"),

    MEMBER_NOT_BLANK(10003, "会员不能为空"),

    MEMBER_SYNC_WX_TAG_SUCCESS(1, "会员同步微信标签成功"),

    MEMBER_SELECTED_ALL_OFFLINE(10005, "强推消息所选会员都不在线"),

    MEMBER_ADD_TAG_OPERATOR_ONLY(10005, "添加会员标签正在执行中"),

    /**
     * member_tag_type
     */
    MEMBER_TAG_TYPE_LIST_SUCCESS(1, "获取会员标签类型列表成功"),

    MEMBER_TAG_TYPE_CREATE_SUCCESS(1, "创建会员标签类型成功"),

    MEMBER_TAG_TYPE_DELETE_SUCCESS(1, "删除会员标签类型成功"),

    MEMBER_TAG_TYPE_UPDATE_SUCCESS(1, "更新会员标签类型成功"),

    MEMBER_TAG_TYPE_NAME_NOT_EMPTY(11001, "会员标签类型名称不能为空"),

    MEMBER_TAG_TYPE_NAME_EXIST(11002, "会员标签类型名称已存在"),

    MEMBER_TAG_TYPE_ID_NOT_EMPTY(11003, "会员标签类型ID不能为空"),

    MEMBER_TAG_TYPE_NOT_EXIST(11004, "会员标签类型不存在"),

    MEMBER_TAG_TYPE_NOT_BLANK(11005, "会员标签类型不能为空"),

    MEMBER_TAG_TYPE_OWN_TAG(11006, "此会员标签类型已存在标签"),

    /**
     * member_tag
     */
    MEMBER_TAG_LIST_SUCCESS(1, "获取会员标签列表成功"),

    MEMBER_TAG_CREATE_SUCCESS(1, "创建会员标签成功"),

    MEMBER_TAG_DELETE_SUCCESS(1, "删除会员标签成功"),

    MEMBER_TAG_UPDATE_SUCCESS(1, "更新会员标签成功"),

    MEMBER_TAG_NAME_NOT_EMPTY(12001, "会员标签名称不能为空"),

    MEMBER_TAG_NAME_EXIST(12002, "会员标签成已存在"),

    MEMBER_TAG_ID_NOT_EMPTY(12003, "会员标签ID不能为空"),

    MEMBER_TAG_NOT_EXIST(12004, "会员标签不存在"),

    MEMBER_TAG_NOT_BLANK(12005, "会员标签不能为空"),

    MEMBER_ADD_TAG_BY_CSV_ERROR(12006, "会员标签CSV批量导入失败"),

    MEMBER_TAG_TASK_LIST_SUCCESS(1, "会员标签CSV批量导入成功"),

    MEMBER_TAG_OWN_MEMBER(12007, "会员标签已有会员标注"),

    MEMBER_TAG_MOVE_SUCCESS(1, "会员标签移动成功"),

    MEMBER_TAG_SEARCH_SUCCESS(1, "会员标签查询成功"),
    MEMBER_TAG_BATCH_FAIL(1022, "批量添加会员标签失败"),

    /**
     * qrcode
     */
    QRCODE_CREATE_SUCCESS(1, "创建二维码成功"),

    QRCODE_UPDATE_SUCCESS(1, "更新二维码成功"),

    QRCODE_DELETE_SUCCESS(1, "删除二维码成功"),

    QRCODE_LIST_SUCCESS(1, "获取二维码列表成功"),

    QRCODE_GET_SUCCESS(1, "获取二维码详情成功"),

    QRCODE_NAME_NOT_BLANK(13001, "二维码名称不能为空"),

    QRCODE_NAME_EXIST(13002, "二维码名称已存在"),

    QRCODE_ID_NOT_BLANK(13005, "二维码ID不能为空"),

    QRCODE_NOT_EXIST(13006, "二维码不存在"),

    QRCODE_ACTION_ENGINE_CREATE_SUCCESS(1, "创建二维码行为规则成功"),

    QRCODE_ACTION_ENGINE_UPDATE_SUCCESS(1, "更新二维码行为规则成功"),

    QRCODE_ACTION_ENGINE_DELETE_SUCCESS(1, "删除二维码行为规则成功"),

    QRCODE_ACTION_ENGINE_NOT_BLANK(13007, "二维码行为规则不能为空"),

    QRCODE_ACTION_ENGINE_NOT_EXIST(13008, "二维码行为规则不存在"),

    QRCODE_ACTION_ENGINE_LIST_SUCCESS(1, "获取二维码行为规则列表成功"),

    QRCODE_ACTION_ENGINE_ID_NOT_BLANK(1, "二维码行为规则ID不能为空"),

    QRCODE_SCENE_NOT_BLANK(13009, "二维码场景ID不能为空"),

    QRCODE_SCENE_NOT_REPEAT(13010, "二维码场景ID不能重复"),

    QRCODE_INIT_SUCCESS(1, "二维码初始化成功"),

    /**
     * qrcodeType
     */
    QRCODE_TYPE_CREATE_SUCCESS(1, "创建二维码类型成功"),

    QRCODE_TYPE_UPDATE_SUCCESS(1, "更新二维码类型成功"),

    QRCODE_TYPE_DELETE_SUCCESS(1, "删除二维码类型成功"),

    QRCODE_TYPE_LIST_SUCCESS(1, "获取二维码类型列表成功"),

    QRCODE_TYPE_NAME_NOT_BLANK(14001, "二维码类型名称不能为空"),

    QRCODE_TYPE_NAME_EXIST(14002, "二维码类型名称已存在"),

    QRCODE_TYPE_ID_NOT_BLANK(14003, "二维码类型ID不能为空"),

    QRCODE_TYPE_NOT_EXIST(14004, "二维码类型不存在"),

    QRCODE_TYPE_PARENT_NOT_EXIST(14005, "二维码类型父节点不存在"),

    QRCODE_QRCODE_TYPE_NOT_EXIST(14006, "二维码类型不存在"),

    /**
     * business 门店
     */
    BUSINESS_LIST_SUCCESS(1, "获取门店列表成功"),

    BUSINESS_CREATE_SUCCESS(1, "创建门店成功"),

    BUSINESS_DELETE_SUCCESS(1, "删除门店成功"),

    BUSINESS_UPDATE_SUCCESS(1, "更新门店成功"),

    BUSINESS_GET_SUCCESS(1, "获取门店详情成功"),

    BUSINESS_NAME_NOT_BLANK(15001, "门店名称不能为空"),

    BUSINESS_BRANCH_NAME_NOT_BLANK(15002, "门店分店名称不能为空"),

    BUSINESS_ADDRESS_NOT_BLANK(15003, "门店地址不能为空"),

    BUSINESS_TELEPHONE_NOT_BLANK(15004, "门店电话不能为空"),

    BUSINESS_NAME_NOT_EQUALS_BRANCH_NAME(15005, "门店名称不能和分店名称一样"),

    BUSINESS_ID_NOT_BLANK(15006, "门店ID不能为空"),

    BUSINESS_NOT_EXIST(15007, "门店不存在"),

    BUSINESS_CATEGORY_LIST_SUCCESS(1, "获取门店分类列表成功"),

    BUSINESS_CATEGORY_CREATE_SUCCESS(1, "创建门店分类列表成功"),

    BUSINESS_CATEGORY_NAME_EXIST(15008, "门店分类名称已存在"),

    BUSINESS_CATEGORY_NAME_NOT_BLANK(15009, "门店分类名称不能为空"),

    BUSINESS_CATEGORY_ID_NOT_BLANK(15010, "门店分类ID不能为空"),

    BUSINESS_CATEGORY_NOT_EXIST(15011, "门店分类不存在"),

    BUSINESS_NAME_EXIST(15012, "门店名称已存在"),

    BUSINESS_CODE_EXIST(15013, "门店编号已存在"),

    BUSINESS_WEIXIN_PHOTO_UPLOAD_FAIL(15014, "门店微信上传图片失败"),

    BUSINESS_WEIXIN_PUBLISE_FAIL(15015, "微信门店发布失败"),

    BUSINESS_WEXIN_UPDATE_FAIL(15016, "微信门店修改信息失败"),

    BUSINESS_WEXIN_DELETE_FAIL(15017, "微信门店删除失败"),

    BUSINESS_FETCH_BAIDU_GEOAPI_FAIL(15018, "百度GEO API异常"),

    BUSINESS_FETCH_BAIDU_ADDRAPI_FAIL(15019, "百度ADDR API异常"),

    BUSINESS_TRANSFER_QQ_GEOAPI_FAIL(15020, "QQ GEO 转换异常"),
    /**
     * material
     */
    MATERIAL_IMAGE_LIST_SUCCESS(1, "获取素材图片列表成功"),

    MATERIAL_IMAGE_TYPE_LIST_SUCCESS(1, "获取素材图片类型列表成功"),

    MATERIAL_IMAGE_TEXT_LIST_SUCCESS(1, "获取素材图文列表成功"),

    MATERIAL_IMAGE_TEXT_GET_SUCCESS(1, "获取图文详情成功"),

    MATERIAL_IMAGE_TEXT_DETAIL_LIST_SUCCESS(1, "获取图文详情列表成功"),

    MATERIAL_IMAGE_NAME_NOT_BLANK(16001, "素材图片名称不能为空"),

    MATERIAL_IMAGE_TEXT_NOT_BLANK(16002, "素材图文详情不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_TITLE_NOT_BLANK(16003, "素材图文详情标题不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_CONTENT_NOT_BLANK(16004, "素材图文详情内容不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_THUMB_MEDIA_NOT_BLANK(16005, "素材图文详情封面不能为空"),

    MATERIAL_IMAGE_TEXT_CREATE_SUCCESS(1, "创建素材图文成功"),

    MATERIAL_IMAGE_TEXT_DETAIL_CONTENT_SOURCE_URL_NOT_BLANK(16006,
            "素材图文详情原文链接不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_ID_NOT_BLANK(16007, "素材图文详情ID不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_NOT_BELONGS_TO_MATERIAL(16008, "素材图文没有该详情"),

    MATERIAL_IMAGE_TEXT_UPDATE_SUCCESS(1, "更新素材图文成功"),

    MATERIAL_NOT_EXIST(16009, "素材不存在"),

    MATERIAL_IMAGE_NOT_EXIST(16010, "素材封面不存在"),

    MATERIAL_IMAGE_TYPE_UNKNOWN(16011, "未知的素材类型"),

    MATERIAL_IMAGE_RENAME_SUCCESS(1, "重命名素材图片成功"),

    MATERIAL_IMAGE_DELETE_SUCCESS(1, "删除素材图片成功"),

    MATERIAL_ID_NOT_BLANK(16011, "素材ID不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_NOT_EXIST(16012, "素材图文详情不存在"),

    MATERIAL_IMAGE_TEXT_DELETE_SUCCESS(1, "删除素材图文成功"),

    MATERIAL_IMAGE_TEXT_DETAIL_DELETE_SUCCESS(1, "删除图文详情成功"),

    MATERIAL_MINI_PROGRAM_CREATE_SUCCESS(1, "创建小程序成功"),

    MATERIAL_MINI_PROGRAM_UPDATE_SUCCESS(1, "更新小程序成功"),

    MATERIAL_MINI_PROGRAM_DELETE_SUCCESS(1, "删除小程序成功"),

    MATERIAL_MINI_PROGRAM_SEARCH_SUCCESS(1, "查询小程序成功"),

    MATERIAL_IMAGE_TEXT_PUSH_WX_SUCCESS(1, "素材图文推送微信成功"),

    MATERIAL_PULL_COMPLETE(1, "素材同步完成"),

    MENU_PULL_COMPLETE(1, "菜单同步完成"),

    MEMBER_PULL_COMPLETE(1, "会员同步完成"),

    MATERIAL_TEXT_DETAIL_NOT_EXIST(16013, "素材文本不存在"),

    MATERIAL_NOT_PUSH_TO_WX(16014, "素材还未同步至微信"),

    MATERIAL_IMAGE_NOT_BLANK(16015, "素材图片不能为空"),

    MATERIAL_IMAGE_TEXT_DETAIL_NOT_BLANK(16016, "素材图文详情不能为空"),

    MATERIAL_NOT_EXIST_IN_WX(16017, "微信不存在该素材"),

    MATERIAL_PREVIEW_SUCCESS(1, "微信预览素材成功"),

    MATERIAL_UNABLE_DELETE(16020, "此图片正在被使用中，无法删除！"),

    MATERIAL_WX_NOT_DELETE(16021, "DCRM已删除{0}微信图片删除失败，请在微信平台手动删除！"),

    /**
     * action_engine
     */
    ACTION_ENGINE_NAME_NOT_BLANK(17001, "行为规则名称不能为空"),

    /**
     * menu_group
     */
    MENU_GROUP_NAME_NOT_BLANK(18001, "菜单组名称不能为空"),

    MENU_GROUP_MENU_NOT_BLANK(18002, "菜单组菜单不能为空"),

    MENU_GROUP_DEFAULT_NOT_BLANK(18003, "默认菜单组不能为空"),

    MENU_GROUP_ID_NOT_BLANK(18004, "菜单组ID不能为空"),

    MENU_GROUP_DEFAULT_ONLY_EXIST_ONE(18005, "默认菜单组只能存在一个"),

    MENU_GROUP_DEFAULT_EXIST(18006, "默认菜单组已存在"),

    MENU_GROUP_NOT_EXIST(18007, "菜单组不存在"),

    MENU_GROUP_FIRST_LEVEL_MENU_MUST_LESS_3(18008, "一级菜单个数最多3个"),

    MENU_GROUP_SECOND_LEVEL_MENU_MUST_LESS_5(18009, "二级菜单个数最多5个"),

    MENU_GROUP_RULE_MUST_BE_EXIST_ONE(18010, "个性化菜单规则必须有一个"),

    MENU_GROUP_LIST_SUCCESS(1, "获取菜单组列表成功"),

    MENU_GROUP_GET_SUCCESS(1, "获取菜单组详情成功"),

    MENU_GROUP_DEFAULT_CANNOT_DELETE_WHEN_PERSONALIZEDMENUGROUP_EXIST(18011,
            "个性化菜单存在时不允许删除默认菜单"),

    MENU_GROUP_PUSH_WX_SUCCESS(1, "菜单组推送微信成功"),

    MENU_GROUP_PUSH_WX_FAILURE(2, "菜单组推送微信失败"),

    MENU_GROUP_DEFAULT_NOT_EXIST_IN_WEIXIN(18012, "默认菜单在微信中不存在"),

    MENU_GROUP_CREATE_SUCCESS(1, "创建菜单组成功"),

    MENU_GROUP_UPDATE_SUCCESS(1, "更新菜单组成功"),

    MENU_GROUP_DELETE_SUCCESS(1, "删除菜单组成功"),

    MENU_GROUP_TAG_ADD_JOB_ERROR(18013, "菜单组加标签任务异常"),

    /**
     * menu
     */
    MENU_NAME_NOT_BLANK(19001, "菜单不能为空"),

    MENU_TYPE_NOT_BLANK(19002, "菜单响应动作类型不能为空"),

    MENU_URL_NOT_BLANK(19003, "菜单超链不能为空"),

    MENU_TYPE_INVALID(19004, "菜单响应动作类型无效"),

    MENU_CONTENT_NOT_BLANK(19005, "菜单响应内容不能为空"),

    MENU_NOT_EXIST(19006, "菜单不存在"),

    MINIPROGRAM_APPID_NOT_BLANK(19007, "小程序APPID不能为空"),

    MINIPROGRAM_PAGEPATH_NOT_BLANK(19008, "小程序的页面路径不能为空"),

    /**
     * conversation
     */
    CONVERSATION_CREATE_SUCCESS(1, "创建会话成功"),

    CONVERSATION_CONTENT_NOT_BLANK(20001, "会话内容不能为空"),

    CONVERSATION_CONTENT_NOT_SUPPORT(20002, "会话内容类型不支持"),

    CONVERSATION_LIST_SUCCESS(1, "获取会话列表成功"),

    CONVERSATION_LIST_FAIL(0, "获取会话列表失败"),

    CONVERSATION_MASS_LIST_SUCCESS(1, "获取群发会话列表成功"),

    CONVERSATION_GET_MASS_AVAILABLE_SUCCESS(1, "获取本月群发会话可用数量成功"),

    CONVERSATION_MASS_NO_AVALIBLE_COUNT(20003, "本月群发没有可用数量"),

    CONVERSATION_MEMBER_NOT_ONLINE(20004, "会员48小时内未在线,不能发送信息"),

    CONVERSATION_TIME_OVERFLOW(20005, "查询时间不能超过当前时间"),

    CONVERSATION_STATUS_INVALID(20006, "群发审核状态无效"),

    CONVERSATION_MASS_NOT_EXIST(20007, "群发不存在"),

    CONVERSATION_MASS_ADD_JOB_ERROR(20008, "群发任务异常"),

    CONVERSATION_MASS_AUDIT_SUCCESS(1, "群发审核成功"),

    CONVERSATION_MASS_SEND_SUCCESS(1, "群发发送成功"),

    CONVERSATION_MASS_CREATE_SUCCESS(1, "创建群发会话成功"),

    CONVERSATION_MASS_RUN_AT_MUST_BE_GE_NOW(1, "群发发送时间必须大于当前时间"),

    /**
     * report
     */
    REPORT_DAY_MESSAGE_GET_SUCCESS(1, "获取日消息报表成功"),

    REPORT_MENU_GET_SUCCESS(1, "获取菜单报表成功"),

    REPORT_MENU_TREND_SUCCESS(1, "获取菜单趋势报表成功"),

    REPORT_ARGS_INVALID(21001, "报表参数错误"),

    REPORT_REPLY_GET_SUCCESS(1, "获取关键字报表成功"),

    REPORT_HOUR_MESSAGE_GET_SUCCESS(1, "获取小时消息报表成功"),

    REPORT_DAY_ARTICLE_GET_SUCCESS(1, "获取群发图文报表成功"),

    REPORT_HOUR_ARTICLE_GET_SUCCESS(1, "获取小时群发图文报表成功"),

    REPORT_ACTIVITY_GET_SUCCESS(1, "获取活动报表成功"),

    REPORT_ACTIVITY_LIST_SUCCESS(1, "获取活动-优惠券列表成功"),

    REPORT_QRCODE_LIST_SUCCESS(1, "获取二维码列表成功"),

    REPORT_QRCODE_START_OR_END_NOT_BLANK(21002, "导出二维码分析报表开始或结束时间不能为空"),

    REPORT_SOURCE_USER_START_OR_END_NOT_BLANK(21003, "导出用户来源分析报表开始或结束时间不能为空"),

    REPORT_ARTICLE_START_OR_END_NOT_BLANK(21004, "导出图文分析报表开始或结束时间不能为空"),

    REPORT_MESSAGE_START_OR_END_NOT_BLANK(21005, "导出消息分析报表开始或结束时间不能为空"),

    REPORT_MENU_START_OR_END_NOT_BLANK(21006, "导出菜单分析报表开始或结束时间不能为空"),

    REPORT_USER_START_OR_END_NOT_BLANK(21007, "导出用户分析报表开始或结束时间不能为空"),

    REPORT_CUSTOMER_SERVICE_START_OR_END_NOT_BLANK(21008, "导出客户服务报表开始或结束时间不能为空"),
    /**
     * reply
     */
    REPLY_SUBSCRIBE_AUTO_EXIST(22001, "关注自动回复已存在"),

    REPLY_NAME_NOT_BLANK(22002, "自动回复关键字不能为空"),

    REPLY_MATCH_MODE_NOT_BLANK(22003, "自动回复匹配模式不能为空"),

    REPLY_MATCH_MODE_INVALID(22004, "自动回复匹配模式不能为空"),

    REPLY_DELETE_SUCCESS(1, "删除自动回复成功"),

    REPLY_ID_NOT_BLANK(22005, "自动回复ID不能为空"),

    REPLY_NOT_EXIST(22006, "自动回复不存在"),

    REPLY_GET_SUCCESS(1, "获取自动回复详情成功"),

    REPLY_UPDATE_SUCCESS(1, "更新自动回复成功"),

    REPLY_LIST_SUCCESS(1, "更新自动回复成功"),

    REPLY_CREATE_SUCCESS(1, "创建自动回复成功"),

    REPLY_ACTION_ENGINE_CREATE_SUCCESS(1, "创建自动回复行为规则成功"),

    REPLY_ACTION_ENGINE_UPDATE_SUCCESS(1, "更新自动回复行为规则成功"),

    REPLY_ACTION_ENGINE_DELETE_SUCCESS(1, "删除自动回复行为规则成功"),

    REPLY_ACTION_ENGINE_NOT_BLANK(22007, "自动回复行为规则不能为空"),

    REPLY_ACTION_ENGINE_NOT_EXIST(22008, "自动回复行为规则不存在"),

    REPLY_ACTION_ENGINE_LIST_SUCCESS(1, "获取自动回复行为规则列表成功"),

    REPLY_ACTION_ENGINE_ID_NOT_BLANK(22008, "自动回复行为规则ID不能为空"),

    REPLY_KEYWORDS_NOT_BLANK(22009, "自动回复关键词不能为空"),

    /**
     * user
     */
    USER_CREATE_SUCCESS(1, "创建用户成功"),

    USER_WECHAT_LIST_SUCCESS(1, "获取用户公众号列表成功"),

    USER_NAME_NOT_BLANK(23001, "用户名不能为空"),

    USER_PASSWORD_NOT_BLANK(23002, "密码不能为空"),

    USER_NAME_OR_PASSWORD_ERROR(23003, "用户名或者密码错误"),

    USER_LOGIN_SUCCESS(1, "用户登录成功"),

    USER_LIST_SUCCESS(1, "获取用户列表成功"),

    USER_NOT_BLANK(23004, "用户不能为空"),

    USER_NAME_EXIST(23005, "用户名已存在"),

    USER_GET_SUCCESS(1, "获取用户成功"),

    USER_UPDATE_SUCCESS(1, "更新用户成功"),

    USER_DELETE_SUCCESS(1, "删除用户成功"),

    USER_FUNCTION_LIST_SUCCESS(1, "用户权限列表成功"),

    USER_ROLE_LIST_SUCCESS(1, "获取角色列表成功"),

    USER_UPDATE_PASSWORD_SUCCESS(1, "用户修改密码成功"),

    USER_CHANGE_WECHAT_SUCCESS(1, "用户切换公众号成功"),

    USER_NOT_RALATE_CHANGE_WECHAT(23006, "用户未关联要切换公众号"),

    USER_INIT_SUCCESS(1, "数据初始化成功"),

    USER_ALREADY_FINISH_INIT(23007, "数据已经完成初始化，不能重复初始化"),

    USER_UNBIND_WECHAT(23008, "用户未绑定公众号"),

    USER_DELETED(23009, "用户已删除"),

    USER_LOCKED(23010, "用户已被锁"),

    /**
     * wechat
     */
    WECHAT_NOT_EXIST(24001, "公众号不存在"),

    WECHAT_LIST_SUCCESS(1, "获取公众号列表成功"),

    WECHAT_INSERT_SUCCESS(1, "新增公众号成功"),

    WECHAT_GET_SUCCESS(1, "获取公众号信息成功"),

    WECHAT_UPDATE_SUCCESS(1, "修改公众号成功"),

    WECHAT_DELETE_SUCCESS(1, "删除公众号成功"),

    WECHAT_UPDATE_STATUS_SUCCESS(1, "修改公众号状态成功"),

    WECHAT_NAME_NOT_BLANK(24002, "公众号名称不能为空"),

    WECHAT_APP_ID_NOT_BLANK(24003, "公众号App Id不能为空"),

    WECHAT_APP_SECRET_NOT_BLANK(24004, "公众号App Secret不能为空"),

    WECHAT_TOKEN_NOT_BLANK(24005, "公众号Token不能为空"),

    WECHAT_URL_NOT_BLANK(24006, "公众号Url不能为空"),

    WECHAT_ENCODING_AES_KEY_NOT_BLANK(24007, "公众号Encoding Aes Key不能为空"),

    WECHAT_OPEN_ID_NOT_BLANK(24008, "公众号Open Id不能为空"),

    WECHAT_NAME_NOT_REPEAT(24009, "公众号名称不能重复"),

    /**
     * weixin
     */
    WEIXIN_SERVER_CONNECTION_TIMED_OUT(25001, "微信连接超时"),

    WEIXIN_HTTPS_REQUEST_ERROR(25002, "微信请求错误"),

    NETWORK_ERROR(25003, "微信网络异常"),

    WEIXIN_UPLOAD_ARTICLES_NOT_EMPTY(25004, "微信图文不能为空"),

    WEIXIN_UPLOAD_ARTICLES_OVER_MAX_SIZE(25005, "微信图文数量最多8个"),

    WEIXIN_UPLOAD_KFACCOUNT_HEADIMG_MUST_JPG(25006, "微信客服头像必须是图片类型"),

    WEIXIN_UPLOAD_FILE_NOT_EXIST(25007, "微信上传文件不能为空"),

    WEIXIN_UPLOAD_MEDIA_NO_CONFIG(25008, "微信请求未配置"),

    /**
     * file upload
     */
    FILE_NOT_BLANK(26001, "文件不能为空"),

    FILE_IS_TOO_BIG(26002, "文件太大"),

    FILE_EXT_NOT_SUPPORT(26003, "文件类型不支持"),

    FILE_UPLOAD_SUCCESS(1, "上传文件成功"),

    CSV_UPLOAD_SUCCESS(1, "csv导入文件上传成功"),

    CSV_OR_EXCEL_PARSER_FAIL(26004, "CSV或者Excel解析失败！"),

    CSV_TEST_FAIL(26005, "CSV测试失败！"),
    CSV_TEST_SUCCESS(26005, "CSV测试成功！"),

    /**
     * Function
     */
    FUNCTION_LIST_SUCCESS(1, "权限列表成功"),

    FUNCTION_NOT_BLANK(27001, "权限不能为空"),

    FUNCTION_NOT_EXIST(27002, "权限不存在"),

    /**
     * Role
     */
    ROLE_SEARCH_SUCCESS(1, "角色列表成功"),

    ROLE_INSERT_SUCCESS(1, "角色新增成功"),

    ROLE_NOT_BLANK(1, "角色不能为空"),

    ROLE_NAME_NOT_BLANK(28001, "角色名字不能为空"),

    ROLE_NAME_EXIST(28002, "角色已经存在"),

    ROLE_GET_SUCCESS(1, "角色获取成功"),

    ROLE_UPDATE_SUCCESS(1, "角色更新成功"),

    ROLE_DELETE_SUCCESS(1, "角色删除成功"),

    ROLE_USED_NOT_DELETE(28003, "使用过的角色不能删除"),

    ROLE_NAME_NOT_REPEAT(28004, "角色名字不能重复"),

    /**
     * task_category
     */
    TASK_CATEGORY_NOT_EXIST(29001, "任务类别不存在"),

    /**
     * activity
     */
    ACTIVITY_LIST_SUCCESS(1, "活动列表成功"),

    ACTIVITY_GET_SUCCESS(1, "活动获取成功"),

    ACTIVITY_CREATE_SUCCESS(1, "活动创建成功"),

    ACTIVITY_UPDATE_SUCCESS(1, "活动更新成功"),

    ACTIVITY_NAME_NOT_BLANK(30001, "活动名称不能为空"),

    ACTIVITY_START_DATE_NOT_BLANK(30002, "活动开始时间不能为空"),

    ACTIVITY_END_DATE_NOT_BLANK(30003, "活动结束时间不能为空"),

    ACTIVITY_START_DATE_MUST_BE_LT_END_DATE(30004, "活动开始时间必须小于结束时间"),

    ACTIVITY_NOT_EXIST(30005, "活动不存在"),

    ACTIVITY_NAME_EXIST(30006, "活动名称已存在"),

    ACTIVITY_QRCODE_CREATE_ERROR(30007, "活动二维码生成异常"),

    ACTIVITY_QRCODE_NOT_EXIST(30008, "活动二维码不存在"),

    ACTIVITY_QRCODE_LIST_SUCCESS(1, "活动二维码列表成功"),

    ACTIVITY_QRCODE_DELETE_SUCCESS(1, "活动二维码删除成功"),

    ACTIVITY_QRCODE_CREATE_SUCCESS(1, "活动二维码创建成功"),

    ACTIVITY_ID_NOT_BLANK(30009, "活动ID不能为空"),

    ACTIVITY_IS_NOT_START(30010, "活动未开始"),

    ACTIVITY_IS_END(30011, "活动已结束"),

    ACTIVITY_HAS_NO_THIS_COUPON_SETTING(30012, "活动没有该优惠券"),

    ACTIVITY_TYPE_NOT_EXIST(30013, "活动类型不存在"),

    ACTIVITY_SHARE_TYPE_NOT_EXIST(30014, "活动分享类型不存在"),

    ACTIVITY_SHARE_TYPE_NOT_EMPTY(30015, "活动分享类型不能为空"),

    ACTIVITY_SHARE_SUCCESS(1, "活动分享成功"),

    ACTIVITY_IS_START(30016, "活动已开始"),

    ACTIVITY_DELETE_SUCCESS(1, "活动删除成功"),

    ACTIVITY_CHANNEL_NOT_BLANK(30017, "活动渠道不能为空"),

    ACTIVITY_CHANNEL_EXIST(30018, "活动渠道已经存在"),

    /**
     * coupon_setting
     */
    COUPON_SETTING_LIST_SUCCESS(1, "优惠券列表成功"),

    COUPON_SETTING_GET_SUCCESS(1, "优惠券获取成功"),

    COUPON_SETTING_UPDATE_SUCCESS(1, "优惠券更新成功"),

    COUPON_SETTING_NOT_BLANK(31001, "优惠券不能为空"),

    COUPON_SETTING_NOT_EXIST(31002, "优惠券不存在"),

    COUPON_SETTING_BG_COLOR_NOT_BLANK(31003, "优惠券不能为空"),

    COUPON_SETTING_PIC_NOT_BLANK(31004, "优惠券图片不能为空"),

    COUPON_SETTING_BUSINESS_LIST_SUCCESS(1, "优惠券门店列表成功"),

    COUPON_SETTING_ID_NOT_BLANK(31005, "优惠券ID不能为空"),

    COUPON_SETTING_RECEIVE_TIME_IS_NOT_START(31006, "优惠券领取时间未开始"),

    COUPON_SETTING_RECEIVE_TIME_IS_END(31007, "优惠券领取时间已结束"),

    COUPON_SETTING_AVALIBALE_COUPON_IS_EMPTY(31008, "没有可领取的优惠券"),

    COUPON_SETTING_TIMES_OF_WIN_OVER_MAX(31009, "超过每天中奖次数"),

    COUPON_SETTING_TIMES_OF_JOIN_OVER_MAX(31010, "超过每天参数次数"),

    COUPON_SETTING_NOT_WIN(31011, "优惠券没有中奖"),

    COUPON_SETTING_NUM_NOT_BLANK(31012, "优惠券数量不能为空"),

    COUPON_SETTING_NUM_MUST_GE_ZERO(31013, "优惠券数量不能小于0"),

    COUPON_SETTING_RECEIVE_NO_PERMISSION(31014, "您没有领取资格"),

    /**
     * coupon
     */
    COUPON_LIST_SUCCESS(1, "优惠码列表成功"),

    COUPON_RECEIVE_SUCCESS(1, "优惠券领取成功"),

    /**
     * activity_coupon_setting
     */
    ACTIVITY_COUPON_SETTING_LIST_SUCCESS(1, "活动优惠券列表成功"),

    ACTIVITY_COUPON_SETTING_CREATE_SUCCESS(1, "活动优惠券创建成功"),

    ACTIVITY_COUPON_SETTING_DELETE_SUCCESS(1, "活动优惠券删除成功"),

    /**
     * area
     */
    AREA_COUNTRY_NOT_EXIST(32001, "国家不存在"),

    AREA_PROVINCE_NOT_EXIST(32002, "省份不存在"),

    AREA_CITY_NOT_EXIST(32003, "城市不存在"),

    /**
     * member_profile
     */
    MEMBER_PROFILE_NOT_BIND(33001, "会员未绑定"),

    MEMBER_PROFILE_REGISTER_SUCCESS(1, "注册成功"),

    MEMBER_PROFILE_VERIFY_SUCCESS(1, "校验成功"),

    MEMBER_PROFILE_BIND_SUCCESS(1, "绑定成功"),

    MEMBER_PROFILE_UN_BUND_SUCCESS(1, "解绑成功"),

    MEMBER_PROFILE_UPDATE_SUCCESS(1, "更新成功"),

    MEMBER_PROFILE_GET_SUCCESS(1, "获取成功"),

    MEMBER_PROFILE_CARD_NOT_EXIST(33002, "会员卡不存在"),

    MEMBER_PROFILE_CARD_NOT_BLANK(33003, "会员不能为空"),

    MEMBER_NOT_LOGIN(33004, "会员未登录"),

    MEMBER_WAIT_BIND_CARD(33005, "会员待绑卡"),

    /**
     * verify_token
     */
    VERIFY_TOKEN_NOT_EXIST(34001, "验证码不存在"),

    VERIFY_TOKEN_IS_EXPIRED(34002, "验证码已过期"),

    VERIFY_TOKEN_IS_VERIFYED(34003, "验证码已失效"),

    VERIFY_TOKEN_SEND_SUCCESS(1, "验证码发送成功"),

    VERIFY_TOKEN_MOBILE_NOT_BLANK(34004, "手机号码不能为空"),

    VERIFY_TOKEN_CODE_NOT_BLANK(34005, "验证码不能为空"),

    MOBILE_INVALID(34006, "手机号码无效"),

    VERIFY_TOKEN_MOBILE_SEND_OVER_MAX(34007, "手机验证码超过每天发送次数"),

    /**
     * hola-template
     */
    TEMPLATE_NOT_BLANK(35001, "消息模板不能为空"),

    TEMPLATE_PARAMETERS_GET_SUCCESS(1, "获取消息模板参数成功"),

    TEMPLATE_WEXIN_NOT_EXIST(35002, "微信消息模板不存在"),

    TEMPLATE_ID_NOT_BLANK(35003, "微信模板消息id不能为空"),

    TEMPLATE_OPENID_NOT_BLANK(35004, "微信模板消息用户openid不能为空"),

    TEMPLATE_DATA_NOT_BLANK(35004, "微信模板消息data数据不能为空"),

    TEMPLATE_RESULT_NOT_BLANK(35004, "微信模板消息未发送"),

    TEMPLATE_ACCESSTOKEN_NOT_BLANK(35005, "微信模板消息accesstoken不能为空"),

    TEMPLATE_LIST_SUCCESS(1, "微信模板列表成功"),

    TEMPLATE_DELETE_SUCCESS(1, "微信模板删除成功"),

    TEMPLATE_PULL_WX_SUCCESS(1, "同步微信模板成功"),

    TEMPLATE_NOT_EXIST(35006, "消息模板不存在"),

    /**
     * template-industry
     */
    TEMPLATE_INDUSTRY_GET_SUCCESS(1, "模板行业获取成功"),

    TEMPLATE_INDUSTRY_PULL_WX_SUCCESS(1, "模板行业同步微信成功"),

    /**
     * hola
     */
    HOLA_REGISTER_SUCCESS(1, "注册成功"),

    HOLA_MOBILE_EXIST_CARD(36001, "手机号已有会员"),

    HOLA_REQUEST_TIME_OUT(36002, "hola连接超时"),

    HOLA_API_ERROR(36003, "hola接口错误"),

    /**
     * file encoding
     */
    FILE_UTF8_ENCODING_ERROR(37001, "文件UTF-8编码错误"),

    /**
     * hola-offline-activity
     */
    HOLA_OFFLINE_ACTIVITY_LIST_SUCCESS(1, "线下活动列表成功"),

    HOLA_OFFLINE_ACTIVITY_INSERT_SUCCESS(1, "线下活动新增成功"),

    HOLA_OFFLINE_ACTIVITY_GET_SUCCESS(1, "线下活动获取成功"),

    HOLA_OFFLINE_ACTIVITY_UPDATE_SUCCESS(1, "线下活动修改成功"),

    HOLA_OFFLINE_ACTIVITY_DELETE_SUCCESS(1, "未开始的线下活动删除成功"),

    HOLA_OFFLINE_ACTIVITY_BUSINESS_GET_SUCCESS(1, "线下活动可链接门店列表成功"),

    HOLA_OFFLINE_ACTIVITY_NAME_NOT_BLANK(38001, "线下活动名称不能为空"),

    HOLA_OFFLINE_ACTIVITY_START_DATE_NOT_BLANK(38002, "线下活动起始时间不能为空"),

    HOLA_OFFLINE_ACTIVITY_END_DATE_NOT_BLANK(38003, "线下活动终止时间不能为空"),

    HOLA_OFFLINE_ACTIVITY_NAME_NOT_REPEAT(38004, "线下活动名称不能重复"),

    HOLA_SESSION_LIST_SUCCESS(1, "线下活动场次列表获取成功"),

    HOLA_SESSION_INSERT_SUCCESS(1, "线下活动报名场次新增成功"),

    HOLA_SESSION_GET_SUCCESS(1, "线下活动场次获取成功"),

    HOLA_SESSION_UPDATE_SUCCESS(1, "线下活动场次修改成功"),

    HOLA_SESSION_UPDATE_STATUS_SUCCESS(1, "线下活动场次更新状态成功"),

    HOLA_SESSION_DELETE_SUCCESS(1, "线下活动场次删除成功"),

    HOLA_SESSION_DATE_NOT_BLANK(38005, "线下活动场次时间不能为空"),

    HOLA_SESSION_ACTIVITY_NOT_BLANK(38006, "线下活动场次不能为空"),

    HOLA_SESSION_MEMBER_LIMIT_NOT_BLANK(38007, "线下活动场次人数上限不能为空"),

    HOLA_SESSION_ID_NOT_BLANK(38008, "线下活动场次ID不能为空"),

    HOLA_SESSION_STATUS_NOT_BLANK(38009, "线下活动场次状态更新不能为空"),

    HOLA_FINISH_SESSION_NOT_DELETE(38010, "线下活动已结束场次不能删除"),

    HOLA_PROCESS_SESSION_NOT_DELETE(38011, "线下活动进行中场次不能删除"),

    HOLA_OFFLINE_ACTIVITY_NOT_EXIST(38012, "线下活动不存在"),

    HOLA_NOT_PREPARE_OFFLINE_ACTIVITY_NOT_DELETE(38013, "进行中或者已结束的线下活动不能删除"),

    HOLA_SESSION_INSERT_NOT_REPEAT(38014, "线下活动场次重复不能新增"),

    /**
     * hola-session-member
     */
    HOLA_SESSION_MEMBER_LIST_SUCCESS(1, "线下活动报名用户列表成功"),

    HOLA_SESSION_MEMBER_LIST_BLANK(39001, "线下活动未创建，用户列表为空"),

    HOLA_SESSION_MEMBER_OPENID_BLANK(39002, "用户OpenID为空，报名失败"),

    HOLA_SESSION_MEMBER_APPLY_SUCCESS(1, "用户线下活动报名成功"),

    HOLA_SESSION_MEMBER_WEIXIN_DETAIL_SUCCESS(1, "移动端获取线下活动详情成功"),

    HOLA_SESSION_MEMBER_WEIXIN_LIST_SUCCESS(1, "移动端获取线下活动列表成功"),

    HOLA_SESSION_MEMBER_APPLY_FAIL(39003, "报名人数达到上限"),

    HOLA_SESSION_MEMBER_ALREADY_APPLY(39004, "已经报名"),

    /**
     * baidu-map
     */
    MAP_URL_ENCODING_FAIL(40001, "URL中特殊字符UTF-8编码失败"),

    MAP_BUSINESS_LAT_LNG_GET_SUCCESS(1, "门店获取百度经纬度成功"),

    /**
     * customer-service-config
     */
    CUSTOMER_SERVICE_CONFIG_LIST_SUCCESS(1, "客服配置列表成功"),

    CUSTOMER_SERVICE_CONFIG_DELETE_SUCCESS(1, "客服配置删除成功"),

    CUSTOMER_SERVICE_CONFIG_ADD_SUCCESS(1, "客服配置添加成功"),

    CUSTOMER_SERVICE_CONFIG_UPDATE_SUCCESS(1, "客服配置更新成功"),

    CUSTOMER_SERVICE_CONFIG_LIST_USERS_SUCCESS(1, "获取微信用户列表成功"),

    CUSTOMER_SERVICE_CONFIG_NOT_BLANK(41001, "客服配置不能为空"),

    CUSTOMER_SERVICE_CONFIG_VALUE_NOT_BLANK(41002, "客服配置值不能为空"),

    CUSTOMER_SERVICE_CONFIG_NOT_EXIST(41003, "客服配置不存在"),

    CUSTOMER_SERVICE_CONFIG_GROUP_NOT_BLANK(41004, "客服配置组不能为空"),

    CUSTOMER_SERVICE_CONFIG_KEY_NOT_BLANK(41005, "客服配置KEY不能为空"),

    CUSTOMER_SERVICE_CONFIG_KEY_REPEAT(41006, "客服配置KEY重复"),

    WX_MEDIA_GET_SUCCESS(1, "多媒体获取成功"),

    WX_MEDIA_INVALID_ID(40007, "无效的media id"),

    WX_MEDIA_INVALID_SIGN(2, "无效的签名"),

    WX_MEDIA_INVALID_ACCESS(3, "无效的接入"),

    /**
     *
     */
    MEMBER_QRCODE_STATISTICS(1, "查询会员扫码统计结果成功"),

    /**
     * material_category
     */
    MATERIAL_CATEGORY_NAME_NOT_NULL(50000, "分类名称不能为空"),
    MATERIAL_CATEGORY_NAME_EXITS(50001, "分类名称已存在"),
    MATERIAL_CATEGORY_ID_NOT_NULL(50002, "id不能为空"),
    MATERIAL_CATEGORY_BE_USED(50003, "有{0}条图文正在使用此分类，无法删除"),
    MATERIAL_CATEGORY_OVER_LIMIT(50004, "分类名称限15个中文字符，英文30个"),

    /**
     * DcrmImageTextDetail
     */
    DcrmImageTextDetail_NOT_NULL(60000, "标题不能为空"),
    DcrmImageTextDetail_IAMGE_NOT_NULL(60001, "封面图不能为空"),
    DCRM_IMAGE_TEXT_DETAIL_ID_NOT(60002, "id不能为空"),
    Dcrm_MATRIAl_NOT_EXIST(60003, "封面图id不存在"),

    /**
     * InterfaceConfig
     */
    INTERFACECONFIG_SELECT_FAIL(42001, "接口查询失败"),

    INTERFACECONFIG_CREATE_FAIL(42011, "接口创建失败"),

    INTERFACECONFIG_EXIST(42012, "接口已存在"),

    INTERFACECONFIG_UPDATE_FAIL(42021, "接口编辑失败"),

    INTERFACECONFIG_DELETE_FAIL(42031, "接口删除失败"),

    INTERFACECONFIG_IN_USED(42032, "接口在被使用中"),


    INTERFACECONFIG_GET_FAIL(42041, "接口获取失败"),

    INTERFACECONFIG_BRAND_LIST_FAIL(42051, "获取第三方名称失败"),

    INTERFACECONFIG_BRAND_CREATE_FAIL(42061, "创建第三方名称失败"),

    INTERFACECONFIG_BRAND_EXIST(42062, "第三方名称已存在"),

    INTERFACECONFIG_BRAND_UPDATE_FAIL(42071, "更新第三方名称失败"),

    INTERFACECONFIG_BRAND_DELETE_FAIL(42081, "删除第三方名称失败"),

    INTERFACECONFIG_BRAND_IN_USED(42082, "第三方名称在使用中"),

    INTERFACECONFIG_BRAND_NOT_EXIST(42083, "第三方名称不存在"),

    INTERFACECONFIG_SECRET_GET_FAIL(42091, "获取秘钥失败"),

    INTERFACECONFIG_SECRET_EXIST(42092, "秘钥已经存在"),

    INTERFACECONFIG_ID_NOT_EXIST(42500, "id不能为空"),

    INTERFACECONFIG_STATUS_NOT_EXIST(42501, "status不能为空"),


    /**
     * event forward
     */

    EVENT_FORWARD_ADD_FAIL(50000, "添加事件转发失败"),
    EVENT_FORWARD_EXIST(50001, "事件转发已存在"),


    SCHEDULER_SUCCESS(70001, "执行成功"),
    SCHEDULER_FAIL(70001, "执行失败"),


    /**
     * 测试接口
     */
     TEST_INTERFACE__FALL(60000,"接口测试失败"),



    ;

    private Integer code;

    private String name;

    /**
     * @param code
     */
    private Message(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Message getByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        for (Message msg : Message.values()) {
            if (StringUtils.equals(msg.name(), name)) {
                return msg;
            }
        }
        return null;
    }

    /**
     * With parameters
     *
     * @param params
     * @return
     */
    public String withParams(Object... params) {
        return MessageFormat.format(this.name, params);
    }
}
