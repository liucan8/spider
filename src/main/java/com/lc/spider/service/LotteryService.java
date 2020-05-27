package com.lc.spider.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.lc.spider.model.LotteryExcelModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * @author liucan
 * @date 2020/5/27 10:59
 */
@Service
public class LotteryService {

    public List<LotteryExcelModel> analyzeLottery(String url) {
        List<LotteryExcelModel> lotteryList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements linkElements = document.getElementsByClass("iSelectList");
            for (Element linkElement : linkElements) {
                Elements linkTags = linkElement.getElementsByTag("a");
                for (Element linkTag : linkTags) {
                    String period = linkTag.text();
                    String linkUrl = linkTag.attr("href");
                    String number = getPeriodBallNumber(linkUrl);
                    lotteryList.add(new LotteryExcelModel(period,number));
                    System.out.println("第: "+period+"期:"+number);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lotteryList;
    }

    private String getPeriodBallNumber(String linkUrl) {
        //StringBuilder sb = new StringBuilder();
        try {
            Document document = Jsoup.connect(linkUrl).get();
            Elements linkElements = document.getElementsByClass("ball_box01");

            for (Element linkElement : linkElements) {
                Elements linkTags = linkElement.getElementsByTag("li");
                return linkTags.text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
