package org.xmlParseExcel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SafTParserSalesInvoice {

    private static final Logger logger = LogManager.getLogger(SafTParserSalesInvoice.class);

    public static void main(String[] args) {
        try {

            // Parse the XML file
            List<SalesInvoice> salesInvoices = parseSalesInvoices(Constants.XML_FILE_PATH);

            // Create an Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(Constants.SHEET_NAME);

            // Write data to Excel sheet
            /*int rowNum = 0;
            for (SalesInvoice rowData : salesInvoices) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String cellData : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(cellData);
                }
            }*/

            int rowNum = 0;
            for (SalesInvoice invoice : salesInvoices) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // Access and write specific fields of the SalesInvoice object
                row.createCell(colNum++).setCellValue(invoice.getInvoiceNo());
                row.createCell(colNum++).setCellValue(invoice.getInvoiceDate());
                row.createCell(colNum++).setCellValue(invoice.getInvoiceStatus());
                row.createCell(colNum++).setCellValue(invoice.getInvoiceType());
                row.createCell(colNum++).setCellValue(invoice.getSystemEntryDate());
                row.createCell(colNum++).setCellValue(invoice.getNetTotal());
            }

            // Write the workbook to an Excel file
            try (FileOutputStream outputStream = new FileOutputStream(Constants.EXCEL_FILE_PATH)) {
                workbook.write(outputStream);
            }

            System.out.println("XML data successfully converted to Excel file: " + Constants.EXCEL_FILE_PATH);

            // Process the extracted customer data (e.g., print to console)
            /*for (SalesInvoice salesInvoice : salesInvoices) {
                System.out.println("InvoiceNo: " + salesInvoice.getInvoiceNo());
                System.out.println("InvoiceDate: " + salesInvoice.getInvoiceDate());
                System.out.println("InvoiceStatus: " + salesInvoice.getInvoiceStatus());
                System.out.println("InvoiceType: " + salesInvoice.getInvoiceType());
                System.out.println("SystemEntryDate: " + salesInvoice.getSystemEntryDate());
                System.out.println("NetTotal: " + salesInvoice.getNetTotal());
                // ... print other customer details
            }*/

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error parsing XML or writing to Excel file: " + e.getMessage());
            logger.error("Error parsing XML or writing to Excel file: ", e);
        }
    }

    private static List<SalesInvoice> parseSalesInvoices(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
        List<SalesInvoice> salesInvoices = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilePath));
        document.getDocumentElement().normalize();

        NodeList customerNodes = document.getElementsByTagName("Invoice");

        for (int i = 0; i < customerNodes.getLength(); i++) {
            Node customerNode = customerNodes.item(i);
            if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element customerElement = (Element) customerNode;
                SalesInvoice salesInvoice = new SalesInvoice();

                salesInvoice.setInvoiceNo(getElementText(customerElement, "InvoiceNo"));
                salesInvoice.setInvoiceDate(getElementText(customerElement, "InvoiceDate"));
                salesInvoice.setInvoiceStatus(getElementText(customerElement, "InvoiceStatus"));
                salesInvoice.setInvoiceType(getElementText(customerElement, "InvoiceType"));
                salesInvoice.setSystemEntryDate(getElementText(customerElement, "SystemEntryDate"));
                salesInvoice.setNetTotal(getElementText(customerElement, "NetTotal"));
                // ... get other customer details

                salesInvoices.add(salesInvoice);
            }
        }

        return salesInvoices;
    }

    private static String getElementText(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            Node node = nodes.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                return node.getTextContent();
            }
        }
        return "";
    }

    // Create a simple POJO to hold customer data
    static class SalesInvoice {
        private String InvoiceNo;
        private String InvoiceDate;
        private String InvoiceStatus;
        private String InvoiceType;
        private String SystemEntryDate;
        private String NetTotal;
        // ... other customer attributes

        // Getters and Setters
        public String getInvoiceNo() {
            return InvoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            InvoiceNo = invoiceNo;
        }

        public String getInvoiceDate() {
            return InvoiceDate;
        }

        public void setInvoiceDate(String invoiceDate) {
            InvoiceDate = invoiceDate;
        }

        public String getInvoiceStatus() {
            return InvoiceStatus;
        }

        public void setInvoiceStatus(String invoiceStatus) {
            InvoiceStatus = invoiceStatus;
        }

        public String getInvoiceType() {
            return InvoiceType;
        }

        public void setInvoiceType(String invoiceType) {
            InvoiceType = invoiceType;
        }

        public String getSystemEntryDate() {
            return SystemEntryDate;
        }

        public void setSystemEntryDate(String systemEntryDate) {
            SystemEntryDate = systemEntryDate;
        }

        public String getNetTotal() {
            return NetTotal;
        }

        public void setNetTotal(String netTotal) {
            NetTotal = netTotal;
        }
        // ... other getters and setters
    }
}
