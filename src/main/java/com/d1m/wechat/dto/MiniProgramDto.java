package com.d1m.wechat.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * MiniProgramDto
 *
 * @author f0rb on 2017-11-23.
 */
@Getter
@Setter
public class MiniProgramDto {

    private Integer id;

    private Integer materialId;

    private Integer wechatId;

    private String title;

    private String appid;

    private String pagepath;

    private Integer coverMaterialId;

    private String coverMaterialUrl;

    private String thumbMediaId;

    private Integer creatorId;

    private Date createdAt;

    private Byte status;
}
