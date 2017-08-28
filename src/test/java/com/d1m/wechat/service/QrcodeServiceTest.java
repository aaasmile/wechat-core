package com.d1m.wechat.service;

import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.QrcodeMapper;
import com.d1m.wechat.mapper.QrcodeTypeMapper;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.QrcodeStatus;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.impl.QrcodeServiceImpl;
import com.github.pagehelper.Page;

public class QrcodeServiceTest {

	QrcodeServiceImpl qrcodeService;
	QrcodeMapper qrcodeMapper;
	QrcodeTypeMapper qrcodeTypeMapper;
	Mockery context = new JUnit4Mockery();

	@Before
	public void setUp() {
		qrcodeMapper = context.mock(QrcodeMapper.class);
		qrcodeTypeMapper = context.mock(QrcodeTypeMapper.class);
		qrcodeService = new QrcodeServiceImpl();
		qrcodeService.setQrcodeMapper(qrcodeMapper);
		qrcodeService.setQrcodeTypeMapper(qrcodeTypeMapper);
	}

	@Test
	public void create() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeTypeMapper).selectOne(
						with(aNonNull(QrcodeType.class)));
				QrcodeType qt = new QrcodeType();
				qt.setId(1);
				will(returnValue(qt));

				oneOf(qrcodeMapper).countDuplicateName(
						with(aNonNull(Integer.class)),
						with(aNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(Byte.class)));
				will(returnValue(new Integer(0)));

				oneOf(qrcodeMapper).insert(with(aNonNull(Qrcode.class)));
				will(returnValue(new Integer(1)));
			}
		});
		Qrcode result = null;
		try {
			User user = new User();
			user.setId(1);
			result = qrcodeService.create(1, user, new QrcodeModel());
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
	}

	@Test
	public void update() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeMapper).selectOne(with(aNonNull(Qrcode.class)));
				Qrcode qrcode = new Qrcode();
				qrcode.setStatus(QrcodeStatus.INUSED.getValue());
				will(returnValue(qrcode));

				oneOf(qrcodeTypeMapper).selectOne(
						with(aNonNull(QrcodeType.class)));
				will(returnValue(new QrcodeType()));

				oneOf(qrcodeMapper).countDuplicateName(
						with(aNonNull(Integer.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(Byte.class)));
				will(returnValue(0));

				oneOf(qrcodeMapper).updateByPrimaryKey(
						with(aNonNull(Qrcode.class)));
				will(returnValue(new Integer(1)));
			}
		});
		Qrcode result = null;
		try {
			result = qrcodeService.update(1, 1, new QrcodeModel());
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
	}

	@Test
	public void delete() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeMapper).selectOne(with(aNonNull(Qrcode.class)));
				Qrcode qrcode = new Qrcode();
				qrcode.setStatus(QrcodeStatus.INUSED.getValue());
				will(returnValue(qrcode));

				oneOf(qrcodeTypeMapper).selectByPrimaryKey(
						with(aNonNull(Integer.class)));
				will(returnValue(new QrcodeType()));

				oneOf(qrcodeMapper).updateByPrimaryKey(
						with(aNonNull(Qrcode.class)));
				will(returnValue(new Integer(1)));
			}
		});
		try {
			qrcodeService.delete(1, 1);
		} catch (WechatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void list() {
		context.checking(new Expectations() {
			{
				oneOf(qrcodeMapper).list(with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(Byte.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				Page<Qrcode> arrayList = new Page<Qrcode>();
				Qrcode qrcode = new Qrcode();
				arrayList.add(qrcode);
				will(returnValue(arrayList));
			}
		});
		Page<QrcodeDto> result = qrcodeService.list(1, new QrcodeModel());
		assertTrue(!result.isEmpty());
	}

}
