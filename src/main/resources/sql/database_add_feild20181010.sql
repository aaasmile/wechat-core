-- 新增用户关注的渠道来源、二维码扫码场景和二维码扫码场景描述三个字段 ---
ALTER TABLE member ADD COLUMN `subscribe_scene` varchar(50)  DEFAULT NULL COMMENT '用户关注的渠道来源';
ALTER TABLE member ADD COLUMN `qr_scene` int(11) DEFAULT NULL COMMENT '二维码扫码场景';
ALTER TABLE member ADD COLUMN `qr_scene_str` varchar(255)  DEFAULT NULL COMMENT '二维码扫码场景描述';