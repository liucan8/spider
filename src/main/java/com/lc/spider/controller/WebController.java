package com.lc.spider.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lc.spider.model.LottData;
import com.lc.spider.model.LotteryExcelModel;
import com.lc.spider.model.Student;
import com.lc.spider.utils.excel.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/data")
    public String getData(Model model){
        List<Student> students = new ArrayList<>();

        Student student1 = new Student(1,"liucan");
        Student student2 = new Student(2,"liucan2");
        Student student3 = new Student(3,"liucan3");
        Student student4 = new Student(4,"liucan3");

        students.addAll(Arrays.asList(student1,student2,student3,student4));

        Map<String, List<Student>> map = new HashMap<>();
        map.put("students", students);

        model.addAllAttributes(map);
        return "index";
    }

    @RequestMapping("/result")
    public String pers(Model model){

        String[] keyList = {"1","2","3","4","5","6","7","8","9","10"};
        String[] valueList = {"100","200","300","400","500","600","700","800","900","1000"};

        Map map = new HashMap();
        map.put("keyList",keyList);
        map.put("valueList",valueList);

        Map result = new HashMap();
        result.put("data",map);

        model.addAllAttributes(result);

        return "statt";
    }

    @RequestMapping("/3d")
    public String threeD(Model model){
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        try {
            InputStream inputStream = new FileInputStream(new File("D://结果-sd.xlsx"));
            List<LotteryExcelModel> lists = ExcelUtil.importData(inputStream,LotteryExcelModel.class);

            List<LotteryExcelModel> list = lists.subList(0,50);

            list.forEach(lott -> {
                keys.add(lott.getPeriod());
                String number = lott.getNumber();
                if(StringUtils.isEmpty(number)) {
                    values.add(0);
                } else {
                    String[] nums = number.split(" ");
                    Integer sum = Integer.parseInt(nums[0])+Integer.parseInt(nums[1])+Integer.parseInt(nums[2]);
                    values.add(sum);
                }

            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map map = new HashMap();
        map.put("keyList",keys);
        map.put("valueList",values);

        Map result = new HashMap();
        result.put("data",map);

        model.addAllAttributes(result);

        return "statt";
    }
}
