package com.lc.spider.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liucan
 * @date 2020/5/27 11:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotteryExcelModel {
    @ExcelProperty(index = 0,value = {"LOTTERY", "期数"})
    private String period;
    @ExcelProperty(index = 1,value = {"LOTTERY", "结果"})
    private String number;
}
