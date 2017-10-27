package com.d1m.wechat.controller.report;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.d1m.wechat.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * ReportController
 *
 * @author f0rb on 2017-02-07.
 */
@Controller
@RequestMapping("/report")
@Api(value="报表通用API", tags="报表通用接口")
public class ReportController extends BaseController {

    /**
     * 通用的报表导出接口
     *
     * @param reportKey 报表的主键
     * @param data      查询数据
     * @return xls的View
     */
	@ApiOperation(value="导出xls文件", tags="报表通用接口")
	@ApiResponse(code=200, message="导出xls文件")
    @RequestMapping(value = "{key}.xls", method = RequestMethod.POST)
    @RequiresPermissions("data-report")
    public String report(Model model, 
    		@ApiParam("key")
    			@PathVariable("key") String reportKey, String data) {
        model.addAttribute(ReportXlsView.REPORT_KEY, reportKey);
        model.addAttribute(ReportXlsView.WECHAT_ID, getWechatId());
        if (StringUtils.isNotBlank(data)) {
            model.addAttribute(ReportXlsView.PARAMS, JSON.parseObject(data, Map.class));
        }
        return "report_xls";
    }
}
