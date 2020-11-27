package com.lc.spider.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lc.spider.model.dto.LotteryExcelModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value = "t_three_D")
public class ThreeD {
    private Long id;

    private String period;

    private String number;

    private Integer firstNo;

    private Integer secondNo;

    private Integer thirdNo;

    private Integer sum;

    private Integer sameNums;

    private Boolean isOddFirst;

    private Boolean isOddSecond;

    private Boolean isOddThird;

    private Integer span;

    private Date createTime;

    private Date updateTime;

    public static ThreeD from(LotteryExcelModel model) {
        ThreeD.ThreeDBuilder builder = ThreeD.builder();
        return builder.number(model.getNumber()).period(model.getPeriod())
                .createTime(new Date()).updateTime(new Date()).build();

    }

    public static List<ThreeD> fromList(List<LotteryExcelModel> models) {
        List<ThreeD> list = new ArrayList<>();
        models.forEach(model -> list.add(from(model)));
        return list;
    }
}
