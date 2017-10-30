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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import tk.mybatis.mapper.util.StringUtil;

@Controller
@RequestMapping("/imagetext")
@Api(value="图文API", tags="图文接口")
public class ImageTextController extends BaseController {
	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialImageTextDetailMapper materialImageTextDetailMapper;
	
	@ApiOperation(value="获取二维码", tags="图文接口")
	@ApiResponse(code=200, message="1-生成二维码")
	@RequestMapping("/qrcode/{imageTextId}")
	@RequiresPermissions("app-msg:list")
	public void getQrCode(
			@ApiParam("图文ID")
				@PathVariable Integer imageTextId, HttpSession session, HttpServletRequest req, HttpServletResponse response) throws Exception {
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
	
	@ApiOperation(value="获取根据imageTextId来生成HTML页面", tags="图文接口")
	@ApiResponse(code=200, message="1-生成HTML页面")
	@RequestMapping("/html/{imageTextId}.html")
	public void getImageTextHtml(
			@ApiParam("图文ID")
				@PathVariable Integer imageTextId, HttpServletRequest req, HttpServletResponse response) throws Exception {
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
