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

@Controller
@RequestMapping("/material-image-type")
public class MaterialImageTypeController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(MaterialImageTypeController.class);

	@Autowired
	private MaterialImageTypeService materialImageTypeService;

	@RequestMapping(value = "list.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject list(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String sortName,
			@RequestParam(required = false) String sortDir,
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
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
