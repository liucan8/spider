package com.lc.spider.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lc.spider.model.LotteryExcelModel;
import com.lc.spider.service.LotteryService;
import com.lc.spider.service.SpiderService;
import com.lc.spider.utils.excel.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liucan
 * @date 2020/4/20 10:26
 */
@RestController
public class SpiderController {
    private static final String LOTTERY_URL = "https://kaijiang.500.com/shtml/dlt/20038.shtml";

    private static final String SSQ_URL = "https://kaijiang.500.com/ssq.shtml";

    private static final String SD_URL = "https://kaijiang.500.com/sd.shtml";

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/lottery")
    public void lottery(HttpServletResponse response) {
        //切换路径执行
        List<LotteryExcelModel> list =  lotteryService.analyzeLottery(SD_URL);
        ExcelUtil.exportData(response,"结果-sd",list,LotteryExcelModel.class);
    }

}
