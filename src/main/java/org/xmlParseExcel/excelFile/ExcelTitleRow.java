package org.xmlParseExcel.excelFile;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelTitleRow {

    public static void createTitleRow(Sheet sheet, int rowNum) {
        Row row = sheet.createRow(rowNum);
        int colNum = 0;

        row.createCell(colNum++).setCellValue("InvoiceNo");
        row.createCell(colNum++).setCellValue("InvoiceDate");
        row.createCell(colNum++).setCellValue("InvoiceStatus");
        row.createCell(colNum++).setCellValue("InvoiceType");
        row.createCell(colNum++).setCellValue("SystemEntryDate");
        row.createCell(colNum++).setCellValue("CustomerID");
        row.createCell(colNum++).setCellValue("NetTotal");
        row.createCell(colNum++).setCellValue("TaxPayable");
        row.createCell(colNum++).setCellValue("GrossTotal");
    }
}

