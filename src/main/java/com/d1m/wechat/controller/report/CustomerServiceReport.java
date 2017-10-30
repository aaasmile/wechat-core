package com.d1m.wechat.controller.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.membertag.MemberTagController;
import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.dto.ReportActivityDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.pamametermodel.SourceUserModel;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by D1M on 2017/5/24.
 */
@Controller
@RequestMapping("/report")
@Api(value="客服报表 API", tags="客服报表接口")
public class CustomerServiceReport extends BaseController {
    private Logger log = LoggerFactory.getLogger(CustomerServiceReport.class);

    @Autowired
    private ConversationService conversationService;
    
	@ApiOperation(value="导出客服报表", tags="客服报表接口")
	@ApiResponses({
		@ApiResponse(code=200, message="导出用户分析"),
		@ApiResponse(code=21008, message="导出客户服务报表开始或结束时间不能为空")
	})
    @RequestMapping(value = "exportCustomerService.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("system-setting:customer-service-config-list")
    public ModelAndView activity(HttpSession session, HttpServletRequest request,
                               HttpServletResponse response){
        SourceUserModel model = null;
        String data = request.getParameter("data");
        if(StringUtils.isNotBlank(data)){
            model = (SourceUserModel)JSON.parseObject(data, SourceUserModel.class);
        }
        if(model == null){
            model = new SourceUserModel();
        }
        if (model.getStart() == null || model.getEnd() == null){
            throw new WechatException(Message.REPORT_CUSTOMER_SERVICE_START_OR_END_NOT_BLANK);
        }
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date exportStartDate = null;
        Date exportEndDate = null;
        try {
            exportStartDate = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
            exportEndDate = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }

        List<ConversationDto> conversationDtos = conversationService.searchCustomerServiceConversation(getWechatId(session), exportStartDate, exportEndDate);
        ModelMap modelMap = new ModelMap();
        modelMap.put("dtos", conversationDtos);
        ConversationData conversationData = new ConversationData();
        return new ModelAndView(conversationData, modelMap);
    }

}
