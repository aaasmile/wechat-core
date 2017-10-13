-- 20170713 add
ALTER TABLE `member` ADD COLUMN `medium` varchar(255) DEFAULT '' COMMENT '媒介' AFTER `last_conversation_at`;
ALTER TABLE `conversation_image_text_detail` CHANGE COLUMN `content_source_url` `content_source_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原文链接';
ALTER TABLE `material_image_text_detail` CHANGE COLUMN `content_source_url` `content_source_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '原文链接';

-- 20170725 add , popup-store 增加限购数量和安全库存字段
ALTER TABLE `popup_goods` ADD COLUMN `limit_count` int DEFAULT '-1' COMMENT '限购数量' AFTER `count`;
ALTER TABLE `popup_goods` ADD COLUMN `safety_stock` int DEFAULT '0' COMMENT '安全库存';

-- 20170802 add , popup-store 增加物料评论
ALTER TABLE `material` ADD COLUMN `comment` tinyint DEFAULT '0' COMMENT '0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论';

-- 20170814 add , popup-store 增加物料评论
ALTER TABLE `material` DROP COLUMN `comment`;
ALTER TABLE `material_image_text_detail` ADD COLUMN `comment` tinyint DEFAULT '0' COMMENT '0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论';

-- 20170816 add , 优化分组群发
ALTER TABLE `mass_conversation_batch_result` ADD COLUMN `msg_type` tinyint DEFAULT NULL COMMENT '群发消息类型' AFTER `status`;
ALTER TABLE `mass_conversation_batch_result` ADD COLUMN `msg_content` text DEFAULT NULL COMMENT '群发消息内容' AFTER `msg_type`;
CREATE TABLE `mass_conversation_batch_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` varchar(50) COLLATE utf8_unicode_ci COMMENT 'openId',
  `member_id` int(11) NOT NULL COMMENT '粉丝ID',
  `batch_id` int(11) NOT NULL COMMENT '批次ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `batch_id` (`batch_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分批群发用户表';

-- 2017-08-22 add lacoste crm
ALTER TABLE `member` ADD COLUMN `status` tinyint(2) NULL COMMENT '绑定状态(0:已解绑,1:已绑定)';
ALTER TABLE `member` ADD COLUMN `pmcode` varchar(255) NULL COMMENT '卡号';
ALTER TABLE `member` ADD COLUMN `levels` varchar(255) NULL COMMENT '卡级别';

-- 拆分repo service时更新的表结构
RENAME TABLE reply_words TO reply_word;
ALTER TABLE `conversation_image_text_detail` ADD COLUMN `wechat_id` int(11) NOT NULL COMMENT '微信ID';
CREATE TABLE `spi_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` int(11) NOT NULL,
  `event` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `action` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `params` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creator_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 2017-09-14 add
CREATE TABLE `qrcode_personal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '简介',
  `ticket` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '二维码ticket',
  `qrcode_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '二维码图片解析后的地址',
  `status` tinyint(2) NOT NULL COMMENT '状态(0:删除,1:使用)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `creator_id` int(11) NOT NULL COMMENT '创建用户ID',
  `expire_seconds` int(11) DEFAULT NULL COMMENT '二维码有效时间，以秒为单位(最大不超过2592000,此字段如果不填，则默认有效期为30秒)',
  `scene` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '场景值ID',
  PRIMARY KEY (`id`),
  KEY `qrcode_ibfk_2` (`wechat_id`),
  KEY `creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户临时二维码表，用于带参的临时二维码'

CREATE TABLE `member_qrcode_invited` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `invited_by` int(11) NOT NULL COMMENT '邀请人id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：1 有效  0 无效',
  `wechat_id` int(11) NOT NULL COMMENT '所属微信',
  `scene` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二维码scene_str',
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户临时二维码分享后，邀请者和被邀请者的关系记录表'

-- 2017-09-19 add，增加授权链接统计表
CREATE TABLE `oauth_url_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '授权用户OPENID',
  `oauth_url_id` int(11) NOT NULL COMMENT '授权ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `open_id` (`open_id`),
  KEY `oauth_url_id` (`oauth_url_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2017-09-25 add，增加跳转链接和来源字段
ALTER TABLE `oauth_url_log` ADD COLUMN `redirect_url` varchar(255) NULL COMMENT '跳转URL';
ALTER TABLE `oauth_url_log` ADD COLUMN `source` varchar(255) NULL COMMENT '来源';

-- 2017-10-09 add，增加微信标签id
ALTER TABLE `member_tag`
ADD COLUMN `wx_id`  int(11) NULL AFTER `id`;

-- 2017-10-09 add，增加菜单扩展表
CREATE TABLE `menu_extra_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL DEFAULT '0',
  `app_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `page_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `app_url` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2017-10-13 会话中的url以及event_key字段长度增加到500
ALTER TABLE `conversation`
	MODIFY COLUMN `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '消息链接' AFTER `description`,
	MODIFY COLUMN `event_key` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '事件KEY值' AFTER `url`;