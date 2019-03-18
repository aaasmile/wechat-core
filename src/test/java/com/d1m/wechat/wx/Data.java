package com.d1m.wechat.wx;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@lombok.Data
public class Data {

    @JsonProperty("keyword3")
    @NotNull
    private Keyword3 keyword3;

    @JsonProperty("keyword1")
    @NotNull
    private Keyword1 keyword1;

    @JsonProperty("keyword2")
    @NotNull
    private Keyword2 keyword2;

    @JsonProperty("keyword4")
    @NotNull
    private Keyword2 keyword4;

    @JsonProperty("keyword5")
    @NotNull
    private Keyword2 keyword5;

    @JsonProperty("remark")
    @NotNull
    private Remark remark;

    @JsonProperty("first")
    @NotNull
    private First first;

}