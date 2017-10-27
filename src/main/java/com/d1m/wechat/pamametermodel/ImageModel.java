package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("图片参数")
public class ImageModel extends BaseModel {
	
	@ApiModelProperty("素材图片类型ID")
	private Integer materialImageTypeId;
	
	@ApiModelProperty("查询条件")
	private String query;
	
	@ApiModelProperty("是否推送")
	private Boolean pushed;
	
	@ApiModelProperty("素材类型")
	private Integer materialType;

	public Integer getMaterialImageTypeId() {
		return materialImageTypeId;
	}

	public Boolean getPushed() {
		return pushed;
	}

	public String getQuery() {
		return query;
	}

	public void setMaterialImageTypeId(Integer materialImageTypeId) {
		this.materialImageTypeId = materialImageTypeId;
	}

	public void setPushed(Boolean pushed) {
		this.pushed = pushed;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

}
