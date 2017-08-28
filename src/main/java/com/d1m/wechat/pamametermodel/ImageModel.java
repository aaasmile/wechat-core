package com.d1m.wechat.pamametermodel;

public class ImageModel extends BaseModel {

	private Integer materialImageTypeId;

	private String query;

	private Boolean pushed;

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
