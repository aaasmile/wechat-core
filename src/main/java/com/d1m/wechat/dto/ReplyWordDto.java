package com.d1m.wechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReplyWordDto {
    private Integer key;
    @JsonProperty("reply_word")
    private String title;
}
