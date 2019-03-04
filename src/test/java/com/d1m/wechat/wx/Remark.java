package com.d1m.wechat.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@lombok.Data
public class Remark {

    @JsonProperty("color")
    @NotEmpty
    private String color;

    @JsonProperty("value")
    @NotEmpty
    private String value;

}