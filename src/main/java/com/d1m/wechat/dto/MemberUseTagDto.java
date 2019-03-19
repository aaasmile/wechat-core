package com.d1m.wechat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUseTagDto {

    private Integer id;
    private String openId;
    private Integer tagId;

    public Integer getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
