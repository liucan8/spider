package com.lc.spider.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.lc.spider.utils.excel.converter.StringConverter;
import com.lc.spider.utils.excel.listener.CommonImportListener;
import com.lc.spider.utils.excel.style.CommonStyle;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel导入导出工具类
 * 样式、校验均默认
 * Created by liucan on 2019/12/15.
 */
@Slf4j
public class ExcelUtil {
    private final static int HEAD_ROW_MIN = 0;
    private final static int SHEET_MIN = 0;

    /**
     * 导出数据
     *
     * @param response response
     * @param fileName 导出文件名称
     * @param data     导出数据集
     * @param clazz    class
     */
    public static void exportData(HttpServletResponse response, String fileName, List data, Class clazz) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String excelName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + excelName + ".xlsx");
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build();

            ExcelWriterSheetBuilder writerSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
            writerSheetBuilder.sheetName(fileName).sheetNo(0)
                    .registerWriteHandler(new CommonStyle(excelWriter));
            writerSheetBuilder.doWrite(data);
        } catch (Exception e) {
            log.error("导出异常 ex={}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("导出异常");
        }
    }

    /**
     * 导入数据
     *
     * @param file  导入文件
     * @param clazz class
     * @param <T>   T
     * @return 成功导入的数据
     */
    public static <T> List<T> importData(MultipartFile file, Class clazz) {
        try {
            return importData(file.getInputStream(), clazz);
        } catch (ExcelAnalysisException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        } catch (IOException e) {
            log.error("导入异常 ex={}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("导入异常");
        }
    }

    /**
     * 导入数据
     *
     * @param inputStream 输入流
     * @param clazz       class
     * @param <T>         返回对象
     * @return 返回成功导出的数据
     */
    public static <T> List<T> importData(InputStream inputStream, Class clazz) {
        return EasyExcel.read(inputStream)
                .head(clazz)
                .registerConverter(new StringConverter())
                .registerReadListener(new CommonImportListener(clazz))
                // 设置sheet,默认读取第一个
                .sheet()
                // 设置表头所在行数
                .headRowNumber(2)
                .doReadSync();
    }

    /**
     * 导入数据
     *
     * @param importData 导入数据入参对象
     * @param <T>        返回对象
     * @return 返回成功导出的数据
     */
    public static <T> List<T> importData(ImportData importData) {
        checkParameters(importData);
        List<ReadListener> listeners = importData.getListeners();
        ExcelReaderSheetBuilder excelReaderSheetBuilder = EasyExcel.read(importData.getInputStream())
                .head(importData.getClazz())
                .autoCloseStream(true)
                .autoTrim(true)
                .registerReadListener(new CommonImportListener(importData.getClazz()))
                .sheet(importData.getSheetNumber())
                // 设置表头所在行数
                .headRowNumber(importData.getHeadRowNumber());

        if (CollectionUtils.isNotEmpty(listeners)) {
            listeners.forEach(excelReaderSheetBuilder::registerReadListener);
        }
        return excelReaderSheetBuilder.doReadSync();
    }

    private static void checkParameters(ImportData importData) {
        Objects.requireNonNull(importData.getInputStream());
        Objects.requireNonNull(importData.getClazz());
        if (importData.getHeadRowNumber() < HEAD_ROW_MIN) {
            throw new RuntimeException("headRowNumber最小为0");
        }
        if (importData.getSheetNumber() < SHEET_MIN) {
            throw new RuntimeException("sheetNumber最小为0");
        }
    }

    @Data
    @Builder
    public static class ImportData {
        /**
         * 文件输入流
         */
        @NotNull
        private InputStream inputStream;
        /**
         * 导入对象的Class类别
         */
        @NotNull
        private Class clazz;

        /**
         * 监听器，如果需要在读取每一行数据的时候做业务处理可以自定义实现
         * 详细实现可以参照@See：https://www.jianshu.com/p/8f3defdc76d4  监听器实现
         */
        private List<ReadListener> listeners;

        /**
         * 设置表头行数，从第几行开始读取，默认为2
         * 下标索引从0开始
         */
        @Min(HEAD_ROW_MIN)
        private int headRowNumber = 2;

        /**
         * 设置读取sheet索引，默认为0
         * 下标索引从0开始
         */
        @Min(SHEET_MIN)
        private int sheetNumber = 0;
    }
}
