package com.d1m.wechat.wechatclient;

import cn.d1m.wechat.client.core.*;
import cn.d1m.wechat.client.model.*;
import cn.d1m.wechat.client.model.common.WxFile;
import cn.d1m.wechat.client.model.common.WxHolder;
import cn.d1m.wechat.client.model.common.WxList;
import cn.d1m.wechat.client.model.common.WxPage;
import cn.d1m.wechat.client.model.request.WxCardExt;
import cn.d1m.wechat.client.model.request.WxVideoDesc;
import com.d1m.wechat.mapper.WechatMapper;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.util.AppContextUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.cn;

/**
 * WechatClientDelegate
 *
 * @author f0rb on 2016-12-14.
 * @since 1.0.4
 */
@SuppressWarnings("unused")
public class WechatClientDelegate {
	
	private static final Logger log = LoggerFactory.getLogger(WechatClientDelegate.class);

    private static final Map<Serializable, WechatClient> holder = new ConcurrentHashMap<Serializable, WechatClient>();
    private static final Map<Integer, AtomicInteger> wechatClientRefCnt = new ConcurrentHashMap<Integer, AtomicInteger>();

    private WechatClientDelegate() {
    }

    public static void main(String[] args) {
        WechatClientDelegateGenerator.generate("com.d1m.wechat", "WechatClientDelegate", true);
    }

    public static WechatClient create(Serializable key, String appid, String secret) {
        return create(key, appid, secret, RestAccessTokenProvider.class);
    }

    public synchronized static WechatClient create(
            Serializable key, String appid, String secret,
            Class<? extends AccessTokenProvider> providerClass
    ) {
        WechatClient wechatClient = WechatClientFactory.createInstance(appid, secret, providerClass);
        if (holder.containsKey(key) && !wechatClient.equals(holder.get(key))) {
            //key对应的appid/secret更新了, 需要销毁旧的WechatClient
            destroy(key);
        }
        holder.put(key, wechatClient);
        Integer hash = wechatClient.hashCode();
        if (!wechatClientRefCnt.containsKey(hash)) {
            wechatClientRefCnt.put(hash, new AtomicInteger(0));
        }
        int refCnt = wechatClientRefCnt.get(hash).incrementAndGet();
        log.info("WechatClient引用统计[{}]: {}",  hash, refCnt);
        return wechatClient;
    }

    public static void destroy(Serializable key) {
        WechatClient wechatClient = holder.remove(key);
        if (wechatClient == null) {
            log.info("WechatClient未创建: key={}",  key);
            return;
        }
        Integer hash = wechatClient.hashCode();
        int refCnt = wechatClientRefCnt.get(hash).decrementAndGet();
        log.info("WechatClient引用统计[{}]: {}",  hash, refCnt);
        if (refCnt <= 0) {
            wechatClientRefCnt.remove(hash);
            WechatClientFactory.destroyInstance(hash);
        }
    }

    public static WechatClient get(Serializable key) {
    	
        if (!holder.containsKey(key)) {
            synchronized (holder) {
                if (!holder.containsKey(key)) {
                    try {
                        WechatMapper wechatMapper = AppContextUtils.getBean("wechatMapper", WechatMapper.class);
                        Wechat wechat = wechatMapper.selectByPrimaryKey(key);
                        WechatClientDelegate.create(key, wechat.getAppid(), wechat.getAppscret());
                    } catch (Exception e) {
                        log.error("WechatClientDelegate设置失败:" + key, e);
                    }
                }
            }
        }
        Object client = holder.get(key);
        if(client != null) {
        	log.info("WechatClient>>" + client.getClass().getCanonicalName());
        } else {
        	log.error("WechatClient>>" + client);
        }
        return holder.get(key);
    }

    public static boolean containsKey(Serializable key) {
        return holder.containsKey(key);
    }

    public static WxList<WxUser> batchGetUserInfo(Serializable key, List<WxUser> user_list) {
        //处理每次最多拉取100条的问题
        int total = user_list.size();
        int step = 100;
        int toIndex = Math.min(step, total);
        WechatClient wechatClient = get(key);
        // 首次拉取, 如果出错或者不超过100条, 立即返回
        WxList<WxUser> wxList = wechatClient.batchGetUserInfo(user_list.subList(0, toIndex));
        if (wxList.fail() || toIndex == total) {
            return wxList;
        }

        //保存每次拉取的用户
        List<WxUser> all = new ArrayList<WxUser>(total);
        //添加第一次拉取的用户
        all.addAll(wxList.get());

        while (toIndex < total) {
            int fromIndex = toIndex;
            toIndex = Math.min(fromIndex + step, total);
            wxList = wechatClient.batchGetUserInfo(user_list.subList(fromIndex, toIndex));
            all.addAll(wxList.get());
        }
        return new WxList<WxUser>(all);
    }

    public static String getAppid(Serializable key) {
        return get(key).getAppid();
    }

    public static WxList<WxKf> getKfList(Serializable key) {
        return get(key).getKfList();
    }

    public static WxList<WxKfOnline> getKfOnlineList(Serializable key) {
        return get(key).getKfOnlineList();
    }

    public static WxAutoreply getCurrentAutoReplyInfo(Serializable key) {
        return get(key).getCurrentAutoReplyInfo();
    }

    public static WxMenuSelfInfo getCurrentSelfMenuInfo(Serializable key) {
        return get(key).getCurrentSelfMenuInfo();
    }

    public static WxList<String> getCallbackIP(Serializable key) {
        return get(key).getCallbackIP();
    }

    public static WxMaterial addMaterial(Serializable key, File media, WxVideoDesc description) {
        return get(key).addMaterial(media, description);
    }

    public static WxMaterial addMaterial(Serializable key, String type, File media) {
        return get(key).addMaterial(type, media);
    }

    public static WxMedia addNews(Serializable key, List<WxArticle> articles) {
        return get(key).addNews(articles);
    }

    public static WxPage<WxMaterial> batchGetMaterial(Serializable key, String type, int offset, int count) {
        return get(key).batchGetMaterial(type, offset, count);
    }

    public static WxResponse deleteMaterial(Serializable key, String media_id) {
        return get(key).deleteMaterial(media_id);
    }

    public static WxMaterialMixed getMaterial(Serializable key, String media_id) {
        return get(key).getMaterial(media_id);
    }

    public static WxMaterialCount getMaterialCount(Serializable key) {
        return get(key).getMaterialCount();
    }

    public static WxResponse updateNews(Serializable key, String media_id, String index, WxArticle articles) {
        return get(key).updateNews(media_id, index, articles);
    }

    public static WxMediaDownload getMedia(Serializable key, String media_id) {
        return get(key).getMedia(media_id);
    }

    public static WxMedia uploadMedia(Serializable key, String type, File media) {
        return get(key).uploadMedia(type, media);
    }

    public static WxHolder<String> uploadImg(Serializable key, File media) {
        return get(key).uploadImg(media);
    }

    public static WxMedia uploadNews(Serializable key, List<WxArticle> articles) {
        return get(key).uploadNews(articles);
    }

    public static WxMedia uploadVideo(Serializable key, String media_id, String title, String description) {
        return get(key).uploadVideo(media_id, title, description);
    }

    public static WxMenuGroup addConditional(Serializable key, List<WxMenu> button, WxMenuMatchrule matchrule) {
        return get(key).addConditional(button, matchrule);
    }

    public static WxResponse createMenu(Serializable key, List<WxMenu> button) {
        return get(key).createMenu(button);
    }

    public static WxResponse deleteConditionalMenu(Serializable key, String menuid) {
        return get(key).deleteConditionalMenu(menuid);
    }

    public static WxResponse deleteMenu(Serializable key) {
        return get(key).deleteMenu();
    }

    public static WxMenuGroupHolder getMenu(Serializable key) {
        return get(key).getMenu();
    }

    public static WxMenuGroup tryMatchMenu(Serializable key, String user_id) {
        return get(key).tryMatchMenu(user_id);
    }

    public static WxMessage sendCustomMessage(Serializable key, String touser, String msgtype, Object media_id) {
        return get(key).sendCustomMessage(touser, msgtype, media_id);
    }

    public static WxMessage sendCustomText(Serializable key, String touser, String text) {
        return get(key).sendCustomMessage(touser, "text", text);
    }

    public static WxMessage sendCustomMessage(Serializable key, String kf_account, String touser, String msgtype, Object media_id) {
        return get(key).sendCustomMessage(kf_account, touser, msgtype, media_id);
    }

    public static WxResponse deleteMessage(Serializable key, Integer msg_id) {
        return get(key).deleteMessage(msg_id);
    }

    public static WxMessage getMessage(Serializable key, long msg_id) {
        return get(key).getMessage(msg_id);
    }

    public static WxMessage previewCard(Serializable key, List<String> touser, String card_id, WxCardExt card_ext) {
        return get(key).previewCard(touser, card_id, card_ext);
    }

    public static WxMessage previewMessage(Serializable key, String touser, String msgtype, String media_id) {
        return get(key).previewMessage(touser, msgtype, media_id);
    }

    public static WxMessage previewText(Serializable key, String touser, String content) {
        return get(key).previewText(touser, content);
    }

    public static WxMessage sendCard(Serializable key, List<String> touser, String card_id) {
        return get(key).sendCard(touser, card_id);
    }

    public static WxMessage sendImage(Serializable key, List<String> touser, String media_id) {
        return get(key).sendImage(touser, media_id);
    }

    public static WxMessage sendMessage(Serializable key, List<String> touser, String msgtype, String media_id) {
        return get(key).sendMessage(touser, msgtype, media_id);
    }

    public static WxMessage sendNews(Serializable key, List<String> touser, String media_id) {
        return get(key).sendNews(touser, media_id);
    }

    public static WxMessage sendText(Serializable key, List<String> touser, String content) {
        return get(key).sendText(touser, content);
    }

    public static WxMessage sendVideo(Serializable key, List<String> touser, String media_id) {
        return get(key).sendVideo(touser, media_id);
    }

    public static WxMessage sendVoice(Serializable key, List<String> touser, String media_id) {
        return get(key).sendVoice(touser, media_id);
    }

    public static WxMessage sendByTagId(Serializable key, int tag_id, String msgtype, String media_id) {
        return get(key).sendByTagId(tag_id, msgtype, media_id);
    }

    public static WxMessage sendToAll(Serializable key, String msgtype, String media_id) {
        return get(key).sendToAll(msgtype, media_id);
    }

    public static WxMessage sendTemplate(Serializable key, String touser, String template_id, String url, Object data) {
        return get(key).sendTemplate(touser, template_id, url, data);
    }

    public static WxResponse addPOI(Serializable key, WxBusiness base_info) {
        return get(key).addPOI(base_info);
    }

    public static WxResponse deletePOI(Serializable key, String poi_id) {
        return get(key).deletePOI(poi_id);
    }

    public static WxHolder<WxBusiness> getPOI(Serializable key, String poi_id) {
        return get(key).getPOI(poi_id);
    }

    public static WxPage<WxBusiness> getPOIList(Serializable key, int begin, int limit) {
        return get(key).getPOIList(begin, limit);
    }

    public static WxList<String> getWxCategory(Serializable key) {
        return get(key).getWxCategory();
    }

    public static WxResponse updatePOI(Serializable key, WxBusiness base_info) {
        return get(key).updatePOI(base_info);
    }

    public static WxQRCode createQRCode(Serializable key, Integer scene_id) {
        return get(key).createQRCode(scene_id);
    }

    public static WxQRCode createQRCode(Serializable key, String scene_str) {
        return get(key).createQRCode(scene_str);
    }

    public static WxQRCode createQRCode(Serializable key, int expire_seconds, Integer scene_id) {
        return get(key).createQRCode(expire_seconds, scene_id);
    }
    public static WxQRCode createQRCode(Serializable key, int expire_seconds, String scene_str) {
        return get(key).createQRCode(expire_seconds, scene_str);
    }

    public static WxHolder<String> shortURL(Serializable key, String long_url) {
        return get(key).shortURL(long_url);
    }

    public static WxHolder<WxTag> createTag(Serializable key, String name) {
        return get(key).createTag(name);
    }

    public static WxResponse deleteTag(Serializable key, Integer id) {
        return get(key).deleteTag(id);
    }

    public static WxList<WxTag> getTags(Serializable key) {
        return get(key).getTags();
    }

    public static WxList<Integer> getTagidList(Serializable key, String openid) {
        return get(key).getTagidList(openid);
    }

    public static WxResponse batchTagging(Serializable key, List<String> openid_list, Integer tagid) {
        return get(key).batchTagging(openid_list, tagid);
    }

    public static WxResponse batchUnTagging(Serializable key, List<String> openid_list, Integer tagid) {
        return get(key).batchUnTagging(openid_list, tagid);
    }

    public static WxResponse updateTag(Serializable key, WxTag tag) {
        return get(key).updateTag(tag);
    }

    public static WxHolder<String> getTemplateId(Serializable key, String template_id_short) {
        return get(key).getTemplateId(template_id_short);
    }

    public static WxResponse setTemplateIndustry(Serializable key, String industry_id1, String industry_id2) {
        return get(key).setTemplateIndustry(industry_id1, industry_id2);
    }

    public static WxResponse deleteTemplate(Serializable key, String template_id) {
        return get(key).deleteTemplate(template_id);
    }

    public static WxList<WxTemplate> getTemplateList(Serializable key) {
        return get(key).getTemplateList();
    }

    public static WxTemplateIndustry getTemplateIndustry(Serializable key) {
        return get(key).getTemplateIndustry();
    }

    public static String getCardApiTicket(Serializable key) {
        return get(key).getCardApiTicket();
    }

    public static String getJsApiTicket(Serializable key) {
        return get(key).getJsApiTicket();
    }

    public static String getAccessToken(Serializable key) {
        return get(key).getAccessToken();
    }

    public static WxOpenidPage getOpenidPage(Serializable key, String next_openid) {
        return get(key).getOpenidPage(next_openid);
    }

    public static WxUser getUser(Serializable key, String openid) {
        return get(key).getUser(openid);
    }

    public static WxUser getUser(Serializable key, String openid, String lang) {
        return get(key).getUser(openid, lang);
    }

    public static WxResponse updateUserRemark(Serializable key, String openid, String remark) {
        return get(key).updateUserRemark(openid, remark);
    }

    public static WxOpenidPage getOpenidByTag(Serializable key, Integer tagid, String next_openid) {
        return get(key).getOpenidByTag(tagid, next_openid);
    }

    public static WxResponse addKf(Serializable key, String kf_account, String nickname, String password) {
        return get(key).addKf(kf_account, nickname, password);
    }

    public static WxResponse deleteKf(Serializable key, String kf_account) {
        return get(key).deleteKf(kf_account);
    }

    public static WxResponse deleteKf(Serializable key, String kf_account, String nickname, String password) {
        return get(key).deleteKf(kf_account, nickname, password);
    }

    public static WxResponse inviteWorker(Serializable key, String kf_account, String invite_wx) {
        return get(key).inviteWorker(kf_account, invite_wx);
    }

    public static WxResponse updateKf(Serializable key, String kf_account, String nickname, String password) {
        return get(key).updateKf(kf_account, nickname, password);
    }

    public static WxResponse uploadKfHeadImg(Serializable key, File media) {
        return get(key).uploadKfHeadImg(media);
    }

    public static WxList<WxArticleData> getArticleSummary(Serializable key, Date begin_date, Date end_date) {
        return get(key).getArticleSummary(begin_date, end_date);
    }

    public static WxList<WxArticleData> getArticleTotal(Serializable key, Date begin_date, Date end_date) {
        return get(key).getArticleTotal(begin_date, end_date);
    }

    public static WxList<WxUserCumulate> getUserCumulate(Serializable key, Date begin_date, Date end_date) {
        return get(key).getUserCumulate(begin_date, end_date);
    }

    public static WxList<WxArticleData> getUserRead(Serializable key, Date begin_date, Date end_date) {
        return get(key).getUserRead(begin_date, end_date);
    }

    public static WxList<WxArticleData> getUserReadHour(Serializable key, Date begin_date, Date end_date) {
        return get(key).getUserReadHour(begin_date, end_date);
    }

    public static WxList<WxArticleData> getUserShare(Serializable key, Date begin_date, Date end_date) {
        return get(key).getUserShare(begin_date, end_date);
    }

    public static WxList<WxArticleData> getUserShareHour(Serializable key, Date begin_date, Date end_date) {
        return get(key).getUserShareHour(begin_date, end_date);
    }

    public static WxList<WxUserSummary> getUserSummary(Serializable key, Date begin_date, Date end_date) {
        return get(key).getUserSummary(begin_date, end_date);
    }

    public static WxMedia uploadVideoFile(Serializable key, String media_id, String title, String description) {
        return get(key).uploadVideoFile(media_id, title, description);
    }

    public static WxFile showQRCode(Serializable key, String ticket) {
        return get(key).showQRCode(ticket);
    }

}