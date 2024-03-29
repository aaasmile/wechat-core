package com.d1m.wechat.controller.api;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.mapper.ConversationMapper;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.MemberMemberTagMapper;
import com.d1m.wechat.mapper.MemberScanQrcodeMapper;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.MemberScanQrcode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.d1m.wechat.dto.ConversationsDto;

/***
 * V4.6.1升级 es 数据初始化
 * */
@RestController
@RequestMapping("/upgradev461")
public class UpgradeV461 extends BaseController {

  private Logger log = LoggerFactory.getLogger(UpgradeV461.class);
  @Autowired
  MemberMapper memberMapper;
  @Autowired
  ConversationMapper conversationMapper;
  @Autowired
  MemberMemberTagMapper memberMemberTagMapper;
  @Autowired
  MemberScanQrcodeMapper memberScanQrcodeMapper;

  @RequestMapping("transferMember")
  public String transferMember() {
    log.info("transferMember...start");
    Integer wechatId = this.getWechatId();
    int count = loadMember(wechatId);
    return "member...load...." + count;
  }
  @RequestMapping("transferConversation")
  public String loadConversation() {
    log.info("loadConversation...start");
    Integer wechatId = this.getWechatId();
    int count = loadConversation(wechatId);
    return "conversation...load...." + count;
  }
  @RequestMapping("transferMemberMemberTag")
  public String loadMemberMemberTag() {
    log.info("loadMemberMemberTag...start");
    Integer wechatId = this.getWechatId();
    int count = loadMemberMemberTag(wechatId);
    return "loadMemberMemberTag...load...." + count;
  }
  @RequestMapping("transferMemberScanQrcode")
  public String loadMemberScanQrcode() {
    log.info("loadMemberScanQrcode...start");
    Integer wechatId = this.getWechatId();
    int count = loadMemberScanQrcode(wechatId);
    return "loadMemberScanQrcode...load...." + count;
  }

  @Autowired
  public RabbitTemplate rabbitTemplate;


  public int loadMember(Integer wechatId) {
    int totalCount = memberMapper.selectCount(null);
    fetchMember(wechatId);
    return totalCount;
  }

  public int loadConversation(Integer wechatId) {
    int totalCount = conversationMapper.selectCount(null);
    fetchConversation(wechatId);
    return totalCount;
  }

  public int loadMemberMemberTag(Integer wechatId) {
    int totalCount = memberMemberTagMapper.selectCount(null);
    fetchMemberMemberTag(wechatId);
    return totalCount;
  }

  public int loadMemberScanQrcode(Integer wechatId) {
    int totalCount = memberScanQrcodeMapper.selectCount(null);
    fetchMemberScanQrcode(wechatId);
    return totalCount;
  }

  private void fetchMember(Integer wechatId) {
    int pageNum = 1;
    int pageSize = 1000;
//    while (true) {
      log.info("current...{},...{}", pageNum, pageSize);
      PageHelper.startPage(pageNum, pageSize, true);
      List<Member> members = memberMapper.selectAll();
      if(members == null || members.isEmpty()) {
        log.error("members.isEmpty");
        return;
//        break;
      }
      JsonArray jsonArray = new JsonArray();
      JsonParser jsonParser = new JsonParser();
      ObjectMapper objectMapper = new ObjectMapper();
      members.stream().forEach(member -> {
        try {
          String memberStr = objectMapper.writeValueAsString(member);
          JsonObject jsonObject = jsonParser.parse(memberStr).getAsJsonObject();
          jsonArray.add(jsonObject);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      });
      log.info("jsonArray..send..." + jsonArray.size());
      rabbitTemplate.convertAndSend("elas.exchange", "elas.queue.memberAdd", jsonArray.toString());
      log.info("jsonArray..end send..." + jsonArray.size());
      pageNum = pageNum + 1;
//    }
  }

  private void fetchConversation(Integer wechatId) {
    int pageNum = 1;
    int pageSize = 1000;
//    while (true) {
      log.info("current...{},...{}", pageNum, pageSize);
      PageHelper.startPage(pageNum, pageSize, true);
      List<Conversation> conversations = conversationMapper.selectAll();
      if(conversations == null || conversations.isEmpty()) {
        log.error("conversations.isEmpty");
        return;
//        break;
      }
      JsonArray jsonArray = new JsonArray();
      JsonParser jsonParser = new JsonParser();
      ObjectMapper objectMapper = new ObjectMapper();
      conversations.stream().forEach(conversation -> {
        try {
          ConversationsDto conversationsDto = new ConversationsDto();
          BeanUtils.copyProperties(conversationsDto, conversation);
          String conversationStr = objectMapper.writeValueAsString(conversationsDto);
          JsonObject jsonObject = jsonParser.parse(conversationStr).getAsJsonObject();
          jsonArray.add(jsonObject);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      });
      log.info("jsonArray..send..." + jsonArray.size());
      rabbitTemplate.convertAndSend("elas.exchange", "elas.queue.conversationAdd", jsonArray.toString());
      log.info("jsonArray..end send..." + jsonArray.size());
      pageNum = pageNum + 1;
//    }
  }

  private void fetchMemberMemberTag(Integer wechatId) {
    int pageNum = 1;
    int pageSize = 1000;
//    while (true) {
      log.info("current...{},...{}", pageNum, pageSize);
      PageHelper.startPage(pageNum, pageSize, false);
      List<MemberMemberTag> memberMemberTags = memberMemberTagMapper.selectAll();
      if(memberMemberTags == null || memberMemberTags.isEmpty()) {
        log.error("memberMemberTags.isEmpty");
        return;
//        break;
      }
      JsonArray jsonArray = new JsonArray();
      JsonParser jsonParser = new JsonParser();
      ObjectMapper objectMapper = new ObjectMapper();
      memberMemberTags.stream().forEach(memberMemberTag -> {
        try {
          String memberMemberTagStr = objectMapper.writeValueAsString(memberMemberTag);
          JsonObject jsonObject = jsonParser.parse(memberMemberTagStr).getAsJsonObject();
          jsonArray.add(jsonObject);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      });
      log.info("jsonArray..send..." + jsonArray.size());
      rabbitTemplate.convertAndSend("elas.exchange", "elas.queue.memberMemberTagAdd", jsonArray.toString());
      log.info("jsonArray..end send..." + jsonArray.size());
      pageNum = pageNum + 1;
//    }
  }

  private void fetchMemberScanQrcode(Integer wechatId) {
    int pageNum = 1;
    int pageSize = 1000;
//    while (true) {
      log.info("current...{},...{}", pageNum, pageSize);
      PageHelper.startPage(pageNum, pageSize, false);
      List<MemberScanQrcode> memberScanQrcodes = memberScanQrcodeMapper.selectAll();
      if(memberScanQrcodes == null || memberScanQrcodes.isEmpty()) {
        log.error("memberMemberTags.isEmpty");
        return;
//        break;
      }
      JsonArray jsonArray = new JsonArray();
      JsonParser jsonParser = new JsonParser();
      ObjectMapper objectMapper = new ObjectMapper();
      memberScanQrcodes.stream().forEach(memberScanQrcode -> {
        try {
          String memberMemberTagStr = objectMapper.writeValueAsString(memberScanQrcode);
          JsonObject jsonObject = jsonParser.parse(memberMemberTagStr).getAsJsonObject();
          jsonArray.add(jsonObject);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      });
      log.info("jsonArray..send..." + jsonArray.size());
      rabbitTemplate.convertAndSend("elas.exchange", "elas.queue.memberScanQrcodeAdd", jsonArray.toString());
      log.info("jsonArray..end send..." + jsonArray.size());
      pageNum = pageNum + 1;
//    }
  }
}
