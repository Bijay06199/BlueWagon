package com.example.bluewagon.ecalculo.utilities;

public class APIs {
//    public static String baseIp = "http://192.168.1.66:9091";
//    public static String baseIp = "http://192.168.1.68:9091";
//    public static String baseIp = "http://10.13.185.10:8080";
//    public static String baseIp = "http://103.233.182.128:8080";
    public static String baseIp = "http://api.ecalculo.com";

    //    live


    public static String auth = baseIp + "/auth";
    public static String createCustomer = baseIp + "/createCustomer";
    public static String resetUserPassword = baseIp + "/resetUserPassword/";

    //account Master
    public static String accountHeadList = baseIp + "/accountMaster/accountHeadList/";
    public static String accountNatureList = baseIp + "/accountMaster/accountNatureList/";
    public static String createAccountHead = baseIp + "/accountMaster/createAccountHead/";
    public static String updateAccountGroup = baseIp + "/accountMaster/accountHead/";
    public static String deleteAccountGroup = baseIp + "/accountMaster/deleteAccountHead/";
    public static String accountSubHeadList = baseIp + "/accountMaster/accountSubHeadList/";
    public static String createSubAccountHead = baseIp + "/accountMaster/createSubAccountHead/";
    public static String deleteAccountSubGroup = baseIp + "/accountMaster/deleteAccountSubHead/";
    public static String accountMasterList = baseIp + "/accountMaster/accountMasterList/";
    public static String supplierMasterList = baseIp + "/accountMaster/supplierMasterList/";
    public static String customerMasterList = baseIp + "/accountMaster/customerMasterList/";
    public static String allAccountList = baseIp + "/accountMaster/supplierCustomerCashAccount/";
    public static String reportBankDetails = baseIp + "/accountMaster/reportBankDetails/";
    public static String createAccountMaster = baseIp + "/accountMaster/createAccountMaster/";
    public static String createSupplierAccount = baseIp + "/accountMaster/createSupplierAccount/";
    public static String createCustomerAccount = baseIp + "/accountMaster/createCustomerAccount/";
    public static String createBankDetails = baseIp + "/accountMaster/createBankDetails/";
    public static String itemGroup = baseIp + "/itemGroup/";
    public static String itemSubGroup = baseIp + "/itemGroup/itemSubGroup/";
    public static String measurementUnit = baseIp + "/measurementUnit/";
    public static String warehouseGroupList = baseIp + "/warehouse/warehouseGroupList/";
    public static String createWarehouse = baseIp + "/warehouse/warehouseGroup/";
    public static String warehouse = baseIp + "/warehouse/";
    public static String item = baseIp + "/item/";

    //transaction
    public static String allAccountMasterListForTransaction = baseIp + "/accountMaster/allAccountMasterListForTransaction/";
    public static String journalVoucherNumber = baseIp + "/transactionMaster/journalEntryLoadPage/";
    public static String saveJournalEntry = baseIp + "/transactionMaster/saveJournalEntry/";
    public static String saveReceiptEntry = baseIp + "/transactionMaster/saveReceiptEntry/";
    public static String savePaymentEntry = baseIp + "/transactionMaster/savePaymentEntry/";
    public static String journalBook = baseIp + "/accountingReport/journalBook/";
    public static String receiptVoucherNumber = baseIp + "/transactionMaster/receiptEntryLoadPage/";
    public static String paymentVoucherNumber = baseIp + "/transactionMaster/paymentEntryLoadPage/";


    //reports
    public static String accountLedger = baseIp + "/accountingReport/accountLedger/";
    public static String partyStatement = baseIp + "/accountingReport/partyStatement/";
    public static String cashBook = baseIp + "/accountingReport/cashBook/";
    public static String supplierAndCustomerAccount = baseIp + "/accountMaster/supplierAndCustomerAccount/";
    public static String bankStatement = baseIp + "/accountingReport/bankStatement/";
    public static String bankPdcTransaction = baseIp + "/accountingReport/bankPdcTransaction/";
    public static String itemStatementReport = baseIp + "/inventoryStock/itemStatementReport/";

    public static String salesOrder = baseIp + "/inventoryOutReport/salesOrder/";
    public static String salesChallan = baseIp + "/inventoryOutReport/salesChallan/";
    public static String salesInvoice = baseIp + "/inventoryOutReport/salesInvoice/";

    public static String salesOrderLoadPage = baseIp + "/inventoryOut/salesOrderLoadPage/";
    public static String salesChallanLoadPage = baseIp + "/inventoryOut/salesChallanLoadPage/";
    public static String salesInvoiceLoadPage = baseIp + "/inventoryOut/salesInvoiceLoadPage/";


    public static String itemMasterListByItemType = baseIp + "/item/itemMasterListByItemType/";
    public static String dailyCashSummary = baseIp + "/dashboard/dailyCashSummary/";
    public static String dailyTransactionSummary = baseIp + "/dashboard/dailyTransactionSummary/";




}