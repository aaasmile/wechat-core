UPDATE `business_photo` SET photo_url=REPLACE(photo_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `conversation` SET content=REPLACE(content, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `conversation` SET pic_url=REPLACE(pic_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `conversation` SET event_key=REPLACE(event_key, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `conversation` SET member_photo=REPLACE(member_photo, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `conversation` SET kf_photo=REPLACE(kf_photo, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `mass_conversation_result` SET conditions=REPLACE(conditions, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `material` SET pic_url=REPLACE(pic_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `material_image_text_detail` SET content=REPLACE(content, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `member` SET local_head_img_url=REPLACE(local_head_img_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `menu` SET url=REPLACE(url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `qrcode` SET qrcode_img_url=REPLACE(qrcode_img_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `user` SET local_head_img_url=REPLACE(local_head_img_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/'); 
UPDATE `wechat` SET head_img_url=REPLACE(head_img_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/');

UPDATE `oauth_url` SET params=REPLACE(params, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/');

UPDATE `activity` SET share_pic=REPLACE(share_pic, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/');
UPDATE `activity_qrcode` SET acty_url=REPLACE(acty_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/');
UPDATE `activity_qrcode` SET qrcode_img_url=REPLACE(qrcode_img_url, 'http://xxx.d.d1m.cn/', 'http://xxx.wechat.d1mgroup.com/');


UPDATE `wechat` SET appid='',appscret='',token='',open_id='',encoding_aes_key='';

