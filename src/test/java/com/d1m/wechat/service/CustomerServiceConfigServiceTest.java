package com.d1m.wechat.service;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import com.d1m.wechat.dto.CustomerServiceConfigDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.CustomerServiceConfigMapper;
import com.d1m.wechat.model.CustomerServiceConfig;
import com.d1m.wechat.pamametermodel.CustomerServiceConfigModel;
import com.d1m.wechat.service.impl.CustomerServiceConfigServiceImpl;

import static org.junit.Assert.assertTrue;

public class CustomerServiceConfigServiceTest {

	Mockery context = new JUnit4Mockery();

	CustomerServiceConfigServiceImpl customerServiceConfigService;

	CustomerServiceConfigMapper customerServiceConfigMapper;

	@Before
	public void setUp() {
		customerServiceConfigMapper = context
				.mock(CustomerServiceConfigMapper.class);
		customerServiceConfigService = new CustomerServiceConfigServiceImpl();
		customerServiceConfigService
				.setCustomerServiceConfigMapper(customerServiceConfigMapper);
	}

	@Test
	public void testDelete() throws WechatException {

		context.checking(new Expectations() {
			{
				CustomerServiceConfig config = new CustomerServiceConfig();
				config.setWechatId(1);
				config.setId(1);
				oneOf(customerServiceConfigMapper).selectOne(
						with(aNonNull(CustomerServiceConfig.class)));
				will(returnValue(config));

				allowing(customerServiceConfigMapper).delete(
						with(aNonNull(CustomerServiceConfig.class)));
				will(returnValue(1));
			}
		});
		assertTrue(customerServiceConfigService.delete(1, 1));
	}

	@Test
	public void testSearch() {
		context.checking(new Expectations() {
			{
				oneOf(customerServiceConfigMapper).search(
						with(aNonNull(Integer.class)),
						with(aNull(String.class)));
				List<CustomerServiceConfigDto> list = new ArrayList<CustomerServiceConfigDto>();
				CustomerServiceConfigDto configDto = new CustomerServiceConfigDto();
				list.add(configDto);
				will(returnValue(list));
			}
		});
		List<CustomerServiceConfigDto> list = customerServiceConfigService
				.search(1, null);
		assertTrue(!list.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		context.checking(new Expectations() {
			{
				CustomerServiceConfig config = new CustomerServiceConfig();
				config.setWechatId(1);
				config.setId(1);
				allowing(customerServiceConfigMapper).selectOne(
						with(aNonNull(CustomerServiceConfig.class)));
				will(returnValue(config));

				allowing(customerServiceConfigMapper)
						.updateByPrimaryKeySelective(
								with(aNonNull(CustomerServiceConfig.class)));
				will(returnValue(1));

				allowing(customerServiceConfigMapper).insertList(
						with(aNonNull(ArrayList.class)));
				will(returnValue(1));
			}
		});
		List<CustomerServiceConfigModel> list = new ArrayList<CustomerServiceConfigModel>();
		CustomerServiceConfigModel model = new CustomerServiceConfigModel();
		model.setValue("有什么帮助的吗？");
		model.setGroup("QUICK_REPLY");
		model.setGroupLabel("快速回复");
		list.add(model);

		model = new CustomerServiceConfigModel();
		model.setId(2);
		model.setValue("再见");
		list.add(model);

		model = new CustomerServiceConfigModel();
		model.setId(3);
		model.setValue("09:00");
		list.add(model);

		model = new CustomerServiceConfigModel();
		model.setKey("MONDAY_OFFICE_HOUR_END");
		model.setValue("18:00");
		model.setGroup("OFFICE_HOUR");
		model.setGroupLabel("工作时间");
		list.add(model);

		assertTrue(customerServiceConfigService.updateGroup(1, list));
	}
}
