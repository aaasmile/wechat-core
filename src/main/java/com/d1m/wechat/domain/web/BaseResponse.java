package com.d1m.wechat.domain.web;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    @ApiModelProperty(value = "状态码", required = true)
    private Integer resultCode;
    @ApiModelProperty(value = "返回信息", example = "操作成功")
    private String msg;
    @ApiModelProperty(value = "返回对象json")
    private T data;
}
