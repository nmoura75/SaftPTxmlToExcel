package org.xmlParseExcel.excelFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.xmlParseExcel.Constants;
import org.xmlParseExcel.SafTParserSalesInvoice;
import org.xmlParseExcel.customer.Customer;
import org.xmlParseExcel.salesInvoice.SalesInvoice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelDataRows {

    private static final Logger logger = LogManager.getLogger(ExcelDataRows.class);

    public static void dataRows(Sheet sheet, int rowIndex, SalesInvoice invoice, Workbook workbook) {

            Row row = sheet.createRow(rowIndex);
            int colNum = 0;

            // Access and write specific fields of the SalesInvoice object
            //createNumericCell(row, colNum++, invoice.getInvoiceNo());
            row.createCell(colNum++).setCellValue(invoice.getInvoiceNo());
            row.createCell(colNum++).setCellValue(invoice.getInvoiceDate());
            String invoiceStatus = invoice.getInvoiceStatus(); // Get status
            row.createCell(colNum++).setCellValue(invoiceStatus);
            row.createCell(colNum++).setCellValue(invoice.getInvoiceType());
            row.createCell(colNum++).setCellValue(invoice.getSystemEntryDate());
            row.createCell(colNum++).setCellValue(invoice.getCustomerId());

            //new columns of the Customer information
            Customer customer = invoice.getCustomer();
            if (customer != null) { // Add a null check for safety
                row.createCell(colNum++).setCellValue(customer.getCompanyName());
                row.createCell(colNum++).setCellValue(customer.getCustomerTaxID());
            } else {
                // Handle cases where customer might be null (optional)
                row.createCell(colNum++).setCellValue(""); // Empty company name
                row.createCell(colNum++).setCellValue(""); // Empty tax ID
                logger.warn("Customer object was null for InvoiceNo: " + invoice.getInvoiceNo());
            }

            // Parse numbers as numbers
            if ("A".equals(invoiceStatus)) {
                Cell netTotalCell = row.createCell(colNum++);
                Cell taxPayableCell = row.createCell(colNum++);
                Cell grossTotalCell = row.createCell(colNum++);
                netTotalCell.setCellValue(0.0); // Set NetTotal to 0 if status is "A"
                taxPayableCell.setCellValue(0.0); // Set TaxPayable to 0 if status is "A"
                grossTotalCell.setCellValue(0.0); // Set GrossTotal to 0 if status is "A"
            } else {
                createNumericCell(row, colNum++, invoice.getNetTotal()); // Otherwise, use the original value
                createNumericCell(row, colNum++, invoice.getTaxPayable());
                createNumericCell(row, colNum++, invoice.getGrossTotal());
            }

            // Write the workbook to an Excel file
           // FileOutputStream outputStream = new FileOutputStream(Constants.EXCEL_FILE_PATH);
           // workbook.write(outputStream);
    }

    private static void createNumericCell(Row row, int colNum, String value) {
        Cell cell = row.createCell(colNum);
        if (value == null || value.trim().isEmpty()) {
            cell.setCellValue(""); // Handle null or empty strings gracefully
            return;
        }
        try {
            double numericValue = Double.parseDouble(value);
            cell.setCellValue(numericValue);
        } catch (NumberFormatException e) {
            // Log the error and write the original string if parsing fails
            logger.warn("Could not parse numeric value: '" + value + "'. Writing as string.", e);
            cell.setCellValue(value);
        }
    }

    public static void writeWorkbookToFile(Workbook workbook) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(Constants.EXCEL_FILE_PATH)) {
            workbook.write(outputStream);
        }
    }
}