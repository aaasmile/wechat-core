package com.d1m.wechat.controller.areainfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.service.AreaInfoService;

@Controller
@RequestMapping("/areainfo")
public class AreaInfoController extends BaseController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AreaInfoService areaInfoService;

}
