package com.d1m.wechat.Handler;

import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.model.Menu;
import com.d1m.wechat.service.MenuService;
import com.d1m.wechat.util.Message;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class MenuHandler {

    private Logger log = LoggerFactory.getLogger(MenuHandler.class);
    @Resource
    TenantHelper tenantHelper;
    @Autowired
    private MenuService menuService;

//    @RabbitListener(queues = "${dcrm.wechat-core.menu.deleteWechatByMenuKey}")
    public void deleteWechatByMenuKey(Map<String,Object> message) {
        try {
            Integer menuKey = (Integer) message.get("menuKey");
            Integer wechatId = (Integer) message.get("wechatId");
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

//    @RabbitListener(queues = "${dcrm.wechat-core.menu.deleteDcrmByMenuKey}")
    public void deleteDcrmByMenuKey(Map<String,Object> message) {
        try {
            Integer menuKey = (Integer) message.get("menuKey");
            Integer wechatId = (Integer) message.get("wechatId");
            String domain = tenantHelper.getTenantByWechatId(wechatId);
            if (domain != null){
                TenantContext.setCurrentTenant(domain);
                Log.info("mq domain: "+domain);
            }
            log.info("deleteDcrmByMenuKey...menuKey..." + menuKey + "...wechatId..." + wechatId);
            final Example example = new Example(Menu.class);
            example.createCriteria().andEqualTo("menuKey", menuKey).andEqualTo("url", 22);
            this.updateByMenuKey(example);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
