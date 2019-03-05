package com.d1m.wechat.dto;

import com.d1m.wechat.model.InterfaceConfig;

import java.util.List;

public class MenuDto {

	private Integer id;

	private Byte type;

	private Byte materialType;

	private String name;

	private String url;

	private MaterialDto material;

	private MenuDto parent;

	private List<MenuDto> children;

	private Integer seq;

	private String appId;

	private String pagePath;

	private String appUrl;
	
	private String menuKey;

	private String apiClass;

	private InterfaceConfig third;
	
	public List<MenuDto> getChildren() {
		return children;
	}

	public Integer getId() {
		return id;
	}

	public MaterialDto getMaterial() {
		return material;
	}

	public Byte getMaterialType() {
		return materialType;
	}

	public String getName() {
		return name;
	}

	public MenuDto getParent() {
		return parent;
	}

	public Integer getSeq() {
		return seq;
	}

	public Byte getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setChildren(List<MenuDto> children) {
		this.children = children;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMaterial(MaterialDto material) {
		this.material = material;
	}

	public void setMaterialType(Byte materialType) {
		this.materialType = materialType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(MenuDto parent) {
		this.parent = parent;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getApiClass() {
		return apiClass;
	}

	public void setApiClass(String apiClass) {
		this.apiClass = apiClass;
	}

	public InterfaceConfig getThird() {
		return third;
	}

	public void setThird(InterfaceConfig third) {
		this.third = third;
	}

	@Override
	public String toString() {
		return "MenuDto{" +
				"id=" + id +
				", type=" + type +
				", materialType=" + materialType +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				", material=" + material +
				", parent=" + parent +
				", children=" + children +
				", seq=" + seq +
				", appId='" + appId + '\'' +
				", pagePath='" + pagePath + '\'' +
				", appUrl='" + appUrl + '\'' +
				", menuKey='" + menuKey + '\'' +
				", apiClass='" + apiClass + '\'' +
				", third=" + third +
				'}';
	}
}
