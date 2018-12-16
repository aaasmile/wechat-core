ALTER TABLE member_profile ADD mobile varchar(20) NULL COMMENT '绑定手机号码';
ALTER TABLE member_profile ADD sex tinyint NULL COMMENT '绑定的性别';
ALTER TABLE member_profile ADD province VARCHAR(15) NULL COMMENT '绑定的省';
ALTER TABLE member_profile ADD city varchar(20) NULL COMMENT '绑定的市';
ALTER TABLE member_profile ADD county varchar(30) NULL COMMENT '绑定的区县';