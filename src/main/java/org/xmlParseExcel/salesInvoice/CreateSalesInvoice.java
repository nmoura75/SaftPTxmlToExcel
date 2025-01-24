package org.xmlParseExcel.salesInvoice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateSalesInvoice {

    public static List<SalesInvoice> parseSalesInvoices(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
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
                salesInvoice.setTaxPayable(getElementText(customerElement, "TaxPayable"));
                salesInvoice.setGrossTotal(getElementText(customerElement, "GrossTotal"));
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
}
