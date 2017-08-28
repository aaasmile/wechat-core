package com.d1m.wechat.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.service.impl.MaterialServiceImpl;

public class MaterialServiceTest {

	Mockery context = new JUnit4Mockery();

	MaterialServiceImpl materialService;
	MaterialMapper materialMapper;
	MaterialImageTextDetailMapper materialImageTextDetailMapper;

	@Before
	public void setUp() {
		materialMapper = context.mock(MaterialMapper.class);
		materialImageTextDetailMapper = context
				.mock(MaterialImageTextDetailMapper.class);
		materialService = new MaterialServiceImpl();
		materialService
				.setMaterialImageTextDetailMapper(materialImageTextDetailMapper);
		materialService.setMaterialMapper(materialMapper);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateImageText() throws WechatException {
		context.checking(new Expectations() {
			{
				oneOf(materialMapper).insert(with(aNonNull(Material.class)));
				will(returnValue(new Integer(1)));

				allowing(materialMapper).selectOne(
						with(aNonNull(Material.class)));
				will(returnValue(new Material()));

				allowing(materialImageTextDetailMapper).selectByPrimaryKey(
						with(aNonNull(Integer.class)));
				MaterialImageTextDetail detail = new MaterialImageTextDetail();
				detail.setMaterialId(3);
				will(returnValue(detail));

				allowing(materialImageTextDetailMapper).insertList(
						with(aNonNull(ArrayList.class)));
			}
		});
		Material material = materialService.createImageText(1, new User(),
				new MaterialModel());
		assertNotNull(material);
	}

}
