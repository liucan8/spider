package com.lc.spider.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.lc.spider.LotteryThreeD;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author liucan
 * @date 2020/4/20 10:21
 */
@Service
public class SpiderService {

    private static final String LOTTO_URL = "http://www.17500.cn/3d/3dmoney.php";

    public void analyze() {

        int startPage = 1;

        String connectPage = "";

        String pageSuffix = "";

        List<LotteryThreeD> list = new ArrayList<>();

        Map<String,Integer> freqMap = new HashMap<>();

        while(true) {
            System.out.println("===============================================页数:"+startPage+"=============================================");
            try {
                if(!StringUtils.isEmpty(pageSuffix)) {
                    connectPage = LOTTO_URL + pageSuffix;
                } else {
                    connectPage = LOTTO_URL;
                }

                Document document = Jsoup.connect(connectPage).get();
                //处理数据
                Elements elements = document.select("tbody");
                elements.remove(0);

                for (Element element : elements) {
                    Elements trElements = element.select("tr");
                    for (Element trElement : trElements) {
                        Elements tdElements = trElement.select("td");
                        LotteryThreeD lotteryThreeD = new LotteryThreeD();
                        lotteryThreeD.setPeriod(tdElements.get(0).text());
                        lotteryThreeD.setDate(tdElements.get(1).text());
                        lotteryThreeD.setWeekDay(tdElements.get(2).text());
                        lotteryThreeD.setNumber(tdElements.get(3).text());

                        Element allAmountElement = tdElements.get(4);
                        if(allAmountElement.hasText()) {
                            String allAmount = allAmountElement.text().replaceAll(",","");
                            lotteryThreeD.setAllAmount(new BigDecimal(allAmount));
                        }

                        Element percentElement = tdElements.get(5);
                        if(percentElement.hasText()) {
                            String percent = percentElement.text().replaceAll("%","");
                            lotteryThreeD.setReturnPercent(new BigDecimal(percent));
                        }

                        Element singleCountElement = tdElements.get(6);
                        if(singleCountElement.hasText()) {
                            lotteryThreeD.setSingleCount(Integer.parseInt(singleCountElement.text()));
                        }

                        Element singleAmountElement = tdElements.get(7);
                        if(singleAmountElement.hasText()) {
                            String singleAmount = singleAmountElement.text().replaceAll(",","");
                            lotteryThreeD.setSingleAmount(Integer.parseInt(singleAmount));
                        }

                        Element threeCountElement = tdElements.get(8);
                        if(threeCountElement.hasText()) {
                            lotteryThreeD.setThreeCount(Integer.parseInt(threeCountElement.text()));
                        }

                        Element threeAmountElement = tdElements.get(9);
                        if(threeAmountElement.hasText()) {
                            String threeAmount = threeAmountElement.text().replaceAll(",","");
                            lotteryThreeD.setThreeAmount(Integer.parseInt(threeAmount));
                        }

                        Element sixCountElement = tdElements.get(10);
                        if(sixCountElement.hasText()) {
                            lotteryThreeD.setSixCount(Integer.parseInt(sixCountElement.text()));
                        }

                        Element sixAmountElement = tdElements.get(11);
                        if(sixAmountElement.hasText()) {
                            String sixAmount = sixAmountElement.text().replaceAll(",","");
                            lotteryThreeD.setSixAmount(Integer.parseInt(sixAmount));
                        }

                        System.out.println(lotteryThreeD);
                        System.out.println();

                        //统计结果
                        addToMap(lotteryThreeD.getNumber(),freqMap);
                        list.add(lotteryThreeD);
                    }

                }
                //下一页
                Elements nextPageElements = document.getElementsContainingOwnText("[下一页]");

                //页面没有下一页则退出
                if(CollectionUtils.isEmpty(nextPageElements)) {
                    break;
                }
                //页面有两个,取第一个
                Element nextPageElement = nextPageElements.get(0);
                pageSuffix = nextPageElement.attr("href");

            } catch (IOException e) {
                e.printStackTrace();
            }
            startPage ++;
        }

        freqMap.forEach((number,freq) -> {
            System.out.println(number+" : "+freq);
        });
        System.out.println("-----------------------------------------");
        for(int i=0;i<50;i++) {
            System.out.println("期数:"+list.get(i).getDate()+" 号:"+list.get(i).getNumber()+"  出现次数:"+freqMap.get(list.get(i).getNumber()));
        }
        System.out.println("-----------------------------------------");
    }

    private void addToMap(String number,Map<String,Integer> freqMap) {
        int nums = 0;
        if(freqMap.containsKey(number)) {
            nums = freqMap.get(number);
        }
        freqMap.put(number,nums+1);
    }
}
