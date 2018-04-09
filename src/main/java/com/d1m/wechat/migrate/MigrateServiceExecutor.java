package com.d1m.wechat.migrate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.d1m.common.ds.TenantContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxArticle;
import cn.d1m.wechat.client.model.WxMaterial;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.model.enums.MaterialType;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.HtmlUtils;

import static com.d1m.wechat.util.FileUtils.*;

/**
 * 微信数据同步的异步执行类
 *
 * @author f0rb on 2016-12-02.
 */
@Component
public class MigrateServiceExecutor {
	
	private static final Logger log = LoggerFactory.getLogger(MigrateServiceExecutor.class);

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private MaterialImageTextDetailMapper materialImageTextDetailMapper;

    private Future<List<WxMaterial>> executeNews(List<WxMaterial> wxMaterials, Integer userId, Integer wechatId, Boolean canUpdate) {
        Date now = new Date();
        List<WxMaterial> ignored = new LinkedList<>();
        List<MaterialImageTextDetail> insertList = new LinkedList<>();
        List<MaterialImageTextDetail> updateList = new LinkedList<>();

        for (WxMaterial wxMaterial : wxMaterials) {
            if (log.isDebugEnabled()) {
                log.debug("处理素材:\n{}", JSON.toJSONString(wxMaterial, true));
            }

            Material material = new Material();
            material.setMediaId(wxMaterial.getMediaId());
            //根据media_id检查系统内是否已关联material
            Material existedMaterial = materialMapper.selectOne(material);

            if (existedMaterial != null) {//material已存在
                material = existedMaterial;
            } else {
                material.setModifyAt(wxMaterial.getUpdateTime());
                material.setLastPushAt(wxMaterial.getUpdateTime());
                material.setCreatedAt(now);
                material.setMaterialType(MaterialType.IMAGE_TEXT.getValue());
                material.setStatus(MaterialStatus.INUSED.getValue());
                material.setCreatorId(userId);
                material.setWechatId(wechatId);
                materialMapper.insert(material);
            }

            List<WxArticle> ignoredWxArticleList = new LinkedList<>();
            processArticle(wxMaterial, material, insertList, updateList, ignoredWxArticleList, wechatId, userId, canUpdate);

            if (!ignoredWxArticleList.isEmpty()) {
                // 这条微信的素材有未更新的图文
                ignored.add(wxMaterial);
            }
        }

        if (!insertList.isEmpty()) {
            log.info("新增{}条图文素材", insertList.size());
            try {
                int count = materialImageTextDetailMapper.insertList(insertList);
                log.info("插入{}条到数据库", count);
            } catch (Exception e) {
                log.error("插入图文失败:\n" + JSON.toJSONString(insertList), e);
            }
        } else {
            log.info("没有需要新增的图文素材");
        }

        if (!updateList.isEmpty()) {
            log.info("更新{}条图文素材", updateList.size());
            int count = 0;
            for (MaterialImageTextDetail imageTextDetail : updateList) {
                try {
                    materialImageTextDetailMapper.updateByPrimaryKey(imageTextDetail);
                    count++;
                } catch (Exception e) {
                    log.error("更新图文失败:\n" + JSON.toJSONString(imageTextDetail), e);
                }
            }
            log.info("更新{}条到数据库", count);
        } else {
            log.info("没有需要更新的图文素材");
        }
        return new AsyncResult<>(ignored);
    }

    private void preProcessThumb(WxMaterial wxMaterial, int wechatId, int userId) {
        List<WxArticle> wxArticleList = wxMaterial.getContent().get();

        Date now = new Date();
        List<Material> insertList = new LinkedList<>();//待批量插入的Material列表

        //根目录
        String type = Constants.IMAGE + File.separator + Constants.MATERIAL;
        File root = getUploadPathRoot(wechatId, type);
        File dir = new File(root, DateUtil.yyyyMMddHHmmss.format(new Date()).substring(0, 6));
        dir.mkdirs();

        Set<String> thumbSet = new HashSet<>();//防止重复下载同一个封面图片
        for (WxArticle wxArticle : wxArticleList) {
            String mediaId = wxArticle.getThumbMediaId();
            if (thumbSet.contains(mediaId)) {
                continue;
            }
            thumbSet.add(mediaId);
            // 封面图片的素材
            Material queryMaterial = new Material();
            queryMaterial.setMediaId(mediaId);
            queryMaterial.setWechatId(wechatId);
            queryMaterial.setStatus(Constants.Status.VALID);
            List<Material> coverList = materialMapper.select(queryMaterial);
            if (!coverList.isEmpty()) {
                continue;
            }
                log.info("图片素材不存在, 添加素材: {}", mediaId);
                Date date = new Date();
                Material material = new Material();
                material.setName("图文封面");
                material.setModifyAt(date);
                material.setLastPushAt(date);

                material.setWxPicUrl(wxArticle.getThumbUrl());
                material.setMediaId(wxArticle.getThumbMediaId());
                material.setMaterialType(MaterialType.IMAGE.getValue());

                material.setStatus(MaterialStatus.INUSED.getValue());
                material.setWechatId(wechatId);
                material.setCreatorId(userId);
                material.setCreatedAt(now);

                try {
                    String filename = "thumb_" + wxArticle.getThumbMediaId();
                    File file = new File(dir, filename);
                    //下载微信图片
                    FileUtils.copyURLToFile(new URL(wxArticle.getThumbUrl()), file);

                    //下载成功, 替换链接
                    String newUrl = FileUploadConfigUtil.getInstance().getValue(wechatId, "upload_url_base")
                            + "/" + Constants.IMAGE + "/" + Constants.MATERIAL + "/"
                            + dir.getName() + "/" + filename;
                    material.setPicUrl(newUrl);
                } catch (IOException e) {
                    log.error("封面图片下载失败!", e);
                }
                insertList.add(material);
        }

        if (!insertList.isEmpty()) {
            log.info("新增{}条封面图片", insertList.size());
            int count = materialMapper.insertList(insertList);
            log.info("插入{}条到数据库", count);
        } else {
            log.info("没有需要新增的封面图片");
        }
    }

    private void transferWxArticle(WxArticle wxArticle, MaterialImageTextDetail detail,
                      int materialId, int wechatId, int sequence) {

        detail.setAuthor(wxArticle.getAuthor());
        //TODO 对图文内容中的图片地址等做相关支持处理。
        processArticlePicture(wechatId, wxArticle);
        detail.setContent(wxArticle.getContent());
        detail.setContentSourceChecked(true);
        detail.setContentSourceUrl(wxArticle.getContentSourceUrl());
        detail.setShowCover("1".equals(wxArticle.getShowCoverPic()));

        // 封面图片的素材
        Material queryMaterial = new Material();
        queryMaterial.setMediaId(wxArticle.getThumbMediaId());
        queryMaterial.setWechatId(wechatId);
        List<Material> coverList = materialMapper.select(queryMaterial);
        if (!coverList.isEmpty()) {
            detail.setMaterialCoverId(coverList.get(0).getId());
            if (coverList.size() > 1) {
                log.error("封面图片[{}]出现重复!", wxArticle.getThumbMediaId());
            }
        } else {
            log.error("封面图片[{}]预处理失败!", wxArticle.getThumbMediaId());
        }
        String summary = wxArticle.getDigest();
        if (StringUtils.isBlank(summary)) {
            String delHTMLTagContent = HtmlUtils.delHTMLTag(wxArticle.getContent());
            if (delHTMLTagContent.length() > 54) {
                delHTMLTagContent = delHTMLTagContent.substring(0, 54);
            }
            detail.setSummary(delHTMLTagContent);
        } else {
            detail.setSummary(summary);
        }
        detail.setTitle(wxArticle.getTitle());
        detail.setWechatId(wechatId);
        detail.setMaterialId(materialId);
        detail.setSequence(sequence);
        detail.setStatus(MaterialStatus.INUSED.getValue());
    }

    private void processArticlePicture(Integer wechatId, WxArticle wxArticle) {
        String content = wxArticle.getContent();
        String type = Constants.IMAGE + File.separator + Constants.MATERIAL;
        File root = getUploadPathRoot(wechatId, type);
        File dir = new File(root, DateUtil.yyyyMMddHHmmss.format(new Date()).substring(0, 6));
        dir.mkdirs();
        //String newContent = wxArticle.getContent();
        // 正则挑出微信图文里的图文链接
        //src="http://mmbiz.qpic.cn/mmbiz_jpg/icRCzVibWeb1KEw1eFQqX0IY80kg0zWN2MRXI3r0ibhBicnCic9TMDVNalmvsX8RuOic4DTPhDhBH9jzWdyCUDnKTw9Q/0"
        Pattern wxPicPattern = Pattern.compile(" (data-)?src=\"(http://mmbiz\\.qpic\\.cn/mmbiz_(\\w+)/.+?)\"");
        //List<String> wxUrlList = new LinkedList<>();
        Matcher matcher = wxPicPattern.matcher(content);
        while (matcher.find()) {
            String toRe = matcher.group();
            String wxUrl = matcher.group(2);
            String fileExt = matcher.group(3);
            //wxUrlList.add(wxUrl);
            String newFileName = generateNewFileName("" + System.currentTimeMillis(), fileExt);
            File file = new File(dir, newFileName);
            try {
                //下载微信图片
                FileUtils.copyURLToFile(new URL(wxUrl), file);

                //下载成功, 替换链接
                String newUrl = FileUploadConfigUtil.getInstance().getValue(wechatId, "upload_url_base")
                        + "/" + Constants.IMAGE + "/" + Constants.MATERIAL + "/"
                        + dir.getName() + "/" + newFileName;
                content = content.replace(toRe,  " src=\"" + newUrl + "\" data-wx-src=\"" + wxUrl + "\"");
            } catch (IOException e) {
                log.error("微信图片下载失败!", e);
            }
        }
        wxArticle.setContent(content);
    }

    /**
     * 处理图文素材的多篇文章
     */
    private void processArticle(WxMaterial wxMaterial, Material material,
                                List<MaterialImageTextDetail> insertList,
                                List<MaterialImageTextDetail> updateList,
                                List<WxArticle> ignoredList,
                                int wechatId, int userId, boolean canUpdate) {

        // 预处理图文的封面图片素材
        preProcessThumb(wxMaterial, wechatId, userId);

        List<WxArticle> wxArticleList = wxMaterial.getContent().get();

        int sequence = 1;
        for (WxArticle wxArticle : wxArticleList) {
            MaterialImageTextDetail origin = null;
                // 根据原文链接查询文章
                MaterialImageTextDetail query = new MaterialImageTextDetail();
            if (StringUtils.isNotBlank(wxArticle.getContentSourceUrl())) {
                query.setContentSourceUrl(wxArticle.getContentSourceUrl());
            } else {
                //query.setMaterialCoverId(wxArticle.getThumb_media_id());
                query.setTitle(wxArticle.getTitle());
                query.setAuthor(wxArticle.getAuthor());
                query.setSummary(wxArticle.getDigest());
            }
                query.setMaterialId(material.getId());
                List<MaterialImageTextDetail> list = materialImageTextDetailMapper.select(query);
                if (!list.isEmpty()) {
                    origin = list.get(0);
                    if (list.size() > 1) {
                        log.warn("根据content_source_url[{}], wechatId[{}]查询到{}篇文章",
                                 wxArticle.getContentSourceUrl(), wechatId, list.size());
                    }
                }

            if (origin == null) {
                //添加文章
                MaterialImageTextDetail detail = new MaterialImageTextDetail();
                transferWxArticle(wxArticle, detail, material.getId(), wechatId, sequence++);
                insertList.add(detail);
            } else {//存在content_source_url一样的图文
                if (canUpdate) {
                    //更新文章
                    transferWxArticle(wxArticle, origin, material.getId(), wechatId, sequence++);
                    updateList.add(origin);
                } else {
                    log.info("文章被忽略: {}", JSON.toJSONString(wxArticle));
                    ignoredList.add(wxArticle);
                }
            }
        }
    }

    @Async("callerRunsExecutor")
    public Future<List<WxMaterial>> executeMaterial(String tenant, List<WxMaterial> wxMaterials, String materialType, Integer userId, Integer wechatId, Boolean canUpdate) {
        TenantContext.setCurrentTenant(tenant);
        log.info("处理素材:{}[{}条]", materialType, wxMaterials.size());

        if ("news".equals(materialType)) {//图文素材单独处理
            return executeNews(wxMaterials, userId, wechatId, canUpdate);
        }

        Date now = new Date();
        List<WxMaterial> ignored = new LinkedList<>();
        List<Material> insertList = new LinkedList<>();

        for (WxMaterial wxMaterial : wxMaterials) {
            if (log.isDebugEnabled()) {
                log.debug("处理素材:\n{}", JSON.toJSONString(wxMaterial, true));
            }

            Material material = new Material();
            material.setMediaId(wxMaterial.getMediaId());
            //根据media_id检查系统内是否已存在对应的material
            Material existedMaterial = materialMapper.selectOne(material);
            if (existedMaterial != null) {//material已存在
                if (!canUpdate) { //不允许更新，则只做新增操作
                    ignored.add(wxMaterial);
                    continue;
                }
                existedMaterial.setName(wxMaterial.getName());
                existedMaterial.setModifyAt(wxMaterial.getUpdateTime());
                existedMaterial.setLastPushAt(wxMaterial.getUpdateTime());
                setMaterialTypeAndUrl(existedMaterial, materialType, wxMaterial.getUrl());

                existedMaterial.setStatus(MaterialStatus.INUSED.getValue());
                materialMapper.updateByPrimaryKey(existedMaterial);
            } else {
                if (StringUtils.isNotBlank(wxMaterial.getUrl())) {//下载素材文件到本地
                    String picUrl = downloadFile(wechatId, materialType, wxMaterial.getUrl());
                    material.setPicUrl(picUrl);
                }

                material.setName(wxMaterial.getName());
                material.setModifyAt(wxMaterial.getUpdateTime());
                material.setLastPushAt(wxMaterial.getUpdateTime());
                setMaterialTypeAndUrl(material, materialType, wxMaterial.getUrl());

                material.setStatus(MaterialStatus.INUSED.getValue());
                material.setCreatorId(userId);
                material.setCreatedAt(now);
                material.setWechatId(wechatId);
                insertList.add(material);
            }
        }

        if (!insertList.isEmpty()) {
            log.info("新增{}条{}", insertList.size(), materialType);
            int count = materialMapper.insertList(insertList);
            log.info("插入{}条到数据库", count);
        } else {
            log.info("没有需要新增的{}", materialType);
        }

        return new AsyncResult<>(ignored);
    }

    private String downloadFile(Integer wechatId, String type, String wxUrl) {
        File root = getUploadPathRoot(wechatId, type + File.separator + Constants.MATERIAL);
        File dir = new File(root, DateUtil.yyyyMMddHHmmss.format(new Date()).substring(0, 6));
        dir.mkdirs();
        String newFileName = generateWxFileName(wxUrl);
        File file = new File(dir, newFileName);
        String newUrl = FileUploadConfigUtil.getInstance().getValue(wechatId, "upload_url_base")
                + "/" + type + "/" + Constants.MATERIAL + "/"
                + dir.getName() + "/" + newFileName;
        try {
            //下载微信图片
            FileUtils.copyURLToFile(new URL(wxUrl), file);
        } catch (IOException e) {
            log.error("微信图片下载失败!", e);
        }

        return newUrl;
    }

    /**
     * 设置素材的类型和url
     */
    private void setMaterialTypeAndUrl(Material material, String materialType, String url) {
        switch (materialType) {
            case "image":
                material.setWxPicUrl(url);
                material.setMaterialType(MaterialType.IMAGE.getValue());
                break;
            case "voice":
                material.setVoiceUrl(url);
                material.setMaterialType(MaterialType.VOICE.getValue());
                break;
            case "video":
                material.setVideoUrl(url);
                material.setMaterialType(MaterialType.VIDEO.getValue());
                break;
            default:
                log.error("Unknown material type: {}", materialType);
        }
    }
}