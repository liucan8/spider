package com.lc.spider.service;

import java.util.List;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lc.spider.mapper.ThreeDMapper;
import com.lc.spider.model.ThreeD;
import lombok.extern.slf4j.Slf4j;
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
}
