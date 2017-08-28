package com.d1m.wechat.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.util.Message;

@Component
public class AuthorizationExceptionResolver implements HandlerExceptionResolver  {  
  
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
         if(ex instanceof AuthorizationException){
        	JSONObject json = new JSONObject();
 			json.put("resultCode", Message.NO_PERMISSION.getCode());
 			json.put("msg", Message.NO_PERMISSION.name());
			try {
				PrintWriter out = response.getWriter();
				out.print(json);
	 			out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }  
        return null;  
	}
}