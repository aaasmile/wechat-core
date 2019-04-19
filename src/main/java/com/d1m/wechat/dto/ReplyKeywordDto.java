package com.d1m.wechat.dto;

import com.d1m.wechat.model.ReplyWords;
import lombok.Data;

import java.util.List;

@Data
public class ReplyKeywordDto {

    private Integer key;

    private String title;

    private Byte replyType;

    private Byte matchMode;

    private String createdAt;

    private Integer weight;

    private List<ReplyWordDto> children;
}
