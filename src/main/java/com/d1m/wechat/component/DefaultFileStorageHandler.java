package com.d1m.wechat.component;

import java.io.File;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUtils;

/**
 * DefaultFileStorageHandler
 *
 * 默认的文件存储实现, 保存文件到本地, 多节点需要挂载共享盘
 *
 * @author f0rb on 2017-04-13.
 */
@Component("fileStorageHandler")
public class DefaultFileStorageHandler implements FileStorageHandler {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultFileStorageHandler.class);

    @Override
    public String saveRemoteFile(Integer wechatId, String url) {
        String dateFormat = DateUtil.yyyyMMddHHmmss.format(new Date());
        String type = Constants.IMAGE + File.separator + Constants.NORMAL;
        File rootPath = FileUtils.getUploadPathRoot(wechatId, type);
        File dir = new File(rootPath, dateFormat.substring(0, 6));
        String suffix = FileUtils.getRemoteImageSuffix(url, Constants.JPG);
        String newFileName = FileUtils.generateNewFileName(DigestUtils.md5Hex(""), suffix);
        FileUtils.copyRemoteFile(url, dir.getAbsolutePath(), newFileName);
        String fileUrl = FileUploadConfig.createUploadUrl(type, dir.getName(), newFileName);
        log.info("文件访问链接: {}", fileUrl);
        return fileUrl;
    }

}
