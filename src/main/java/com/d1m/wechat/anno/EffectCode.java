package com.d1m.wechat.anno;

import com.d1m.wechat.model.enums.Effect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EffectCode {

	public Effect value();

}
