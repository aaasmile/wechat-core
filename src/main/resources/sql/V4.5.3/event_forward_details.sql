CREATE TABLE `event_forward_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_forward_id` int(11) NOT NULL COMMENT '事件转发id(event_forward id)',
  `event_id` int(11) NOT NULL COMMENT '事件id (wx_event id)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci