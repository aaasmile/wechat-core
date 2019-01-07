package com.d1m.wechat.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.model.Menu;
import com.d1m.wechat.service.MenuService;
import com.d1m.wechat.util.Message;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuApiController extends BaseController {

    private Logger log = LoggerFactory.getLogger(MenuApiController.class);
    @Autowired
    private MenuService menuService;
    @Resource
    TenantHelper tenantHelper;

    @RequestMapping("deleteWechatByMenuKey")
    public JSONObject deleteWechatByMenuKey(Integer menuKey, Integer wechatId) {
       try {
           log.info("deleteWechatByMenuKey...menuKey..." + menuKey + "...wechatId..." + wechatId);
           // 多数据源支持,临时方案
           String domain = tenantHelper.getTenantByWechatId(wechatId);
           if (domain != null){
               TenantContext.setCurrentTenant(domain);
               Log.info("mq domain: "+domain);
           }
           final Example example = new Example(Menu.class);
           example.createCriteria().andEqualTo("menuKey", menuKey).andEqualTo("url", 21);
           this.updateByMenuKey(example);
           return representation(Message.SUCCESS);
       } catch (Exception e) {
           log.error(e.getMessage(), e);
           return representation(Message.SYSTEM_ERROR, e.getMessage());
       }
    }

    @RequestMapping("deleteDcrmByMenuKey")
    public JSONObject deleteDcrmByMenuKey(Integer menuKey, Integer wechatId) {
        try {
            String domain = tenantHelper.getTenantByWechatId(wechatId);
            if (domain != null){
                TenantContext.setCurrentTenant(domain);
                Log.info("mq domain: "+domain);
            }
            log.info("deleteDcrmByMenuKey...menuKey..." + menuKey + "...wechatId..." + wechatId);
            final Example example = new Example(Menu.class);
            example.createCriteria().andEqualTo("menuKey", menuKey).andEqualTo("url", 22);
            this.updateByMenuKey(example);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.SYSTEM_ERROR, e.getMessage());
        }
    }

    private void updateByMenuKey(Example example) throws Exception {
        List<Menu> menuList = menuService.selectByExample(example);
        if(menuList == null || menuList.isEmpty()) {
            throw  new Exception("menuKey can not find from menu");
        }
        for( int i = 0; i < menuList.size(); i++) {
            Menu menuR = menuList.get(i);
            menuR.setUrl(null);
            menuR.setMenuKey(null);
            menuService.updateAll(menuR);
        }
    }
}
