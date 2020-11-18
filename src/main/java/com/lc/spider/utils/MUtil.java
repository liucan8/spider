package com.lc.spider.utils;

import java.math.BigDecimal;

public class MUtil {
    public static BigDecimal getAResult(int pay) {
        BigDecimal payB = new BigDecimal(pay);

        BigDecimal jx = payB.multiply(new BigDecimal("0.2"));

        return getAPay(jx,"1.2");
    }

    public static BigDecimal getBPlusResult(int pay) {
        BigDecimal payB = new BigDecimal(pay);

        BigDecimal jx = payB.multiply(new BigDecimal("0.2"));

        return getAPay(jx,"1.1");
    }

    public static BigDecimal getBResult(int pay) {
        BigDecimal payB = new BigDecimal(pay);

        BigDecimal jx = payB.multiply(new BigDecimal("0.2"));

        return getAPay(jx,"0.9");
    }

    public static BigDecimal getCResult(int pay) {
        BigDecimal payB = new BigDecimal(pay);

        BigDecimal jx = payB.multiply(new BigDecimal("0.2"));

        return getAPay(jx,"0.5");
    }

    public static BigDecimal getAPay(BigDecimal jx,String rate) {
        BigDecimal companyRate = new BigDecimal("0.9");
        BigDecimal yangRate = new BigDecimal("1.1");
        BigDecimal myRate = new BigDecimal(rate);

        BigDecimal oneRate = new BigDecimal("0.3");
        BigDecimal twoRate = new BigDecimal("0.7");

        BigDecimal part1 = jx.multiply(oneRate);
        System.out.println("part1: "+part1);

        BigDecimal part11 = part1.multiply(oneRate);
        System.out.println("part11: "+part11);
        BigDecimal part12 = part1.multiply(twoRate);
        System.out.println("part12: "+part12);

        BigDecimal part2 = jx.multiply(twoRate);
        System.out.println("part2: "+part2);

        System.out.println("1: "+part11.multiply(companyRate));
        System.out.println("2: "+part12.multiply(yangRate));
        System.out.println("3: "+part2.multiply(myRate));

        BigDecimal result = part11.multiply(companyRate).add(part12.multiply(yangRate)).add(part2.multiply(myRate));
        System.out.println(result.toPlainString());
        System.out.println(result.subtract(jx).multiply(new BigDecimal("3")).toPlainString());
        return result.subtract(jx).multiply(new BigDecimal("3"));
    }
}
