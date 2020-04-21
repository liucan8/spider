package com.lc.spider;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.Data;

/**
 * @author liucan
 * @date 2020/4/20 14:07
 */
@Data
public class LotteryThreeD {
    //期数
    private String period;
    //日期
    private String date;
    //周几
    private String weekDay;
    //号码
    private String number;
    //本期总金额
    private BigDecimal allAmount;

    private BigDecimal returnPercent;

    private Integer singleCount;

    private Integer singleAmount;

    private Integer threeCount;

    private Integer threeAmount;

    private Integer sixCount;

    private Integer sixAmount;


    public Integer getThreeCount() {
        return Objects.isNull(threeCount)?-1:threeCount;
    }

    public Integer getThreeAmount() {
        return Objects.isNull(threeAmount)?-1:threeAmount;
    }

    public Integer getSixCount() {
        return Objects.isNull(sixCount)?-1:sixCount;
    }

    public Integer getSixAmount() {
        return Objects.isNull(sixAmount)?-1:sixAmount;
    }
}
