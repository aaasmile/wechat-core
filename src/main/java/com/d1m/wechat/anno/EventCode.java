package com.d1m.wechat.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.d1m.wechat.model.enums.Event;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventCode {

	public Event value();

}
