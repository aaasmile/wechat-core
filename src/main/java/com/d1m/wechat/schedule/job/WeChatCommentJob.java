package com.d1m.wechat.schedule.job;

import cn.d1m.wechat.client.model.WxTemplate;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.ElasticsearchConsumer;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.HttpUtils;
import com.d1m.wechat.util.ParamUtil;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@JobHander(value="weChatCommentsJob")
@Component
public class WeChatCommentJob extends BaseJobHandler {
    @Resource
    private MassConversationResultService massConversationResultService;

    @Resource
    private ConversationService conversationService;

    @Resource
    private MemberService memberService;

    private final static String COMMENT_API="https://api.weixin.qq.com/cgi-bin/comment/list?access_token=";

    @Resource
    public RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        XxlJobLogger.log("WeChatCommentJob===========>>");
        List<Conversation> conversationList=new ArrayList<>();
        if(strings!=null){
            // 参数1为微信ID
            Integer wechatId = ParamUtil.getInt(strings[0], null);
            XxlJobLogger.log("wechatId===========>>"+wechatId);
            String tokenUrl=System.getProperty("accesstoken.url") == null ?"http://qa.wechat.d1m.cn/api/wechat/access-token/wxb709c8392fe2b357/412e024cfbd344ff630bf496138f5cd8":System.getProperty("accesstoken.url");
            String tokenDataString = HttpUtils.sendGet(tokenUrl);
            if(tokenDataString==null){
                XxlJobLogger.log("ERROR===========>>tokenData is null");
                return ReturnT.FAIL;
            }
            JSONObject tokenData = JSONObject.parseObject(tokenDataString);
            List<String> list = massConversationResultService.selectMsgDataId(wechatId);
            XxlJobLogger.log("msgData===========>>"+list);
            for (String msgDataId : list) {
                if(msgDataId==null)
                    continue;
                JSONObject sendData=new JSONObject();
                sendData.put("msg_data_id",msgDataId);
                sendData.put("begin",0);
                sendData.put("count",50);
                sendData.put("type",0);
                String commenData = HttpUtils.doPost(COMMENT_API + tokenData.get("data"), sendData);
                JSONObject commentDataJson =JSONObject.parseObject(commenData);
                if (commentDataJson == null) continue;
                if(commentDataJson.get("errmsg").toString().equalsIgnoreCase("ok")){
                    saveJsonObject(wechatId, msgDataId, commentDataJson,conversationList);
                    int total = Integer.parseInt(commentDataJson.get("total").toString());
                    if(total>50){
                        int count=total%50==0?total/50:total/50+1;
                        for(int i=1;i<count;i++){
                            sendData.put("begin",0);
                            sendData.put("count",50);
                            String commenDataMore = HttpUtils.doPost(COMMENT_API + tokenData.get("data"), sendData);
                            JSONObject commentDataJsonMore =JSONObject.parseObject(commenDataMore);
                            saveJsonObject(wechatId,msgDataId,commentDataJsonMore,conversationList);
                        }
                    }
                }else{
                    continue;
                }
            }
            //v4.6.1
            pushEs(conversationList);
        }
        return ReturnT.SUCCESS;
    }

    private JSONObject saveJsonObject(Integer wechatId, String msgDataId, JSONObject commentDataJson,List<Conversation> conversationList) {
        XxlJobLogger.log("save===========>>"+msgDataId);
        JSONArray comments = (JSONArray) commentDataJson.get("comment");
        for (Object commentObject : comments) {
            JSONObject comment = JSONObject.parseObject(commentObject.toString());
            Member member = memberService.getMemberByOpenId(wechatId, comment.get("openid").toString());
            if(member==null){
                continue;
            }
            Conversation conversation=new Conversation();
            conversation.setEvent(Event.USER_COMMENT.getValue());
            conversation.setEventName(Event.USER_COMMENT.getName());
            conversation.setMemberId(member.getId());
            conversation.setMsgId(msgDataId);
            conversation.setDescription(comment.get("user_comment_id").toString());
            List<ConversationDto> conversationDtos = conversationService.searchComment(wechatId, conversation);
            if(conversationDtos.size()>0){
                continue;
            }
            conversation.setOpenId(member.getOpenId());
            conversation.setUnionId(member.getUnionId());
            conversation.setCreatedAt(DateUtil.parse(DateUtil.timeStamp2Date(comment.get("create_time").toString(),null)));
            List<Integer> conversationIds = massConversationResultService.getConversationId(wechatId, msgDataId);
            if(conversationIds.size()==0)
                continue;
            Conversation conversationData = conversationService.selectByKey(conversationIds.get(0));
            if(conversationData==null){
                continue;
            }
            JSONArray contents= (JSONArray) JSONArray.parse(conversationData.getContent());
            for (Object content : contents) {
                JSONObject contentObject = JSONObject.parseObject(content.toString());
                org.json.JSONArray titles = new org.json.JSONArray();
                titles.put(contentObject.get("title"));
                titles.put(contentObject.get("id"));
                titles.put(comment.get("content"));
                conversation.setTitle(titles.toString());
            }
            conversation.setMsgType((byte)10);
            conversation.setWechatId(wechatId);
            conversation.setStatus((byte)0);
            conversation.setDirection(true);
            conversation.setContent(comment.get("content").toString());
            XxlJobLogger.log("conversation===========>>"+conversation);
            conversationService.save(conversation);
            conversationList.add(conversation);
        }
        return commentDataJson;
    }

    private void pushEs(List<Conversation> conversationListonversation) {
        try {
            String memberStr = objectMapper.writeValueAsString(conversationListonversation);
            JsonParser jsonParser = new JsonParser();
            JsonArray array = jsonParser.parse(memberStr).getAsJsonArray();
            rabbitTemplate.convertAndSend(ElasticsearchConsumer.ELAS_EXCHANGE, ElasticsearchConsumer.ELAS_QUEUE_COMMENT,array.toString());
        } catch (Exception e) {
            XxlJobLogger.log(e.getMessage());
        }
    }

}
