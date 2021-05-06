package com.example.bluewagon.ecalculo.utilities;

public enum RoleEnum {
    //Master
    AccountHead("Account Head", 1.01, "Master"),
    AccountSubHead("Account Sub Head", 1.02, "Master"),
    AccountMaster("Account Master", 1.03, "Master"),
    SupplierAccount("Supplier Account", 1.04, "Master"),
    CustomerAccount("Customer Account", 1.05, "Master"),
    BankAccount("Bank Account", 1.06, "Master"),
    IncomeAccount("Income Account", 1.07, "Master"),
    ExpensesAccount("Expenses Account", 1.08, "Master"),
    ItemGroup("Item Group", 1.09, "Master"),
    ItemSubGroup("Item Sub Group", 1.11, "Master"),
    ItemMaster("Item Master", 1.12, "Master"),
    MeasurementUnit("Measurement unit", 1.13, "Master"),
    WarehouseGroup("Warehouse Group", 1.14, "Master"),
    Warehouse("Item Group", 1.15, "Master"),
    ExtraExpensesPurchase("Extra Expenses Purchase", 1.16, "Master"),
    ExtraExpensesSales("Extra Expenses Sales", 1.17, "Master"),
    //Transaction
    Journal("Journal Entry", 2.01, "Transaction"),
    Payment("Payment Entry", 2.02, "Transaction"),
    Receipt("Receipt Entry", 2.03, "Transaction"),
    DebitNote("Debit Note", 2.04, "Transaction"),
    CreditNote("Credit Note", 2.05, "Transaction"),
    SalesOrder("Sales Order", 2.06, "Transaction"),
    Saleschallan("Sales Challan", 2.07, "Transaction"),
    SalesInvoice("Sales Invoice", 2.08, "Transaction"),
    SalesReturn("Sales Return", 2.09, "Transaction"),
    PurchaseOrder("Purchase Order", 2.11, "Transaction"),
    PurchaseChallan("Purchase Challan", 2.12, "Transaction"),
    PurchaseInvoice("Purchase Invoice", 2.13, "Transaction"),
    PurchaseReturn("Purchase Return", 2.14, "Transaction"),
    CashWithDraw("Cash WithDraw", 2.15, "Transaction"),
    CashDeposit("Cash Deposit", 2.16, "Transaction"),
    BankTransfer("Bank Transfer", 2.17, "Transaction"),
    //Accounting Report
    CashBook("Cash Book", 3.01, "Accounting Report"),
    DayBook("Day Book", 3.02, "Accounting Report"),
    JournalBook("Journal Book", 3.03, "Accounting Report"),
    LedgerInquiry("Ledger Inquiry", 3.04, "Accounting Report"),
    TrialBalance("Trial Balance", 3.05, "Accounting Report"),
    ProfitAndLoss("Profit And Loss", 3.06, "Accounting Report"),
    BalanceSheet("Balance Sheet", 3.07, "Accounting Report"),
    IncomeAndExpenses("Income And Expenses", 3.08, "Accounting Report"),
    TradingAccount("Trading Account", 3.09, "Accounting Report"),
    PartStatement("Party Statement", 3.11, "Accounting Report"),
    SupplierTransactionSummary("Supplier Transaction Summary", 3.12, "Accounting Report"),
    CustomerTransactionSummary("Customer Transaction Summary", 3.13, "Accounting Report"),
    BANKSTATEMENT("Bank Statement", 3.18, "Accounting Report"),
    DayBookWITHOUTCASH("Day Book Without Cash", 3.19, "Accounting Report"),
    //Inventory Report
    SalesOrderReport("Sales Order Report", 4.01, "Inventory Report"),
    SalesChallanReport("Sales Challan Report", 4.02, "Inventory Report"),
    SalesInvoiceReport("Sales Invoice Report", 4.03, "Inventory Report"),
    SalesTaxRegisterReport("Sales Tax Register", 4.12, "Inventory Report"),
    SalesReturnReport("Sales Return Report", 4.04, "Inventory Report"),
    PurchaseOrderReport("Purchase Order Report", 4.05, "Inventory Report"),
    PurchaseChallanReport("Purchase Challan Report", 4.06, "Inventory Report"),
    PurchaseInvoiceReport("Purchase Invoice Report", 4.07, "Inventory Report"),
    PurchaseReturnReport("Purchase Return Report", 4.08, "Inventory Report"),
    PurchaseTaxRegisterReport("Purchase Tax Register", 4.13, "Inventory Report"),

    InventoryInOutReport("Inventory In/Out Report", 4.09, "Inventory Report"),
    ItemStatementReport("Item Statement Report", 4.11, "Inventory Report"),
    //Setting
    User("User", 5.01, "Setting"),
    Role("Role", 5.02, "Setting"),
    FiscalYear("Fiscal Year", 5.03, "Setting"),
    MasterSetUp("Master Setup", 5.04, "Setting");


    RoleEnum(String pageName, double pageId, String pageHead) {
        this.pageName = pageName;
        this.pageId = pageId;
        this.pageHead = pageHead;
    }

    private String pageName;
    private double pageId;
    private String pageHead;
}
