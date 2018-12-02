package com.d1m.wechat.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.d1m.wechat.util.DateUtil;

public class MaterialDto {

	private Integer id;

	private List<ImageTextDto> items;

	private String url;

	private MaterialTextDetailDto text;

	private String title;

	private String createdAt;

	private Byte materialType;

	private String lastPushAt;

	private boolean isPushed;

	private String modifyAt;
	
	private String wxPicUrl;

	private String mediaId;

	private String name;


//	private Integer comment;

	public String getCreatedAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(createdAt));
	}

	public Integer getId() {
		return id;
	}

	public List<ImageTextDto> getItems() {
		return items;
	}

	public String getLastPushAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(lastPushAt));
	}

	public Byte getMaterialType() {
		return materialType;
	}

	public String getModifyAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(modifyAt));
	}

	public MaterialTextDetailDto getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public boolean isPushed() {
		return StringUtils.isNotBlank(getLastPushAt())
				&& StringUtils.isNotBlank(getModifyAt())
				&& StringUtils.equals(getLastPushAt(), getModifyAt());
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItems(List<ImageTextDto> items) {
		this.items = items;
	}

	public void setLastPushAt(String lastPushAt) {
		this.lastPushAt = lastPushAt;
	}

	public void setMaterialType(Byte materialType) {
		this.materialType = materialType;
	}

	public void setModifyAt(String modifyAt) {
		this.modifyAt = modifyAt;
	}

	public void setPushed(boolean isPushed) {
		this.isPushed = isPushed;
	}

	public void setText(MaterialTextDetailDto text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWxPicUrl() {
		return wxPicUrl;
	}

	public void setWxPicUrl(String wxPicUrl) {
		this.wxPicUrl = wxPicUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//	public Integer getComment() {
//		return comment;
//	}
//	public void setComment(Integer comment) {
//		this.comment = comment;
//	}

}
