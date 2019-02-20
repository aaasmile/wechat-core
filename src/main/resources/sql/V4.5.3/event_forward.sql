CREATE TABLE `event_forward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `third_party_id` int(11) NOT NULL,
  `interface_id` int(11) NOT NULL,
  `user_uuid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint NOT NULL DEFAULT '1',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci