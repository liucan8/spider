package com.lc.spider.controller;

import com.lc.spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liucan
 * @date 2020/4/20 10:26
 */
@RestController
public class SpiderController {
    @Autowired
    private SpiderService spiderService;

    @GetMapping("/spider")
    public void spider() {
        spiderService.analyze();
    }

}
