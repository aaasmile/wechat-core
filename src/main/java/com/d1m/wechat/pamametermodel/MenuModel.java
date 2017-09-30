package com.d1m.wechat.pamametermodel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MenuModel {

	private Integer id;

	private Byte type;

	private Byte materialType;

	private String name;

	private MaterialModel material;

	private MenuModel parent;

	private List<MenuModel> children;

	private String url;

	private Integer seq;

	private String appId;

	private String pagePath;

	private String appUrl;

//	public List<MenuModel> getChildren() {
//		return children;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public MaterialModel getMaterial() {
//		return material;
//	}
//
//	public Byte getMaterialType() {
//		return materialType;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public MenuModel getParent() {
//		return parent;
//	}
//
//	public Integer getSeq() {
//		return seq;
//	}
//
//	public Byte getType() {
//		return type;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setChildren(List<MenuModel> children) {
//		this.children = children;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public void setMaterial(MaterialModel material) {
//		this.material = material;
//	}
//
//	public void setMaterialType(Byte materialType) {
//		this.materialType = materialType;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public void setParent(MenuModel parent) {
//		this.parent = parent;
//	}
//
//	public void setSeq(Integer seq) {
//		this.seq = seq;
//	}
//
//	public void setType(Byte type) {
//		this.type = type;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}

}
