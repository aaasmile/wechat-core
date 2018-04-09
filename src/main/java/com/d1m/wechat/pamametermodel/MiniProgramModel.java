package com.d1m.wechat.pamametermodel;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * MiniProgramModel
 *
 * @author f0rb on 2017-11-22.
 */
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}

	public Integer getThumbMaterialId() {
		return thumbMaterialId;
	}

	public void setThumbMaterialId(Integer thumbMaterialId) {
		this.thumbMaterialId = thumbMaterialId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
    
    
}
