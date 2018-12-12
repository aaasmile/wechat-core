-- member_member_tag增加唯一索引
alter table member_member_tag add unique index member_id_tag_id_wechat_id(`member_id`, `member_tag_id`, `wechat_id`);

-- 删除标签表重复记录
delete from member_member_tag where id not in (select maxid from (select max(id) as maxid from member_member_tag group by `member_id`, `member_tag_id`, `wechat_id`) b);