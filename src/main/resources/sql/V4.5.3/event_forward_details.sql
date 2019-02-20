CREATE TABLE `event_forward_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_forward_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci