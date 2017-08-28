package com.d1m.wechat.pamametermodel;

import java.util.List;

public class MenuGroupModel extends BaseModel {

	private Integer id;

	private String menuGroupName;

	private List<MenuModel> menus;

	private MenuRuleModel rules;

    private String wxMenuId;

	private Boolean push;

	public Integer getId() {
		return id;
	}

	public String getMenuGroupName() {
		return menuGroupName;
	}

	public List<MenuModel> getMenus() {
		return menus;
	}

	public Boolean getPush() {
		return push;
	}

	public MenuRuleModel getRules() {
		return rules;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}

	public void setMenus(List<MenuModel> menus) {
		this.menus = menus;
	}

    public String getWxMenuId() {
        return wxMenuId;
    }

    public void setWxMenuId(String wxMenuId) {
        this.wxMenuId = wxMenuId;
    }

    public void setPush(Boolean push) {
		this.push = push;
	}

	public void setRules(MenuRuleModel rules) {
		this.rules = rules;
	}

}
