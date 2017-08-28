package com.d1m.wechat.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {
	public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) throws IllegalAccessException, InvocationTargetException, InstantiationException{  
	    if (source.size()==0) return Collections.emptyList();
	    List<E> res = new ArrayList<E>(source.size());
	    for (Object o : source) {
	        E e = destinationClass.newInstance();
	        BeanUtils.copyProperties(e, o);
	        res.add(e);
	    }
	    return res;  
	}
}
