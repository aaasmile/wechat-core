package com.d1m.wechat.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.d1m.wechat.util.DateUtil;

public class MenuGroupDto {

	private Integer id;

	private String name;

	private List<MenuDto> menus;

	private MenuRuleDto menuRule;

	private String lastPushAt;

	private boolean isPushed;

	private String modifyAt;

	public Integer getId() {
		return id;
	}

	public String getLastPushAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(lastPushAt));
	}

	public MenuRuleDto getMenuRule() {
		return menuRule;
	}

	public List<MenuDto> getMenus() {
		return menus;
	}

	public String getModifyAt() {
		return DateUtil.formatYYYYMMDDHHMMSS(DateUtil.parse(modifyAt));
	}

	public String getName() {
		return name;
	}

	public boolean isPushed() {
		return StringUtils.isNotBlank(getLastPushAt())
				&& StringUtils.isNotBlank(getModifyAt())
				&& StringUtils.equals(getLastPushAt(), getModifyAt());
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastPushAt(String lastPushAt) {
		this.lastPushAt = lastPushAt;
	}

	public void setMenuRule(MenuRuleDto menuRule) {
		this.menuRule = menuRule;
	}

	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

	public void setModifyAt(String modifyAt) {
		this.modifyAt = modifyAt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPushed(boolean isPushed) {
		this.isPushed = isPushed;
	}

}
