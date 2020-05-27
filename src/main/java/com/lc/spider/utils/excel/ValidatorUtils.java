package com.lc.spider.utils.excel;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.alibaba.excel.exception.ExcelAnalysisException;

/**
 * hibernate入参校验工具
 * @author liucan
 * @date 2020/1/19 15:38
 */
public class ValidatorUtils {
    /**
     * 验证器
     */
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     *  验证方法
     * @param object 被校验的对象
     */
    public static void validate(Object object,Integer rowIndex) {
        //使用默认的组
        Class[] groups = {Default.class};
        // 用验证器执行验证，返回一个违反约束的set集合
        Set<ConstraintViolation<Object>> violationSet = validator.validate(object, groups);
        // 判断是否为空，空：说明验证通过，否则就验证失败
        if(!violationSet.isEmpty()) {
            // 获取第一个验证失败的属性
            ConstraintViolation<Object> violation = violationSet.iterator().next();
            // 抛出自定义异常
            throw new ExcelAnalysisException("第"+rowIndex+"行:"+violation.getMessage());
        }

    }

    /**
     *  验证方法
     * @param object 被校验的对象
     * @param groups 被校验的组
     */
    public static void validate(Object object,Integer rowIndex, Class<?>... groups) {
        // 用验证器执行验证，返回一个违反约束的set集合
        Set<ConstraintViolation<Object>> violationSet = validator.validate(object, groups);
        // 判断是否为空，空：说明验证通过，否则就验证失败
        if(!violationSet.isEmpty()) {
            // 获取第一个验证失败的属性
            ConstraintViolation<Object> violation = violationSet.iterator().next();
            // 抛出自定义异常
            throw new ExcelAnalysisException("第"+rowIndex+"行:"+violation.getMessage());
        }

    }
}
