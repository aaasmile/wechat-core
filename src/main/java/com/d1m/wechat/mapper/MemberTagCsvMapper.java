package com.d1m.wechat.mapper;


import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.dto.ImportCsvDto;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface MemberTagCsvMapper extends MyMapper<MemberTagCsv> {


    Page<ImportCsvDto> searchTask(Map map);
}