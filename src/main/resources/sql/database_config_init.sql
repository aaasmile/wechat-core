--请先修改wechat_id

--地理位置推送图文配置
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( 'LBS配置', 'LOCATION_API_CLASS', '门店位置处理API', 'LBS', 'com.d1m.wechat.service.engine.api.impl.LbsOutletApi', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( 'LBS配置', 'OUTLET_SIZE', '门店显示数目', 'LBS', '7', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( 'LBS配置', 'OUTLET_URL', '门店显示链接', 'LBS', 'http://qa.wechat.d1m.cn/celine/#!/outlet/', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( 'LBS配置', 'FIRST_TITLE', '头条标题', 'LBS', '查找专卖店', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( 'LBS配置', 'FIRST_PIC', '头条图片', 'LBS', 'http://qa.wechat.d1m.cn/celine/first.jpg', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( 'LBS配置', 'FIRST_URL', '头条链接', 'LBS', 'https://www.d1m.cn/', '0');

--地理位置初始化配置
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( '地理位置服务', 'BAIDU_GEO_SERVICE_KEY', '百度地图KEY', 'LBS', 'DEce9781adb6bb42507fff0ba4228ad6', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( '地理位置服务', 'QQ_GEO_SERVICE_KEY', 'QQ地图KEY', 'LBS', 'YLFBZ-WHAWI-ZXUGH-53Q65-TOJ7E-ADBNQ', '0');

--群发配置
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( '群发', 'BATCH_SIZE', '每批群发人数上限', 'MASS_CONVERSATION', '2', '0');
insert into `config` ( `cfg_group_label`, `cfg_key`, `cfg_key_label`, `cfg_group`, `cfg_value`, `wechat_id`) values ( '群发配置', 'BATCH_INTERVAL', '批次群发间隔', 'MASS_CONVERSATION', '10', '0');


