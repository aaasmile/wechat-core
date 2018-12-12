create table interface_config(
id	Varchar(32) COMMENT	'Uuid 32位uuid，分布式系统表ID采用32位UUID',
brand	Varchar(32) COMMENT	'品牌（唯一索引）',
`name`	Varchar(64) COMMENT	'第三方接口名称',
method_type	Varchar(4) COMMENT	'POST/GET',
type	Varchar(4) COMMENT	'接口类型',
`event`	Varchar(32) COMMENT	'事件(包含微信事件,D1M事件)',
parameter	Varchar(512) COMMENT	'接口参数(json格式)',
description	Varchar(512) COMMENT	'接口描述',
url	Varchar(128) COMMENT	'接口地址',
`key`	Varchar(32) COMMENT	'接口key',
`secret`	Varchar(32) COMMENT	'接口密码',
sequence	Int(11) COMMENT	'序列号(优先级，排序)',
is_deleted	tinyint(4) DEFAULT 0 COMMENT	'是否删除(yes,no)',
created_at	timestamp  COMMENT	'创建时间' DEFAULT now(),
created_by	Varchar(32) COMMENT	'创建人',
updated_at	timestamp  COMMENT	'最后更新时间' DEFAULT now(),
updated_by	Varchar(32)	COMMENT '最后更新人',
PRIMARY KEY (`id`)
);

CREATE TABLE `interface_brand` (
  `id` int(11) NOT NULL COMMENT 'ID 唯一标识',
  `name` varchar(63) DEFAULT NULL COMMENT '第三方名称',
  `key` varchar(32) DEFAULT NULL COMMENT '接口KEY',
  `secret` varchar(32) DEFAULT NULL COMMENT '接口密码',
  `is_deleted` tinyint(2) DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;