package com.d1m.wechat.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberTagTypeMapper;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.service.impl.MemberTagTypeServiceImpl;
import com.github.pagehelper.Page;

public class MemberTagTypeServiceTest {

	Mockery context = new JUnit4Mockery();

	MemberTagTypeServiceImpl memberTagTypeService;

	MemberTagTypeMapper memberTagTypeMapper;

	@Before
	public void setUp() {
		memberTagTypeMapper = context.mock(MemberTagTypeMapper.class);
		memberTagTypeService = new MemberTagTypeServiceImpl();
		memberTagTypeService.setMemberTagTypeMapper(memberTagTypeMapper);
	}

	@Test
	public void create() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagTypeMapper).selectCount(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new Integer(0)));

				oneOf(memberTagTypeMapper).insert(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new Integer(1)));
			}
		});
		MemberTagType result = null;
		try {
			result = memberTagTypeService.create(new User(), 1, "name", 0);
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertTrue(result != null);
	}

	@Test
	public void update() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagTypeMapper).selectOne(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new MemberTagType()));

				oneOf(memberTagTypeMapper).countDuplicateName(
						with(aNonNull(Integer.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(String.class)));
				will(returnValue(new Integer(0)));

				oneOf(memberTagTypeMapper).updateByPrimaryKey(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new Integer(1)));
			}
		});
		MemberTagType result = null;
		try {
			result = memberTagTypeService.update(new User(), 1, 1, "name");
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertEquals("name", result.getName());
	}

	@Test
	public void delete() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagTypeMapper).selectOne(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new MemberTagType()));

				oneOf(memberTagTypeMapper).updateByPrimaryKey(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new Integer(1)));
			}
		});
		try {
			memberTagTypeService.delete(new User(), 1, 1);
		} catch (WechatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void list() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagTypeMapper).search(
						with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				Page<MemberTagType> list = new Page<MemberTagType>();
				MemberTagType memberTagType = new MemberTagType();
				list.add(memberTagType);
				will(returnValue(list));
			}
		});
		List<MemberTagType> results = memberTagTypeService.search(1, "name",
				"sortName", "sortDir", 1, 10, true);
		assertTrue(!results.isEmpty());
	}

}
