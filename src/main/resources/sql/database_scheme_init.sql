/*
 Navicat Premium Data Transfer

 Source Server         : 10.60.1.122
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 10.60.1.122
 Source Database       : d1m_wechat

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 06/22/2017 11:43:45 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `action_engine`
-- ----------------------------
DROP TABLE IF EXISTS `action_engine`;
CREATE TABLE `action_engine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建人',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0:关闭,1:启用)',
  `start_at` datetime DEFAULT NULL COMMENT '有效开始时间',
  `end_at` datetime DEFAULT NULL COMMENT '有效结束时间',
  `run_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '运行类型(1:总是运行,2:cron定时）',
  `conditions` text COMMENT '完成条件(json格式)',
  `condition_sql` text COMMENT '完成条件sql',
  `effect` text COMMENT '产生的效果(json格式)',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  `wechat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `creator_id` (`creator_id`) USING BTREE,
  KEY `action_engine_ibfk_2` (`wechat_id`),
  CONSTRAINT `action_engine_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `action_engine_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务';

-- ----------------------------
--  Table structure for `activity`
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(30) DEFAULT NULL COMMENT '活动编号',
  `name` varchar(200) NOT NULL COMMENT '活动名称',
  `description` varchar(500) DEFAULT NULL COMMENT '活动描述',
  `start_date` datetime NOT NULL COMMENT '活动开始时间',
  `end_date` datetime NOT NULL COMMENT '活动结束时间',
  `share_pic` varchar(200) DEFAULT NULL COMMENT '分享封面',
  `share_title` varchar(200) DEFAULT NULL COMMENT '分享标题',
  `share_description` varchar(500) DEFAULT NULL COMMENT '分享描述',
  `acty_h5` longtext COMMENT '活动H5页面',
  `acty_offline_h5` longtext COMMENT '活动下线H5页面',
  `acty_url` varchar(255) DEFAULT NULL COMMENT '活动URL',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_by_id` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_at` datetime DEFAULT NULL COMMENT '修改时间',
  `oauth_url_id` int(11) DEFAULT NULL COMMENT '短地址ID',
  `type` tinyint(2) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `activity_coupon_setting`
-- ----------------------------
DROP TABLE IF EXISTS `activity_coupon_setting`;
CREATE TABLE `activity_coupon_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coupon_setting_id` int(11) NOT NULL COMMENT '电子券设置ID',
  `activity_id` int(11) NOT NULL COMMENT '活动ID',
  `wechat_id` int(11) DEFAULT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `activity_id` (`activity_id`),
  KEY `coupon_setting_id` (`coupon_setting_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `activity_coupon_setting_ibfk_1` FOREIGN KEY (`coupon_setting_id`) REFERENCES `coupon_setting` (`id`),
  CONSTRAINT `activity_coupon_setting_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `activity_coupon_setting_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `activity_qrcode`
-- ----------------------------
DROP TABLE IF EXISTS `activity_qrcode`;
CREATE TABLE `activity_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `activity_id` int(11) NOT NULL COMMENT '活动ID',
  `channel` varchar(255) DEFAULT NULL COMMENT '渠道',
  `acty_url` varchar(255) NOT NULL COMMENT '活动url',
  `qrcode_img_url` varchar(255) NOT NULL COMMENT '二维码图片url',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(4) NOT NULL COMMENT '状态(1:使用,0:删除)',
  `creator_id` int(11) NOT NULL COMMENT '创建人',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `activity_id` (`activity_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `activity_qrcode_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `activity_qrcode_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `activity_qrcode_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动二维码';

-- ----------------------------
--  Table structure for `activity_share`
-- ----------------------------
DROP TABLE IF EXISTS `activity_share`;
CREATE TABLE `activity_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `activity_id` int(11) NOT NULL COMMENT '活动ID',
  `share_at` datetime NOT NULL COMMENT '分享时间',
  `type` tinyint(2) NOT NULL COMMENT '类型(1:朋友圈,2:朋友,3:QQ,4:腾讯博客,5:QQ空间)',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `activity_id` (`activity_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `activity_share_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `activity_share_ibfk_2` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  CONSTRAINT `activity_share_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动分享';

-- ----------------------------
--  Table structure for `activity_tmpl`
-- ----------------------------
DROP TABLE IF EXISTS `activity_tmpl`;
CREATE TABLE `activity_tmpl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `h5` longtext NOT NULL COMMENT '活动ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `area_info`
-- ----------------------------
DROP TABLE IF EXISTS `area_info`;
CREATE TABLE `area_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` int(11) DEFAULT NULL COMMENT '地区码',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `c_name` varchar(50) NOT NULL DEFAULT '' COMMENT '中文名称',
  `e_name` varchar(50) DEFAULT '' COMMENT '英文名称',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `area_info_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `area_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地区';

-- ----------------------------
--  Table structure for `business`
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `business_code` varchar(30) NOT NULL DEFAULT '',
  `business_name` varchar(200) NOT NULL COMMENT '门店名称',
  `branch_name` varchar(200) DEFAULT NULL COMMENT '分店名称',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `district` varchar(50) DEFAULT NULL COMMENT '区/县',
  `address` varchar(100) NOT NULL COMMENT '详细地址',
  `telephone` varchar(30) NOT NULL COMMENT '电话',
  `categories` varchar(30) DEFAULT NULL COMMENT '门店类型',
  `longitude` double DEFAULT NULL COMMENT '地理位置的经度',
  `latitude` double DEFAULT NULL COMMENT '地理位置的纬度',
  `special` varchar(255) DEFAULT NULL COMMENT '特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务',
  `open_time` varchar(20) DEFAULT NULL COMMENT '营业时间，24 小时制表示，用“-”连接，如 8:00-20:00',
  `avg_price` decimal(10,2) DEFAULT NULL COMMENT '人均价格',
  `introduction` longtext COMMENT '商户简介，主要介绍商户信息等',
  `recommend` longtext COMMENT '推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建者',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `modify_at` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(2) unsigned zerofill NOT NULL DEFAULT '00' COMMENT '状态(1:使用,0:删除)',
  `bus` varchar(255) DEFAULT NULL COMMENT '公交',
  `is_push` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否发布（0-未发布，1-已发布）',
  `wxlng` double DEFAULT NULL COMMENT '地理位置腾讯经度',
  `wxlat` double DEFAULT NULL COMMENT '地理位置腾讯纬度',
  `poi_id` varchar(30) DEFAULT NULL COMMENT '微信门店id',
  `check_status` tinyint(2) DEFAULT NULL COMMENT '审核状态（0-审核中，1-审核成功，2-审核失败）',
  `check_msg` varchar(255) DEFAULT NULL COMMENT '审核消息',
  `update_status` tinyint(2) DEFAULT NULL COMMENT '更新状态',
  PRIMARY KEY (`id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `business_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `business_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='门店';

-- ----------------------------
--  Table structure for `business_category`
-- ----------------------------
DROP TABLE IF EXISTS `business_category`;
CREATE TABLE `business_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID【0：一级】',
  PRIMARY KEY (`id`),
  KEY `qrcode_type_ibfk_1` (`parent_id`),
  CONSTRAINT `business_category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `business_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `business_photo`
-- ----------------------------
DROP TABLE IF EXISTS `business_photo`;
CREATE TABLE `business_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `business_id` int(11) NOT NULL COMMENT '门店ID',
  `photo_url` varchar(255) NOT NULL COMMENT '本地图片路径',
  `wx_url` varchar(255) DEFAULT NULL COMMENT '微信上传路径',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `business_id` (`business_id`),
  CONSTRAINT `business_photo_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `business_photo_ibfk_2` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='门店图片';

-- ----------------------------
--  Table structure for `card`
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `local_card_id` varchar(30) DEFAULT NULL COMMENT '本地CARDID，如CRM-CardId',
  `wx_card_id` varchar(50) DEFAULT NULL COMMENT '微信card_id',
  `card_type` varchar(20) NOT NULL COMMENT '卡券类型(0:general_coupon-通用优惠券,1:discount-折扣券,2:cash-代金券,3:groupon-团购券,4:gift-兑换券)',
  `base_info_id` int(11) DEFAULT NULL COMMENT '卡券基本信息ID',
  `advanced_info_id` int(11) DEFAULT NULL COMMENT '卡券高级信息ID',
  `ext_id` int(11) DEFAULT NULL COMMENT '卡券扩展信息ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `modify_at` datetime DEFAULT NULL COMMENT '更新时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  `push_at` datetime DEFAULT NULL COMMENT '微信同步推送时间',
  `push_status` tinyint(2) DEFAULT NULL COMMENT '状态(0:失败,1:成功)',
  `push_reason` varchar(50) DEFAULT NULL COMMENT '同步失败原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_advanced_info`
-- ----------------------------
DROP TABLE IF EXISTS `card_advanced_info`;
CREATE TABLE `card_advanced_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `use_condition` tinytext COMMENT 'JSON结构：使用条件',
  `cover` longtext COMMENT 'JSON结构：封面信息，对应abstract结构体',
  `text_image_list` longtext COMMENT 'JSON结构：图文信息',
  `time_limit` tinytext COMMENT 'JSON结构：使用时间信息',
  `business_service` tinytext COMMENT 'JSON数组结构：商户服务信息',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_base_info`
-- ----------------------------
DROP TABLE IF EXISTS `card_base_info`;
CREATE TABLE `card_base_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `logo_url` varchar(255) NOT NULL COMMENT '本地卡券图片',
  `wx_logo_url` varchar(255) NOT NULL COMMENT '卡券的商户logo，建议像素为300*300。',
  `code_type` varchar(20) NOT NULL COMMENT '卡券类型(1:CODE_TYPE_NONE,2:CODE_TYPE_TEXT,3:CODE_TYPE_BARCODE,4:CODE_TYPE_ONLY_BARCODE,5:CODE_TYPE_QRCODE,6:CODE_TYPE_ONLY_QRCODE"',
  `brand_name` varchar(100) NOT NULL COMMENT '商户名字,字数上限为12个汉字。',
  `title` varchar(100) NOT NULL COMMENT '卡券名，字数上限为9个汉字。',
  `color` varchar(20) NOT NULL COMMENT '券颜色。按色彩规范标注填写Color010-Color100',
  `notice` varchar(100) NOT NULL COMMENT '卡券使用提醒，字数上限为16个汉字。',
  `description` tinytext NOT NULL COMMENT '卡券使用说明，字数上限为1024个汉字。',
  `sku` tinytext NOT NULL COMMENT 'JSON结构：商品信息。{"quantity": 500000}',
  `date_info` tinytext NOT NULL COMMENT 'JSON结构：使用日期，有效期的信息',
  `use_custom_code` tinyint(3) DEFAULT '0' COMMENT '是否自定义Code码(1:是,0:否)',
  `get_custom_code_mode` varchar(20) DEFAULT '0' COMMENT '自定义Code码获取方式(1:GET_CUSTOM_COD,2:E_MODE_DEPOSIT)',
  `bind_openid` tinyint(3) DEFAULT '0' COMMENT '是否指定用户领取(1:是,0:否)',
  `service_phone` varchar(20) DEFAULT NULL COMMENT '客服电话。',
  `location_id_list` tinytext COMMENT 'JSON数组：门店位置poiid',
  `use_all_locations` tinyint(3) DEFAULT NULL COMMENT '是否支持全部门店，与location_id_list互斥(1:是,0:否)',
  `source` varchar(36) DEFAULT NULL COMMENT '第三方来源名',
  `center_title` varchar(50) DEFAULT NULL COMMENT '卡券顶部居中的按钮',
  `center_sub_title` varchar(50) DEFAULT NULL COMMENT '显示在入口下方的提示语',
  `center_url` varchar(255) DEFAULT NULL COMMENT '顶部居中的url',
  `custom_url_name` varchar(50) DEFAULT NULL COMMENT '自定义跳转按钮',
  `custom_url_sub_title` varchar(50) DEFAULT NULL COMMENT '自定义跳转提示语',
  `custom_url` varchar(255) DEFAULT NULL COMMENT '自定义跳转url',
  `promotion_url_name` varchar(50) DEFAULT NULL COMMENT '营销场景跳转按钮',
  `promotion_url_sub_title` varchar(50) DEFAULT NULL COMMENT '营销场景跳转提示语',
  `promotion_url` varchar(255) DEFAULT NULL COMMENT '营销场景跳转url',
  `get_limit` int(11) DEFAULT NULL COMMENT '每人可领券的数量限制',
  `can_share` tinyint(3) DEFAULT '1' COMMENT '是否可分享卡券页面(1:是,0:否)',
  `can_give_friend` tinyint(3) DEFAULT '1' COMMENT '是否可转赠(1:是,0:否)',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_ext_cash`
-- ----------------------------
DROP TABLE IF EXISTS `card_ext_cash`;
CREATE TABLE `card_ext_cash` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `least_cost` int(11) NOT NULL DEFAULT '0' COMMENT '代金券专用，表示起用金额（单位为分）,如果无起用门槛则填0。',
  `reduce_cost` int(11) NOT NULL COMMENT '代金券专用，表示减免金额。（单位为分）',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_ext_discount`
-- ----------------------------
DROP TABLE IF EXISTS `card_ext_discount`;
CREATE TABLE `card_ext_discount` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `discount` int(11) NOT NULL COMMENT '折扣券专用，表示打折额度（百分比）。填30就是七折。',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_ext_general_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `card_ext_general_coupon`;
CREATE TABLE `card_ext_general_coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `default_detail` longtext NOT NULL COMMENT '优惠券专用，填写优惠详情。',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_ext_gift`
-- ----------------------------
DROP TABLE IF EXISTS `card_ext_gift`;
CREATE TABLE `card_ext_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `gift` longtext NOT NULL COMMENT '兑换券专用，填写兑换内容的名称。',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `card_ext_groupon`
-- ----------------------------
DROP TABLE IF EXISTS `card_ext_groupon`;
CREATE TABLE `card_ext_groupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` int(11) NOT NULL COMMENT '卡券ID',
  `deal_detail` longtext NOT NULL COMMENT '团购券专用，团购详情。',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL COMMENT '公司名称',
  `code` varchar(255) DEFAULT NULL COMMENT '公司编号',
  `logo` varchar(255) DEFAULT NULL COMMENT ' 公司logo链接地址',
  `status` tinyint(2) NOT NULL COMMENT '状态（0-删除，1-使用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='公司信息表';

-- ----------------------------
--  Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cfg_group` varchar(30) NOT NULL COMMENT '组名code',
  `cfg_group_label` varchar(30) DEFAULT NULL COMMENT '组名label',
  `cfg_key_label` varchar(30) DEFAULT NULL COMMENT '键名label',
  `cfg_key` varchar(30) NOT NULL COMMENT '键名code',
  `cfg_value` varchar(200) NOT NULL COMMENT '键值',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `conversation`
-- ----------------------------
DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `open_id` varchar(255) DEFAULT '' COMMENT 'openId',
  `union_id` varchar(255) DEFAULT '' COMMENT 'unionId',
  `user_id` int(11) DEFAULT NULL COMMENT '客服ID(为空代表系统)',
  `material_id` int(11) DEFAULT NULL COMMENT '素材ID',
  `msg_id` varchar(255) DEFAULT NULL COMMENT '微信消息id',
  `msg_type` tinyint(4) NOT NULL COMMENT '消息类型',
  `created_at` datetime NOT NULL COMMENT '会话时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(3) NOT NULL COMMENT '状态(0:未回复,1:已回复)',
  `original_conversation_id` int(11) DEFAULT NULL COMMENT '原始xml会话ID',
  `event` tinyint(4) DEFAULT NULL COMMENT '事件类型',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '文本',
  `pic_url` varchar(255) DEFAULT NULL COMMENT '图片链接',
  `music_url` varchar(255) DEFAULT NULL COMMENT '音乐链接',
  `media_id` varchar(255) DEFAULT NULL COMMENT '媒体id',
  `format` varchar(255) DEFAULT NULL COMMENT '语音格式，如amr，speex等',
  `recognition` varchar(255) DEFAULT NULL COMMENT '语音识别结果',
  `thumb_media_id` int(11) DEFAULT NULL COMMENT '视频消息缩略图的媒体id',
  `location_x` double DEFAULT NULL COMMENT '地理位置纬度',
  `location_y` double DEFAULT NULL COMMENT '地理位置经度',
  `location_precision` double DEFAULT NULL COMMENT '地理位置精度',
  `scale` double DEFAULT NULL COMMENT '地图缩放大小',
  `label` varchar(255) DEFAULT NULL COMMENT '	地理位置信息',
  `title` varchar(255) DEFAULT NULL COMMENT '消息标题',
  `description` varchar(255) DEFAULT NULL COMMENT '消息描述',
  `url` varchar(255) DEFAULT NULL COMMENT '消息链接',
  `event_key` varchar(255) DEFAULT NULL COMMENT '事件KEY值',
  `menu_id` int(11) DEFAULT NULL COMMENT '点击菜单id',
  `menu_parent_id` int(11) DEFAULT NULL COMMENT '点击父级菜单id',
  `menu_group_id` int(11) DEFAULT NULL COMMENT '点击菜单组id',
  `ticket` varchar(255) DEFAULT NULL COMMENT '二维码的ticket',
  `uniq_id` varchar(11) DEFAULT NULL COMMENT '商户自己内部ID，即字段中的sid',
  `poi_id` varchar(11) DEFAULT NULL COMMENT '微信的门店ID，微信内门店唯一标示ID',
  `result` varchar(255) DEFAULT NULL COMMENT '审核结果，成功succ 或失败fail',
  `direction` tinyint(1) NOT NULL COMMENT '会话方向(0:进,1:出)',
  `member_photo` varchar(255) DEFAULT NULL COMMENT '会员头像',
  `kf_photo` varchar(255) DEFAULT NULL COMMENT '客服头像',
  `is_mass` bit(1) DEFAULT b'0' COMMENT '是否是群发会话',
  `voice_url` varchar(255) DEFAULT NULL COMMENT '语音url',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频url',
  `short_video_url` varchar(255) DEFAULT NULL COMMENT '小视频url',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `user_id` (`user_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `original_conversation_id` (`original_conversation_id`),
  CONSTRAINT `conversation_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `conversation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `conversation_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `conversation_ibfk_4` FOREIGN KEY (`original_conversation_id`) REFERENCES `original_conversation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会话';

-- ----------------------------
--  Table structure for `conversation_image_text_detail`
-- ----------------------------
DROP TABLE IF EXISTS `conversation_image_text_detail`;
CREATE TABLE `conversation_image_text_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `content` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '正文',
  `content_source_checked` bit(1) DEFAULT NULL COMMENT '原文链接是否存在',
  `content_source_url` varchar(255) DEFAULT NULL COMMENT '原文链接',
  `show_cover` bit(1) DEFAULT NULL COMMENT '封面图片显示在正文',
  `material_cover_url` varchar(255) DEFAULT NULL COMMENT '封面图片素材url',
  `summary` varchar(255) DEFAULT NULL COMMENT '摘要',
  `conversation_id` int(11) NOT NULL COMMENT '会话ID',
  PRIMARY KEY (`id`),
  KEY `conversation_id` (`conversation_id`),
  CONSTRAINT `conversation_image_text_detail_ibfk_1` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `conversation_index`
-- ----------------------------
DROP TABLE IF EXISTS `conversation_index`;
CREATE TABLE `conversation_index` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `msg_id` bigint(20) DEFAULT NULL COMMENT '微信消息id',
  `open_id` varchar(100) NOT NULL COMMENT 'openId',
  `created_at` datetime NOT NULL COMMENT '会话时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `msg_id` (`msg_id`) USING BTREE,
  CONSTRAINT `conversation_index_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会话索引';

-- ----------------------------
--  Table structure for `coupon`
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '电子券编码',
  `source` tinyint(2) NOT NULL COMMENT '来源(0:D1M,1:CRM)',
  `status` tinyint(2) NOT NULL COMMENT '使用状态(0:未领用,1:已领用,2:已核销)',
  `created_at` datetime NOT NULL COMMENT '生成时间',
  `coupon_setting_id` int(11) NOT NULL COMMENT '活动ID',
  `wechat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `code` (`code`),
  KEY `coupon_setting_id` (`coupon_setting_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `coupon_business`
-- ----------------------------
DROP TABLE IF EXISTS `coupon_business`;
CREATE TABLE `coupon_business` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `coupon_setting_id` int(11) NOT NULL COMMENT '活动ID',
  `business_id` int(11) NOT NULL COMMENT '门店ID',
  `type` tinyint(2) NOT NULL COMMENT '0：领礼门店；1：核销门店',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `business_id` (`business_id`),
  KEY `coupon_setting_id` (`coupon_setting_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `coupon_member`
-- ----------------------------
DROP TABLE IF EXISTS `coupon_member`;
CREATE TABLE `coupon_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `push_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '推送状态：0未推送，1已推送',
  `push_at` datetime DEFAULT NULL,
  `status` tinyint(2) NOT NULL COMMENT '核销状态(0:未核销,1:已核销)',
  `receive_at` datetime NOT NULL COMMENT '领取时间',
  `verification_at` datetime DEFAULT NULL COMMENT '核销时间',
  `coupon_id` int(11) NOT NULL COMMENT '电子券ID',
  `coupon_setting_id` int(11) NOT NULL COMMENT '活动ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `business_id` int(11) DEFAULT NULL COMMENT '核销门店',
  `wechat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `coupon_id` (`coupon_id`),
  KEY `member_id` (`member_id`),
  KEY `business_id` (`business_id`),
  KEY `coupon_setting_id` (`coupon_setting_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `coupon_setting`
-- ----------------------------
DROP TABLE IF EXISTS `coupon_setting`;
CREATE TABLE `coupon_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `grno` varchar(30) NOT NULL COMMENT '活动编号',
  `name` varchar(200) NOT NULL COMMENT '活动名称',
  `short_name` varchar(200) DEFAULT NULL COMMENT '简称',
  `conditions` varchar(255) DEFAULT NULL COMMENT '使用条件',
  `channel` varchar(20) NOT NULL COMMENT '渠道类型：10：微信渠道、20：其他渠道',
  `type` varchar(20) NOT NULL COMMENT '领礼类型：10：一般领礼、20：发票领礼、30：积分换礼、40：银行礼、50：补库存',
  `gift_type` varchar(20) NOT NULL COMMENT '赠送类别：10：商品、20：折扣（价）券、30：折扣(价)券_现折',
  `start_date` datetime NOT NULL COMMENT '领券开始时间',
  `end_date` datetime NOT NULL COMMENT '领券结束时间',
  `validity_start_date` datetime NOT NULL COMMENT '核销开始时间',
  `validity_end_date` datetime NOT NULL COMMENT '核销结束时间',
  `rebate_method` varchar(20) DEFAULT NULL COMMENT '折扣方式：10：扣减金额、20：折扣比例',
  `rebate_sum` double DEFAULT NULL COMMENT '折价券扣减金额/折扣比例',
  `coupon_sum` int(11) DEFAULT NULL COMMENT '优惠券总数',
  `issue_limit_type` varchar(20) DEFAULT NULL COMMENT '折扣券时，门槛类型：20-金额门槛，30-积分门槛',
  `issue_limit_value` double DEFAULT NULL COMMENT '门槛值',
  `fetch_type` tinyint(2) DEFAULT NULL COMMENT '活动领取类型(0:非关注后领取,1:关注后领取)',
  `win_probability` int(11) DEFAULT NULL COMMENT '中奖概率，取值0-100',
  `times_of_join` int(11) DEFAULT NULL COMMENT '每人参与次数',
  `times_of_win` int(11) DEFAULT NULL COMMENT '每人中奖次数上限',
  `coupon_description` varchar(500) DEFAULT NULL COMMENT '优惠券描述',
  `coupon_pic` varchar(200) DEFAULT NULL COMMENT '优惠券图片',
  `coupon_bgcolor` varchar(50) DEFAULT NULL COMMENT '优惠券背景颜色',
  `coupon_title_bgcolor` varchar(50) DEFAULT NULL COMMENT '优惠券标题背景颜色',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_by_id` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_at` datetime DEFAULT NULL COMMENT '修改时间',
  `pordInfos` varchar(255) DEFAULT NULL COMMENT '赠品信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `customer_service_config`
-- ----------------------------
DROP TABLE IF EXISTS `customer_service_config`;
CREATE TABLE `customer_service_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cfg_group` varchar(100) DEFAULT NULL COMMENT '组名',
  `cfg_group_label` varchar(100) DEFAULT NULL COMMENT '组名标签',
  `cfg_key` varchar(100) DEFAULT NULL COMMENT 'key',
  `cfg_key_label` varchar(100) DEFAULT NULL COMMENT 'key标签',
  `cfg_value` varchar(500) DEFAULT NULL COMMENT '值',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `customer_service_config_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客服配置';

-- ----------------------------
--  Table structure for `demo_owen`
-- ----------------------------
DROP TABLE IF EXISTS `demo_owen`;
CREATE TABLE `demo_owen` (
  `id` int(11) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `value` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `function`
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '功能代码',
  `name_cn` varchar(200) NOT NULL DEFAULT '' COMMENT '功能名称-中文',
  `name_en` varchar(200) NOT NULL DEFAULT '' COMMENT '功能名称-英文',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `role_id` (`role_id`) USING BTREE,
  CONSTRAINT `function_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `hola_card_bind_error_record`
-- ----------------------------
DROP TABLE IF EXISTS `hola_card_bind_error_record`;
CREATE TABLE `hola_card_bind_error_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` int(11) NOT NULL COMMENT 'job类型(1:绑卡,2:更新信息)',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`) USING BTREE,
  KEY `wechat_id` (`wechat_id`) USING BTREE,
  CONSTRAINT `hola_card_bind_error_record_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `hola_card_bind_error_record_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `login_token`
-- ----------------------------
DROP TABLE IF EXISTS `login_token`;
CREATE TABLE `login_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `token` varchar(255) NOT NULL COMMENT 'token',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `created_at` varchar(255) NOT NULL COMMENT '创建时间',
  `wechat_id` int(11) DEFAULT NULL COMMENT '公众号ID',
  `expired_at` datetime NOT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `mass_conversation`
-- ----------------------------
DROP TABLE IF EXISTS `mass_conversation`;
CREATE TABLE `mass_conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `conversation_id` int(11) NOT NULL COMMENT '会话ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `pi_ci` int(11) DEFAULT NULL COMMENT '群发分组的第几批次',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `conversation_id` (`conversation_id`),
  KEY `member_id` (`member_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `creator_id` (`creator_id`),
  CONSTRAINT `mass_conversation_ibfk_1` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `mass_conversation_ibfk_2` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `mass_conversation_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `mass_conversation_ibfk_4` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群发会话';

-- ----------------------------
--  Table structure for `mass_conversation_batch_result`
-- ----------------------------
DROP TABLE IF EXISTS `mass_conversation_batch_result`;
CREATE TABLE `mass_conversation_batch_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `msg_id` varchar(50) DEFAULT NULL COMMENT '群发消息ID',
  `pi_ci` int(11) NOT NULL COMMENT '群发分组的第几批次',
  `status` tinyint(2) NOT NULL COMMENT '结果',
  `total_count` int(11) DEFAULT NULL COMMENT 'tag_id下粉丝数；或者openid_list中的粉丝数',
  `filter_count` int(11) DEFAULT NULL COMMENT '过滤后准备发送的粉丝数',
  `send_count` int(11) DEFAULT NULL COMMENT '发送成功的粉丝数',
  `error_count` int(11) DEFAULT NULL COMMENT '发送失败的粉丝数',
  `conversation_id` int(11) NOT NULL COMMENT '群发会话ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `conversation_id` (`conversation_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分批群发消息结果';

-- ----------------------------
--  Table structure for `mass_conversation_result`
-- ----------------------------
DROP TABLE IF EXISTS `mass_conversation_result`;
CREATE TABLE `mass_conversation_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `conditions` text COMMENT '条件',
  `mp_helper` varchar(50) DEFAULT NULL COMMENT '公众号群发助手的微信号',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `audit_at` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_by` bigint(11) DEFAULT NULL COMMENT '审核人',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `send_at` datetime DEFAULT NULL COMMENT '发送时间',
  `run_at` datetime DEFAULT NULL COMMENT '执行时间',
  `wx_send_at` datetime DEFAULT NULL COMMENT '微信发送回调时间',
  `task_id` int(11) DEFAULT NULL COMMENT '主键ID',
  `material_id` bigint(20) DEFAULT NULL COMMENT '素材ID',
  `msg_type` tinyint(2) NOT NULL COMMENT '消息类型',
  `event` tinyint(2) DEFAULT NULL COMMENT '事件类型',
  `total_batch` int(11) DEFAULT NULL COMMENT '群发总批次',
  `msg_id` varchar(50) DEFAULT NULL COMMENT '群发消息ID',
  `status` tinyint(2) NOT NULL COMMENT '结果',
  `total_count` int(11) DEFAULT NULL COMMENT 'tag_id下粉丝数；或者openid_list中的粉丝数',
  `filter_count` int(11) DEFAULT NULL COMMENT '过滤后准备发送的粉丝数',
  `send_count` int(11) DEFAULT NULL COMMENT '发送成功的粉丝数',
  `error_count` int(11) DEFAULT NULL COMMENT '发送失败的粉丝数',
  `conversation_id` int(11) NOT NULL COMMENT '群发会话ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `conversation_id` (`conversation_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `mass_conversation_result_ibfk_1` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`),
  CONSTRAINT `mass_conversation_result_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群发消息结果';

-- ----------------------------
--  Table structure for `material`
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `material_type` tinyint(3) NOT NULL COMMENT '素材类型(1:图文,2:图片,3:语音,4:视频,5:小视频,6:文字,7:图文图片,8:门店图片)',
  `name` varchar(255) DEFAULT '' COMMENT '名称',
  `pic_url` varchar(255) DEFAULT '' COMMENT '图片路径',
  `wx_pic_url` varchar(255) DEFAULT NULL COMMENT '微信图片路径',
  `material_image_type_id` int(11) DEFAULT NULL COMMENT '图片分类',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `material_voice_type_id` int(11) DEFAULT NULL COMMENT '语音类型',
  `voice_url` varchar(255) DEFAULT '' COMMENT '语音路径',
  `material_video_type_id` int(11) DEFAULT NULL COMMENT '视频分类',
  `video_url` varchar(255) DEFAULT '' COMMENT '视频路径',
  `video_tag` varchar(255) DEFAULT '' COMMENT '视频标签',
  `description` varchar(255) DEFAULT '' COMMENT '简介',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建人',
  `modify_by_id` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_at` datetime DEFAULT NULL COMMENT '修改时间',
  `last_push_at` datetime DEFAULT NULL COMMENT '最近同步微信时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `media_id` varchar(255) DEFAULT NULL COMMENT '微信素材的media_id',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  PRIMARY KEY (`id`,`creator_id`),
  KEY `material_image_type_id` (`material_image_type_id`),
  KEY `material_voice_type_id` (`material_voice_type_id`),
  KEY `material_video_type_id` (`material_video_type_id`),
  KEY `creator_id` (`creator_id`),
  KEY `modify_by_id` (`modify_by_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `id` (`id`),
  CONSTRAINT `material_ibfk_1` FOREIGN KEY (`material_image_type_id`) REFERENCES `material_image_type` (`id`),
  CONSTRAINT `material_ibfk_2` FOREIGN KEY (`material_voice_type_id`) REFERENCES `material_voice_type` (`id`),
  CONSTRAINT `material_ibfk_3` FOREIGN KEY (`material_video_type_id`) REFERENCES `material_video_type` (`id`),
  CONSTRAINT `material_ibfk_5` FOREIGN KEY (`modify_by_id`) REFERENCES `user` (`id`),
  CONSTRAINT `material_ibfk_6` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='素材';

-- ----------------------------
--  Table structure for `material_image_text_detail`
-- ----------------------------
DROP TABLE IF EXISTS `material_image_text_detail`;
CREATE TABLE `material_image_text_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `author` varchar(64) DEFAULT '' COMMENT '作者',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '正文',
  `content_source_checked` bit(1) DEFAULT NULL COMMENT '原文链接是否存在',
  `content_source_url` varchar(255) DEFAULT '' COMMENT '原文链接',
  `show_cover` bit(1) DEFAULT NULL COMMENT '封面图片显示在正文',
  `material_cover_id` int(11) DEFAULT NULL COMMENT '封面图片素材ID',
  `summary` varchar(120) DEFAULT '' COMMENT '摘要',
  `material_id` int(11) DEFAULT NULL COMMENT '素材ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  `sequence` int(11) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `material_id` (`material_id`),
  CONSTRAINT `material_image_text_detail_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `material_image_type`
-- ----------------------------
DROP TABLE IF EXISTS `material_image_type`;
CREATE TABLE `material_image_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '图片分组名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建人',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `material_image_type_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `material_image_type` (`id`),
  CONSTRAINT `material_image_type_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `material_image_type_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `material_text_detail`
-- ----------------------------
DROP TABLE IF EXISTS `material_text_detail`;
CREATE TABLE `material_text_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `material_id` int(11) DEFAULT NULL COMMENT '素材ID',
  `content` longtext COLLATE utf8mb4_unicode_ci COMMENT '文字内容',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `material_id` (`material_id`),
  KEY `material_text_detail_ibfk_2` (`wechat_id`),
  CONSTRAINT `material_text_detail_ibfk_1` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`),
  CONSTRAINT `material_text_detail_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `material_video_type`
-- ----------------------------
DROP TABLE IF EXISTS `material_video_type`;
CREATE TABLE `material_video_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) DEFAULT '' COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `material_video_type_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `material_video_type` (`id`),
  CONSTRAINT `material_video_type_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `material_video_type_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `material_voice_type`
-- ----------------------------
DROP TABLE IF EXISTS `material_voice_type`;
CREATE TABLE `material_voice_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) DEFAULT '' COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `material_voice_type_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `material_voice_type` (`id`),
  CONSTRAINT `material_voice_type_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `material_voice_type_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member`
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `union_id` varchar(50) DEFAULT '' COMMENT 'unionId',
  `open_id` varchar(50) NOT NULL DEFAULT '' COMMENT 'openId',
  `nickname` varchar(255) DEFAULT '' COMMENT '昵称',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别',
  `language` tinyint(2) DEFAULT NULL COMMENT '语言',
  `city` int(11) DEFAULT NULL COMMENT '城市',
  `province` int(11) DEFAULT NULL COMMENT '省份',
  `country` int(11) DEFAULT NULL COMMENT '国家',
  `local_head_img_url` varchar(256) DEFAULT '' COMMENT '本地头像地址',
  `head_img_url` varchar(256) DEFAULT '' COMMENT '微信头像地址',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `wechat_id` int(20) NOT NULL COMMENT '所属公众号ID',
  `member_group_id` int(11) DEFAULT NULL COMMENT '用户分组ID',
  `activity` tinyint(4) DEFAULT NULL COMMENT '活跃度',
  `batchsend_month` int(8) DEFAULT NULL COMMENT '本月群发数量',
  `subscribe_at` datetime DEFAULT NULL COMMENT '关注时间',
  `unsubscribe_at` datetime DEFAULT NULL COMMENT '取消关注时间',
  `fromwhere` varchar(255) DEFAULT NULL COMMENT '来源',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_subscribe` bit(1) DEFAULT NULL COMMENT '是否关注(1:关注,0:取消关注)',
  `last_conversation_at` datetime DEFAULT NULL COMMENT '最后一次会话时间',
  `source` varchar(255) DEFAULT NULL COMMENT '来源',
  `keyword` varchar(255) DEFAULT NULL COMMENT '关键词',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wechat_open_id` (`open_id`,`wechat_id`) USING BTREE,
  KEY `wechat_id` (`wechat_id`),
  KEY `city` (`city`),
  KEY `province` (`province`),
  KEY `country` (`country`),
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `member_ibfk_2` FOREIGN KEY (`city`) REFERENCES `area_info` (`id`),
  CONSTRAINT `member_ibfk_3` FOREIGN KEY (`province`) REFERENCES `area_info` (`id`),
  CONSTRAINT `member_ibfk_4` FOREIGN KEY (`country`) REFERENCES `area_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
--  Table structure for `member_click_menu`
-- ----------------------------
DROP TABLE IF EXISTS `member_click_menu`;
CREATE TABLE `member_click_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `open_id` varchar(50) DEFAULT NULL,
  `union_id` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  `menu_group_id` int(11) DEFAULT NULL COMMENT '菜单组ID',
  `wechat_id` int(11) DEFAULT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `menu_id` (`menu_id`),
  KEY `menu_group_id` (`menu_group_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `member_click_menu_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `member_click_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `member_click_menu_ibfk_3` FOREIGN KEY (`menu_group_id`) REFERENCES `menu_group` (`id`),
  CONSTRAINT `member_click_menu_ibfk_4` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单点击';

-- ----------------------------
--  Table structure for `member_group`
-- ----------------------------
DROP TABLE IF EXISTS `member_group`;
CREATE TABLE `member_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '名称',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `modify_at` datetime DEFAULT NULL COMMENT '更新时间',
  `push_at` datetime DEFAULT NULL COMMENT '微信同步推送时间',
  `wx_group_id` int(11) DEFAULT NULL COMMENT '微信用户组ID',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_member_tag`
-- ----------------------------
DROP TABLE IF EXISTS `member_member_tag`;
CREATE TABLE `member_member_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `member_tag_id` int(11) NOT NULL COMMENT '会员分组ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `member_tag_id` (`member_tag_id`),
  CONSTRAINT `member_member_tag_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `member_member_tag_ibfk_2` FOREIGN KEY (`member_tag_id`) REFERENCES `member_tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_profile`
-- ----------------------------
DROP TABLE IF EXISTS `member_profile`;
CREATE TABLE `member_profile` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `card_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员卡号',
  `card_type` tinyint(2) DEFAULT NULL COMMENT '会员卡类型',
  `issue_date` datetime DEFAULT NULL COMMENT '到期时间',
  `card_status` tinyint(2) DEFAULT NULL COMMENT '会员卡状态',
  `store_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '门店ID',
  `credits` int(11) DEFAULT NULL COMMENT '积分',
  `level` varchar(20) DEFAULT NULL COMMENT '等级',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员姓名',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员地址',
  `birth_date` date DEFAULT NULL COMMENT '会员生日',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `wechat_id` int(20) NOT NULL COMMENT '所属公众号ID',
  `accept_promotion` bit(1) DEFAULT NULL COMMENT '接受促销信息',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(2) DEFAULT NULL COMMENT '绑定状态(0:已解绑,1:已绑定)',
  `bind_at` datetime DEFAULT NULL COMMENT '绑定时间',
  `unbund_at` datetime DEFAULT NULL COMMENT '解绑时间',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_reply`
-- ----------------------------
DROP TABLE IF EXISTS `member_reply`;
CREATE TABLE `member_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `reply_id` int(11) DEFAULT NULL COMMENT '自动回复ID',
  `conversation_id` int(11) NOT NULL COMMENT '会话ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `reply_id` (`reply_id`),
  KEY `conversation_id` (`conversation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_scan_qrcode`
-- ----------------------------
DROP TABLE IF EXISTS `member_scan_qrcode`;
CREATE TABLE `member_scan_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `open_id` varchar(50) DEFAULT NULL,
  `union_id` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `is_subscribe` tinyint(1) DEFAULT NULL COMMENT '扫码时的关注状态',
  `wechat_id` int(11) DEFAULT NULL COMMENT '公众号ID',
  `qrcode_id` int(11) DEFAULT NULL COMMENT '二维码ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `qrcode_id` (`qrcode_id`),
  CONSTRAINT `member_scan_qrcode_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `member_scan_qrcode_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `member_scan_qrcode_ibfk_3` FOREIGN KEY (`qrcode_id`) REFERENCES `qrcode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_subscribe`
-- ----------------------------
DROP TABLE IF EXISTS `member_subscribe`;
CREATE TABLE `member_subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `subscribe` tinyint(4) NOT NULL COMMENT '是否关注(0:已关注,1:未关注)',
  `subscribe_at` datetime NOT NULL COMMENT '关注/取消关注时间',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `subscribe_ip` varchar(20) DEFAULT NULL COMMENT 'ip',
  `subscribe_by` tinyint(2) DEFAULT NULL COMMENT '关注方式',
  `qrcode_id` int(11) DEFAULT NULL COMMENT '二维码ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `qrcode_id` (`qrcode_id`),
  CONSTRAINT `member_subscribe_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `member_subscribe_ibfk_2` FOREIGN KEY (`qrcode_id`) REFERENCES `qrcode` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_tag`
-- ----------------------------
DROP TABLE IF EXISTS `member_tag`;
CREATE TABLE `member_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `member_tag_type_id` int(11) DEFAULT NULL COMMENT '会员标签类型',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  PRIMARY KEY (`id`),
  KEY `member_tag_type_id` (`member_tag_type_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `member_tag_ibfk_1` FOREIGN KEY (`member_tag_type_id`) REFERENCES `member_tag_type` (`id`),
  CONSTRAINT `member_tag_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `member_tag_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_tag_csv`
-- ----------------------------
DROP TABLE IF EXISTS `member_tag_csv`;
CREATE TABLE `member_tag_csv` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ori_file` varchar(255) NOT NULL COMMENT '原始文件名',
  `csv` varchar(255) NOT NULL COMMENT '导入加会员标签文件',
  `exception` varchar(255) DEFAULT NULL COMMENT '异常信息文件',
  `status` tinyint(2) NOT NULL COMMENT '处理状态（0-未处理，1-进行中，2-处理完成）',
  `task` varchar(255) NOT NULL COMMENT '任务名称',
  `data` text COMMENT '数据',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `creator_id` int(11) NOT NULL COMMENT '创建人ID',
  `created_at` datetime NOT NULL COMMENT '任务执行时间',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `member_tag_csv_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='导入CSV批量加会员标签';

-- ----------------------------
--  Table structure for `member_tag_type`
-- ----------------------------
DROP TABLE IF EXISTS `member_tag_type`;
CREATE TABLE `member_tag_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `user_id` (`creator_id`),
  CONSTRAINT `member_tag_type_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `member_tag_type_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `member_wx_member_tag`
-- ----------------------------
DROP TABLE IF EXISTS `member_wx_member_tag`;
CREATE TABLE `member_wx_member_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `wx_member_tag_id` int(11) NOT NULL COMMENT '微信会员标签ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `wx_member_tag_id` (`wx_member_tag_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `member_wx_member_tag_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `member_wx_member_tag_ibfk_2` FOREIGN KEY (`wx_member_tag_id`) REFERENCES `wx_member_tag` (`id`),
  CONSTRAINT `member_wx_member_tag_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信会员标签';

-- ----------------------------
--  Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(40) NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(2) DEFAULT NULL COMMENT '动作类型',
  `menu_key` int(11) DEFAULT NULL COMMENT '点击类型KEY值',
  `url` varchar(1024) DEFAULT NULL COMMENT '网页链接',
  `api_class` varchar(255) DEFAULT NULL COMMENT 'API处理全类名',
  `media_id` int(11) DEFAULT NULL COMMENT '永久素材mediaId',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `modify_at` datetime DEFAULT NULL COMMENT '更新时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `menu_group_id` int(11) DEFAULT NULL COMMENT '菜单组ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级菜单ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  `deletor_id` int(11) DEFAULT NULL COMMENT '删除用户ID',
  `seq` int(11) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `menu_key` (`menu_key`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `menu_group_id` (`menu_group_id`),
  KEY `parent_id` (`parent_id`),
  KEY `deletor_id` (`deletor_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`menu_key`) REFERENCES `material` (`id`),
  CONSTRAINT `menu_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `menu_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `menu_ibfk_4` FOREIGN KEY (`menu_group_id`) REFERENCES `menu_group` (`id`),
  CONSTRAINT `menu_ibfk_5` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `menu_ibfk_6` FOREIGN KEY (`deletor_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `menu_group`
-- ----------------------------
DROP TABLE IF EXISTS `menu_group`;
CREATE TABLE `menu_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `menu_rule_id` int(11) DEFAULT NULL COMMENT '个性化菜单规则ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `modify_at` datetime DEFAULT NULL COMMENT '更新时间',
  `push_at` datetime DEFAULT NULL COMMENT '微信同步推送时间',
  `wx_menu_id` varchar(50) DEFAULT NULL COMMENT '微信菜单组ID',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  `deletor_id` int(11) DEFAULT NULL COMMENT '删除用户ID',
  PRIMARY KEY (`id`),
  KEY `menu_rule_id` (`menu_rule_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `deletor_id` (`deletor_id`),
  CONSTRAINT `menu_group_ibfk_1` FOREIGN KEY (`menu_rule_id`) REFERENCES `menu_rule` (`id`),
  CONSTRAINT `menu_group_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `menu_group_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `menu_group_ibfk_4` FOREIGN KEY (`deletor_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `menu_rule`
-- ----------------------------
DROP TABLE IF EXISTS `menu_rule`;
CREATE TABLE `menu_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` int(11) DEFAULT '0' COMMENT '用户组ID',
  `sex` tinyint(2) DEFAULT NULL COMMENT '性别(1:男性，2:女性，0:未知)',
  `client_platform_type` tinyint(2) DEFAULT NULL COMMENT '客户端版本',
  `country` int(11) DEFAULT NULL COMMENT '国家',
  `province` int(11) DEFAULT NULL COMMENT '省份',
  `city` int(11) DEFAULT NULL COMMENT '城市',
  `language` tinyint(2) DEFAULT NULL COMMENT '语言',
  `tag_id` int(11) DEFAULT NULL COMMENT '微信标签id',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `menu_group_id` int(11) NOT NULL COMMENT '菜单组ID',
  PRIMARY KEY (`id`),
  KEY `menu_group_id` (`menu_group_id`),
  KEY `country` (`country`),
  KEY `province` (`province`),
  KEY `city` (`city`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `menu_rule_ibfk_1` FOREIGN KEY (`menu_group_id`) REFERENCES `menu_group` (`id`),
  CONSTRAINT `menu_rule_ibfk_2` FOREIGN KEY (`country`) REFERENCES `area_info` (`id`),
  CONSTRAINT `menu_rule_ibfk_3` FOREIGN KEY (`province`) REFERENCES `area_info` (`id`),
  CONSTRAINT `menu_rule_ibfk_4` FOREIGN KEY (`city`) REFERENCES `area_info` (`id`),
  CONSTRAINT `menu_rule_ibfk_5` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `menu_rule_ibfk_6` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='个性化菜单规则';

-- ----------------------------
--  Table structure for `oauth_url`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_url`;
CREATE TABLE `oauth_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `short_url` varchar(30) DEFAULT NULL COMMENT '短地址',
  `scope` varchar(30) NOT NULL COMMENT '授权作用域：snsapi_base，snsapi_userinfo',
  `process_class` varchar(200) DEFAULT NULL COMMENT '处理全类名',
  `params` longtext COMMENT '参数，Json对象',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `offline_activity`
-- ----------------------------
DROP TABLE IF EXISTS `offline_activity`;
CREATE TABLE `offline_activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(30) DEFAULT NULL COMMENT '离线活动编号',
  `name` varchar(255) NOT NULL COMMENT '线下活动名称',
  `description` varchar(255) DEFAULT NULL COMMENT '线下活动描述',
  `start_date` datetime NOT NULL COMMENT '线下活动开始时间',
  `end_date` datetime NOT NULL COMMENT '线下活动结束时间',
  `share_pic` varchar(255) DEFAULT NULL COMMENT '分享封面',
  `share_title` varchar(255) DEFAULT NULL COMMENT '分享标题',
  `share_description` varchar(255) DEFAULT NULL COMMENT '分享描述',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(2) NOT NULL COMMENT '状态（0-删除，1-使用）',
  `creator_id` int(11) NOT NULL COMMENT '创建人ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `offline_activity_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='线下活动表';

-- ----------------------------
--  Table structure for `offline_activity_business`
-- ----------------------------
DROP TABLE IF EXISTS `offline_activity_business`;
CREATE TABLE `offline_activity_business` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `offline_activity_id` int(11) NOT NULL COMMENT '线下活动ID',
  `business_id` int(11) NOT NULL COMMENT '门店ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `creator_id` int(11) NOT NULL COMMENT '创建者ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `offline_activity_id` (`offline_activity_id`) USING BTREE,
  KEY `business_id` (`business_id`) USING BTREE,
  CONSTRAINT `offline_activity_business_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `offline_activity_business_ibfk_2` FOREIGN KEY (`offline_activity_id`) REFERENCES `offline_activity` (`id`),
  CONSTRAINT `offline_activity_business_ibfk_3` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='线下活动与门店关联表';

-- ----------------------------
--  Table structure for `original_conversation`
-- ----------------------------
DROP TABLE IF EXISTS `original_conversation`;
CREATE TABLE `original_conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `wechat_id` int(11) DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL COMMENT '状态',
  `execute_start` datetime DEFAULT NULL COMMENT '会话处理开始时间',
  `execute_end` datetime DEFAULT NULL COMMENT '会话处理结束时间',
  `execute_millis` int(11) DEFAULT NULL COMMENT '会话处理时间(毫秒)',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `original_conversation_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=195340 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='原始未处理的xml会话';

-- ----------------------------
--  Table structure for `popup_config`
-- ----------------------------
DROP TABLE IF EXISTS `popup_config`;
CREATE TABLE `popup_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wechat_id` int(20) NOT NULL,
  `type` varchar(100) NOT NULL,
  `val` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_country_area`
-- ----------------------------
DROP TABLE IF EXISTS `popup_country_area`;
CREATE TABLE `popup_country_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL COMMENT '政府区域编码',
  `name_zh` varchar(20) NOT NULL,
  `name_en` varchar(50) DEFAULT NULL,
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_goods`
-- ----------------------------
DROP TABLE IF EXISTS `popup_goods`;
CREATE TABLE `popup_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wechat_id` int(20) NOT NULL,
  `img_urls` varchar(500) NOT NULL COMMENT '商品图片集，json格式：[{bigImg:'''',img:''''},]',
  `name` varchar(255) DEFAULT NULL COMMENT '简称',
  `price` int(11) DEFAULT NULL COMMENT '价格，单位：分',
  `count` int(11) DEFAULT NULL,
  `desc` varchar(2000) DEFAULT NULL COMMENT '商品描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(100) NOT NULL DEFAULT '0' COMMENT '商品状态：默认0新建，1在售，-1下架',
  `category_id` bigint(20) NOT NULL DEFAULT '0',
  `type` varchar(100) NOT NULL DEFAULT '1' COMMENT '商品类型：默认1实物',
  `goods_no` varchar(50) DEFAULT NULL COMMENT '货号，spu概念',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_goods_prop_rel`
-- ----------------------------
DROP TABLE IF EXISTS `popup_goods_prop_rel`;
CREATE TABLE `popup_goods_prop_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `goods_id` bigint(20) NOT NULL,
  `prop_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_order`
-- ----------------------------
DROP TABLE IF EXISTS `popup_order`;
CREATE TABLE `popup_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` int(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `address` varchar(1000) DEFAULT NULL,
  `invoice_address` varchar(1000) DEFAULT NULL,
  `invoice_open` smallint(1) NOT NULL DEFAULT '0' COMMENT '0不需要，1需要',
  `invoice_info` varchar(1000) DEFAULT NULL COMMENT '发票内容，json格式：{title:''抬头'',no:''纳税人识别号'',code:''统一社会信用代码''}',
  `invoice_type` varchar(100) DEFAULT NULL COMMENT '类型：1个人，2公司',
  `invoice_prop` varchar(100) NOT NULL DEFAULT '1' COMMENT '发票性质：1增值税普通发票',
  `gift_content` varchar(255) DEFAULT NULL,
  `msm_open` smallint(1) NOT NULL DEFAULT '0',
  `msm_phone` varchar(20) DEFAULT NULL,
  `delivery_type` varchar(100) DEFAULT NULL COMMENT '类型：1工作日，2非工作日',
  `delivery_date` datetime DEFAULT NULL COMMENT '送货日期',
  `wechat_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_order_goods_rel`
-- ----------------------------
DROP TABLE IF EXISTS `popup_order_goods_rel`;
CREATE TABLE `popup_order_goods_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `order_id` bigint(20) NOT NULL,
  `goods_props` varchar(500) DEFAULT NULL COMMENT 'json数组：[{key:'''',value:''''},...]',
  `goods_name` varchar(255) DEFAULT NULL,
  `goods_id` bigint(20) NOT NULL,
  `sum` int(11) NOT NULL,
  `price` int(20) NOT NULL DEFAULT '0' COMMENT '单位：分',
  `remarks` varchar(255) DEFAULT NULL,
  `pay_type` varchar(100) DEFAULT '0' COMMENT '1微信 2支付宝 3银联',
  `pay_status` varchar(100) DEFAULT '0' COMMENT '0未支付 1已支付 2取消 3待发货 4已发货 5完成',
  `invoice_status` varchar(100) DEFAULT '0' COMMENT '0未寄出，1已寄出，2完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_pay_config`
-- ----------------------------
DROP TABLE IF EXISTS `popup_pay_config`;
CREATE TABLE `popup_pay_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `icon_url` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `config` varchar(1000) DEFAULT NULL,
  `type` varchar(100) NOT NULL COMMENT '支付类型，WECHAT_PAY|ALIPAY|UNION_PAY',
  `open` smallint(1) NOT NULL COMMENT '0未开启，1开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_pay_log`
-- ----------------------------
DROP TABLE IF EXISTS `popup_pay_log`;
CREATE TABLE `popup_pay_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `status` varchar(100) NOT NULL,
  `data` varchar(2000) DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  `pay_type` varchar(100) NOT NULL COMMENT 'WECHAT_PAY|ALIPAY|UNION_PAY',
  `member_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_prop`
-- ----------------------------
DROP TABLE IF EXISTS `popup_prop`;
CREATE TABLE `popup_prop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父选项id',
  `prop_key_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '属性id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `popup_prop_key`
-- ----------------------------
DROP TABLE IF EXISTS `popup_prop_key`;
CREATE TABLE `popup_prop_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `category_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `qrcode`
-- ----------------------------
DROP TABLE IF EXISTS `qrcode`;
CREATE TABLE `qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `qrcode_type_id` int(11) DEFAULT NULL COMMENT '二维码类型ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `summary` varchar(255) DEFAULT NULL COMMENT '简介',
  `ticket` varchar(255) NOT NULL DEFAULT '' COMMENT '二维码ticket',
  `qrcode_url` varchar(255) NOT NULL DEFAULT '' COMMENT '二维码图片解析后的地址',
  `qrcode_img_url` varchar(255) NOT NULL COMMENT '二维码本地图片地址',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `expire_seconds` int(11) DEFAULT NULL COMMENT '二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)',
  `action_name` tinyint(2) NOT NULL COMMENT '二维码类型(1:临时,2:永久)',
  `scene` varchar(255) DEFAULT NULL COMMENT '场景值ID',
  `sopport_subscribe_reply` tinyint(2) DEFAULT '0' COMMENT '是否支持关注回复(1:支持,0:不支持)',
  PRIMARY KEY (`id`),
  KEY `qrcode_ibfk_1` (`qrcode_type_id`),
  KEY `qrcode_ibfk_2` (`wechat_id`),
  KEY `creator_id` (`creator_id`),
  KEY `qrcode_create_index` (`created_at`) USING BTREE,
  CONSTRAINT `qrcode_ibfk_1` FOREIGN KEY (`qrcode_type_id`) REFERENCES `qrcode_type` (`id`),
  CONSTRAINT `qrcode_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `qrcode_ibfk_3` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `qrcode_action_engine`
-- ----------------------------
DROP TABLE IF EXISTS `qrcode_action_engine`;
CREATE TABLE `qrcode_action_engine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qrcode_id` int(11) NOT NULL COMMENT '二维码ID',
  `action_engine_id` int(11) NOT NULL COMMENT '行为引擎规则ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `qrcode_id` (`qrcode_id`),
  KEY `action_engine_id` (`action_engine_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `qrcode_action_engine_ibfk_1` FOREIGN KEY (`qrcode_id`) REFERENCES `qrcode` (`id`),
  CONSTRAINT `qrcode_action_engine_ibfk_2` FOREIGN KEY (`action_engine_id`) REFERENCES `action_engine` (`id`),
  CONSTRAINT `qrcode_action_engine_ibfk_3` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `qrcode_action_engine_ibfk_4` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `qrcode_type`
-- ----------------------------
DROP TABLE IF EXISTS `qrcode_type`;
CREATE TABLE `qrcode_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `qrcode_type_ibfk_1` (`parent_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `qrcode_type_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `qrcode_type` (`id`),
  CONSTRAINT `qrcode_type_ibfk_2` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `qrcode_type_ibfk_3` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `reply_type` tinyint(2) NOT NULL COMMENT '回复类型',
  `match_mode` tinyint(2) DEFAULT NULL COMMENT '匹配模式',
  `reply_key` varchar(255) DEFAULT NULL COMMENT '回复关键字',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建者',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `weight` int(11) DEFAULT NULL COMMENT '权重',
  PRIMARY KEY (`id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='回复';

-- ----------------------------
--  Table structure for `reply_action_engine`
-- ----------------------------
DROP TABLE IF EXISTS `reply_action_engine`;
CREATE TABLE `reply_action_engine` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reply_id` int(11) NOT NULL COMMENT '回复ID',
  `action_engine_id` int(11) NOT NULL COMMENT '行为引擎规则ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `reply_id` (`reply_id`),
  KEY `action_engine_id` (`action_engine_id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `reply_action_engine_ibfk_1` FOREIGN KEY (`reply_id`) REFERENCES `reply` (`id`),
  CONSTRAINT `reply_action_engine_ibfk_2` FOREIGN KEY (`action_engine_id`) REFERENCES `action_engine` (`id`),
  CONSTRAINT `reply_action_engine_ibfk_3` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `reply_action_engine_ibfk_4` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `reply_words`
-- ----------------------------
DROP TABLE IF EXISTS `reply_words`;
CREATE TABLE `reply_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `reply_id` int(11) NOT NULL COMMENT '自动回复ID',
  `reply_word` varchar(255) NOT NULL COMMENT ' 关键词',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `reply_id` (`reply_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关键词表';

-- ----------------------------
--  Table structure for `report_article_hour_source`
-- ----------------------------
DROP TABLE IF EXISTS `report_article_hour_source`;
CREATE TABLE `report_article_hour_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ref_date` varchar(10) NOT NULL COMMENT '群发日期',
  `ref_hour` varchar(10) NOT NULL COMMENT '群发时段',
  `user_source` int(20) DEFAULT NULL COMMENT '用户阅读来源(0:会话;1.好友;2.朋友圈;3.腾讯微博;4.历史消息页;5.其他)',
  `page_user` int(20) DEFAULT NULL COMMENT '图文页阅读人数',
  `page_count` int(20) DEFAULT NULL COMMENT '图文页阅读次数',
  `ori_page_user` int(20) DEFAULT NULL COMMENT '原文页阅读人数',
  `ori_page_count` int(20) DEFAULT NULL COMMENT '原文页阅读次数',
  `add_fav_user` int(20) DEFAULT NULL COMMENT '收藏人数',
  `add_fav_count` int(20) DEFAULT NULL COMMENT '收藏次数',
  `share_user` int(20) DEFAULT NULL COMMENT '分享人数',
  `share_count` int(20) DEFAULT NULL COMMENT '分享次数',
  `wechat_id` int(20) NOT NULL COMMENT '所属公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `report_article_source`
-- ----------------------------
DROP TABLE IF EXISTS `report_article_source`;
CREATE TABLE `report_article_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `msgid` varchar(20) NOT NULL COMMENT '图文消息ID',
  `title` varchar(100) DEFAULT NULL COMMENT ' 图文消息标题',
  `ref_date` varchar(10) NOT NULL COMMENT '群发日期',
  `page_user` int(20) DEFAULT NULL COMMENT '图文页阅读人数',
  `page_count` int(20) DEFAULT NULL COMMENT '图文页阅读次数',
  `ori_page_user` int(20) DEFAULT NULL COMMENT '原文页阅读人数',
  `ori_page_count` int(20) DEFAULT NULL COMMENT '原文页阅读次数',
  `add_fav_user` int(20) DEFAULT NULL COMMENT '收藏人数',
  `add_fav_count` int(20) DEFAULT NULL COMMENT '收藏次数',
  `share_user` int(20) DEFAULT NULL COMMENT '分享人数',
  `share_count` int(20) DEFAULT NULL COMMENT '分享次数',
  `wechat_id` int(20) NOT NULL COMMENT '所属公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `report_config`
-- ----------------------------
DROP TABLE IF EXISTS `report_config`;
CREATE TABLE `report_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_key` varchar(50) NOT NULL COMMENT '报表索引键',
  `report_name` varchar(100) DEFAULT NULL COMMENT '导出的文件名',
  `sheet_key` varchar(50) NOT NULL COMMENT '报表索引键',
  `sheet_name` varchar(50) DEFAULT NULL COMMENT '工作表名',
  `report_sql` varchar(2000) NOT NULL COMMENT '报表SQL',
  `wechat_id` int(11) NOT NULL DEFAULT '0' COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `report_user_source`
-- ----------------------------
DROP TABLE IF EXISTS `report_user_source`;
CREATE TABLE `report_user_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `date` varchar(10) NOT NULL COMMENT '日期',
  `new_user_0` int(20) DEFAULT '0' COMMENT '其他合计-新增的用户数量',
  `cancel_user_0` int(20) DEFAULT '0' COMMENT '其他合计-取消关注的用户数量',
  `new_user_1` int(20) DEFAULT '0' COMMENT '公众号搜索-新增的用户数量',
  `cancel_user_1` int(20) DEFAULT '0' COMMENT '公众号搜索-取消关注的用户数量',
  `new_user_17` int(20) DEFAULT '0' COMMENT '名片分享-新增的用户数量',
  `cancel_user_17` int(20) DEFAULT '0' COMMENT '名片分享-取消关注的用户数量',
  `new_user_30` int(20) DEFAULT '0' COMMENT '扫描二维码-新增的用户数量',
  `cancel_user_30` int(20) DEFAULT '0' COMMENT '扫描二维码-取消关注的用户数量',
  `new_user_43` int(20) DEFAULT '0' COMMENT '图文页右上角菜单-新增的用户数量',
  `cancel_user_43` int(20) DEFAULT '0' COMMENT '图文页右上角菜单-取消关注的用户数量',
  `new_user_51` int(20) DEFAULT '0' COMMENT '支付后关注（在支付完成页）-新增的用户数量',
  `cancel_user_51` int(20) DEFAULT '0' COMMENT '支付后关注（在支付完成页）-取消关注的用户数量',
  `new_user_57` int(20) DEFAULT '0' COMMENT '图文页内公众号名称-新增的用户数量',
  `cancel_user_57` int(20) DEFAULT '0' COMMENT '图文页内公众号名称-取消关注的用户数量',
  `new_user_75` int(20) DEFAULT '0' COMMENT '公众号文章广告-新增的用户数量',
  `cancel_user_75` int(20) DEFAULT '0' COMMENT '公众号文章广告-取消关注的用户数量',
  `new_user_78` int(20) DEFAULT '0' COMMENT '朋友圈广告-新增的用户数量',
  `cancel_user_78` int(20) DEFAULT '0' COMMENT '朋友圈广告-取消关注的用户数量',
  `cumulate_user` int(20) DEFAULT '0' COMMENT '总用户数量',
  `wechat_id` int(20) NOT NULL COMMENT '所属公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `function_id` int(11) DEFAULT NULL COMMENT '权限ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司ID',
  `isSystemRole` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否是系统管理员',
  PRIMARY KEY (`id`),
  KEY `function_id` (`function_id`) USING BTREE,
  KEY `company_id` (`company_id`),
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`function_id`) REFERENCES `function` (`id`),
  CONSTRAINT `role_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `role_function`
-- ----------------------------
DROP TABLE IF EXISTS `role_function`;
CREATE TABLE `role_function` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `function_id` int(11) DEFAULT NULL COMMENT '功能ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `function_id` (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `session`
-- ----------------------------
DROP TABLE IF EXISTS `session`;
CREATE TABLE `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `offline_activity_id` int(11) DEFAULT NULL COMMENT '线下活动ID',
  `session` varchar(20) NOT NULL COMMENT '场次',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `member_limit` int(11) NOT NULL COMMENT '人数上限',
  `apply` int(11) DEFAULT '0' COMMENT '统计报名人数',
  `status` tinyint(2) NOT NULL COMMENT '场次状态(0-删除,1-进行中,2-已暂停)',
  `creator_id` int(11) NOT NULL COMMENT '创建者ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `offline_activity_id` (`offline_activity_id`),
  CONSTRAINT `session_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `session_ibfk_2` FOREIGN KEY (`offline_activity_id`) REFERENCES `offline_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='线下活动报名场次表';

-- ----------------------------
--  Table structure for `session_member`
-- ----------------------------
DROP TABLE IF EXISTS `session_member`;
CREATE TABLE `session_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `business_id` int(11) NOT NULL COMMENT '报名门店ID',
  `offline_activity_id` int(11) NOT NULL COMMENT '线下活动ID',
  `session_id` int(11) NOT NULL COMMENT '报名场次ID',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机电话',
  `open_id` varchar(255) DEFAULT NULL COMMENT '报名者OpenID',
  `created_at` datetime NOT NULL COMMENT '报名时间',
  `province` int(11) DEFAULT NULL COMMENT '省份',
  `city` int(11) DEFAULT NULL COMMENT '城市',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `business_id` (`business_id`),
  KEY `offline_activity_id` (`offline_activity_id`),
  KEY `session_id` (`session_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `session_member_ibfk_2` FOREIGN KEY (`business_id`) REFERENCES `business` (`id`),
  CONSTRAINT `session_member_ibfk_3` FOREIGN KEY (`offline_activity_id`) REFERENCES `offline_activity` (`id`),
  CONSTRAINT `session_member_ibfk_4` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`),
  CONSTRAINT `session_member_ibfk_5` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='线下活动报名用户表';

-- ----------------------------
--  Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cate_id` int(11) NOT NULL COMMENT '创建用户ID',
  `task_name` varchar(50) NOT NULL COMMENT '任务分类名称',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `stateful` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否有状态(0:无,1:有)',
  `cron_expression` varchar(100) NOT NULL COMMENT '时间表达式',
  `last_exec_status` tinyint(2) DEFAULT '0' COMMENT '最后执行状态(0:失败,1:成功)',
  `last_exec_error` text COLLATE utf8_unicode_ci,
  `last_exec_time` datetime DEFAULT NULL COMMENT '最后执行时间',
  `next_exec_time` datetime DEFAULT NULL COMMENT '下次执行时间',
  `params` longtext COMMENT '任务上下文参数值',
  `status` varchar(20) DEFAULT NULL COMMENT '任务状态',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `task_category`
-- ----------------------------
DROP TABLE IF EXISTS `task_category`;
CREATE TABLE `task_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cate_name` varchar(50) NOT NULL COMMENT '任务分类名称',
  `task_class` varchar(200) NOT NULL COMMENT '任务执行类',
  `params` longtext COMMENT '任务上下文参数定义',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `template`
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_id` varchar(255) NOT NULL COMMENT '微信模板ID',
  `title` varchar(255) NOT NULL COMMENT '模板标题',
  `primary_industry` varchar(20) NOT NULL COMMENT '模板所属行业的一级行业',
  `deputy_industry` varchar(20) NOT NULL COMMENT '模板所属行业的二级行业',
  `content` text NOT NULL COMMENT '模板内容',
  `parameters` varchar(255) NOT NULL COMMENT '模板参数',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) NOT NULL COMMENT '模板使用状态（0-停用，1-使用）',
  `example` text COMMENT '模板示例',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `template_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模板消息';

-- ----------------------------
--  Table structure for `template_industry`
-- ----------------------------
DROP TABLE IF EXISTS `template_industry`;
CREATE TABLE `template_industry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `primary_first` varchar(255) DEFAULT NULL COMMENT '主营行业一级行业',
  `primary_second` varchar(255) DEFAULT NULL COMMENT '主营行业二级行业',
  `secondary_first` varchar(255) DEFAULT NULL COMMENT '副营行业一级行业',
  `secondary_second` varchar(255) DEFAULT NULL COMMENT '副营行业二级行业',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`) USING BTREE,
  CONSTRAINT `template_industry_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模板行业';

-- ----------------------------
--  Table structure for `template_result`
-- ----------------------------
DROP TABLE IF EXISTS `template_result`;
CREATE TABLE `template_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `open_id` varchar(255) NOT NULL COMMENT 'openID',
  `data` text NOT NULL COMMENT '发送内容',
  `template_id` varchar(255) NOT NULL COMMENT '微信消息模板ID',
  `url` varchar(255) DEFAULT NULL COMMENT '模板跳转链接',
  `push_at` datetime NOT NULL COMMENT '发送时间',
  `msg_id` varchar(255) NOT NULL COMMENT '发送返回消息ID',
  `create_at` datetime DEFAULT NULL COMMENT '微信实际创建时间',
  `status` tinyint(2) DEFAULT NULL COMMENT '发送状态（1-发送成功，0-发送失败）',
  `msg` varchar(255) DEFAULT NULL COMMENT '回文消息',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `template_result_ibfk_1` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='模板消息发送统计表';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `nickname` varchar(0) DEFAULT NULL COMMENT '昵称',
  `kf_id` varchar(50) DEFAULT '' COMMENT '客服工号',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '微信头像地址',
  `local_head_img_url` varchar(255) DEFAULT NULL COMMENT '本地头像地址',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `modify_at` datetime DEFAULT NULL COMMENT '更新时间',
  `push_at` datetime DEFAULT NULL COMMENT '微信同步推送时间',
  `wechat_id` int(11) DEFAULT NULL COMMENT '公众号ID',
  `position` varchar(255) DEFAULT NULL COMMENT '职位',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `last_login_at` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '用户存在-1，用户删除-0',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司ID',
  `counter` int(11) DEFAULT '0' COMMENT '登录失败计数器',
  PRIMARY KEY (`id`),
  KEY `creator_id` (`creator_id`),
  KEY `wechat_id` (`wechat_id`),
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `company_id` (`company_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`),
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `user_ibfk_4` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `user_wechat`
-- ----------------------------
DROP TABLE IF EXISTS `user_wechat`;
CREATE TABLE `user_wechat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `is_use` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否在使用（0-未使用，1-正使用）',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `user_wechat_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_wechat_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `verify_token`
-- ----------------------------
DROP TABLE IF EXISTS `verify_token`;
CREATE TABLE `verify_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `member_id` int(11) NOT NULL COMMENT '会员ID',
  `token` varchar(255) NOT NULL COMMENT 'token',
  `code` varchar(255) NOT NULL COMMENT 'code',
  `verified` bit(1) NOT NULL COMMENT '是否已验证',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `expired_on` datetime NOT NULL COMMENT '失效时间',
  `accept` varchar(255) NOT NULL COMMENT '接受对象(手机或者邮箱)',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `wechat_id` (`wechat_id`),
  CONSTRAINT `verify_token_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `verify_token_ibfk_2` FOREIGN KEY (`wechat_id`) REFERENCES `wechat` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='验证码';

-- ----------------------------
--  Table structure for `wechat`
-- ----------------------------
DROP TABLE IF EXISTS `wechat`;
CREATE TABLE `wechat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `appid` varchar(255) DEFAULT NULL COMMENT 'appid',
  `appscret` varchar(255) DEFAULT NULL COMMENT 'appscret',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `token` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `encoding_aes_key` varchar(255) DEFAULT NULL,
  `open_id` varchar(100) DEFAULT NULL COMMENT '开发者微信号',
  `status` tinyint(2) DEFAULT '0',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '微信公众号头像地址',
  `company_id` int(11) DEFAULT NULL COMMENT '公司ID',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `wechat_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `wechat_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ;

-- ----------------------------
--  Table structure for `wx_member_tag`
-- ----------------------------
DROP TABLE IF EXISTS `wx_member_tag`;
CREATE TABLE `wx_member_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `wx_id` int(11) DEFAULT NULL COMMENT '标签ID',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `creator_at` datetime DEFAULT NULL COMMENT '创建时间',
  `wechat_id` int(11) DEFAULT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='微信标签';

SET FOREIGN_KEY_CHECKS = 1;
