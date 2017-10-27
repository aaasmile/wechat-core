package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("菜单组参数")
public class MenuGroupModel extends BaseModel {
	
	@ApiModelProperty("菜单组ID")
	private Integer id;
	
	@ApiModelProperty("菜单组名称")
	private String menuGroupName;
	
	@ApiModelProperty("菜单")
	private List<MenuModel> menus;
	
	@ApiModelProperty("规则")
	private MenuRuleModel rules;
	
	@ApiModelProperty("微信菜单ID")
    private String wxMenuId;
	
	@ApiModelProperty("是否推送")
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
