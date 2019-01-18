ALTER TABLE material ADD COLUMN  `is_scrm` int(1) DEFAULT '0' COMMENT '是否SCRM图文(SCRM新增图文为1，微信图文为0)';

ALTER TABLE material ADD COLUMN `material_category_id` varchar(50) DEFAULT NULL COMMENT '素材分类id';

alter TABLE material_image_text_detail add COLUMN `url` varchar(200)  DEFAULT NULL COMMENT '图文页的URL';

alter TABLE material_image_text_detail add COLUMN `sn` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信图文URL链接sn参数';

alter TABLE material add COLUMN `sn` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信图文URL链接sn参数';
-- 素材分类表 --
CREATE TABLE `material_category` (
  `id` varchar(50) NOT NULL DEFAULT '0' COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `deleted` varchar(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0 否，1 是）',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `laste_updated_at` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `laste_updated_by` varchar(32) DEFAULT NULL COMMENT '最后更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='素材分类表';


-- 非群发单图文表 --
CREATE TABLE `dcrm_image_text_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COMMENT '正文',
  `link` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址',
  `material_cover_id` int(11) DEFAULT NULL COMMENT '封面图片素材ID',
  `summary` varchar(120) CHARACTER SET utf8 DEFAULT NULL COMMENT '摘要',
  `material_id` int(11) DEFAULT NULL COMMENT '素材ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态(0:删除,1:使用)',
  `sequence` int(11) DEFAULT NULL COMMENT '顺序',
  `remark` varchar(2000) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `material_category_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '图文分类id',
  `wx_image_text_id` int(50) DEFAULT NULL COMMENT '关联微信图文id',
  `tag_id` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '阅读标签id',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `laste_updated_at` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',
  `laste_updated_by` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后更新人',
  `send_times` int(11) DEFAULT '0' COMMENT '发送次数',
  `read_times` int(11) DEFAULT '0' COMMENT '阅读次数',
  `qrcode_id` int(11) DEFAULT NULL COMMENT '二维码id',
  `url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图文页的URL',
  `pic_url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '微信封面url',
  `wx_last_pust_time` datetime DEFAULT NULL COMMENT '微信图文最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT='非群发单图文表';
