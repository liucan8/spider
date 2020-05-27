package com.lc.spider.utils.excel.style;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by liucan on 2019/12/8.
 */
public class CommonStyle implements CellWriteHandler, NotRepeatExecutor {

    private ExcelWriter excelWriter;

    private boolean hadSetTitleColumnStyle;

    private boolean hadSetHeadColumnStyle;

    private Map<String, CellStyle> styleMap;

    public CommonStyle(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

    @Override
    public String uniqueValue() {
        return "commonStyle";
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder tableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        int rowIndex = cell.getRowIndex();
        if(rowIndex < 2 && !isHead) {
            throw new RuntimeException("导出Excel必须包含标题和表头");
        }
        //初始化样式
        if(styleMap == null) {
            Workbook wb = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
            styleMap = createStyles(wb);
        }
        //设置头部样式
        if(isHead != null&&isHead.booleanValue()) {
            Row row = cell.getRow();
            //设置标题样式
            if(!hadSetTitleColumnStyle && rowIndex == 0) {
                cell.setCellStyle(styleMap.get("title"));
                row.setHeightInPoints(30);
                hadSetTitleColumnStyle = true;
            }
            //设置表头样式
            if(rowIndex == 1) {
                cell.setCellStyle(styleMap.get("header"));
                int defaulColumnWith = cell.getSheet().getColumnWidth(cell.getColumnIndex());
                cell.getSheet().setColumnWidth(cell.getColumnIndex(),defaulColumnWith < 3500 ? 3500 : defaulColumnWith);

                if(!hadSetHeadColumnStyle) {
                    row.setHeightInPoints(16);
                    hadSetHeadColumnStyle = true;
                }

            }
        }
        //设置数据样式
        if(isHead != null && !isHead.booleanValue()) {
            cell.setCellStyle(styleMap.get("data"));
        }
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

}
