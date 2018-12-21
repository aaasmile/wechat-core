ALTER TABLE `conversation`
ADD COLUMN `event_name`  varchar(64) NULL COMMENT '事件名称' AFTER `short_video_url`;

ALTER TABLE `conversation`
ADD COLUMN `menu_name`  varchar(128) NULL COMMENT '菜单名称' AFTER `menu_id`;

ALTER TABLE `conversation`
ADD COLUMN `qrcode_name`  varchar(128) NULL COMMENT '二维码名称' AFTER `event_key`;

ALTER TABLE `conversation`
ADD COLUMN `author`  varchar(64) NULL COMMENT '作者' AFTER `title`; 

ALTER TABLE `conversation`
ADD COLUMN `content_source_checked`  varchar(4) NULL COMMENT '原文链接是否存在' AFTER `author`; 

ALTER TABLE `conversation`
ADD COLUMN `content_source_url`  varchar(500) NULL COMMENT '原文链接' AFTER `content_source_checked`; 

ALTER TABLE `conversation`
ADD COLUMN `show_cover`  varchar(4) NULL COMMENT '封面图片显示在正文' AFTER `content_source_url`; 

ALTER TABLE `conversation`
ADD COLUMN `material_cover_url`  varchar(255) NULL COMMENT '封面图片素材url' AFTER `show_cover`; 

ALTER TABLE `conversation`
ADD COLUMN `summary`  varchar(255) NULL COMMENT '摘要' AFTER `material_cover_url`; 

update conversation set event_name = (case `event` when 1 then 'subscribe' when 2 then 'unsubscribe'  
when 3 then 'SCAN' when 4 then 'LOCATION' when 5 then 'CLICK' 
when 6 then 'VIEW' when 7 then 'poi_check_notify' when 8 then 'MASSSENDJOBFINISH' 
when 9 then 'auto_reply' when 10 then 'scancode_push' when 11 then 'scancode_waitmsg' 
when 12 then 'TEMPLATESENDJOBFINISH' when 13 then 'card_pass_check' 
when 14 then 'card_not_pass_check' when 15 then 'user_get_card' when 16 then 'user_gifting_card' 
when 17 then 'user_del_card' when 18 then 'user_consume_card' when 19 then 'user_pay_from_pay_cell'
when 20 then 'user_enter_session_from_card' when 21 then 'card_sku_remind' 
when 99 then 'conversation_transfer' when 22 then 'location_select'
else 'empty' end);

update conversation c,menu m set menu_name = m.`name`, menu_id=m.id where c.content = m.`name` and `event`=5;

update conversation c,menu m set menu_name = m.`name`, menu_id=m.id where c.content=m.url and `event`=6;

update conversation c,qrcode q set qrcode_name = q.`name` where c.event_key=q.scene and `event`=3;
