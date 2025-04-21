package org.xmlParseExcel.customer;

public class Customer {

    private String CustomerID;
    private String CustomerTaxID;
    private String CompanyName;

    // Constructor
    public Customer(String customerID, String customerTaxID, String companyName) {
        this.CustomerID = customerID;
        this.CustomerTaxID = customerTaxID;
        this.CompanyName = companyName;
    }

    // Getters and Setters
    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        this.CustomerID = customerID;
    }

    public String getCustomerTaxID() {
        return CustomerTaxID;
    }

    public void setCustomerTaxID(String customerTaxID) {
        this.CustomerTaxID = customerTaxID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        this.CompanyName = companyName;
    }
    // ... other getters and setters ...
}
