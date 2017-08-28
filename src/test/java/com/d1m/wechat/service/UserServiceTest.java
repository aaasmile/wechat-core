package com.d1m.wechat.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.mapper.UserMapper;
import com.d1m.wechat.model.User;
import com.d1m.wechat.service.impl.UserServiceImpl;

public class UserServiceTest {

	Mockery context = new JUnit4Mockery();

	UserServiceImpl userService;
	UserMapper userMapper;

	@Before
	public void setUp() {
		userMapper = context.mock(UserMapper.class);
		userService = new UserServiceImpl();
		userService.setUserMapper(userMapper);
	}

	@Test
	public void create() {
		final User user = new User();
		user.setCreatedAt(new Date());
		user.setEmail("test@wechat.com");
		user.setPassword("pass");
		user.setUsername("test");
		context.checking(new Expectations() {
			{
				oneOf(userMapper).insert(with(aNonNull(User.class)));
				will(returnValue(new Integer(1)));
			}
		});
		int result = userService.save(user);
		assertTrue(result > 0);
	}

	@Test(expected = AssertionError.class)
	public void deleteFail() {
		userService.delete(null);
	}

}
