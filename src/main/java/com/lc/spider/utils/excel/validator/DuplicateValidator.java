package com.lc.spider.utils.excel.validator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 加@NotDuplicate注解的字段值不可重复校验
 */
public class DuplicateValidator {

    private Map<String, Set<String>> fieldMap = new HashMap<>();

    /**
     * 校验加了NotDuplicate注解的字段是否重复
     * @param field 属性
     * @param name 名称
     * @param value 值
     * @return 如果重复了,返回注解错误信息
     */
    public String validate(Field field, String name, String value) {
        if (field.isAnnotationPresent(NotDuplicate.class)) {
            if (fieldMap.containsKey(name)) {
                if (fieldMap.get(name).contains(value)) {
                    NotDuplicate notDuplicate = field.getAnnotation(NotDuplicate.class);
                    return notDuplicate.message();
                } else {
                    fieldMap.get(name).add(value);
                }
            } else {
                Set<String> values = new HashSet<>();
                values.add(value);
                fieldMap.put(name, values);
            }
        }
        return "";
    }

}
