package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: wechat-core
 * @description: 活动事件参数
 * @author: LiuTuo
 * @create: 2019-03-27 16:54
 **/
@ApiModel("活动事件参数")
public class ConversationActivityModel extends BaseModel{

    private String memberId;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("unionId")
    private String unionId;

    @ApiModelProperty("活动标题（json数组字符串，活动名称+活动编号）")
    private String title;

    @ApiModelProperty("活动编号")
    private String eventKey;

    private Byte event;

    private String eventName;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Byte getEvent() {
        return event;
    }

    public void setEvent(Byte event) {
        this.event = event;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
