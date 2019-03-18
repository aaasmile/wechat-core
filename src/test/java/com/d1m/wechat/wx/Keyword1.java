package com.d1m.wechat.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class Keyword1 {

    @JsonProperty("color")
    @NotEmpty
    private String color;

    @JsonProperty("value")
    @NotEmpty
    private String value;

}