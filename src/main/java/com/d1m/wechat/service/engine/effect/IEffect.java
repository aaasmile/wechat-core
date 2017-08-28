package com.d1m.wechat.service.engine.effect;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface IEffect {

	void handle(Map<String, Object> map);
}
