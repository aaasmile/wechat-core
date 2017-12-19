package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("素材")
@Getter
@Setter
public class MaterialModel {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("图文")
    private List<ImageTextModel> items;

    @ApiModelProperty("主键ID")
    private String url;

    @ApiModelProperty("图文详情")
    private MaterialTextDetailModel text;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("创建时间")
    private String createdAt;

    @ApiModelProperty("图片分类")
    private Byte materialType;

    @ApiModelProperty("图文列表")
    private List<ImageTextModel> imagetexts;// for create imagetext

    @ApiModelProperty("小程序")
    private MiniProgramModel miniProgram;// for create mini program

    @ApiModelProperty("图文列表")
    private Boolean push;

    private Integer memberId;

}
