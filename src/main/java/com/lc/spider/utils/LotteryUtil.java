package com.lc.spider.utils;

import java.util.Arrays;

public class LotteryUtil {

    public static boolean isOdd(Integer num) {
        return num % 2 == 1;
    }

    public static int getSameNums(int firstNum, int secondNum, int thirdNum) {
        if(firstNum != secondNum && firstNum != thirdNum && secondNum != thirdNum) {
            return 1;
        }
        if(firstNum == secondNum &&  secondNum == thirdNum) {
            return 3;
        }
        return 2;
    }

    public static int getSpan(int firstNum, int secondNum, int thirdNum) {
        int[] nums = {firstNum, secondNum, thirdNum};
        Arrays.sort(nums);
        return nums[2] - nums[0];
    }
}
