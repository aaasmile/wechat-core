package com.d1m.wechat.schedule.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.MemberMemberTagService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.MemberTagTypeService;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.MemberTagType;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;

import cn.d1m.wechat.client.core.WechatClient;
import cn.d1m.wechat.client.model.WxOpenidPage;
import cn.d1m.wechat.client.model.WxTag;
import cn.d1m.wechat.client.model.common.WxHolder;
import cn.d1m.wechat.client.model.common.WxList;
import com.d1m.wechat.util.DateUtil;

import org.slf4j.*;

/**
 * @author D1M
 * 双向同步会员标签
 * 同步之前需要将数据库表member_member_tag 增加唯一索引
 *   UNIQUE KEY `member_id_tag_id_wechat_id` (`member_id`,`member_tag_id`,`wechat_id`),
 *
 */
@JobHander(value="memberTagSyncJob")
@Component
public class MemberTagSyncJob extends BaseJobHandler  {
	
	Logger log = LoggerFactory.getLogger(MemberTagSyncJob.class);
	
	@Autowired
	private MemberTagService memberTagService;
	@Autowired
    private MemberMemberTagService memberMemberTagService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberTagTypeService memberTagTypeService;

	@Override
	public ReturnT<String> run(String... strings) throws Exception {
		if(strings == null) {
			log.error("strings is null!!");
			return ReturnT.FAIL;
		}
		String wechatIdStr = strings[0];
		log.info("wechatId>>" + wechatIdStr);
		Integer wechatId = null;
		if(StringUtils.isEmpty(wechatIdStr)) {
			log.error("wechatIdStr is null!!");
			return ReturnT.FAIL;
		}
		wechatId = Integer.valueOf(wechatIdStr);
		Integer memberTagTypeId = null;
		try {
			MemberTagType query = new MemberTagType();
			query.setName("门店");
			query = memberTagTypeService.selectOne(query);
			memberTagTypeId = query.getId();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//步骤1,将微信后台用户标签同步到数据库
		try {
			ReturnT<String> step1 = step1(wechatId, memberTagTypeId);
			log.info("step1>>" + step1.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//步骤2，将本地用户标签推送给微信
		try {
			ReturnT<String> step2 = step2(wechatId);
			log.info("step2>>" + step2.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ReturnT.SUCCESS;
	}
	
	public ReturnT<String> step2(Integer wechatId) {
		
		//获取微信tags
		log.info("获取微信tags");
		WechatClient client = WechatClientDelegate.get(wechatId);
		WxList<WxTag> wxTagList = client.getTags();
		List<WxTag> tagList = null;
		if(wxTagList != null) {
			tagList = wxTagList.get();
		}
		//将已有tag放入map中
		log.info("将已有tag放入map中");
		Map<Integer, String> tagMap = new HashMap<Integer, String>();
		Map<String, Integer> tagMap1 = new HashMap<String, Integer>();
		if(tagList != null && tagList.size() > 0) {
			for(int i = 0 ; i < tagList.size(); i++) {
				WxTag wxTag = tagList.get(i);
				tagMap.put(wxTag.getId(), wxTag.getName());
				tagMap1.put(wxTag.getName(), wxTag.getId());
			}
		}
		log.info("tagMap>>" + tagMap.toString());
		List<MemberTagDto> allMemberTags = memberTagService.getAllMemberTags(wechatId);
		for(int i = 0; i < allMemberTags.size(); i++) {
			MemberTagDto memberTagDto = allMemberTags.get(i);
			List<String> openid_list = new ArrayList<String>();
			MemberTag memberTag = null;
			String tagname = tagMap.get(memberTagDto.getId());
			if (tagMap.containsKey(memberTagDto.getId())) {
                if (!StringUtils.equals(memberTagDto.getName(), tagMap.get(memberTagDto.getId()))) {
                    memberTag = new MemberTag();
                	memberTag.setId(memberTagDto.getId());
                	memberTag.setName(tagname);
                    memberTagService.updateAll(memberTag);
                }
            }
			
			//如果wechat中没有tag，则创建微信tag
			Integer tagid = tagMap1.get(tagname);
			if(tagid == null) {
				WxHolder<WxTag> wxtagHolder = client.createTag(tagname);
				WxTag wxtag = wxtagHolder.getData();
				tagid = wxtag.getId();
			}
			//查询出该tag下所有openid
			MemberMemberTag query = new MemberMemberTag();
			query.setMemberTagId(memberTagDto.getId());
			List<MemberMemberTag> memberTagList = memberMemberTagService.getMemberMemberTagList(query);
			for(int j = 0; j < memberTagList.size(); j++) {
				MemberMemberTag memberMemberTag = memberTagList.get(j);
				openid_list.add(memberMemberTag.getOpenId());
				//每次传入的openid列表个数不能超过50个
				if(openid_list.size() % 50 == 0 && openid_list.size() != 0) {
					log.info("同步数据到微信：已经同步标签[{}]的粉丝数: {}", tagname, openid_list.size());
					client.batchTagging(openid_list, tagid);
					openid_list.clear();
				}
			}
			//将剩余的openid发送
			if(openid_list.size() > 0) {
				client.batchTagging(openid_list, tagid);
				openid_list.clear();
			}
		}
		return ReturnT.SUCCESS;
	}

	
	public ReturnT<String> step1(Integer wechatId, Integer memberTagTypeId) {
        // 1. 同步微信用户标签
        List<MemberTagDto> allMemberTags = memberTagService.getAllMemberTags(wechatId);
        Map<Integer, MemberTagDto> TagIdMap = new HashMap<Integer, MemberTagDto>();
        for (MemberTagDto memberTagDto : allMemberTags) {
            TagIdMap.put(memberTagDto.getId(), memberTagDto);
        }

        List<WxTag> TagList = WechatClientDelegate.getTags(wechatId).get();
        Date current = new Date();
        String now = DateUtil.formatYYYYMMDDHHMMSS(new Date());
        for (WxTag wxTag : TagList) {
            MemberTag memberTag;
            if (TagIdMap.containsKey(wxTag.getId())) {
                memberTag = new MemberTag();
                if (!StringUtils.equals(memberTag.getName(), wxTag.getName())) {
                    memberTag.setName(wxTag.getName());
                    memberTagService.updateAll(memberTag);
                }
            } else {
                memberTag = new MemberTag();
                memberTag.setId(wxTag.getId());
                memberTag.setName(wxTag.getName());
                memberTag.setCreatedAt(current);
                memberTag.setCreatorId(1);
                memberTag.setWechatId(wechatId);
                memberTag.setStatus(Byte.valueOf("1"));
                memberTag.setMemberTagTypeId(memberTagTypeId);
                memberTagService.save(memberTag);
                
                MemberTagDto memberTagDto = new MemberTagDto();
                memberTagDto.setId(wxTag.getId());
                memberTagDto.setName(wxTag.getName());
                memberTagDto.setCreatedAt(now);
                memberTagDto.setCreatorId(1);
                memberTagDto.setWechatId(wechatId);
                TagIdMap.put(memberTag.getId(), memberTagDto);
            }
        }
        
        // 2. 同步各个标签下的用户
        String nextOpenid;
        List<MemberMemberTag> memberMemberTagList = new ArrayList<MemberMemberTag>();

        for (MemberTagDto MemberTag : TagIdMap.values()) {
            int count = 0;
            nextOpenid = null;
            do {
                WxOpenidPage OpenidPage = WechatClientDelegate.getOpenidByTag(wechatId, MemberTag.getId(), nextOpenid);
                nextOpenid = OpenidPage.getNextOpenid();
                if (OpenidPage.getCount() > 0) {
                    List<String> openIdList = OpenidPage.getData();
                    for (String openId : openIdList) {
                        Member member = memberService.getMemberByOpenId(wechatId, openId);

                        MemberMemberTag memberMemberTag = new MemberMemberTag();
                        memberMemberTag.setMemberId(member.getId());
                        memberMemberTag.setMemberTagId(MemberTag.getId());
                        memberMemberTag.setWechatId(wechatId);

                        memberMemberTagList.add(memberMemberTag);
                    }
                    if (nextOpenid.equals(openIdList.get(openIdList.size() - 1))) {
                        nextOpenid = null;
                    }
                    count += OpenidPage.getCount();
                }
            } while (nextOpenid != null);
            log.info("同步数据到本地：已经同步标签[{}]的粉丝数: {}", MemberTag.getName(), count);
        }
        log.info("同步数据到本地：已经同步的粉丝数: {}", memberMemberTagList.size());
        memberMemberTagService.insertOrUpdateList(memberMemberTagList);
        return ReturnT.SUCCESS;
    }
}
