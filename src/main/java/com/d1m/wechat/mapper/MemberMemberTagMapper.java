package com.d1m.wechat.mapper;

import java.util.List;

import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.Tag;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface MemberMemberTagMapper extends MyMapper<MemberMemberTag> {
	public List<MemberMemberTag> getMemberMemberTagList(MemberMemberTag memberMemberTag);
	public void insertOrUpdateList(List<Tag> tags);

	/**
	 * 查询标签name是否存在
	 * @param openId
	 * @param name
	 * @param wechatId
	 * @return
	 */
	List<MemberMemberTag> selecteIsExist(@Param("openId") String openId, @Param("name") String name, @Param("wechatId") Integer wechatId);
}