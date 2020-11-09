import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.ArrayList;

class Excel {

    private Workbook workbook;
    private Sheet sheet;
    private CellStyle headerStyle;
    private int rowNumber;


    Excel(String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)12);
        headerFont.setColor(IndexedColors.BLACK.index);
        headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
    }

    void export(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    void writeHeaderRow(String[] columnHeadings) {
        Row headerRow = sheet.createRow(rowNumber++);
        for(int i = 0;i < columnHeadings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeadings[i]);
            cell.setCellStyle(headerStyle);
        }
        sheet.createFreezePane(0, 1);
        for(int i = 0; i < columnHeadings.length; i++)
            sheet.autoSizeColumn(i);
    }

    void writeRow(ArrayList<String> data) {
        Row row = sheet.createRow(rowNumber++);
        for (int i = 0; i < data.size(); i++)
            row.createCell(i).setCellValue(data.get(i));
        for(int i = 0; i < data.size(); i++)
            sheet.autoSizeColumn(i);
    }

    void writeRow(String text, int cellNumber) {
        Row row = sheet.createRow(rowNumber++);
        row.createCell(cellNumber).setCellValue(text);
        sheet.autoSizeColumn(cellNumber);
    }
}
