package com.d1m.wechat.controller.api;

import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.model.MemberQrcodeInvited;
import com.d1m.wechat.model.QrcodePersonal;
import com.d1m.wechat.service.MemberQrcodeInvitedService;
import com.d1m.wechat.service.QrcodePersonalService;
import com.esotericsoftware.minlog.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * AshZhang created by 2017-09-13 18:42:13
 */
@Controller
@RequestMapping("/api/qrcode")
@Api(value="二维码API", tags="二维码接口")
public class QrcodeApiController extends ApiController {

	private Logger log = LoggerFactory.getLogger(QrcodeApiController.class);

	@Autowired
	private QrcodePersonalService qrcodePersonalService;

	@Autowired
	private MemberQrcodeInvitedService invitedService;

    @Resource
    TenantHelper tenantHelper;

	/**
	 * 根据scene获取临时二维码
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据scene获取临时二维码", tags="二维码接口")
	@ApiResponse(code=200, message="返回创建的二维码信息")
	@RequestMapping(value = "/getQrcode/{id}.json", method = RequestMethod.GET)
	@ResponseBody
	public QrcodePersonal getQrcode(
								@ApiParam("ID")
									@PathVariable Integer id,
								@ApiParam("公众号ID")
                                    @RequestParam Integer wechatId,
                                @ApiParam("场景值")
                                    @RequestParam String scene,
                                    HttpSession session,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        // 多数据源支持,临时方案
        String domain = tenantHelper.getTenantByWechatId(wechatId);
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            Log.info("mq domain: "+domain);
        }
		QrcodePersonal qrcodePersonal = null;
		try {
			return qrcodePersonalService.create(wechatId, id,scene);
		} catch (Exception e) {
			log.error(e.getMessage());
			return qrcodePersonal;
		}
	}

	/**
	 * 根据memberId 获取memberInvited record
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据被邀请者的ID查询发送邀请二维码者的列表", tags="二维码接口")
	@ApiResponse(code=200, message="返回发送邀请二维码者的列表")
	@RequestMapping(value = "/getQrcodeInvited/{id}.json", method = RequestMethod.GET)
	@ResponseBody
	public List<MemberQrcodeInvited> getQrcodeInvited(@PathVariable Integer id,
                                                      @RequestParam Integer wechatId,
                                                      HttpSession session,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        // 多数据源支持,临时方案
        String domain = tenantHelper.getTenantByWechatId(wechatId);
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            Log.info("mq domain: "+domain);
        }
		List<MemberQrcodeInvited> listOrderByCreatedAt = null;
		try {
			return invitedService.getListOrderByCreatedAt(wechatId, id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return listOrderByCreatedAt;
		}
	}

	/**
	 * 记录用户扫码邀请记录
	 * @return
	 */
	@ApiOperation(value="创建新的二维码分享后分享者和扫码人的关系数据", tags="二维码接口")
	@ApiResponse(code=200, message="返回创建数量")
	@RequestMapping(value = "/createInvited.json", method = RequestMethod.POST)
	@ResponseBody
	public Integer createInvited(@RequestBody MemberQrcodeInvited invited,
                                 @RequestParam Integer wechatId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 多数据源支持,临时方案
        String domain = tenantHelper.getTenantByWechatId(wechatId);
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            Log.info("mq domain: "+domain);
        }
		int i = 0;
		try {

			return invitedService.create(invited);
		} catch (Exception e) {
			log.error(e.getMessage());
			return i;
		}
	}



}
