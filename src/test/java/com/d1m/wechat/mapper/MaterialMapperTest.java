package com.d1m.wechat.mapper;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.d1m.wechat.CoreApplication;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.test.SpringTest;

/**
 * MaterialMapperTest
 *
 * @author f0rb on 2017-10-23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class MaterialMapperTest extends SpringTest {
	
	private static final Logger log = LoggerFactory.getLogger(MaterialMapperTest.class);

    @Resource
    MaterialMapper materialMapper;

    @Test
    public void getImageText() throws Exception {
        MaterialDto materialDto = materialMapper.getImageText(29, 60);
        log.info("MaterialDto={}", materialDto);
    }

}