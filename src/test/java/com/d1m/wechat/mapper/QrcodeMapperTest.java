package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import com.d1m.wechat.dto.ReportQrcodeItemDto;
import com.d1m.wechat.test.SpringTest;
import com.d1m.wechat.util.DateUtil;

/**
 * QrcodeMapperTest
 *
 * @author f0rb on 2017-05-18.
 */
public class QrcodeMapperTest extends SpringTest {
    @Resource
    private QrcodeMapper qrcodeMapper;

    @Test
    public void qrcodeReport() throws Exception {
        Date start = DateUtil.getDate(-15);
        Date end = new Date();

        List<ReportQrcodeItemDto> reportQrcodeItemDtoList =
                qrcodeMapper.qrcodeReport(1, start, end, null, null, null);

        System.out.println(JSON.toJSONString(reportQrcodeItemDtoList));

    }

    @Test
    public void qrcodeNameList() throws Exception {
        Date start = DateUtil.getDate(-15);
        Date end = new Date();

        List<ReportQrcodeItemDto> reportQrcodeItemDtoList =
                qrcodeMapper.qrcodeNameList(1, start, end, null, null, null);

        System.out.println(JSON.toJSONString(reportQrcodeItemDtoList));

    }

}
