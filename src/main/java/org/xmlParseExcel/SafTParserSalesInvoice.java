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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SafTParserSalesInvoice {

    private static final Logger logger = LogManager.getLogger(SafTParserSalesInvoice.class);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {

            // Parse the XML file
            List<SalesInvoice> salesInvoices = CreateSalesInvoice.parseSalesInvoices(Constants.XML_FILE_PATH);


            // Create an Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(Constants.SHEET_NAME);

            // Create the title row
            ExcelTitleRow.createTitleRow(sheet, 0);

            // Create data rows in parallel
            for (int i = 0; i < salesInvoices.size(); i++) {
                final int rowIndex = i + 1;
                final SalesInvoice invoice = salesInvoices.get(i);
                executorService.submit(() -> {
                    try {
                        ExcelDataRows.dataRows(sheet, rowIndex, invoice, workbook);
                    } catch (IOException e) {
                        logger.error("Error writing to Excel file for invoice: " + invoice, e);
                    }
                });
            }

            // Shutdown executor service and wait for tasks to complete
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);

        } catch (ParserConfigurationException | SAXException | IOException | InterruptedException e) {
            System.err.println("Error parsing XML or writing to Excel file: " + e.getMessage());
            logger.error("Error parsing XML or writing to Excel file: ", e);
        }



    }
}
