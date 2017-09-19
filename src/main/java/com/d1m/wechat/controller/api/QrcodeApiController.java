package com.d1m.wechat.controller.api;

import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.model.MemberQrcodeInvited;
import com.d1m.wechat.model.QrcodePersonal;
import com.d1m.wechat.service.MemberQrcodeInvitedService;
import com.d1m.wechat.service.QrcodePersonalService;
import com.esotericsoftware.minlog.Log;
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
	@RequestMapping(value = "/getQrcode/{id}.json", method = RequestMethod.GET)
	@ResponseBody
	public QrcodePersonal getQrcode(@PathVariable Integer id,
                                    @RequestParam Integer wechatId,
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
