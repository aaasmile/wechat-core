package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.ReportMemberTagDto;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MemberTagMapper extends MyMapper<MemberTag> {

	MemberTag getDuplicateName(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id, @Param("name") String name);

	Page<MemberTag> search(@Param("wechatId") Integer wechatId,
			@Param("name") String name,
			@Param("memberTagTypeIds") List<Integer> memberTagTypeIds,
			@Param("memberTagIds") List<Integer> memberTagIds, @Param("sortName") String sortName, 
			@Param("sortDir") String sortDir);

	Page<ReportMemberTagDto> tagUser(
			@Param("wechatId") Integer wechatId, 
			@Param("start") Date start, 
			@Param("end") Date end,
			@Param("top") Integer top);
	
	List<MemberTagDto> getAllMemberTags(@Param("wechatId") Integer wechatId);

	List<MemberTagDto> searchName(@Param("wechatId") Integer wechatId, @Param("name") String name);

}