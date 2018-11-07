ALTER TABLE member_tag_csv ADD source_file_path varchar(255) NULL COMMENT '源文件路径';
ALTER TABLE member_tag_csv ADD exception_file_path varchar(255) NULL COMMENT '异常信息文件路径';
ALTER TABLE member_tag_csv ADD file_size varchar(50) NULL COMMENT '文件大小';
ALTER TABLE member_tag_csv ADD rows int NULL COMMENT '行数';
ALTER TABLE member_tag_csv ADD format varchar(50) NULL COMMENT '文件格式';
ALTER TABLE member_tag_csv ADD encoding varchar(50) NULL COMMENT '文件编码';

ALTER TABLE member_tag_csv ADD last_update_time timestamp DEFAULT current_timestamp NULL;
ALTER TABLE member_tag_csv ADD success_count int NULL COMMENT '成功条数';
ALTER TABLE member_tag_csv ADD fail_count int NULL COMMENT '失败条数';
ALTER TABLE member_tag_csv ADD error_msg VARCHAR(100) NULL COMMENT '失败原因
';

ALTER TABLE member_tag_csv MODIFY status tinyint(2) NOT NULL COMMENT '//处理状态（0-未处理，1-进行中，2-处理完成）
状态(0 导入中，1 已导入，2 导入失败，3 处理中，4 处理成功，5 处理失败)';

ALTER TABLE member_tag_csv ADD remark varchar(200) NULL;
ALTER TABLE member_tag_csv MODIFY created_at timestamp NOT NULL DEFAULT current_timestamp COMMENT '任务执行时间';

ALTER TABLE member_tag_csv CHANGE id file_id int(11) NOT NULL auto_increment COMMENT '主键ID';

alter table member_tag_csv
  drop column csv;

alter table member_tag_csv
  drop column exception;

alter table member_tag_csv
  drop column data;


CREATE TABLE member_tag_data
(
  data_id int PRIMARY KEY AUTO_INCREMENT,
  file_id int COMMENT '上传文件编号',
  open_id varchar(100),
  wechat_id int,
  tag varchar(200),
  error_tag varchar(200) COMMENT '问题标签',
  original_tag varchar(200) COMMENT '原始标签',
  status tinyint DEFAULT 0 COMMENT '状态（0 未处理，4 处理中，5 处理成功，6 处理失败）',
  check_status bit DEFAULT 1 COMMENT '数据检查状态
(1 正常，0 有问题)',
  remark varchar(200),
  error_msg varchar(200),
  version int COMMENT '乐观锁版本号',
  created_at timestamp DEFAULT current_timestamp,
  finish_time timestamp DEFAULT current_timestamp
);
ALTER TABLE member_tag_data COMMENT = '上传数据表';
