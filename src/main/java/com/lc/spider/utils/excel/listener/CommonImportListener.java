package com.lc.spider.utils.excel.listener;

import java.lang.reflect.Field;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.lc.spider.utils.excel.ValidatorUtils;
import com.lc.spider.utils.excel.validator.DuplicateValidator;
import com.lc.spider.utils.excel.validator.NotDuplicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Created by liucan on 2019/12/15.
 */
@Slf4j
public class CommonImportListener extends AnalysisEventListener {
    //重复数据校验器
    private DuplicateValidator duplicateValidator;
    //导入数据属性列表
    private Field[] fields;

    public CommonImportListener(Class tClass) {
        duplicateValidator = new DuplicateValidator();
        fields = tClass.getDeclaredFields();
    }

        /**
         * 每解析一行，回调该方法
         * @param data
         * @param context
         */
        @Override
        public void invoke(Object data, AnalysisContext context) {
            Integer rowIndex = context.readRowHolder().getRowIndex()+1;
            //重复数据校验
            for (Field field : fields) {
                //不存在这个注解的直接跳过
                if (!field.isAnnotationPresent(NotDuplicate.class)) {
                    continue;
                }
                String name = field.getName();
                String value = null;
                try {
                    field.setAccessible(true);
                    value = (String) field.get(data);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    log.error("导入listener异常 ex={}", ExceptionUtils.getStackTrace(e));
                    throw new RuntimeException("导入异常");
                }
                if (StringUtils.isNotEmpty(value)) {
                    String message = duplicateValidator.validate(field, name, value);
                    if(StringUtils.isNotEmpty(message)) {
                        String errMessage = "第"+rowIndex+"行:"+message;
                        throw new RuntimeException(errMessage);
                    }

                }
            }
            //校验入参
            ValidatorUtils.validate(data,rowIndex);
        }

        /**
         * 出现异常回调
         * @param exception
         * @param context
         * @throws Exception
         */
        @Override
        public void onException(Exception exception, AnalysisContext context) throws Exception {
            // ExcelDataConvertException:当数据转换异常的时候，会抛出该异常，此处可以得知第几行，第几列的数据
            if (exception instanceof ExcelDataConvertException) {
                Integer columnIndex = ((ExcelDataConvertException) exception).getColumnIndex() + 1;
                Integer rowIndex = ((ExcelDataConvertException) exception).getRowIndex() + 1;
                String message = "第" + rowIndex + "行，第" + columnIndex + "列" + "数据格式有误，请核实";
                throw new RuntimeException(message);
            } else {
                super.onException(exception, context);
            }
        }

        /**
         * 解析完全部回调
         * @param context
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {

        }

}
