package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("微信公众号参数")
public class WechatModel extends BaseModel {
	
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("状态")
	private Byte status;
	@ApiModelProperty("公司ID")
	private Integer companyId;
	@ApiModelProperty("是否有系统权限")
	private Integer isSystemRole;

	public String getName() {
		return name;
	}

	public Byte getStatus() {
		return status;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public Integer getIsSystemRole() {
		return isSystemRole;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setIsSystemRole(Integer isSystemRole) {
		this.isSystemRole = isSystemRole;
	}


}
