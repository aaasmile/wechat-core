package com.d1m.wechat.controller.imagetext;

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.d1m.wechat.component.FileUploadConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.util.QrcodeUtils;

import tk.mybatis.mapper.util.StringUtil;

@Controller
@RequestMapping("/imagetext")
public class ImageTextController extends BaseController {
	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialImageTextDetailMapper materialImageTextDetailMapper;
	
	@RequestMapping("/qrcode/{imageTextId}")
	@RequiresPermissions("app-msg:list")
	public void getQrCode(@PathVariable Integer imageTextId, HttpSession session, HttpServletRequest req, HttpServletResponse response) throws Exception {
		Integer wechatId = getWechatId(session);
		MaterialImageTextDetail detail = new MaterialImageTextDetail();
		detail.setWechatId(wechatId);
		detail.setId(imageTextId);
		detail = materialImageTextDetailMapper.selectOne(detail);
		String url = FileUploadConfig.getValue(wechatId, "httpPath") + "/imagetext/html/" + imageTextId + ".html";
		Material materia = materialService.getMaterial(wechatId, detail.getMaterialId());
		if(!StringUtil.isEmpty(materia.getMediaId())) {
			//todo add wxUrl
			//url = detail.getWxUrl();
		}
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();
		QrcodeUtils.encode(url, sos);
		sos.close();
	}
	
	@RequestMapping("/html/{imageTextId}.html")
	public void getImageTextHtml(@PathVariable Integer imageTextId, HttpServletRequest req, HttpServletResponse response) throws Exception {
		MaterialImageTextDetail detail = new MaterialImageTextDetail();
		detail.setId(imageTextId);
		detail = materialImageTextDetailMapper.selectOne(detail);
		
		StringBuffer sf = new StringBuffer();
		sf.append("<html><title>");
		sf.append(detail.getTitle());
		sf.append("</title><body>");
		sf.append(detail.getContent());
		sf.append("</body>");
		sf.append("</html>");
		response.setContentType("text/html;charset=utf-8"); 
		PrintWriter out=response.getWriter();
		out.println(sf.toString());
	}
}
