package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.MemberTagTypeDto;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MemberTagTypeMapper extends MyMapper<MemberTagType> {

	Integer countDuplicateName(@Param("wechatId") Integer wechatId, @Param("id") Integer id, 
			@Param("name") String name);

	Page<MemberTagType> search(@Param("wechatId") Integer wechatId,
			@Param("name") String name, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

	Integer getFirst();

	List<MemberTagTypeDto> selectAllTagTypes(@Param("wechatId") Integer wechatId);
	
	void deleteAllTags(@Param("wechatId") Integer wechatId, @Param("id") Integer id);
	
	void deleteAllChildTagTypes(@Param("wechatId") Integer wechatId, @Param("id") Integer id);
}
