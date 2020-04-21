package com.lc.spider.modules.hosjoy.controller;

import com.lc.spider.modules.hosjoy.service.LoginService;
import com.lc.spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liucan
 * @date 2020/4/20 10:26
 */
@RestController
public class HosjoyController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public void spider() {
        loginService.login();
    }

}
