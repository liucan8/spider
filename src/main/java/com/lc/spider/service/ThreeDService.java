package com.lc.spider.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lc.spider.mapper.ThreeDMapper;
import com.lc.spider.model.ThreeD;
import com.lc.spider.utils.LotteryUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreeDService {
    @Resource
    private ThreeDMapper threeDMapper;

    public void save(ThreeD threeD) {
        threeDMapper.insert(threeD);
    }

    public void saveBatch(List<ThreeD> dataList) {
        dataList.forEach(data -> save(data));
    }

    public void splitNumber() {
        List<ThreeD> allData = threeDMapper.selectList(new LambdaQueryWrapper());

        allData.forEach(data -> {
            String[] nums = data.getNumber().split(" ");
            data.setFirstNo(Integer.parseInt(nums[0]));
            data.setSecondNo(Integer.parseInt(nums[1]));
            data.setThirdNo(Integer.parseInt(nums[2]));

            threeDMapper.updateById(data);
        });
    }

    private void saveLastestNumber(String period, String number) {
        String[] nums = number.split(" ");
        Integer firstNo = Integer.parseInt(nums[0]);
        Integer secondNo = Integer.parseInt(nums[1]);
        Integer thirdNo = Integer.parseInt(nums[2]);

        ThreeD threeD = ThreeD.builder().period(period)
                .number(number).firstNo(firstNo)
                .secondNo(secondNo).thirdNo(thirdNo)
                .sum(firstNo+secondNo+thirdNo)
                .isOddFirst(LotteryUtil.isOdd(firstNo))
                .isOddSecond(LotteryUtil.isOdd(secondNo))
                .isOddSecond(LotteryUtil.isOdd(thirdNo))
                .sameNums(LotteryUtil.getSameNums(firstNo,secondNo,thirdNo))
                .span(LotteryUtil.getSpan(firstNo,secondNo,thirdNo)).build();

        threeDMapper.insert(threeD);

    }

    private static final String SD_URL = "https://kaijiang.500.com/sd.shtml";

    public void syncLatestNumber() {
        try {
            Document document = Jsoup.connect(SD_URL).get();
            Elements linkElements = document.getElementsByClass("iSelectList");
            for (Element linkElement : linkElements) {
                Elements linkTags = linkElement.getElementsByTag("a");
                for (Element linkTag : linkTags) {
                    String period = linkTag.text();
                    String linkUrl = linkTag.attr("href");

                    Document documentNumber = Jsoup.connect(linkUrl).get();
                    Elements linkElementsNumber = documentNumber.getElementsByClass("ball_box01");

                    for (Element linkElementNumber : linkElementsNumber) {
                        Elements linkTagsNumber = linkElementNumber.getElementsByTag("li");
                        String number = linkTagsNumber.text();
                        System.out.println("=============================period:"+period+" number:"+number);
                        saveLastestNumber(period, number);
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
