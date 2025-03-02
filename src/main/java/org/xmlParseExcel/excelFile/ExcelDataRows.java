package org.xmlParseExcel.excelFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.xmlParseExcel.Constants;
import org.xmlParseExcel.SafTParserSalesInvoice;
import org.xmlParseExcel.salesInvoice.SalesInvoice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelDataRows {

    private static final Logger logger = LogManager.getLogger(SafTParserSalesInvoice.class);

    public static void dataRows(Sheet sheet, int rowIndex, SalesInvoice invoice, Workbook workbook) throws IOException {

            Row row = sheet.createRow(rowIndex);
            int colNum = 0;

            // Access and write specific fields of the SalesInvoice object
            row.createCell(colNum++).setCellValue(invoice.getInvoiceNo());
            row.createCell(colNum++).setCellValue(invoice.getInvoiceDate());
            row.createCell(colNum++).setCellValue(invoice.getInvoiceStatus());
            row.createCell(colNum++).setCellValue(invoice.getInvoiceType());
            row.createCell(colNum++).setCellValue(invoice.getSystemEntryDate());
            row.createCell(colNum++).setCellValue(invoice.getCustomerId());

            // Parse numbers as numbers
            createNumericCell(row, colNum++, invoice.getNetTotal());
            createNumericCell(row, colNum++, invoice.getTaxPayable());
            createNumericCell(row, colNum++, invoice.getGrossTotal());


            // Write the workbook to an Excel file
            FileOutputStream outputStream = new FileOutputStream(Constants.EXCEL_FILE_PATH);
            workbook.write(outputStream);
    }

    private static void createNumericCell(Row row, int colNum, String value) {
        Cell cell = row.createCell(colNum);
        try {
            double numericValue = Double.parseDouble(value);
            cell.setCellValue(numericValue);
        } catch (NumberFormatException e) {
            cell.setCellValue(value);
        }
    }
}