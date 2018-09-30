package com.d1m.wechat;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.d1m.wechat.dto.FunctionDto;
import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.FunctionModel;
import com.d1m.wechat.security.AuthcAuthenticationFilter;
import com.d1m.wechat.util.CookieUtils;
import com.d1m.wechat.util.Message;

@Controller
public class SwaggerController {

    @RequestMapping(value = "/apis")
    public String index() {
        System.out.println("swagger-ui.html");
        
        String username = "admin";
        String password = "12345";
        
		UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.sha256Hex(password));  
	    //token.setRememberMe(true);  
	    Subject subject = SecurityUtils.getSubject();  
        subject.login(token);  
        
        return "redirect:swagger-ui.html";
    }
}
