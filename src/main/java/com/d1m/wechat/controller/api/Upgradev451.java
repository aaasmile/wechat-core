package com.d1m.wechat.controller.api;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.service.Upgradev451Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/upgradev451")
public class Upgradev451 extends BaseController {

    @Autowired
    private Upgradev451Service upgradev451Service;

    @RequestMapping("move2dcrm")
    public void move2dcrm() {
        upgradev451Service.move2dcrm();
    }
    @RequestMapping("upgradeMenu")
    public void upgradeMenu() {
        upgradev451Service.upgradeMenu();
    }
    @RequestMapping("upgradeActionEngine")
    public void upgradeActionEngine() {
        upgradev451Service.upgradeActionEngine();
    }
}
