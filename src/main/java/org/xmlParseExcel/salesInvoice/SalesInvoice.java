package org.xmlParseExcel.salesInvoice;

public class SalesInvoice {

    // Create a simple POJO to hold customer data
        private String InvoiceNo;
        private String InvoiceDate;
        private String InvoiceStatus;
        private String InvoiceType;
        private String SystemEntryDate;
        private String NetTotal;
        private String TaxPayable;
        private String GrossTotal;
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

        public String getTaxPayable() {
            return TaxPayable;
        }

        public void setTaxPayable(String taxPayable) {
            TaxPayable = taxPayable;
        }

        public String getGrossTotal() {
            return GrossTotal;
        }

        public void setGrossTotal(String grossTotal) {
            GrossTotal = grossTotal;
        }

        // ... other getters and setters
    }

