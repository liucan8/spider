package com.lc.spider.controller;

import java.util.Date;

import com.lc.spider.model.ThreeD;
import com.lc.spider.service.ThreeDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private ThreeDService threeDService;

    @PostMapping
    public void test() {
        ThreeD threeD = ThreeD.builder().period("1").number("537")
        .firstNo(5).secondNo(3).thirdNo(7)
        .createTime(new Date()).updateTime(new Date()).build();

        threeDService.save(threeD);
    }

    @PostMapping("/split")
    public void splitNumber() {
        threeDService.splitNumber();
    }

    @PostMapping("/syncLatest")
    public void syncLatest() {
        threeDService.syncLatestNumber();
    }

    @Value("${testUrl}")
    private String testUrl;

    @PostMapping("/testValue")
    public void testValue() {
        System.out.println("==================testUrl:"+testUrl);
    }
}
