package com.lc.spider.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    //@Scheduled(cron = "*/1 * * * * ?")
    public void testSedule() {
        System.out.println("=========================");
    }

}
