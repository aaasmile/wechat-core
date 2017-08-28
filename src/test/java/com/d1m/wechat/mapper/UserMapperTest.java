package com.d1m.wechat.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapperTest extends BaseTxTests {

	@Autowired
	UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Test
	public void testInsert() {
		// User user = new User();
		// user.setCreatedAt(new Date());
		// user.setEmail("test@wechat.com");
		// user.setPassword("123456");
		// user.setUsername("test");
		// user.setWechatId(1);
		// userMapper.insert(user);
		// assertTrue(!userMapper.selectAll().isEmpty());
	}

}
