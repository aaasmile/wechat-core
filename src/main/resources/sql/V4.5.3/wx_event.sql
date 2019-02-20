CREATE TABLE `wx_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci


INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('自定义菜单拉取消息事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('自定义菜单跳转链接事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('自定义菜单跳跳转小程序事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('关注事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('取消关注事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('已关注用户扫描二维码事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('未关注用户扫描二维码关注后推送的事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('上报地理位置事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`) VALUES ('卡券事件');
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('卡券审核事件', 9);
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('卡券领取事件', 9);
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('卡券核销事件', 9);
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('卡券转赠事件', 9);
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('卡券删除事件', 9);
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('卡券买单事件', 9);
INSERT INTO `d1m_wechat`.`wx_event` (`name`, `parent_id`) VALUES ('从卡券进入公众号事件', 9);
