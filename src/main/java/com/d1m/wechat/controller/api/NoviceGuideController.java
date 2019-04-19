package com.d1m.wechat.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.UserModel;
import com.d1m.wechat.service.UserService;
import com.d1m.wechat.util.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by D1M on 2019/4/18.
 */
@RestController
@RequestMapping("/guide")
@Api(value="新手引导API", tags="新手引导接口")
public class NoviceGuideController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新手引导更新API", tags = "新手引导更新接口")
    @ApiResponse(code = 200, message = "返回成功")
    @RequestMapping(value = "noviceGuide", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateGuide(
            HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        try {
            Subject subject = SecurityUtils.getSubject();
            User currentUser = (User) subject.getPrincipal();
            Integer result =userService.updateGuide(currentUser.getId());
            if(result!=null&&result==1){
                return representation(Message.SUCCESS);
            }else{
                return representation(Message.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            if(Message.getByName(e.getMessage())!=null){
                return representation(Message.getByName(e.getMessage()));
            }
        }
        return representation(Message. SYSTEM_ERROR);
    }

    @ApiOperation(value = "新手引导更新API", tags = "新手引导更新接口")
    @ApiResponse(code = 200, message = "返回成功")
    @RequestMapping(value = "noviceGuide", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getGuide(
            HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        try {
            Subject subject = SecurityUtils.getSubject();
            User currentUser = (User) subject.getPrincipal();
            UserDto userDto =userService.getById(currentUser.getId());
            return representation(Message.SUCCESS,userDto);
        } catch (Exception e) {
            if(Message.getByName(e.getMessage())!=null){
                return representation(Message.getByName(e.getMessage()));
            }
        }
        return representation(Message. SYSTEM_ERROR);
    }
}
