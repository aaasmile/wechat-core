package com.d1m.wechat.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ElasticsearchConsumer {
  private static Logger log = LoggerFactory.getLogger(ElasticsearchConsumer.class);
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static final String ELAS_EXCHANGE = "elas.exchange";
  public static final String DIRECT = "direct";
  public static final String ELAS_DEADLETTER_EXCHANGE = "elas.deadletter.exchange";
  public static final String ELAS_ALTERNATE_EXCHANGE = "elas.alternate.exchange";
  public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
  public static final String ALTERNATE_EXCHANGE = "alternate-exchange";
  public static final String ELAS_QUEUE_MEMBERADD = "elas.queue.memberAdd";
  public static final String ELAS_QUEUE_MEMBERUPDATE = "elas.queue.memberUpdate";
  public static final String ELAS_QUEUE_CONVERSATION_ADD = "elas.queue.conversationAdd";
  public static final String ELAS_QUEUE_CONVERSATION_UPDATE = "elas.queue.conversationUpdate";
  public static final String ELAS_QUEUE_MEMBER_MEMBER_TAG_ADD = "elas.queue.memberMemberTagAdd";
  public static final String ELAS_QUEUE_MEMBER_MEMBER_TAG_UPDATE = "elas.queue.memberMemberTagUpdate";

  public static final String ELAS_QUEUE_MEMBER_SCAN_QRCODE_ADD = "elas.queue.memberScanQrcodeAdd";
  public static final String ELAS_QUEUE_MEMBER_SCAN_QRCODE_UPDATE = "elas.queue.memberScanQrcodeUpdate";

  public static final String ELAS_QUEUE_WECHAT_IMAGE_TEXT_ADD = "elas.queue.wechatImageTextAdd";
  public static final String ELAS_QUEUE_DCRM_IMAGE_TEXT_ADD = "elas.queue.dcrmImageTextAdd";

  public static final String MEMBER = "member";
  public static final String ID = "id";
  public static final String OPENID = "openId";
  public static final String CONVERSATION = "conversation";
  public static final String MEMBER_MEMBER_TAG = "member_member_tag";
  public static final String MEMBER_SCAN_QRCODE = "member_scan_qrcode";
  public static final String WECHAT_IMAGE_TEXT = "wechat_image_text";
  public static final String DCRM_IMAGE_TEXT = "dcrm_image_text";

  public static final String MEMBER_ID = "memberId";
  public static final String MEMBER_TAG_ID = "memberTagId";
  public static final String WECHAT_ID = "wechatId";

  public static void pushEs(RabbitTemplate rabbitTemplate, Object object, String queue) {
    try {
      String memberStr = objectMapper.writeValueAsString(object);
      JsonArray array = new JsonArray();
      JsonParser jsonParser = new JsonParser();
      JsonObject jsonObject = jsonParser.parse(memberStr).getAsJsonObject();
      array.add(jsonObject);
      rabbitTemplate.convertAndSend(ELAS_EXCHANGE, queue, array.toString());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
