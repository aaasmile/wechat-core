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

--20170816 add , 优化分组群发
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

---2017-08-22 add lacoste crm
ALTER TABLE `member` ADD COLUMN `status` tinyint(2) NULL COMMENT '绑定状态(0:已解绑,1:已绑定)';
ALTER TABLE `member` ADD COLUMN `pmcode` varchar(255) NULL COMMENT '卡号';
ALTER TABLE `member` ADD COLUMN `levels` varchar(255) NULL COMMENT '卡级别';