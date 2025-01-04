package org.xmlParseExcel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SafTParser {

    public static void main(String[] args) {
        try {
            // Replace with the actual path to your SAF-T PT XML file
            String xmlFilePath = "G:\\Other computers\\O meu Portátil (1)\\clientes 5nov18\\Elite\\2024\\vendas\\saft\\ana outubro\\SAF-T_10_2024.xml";

            List<Customer> customers = parseCustomers(xmlFilePath);

            // Process the extracted customer data (e.g., print to console)
            for (Customer customer : customers) {
                System.out.println("CustomerID: " + customer.getCustomerID());
                System.out.println("CustomerName: " + customer.getCustomerName());
                // ... print other customer details
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Customer> parseCustomers(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
        List<Customer> customers = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilePath));
        document.getDocumentElement().normalize();

        NodeList customerNodes = document.getElementsByTagName("Customer");

        for (int i = 0; i < customerNodes.getLength(); i++) {
            Node customerNode = customerNodes.item(i);
            if (customerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element customerElement = (Element) customerNode;
                Customer customer = new Customer();

                customer.setCustomerID(getElementText(customerElement, "CustomerID"));
                customer.setCustomerName(getElementText(customerElement, "CustomerName"));
                // ... get other customer details

                customers.add(customer);
            }
        }

        return customers;
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
    static class Customer {
        private String CustomerID;
        private String CustomerName;
        // ... other customer attributes

        // Getters and Setters
        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }
        // ... other getters and setters
    }
}
