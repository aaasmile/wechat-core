package com.d1m.wechat.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.mapper.MemberTagTypeMapper;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.service.impl.MemberTagServiceImpl;
import com.github.pagehelper.Page;

public class MemberTagServiceTest {

	Mockery context = new JUnit4Mockery();

	MemberTagServiceImpl memberTagService;
	MemberTagMapper memberTagMapper;
	MemberTagTypeMapper memberTagTypeMapper;

	@Before
	public void setUp() {
		memberTagMapper = context.mock(MemberTagMapper.class);
		memberTagTypeMapper = context.mock(MemberTagTypeMapper.class);
		memberTagService = new MemberTagServiceImpl();
		memberTagService.setMemberTagMapper(memberTagMapper);
		memberTagService.setMemberTagTypeMapper(memberTagTypeMapper);
	}

	@Test
	public void create() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagTypeMapper).selectOne(
						with(aNonNull(MemberTagType.class)));
				will(returnValue(new MemberTagType()));

				oneOf(memberTagMapper).selectCount(
						with(aNonNull(MemberTag.class)));
				will(returnValue(new Integer(0)));

				oneOf(memberTagMapper).insert(with(aNonNull(MemberTag.class)));
				will(returnValue(new Integer(1)));

			}
		});
		List<MemberTagDto> result = null;
		try {
			result = memberTagService.create(new User(), 1,
					new AddMemberTagModel());
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

				oneOf(memberTagMapper).selectOne(
						with(aNonNull(MemberTag.class)));
				will(returnValue(new MemberTag()));

				oneOf(memberTagMapper).getDuplicateName(
						with(aNonNull(Integer.class)),
						with(aNonNull(Integer.class)),
						with(aNonNull(String.class)));
				will(returnValue(null));

				oneOf(memberTagMapper).updateByPrimaryKey(
						with(aNonNull(MemberTag.class)));
				will(returnValue(new Integer(1)));
			}
		});
		MemberTag result = null;
		try {
			result = memberTagService.update(new User(), 1, 1, 1, "name");
		} catch (WechatException e) {
			e.printStackTrace();
		}
		assertEquals("name", result.getName());
	}

	@Test
	public void delete() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagMapper).selectOne(
						with(aNonNull(MemberTag.class)));
				will(returnValue(new MemberTag()));

				oneOf(memberTagMapper).updateByPrimaryKey(
						with(aNonNull(MemberTag.class)));
				will(returnValue(new Integer(1)));
			}
		});
		try {
			memberTagService.delete(new User(), 1, 1);
		} catch (WechatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void list() {
		context.checking(new Expectations() {
			{
				oneOf(memberTagMapper).search(with(aNonNull(Integer.class)),
						with(aNonNull(String.class)),
						with(aNonNull(List.class)),
						with(aNonNull(List.class)),
						with(aNonNull(String.class)),
						with(aNonNull(String.class)));
				Page<MemberTag> arrayList = new Page<MemberTag>();
				MemberTag memberTag = new MemberTag();
				arrayList.add(memberTag);
				will(returnValue(arrayList));
			}
		});
		Page<MemberTag> results = memberTagService.search(1, 1, "name",
				"sortName", "sortDir", 1, 10, true);
		assertTrue(!results.isEmpty());
	}

}
