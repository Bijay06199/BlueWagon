package com.example.bluewagon.ecalculo.utilities;

/**
 * Created by Mobin on 5/12/15.
 */
public class AppTexts {

    public static final String SERVER_ISSUE = "Unfortunately, server is not responding.\nPlease try again later!";
    public static final String SOMETHING_WRONG = "Something went wrong.\nPlease try again later!";
    static final String PARSE_ERROR_MESSAGE = "Sorry!\nA Data Parse Error has occurred";
    static final String NETWORK_ERROR_MESSAGE = "Sorry!\nA Network Error has occurred";
    public static final String SYSTEM_NOT_RESPONDING = "Unfortunately System is not responding. Please try again later";
    static final String NO_INTERNET_MESSAGE_MESSAGE = "No internet connection!\nPlease check your network and try again";
    static final String AUTHENTICATION_ERROR_MESSAGE = "Sorry!\nAuthentication Failed. Please try again later";
    static final String CONNECTION_TIMEOUT_ERROR_MESSAGE = "Sorry!\nConnection has timed out. Please try again later";



    public static final String data = "data";
    public static final String orgId = "organisationId";
    public static final String userId = "userId";
    public static final String fyId = "fyId";
    public static final String startDate = "startDate";
    public static final String endDate = "endDate";
    public static final String dash = "----";


    public static final String loggedin="logged_in";
    public static final String ok = "OK";
    public static final String empty = "";
    public static final String rs = "Rs. ";
    public static final String from = "From";
    public static final String info = "Info!";
    public static final String edit = "edit";
    public static final String error = "Error!";
    public static final String status = "status";
    public static final String success = "SUCCESS";
    public static final String message = "message";
    public static final String required = "Required!";
    public static final String successMsg = "Successful!";
    public static final String postObject = "postObject:: ";
    public static final String pleaseWait = "Please wait...";
    public static final String response = "response";

    public static final String saleInvoice = "Sales Invoice View";
    public static final String saleOrder = "Sales Order View";
    public static final String saleChallan = "Sales Challan View";
    public static final String saleReturn = "Sales Return/Credit Note";
    public static final String purchaseInvoice = "Purchase Invoice View";
    public static final String purchaseReturn = "Purchase Return/Debit Note";
    public static final String journalBook = "Journal Book";
    public static final String journalView = "Journal View";
    public static final String paymentView = "Payment View";
    public static final String receiptView = "Receipt View";
    public static final String debitNote = "Debit Note W/O Items";
    public static final String creditNote = "Credit Note W/O Items";
    public static final String purchaseNote = "Purchase W/O Items";
    public static final String SalesNote = "Sales W/O Items";


    public static final String salesInvoice = "salesInvoice";
    public static final String journalNDebitCreditNote = "journalNDebitCreditNote";
    public static final String paymentNReceipt = "paymentNReceipt";


    public static final String extension = ".jpg";
    public static final String name = "image";
    public static final String dirNameforImages = "Images";


    public static final String logoutTest = "Are you sure you want to log out?";
    public static final String invalidMobile = "Invalid mobile number";
    public static final String errorTryAgain = "Something went wrong! Please try again.";

    public static final String updateApp = "Your current app is outdated.\nPlease update app from Playstore.";
    public static final String loginError = "Unfortunately some error occurred while signing in! \nPlease try again later.";


    // for app use only.......

    public static final String discountFlat = "Discount";
    public static final String discountPercent = "Discount %";
    public static final String extraExpenses = "extraExpenses";
    public static final String discount = "discount";
    public static final String vatFlat = "VAT";
    public static final String vatPercent = "VAT %";
    public static final String roundOff = "roundOff";


    //for intent values only
    public static final int addSalesInvoiceServiceIntent = 1;
    public static final int addSalesReturnServiceIntent = 2;
    public static final int addSalesReturnByBillServiceIntent = 3;
    public static final int addPurchaseInvoiceServiceIntent = 4;
    public static final int addPurchaseReturnServiceIntent = 5;
    public static final int addPurchaseReturnByBillServiceIntent = 6;


    //demo data

    public static final String summaryAccountArray = "[\n" +
            "\t\t\t{\"reportTypeName\":\"Cash In Hand\", \"txnSumAmount\":\"20000.00\"},\n" +
            "            {\"reportTypeName\":\"Bank Balance\", \"txnSumAmount\":\"20000.00\"},\n" +
            "            {\"reportTypeName\":\"Supplier\", \"txnSumAmount\":\"20000.00\"},\n" +
            "            {\"reportTypeName\":\"Customer\", \"txnSumAmount\":\"20000.00\"}" +
            "\t\t]";

    public static final String transactionAccountArray = "[\n" +
            "\t\t\t{\"reportTypeName\":\"Purchase\", \"txnSumAmount\":\"300000.00\"},\n" +
            "            {\"reportTypeName\":\"Sales\", \"txnSumAmount\":\"300000.00\"},\n" +
            "            {\"reportTypeName\":\"Payment\", \"txnSumAmount\":\"300000.00\"},\n" +
            "            {\"reportTypeName\":\"Receipt\", \"txnSumAmount\":\"300000.00\"}" +
            "\t\t]";

    public static final String inventoryAccountArray = "[\n" +
            "\t\t\t{\"reportTypeName\":\"Stock In Stock\", \"txnSumAmount\":\"800.00\"},\n" +
            "            {\"reportTypeName\":\"Minimum Stock Item\", \"txnSumAmount\":\"150.00\"},\n" +
            "            {\"reportTypeName\":\"Re-Order Stock Item\", \"txnSumAmount\":\"100.00\"},\n" +
            "            {\"reportTypeName\":\"Max Stock Item\", \"txnSumAmount\":\"500.00\"}" +
            "\t\t]";


    public static final String itemProductsArray = "[\n" +
            "\t\t\t{\"productName\":\"Shampoo\", \"availableQty\":\"800.00\", \"unit\":\"pcs\", \"salesRate\":\"200\"},\n" +
            "            {\"productName\":\"Saboon\", \"availableQty\":\"150.00\", \"unit\":\"pcs\", \"salesRate\":\"100\"},\n" +
            "            {\"productName\":\"HandWash\", \"availableQty\":\"100.00\", \"unit\":\"pcs\", \"salesRate\":\"123\"},\n" +
            "            {\"productName\":\"Conditioner\", \"availableQty\":\"50.00\", \"unit\":\"pcs\", \"salesRate\":\"150\"}" +
            "\t\t]";

    public static final String itemsServiceArray = "[\n" +
            "\t\t\t{\"serviceName\":\"Service A\", \"soldQty\":\"80.00\", \"unit\":\"pcs\"},\n" +
            "            {\"serviceName\":\"Service B\", \"soldQty\":\"15.00\", \"unit\":\"pcs\"},\n" +
            "            {\"serviceName\":\"Service B\", \"soldQty\":\"10.00\", \"unit\":\"pcs\"},\n" +
            "            {\"serviceName\":\"Service B\", \"soldQty\":\"5.00\", \"unit\":\"pcs\"}" +
            "\t\t]";

    public static final String itemUnitArray = "[\n" +
            "\t\t\t{\"name\":\"Pieces\", \"shortName\":\"pcs\"},\n" +
            "            {\"name\":\"Gram\", \"shortName\":\"gm\"},\n" +
            "            {\"name\":\"Kilogram\", \"shortName\":\"kg\"},\n" +
            "            {\"name\":\"Litres\", \"shortName\":\"ltr\"}" +
            "\t\t]";


    public static final String customerAccountArray = "[\n" +
            "\t\t\t{\"customerSupplierName\":\"Customer-A\", \"balance\":\"150000\"},\n" +
            "            {\"customerSupplierName\":\"Customer-B\", \"balance\":\"250000\"},\n" +
            "            {\"customerSupplierName\":\"Customer-C\", \"balance\":\"180000\"},\n" +
            "            {\"customerSupplierName\":\"Customer-D\", \"balance\":\"260000\"},\n" +
            "            {\"customerSupplierName\":\"Customer-E\", \"balance\":\"280000\"},\n" +
            "            {\"customerSupplierName\":\"Customer-F\", \"balance\":\"360000\"}" +
            "\t\t]";

    public static final String supplierAccountArray = "[\n" +
            "\t\t\t{\"customerSupplierName\":\"Supplier-A\", \"balance\":\"180000\"},\n" +
            "            {\"customerSupplierName\":\"Supplier-B\", \"balance\":\"290000\"},\n" +
            "            {\"customerSupplierName\":\"Supplier-C\", \"balance\":\"100000\"},\n" +
            "            {\"customerSupplierName\":\"Supplier-D\", \"balance\":\"260000\"},\n" +
            "            {\"customerSupplierName\":\"Supplier-E\", \"balance\":\"210000\"},\n" +
            "            {\"customerSupplierName\":\"Supplier-F\", \"balance\":\"630000\"}" +
            "\t\t]";


    public static final String dashboardTransactionArray = "[\n" +
            "\t\t\t{\"dueDays\":\"20\", \"transactionType\":\"Sales\", \"billAmount\":\"20000\", \"date\":\"2077-02-28\", \"billType\":\"SIV\",\"billNumber\":\"1\", \"party\":\"Cash\", \"name\":\"Test User\"},\n" +
            "            {\"dueDays\":\"20\", \"transactionType\":\"Purchase\", \"billAmount\":\"200000\", \"date\":\"2077-02-28\", \"billType\":\"Pur\",\"billNumber\":\"1\", \"party\":\"Credit\", \"name\":\" \"},\n" +
            "            {\"dueDays\":\"20\", \"transactionType\":\"Payment\", \"billAmount\":\"10000\", \"date\":\"2077-02-28\", \"billType\":\"Pay\",\"billNumber\":\"1\", \"party\":\"Cash\", \"name\":\"Test User 2\"},\n" +
            "            {\"dueDays\":\"20\", \"transactionType\":\"Receipt\", \"billAmount\":\"100000\", \"date\":\"2077-02-28\", \"billType\":\"Rec\",\"billNumber\":\"1\", \"party\":\"Credit\", \"name\":\" \"}" +
            "\t\t]";




}

