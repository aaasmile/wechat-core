package com.d1m.wechat.pamametermodel;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * MiniProgramModel
 *
 * @author f0rb on 2017-11-22.
 */
@Getter
@Setter
@ApiModel("图文参数")
public class MiniProgramModel extends BaseModel {

    private Integer id;

    private Integer materialId;

    private Integer wechatId;

    @ApiModelProperty("小程序的标题")
    private String title;

    @ApiModelProperty("小程序的appid")
    private String appid;

    @ApiModelProperty("小程序的页面路径")
    private String pagepath;

    @ApiModelProperty("小程序卡片图片的素材ID")
    private Integer thumbMaterialId;

    private Integer creatorId;

    private Date createdAt;

    private Byte status;
}
