package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import cn.d1m.wechat.client.core.WxResponse;
import cn.d1m.wechat.client.model.WxMenu;
import cn.d1m.wechat.client.model.WxMenuGroup;
import cn.d1m.wechat.client.model.WxMenuMatchrule;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MenuDto;
import com.d1m.wechat.dto.MenuGroupDto;
import com.d1m.wechat.dto.ReportMenuGroupDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.*;
import com.d1m.wechat.pamametermodel.*;
import com.d1m.wechat.service.MenuGroupService;
import com.d1m.wechat.util.Message;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Service
public class MenuGroupServiceImpl extends BaseService<MenuGroup> implements
		MenuGroupService {

	private Logger log = LoggerFactory.getLogger(MenuGroupServiceImpl.class);

	@Autowired
	private MenuGroupMapper menuGroupMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private MaterialTextDetailMapper materialTextDetailMapper;

	@Autowired
	private MaterialMapper materialMapper;

	@Autowired
	private MenuRuleMapper menuRuleMapper;

	@Autowired
	private AreaInfoMapper areaInfoMapper;

	@Override
	public Mapper<MenuGroup> getGenericMapper() {
		return menuGroupMapper;
	}

	@Override
	public MenuGroup getDefaultMenuGroup(User user, Integer wechatId) {
		return menuGroupMapper.getDefaultMenuGroup(wechatId);
	}

	@Override
	public MenuGroup create(User user, Integer wechatId,
			MenuGroupModel menuGroupModel) throws WechatException {
		notBlank(menuGroupModel.getMenuGroupName(),
				Message.MENU_GROUP_NAME_NOT_BLANK);
		List<MenuModel> menuModels = menuGroupModel.getMenus();
		boolean menusIsEmpty = (menuModels == null || menuModels.isEmpty());
		if (menusIsEmpty) {
			throw new WechatException(Message.MENU_GROUP_MENU_NOT_BLANK);
		}
		boolean ruleIsEmpty = menuGroupModel.getRules() == null
				|| menuGroupModel.getRules().empty();

		MenuGroup defaultMenuGroup = menuGroupMapper
				.getDefaultMenuGroup(wechatId);
		if (!ruleIsEmpty) {
			notBlank(defaultMenuGroup, Message.MENU_GROUP_DEFAULT_NOT_BLANK);
		} else {
			if (defaultMenuGroup != null) {
				throw new WechatException(Message.MENU_GROUP_DEFAULT_EXIST);
			}
		}
		Date current = new Date();
		List<MenuDto> menuList = getMenuDtos(user, wechatId, menuModels,
				current);
		MenuRule menuRule = getMenuRule(user, menuGroupModel.getRules());

		MenuGroup menuGroup = createMenuGroup(user, wechatId, menuGroupModel, current);

		Menu menu = null;
		for (MenuDto dto : menuList) {
			menu = create(user, wechatId, current, menuGroup, null, dto);
			if (dto.getChildren() != null && !dto.getChildren().isEmpty()) {
				for (MenuDto menuDto : dto.getChildren()) {
					create(user, wechatId, current, menuGroup, menu.getId(),
							menuDto);
				}
			}
		}

		createMenuRule(user, wechatId, current, menuRule, menuGroup);

		return menuGroup;
	}

	private void createMenuRule(User user, Integer wechatId, Date current,
			MenuRule menuRule, MenuGroup menuGroup) {
		if (menuRule == null) {
			return;
		}
		menuRule.setMenuGroupId(menuGroup.getId());
		menuRule.setCreatedAt(current);
		menuRule.setCreatorId(user.getId());
		menuRule.setWechatId(wechatId);
		menuRuleMapper.insert(menuRule);
		menuGroup.setMenuRuleId(menuRule.getId());
		menuGroupMapper.updateByPrimaryKey(menuGroup);
	}

    /**
     * 调整String menuGroupName为MenuGroupModel, 以便同步微信菜单时调用, f0rb
     */
	private MenuGroup createMenuGroup(User user, Integer wechatId,
			MenuGroupModel menuGroupModel, Date current) {
		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setCreatedAt(current);
		menuGroup.setCreatorId(user.getId());
		menuGroup.setName(menuGroupModel.getMenuGroupName());
		menuGroup.setWxMenuId(menuGroupModel.getWxMenuId());
        if (Boolean.TRUE.equals(menuGroupModel.getPush())) {
            menuGroup.setPushAt(current);
        }
		menuGroup.setWechatId(wechatId);
		menuGroup.setStatus(MenuGroupStatus.INUSED.getValue());
		menuGroupMapper.insert(menuGroup);
		return menuGroup;
	}

	private Menu create(User user, Integer wechatId, Date current,
			MenuGroup menuGroup, Integer parentId, MenuDto menuDto) {
		Menu menu = new Menu();
		menu.setCreatedAt(current);
		menu.setCreatorId(user.getId());
		MaterialDto materialDto = menuDto.getMaterial();
		if (materialDto != null) {
			menu.setMenuKey(materialDto.getId());
			menu.setUrl(materialDto.getUrl());
		}
		menu.setName(menuDto.getName());
		menu.setStatus(MenuStatus.INUSED.getValue());
		MenuType menuType = MenuType.getByValue(menuDto.getType());
		if (menuType != null) {
			menu.setType(menuType.getValue());
		}
		menu.setWechatId(wechatId);
		menu.setMenuGroupId(menuGroup.getId());
		menu.setParentId(parentId);
		menuMapper.insert(menu);
		return menu;
	}

	private List<MenuDto> getMenuDtos(User user, Integer wechatId,
			List<MenuModel> menuModels, Date current) throws WechatException {
		int menuSize = menuModels.size();
		if (menuSize > 3) {
			throw new WechatException(
					Message.MENU_GROUP_FIRST_LEVEL_MENU_MUST_LESS_3);
		}
		List<MenuDto> menuList = new ArrayList<MenuDto>();
		List<MenuDto> childrenMenuDtos = null;
		List<MenuModel> childrenMenuModels = null;
		MenuDto menuDto = null;
		Integer id = null;
		Integer seq = 1;
		for (MenuModel menuModel : menuModels) {
			menuModel.setSeq(seq);
			childrenMenuModels = menuModel.getChildren();
			if (childrenMenuModels != null && !childrenMenuModels.isEmpty()) {
				if (menuModel.getChildren().size() > 5) {
					throw new WechatException(
							Message.MENU_GROUP_SECOND_LEVEL_MENU_MUST_LESS_5);
				}
				notBlank(menuModel.getName(), Message.MENU_NAME_NOT_BLANK);
				id = menuModel.getId();
				if (id != null) {
					getMenu(wechatId, id);
				}
				menuDto = new MenuDto();
				menuDto.setId(id);
				menuDto.setName(menuModel.getName());
				menuDto.setSeq(seq);
				menuDto.setType((byte)0); //对于父菜单,重置type为null
				menuList.add(menuDto);
				childrenMenuDtos = new ArrayList<MenuDto>();
				Integer subSeq = 1;
				for (MenuModel child : childrenMenuModels) {
					child.setSeq(subSeq);
					childrenMenuDtos
							.add(getMenu(child, user, wechatId, current));
					subSeq++;
				}
				menuDto.setChildren(childrenMenuDtos);
			} else {
				menuList.add(getMenu(menuModel, user, wechatId, current));
			}
			seq++;
		}
		return menuList;
	}

	private MenuDto getMenu(MenuModel menuModel, User user, Integer wechatId,
			Date current) throws WechatException {
		String content = null, url = null;
		Integer materialId = null;
		notBlank(menuModel.getName(), Message.MENU_NAME_NOT_BLANK);
		notBlank(menuModel.getType(), Message.MENU_TYPE_NOT_BLANK);
		MenuType menuType = MenuType.getByValue(menuModel.getType());
		if (menuType == null) {
			throw new WechatException(Message.MENU_TYPE_INVALID);
		}
		MaterialModel materialModel = menuModel.getMaterial();
		if (menuType == MenuType.CLICK) {
			MaterialTextDetailModel materialTextDetailModel = null;
			if (materialModel != null) {
				materialTextDetailModel = materialModel.getText();
			}
			boolean empty = false;
			if ((materialModel != null && materialModel.getId() != null)
					|| (materialTextDetailModel != null && StringUtils
							.isNotBlank(materialTextDetailModel.getContent()))) {
				empty = false;
			}
			if (empty) {
				throw new WechatException(Message.MENU_CONTENT_NOT_BLANK);
			}
			if (materialModel != null) {
				materialId = materialModel.getId();
			}
			if (materialTextDetailModel != null) {
				content = materialTextDetailModel.getContent();
			}
			if (materialId == null) {
				MaterialTextDetail record = new MaterialTextDetail();
				record.setContent(content);
				record.setWechatId(wechatId);
				List<MaterialTextDetail> records = materialTextDetailMapper
						.select(record);
				if (!records.isEmpty()) {
					record = records.get(0);
				} else {
					record = null;
				}
				if (record == null) {
					Material material = new Material();
					material.setCreatedAt(current);
					if (user != null) {
						material.setCreatorId(user.getId());
					}
					material.setPicUrl(StringUtils.EMPTY);
					material.setStatus(MaterialStatus.INUSED.getValue());
					material.setMaterialType(MaterialType.TEXT.getValue());
					material.setWechatId(wechatId);
					materialMapper.insert(material);

					MaterialTextDetail materialTextDetail = new MaterialTextDetail();
					materialTextDetail.setContent(content);
					materialTextDetail.setMaterialId(material.getId());
					materialTextDetail.setWechatId(wechatId);
					materialTextDetailMapper.insert(materialTextDetail);
					materialId = material.getId();
				} else {
					materialId = record.getMaterialId();
				}
			} else {
				Material record = new Material();
				record.setWechatId(wechatId);
				record.setId(materialId);
				record.setStatus(MenuStatus.INUSED.getValue());
				if (materialMapper.selectCount(record) == 0) {
					throw new WechatException(Message.MATERIAL_NOT_EXIST);
				}
			}
		} else {
            if (menuType == MenuType.VIEW) {//只有view有url, f0rb
                notBlank(menuModel.getUrl(), Message.MENU_URL_NOT_BLANK);
            }
			url = menuModel.getUrl();
			if (materialModel != null) {
				materialId = materialModel.getId();
			}
			if (materialId != null) {
				Material record = new Material();
				record.setId(materialId);
				record.setWechatId(wechatId);
				record = materialMapper.selectOne(record);
				if (record == null) {
					throw new WechatException(Message.MATERIAL_NOT_EXIST);
				}
			}
		}
		if (menuModel.getId() != null) {
			getMenu(wechatId, menuModel.getId());
		}
		MenuDto menu = new MenuDto();
		menu.setId(menuModel.getId());
		menu.setName(menuModel.getName());
		menu.setSeq(menuModel.getSeq());
		menu.setType(menuType.getValue());
		MaterialDto material = new MaterialDto();
		material.setId(materialId);
		material.setUrl(url);
		menu.setMaterial(material);
		return menu;
	}

	private void getMenu(Integer wechatId, Integer id) throws WechatException {
		Menu record = new Menu();
		record.setId(id);
		record.setWechatId(wechatId);
		record = menuMapper.selectOne(record);
		if (record == null) {
			throw new WechatException(Message.MENU_NOT_EXIST);
		}
	}

	private MenuRule getMenuRule(User user, MenuRuleModel menuRuleModel)
			throws WechatException {
		if (menuRuleModel == null) {
			return null;
		}
		if (menuRuleModel.empty()) {
			throw new WechatException(Message.MENU_GROUP_RULE_MUST_BE_EXIST_ONE);
		}
		AreaInfo provinceAreaInfo = null;
		if (menuRuleModel.getProvince() != null) {
			provinceAreaInfo = areaInfoMapper.selectByPrimaryKey(menuRuleModel
					.getProvince());
			notBlank(provinceAreaInfo, Message.AREA_PROVINCE_NOT_EXIST);
		}
		AreaInfo cityAreaInfo = null;
		if (menuRuleModel.getCity() != null) {
			cityAreaInfo = areaInfoMapper.selectByPrimaryKey(menuRuleModel
					.getCity());
			notBlank(cityAreaInfo, Message.AREA_CITY_NOT_EXIST);
		}
		AreaInfo countryAreaInfo = null;
		if (menuRuleModel.getCountry() != null) {
			countryAreaInfo = areaInfoMapper.selectByPrimaryKey(menuRuleModel
					.getCountry());
			notBlank(countryAreaInfo, Message.AREA_COUNTRY_NOT_EXIST);
		}

		MenuRule menuRule = new MenuRule();
		if (provinceAreaInfo != null) {
			menuRule.setProvince(provinceAreaInfo.getId());
		}
		if (cityAreaInfo != null) {
			menuRule.setCity(cityAreaInfo.getId());
		}
		if (countryAreaInfo != null) {
			menuRule.setCountry(countryAreaInfo.getId());
		}
		Os os = Os.getByValue(menuRuleModel.getClientPlatformType());
		if (os != null) {
			menuRule.setClientPlatformType(os.getValue());
		}
		menuRule.setCreatorId(user.getId());
		menuRule.setLanguage(menuRuleModel.getLanguage());
		Sex sexEnum = Sex.getByValue(menuRuleModel.getGender());
		if (sexEnum != null) {
			menuRule.setSex((byte) sexEnum.getValue());
		}
		menuRule.setTagId(menuRuleModel.getTagId());
		return menuRule;
	}

	@Override
	public MenuGroup update(User user, Integer wechatId, Integer menuGroupId,
			MenuGroupModel menuGroupModel) throws WechatException {
		notBlank(menuGroupId, Message.MENU_GROUP_ID_NOT_BLANK);
		notBlank(menuGroupModel.getMenuGroupName(),
				Message.MENU_GROUP_NAME_NOT_BLANK);

		List<MenuModel> menuModels = menuGroupModel.getMenus();
		boolean menusIsEmpty = (menuModels == null || menuModels.isEmpty());
		if (menusIsEmpty) {
			throw new WechatException(Message.MENU_GROUP_MENU_NOT_BLANK);
		}

		MenuGroup menuGroup = getMenuGroup(wechatId, menuGroupId);
		notBlank(menuGroup, Message.MENU_GROUP_NOT_EXIST);

		MenuGroup defaultMenuGroup = menuGroupMapper
				.getDefaultMenuGroup(wechatId);
		if (defaultMenuGroup.getId().equals(menuGroupId)) {
			if (menuGroupModel.getRules() != null) {
				notBlank(defaultMenuGroup, Message.MENU_GROUP_DEFAULT_NOT_BLANK);
			}
		} else {
			if (menuGroupModel.getRules() == null) {
				notBlank(defaultMenuGroup,
						Message.MENU_GROUP_DEFAULT_ONLY_EXIST_ONE);
			}
		}
		Date current = new Date();
		List<MenuDto> menuDtoList = getMenuDtos(user, wechatId, menuModels,
				current);
		List<MenuDto> allMenuDtoList = new ArrayList<MenuDto>();
		for (MenuDto menuDto : menuDtoList) {
			allMenuDtoList.add(menuDto);
			if (menuDto.getChildren() != null
					&& !menuDto.getChildren().isEmpty()) {
				for (MenuDto child : menuDto.getChildren()) {
					allMenuDtoList.add(child);
				}
			}
		}

		MenuRule menuRule = getMenuRule(user, menuGroupModel.getRules());

		Menu record = new Menu();
		record.setWechatId(wechatId);
		record.setMenuGroupId(menuGroupId);
		List<Menu> existMenus = menuMapper.select(record);

		List<Menu> deleteList = new ArrayList<Menu>();
		for (Menu m : existMenus) {
			if (!contains(allMenuDtoList, m)) {
				deleteList.add(m);
			}
		}

		logicalDeleteMenus(deleteList, user, current);

		Menu menu = null;
		for (MenuDto m : menuDtoList) {
			menu = createdOrUpdated(user, wechatId, menuGroup, current, m, null);
			if (m.getChildren() != null && !m.getChildren().isEmpty()) {
				for (MenuDto child : m.getChildren()) {
					createdOrUpdated(user, wechatId, menuGroup, current, child,
							menu.getId());
				}
			}
		}

		createMenuRule(user, wechatId, current, menuRule, menuGroup);

		menuGroup.setModifyAt(current);
		menuGroup.setName(menuGroupModel.getMenuGroupName());
		menuGroupMapper.updateByPrimaryKey(menuGroup);

		return menuGroup;
	}

	private MenuGroup getMenuGroup(Integer wechatId, Integer menuGroupId) {
		MenuGroup menuGroup = new MenuGroup();
		menuGroup.setId(menuGroupId);
		menuGroup.setWechatId(wechatId);
		menuGroup.setStatus(MenuGroupStatus.INUSED.getValue());
		menuGroup = menuGroupMapper.selectOne(menuGroup);
		return menuGroup;
	}

	private Menu createdOrUpdated(User user, Integer wechatId,
			MenuGroup menuGroup, Date current, MenuDto m, Integer parentId) {
		Menu menu = null;
		MaterialDto material = m.getMaterial();
		if (m.getId() != null) {
			/** update */
			menu = menuMapper.selectByPrimaryKey(m.getId());
			if (material != null) {
				menu.setMenuKey(material.getId());
				menu.setUrl(material.getUrl());
			}else{
				menu.setMenuKey(null);
			}
			menu.setName(m.getName());
			MenuType menuType = MenuType.getByValue(m.getType());
			if (menuType != null) {
				menu.setType(menuType.getValue());
			}else{
				menu.setType((byte)0);
			}
			menu.setModifyAt(current);
			menu.setParentId(parentId);
			menu.setSeq(m.getSeq());
			menuMapper.updateByPrimaryKey(menu);
		} else {
			/** create */
			menu = new Menu();
			menu.setCreatedAt(current);
			menu.setCreatorId(user.getId());
			menu.setMenuGroupId(menuGroup.getId());
			if (material != null) {
				menu.setMenuKey(material.getId());
				menu.setUrl(material.getUrl());
			}
			menu.setName(m.getName());
			menu.setStatus(MenuStatus.INUSED.getValue());
			MenuType menuType = MenuType.getByValue(m.getType());
			if (menuType != null) {
				menu.setType(menuType.getValue());
			}else{
				menu.setType((byte)0);
			}
			menu.setWechatId(wechatId);
			menu.setParentId(parentId);
			menu.setSeq(m.getSeq());
			menuMapper.insert(menu);
		}
		return menu;
	}

	private boolean contains(List<MenuDto> list, Menu menu) {
		if (menu == null) {
			return false;
		}
		for (MenuDto m : list) {
			if (m.getId() != null && m.getId().equals(menu.getId())) {
				return true;
			}
		}
		return false;
	}

	private void logicalDeleteMenus(List<Menu> menus, User user, Date current) {
		for (Menu m : menus) {
			m.setStatus(MenuStatus.DELETED.getValue());
			m.setDeletedAt(current);
			m.setDeletorId(user.getId());
			menuMapper.updateByPrimaryKey(m);
		}
	}

	@Override
	public MenuGroupDto get(Integer wechatId, Integer menuGroupId) {
		return menuGroupMapper.get(wechatId, menuGroupId,
				MenuStatus.INUSED.getValue());
	}

	@Override
	public Page<MenuGroupDto> find(Integer wechatId,
			MenuGroupModel menuGroupModel, boolean queryCount) {
		if (menuGroupModel == null) {
			menuGroupModel = new MenuGroupModel();
		}
		if (menuGroupModel.pagable()) {
			PageHelper.startPage(menuGroupModel.getPageNum(),
					menuGroupModel.getPageSize(), queryCount);
		}
		return menuGroupMapper.search(wechatId, MenuStatus.INUSED.getValue());
	}

	@Override
	public MenuGroup delete(User user, Integer wechatId, Integer menuGroupId)
			throws WechatException {
		notBlank(menuGroupId, Message.MENU_GROUP_ID_NOT_BLANK);
		MenuGroup menuGroup = getMenuGroup(wechatId, menuGroupId);
		notBlank(menuGroup, Message.MENU_GROUP_NOT_EXIST);

		if (menuGroup.getMenuRuleId() == null) {
			Integer count = menuGroupMapper
					.getPersonalizedMenugroupCount(wechatId);
			if (count > 0) {
				throw new WechatException(
						Message.MENU_GROUP_DEFAULT_CANNOT_DELETE_WHEN_PERSONALIZEDMENUGROUP_EXIST);
			}
		}

		menuGroup.setDeletedAt(new Date());
		menuGroup.setDeletorId(user.getId());
		menuGroup.setStatus(MenuGroupStatus.DELETED.getValue());

		menuGroupMapper.updateByPrimaryKey(menuGroup);
		/**
		 * TODO synchronized weixin
		 */


		if (menuGroup.getMenuRuleId() == null) {
            WechatClientDelegate.deleteMenu(wechatId);
		} else {
			if (StringUtils.isNotBlank(menuGroup.getWxMenuId())) {
                WechatClientDelegate.deleteConditionalMenu(wechatId, menuGroup.getWxMenuId());
			}
		}

		return menuGroup;
	}

	@Override
	public synchronized void pushMenuGroupToWx(Integer wechatId,
			Integer menuGroupId) throws WechatException {
		log.info("wechatId {} menugroup start to push wx .", wechatId);
		notBlank(menuGroupId, Message.MENU_GROUP_ID_NOT_BLANK);
		MenuGroup menuGroup = getMenuGroup(wechatId, menuGroupId);
		notBlank(menuGroup, Message.MENU_GROUP_NOT_EXIST);
		if (menuGroup.getMenuRuleId() != null) {
			MenuGroup defaultMenuGroup = menuGroupMapper
					.getDefaultMenuGroup(wechatId);
			if (defaultMenuGroup == null) {
				log.warn("wechatId {} have no default menugroup", wechatId);
				throw new WechatException(Message.MENU_GROUP_DEFAULT_NOT_BLANK);
			}
			if (defaultMenuGroup.getPushAt() == null) {
				log.warn("wechatId {} default menugroup not push to wx",
						wechatId);
				throw new WechatException(
						Message.MENU_GROUP_DEFAULT_NOT_EXIST_IN_WEIXIN);
			}
		}

		Date current = new Date();

        if (menuGroup.getMenuRuleId() == null) {
            menuGroup = menuGroupMapper.getDefaultMenuGroup(wechatId);
        }
        createMenu(menuGroup, current, wechatId, true);
	}

	private void createMenu(MenuGroup menuGroup, Date current, Integer wechatId, boolean deleteConditionalMenu) {
        if (StringUtils.isNotBlank(menuGroup.getWxMenuId()) && deleteConditionalMenu) {
            WechatClientDelegate.deleteConditionalMenu(wechatId, menuGroup.getWxMenuId());
        }
		if (menuGroup.getMenuRuleId() != null) {
            //创建个性化菜单
            MenuRule menuRule = menuRuleMapper.selectByPrimaryKey(menuGroup.getMenuRuleId());
            WxMenuMatchrule matchRule = getMatchRule(menuRule);
            WxMenuGroup wxMenuGroup = WechatClientDelegate.addConditional(wechatId, getWeiXinButton(menuGroup), matchRule);
            menuGroup.setWxMenuId(wxMenuGroup.getMenuId());
		} else {
            //创建自定义菜单
            WxResponse wxResponse = WechatClientDelegate.createMenu(wechatId, getWeiXinButton(menuGroup));
        }
		menuGroup.setPushAt(current);
		menuGroup.setModifyAt(current);
		menuGroupMapper.updateByPrimaryKeySelective(menuGroup);
	}

	private WxMenuMatchrule getMatchRule(MenuRule menuRule) {
		if (menuRule == null) {
			return null;
		}
		AreaInfo provinceAreaInfo = null;
		if (menuRule.getProvince() != null) {
			provinceAreaInfo = areaInfoMapper.selectByPrimaryKey(menuRule
					.getProvince());
			notBlank(provinceAreaInfo, Message.AREA_PROVINCE_NOT_EXIST);
		}
		AreaInfo cityAreaInfo = null;
		if (menuRule.getCity() != null) {
			cityAreaInfo = areaInfoMapper
					.selectByPrimaryKey(menuRule.getCity());
			notBlank(cityAreaInfo, Message.AREA_CITY_NOT_EXIST);
		}
		AreaInfo countryAreaInfo = null;
		if (menuRule.getCountry() != null) {
			countryAreaInfo = areaInfoMapper.selectByPrimaryKey(menuRule
					.getCountry());
			notBlank(countryAreaInfo, Message.AREA_COUNTRY_NOT_EXIST);
		}

        WxMenuMatchrule matchRule = new WxMenuMatchrule();
		if (provinceAreaInfo != null) {
			matchRule.setProvince(provinceAreaInfo.getcName());
		}
		if (cityAreaInfo != null) {
			matchRule.setCity(cityAreaInfo.getcName());
		}
		if (countryAreaInfo != null) {
			matchRule.setCountry(countryAreaInfo.getcName());
		}

		matchRule.setClientPlatformType(menuRule.getClientPlatformType());
		Language language = Language.getByValue(menuRule.getLanguage());
		matchRule.setLanguage(language != null ? language.name() : null);
		Sex sex = Sex.getByValue(menuRule.getSex());
		matchRule.setSex(sex != null ? sex.getValue() + "" : null);
		matchRule.setTagId(menuRule.getTagId()+"");
		return matchRule;
	}

	private List<WxMenu> getWeiXinButton(MenuGroup menuGroup) {
		Page<MenuDto> menus = menuGroupMapper.getRootMenus(menuGroup.getId());
		List<MenuDto> menuDtos = null;
		List<WxMenu> weixinButtons = new ArrayList<WxMenu>();
		List<WxMenu> subWxMenus = null;
		WxMenu weixinButton = null;
		WxMenu subWxMenu = null;
		for (MenuDto menuDto : menus) {
			weixinButton = new WxMenu();
			MenuType menuType = MenuType.getByValue(menuDto.getType());
			weixinButton.setType(menuType != null ? menuType.name()
					.toLowerCase() : null);
			if (menuType == MenuType.CLICK) {
				weixinButton.setKey(menuDto.getId().toString());
			} else {
				weixinButton.setUrl(menuDto.getUrl());
			}
			weixinButton.setName(menuDto.getName());
			menuDtos = menuDto.getChildren();
			if (menuDtos != null && !menuDtos.isEmpty()) {
				subWxMenus = new ArrayList<WxMenu>();
				for (MenuDto child : menuDtos) {
					subWxMenu = new WxMenu();
					menuType = MenuType.getByValue(child.getType());
					subWxMenu.setType(menuType != null ? menuType.name()
							.toLowerCase() : null);
					if (menuType == MenuType.CLICK) {
						subWxMenu.setKey(child.getId().toString());
					} else {
						subWxMenu.setUrl(child.getUrl());
					}
					subWxMenu.setName(child.getName());
					subWxMenus.add(subWxMenu);
				}
				if (!subWxMenus.isEmpty()) {
					weixinButton.setSubButton(subWxMenus);
				}
			}
			weixinButtons.add(weixinButton);
		}
		log.info("weixinButtons : " + JSONObject.toJSONString(weixinButtons));
		return weixinButtons;
	}

	@Override
	public List<ReportMenuGroupDto> menuGroupList(Integer wechatId)
			throws WechatException {
		// TODO Auto-generated method stub
		return menuGroupMapper.reportMenuGroupList(wechatId);
	}
}
