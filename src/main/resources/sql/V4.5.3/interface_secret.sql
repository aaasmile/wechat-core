CREATE TABLE `interface_secret` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID 唯一标识',
  `brand_id` int(11) DEFAULT NULL COMMENT '第三方名称(interface_brand)',
  `type` tinyint(4) DEFAULT NULL COMMENT '接口类型 主动推送(专用接口)/第三方拉取/主动推送(事件转发)  0/1/2',
  `app_key` varchar(32) DEFAULT NULL COMMENT '接口KEY',
  `app_secret` varchar(16) DEFAULT NULL COMMENT '接口密码  16位',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;