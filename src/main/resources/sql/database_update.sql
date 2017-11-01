-- 20170713 add
ALTER TABLE `member` ADD COLUMN `medium` varchar(255) DEFAULT '' COMMENT '媒介' AFTER `last_conversation_at`;
ALTER TABLE `conversation_image_text_detail` CHANGE COLUMN `content_source_url` `content_source_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原文链接';
ALTER TABLE `material_image_text_detail` CHANGE COLUMN `content_source_url` `content_source_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '原文链接';

-- 20170802 add 增加物料评论
ALTER TABLE `material` ADD COLUMN `comment` tinyint DEFAULT '0' COMMENT '0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分批群发用户表';

-- 2017-08-22 add lacoste crm
ALTER TABLE `member` ADD COLUMN `status` tinyint(2) default 2 NULL COMMENT '绑定状态(0:已解绑,1:已绑定,2:未绑定)';
ALTER TABLE `member` ADD COLUMN `pmcode` varchar(255) NULL COMMENT '卡号';
ALTER TABLE `member` ADD COLUMN `levels` varchar(255) NULL COMMENT '卡级别';

-- 拆分repo service时更新的表结构
RENAME TABLE reply_words TO reply_word;
ALTER TABLE `conversation_image_text_detail` ADD COLUMN `wechat_id` int(11) DEFAULT 0 NOT NULL COMMENT '微信ID';

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


-- 2017-09-19 add，增加授权链接统计表
CREATE TABLE `oauth_url_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '授权用户OPENID',
  `oauth_url_id` int(11) NOT NULL COMMENT '授权ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `open_id` (`open_id`),
  KEY `oauth_url_id` (`oauth_url_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2017-09-25 add，增加跳转链接和来源字段
ALTER TABLE `oauth_url_log` ADD COLUMN `redirect_url` varchar(255) NULL COMMENT '跳转URL';
ALTER TABLE `oauth_url_log` ADD COLUMN `source` varchar(255) NULL COMMENT '来源';

-- 2017-10-09 add，增加微信标签id
ALTER TABLE `member_tag`
ADD COLUMN `wx_id` int(11) NULL AFTER `id`;

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

-- 2017-10-23
ALTER TABLE business_category ADD wechat_id INT DEFAULT 0 NOT NULL;

-- 2017-11-01 增加群发msg_data_id
ALTER TABLE `mass_conversation_batch_result`
  ADD COLUMN `msg_data_id`  varchar(50) NULL AFTER `msg_id`;
ALTER TABLE `mass_conversation_result`
  ADD COLUMN `msg_data_id`  varchar(50) NULL AFTER `msg_id`;

ALTER TABLE `mass_conversation_batch_result`
  ADD COLUMN `errcode`  varchar(20) NULL AFTER `msg_data_id`,
  ADD COLUMN `errmsg`  varchar(100) NULL AFTER `errcode`;

ALTER TABLE `mass_conversation_result`
  ADD COLUMN `errcode`  varchar(20) NULL AFTER `msg_data_id`,
  ADD COLUMN `errmsg`  varchar(100) NULL AFTER `errcode`;
