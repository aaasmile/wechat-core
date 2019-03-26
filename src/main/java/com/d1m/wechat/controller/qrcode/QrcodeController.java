package com.d1m.wechat.controller.qrcode;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.QrcodeService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "二维码API", tags = "二维码接口")
@Controller
@RequestMapping("/qrcode")
public class QrcodeController extends BaseController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QrcodeService qrcodeService;

    @ApiOperation(value = "二维码下载", tags = "二维码接口")
    @ApiResponse(code = 200, message = "二维码下载")
    @RequestMapping(value = "{id}/download.json", method = RequestMethod.GET)
    public void download(
            @ApiParam("二维码ID")
            @PathVariable Integer id,
            HttpServletResponse response, HttpSession session) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        QrcodeDto qrcodeDto = qrcodeService.get(getWechatId(session), id);
        if (qrcodeDto != null) {
            String fileName = qrcodeDto.getQrcodeUrl().substring(
                    qrcodeDto.getQrcodeUrl().lastIndexOf("/") + 1);
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + fileName);
            InputStream inputStream = null;
            ServletOutputStream outputStream = null;
            try {
                URL url = new URL(qrcodeDto.getQrcodeUrl());
                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
                uc.connect();
                inputStream = uc.getInputStream();
                outputStream = response.getOutputStream();
                byte[] b = new byte[1024];
                int length;
                while ((length = inputStream.read(b)) > 0) {
                    outputStream.write(b, 0, length);
                }
                inputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

    @ApiOperation(value = "创建二维码", tags = "二维码接口")
    @ApiResponse(code = 200, message = "1-创建二维码成功")
    @RequestMapping(value = "new.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("qrCode:list")
    public JSONObject create(
            @ApiParam("QrcodeModel")
            @RequestBody(required = false) QrcodeModel qrcodeModel,
            HttpSession session) {
        try {
            Qrcode qrcode = qrcodeService.create(getWechatId(session),
                    getUser(session), qrcodeModel);
            QrcodeDto qrcodeDto = qrcodeService.get(qrcode.getWechatId(),
                    qrcode.getId());
            return representation(Message.QRCODE_CREATE_SUCCESS, qrcodeDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "删除二维码", tags = "二维码接口")
    @ApiResponse(code = 200, message = "1-删除二维码成功")
    @RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("qrCode:list")
    public JSONObject delete(
            @ApiParam("二维码ID")
            @PathVariable Integer id, HttpSession session) {
        try {
            qrcodeService.delete(getWechatId(session), id);
            return representation(Message.QRCODE_DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "获取二维码详情", tags = "二维码接口")
    @ApiResponse(code = 200, message = "1-获取二维码详情成功")
    @RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("qrCode:list")
    public JSONObject get(
            @ApiParam("二维码ID")
            @PathVariable Integer id, HttpSession session) {
        try {
            QrcodeDto qrcode = qrcodeService.get(getWechatId(), id);
            return representation(Message.QRCODE_GET_SUCCESS, qrcode);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "获取二维码列表", tags = "二维码接口")
    @ApiResponse(code = 200, message = "1-获取二维码列表成功")
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("qrCode:list")
    public JSONObject list(
            @ApiParam("QrcodeModel")
            @RequestBody(required = false) QrcodeModel qrcodeModel,
            HttpSession session) {
        try {
            Page<QrcodeDto> page = qrcodeService.list(getWechatId(session),
                    qrcodeModel);
            return representation(Message.QRCODE_LIST_SUCCESS,
                    page.getResult(), qrcodeModel.getPageSize(),
                    qrcodeModel.getPageNum(), page.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "搜索服务用的二维码列表", tags = "二维码接口")
    @GetMapping(value = "list_for_es.json")
    public BaseResponse<List<QrCodeES>> qrCodeListForES() {
        final List<Qrcode> qrcodes = qrcodeService.getAllByWechatId(getWechatId());
        final List<QrCodeES> result = qrcodes.stream()
                .map(QrCodeES::createdBy)
                .collect(Collectors.toList());
        return BaseResponse.builder()
                .resultCode(1)
                .data(result)
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class QrCodeES {
        private String title;
        private Integer key;

        public static QrCodeES createdBy(Qrcode qrcode) {
            return QrCodeES.builder()
                    .key(qrcode.getId())
                    .title(qrcode.getName())
                    .build();
        }
    }

    @ApiOperation(value = "更新二维码", tags = "二维码接口")
    @ApiResponse(code = 200, message = "1-更新二维码成功")
    @RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("qrCode:list")
    public JSONObject update(
            @ApiParam("二维码ID")
            @PathVariable Integer id,
            @ApiParam("QrcodeModel")
            @RequestBody(required = false) QrcodeModel qrcodeModel,
            HttpSession session) {
        try {
            qrcodeService.update(getWechatId(session), id, qrcodeModel);
            return representation(Message.QRCODE_UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "二维码初始化", tags = "二维码接口")
    @ApiResponse(code = 200, message = "1-二维码初始化成功")
    @RequestMapping(value = "init-qrcode.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject initQrcode(HttpSession session) {
        try {
            qrcodeService.init(getWechatId(session), getUser(session));
            return representation(Message.QRCODE_INIT_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

}
