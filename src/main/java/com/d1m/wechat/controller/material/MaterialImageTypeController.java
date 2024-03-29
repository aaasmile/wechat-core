package com.d1m.wechat.controller.material;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MaterialImageTypeDto;
import com.d1m.wechat.model.MaterialImageType;
import com.d1m.wechat.service.MaterialImageTypeService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/material-image-type")
@Api(value="素材图片类型API", tags="素材图片类型接口")
public class MaterialImageTypeController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(MaterialImageTypeController.class);

	@Autowired
	private MaterialImageTypeService materialImageTypeService;
	
	@ApiOperation(value="获取素材图片类型列表", tags="素材图片类型接口")
	@ApiResponse(code=200, message="1-获取素材图片类型列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject list(
			@ApiParam(name="素材名称", required=false)
				@RequestParam(required = false) String name,
			@ApiParam(name="排序列", required=false)
				@RequestParam(required = false) String sortName,
			@ApiParam(name="素材顺序", required=false)
				@RequestParam(required = false) String sortDir,
			@ApiParam(name="当前页", required=false, defaultValue="1")
				@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			@ApiParam(name="分页大小", required=false, defaultValue="10")
				@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Page<MaterialImageType> page = materialImageTypeService.search(
				getWechatId(session), name, sortName, sortDir, pageNum,
				pageSize, true);
		List<MaterialImageTypeDto> memberDtos = new ArrayList<MaterialImageTypeDto>();
		for (MaterialImageType materialImageType : page.getResult()) {
			memberDtos.add(convert(materialImageType));
		}
		return representation(Message.MATERIAL_IMAGE_TYPE_LIST_SUCCESS,
				memberDtos, pageNum, pageSize, page.getTotal());
	}

	private MaterialImageTypeDto convert(MaterialImageType materialImageType) {
		MaterialImageTypeDto dto = new MaterialImageTypeDto();
		dto.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(materialImageType
				.getCreatedAt()));
		dto.setCreatorId(materialImageType.getCreatorId());
		dto.setId(materialImageType.getId());
		dto.setName(materialImageType.getName());
		dto.setParentId(materialImageType.getParentId());
		dto.setWechatId(materialImageType.getWechatId());
		return dto;
	}

}
