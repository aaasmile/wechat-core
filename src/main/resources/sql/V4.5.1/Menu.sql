ALTER TABLE `menu`
ADD COLUMN `interface_id`  varchar(32) NULL COMMENT '第三方接口ID' AFTER `seq`;