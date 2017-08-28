package com.d1m.wechat.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.mapper.AreaInfoMapper;
import com.d1m.wechat.model.AreaInfo;
import com.d1m.wechat.service.impl.AreaInfoServiceImpl;

public class AreaInfoServiceTest {

	AreaInfoServiceImpl areaInfoService;

	AreaInfoMapper areaInfoMapper;

	Mockery context = new JUnit4Mockery();

	@Before
	public void setUp() {
		areaInfoMapper = context.mock(AreaInfoMapper.class);
		areaInfoService = new AreaInfoServiceImpl();
		areaInfoService.setAreaInfoMapper(areaInfoMapper);
	}

	@Test
	public void selectAll() {
		context.checking(new Expectations() {
			{
				oneOf(areaInfoMapper).selectAll();
				List<AreaInfo> aiList = new ArrayList<AreaInfo>();
				AreaInfo ai = new AreaInfo();
				aiList.add(ai);
				will(returnValue(aiList));
			}
		});

		List<AreaInfo> aiList = areaInfoService.selectAll();
		assertTrue(!aiList.isEmpty());
	}

}
