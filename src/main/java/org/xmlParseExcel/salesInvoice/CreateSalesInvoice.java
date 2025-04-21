package org.xmlParseExcel.salesInvoice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlParseExcel.customer.Customer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateSalesInvoice {

    public static List<SalesInvoice> parseSalesInvoices(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {

        Map<String, Customer> customerMap = new HashMap<>();
        List<SalesInvoice> salesInvoices = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilePath));
        document.getDocumentElement().normalize();

        parseCustomers(document, customerMap);

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
                String customerId = getElementText(customerElement, "CustomerID");
                salesInvoice.setCustomerId(customerId);
                //get the customer object from the map
                Customer customer = customerMap.get(customerId);
                salesInvoice.setCustomer(customer);
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

    private static void parseCustomers(Document document, Map<String, Customer> customerMap) {
        NodeList customerNodes = document.getElementsByTagName("Customer");
        for (int i = 0; i < customerNodes.getLength(); i++) {
            Node customerNode = customerNodes.item(i);
            if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element customerElement = (Element) customerNode;
                String customerId = getElementText(customerElement, "CustomerID");
                String customerTaxId = getElementText(customerElement, "CustomerTaxID");
                String companyName = getElementText(customerElement, "CompanyName");

                Customer customer = new Customer(customerId, customerTaxId, companyName);
                customerMap.put(customerId, customer);
            }
        }
    }
}
