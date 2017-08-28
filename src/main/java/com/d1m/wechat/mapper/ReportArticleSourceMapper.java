package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ReportArticleDto;
import com.d1m.wechat.dto.ReportArticleItemDto;
import com.d1m.wechat.model.ReportArticleSource;
import com.d1m.wechat.util.MyMapper;

public interface ReportArticleSourceMapper extends MyMapper<ReportArticleSource> {

	ReportArticleDto countArticle(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);

	List<ReportArticleItemDto> articleReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);

	List<ReportArticleItemDto> articleListReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);
}
