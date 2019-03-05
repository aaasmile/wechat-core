package com.d1m.wechat.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@lombok.Data
public class WxTemplateMsg {

    @JsonProperty("touser")
    @NotEmpty
    private String touser;

    @JsonProperty("data")
    @NotNull
    @Valid
    private Data data;

    @JsonProperty("template_id")
    @NotEmpty
    private String templateId;

    @JsonProperty("miniprogram")
    @NotNull
    @Valid
    private Miniprogram miniprogram;

}