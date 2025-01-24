package org.xmlParseExcel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;
import org.xmlParseExcel.excelFile.ExcelDataRows;
import org.xmlParseExcel.excelFile.ExcelTitleRow;
import org.xmlParseExcel.salesInvoice.CreateSalesInvoice;
import org.xmlParseExcel.salesInvoice.SalesInvoice;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class SafTParserSalesInvoice {

    private static final Logger logger = LogManager.getLogger(SafTParserSalesInvoice.class);

    public static void main(String[] args) {
        try {

            // Parse the XML file
            List<SalesInvoice> salesInvoices = CreateSalesInvoice.parseSalesInvoices(Constants.XML_FILE_PATH);

            // Create an Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(Constants.SHEET_NAME);

            // Create the title row
            ExcelTitleRow.createTitleRow(sheet, 0);

            // Create data rows
            ExcelDataRows.dataRows(sheet, salesInvoices, workbook);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error parsing XML or writing to Excel file: " + e.getMessage());
            logger.error("Error parsing XML or writing to Excel file: ", e);
        }



    }
}
