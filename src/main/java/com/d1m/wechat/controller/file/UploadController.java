package com.d1m.wechat.controller.file;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.enums.FileType;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.util.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/upload")
@Api(value="上传API", tags="上传接口")
public class UploadController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private MaterialService materialService;
	
	@ApiOperation(value="上传文件", tags="上传接口")
	@ApiResponse(code=200, message="1-上传文件成功")
	@RequestMapping(value = "image/new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadImage(
			@ApiParam("上传文件")
			@RequestParam(required = false) MultipartFile file,
			HttpServletResponse response, HttpSession session) {
		try {
			Upload upload = upload(getWechatId(), file, Constants.IMAGE, Constants.NORMAL);
			Material material = materialService.createImage(
					getWechatId(session), getUser(session), upload);
			MaterialDto materialDto = new MaterialDto();
			materialDto.setId(material.getId());
			materialDto.setTitle(material.getName());
			materialDto.setUrl(material.getPicUrl());
			return representation(Message.FILE_UPLOAD_SUCCESS, materialDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiIgnore
	@RequestMapping(value = "video/new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject uploadVideo(
			@ApiParam("上传文件")
				@RequestParam MultipartFile file,
			HttpServletResponse response, HttpSession session) {
		try {
			// Upload upload = upload(file, Constants.VIDEO);
			// Material material = materialService.createMaterialVideo(
			// getWechatId(session), getUser(session), upload);
			// return representation(Message.FILE_UPLOAD_SUCCESS,
			// material.getPicUrl());
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	public static Upload upload(Integer wechatId, MultipartFile file, String type,
                                String subType) throws Exception {
		if (file.isEmpty()) {
			throw new WechatException(Message.FILE_NOT_BLANK);
		}
		long size = file.getSize();
		log.info("Upload File Size: {}", size);
		FileUploadConfigUtil config = FileUploadConfigUtil.getInstance();
		if (size > ParamUtil.getLong(config.getValue(null, type + "_max_size"), 0L)) {
			throw new WechatException(Message.FILE_IS_TOO_BIG);
		}
		String fileName = file.getOriginalFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		String imgExt = config.getValue(null, type + "_ext");
		if (!imgExt.contains(fileExt)) {
			throw new WechatException(Message.FILE_EXT_NOT_SUPPORT);
		}
		String format = DateUtil.yyyyMMddHHmmss.format(new Date());
		String newFileName = FileUtils.generateNewFileName(
				MD5.MD5Encode(fileName.replace("." + fileExt, "")), fileExt);

		File root = FileUtils
				.getUploadPathRoot(wechatId, type + File.separator + subType);
		File dir = new File(root, format.substring(0, 6));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String absolutePath = dir.getAbsoluteFile().getAbsolutePath();
		log.info("dir path : " + absolutePath);
		file.transferTo(new File(dir, newFileName));
		log.info("dir name : " + dir.getName());

		String accessPath = config.getValue(wechatId,"upload_url_base") + File.separator
				+ type + File.separator + subType + File.separator
				+ dir.getName() + File.separator + newFileName;
		log.info("accessPath : {}", accessPath);
		Upload upload = new Upload();
		upload.setNewFileName(newFileName);
		upload.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
		upload.setAbsolutePath(absolutePath + File.separator + newFileName);
		upload.setAccessPath(accessPath);
		return upload;
	}
	
	/**
	 * Upload file to WX from file URL.
	 * 
	 * @param filePath String
	 * @param type String
	 * @param subType String
	 * @return uploaded file
	 * @throws Exception
	 */
	public static Upload upload(Integer wechatId, String filePath, String type,
			String subType) throws Exception {
	
		URL fileUrl = new URL(filePath);
		long size = FileUtils.getFileSize(fileUrl);
		FileUploadConfigUtil config = FileUploadConfigUtil.getInstance();
		if (size > ParamUtil.getLong(config.getValue(null, type + "_max_size"), 0L)) {
			throw new WechatException(Message.FILE_IS_TOO_BIG);
		}
		String fileName = FilenameUtils.getName(fileUrl.getPath());
		if (filePath.startsWith("https://mmbiz")) {
			fileName = FileUtils.generateWxFileName(filePath);
		}
		FileType fileType = FileTypeUtil.getType(fileUrl.openStream());
		String fileExt = null;
		if(fileType!=null){
			fileExt = fileType.name().toLowerCase();
		}
		String imgExt = config.getValue(null, type + "_ext");
		if (!imgExt.contains(fileExt)) {
			throw new WechatException(Message.FILE_EXT_NOT_SUPPORT);
		}
		String format = DateUtil.yyyyMMddHHmmss.format(new Date());
		String newFileName = FileUtils.generateNewFileName(
				MD5.MD5Encode(fileName.replace("." + fileExt, "")), fileExt);

		File root = FileUtils
				.getUploadPathRoot(wechatId, type + File.separator + subType);
		File dir = new File(root, format.substring(0, 6));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String absolutePath = dir.getAbsoluteFile().getAbsolutePath();
		log.info("dir path : " + absolutePath);
		//org.apache.commons.io.FileUtils.copyURLToFile(fileUrl, new File(dir, newFileName));
		HttpFileUtil.copy(filePath, dir + "/" + newFileName);
		log.info("dir name : " + dir.getName());

		String accessPath = config.getValue(wechatId, "upload_url_base") + "/"
				+ type + "/" + subType + "/"
				+ dir.getName() + "/" + newFileName;
		log.info("accessPath : {}", accessPath);
		Upload upload = new Upload();
		upload.setNewFileName(newFileName);
		upload.setFileName(fileName.substring(0, -1!=fileName.lastIndexOf(".")?fileName.lastIndexOf("."): fileName.length()));
		upload.setAbsolutePath(absolutePath + File.separator + newFileName);
		upload.setAccessPath(accessPath);
		return upload;
	}
	
	public static List<Upload> uploadList(Integer wechatId, MultipartFile[] files, String type,
			String subType) throws Exception {
		if (files.length == 0) {
			throw new WechatException(Message.FILE_NOT_BLANK);
		}
		List<Upload> list = new ArrayList<Upload>();
		for(MultipartFile file : files){
			long size = file.getSize();
			FileUploadConfigUtil config = FileUploadConfigUtil.getInstance();
			if (size > ParamUtil.getLong(config.getValue(null, type + "_max_size"), 0L)) {
				throw new WechatException(Message.FILE_IS_TOO_BIG);
			}
			String fileName = file.getOriginalFilename();
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
					.toLowerCase();
			String imgExt = config.getValue(null, type + "_ext");
			if (!imgExt.contains(fileExt)) {
				throw new WechatException(Message.FILE_EXT_NOT_SUPPORT);
			}
			String format = DateUtil.yyyyMMddHHmmss.format(new Date());
			String newFileName = FileUtils.generateNewFileName(
					MD5.MD5Encode(fileName.replace("." + fileExt, "")), fileExt);

			File root = FileUtils
					.getUploadPathRoot(wechatId, type + File.separator + subType);
			File dir = new File(root, format.substring(0, 6));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String absolutePath = dir.getAbsoluteFile().getAbsolutePath();
			log.info("dir path : " + absolutePath);
			file.transferTo(new File(dir, newFileName));
			log.info("dir name : " + dir.getName());

			String accessPath = config.getValue(wechatId, "upload_url_base") + File.separator
					+ type + File.separator + subType + File.separator
					+ dir.getName() + File.separator + newFileName;
			log.info("accessPath : {}", accessPath);
			Upload upload = new Upload();
			upload.setNewFileName(newFileName);
			upload.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
			upload.setAbsolutePath(absolutePath + File.separator + newFileName);
			upload.setAccessPath(accessPath);
			list.add(upload);
		}
		
		return list;
	}
}
