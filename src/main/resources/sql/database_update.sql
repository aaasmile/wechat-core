-- 20170713 add
ALTER TABLE `member` ADD COLUMN `name`  varchar(255) NULL DEFAULT NULL COMMENT '会员姓名' AFTER `keyword`;
ALTER TABLE `member` ADD COLUMN `birth_date`  date NULL DEFAULT NULL COMMENT '会员生日' AFTER `name`;
ALTER TABLE `member` ADD COLUMN `email`  varchar(255) NULL DEFAULT NULL COMMENT '会员邮箱' AFTER `birth_date`;
ALTER TABLE `member` ADD COLUMN `bind_at`  datetime NULL DEFAULT NULL COMMENT '绑定时间' AFTER `status`;
ALTER TABLE `member` ADD COLUMN `unbund_at`  datetime NULL DEFAULT NULL COMMENT '解绑时间' AFTER `bind_at`;
ALTER TABLE `member` ADD COLUMN `province_code`  varchar(255) NULL DEFAULT NULL AFTER `unbund_at`;
ALTER TABLE `member` ADD COLUMN `city_code`  varchar(255) NULL DEFAULT NULL AFTER `province_code`;
ALTER TABLE `member` ADD COLUMN `country_code`  varchar(255) NULL DEFAULT NULL AFTER `city_code`;
ALTER TABLE `member` ADD COLUMN `province_name`  varchar(255) NULL DEFAULT NULL AFTER `country_code`;
ALTER TABLE `member` ADD COLUMN `city_name`  varchar(255) NULL DEFAULT NULL AFTER `province_name`;
ALTER TABLE `member` ADD COLUMN `country_name`  varchar(255) NULL DEFAULT NULL AFTER `city_name`;
ALTER TABLE `member` ADD COLUMN `address`  varchar(255) NULL DEFAULT NULL COMMENT '详细地址' AFTER `country_name`;
ALTER TABLE `member` ADD COLUMN `medium` varchar(255) DEFAULT '' COMMENT '媒介' AFTER `last_conversation_at`;
ALTER TABLE `conversation_image_text_detail` CHANGE COLUMN `content_source_url` `content_source_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原文链接';
ALTER TABLE `material_image_text_detail` CHANGE COLUMN `content_source_url` `content_source_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '原文链接';

-- 20170802 add 增加物料评论
ALTER TABLE `material` ADD COLUMN `comment` tinyint DEFAULT '0' COMMENT '0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论';

ALTER TABLE `material_image_text_detail` ADD COLUMN `comment` tinyint DEFAULT '0' COMMENT '0:不能评论,1:所有人可以评论，2：仅有粉丝可以评论';

-- 20170816 add , 优化分组群发
ALTER TABLE `mass_conversation_batch_result` ADD COLUMN `msg_type` tinyint DEFAULT NULL COMMENT '群发消息类型' AFTER `status`;
ALTER TABLE `mass_conversation_batch_result` ADD COLUMN `msg_content` text DEFAULT NULL COMMENT '群发消息内容' AFTER `msg_type`;
CREATE TABLE `mass_conversation_batch_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` varchar(50) COLLATE utf8_unicode_ci COMMENT 'openId',
  `member_id` int(11) NOT NULL COMMENT '粉丝ID',
  `batch_id` int(11) NOT NULL COMMENT '批次ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  PRIMARY KEY (`id`),
  KEY `member_id` (`member_id`),
  KEY `batch_id` (`batch_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分批群发用户表';

-- 2017-08-22 add lacoste crm
ALTER TABLE `member` ADD COLUMN `status` tinyint(2) default 2 NULL COMMENT '绑定状态(0:已解绑,1:已绑定,2:未绑定)';
ALTER TABLE `member` ADD COLUMN `pmcode` varchar(255) NULL COMMENT '卡号';
ALTER TABLE `member` ADD COLUMN `levels` varchar(255) NULL COMMENT '卡级别';

-- 拆分repo service时更新的表结构
RENAME TABLE reply_words TO reply_word;
ALTER TABLE `conversation_image_text_detail` ADD COLUMN `wechat_id` int(11) DEFAULT 0 NOT NULL COMMENT '微信ID';

CREATE TABLE `spi_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechat_id` int(11) NOT NULL,
  `event` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `action` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `params` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `creator_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `status` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;


-- 2017-09-19 add，增加授权链接统计表
CREATE TABLE `oauth_url_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '授权用户OPENID',
  `oauth_url_id` int(11) NOT NULL COMMENT '授权ID',
  `wechat_id` int(11) NOT NULL COMMENT '公众号ID',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `open_id` (`open_id`),
  KEY `oauth_url_id` (`oauth_url_id`),
  KEY `wechat_id` (`wechat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2017-09-25 add，增加跳转链接和来源字段
ALTER TABLE `oauth_url_log` ADD COLUMN `redirect_url` varchar(255) NULL COMMENT '跳转URL';
ALTER TABLE `oauth_url_log` ADD COLUMN `source` varchar(255) NULL COMMENT '来源';

-- 2017-10-09 add，增加微信标签id
ALTER TABLE `member_tag`
ADD COLUMN `wx_id` int(11) NULL AFTER `id`;

-- 2017-10-09 add，增加菜单扩展表
CREATE TABLE `menu_extra_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL DEFAULT '0',
  `app_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `page_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `app_url` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2017-10-13 会话中的url以及event_key字段长度增加到500
ALTER TABLE `conversation`
	MODIFY COLUMN `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '消息链接' AFTER `description`,
	MODIFY COLUMN `event_key` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '事件KEY值' AFTER `url`;

-- 2017-10-23
ALTER TABLE business_category ADD wechat_id INT DEFAULT 0 NOT NULL;

-- 2017-11-01 增加群发msg_data_id
ALTER TABLE `mass_conversation_batch_result`
  ADD COLUMN `msg_data_id`  varchar(50) NULL AFTER `msg_id`;
ALTER TABLE `mass_conversation_result`
  ADD COLUMN `msg_data_id`  varchar(50) NULL AFTER `msg_id`;

ALTER TABLE `mass_conversation_batch_result`
  ADD COLUMN `errcode`  varchar(20) NULL AFTER `msg_data_id`,
  ADD COLUMN `errmsg`  varchar(100) NULL AFTER `errcode`;

ALTER TABLE `mass_conversation_result`
  ADD COLUMN `errcode`  varchar(20) NULL AFTER `msg_data_id`,
  ADD COLUMN `errmsg`  varchar(100) NULL AFTER `errcode`;

-- 2017-11-16 添加用户权限表
create table user_function
(
    id int auto_increment comment '主键ID' primary key,
    user_id int null comment '用户ID',
    function_id int null comment '功能ID',
    created_at datetime not null comment '创建时间',
    creator_id int null comment '创建用户ID',
    constraint user_function_user_id_fk
    foreign key (user_id) references user (id),
    constraint user_function_function_id_fk
    foreign key (function_id) references function (id)
)
;

create index function_id
    on user_function (function_id)
;

create index user_id
    on user_function (user_id)
;
# DROP TABLE material_mini_program;
-- 2017-11-21 添加小程序
CREATE TABLE material_mini_program
(
    id INT AUTO_INCREMENT COMMENT '主键ID' PRIMARY KEY,
    material_id INT NOT NULL COMMENT '素材ID',
    wechat_id INT NOT NULL COMMENT '公众号ID',
    title VARCHAR(50) NOT NULL COMMENT '小程序的标题',
    appid VARCHAR(50) NOT NULL COMMENT '小程序的appid',
    pagepath VARCHAR(50) NOT NULL COMMENT '小程序的页面路径',
    thumb_material_id INT NOT NULL COMMENT '素材ID',
    creator_id INT(11) NOT NULL,
    created_at DATETIME NOT NULL,
    status TINYINT(4) DEFAULT '1',
    CONSTRAINT material_mini_program_material_id_fk
    FOREIGN KEY (material_id) REFERENCES material (id),
    CONSTRAINT material_mini_program_wechat_id_fk
    FOREIGN KEY (wechat_id) REFERENCES wechat (id),
    CONSTRAINT material_mini_program_thumb_material_id_fk
    FOREIGN KEY (thumb_material_id) REFERENCES material (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci
    COMMENT '小程序素材';


CREATE VIEW material_mini_program_view AS
    SELECT mp.*, mc.pic_url as thumb_url, mc.media_id as thumb_media_id
    FROM material_mini_program mp
        LEFT JOIN material mc ON mp.thumb_material_id = mc.id;

-- 2017-12-11 添加礼品卡订单
CREATE TABLE wx_giftcard_order
(
    id BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    wechat_id INT NULL,
    member_id INT NULL,
    order_id VARCHAR(30) NOT NULL,
    page_id VARCHAR(100) NULL,
    trans_id VARCHAR(100) NULL,
    create_time DATETIME NULL,
    pay_finish_time DATETIME NULL,
    total_price BIGINT NULL COMMENT '全部金额，以分为单位',
    open_id VARCHAR(100) NULL,
    accepter_openid VARCHAR(100) NULL,
    valid BIT DEFAULT b'1' NULL,
    is_chat_room BIT NULL,
    outer_str VARCHAR(30) NULL,
    CONSTRAINT wx_giftcard_order_order_id_uindex
    UNIQUE (order_id)
)
    COMMENT '微信礼品卡订单';

CREATE TABLE wx_giftcard_order_history
(
    id BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    wechat_id INT NULL,
    event VARCHAR(30) NOT NULL,
    created_at DATETIME NULL,
    member_id INT NULL,
    order_id VARCHAR(30) NOT NULL,
    page_id VARCHAR(100) NULL,
    trans_id VARCHAR(100) NULL,
    create_time DATETIME NULL,
    pay_finish_time DATETIME NULL,
    total_price BIGINT NULL COMMENT '全部金额，以分为单位',
    open_id VARCHAR(100) NULL,
    accepter_openid VARCHAR(100) NULL,
    is_chat_room BIT NULL,
    outer_str VARCHAR(30) NULL
)
    COMMENT '微信礼品卡订单事件记录';

CREATE TABLE wx_giftcard_customer
(
    id BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    wechat_id INT NULL,
    name VARCHAR(30) NULL,
    phone VARCHAR(30) NULL,
    valid BIT DEFAULT b'1' NULL,
    CONSTRAINT wx_giftcard_customer_phone_uindex
    UNIQUE (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

INSERT INTO d1m_wechat.wx_giftcard_customer (wechat_id, name, phone)
    SELECT 81, eo.delivery_name, eo.delivery_phone
    FROM estore_order eo
    WHERE eo.id IN (
        SELECT min(eo_.id)
        FROM estore_order eo_
        GROUP BY eo_.delivery_phone
    ) and eo.delivery_phone not in (
        SELECT phone
        FROM wx_giftcard_customer
    );

CREATE TABLE wx_giftcard_order_card
(
    id BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    wechat_id INT NULL,
    order_id VARCHAR(100) NULL,
    card_id VARCHAR(50) NULL,
    price BIGINT NULL,
    code VARCHAR(50) NULL,
    default_gifting_msg VARCHAR(200) NULL,
    background_pic_url VARCHAR(200) NULL,
    outer_img_id VARCHAR(50) NULL,
    accepter_openid VARCHAR(100) NULL,
    valid BIT DEFAULT b'1' NULL,
    CONSTRAINT wx_giftcard_order_card_code_uindex
    UNIQUE (code)
);

CREATE TABLE wx_giftcard_order_shipment
(
    id BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    wechat_id INT NULL,
    order_no VARCHAR(100) NOT NULL COMMENT 'estore订单号',
    order_id VARCHAR(100) NULL COMMENT '微信订单号',
    express_code VARCHAR(15) NOT NULL COMMENT '物流公司',
    shipment_id VARCHAR(100) NOT NULL COMMENT '物流订单号',
    shipment_exd VARCHAR(100) NOT NULL COMMENT '物流订单号2',

    ship_time DATETIME NOT NULL COMMENT '发货时间',
    sign_time DATETIME NULL COMMENT '签收时间',
    refuse_time DATETIME NULL COMMENT '拒收时间',

    valid BIT DEFAULT b'1' NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE VIEW wx_giftcard_customer_view AS
    SELECT
        'CUWC1I ' as 'Rec tRpe',
        concat('C90001', lpad(c.id, 11, '0')) as 'Rip code',
        'C90001' as 'StoreCode',
        '' as 'title',
        eo.delivery_name as 'last name',
        '' as 'first name',
        substring(eo.delivery_address, 1, 35) as 'address1',
        substring(eo.delivery_address, 36, 35) as 'address2',
        substring(eo.delivery_address, 71, 35) as 'address3',
        eo.delivery_city as 'city',
        REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(eo.delivery_province , '省', '' ) , '特别行政区', '' ) , '自治区', '' ) , '维吾尔', '' ) , '壮族', '' ) , '回族', '' ) as 'state',
        '' as 'post code',
        'CHN' as 'Country',
        '' as 'phone',
        eo.delivery_phone as 'mobile',
        '' as 'phone3',
        '' as 'email',
        '' as 'day_birth',
        '' as 'month_birth',
        '' as 'year_birth',
        '' as 'sex',
        '' as 'classification',
        '' as 'SMS',
        '' as 'mailing',
        '' as 'e-mailing',
        '' as 'contact method',
        '' as 'skin type',
        '' as 'how found',
        DATE_FORMAT(eo.create_at,'%Y%m%d') as 'creation date',
        '' as 'origin vip code',
        '' as 'FreeText',
        '' as 'NFC',
        '' as 'AskCustomer',
        'C90001' as 'CreationStoreID',
        'CHI' as 'CustomerLanguage'
    FROM estore_order eo
        LEFT JOIN wx_giftcard_customer c ON c.phone = eo.delivery_phone
    WHERE eo.id in (
        SELECT min(eo_.id) FROM estore_order eo_
        GROUP BY eo_.delivery_phone
    );

-- 2017-12-11 礼品卡顾客视图调整
DROP VIEW wx_giftcard_customer_view;
CREATE VIEW wx_giftcard_customer_view AS
    SELECT
        concat('C90001', lpad(c.id, 11, '0')) as 'code',
        c.name,
        REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(eo.delivery_province , '省', '' ) , '特别行政区', '' ) , '自治区', '' ) , '维吾尔', '' ) , '壮族', '' ) , '回族', '' ) as 'state',
        eo.delivery_city as 'city',
        eo.delivery_address as 'address',
        eo.delivery_phone as 'mobile',
        eo.create_at as 'created_at'
    FROM estore_order eo
        LEFT JOIN wx_giftcard_customer c ON c.phone = eo.delivery_phone
    WHERE eo.id in (
        SELECT min(eo_.id) FROM estore_order eo_
        GROUP BY eo_.delivery_phone
    );

ALTER TABLE report_config ADD report_param VARCHAR(1000) DEFAULT '{}' NOT NULL;
ALTER TABLE report_config
    MODIFY COLUMN wechat_id INT(11) NOT NULL DEFAULT '0' COMMENT '公众号ID' AFTER id;

create table report_article_source
(
    id int auto_increment comment '主键ID'
        primary key,
    msgid varchar(20) not null comment '图文消息ID',
    title varchar(100) null comment ' 图文消息标题',
    ref_date varchar(10) not null comment '群发日期',
    page_user int(20) null comment '图文页阅读人数',
    page_count int(20) null comment '图文页阅读次数',
    ori_page_user int(20) null comment '原文页阅读人数',
    ori_page_count int(20) null comment '原文页阅读次数',
    add_fav_user int(20) null comment '收藏人数',
    add_fav_count int(20) null comment '收藏次数',
    share_user int(20) null comment '分享人数',
    share_count int(20) null comment '分享次数',
    wechat_id int(20) not null comment '所属公众号ID',
    created_at datetime not null comment '创建时间'
)
;

create table report_article_source_detail
(
    id int auto_increment comment '主键ID'
        primary key,
    wechat_id int(20) not null comment '所属公众号ID',
    msgid varchar(20) not null comment '图文消息ID',
    title varchar(100) null comment ' 图文消息标题',
    ref_date varchar(10) not null comment '群发日期',
    stat_date varchar(10) not null comment '统计的日期',
    target_user int not null comment '图文消息的标题',
    int_page_read_user int not null comment '图文页的阅读人数',
    int_page_read_count int not null comment '图文页的阅读次数',
    ori_page_read_user int not null comment '原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0',
    ori_page_read_count int not null comment '原文页的阅读次数',
    share_user int not null comment '分享人数',
    share_count int not null comment '分享次数',
    add_to_fav_user int not null comment '收藏的人数',
    add_to_fav_count int not null comment '收藏的次数',
    int_page_from_session_read_user int not null comment '公众号会话阅读人数',
    int_page_from_session_read_count int not null comment '公众号会话阅读次数',
    int_page_from_hist_msg_read_user int not null comment '历史消息页阅读人数',
    int_page_from_hist_msg_read_count int not null comment '历史消息页阅读次数',
    int_page_from_feed_read_user int not null comment '朋友圈阅读人数',
    int_page_from_feed_read_count int not null comment '朋友圈阅读次数',
    int_page_from_friends_read_user int not null comment '好友转发阅读人数',
    int_page_from_friends_read_count int not null comment '好友转发阅读次数',
    int_page_from_other_read_user int not null comment '好友转发阅读人数',
    int_page_from_other_read_count int not null comment '好友转发阅读次数',
    feed_share_from_session_user int not null comment '公众号会话转发朋友圈人数',
    feed_share_from_session_cnt int not null comment '公众号会话转发朋友圈次数',
    feed_share_from_feed_user int not null comment '朋友圈转发朋友圈人数',
    feed_share_from_feed_cnt int not null comment '朋友圈转发朋友圈次数',
    feed_share_from_other_user int not null comment '朋友圈转发朋友圈次数',
    feed_share_from_other_cnt int not null comment '其他场景转发朋友圈人数',
    created_at timestamp default CURRENT_TIMESTAMP not null,
    constraint report_article_source_detail_msgid_stat_date_uindex
    unique (msgid, stat_date)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
;

CREATE TABLE celine_payment_tag_record (
    id INT AUTO_INCREMENT COMMENT '主键ID'
        PRIMARY KEY,
    wechat_id INT NOT NULL COMMENT '所属公众号ID',
    shop_id VARCHAR(30) NOT NULL COMMENT '门店id',
    open_id VARCHAR(50) NOT NULL COMMENT '微信openid',
    tag_id INT NOT NULL COMMENT '',
    success BIT NOT NULL COMMENT '是否成功打上标签',
    created_at DATETIME(6) NOT NULL COMMENT '打标签时间'
)
;
