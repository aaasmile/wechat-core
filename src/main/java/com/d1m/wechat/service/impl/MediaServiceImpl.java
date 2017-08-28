package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxMediaDownload;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Config;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.MediaService;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.MD5;
import com.d1m.wechat.util.Message;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * Created by D1M on 2017/5/16.
 */
@Service
public class MediaServiceImpl implements MediaService {
    private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Autowired
    private ConfigService configService;

    @Override
    public JSONObject getWxMedia(String appkey, String mediaId, String sign, String timestamp) {
        final Config config = configService.getConfig(null, "GET_WX_MEDIA", appkey);
        if (config == null) {
            throw new WechatException(Message.WX_MEDIA_INVALID_ACCESS);
        }
        String cfgValue = config.getCfgValue();
        JSONObject jsonConfig  = JSONObject.parseObject(cfgValue);

        //check enable
        if(!"1".equals(jsonConfig.get("enable"))) {
            throw new WechatException(Message.WX_MEDIA_INVALID_ACCESS);
        }

        //check timestamp
        Integer timeout = (Integer)jsonConfig.get("timeout");
        Date date = new Date();
        long requestTime = Long.parseLong(timestamp);
        if(Math.abs(date.getTime() - requestTime) / 1000 > timeout) {
            throw new WechatException(Message.WX_MEDIA_INVALID_ACCESS);
        }

        //check sign
        String secret = (String)jsonConfig.get("secret");
        String mdStr = MD5.MD5Encode(appkey + mediaId + secret + timestamp);
        if (!mdStr.equals(sign)) {
            throw new WechatException(Message.WX_MEDIA_INVALID_SIGN);
        }

        //WX media request
        WxMediaDownload media = WechatClientDelegate.getMedia(config.getWechatId(), mediaId);
        if(media.fail()) {
            throw new WechatException(Message.WX_MEDIA_INVALID_ID);
        }

        String fileParentDir = FileUploadConfigUtil.getInstance().getValue(config.getWechatId(), "upload_path") + File.separator + "api";
        File file = new File(fileParentDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        media.moveFileTo(file);

        JSONObject resultJson = new JSONObject();
        if(StringUtils.isNotBlank(media.getVideoUrl())) {
            log.debug("wx video request, media_id: {} ", mediaId);
            resultJson.put("media_url", media.getVideoUrl());
        } else {
            log.debug("wx image request, media_id: {} ", mediaId);
            resultJson.put("media_url", FileUploadConfigUtil.getInstance().getValue(config.getWechatId(), "upload_url_base") + File.separator + "api" + File.separator  + media.getFilename());
        }
        return resultJson;
    }


}
