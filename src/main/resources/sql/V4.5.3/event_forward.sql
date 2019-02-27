CREATE TABLE `event_forward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `third_party_id` int(11) NOT NULL COMMENT '第三方id(interface_brand id)',
  `interface_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口id(interface_config id)',
  `user_uuid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户UUID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1-开启',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci