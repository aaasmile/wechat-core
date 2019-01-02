package com.d1m.wechat.service;

import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.MemberMemberTagMapper;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.service.impl.MemberServiceImpl;
import com.github.pagehelper.Page;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class MemberServiceTest {

    Mockery context = new JUnit4Mockery();

    MemberServiceImpl memberService;

    MemberMapper memberMapper;

    MemberTagMapper memberTagMapper;

    MemberMemberTagMapper memberMemberTagMapper;

    @Before
    public void setUp() {
        memberMapper = context.mock(MemberMapper.class);
        memberTagMapper = context.mock(MemberTagMapper.class);
        memberMemberTagMapper = context.mock(MemberMemberTagMapper.class);
        memberService = new MemberServiceImpl();
        memberService.setMemberMapper(memberMapper);
        memberService.setMemberTagMapper(memberTagMapper);
        memberService.setMemberMemberTagMapper(memberMemberTagMapper);
    }

    @Test
    public void searchTest() {
        context.checking(new Expectations() {
            {
                oneOf(memberMapper).search(with(aNonNull(Integer.class)),
                        with(aNull(String.class)), with(aNull(String.class)),
                        with(aNull(Byte.class)), with(aNull(Integer.class)),
                        with(aNull(Integer.class)), with(aNull(Integer.class)),
                        with(aNull(Boolean.class)), with(aNull(Integer.class)),
                        with(aNull(Integer.class)), with(aNull(Integer.class)),
                        with(aNull(Integer.class)), with(aNull(Date.class)),
                        with(aNull(Date.class)), with(aNull(Date.class)),
                        with(aNull(Date.class)), with(aNull(Boolean.class)),
                        with(aNull(String.class)), with(aNull(String.class)),
                        with(aNull(Integer[].class)),
                        with(aNonNull(String.class)),
                        with(aNonNull(String.class)),
                        with(aNull(Integer.class)),
                        with(aNull(Date.class)), null);
                Page<Member> arrayList = new Page<Member>();
                Member member = new Member();
                arrayList.add(member);
                will(returnValue(arrayList));
            }
        });
        Page<MemberDto> results = memberService.search(1,
                new AddMemberTagModel(), true);
        assertTrue(!results.isEmpty());
    }
    // @Test
    // public void reportTest() {
    // context.checking(new Expectations() {
    // {
    // oneOf(memberMapper).search(with(aNonNull(Integer.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(Integer.class)),
    // with(aNonNull(String.class)),
    // with(aNonNull(Integer[].class)));
    // Page<Member> arrayList = new Page<Member>();
    // Member member = new Member();
    // arrayList.add(member);
    // will(returnValue(arrayList));
    // }
    // });
    // List<MemberDto> results = memberService.search(1, "nickname", 1,
    // "country", "province", "city", 1, 1, "sortName", "sortDir", 1,
    // 90, 0, 100, "2016-04-03 09:30", "2016-04-06 09:30",
    // "2016-04-03 09:30", "2016-04-06 09:30", 1, "二维码扫描",
    // new Integer[] { 1, 2 });
    // assertTrue(!results.isEmpty());
    // }

    // @Test
    // public void addMemberTagTest() {
    // context.checking(new Expectations() {
    // {
    // allowing(memberTagMapper
    // .selectOne(with(aNonNull(MemberTag.class))));
    // will(returnValue(new MemberTag()));
    //
    // oneOf(memberTagMapper.countDuplicateName(
    // with(aNonNull(Integer.class)),
    // with(aNull(Integer.class)),
    // with(aNonNull(String.class))));
    // will(returnValue(0));
    //
    // oneOf(memberMapper).selectByMemberId(
    // with(aNonNull(Integer[].class)),
    // with(aNonNull(Integer.class)));
    // List<MemberDto> dtolist = new ArrayList<MemberDto>();
    // List<MemberTagDto> taglist = new ArrayList<MemberTagDto>();
    // MemberDto dto = new MemberDto();
    // MemberTagDto tagDto = new MemberTagDto();
    // taglist.add(tagDto);
    // dto.setMemberTagDtos(taglist);
    // dtolist.add(dto);
    // will(returnValue(dtolist));
    //
    // allowing(memberMemberTagMapper).insertList(
    // with(aNonNull(new ArrayList<MemberMemberTag>()
    // .getClass())));
    // will(returnValue(1));
    //
    // }
    // });
    // try {
    // boolean result = memberService.addMemberTag(1, new User(), null,
    // new Integer[] { 1, 2 }, "[{id:2},{id:3},{name:'新标签'}]");
    // assertTrue(result);
    // } catch (WechatException e) {
    // e.printStackTrace();
    // }
    // }
}
