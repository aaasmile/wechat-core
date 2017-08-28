package com.d1m.wechat.service.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.d1m.wechat.anno.EffectCode;
import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.service.engine.event.IEvent;
import com.d1m.wechat.util.AppContextUtils;
import com.d1m.wechat.util.ClassSearcher;

public class EngineContext implements InitializingBean {

	private static Logger log = LoggerFactory.getLogger(EngineContext.class);

	private static Map<Event, IEvent> eventMaps = new HashMap<Event, IEvent>();

	private static Map<Effect, IEffect> effectMaps = new HashMap<Effect, IEffect>();

	@Override
	public void afterPropertiesSet() throws Exception {
		initEventMap(eventMaps, "com.d1m.wechat.service.engine.event.impl");
		initEffectMap(effectMaps, "com.d1m.wechat.service.engine.effect.impl");
	}

	private void initEventMap(Map<Event, IEvent> map, String pack) {
		Set<Class<?>> effectClasses = ClassSearcher.getClasses(pack);
		for (Class<?> clz : effectClasses) {
			EventCode anno = (EventCode) clz.getAnnotation(EventCode.class);
			log.info("Event anno : {}", anno);
			if (anno == null) {
				continue;
			}
			map.put(anno.value(), (IEvent) AppContextUtils.getBean(clz));
		}
		log.info("events : {}", map);
	}

	private void initEffectMap(Map<Effect, IEffect> map, String pack) {
		Set<Class<?>> effectClasses = ClassSearcher.getClasses(pack);
		for (Class<?> clz : effectClasses) {
			EffectCode anno = (EffectCode) clz.getAnnotation(EffectCode.class);
			log.info("Effect anno : {}", anno);
			if (anno == null) {
				continue;
			}
			map.put(anno.value(), (IEffect) AppContextUtils.getBean(clz));
		}
		log.info("effects : {}", map);
	}

	public static IEvent getEventMaps(Event event) {
		return eventMaps.get(event);
	}

	public static IEffect getEffectMaps(Effect effect) {
		return effectMaps.get(effect);
	}

}
