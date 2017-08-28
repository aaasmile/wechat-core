package com.d1m.wechat.service;

import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.dto.QrcodeTypeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.QrcodeTypeMapper;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.QrcodeTypeStatus;
import com.d1m.wechat.pamametermodel.QrcodeTypeModel;
import com.d1m.wechat.service.impl.QrcodeTypeServiceImpl;
import com.github.pagehelper.Page;

public class QrcodeTypeServiceTest {
	QrcodeTypeServiceImpl qrcodeTypeService;
	QrcodeTypeMapper qrcodeTypeMapper;

	Mockery context = new JUnit4Mockery();

	@Before
	public void setUp() {
		qrcodeTypeMapper = context.mock(QrcodeTypeMapper.class);
		qrcodeTypeService = new QrcodeTypeServiceImpl();
		qrcodeTypeService.setQrcodeTypeMapper(qrcodeTypeMapper);
	}

	@Test
	public void create() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeTypeMapper).countDuplicateName(
						with(aNonNull(Integer.class)),
						with(aNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(Byte.class)));
				will(returnValue(0));

				oneOf(qrcodeTypeMapper).selectOne(
						with(aNonNull(QrcodeType.class)));
				QrcodeType record = new QrcodeType();
				record.setStatus(QrcodeTypeStatus.INUSED.getValue());
				will(returnValue(record));

				oneOf(qrcodeTypeMapper)
						.insert(with(aNonNull(QrcodeType.class)));
				will(returnValue(new Integer(1)));
			}
		});
		User user = new User();
		user.setId(1);
		QrcodeType result = null;
		try {
			result = qrcodeTypeService.create(1, user, new QrcodeTypeModel());
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
	}

	@Test
	public void update() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeTypeMapper).selectOne(
						with(aNonNull(QrcodeType.class)));
				QrcodeType record = new QrcodeType();
				record.setStatus(QrcodeTypeStatus.INUSED.getValue());
				will(returnValue(record));

				oneOf(qrcodeTypeMapper).countDuplicateName(
						with(aNonNull(Integer.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNull(Integer.class)), with(aNonNull(Byte.class)));
				will(returnValue(0));

				oneOf(qrcodeTypeMapper).updateByPrimaryKey(
						with(aNonNull(QrcodeType.class)));
				will(returnValue(new Integer(1)));
			}
		});
		QrcodeType result = null;
		try {
			result = qrcodeTypeService.update(1, new QrcodeTypeModel());
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
	}

	@Test
	public void delete() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeTypeMapper).selectOne(
						with(aNonNull(QrcodeType.class)));
				QrcodeType record = new QrcodeType();
				record.setStatus(QrcodeTypeStatus.INUSED.getValue());
				will(returnValue(record));

				oneOf(qrcodeTypeMapper).updateByPrimaryKey(
						with(aNonNull(QrcodeType.class)));
				will(returnValue(new Integer(1)));
			}
		});
		try {
			qrcodeTypeService.delete(1, 1);
		} catch (WechatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void list() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeTypeMapper).list(with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				Page<QrcodeType> arrayList = new Page<QrcodeType>();
				QrcodeType qrcodeType = new QrcodeType();
				arrayList.add(qrcodeType);
				will(returnValue(arrayList));
			}
		});
		Page<QrcodeTypeDto> result = qrcodeTypeService.list(1,
				new QrcodeTypeModel());
		assertTrue(!result.isEmpty());
	}
}
