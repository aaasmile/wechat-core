-- 20170814 add , 增加物料评论
ALTER TABLE `material` DROP COLUMN `comment`;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户临时二维码表，用于带参的临时二维码';

CREATE TABLE `member_qrcode_invited` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NOT NULL COMMENT '用户id',
  `invited_by` int(11) NOT NULL COMMENT '邀请人id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：1 有效  0 无效',
  `wechat_id` int(11) NOT NULL COMMENT '所属微信',
  `scene` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二维码scene_str',
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户临时二维码分享后，邀请者和被邀请者的关系记录表';

-- 20170725 add , popup-store 增加限购数量和安全库存字段
ALTER TABLE `popup_goods` ADD COLUMN `limit_count` int DEFAULT '-1' COMMENT '限购数量' AFTER `count`;
ALTER TABLE `popup_goods` ADD COLUMN `safety_stock` int DEFAULT '0' COMMENT '安全库存';

