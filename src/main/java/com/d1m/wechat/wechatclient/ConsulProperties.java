package com.d1m.wechat.wechatclient;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.d1m.wechat.common.Config;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;

@Component
public class ConsulProperties {
	
	Logger log = LoggerFactory.getLogger(ConsulProperties.class);
	
	
	public void onStartup() {
		
        String key = Config.bootstrap.getProperty("spring.cloud.consul.config.data-key");
        if(StringUtils.isNotEmpty(System.getProperty("data-key"))) {
			log.info("data-key>>" + System.getProperty("data-key"));
			key = System.getProperty("data-key");
		}
		log.info("Consul start!");
		
		Response<GetValue> response = consulClient().getKVValue("configuration/application/" + key);
		log.info(response.getValue().getDecodedValue());
		
		Properties properties = new Properties();
		StringReader reader = new StringReader(response.getValue().getDecodedValue());
		try {
			properties.load(reader);
			for (Entry<Object, Object> m : properties.entrySet()) {
				System.setProperty((String) m.getKey(), (String) m.getValue());
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		log.info("Consul start successful!");
	}
	
	@Bean
	public ConsulClient consulClient() {
		String host = Config.bootstrap.getProperty("spring.cloud.consul.host");
		if(StringUtils.isNotEmpty(System.getProperty("ENV_HOST"))) {
			log.info("ENV_HOST>>" + System.getProperty("ENV_HOST"));
			host = System.getProperty("ENV_HOST");
		}
		ConsulClient consulClient = new ConsulClient(host, 8500);
		return consulClient;
	}
}
