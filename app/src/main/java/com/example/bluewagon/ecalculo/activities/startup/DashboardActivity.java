package com.example.bluewagon.ecalculo.activities.startup;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.activities.creation.AccountGroupActivity;
import com.example.bluewagon.ecalculo.activities.creation.AccountSubGroupActivity;
import com.example.bluewagon.ecalculo.activities.creation.AddCustomerActivity;
import com.example.bluewagon.ecalculo.activities.creation.AddItemsActivity;
import com.example.bluewagon.ecalculo.activities.creation.AddMeasurementUnitActivity;
import com.example.bluewagon.ecalculo.activities.creation.AddSupplierActivity;
import com.example.bluewagon.ecalculo.activities.creation.CreateAccountActivity;
import com.example.bluewagon.ecalculo.activities.creation.CreateBankActivity;
import com.example.bluewagon.ecalculo.activities.creation.CreateExpensesActivity;
import com.example.bluewagon.ecalculo.activities.creation.CreateIncomeActivity;
import com.example.bluewagon.ecalculo.activities.creation.CreateWarehouseActivity;
import com.example.bluewagon.ecalculo.activities.creation.CreateWarehouseGroupActivity;
import com.example.bluewagon.ecalculo.activities.creation.ItemCategoryActivity;
import com.example.bluewagon.ecalculo.activities.creation.ItemSubGroupActivity;
import com.example.bluewagon.ecalculo.activities.reports.AccountStatementLedgerActivity;
import com.example.bluewagon.ecalculo.activities.reports.BalanceSheetActivity;
import com.example.bluewagon.ecalculo.activities.reports.BankBalanceActivity;
import com.example.bluewagon.ecalculo.activities.reports.BankStatementActivity;
import com.example.bluewagon.ecalculo.activities.reports.CashBookReportActivity;
import com.example.bluewagon.ecalculo.activities.reports.CustomerTransactionSummaryActivity;
import com.example.bluewagon.ecalculo.activities.reports.ItemClosingBalanceActivity;
import com.example.bluewagon.ecalculo.activities.reports.ItemStatementActivity;
import com.example.bluewagon.ecalculo.activities.reports.ItemSummaryActivity;
import com.example.bluewagon.ecalculo.activities.reports.PartyAgedPayableActivity;
import com.example.bluewagon.ecalculo.activities.reports.PartyAgedReceivableActivity;
import com.example.bluewagon.ecalculo.activities.reports.PartyOutstandingPayableActivity;
import com.example.bluewagon.ecalculo.activities.reports.PartyOutstandingReceivableActivity;
import com.example.bluewagon.ecalculo.activities.reports.PartyStatementActivity;
import com.example.bluewagon.ecalculo.activities.reports.PostdatedChequeReportActivity;
import com.example.bluewagon.ecalculo.activities.reports.ProfitAndLossActivity;
import com.example.bluewagon.ecalculo.activities.reports.SupplierTransactionSummaryActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.BankTransferActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.CashDepositActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.CashWithdrawlActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.CreditNoteWOItems;
import com.example.bluewagon.ecalculo.activities.transactions.entry.DebitNoteWOItems;
import com.example.bluewagon.ecalculo.activities.transactions.entry.EntryActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.PaymentDoubleEntryActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.PurchaseInvoiceActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.PurchaseReturnActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.PurchaseReturnByBillActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.PurchaseWOItemsActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.ReceiptDoubleEntryActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.SalesChallanActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.SalesInvoiceActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.SalesOrderActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.SalesReturnActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.SalesReturnByBillActivity;
import com.example.bluewagon.ecalculo.activities.transactions.entry.SalesWOItemsActivity;
import com.example.bluewagon.ecalculo.activities.transactions.view.InvoiceViewActivity;
import com.example.bluewagon.ecalculo.activities.transactions.view.JournalViewActivity;
import com.example.bluewagon.ecalculo.activities.transactions.view.SalesChallanViewActivity;
import com.example.bluewagon.ecalculo.activities.transactions.view.SalesOrderViewActivity;
import com.example.bluewagon.ecalculo.adapters.dashboardAccount.AccountCustomerSupplierAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.ExpandableListAdapterParent;
import com.example.bluewagon.ecalculo.adapters.reportsAdapter.DashboardItemUnitAdapter;
import com.example.bluewagon.ecalculo.adapters.reportsAdapter.DashboardItemsProductAdapter;
import com.example.bluewagon.ecalculo.adapters.reportsAdapter.DashboardTransactionAdapter;
import com.example.bluewagon.ecalculo.adapters.startup.DashboardAdapter;
import com.example.bluewagon.ecalculo.dtos.CustomerSupplierLedgerDTO;
import com.example.bluewagon.ecalculo.dtos.DashboardDTO;
import com.example.bluewagon.ecalculo.dtos.DashboardItemDTO;
import com.example.bluewagon.ecalculo.dtos.DashboardItemServiceDTO;
import com.example.bluewagon.ecalculo.dtos.DashboardTransactionDTO;
import com.example.bluewagon.ecalculo.dtos.UnitsDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DashboardActivity activity = DashboardActivity.this;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private CardView cVProfile;
    private PopupWindow popupWindow;
    private LinearLayout lLDashboard, lLAccount, lLTransaction, lLReport, lLMore, lLDashboardItems, lLAccountsItems, lLTransactionDetails, lLReportDetails, lLSummary, lLTransactions,
            lLItems, lLSummaryDetails, lLItemsDetails, lLDateSelection, lLItemDateSelection, lLItemsUnit, lLCustomerDetails, lLSupplierDetails, lLNoData;

    private TextView tVDashboard, tVAccount, tVTransaction, tVReport, tVMore, tVCustomerDetails, tVSupplierDetails, tVAccountDetails, tVJournalEntry, tVPaymentEntry,
            tVSalesInvoice, tVSalesOrder, tVSalesChallan, tVPurchaseInvoice, tVSalesReturn, tVPurchaseReturn, tVReceiptEntry, tVCashWithdraw, tVPurchaseWOItems, tVSalesWOItem, tVDebitNoteItem, tVCreditNoteItem,
            tVCashDeposit, tVBankTransfer, tVPartyStatement, tVPartyOutstandingPayable, tVPartyOutstandingReceivable, tVPartyAgedPayable, tVPartyAgedReceivable, tVSupplierTxnSummary,
            tVCustomerTxnSummary, tVBankStatement, tVBankBalance, tVPostDatedChequeReport, tVAccountStatement, tVCashBook, tVJournalBook, tVItemStatement, tVItemSummary, tVItemClosingBalance,
            tVSalesTaxRegister, tVPurchaseTaxRegister, tVBalanceSheet, tVProfitAndLossAccount, tVTrialBalance, tVSummary, tVTransactions, tVItems, tVSummaryAccount, tVSummaryTransaction,
            tVSummaryInventory, tVItemProducts, tVItemServices, tVItemUnit, tVAsOfDate, tVDate;

    private ImageView iVDashboard, iVAccount, iVTransaction, iVReport, iVMore, iVSummary, iVTransactions, iVItems;

    private RecyclerView rVCustomer, rVSupplier, rVAccounts, rVAccountDetails, rVTransactionDetails, rVInventoryDetails, rVItemsProduct, rVItemsServices, rVItemsUnit, rVTransactionsDetails;

    private HorizontalScrollView hScrollViewProduct, hScrollViewService;

    private SwitchCompat switchCompat;
    private LayoutInflater inflater;
    private AlertDialog dialog;

    //listview
    private List<String> listDataHeader;
    private List<Integer> listIcons;
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<String>> listDataChildTwo;
    private int closeCount = 0;

    private String asOfDate, currentNepaliDate;
    private Calendar calendar;
    private DateFormat df;


    private androidx.appcompat.app.AlertDialog progressDialog;
    private RequestQueue requestQueue;
    private SharedPreferences preferences;
    private List<CustomerSupplierLedgerDTO> customerSupplierLedgerDTOS;
    private List<DashboardItemDTO> dashboardItemDTOList;
    private List<DashboardDTO> dashboardDTOList;
    private AccountCustomerSupplierAdapter accountCustomerSupplierAdapter;
    private DashboardItemsProductAdapter dashboardItemsProductAdapter;
    private DashboardAdapter dashboardAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            System.out.println("Night Mode");
            setTheme(R.style.darkTheme);
        }


        setContentView(R.layout.activity_dashboard);
        init();
//        darkMode();
        initToolbar();
        initNavigationBar();
        getAllDatas();
        setDate();
        //loading dashboard data for first time
        getAccountData();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {


        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MM-dd");

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);



        hScrollViewProduct = findViewById(R.id.hScrollViewProduct);
        hScrollViewService = findViewById(R.id.hScrollViewService);

        lLDashboard = findViewById(R.id.lLDashboard);
        lLAccount = findViewById(R.id.lLAccount);
        lLTransaction = findViewById(R.id.lLTransaction);
        lLReport = findViewById(R.id.lLReport);
        lLMore = findViewById(R.id.lLMore);
        lLDashboardItems = findViewById(R.id.lLDashboardItems);
        lLAccountsItems = findViewById(R.id.lLAccountsItems);
        lLTransactionDetails = findViewById(R.id.lLTransactionDetails);
        lLReportDetails = findViewById(R.id.lLReportDetails);
        lLSummary = findViewById(R.id.lLSummary);
        lLTransactions = findViewById(R.id.lLTransactions);
        lLItems = findViewById(R.id.lLItems);
        lLSummaryDetails = findViewById(R.id.lLSummaryDetails);
        lLItemsDetails = findViewById(R.id.lLItemsDetails);
        lLDateSelection = findViewById(R.id.lLDateSelection);
        lLItemDateSelection = findViewById(R.id.lLItemDateSelection);
        lLItemsUnit = findViewById(R.id.lLItemsUnit);
        lLCustomerDetails = findViewById(R.id.lLCustomerDetails);
        lLSupplierDetails = findViewById(R.id.lLSupplierDetails);
        lLNoData = findViewById(R.id.lLNoData);

        tVDashboard = findViewById(R.id.tVDashboard);
        tVAccount = findViewById(R.id.tVAccount);
        tVTransaction = findViewById(R.id.tVTransaction);
        tVReport = findViewById(R.id.tVReport);
        tVMore = findViewById(R.id.tVMore);
        tVCustomerDetails = findViewById(R.id.tVCustomerDetails);
        tVSupplierDetails = findViewById(R.id.tVSupplierDetails);
        tVAccountDetails = findViewById(R.id.tVAccountDetails);
        tVSalesInvoice = findViewById(R.id.tVSalesInvoice);
        tVSalesOrder = findViewById(R.id.tVSalesOrder);
        tVSalesChallan = findViewById(R.id.tVSalesChallan);
        tVPurchaseInvoice = findViewById(R.id.tVPurchaseInvoice);
        tVSalesReturn = findViewById(R.id.tVSalesReturn);
        tVJournalEntry = findViewById(R.id.tVJournalEntry);
        tVPaymentEntry = findViewById(R.id.tVPaymentEntry);
        tVPurchaseReturn = findViewById(R.id.tVPurchaseReturn);
        tVReceiptEntry = findViewById(R.id.tVReceiptEntry);
        tVCashWithdraw = findViewById(R.id.tVCashWithdraw);
        tVPurchaseWOItems = findViewById(R.id.tVPurchaseWOItems);
        tVSalesWOItem = findViewById(R.id.tVSalesWOItem);
        tVDebitNoteItem = findViewById(R.id.tVDebitNoteItem);
        tVCreditNoteItem = findViewById(R.id.tVCreditNoteItem);
        tVCashDeposit = findViewById(R.id.tVCashDeposit);
        tVBankTransfer = findViewById(R.id.tVBankTransfer);
        tVPartyStatement = findViewById(R.id.tVPartyStatement);
        tVSummary = findViewById(R.id.tVSummary);
        tVTransactions = findViewById(R.id.tVTransactions);
        tVItems = findViewById(R.id.tVItems);
        tVSummaryAccount = findViewById(R.id.tVSummaryAccount);
        tVSummaryTransaction = findViewById(R.id.tVSummaryTransaction);
        tVSummaryInventory = findViewById(R.id.tVSummaryInventory);
        tVItemProducts = findViewById(R.id.tVItemProducts);
        tVItemServices = findViewById(R.id.tVItemServices);
        tVItemUnit = findViewById(R.id.tVItemUnit);
        tVAsOfDate = findViewById(R.id.tVAsOfDate);
        tVDate = findViewById(R.id.tVDate);

        tVPartyOutstandingPayable = findViewById(R.id.tVPartyOutstandingPayable);
        tVPartyOutstandingReceivable = findViewById(R.id.tVPartyOutstandingReceivable);
        tVPartyAgedPayable = findViewById(R.id.tVPartyAgedPayable);
        tVPartyAgedReceivable = findViewById(R.id.tVPartyAgedReceivable);
        tVSupplierTxnSummary = findViewById(R.id.tVSupplierTxnSummary);
        tVCustomerTxnSummary = findViewById(R.id.tVCustomerTxnSummary);
        tVBankStatement = findViewById(R.id.tVBankStatement);
        tVBankBalance = findViewById(R.id.tVBankBalance);
        tVPostDatedChequeReport = findViewById(R.id.tVPostDatedChequeReport);
        tVAccountStatement = findViewById(R.id.tVAccountStatement);
        tVCashBook = findViewById(R.id.tVCashBook);
        tVJournalBook = findViewById(R.id.tVJournalBook);
        tVItemStatement = findViewById(R.id.tVItemStatement);
        tVItemSummary = findViewById(R.id.tVItemSummary);
        tVItemClosingBalance = findViewById(R.id.tVItemClosingBalance);
        tVSalesTaxRegister = findViewById(R.id.tVSalesTaxRegister);
        tVPurchaseTaxRegister = findViewById(R.id.tVPurchaseTaxRegister);
        tVBalanceSheet = findViewById(R.id.tVBalanceSheet);
        tVProfitAndLossAccount = findViewById(R.id.tVProfitAndLossAccount);
        tVTrialBalance = findViewById(R.id.tVTrialBalance);

        iVDashboard = findViewById(R.id.iVDashboard);
        iVAccount = findViewById(R.id.iVAccount);
        iVTransaction = findViewById(R.id.iVTransaction);
        iVReport = findViewById(R.id.iVReport);
        iVMore = findViewById(R.id.iVMore);
        iVSummary = findViewById(R.id.iVSummary);
        iVTransactions = findViewById(R.id.iVTransactions);
        iVItems = findViewById(R.id.iVItems);

        rVCustomer = findViewById(R.id.rVCustomer);
        rVSupplier = findViewById(R.id.rVSupplier);
        rVAccounts = findViewById(R.id.rVAccounts);
        rVAccountDetails = findViewById(R.id.rVAccountDetails);
        rVTransactionDetails = findViewById(R.id.rVTransactionDetails);
        rVInventoryDetails = findViewById(R.id.rVInventoryDetails);
        rVItemsProduct = findViewById(R.id.rVItemsProduct);
        rVItemsServices = findViewById(R.id.rVItemsServices);
        rVItemsUnit = findViewById(R.id.rVItemsUnit);
        rVTransactionsDetails = findViewById(R.id.rVTransactionsDetails);

        switchCompat = findViewById(R.id.switchCompact);
        inflater = LayoutInflater.from(activity);

        clickListener();
    }

    private void darkMode() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            switchCompat.setChecked(true);

        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                restartApp();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                restartApp();
            }
        });
    }

    private void restartApp() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void clickListener() {
        lLDashboard.setOnClickListener(activity);
        lLAccount.setOnClickListener(activity);
        lLTransaction.setOnClickListener(activity);
        lLReport.setOnClickListener(activity);
        lLMore.setOnClickListener(activity);
        lLSummary.setOnClickListener(activity);
        lLTransactions.setOnClickListener(activity);
        lLItems.setOnClickListener(activity);

        tVCustomerDetails.setOnClickListener(activity);
        tVSupplierDetails.setOnClickListener(activity);
        tVAccountDetails.setOnClickListener(activity);
        tVSalesInvoice.setOnClickListener(activity);
        tVSalesOrder.setOnClickListener(activity);
        tVSalesChallan.setOnClickListener(activity);
        tVPurchaseInvoice.setOnClickListener(activity);
        tVSalesReturn.setOnClickListener(activity);
        tVJournalEntry.setOnClickListener(activity);
        tVPaymentEntry.setOnClickListener(activity);
        tVPurchaseReturn.setOnClickListener(activity);
        tVReceiptEntry.setOnClickListener(activity);
        tVCashWithdraw.setOnClickListener(activity);
        tVPurchaseWOItems.setOnClickListener(activity);
        tVSalesWOItem.setOnClickListener(activity);
        tVDebitNoteItem.setOnClickListener(activity);
        tVCreditNoteItem.setOnClickListener(activity);
        tVCashDeposit.setOnClickListener(activity);
        tVBankTransfer.setOnClickListener(activity);
        tVPartyStatement.setOnClickListener(activity);
        tVAsOfDate.setOnClickListener(activity);
        tVDate.setOnClickListener(activity);


        tVPartyOutstandingPayable.setOnClickListener(activity);
        tVPartyOutstandingReceivable.setOnClickListener(activity);
        tVPartyAgedPayable.setOnClickListener(activity);
        tVPartyAgedReceivable.setOnClickListener(activity);
        tVSupplierTxnSummary.setOnClickListener(activity);
        tVCustomerTxnSummary.setOnClickListener(activity);
        tVBankStatement.setOnClickListener(activity);
        tVBankBalance.setOnClickListener(activity);
        tVPostDatedChequeReport.setOnClickListener(activity);
        tVAccountStatement.setOnClickListener(activity);
        tVCashBook.setOnClickListener(activity);
        tVJournalBook.setOnClickListener(activity);
        tVItemStatement.setOnClickListener(activity);
        tVItemSummary.setOnClickListener(activity);
        tVItemClosingBalance.setOnClickListener(activity);
        tVSalesTaxRegister.setOnClickListener(activity);
        tVPurchaseTaxRegister.setOnClickListener(activity);
        tVBalanceSheet.setOnClickListener(activity);
        tVProfitAndLossAccount.setOnClickListener(activity);
        tVTrialBalance.setOnClickListener(activity);
        tVSummaryAccount.setOnClickListener(activity);
        tVSummaryTransaction.setOnClickListener(activity);
        tVSummaryInventory.setOnClickListener(activity);
        tVItemProducts.setOnClickListener(activity);
        tVItemServices.setOnClickListener(activity);
        tVItemUnit.setOnClickListener(activity);
    }


    private void setDate() {
//        progressDialog.show();
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String fromDate = preferences.getString(AppTexts.spFromDate, "");
//        String toDate = preferences.getString(AppTexts.spToDate, "");

        //load ledger for the first time
//        getLedger(fromDate, toDate);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calendar.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calendar.getTime());
        tVAsOfDate.setText(currentDateNepali);
        tVDate.setText(currentDateNepali);
        currentNepaliDate = currentDateNepali;
    }


    private void showPopUpWindow() {
        View popUpView = getLayoutInflater().inflate(R.layout.layout_popup_window, null); // inflating popup layout
        popupWindow = new PopupWindow(popUpView, 400, ListPopupWindow.WRAP_CONTENT, true); // Creation of popup
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow.showAtLocation(popUpView, Gravity.TOP, 270, 150); // Displaying popup

        LinearLayout lLLogout = popUpView.findViewById(R.id.lLLogout);
        LinearLayout lLReset = popUpView.findViewById(R.id.lLReset);

        lLLogout.setOnClickListener(v -> {
            Intent logoutIntent = new Intent(activity, LoginSplashActivity.class);
            startActivity(logoutIntent);
            finish();
        });

        lLReset.setOnClickListener(v -> {
            Intent resetPasswordIntent = new Intent(activity, ResetPasswordActivity.class);
            startActivity(resetPasswordIntent);
            finish();
        });

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    private Response.Listener<JSONObject> passwordResetResponse() {
        return response -> {
            System.out.println("response:: " + response);
//                progressDialog.dismiss();
        };
    }

    public void initNavigationBar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity,
                drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(activity);

//        View headerView = navigationView.getHeaderView(0);
//        ImageView iVNavImageView = headerView.findViewById(R.id.imageViewHeaderLogo);
//        ImageUtil.showNetworkImage(activity, preferences.getString(AppText.spOfficeLogo, ""), iVNavImageView);
        initNavigationList();
    }


    private void initNavigationList() {
        prepareListDataAsRole();
        ExpandableListView expListView = findViewById(R.id.lvExp);
        ExpandableListAdapterParent expListAdapter = new ExpandableListAdapterParent(activity, listDataHeader, listDataChild, listDataChildTwo, listIcons);
        expListView.setAdapter(expListAdapter);

        expListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            String headerName = listDataHeader.get(groupPosition);
            System.out.println("headerName::: " + headerName);
            if (!headerName.equals("Master") &&
//                    !headerName.equals("Transaction") &&
//                    !headerName.equals("Reports") &&
                    !headerName.equals("Fiscal Year")) {
                selectHeaderItem(headerName);
                return true;
            }
            System.out.println("pos1::::: " + groupPosition);
            return false;
        });

        expListAdapter.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String headerName = listDataHeader.get(groupPosition);
            if (!headerName.equals("Master") &&
//                    !headerName.equals("Transaction") &&
//                    !headerName.equals("Reports") &&
                    !headerName.equals("Fiscal Year")) {
                return false;
            }
            String lvlTwoHeaderName = listDataChild.get(headerName).get(childPosition);
            if (lvlTwoHeaderName.equals("Account") ||
                    lvlTwoHeaderName.equals("Inventory") ||
                    lvlTwoHeaderName.equals("Warehouse") ||
                    lvlTwoHeaderName.equals("Accounting") ||
                    lvlTwoHeaderName.equals("Banking") ||
                    lvlTwoHeaderName.equals("Sales") ||
                    lvlTwoHeaderName.equals("Purchase") ||
                    lvlTwoHeaderName.equals("Account Book") ||
                    lvlTwoHeaderName.equals("Inventory Book") ||
                    lvlTwoHeaderName.equals("Party Reports")) {
                return false;
            }
            selectChildItem(groupPosition, childPosition);
            return false;
        });

        expListAdapter.setOnThirdChildClickListener(this::selectThirdChildItem);
    }


    private void selectChildItem(int groupPos, int childPos) {
        String header = listDataHeader.get(groupPos);
        System.out.println("headerName:::: " + header);
        switch (header) {
            case "Fiscal Year":
                switch (childPos) {
                    case 0:
                        Toast.makeText(activity, "New Fiscal Year...........", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Toast.makeText(activity, "Change Fiscal Year...........", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void selectThirdChildItem(String groupName, int childPos) {
        switch (groupName) {
            case "Account":
                switch (childPos) {
                    case 0:
                        startActivity(new Intent(activity, AccountGroupActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(activity, AccountSubGroupActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(activity, CreateAccountActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(activity, AddSupplierActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(activity, AddCustomerActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(activity, CreateBankActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(activity, CreateExpensesActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(activity, CreateIncomeActivity.class));
                        break;
                }
                break;

            case "Inventory":
                switch (childPos) {
                    case 0:
                        startActivity(new Intent(activity, ItemCategoryActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(activity, ItemSubGroupActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(activity, AddItemsActivity.class));
                        break;

                    case 3:
                        startActivity(new Intent(activity, AddMeasurementUnitActivity.class));
                        break;
                }
                break;

            case "Warehouse":
                switch (childPos) {
                    case 0:
                        startActivity(new Intent(activity, CreateWarehouseGroupActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(activity, CreateWarehouseActivity.class));
                        break;
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void selectHeaderItem(String name) {
        switch (name) {
            case "Transaction":
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.white));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.white));

                lLDashboardItems.setVisibility(View.GONE);
                lLTransactionDetails.setVisibility(View.VISIBLE);
                lLAccountsItems.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.GONE);
                break;

            case "Reports":
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.white));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.white));

                lLDashboardItems.setVisibility(View.GONE);
                lLAccountsItems.setVisibility(View.GONE);
                lLTransactionDetails.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.VISIBLE);
                break;

            case "Setting":
                Toast.makeText(activity, "Settings.....", Toast.LENGTH_SHORT).show();
                break;

            case "Logout":
                preferences.edit().putBoolean(AppTexts.loggedin,false).apply();
                Intent intent = new Intent(activity, LoginSplashActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void prepareListDataAsRole() {
        listDataHeader = new ArrayList<>();
        listIcons = new ArrayList<>();
        listDataChild = new HashMap<>();
        listDataChildTwo = new HashMap<>();

        //first header
        listDataHeader.add("Master");
        listIcons.add(R.drawable.user);
        List<String> master = new ArrayList<>();
        master.add("Account");
        master.add("Inventory");
        master.add("Warehouse");
        listDataChild.put("Master", master);

        List<String> accountList = new ArrayList<>();
        accountList.add("Account Group");
        accountList.add("Account SubGroup");
        accountList.add("Account Ledger");
        accountList.add("Supplier Ledger");
        accountList.add("Customer Ledger");
        accountList.add("Bank Ledger");
        accountList.add("Expenses Ledger");
        accountList.add("Income Ledger");
        listDataChildTwo.put("Account", accountList);

        List<String> inventoryList = new ArrayList<>();
        inventoryList.add("Item Group");
        inventoryList.add("Item Sub Group");
        inventoryList.add("Item");
        inventoryList.add("Unit");
        listDataChildTwo.put("Inventory", inventoryList);

        List<String> warehouseList = new ArrayList<>();
        warehouseList.add("Warehouse Group");
        warehouseList.add("Warehouse");
        listDataChildTwo.put("Warehouse", warehouseList);

        //second Header
        listDataHeader.add("Transaction");
        listIcons.add(R.drawable.calculator);

        //Third Header
        listDataHeader.add("Reports");
        listIcons.add(R.drawable.summary);

        //Fourth Header
        listDataHeader.add("Fiscal Year");
        listIcons.add(R.drawable.calendar);
        List<String> fiscalYears = new ArrayList<>();
        fiscalYears.add("New Fiscal Year");
        fiscalYears.add("Change Fiscal Year");
        listDataChild.put("Fiscal Year", fiscalYears);

        //Fifth Header
        listDataHeader.add("Setting");
        listIcons.add(R.drawable.settings);

        //Sixth Header
        listDataHeader.add("Logout");
        listIcons.add(R.drawable.logout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }


    private void getAllDatas() {

    }

    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
//                progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLDashboard:
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.white));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.white));

                lLDashboardItems.setVisibility(View.VISIBLE);
                lLAccountsItems.setVisibility(View.GONE);
                lLTransactionDetails.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.GONE);

                break;

            case R.id.lLAccount:
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.white));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.white));

                lLDashboardItems.setVisibility(View.GONE);
                lLAccountsItems.setVisibility(View.VISIBLE);
                lLTransactionDetails.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.GONE);

                tVCustomerDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVSupplierDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVAccountDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSupplierDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccountDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVCustomerDetails.setTextColor(ContextCompat.getColor(activity, R.color.black));

                lLCustomerDetails.setVisibility(View.VISIBLE);
                lLSupplierDetails.setVisibility(View.GONE);
                rVAccounts.setVisibility(View.GONE);

                getAccountCustomer();

                break;

            case R.id.lLTransaction:
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.white));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.white));

                lLDashboardItems.setVisibility(View.GONE);
                lLTransactionDetails.setVisibility(View.VISIBLE);
                lLAccountsItems.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.GONE);
                break;

            case R.id.lLReport:
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.white));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.black));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.white));

                lLDashboardItems.setVisibility(View.GONE);
                lLAccountsItems.setVisibility(View.GONE);
                lLTransactionDetails.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.VISIBLE);
                break;

            case R.id.lLMore:
                lLDashboard.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLReport.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLMore.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                lLAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVDashboard.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVReport.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVMore.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));

                iVDashboard.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVAccount.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransaction.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVReport.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVMore.setColorFilter(ContextCompat.getColor(activity, R.color.black));

                lLDashboardItems.setVisibility(View.GONE);
                lLTransactionDetails.setVisibility(View.GONE);
                lLAccountsItems.setVisibility(View.GONE);
                lLReportDetails.setVisibility(View.GONE);
                break;


            // items from dashboard
            case R.id.lLSummary:
                lLItems.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransactions.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLSummary.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));

                tVItems.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransactions.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSummary.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));

                iVItems.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransactions.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVSummary.setColorFilter(ContextCompat.getColor(activity, R.color.black));


                //showing account all the time on click to layout
                tVSummaryAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSummaryInventory.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVSummaryAccount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVSummaryInventory.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));

                //ends here

                lLSummaryDetails.setVisibility(View.VISIBLE);
                lLItemsDetails.setVisibility(View.GONE);
                lLDateSelection.setVisibility(View.VISIBLE);

                rVAccountDetails.setVisibility(View.VISIBLE);
                rVTransactionDetails.setVisibility(View.GONE);
                rVInventoryDetails.setVisibility(View.GONE);
                rVTransactionsDetails.setVisibility(View.GONE);
                lLItemDateSelection.setVisibility(View.GONE);

                hScrollViewProduct.setVisibility(View.GONE);
                hScrollViewService.setVisibility(View.GONE);
                lLItemsUnit.setVisibility(View.GONE);

                getAccountData();
                break;

            case R.id.lLTransactions:
                lLItems.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLSummary.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransactions.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));

                tVItems.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSummary.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransactions.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));

                iVItems.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVSummary.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransactions.setColorFilter(ContextCompat.getColor(activity, R.color.black));

                lLSummaryDetails.setVisibility(View.GONE);
                lLDateSelection.setVisibility(View.GONE);
                lLItemsDetails.setVisibility(View.GONE);

                lLItemDateSelection.setVisibility(View.GONE);

                rVAccountDetails.setVisibility(View.GONE);
                rVTransactionDetails.setVisibility(View.GONE);
                rVInventoryDetails.setVisibility(View.GONE);

                rVTransactionsDetails.setVisibility(View.VISIBLE);

                hScrollViewProduct.setVisibility(View.GONE);
                hScrollViewService.setVisibility(View.GONE);
                lLItemsUnit.setVisibility(View.GONE);
                getDashboardTransaction();
                break;

            case R.id.lLItems:
                lLSummary.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLTransactions.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                lLItems.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));

                tVSummary.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVTransactions.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVItems.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));

                iVSummary.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVTransactions.setColorFilter(ContextCompat.getColor(activity, R.color.white));
                iVItems.setColorFilter(ContextCompat.getColor(activity, R.color.black));


                //showing products all the time
                tVItemProducts.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVItemServices.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVItemUnit.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVItemProducts.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVItemServices.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVItemUnit.setTextColor(ContextCompat.getColor(activity, R.color.white));
                //ends here

                lLSummaryDetails.setVisibility(View.GONE);
                lLDateSelection.setVisibility(View.GONE);
                lLItemsDetails.setVisibility(View.VISIBLE);

                lLItemDateSelection.setVisibility(View.VISIBLE);

                rVAccountDetails.setVisibility(View.GONE);
                rVTransactionDetails.setVisibility(View.GONE);
                rVInventoryDetails.setVisibility(View.GONE);
                rVTransactionsDetails.setVisibility(View.GONE);


                hScrollViewProduct.setVisibility(View.VISIBLE);
                hScrollViewService.setVisibility(View.GONE);
                lLItemsUnit.setVisibility(View.GONE);

                getItemProducts();
                break;


            case R.id.tVSummaryAccount:
                tVSummaryAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSummaryInventory.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVSummaryAccount.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVSummaryInventory.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));

                rVAccountDetails.setVisibility(View.VISIBLE);
                rVTransactionDetails.setVisibility(View.GONE);
                rVInventoryDetails.setVisibility(View.GONE);
                setDate();
                getAccountData();
                break;

            case R.id.tVSummaryTransaction:
                tVSummaryTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSummaryInventory.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVSummaryTransaction.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVSummaryAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryInventory.setTextColor(ContextCompat.getColor(activity, R.color.white));

                rVAccountDetails.setVisibility(View.GONE);
                rVTransactionDetails.setVisibility(View.VISIBLE);
                rVInventoryDetails.setVisibility(View.GONE);
                setDate();
                getTransactionData();
                break;

            case R.id.tVSummaryInventory:
                tVSummaryInventory.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryAccount.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSummaryTransaction.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVSummaryInventory.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVSummaryAccount.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSummaryTransaction.setTextColor(ContextCompat.getColor(activity, R.color.white));

                rVAccountDetails.setVisibility(View.GONE);
                rVTransactionDetails.setVisibility(View.GONE);
                rVInventoryDetails.setVisibility(View.VISIBLE);
                setDate();
                getInventoryData();
                break;

            //items details here........
            case R.id.tVItemProducts:
                tVItemProducts.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVItemServices.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVItemUnit.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVItemProducts.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVItemServices.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVItemUnit.setTextColor(ContextCompat.getColor(activity, R.color.white));


                hScrollViewProduct.setVisibility(View.VISIBLE);
                hScrollViewService.setVisibility(View.GONE);
                lLItemsUnit.setVisibility(View.GONE);
                setDate();
                getItemProducts();
                break;

            case R.id.tVItemServices:
                tVItemServices.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVItemProducts.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVItemUnit.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVItemServices.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVItemProducts.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVItemUnit.setTextColor(ContextCompat.getColor(activity, R.color.white));

                hScrollViewProduct.setVisibility(View.GONE);
                hScrollViewService.setVisibility(View.VISIBLE);
                lLItemsUnit.setVisibility(View.GONE);
                setDate();
                getItemServices();
                break;

            case R.id.tVItemUnit:
                tVItemUnit.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVItemServices.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVItemProducts.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

                tVItemUnit.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVItemServices.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVItemProducts.setTextColor(ContextCompat.getColor(activity, R.color.white));

                hScrollViewProduct.setVisibility(View.GONE);
                hScrollViewService.setVisibility(View.GONE);
                lLItemsUnit.setVisibility(View.VISIBLE);
                setDate();
                getItemUnit();
                break;


            case R.id.tVAsOfDate:
                tVAsOfDate.setSelected(true);
                tVDate.setSelected(false);
                nepaliCalender();
                break;

            case R.id.tVDate:
                tVAsOfDate.setSelected(false);
                tVDate.setSelected(true);
                nepaliCalender();
                break;


            //Dashboard Details Ends Here

            //for account
            case R.id.tVCustomerDetails:
                tVCustomerDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVSupplierDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVAccountDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSupplierDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccountDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVCustomerDetails.setTextColor(ContextCompat.getColor(activity, R.color.black));

                lLCustomerDetails.setVisibility(View.VISIBLE);
                lLSupplierDetails.setVisibility(View.GONE);
                rVAccounts.setVisibility(View.GONE);
//                lLNoData.setVisibility(View.GONE);

                getAccountCustomer();
                break;

            case R.id.tVSupplierDetails:
                tVCustomerDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSupplierDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVAccountDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSupplierDetails.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVCustomerDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVAccountDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));


                lLCustomerDetails.setVisibility(View.GONE);
                lLSupplierDetails.setVisibility(View.VISIBLE);
                rVAccounts.setVisibility(View.GONE);
//                lLNoData.setVisibility(View.GONE);

                getAccountSupplier();
                break;

            case R.id.tVAccountDetails:
                tVCustomerDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVSupplierDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVAccountDetails.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVAccountDetails.setTextColor(ContextCompat.getColor(activity, R.color.black));
                tVCustomerDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVSupplierDetails.setTextColor(ContextCompat.getColor(activity, R.color.white));

                lLCustomerDetails.setVisibility(View.GONE);
                lLSupplierDetails.setVisibility(View.GONE);
                rVAccounts.setVisibility(View.VISIBLE);
//                lLNoData.setVisibility(View.VISIBLE);
                getAccountMaster();
                break;

            //accounts ends here

            case R.id.tVSalesOrder:
                View salesOrderDialog = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder salesOrderBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                salesOrderBuilder.setView(salesOrderDialog);

                TextView tVCancelOrder = salesOrderDialog.findViewById(R.id.tVCancel);
                tVCancelOrder.setText(R.string.cancel);
                TextView tVInvoiceOrder = salesOrderDialog.findViewById(R.id.tVInvoice);
                tVInvoiceOrder.setText(R.string.salesOrder);
                TextView tVShowOrder = salesOrderDialog.findViewById(R.id.tVShow);
                tVShowOrder.setText(getString(R.string.view));
                TextView tVCreateOrder = salesOrderDialog.findViewById(R.id.tVCreate);
                tVCreateOrder.setText(getString(R.string.createOrder));

                tVCreateOrder.setOnClickListener(v14 -> {
                    Intent intent = new Intent(activity, SalesOrderActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVShowOrder.setOnClickListener(v18 -> {
                    Intent intent = new Intent(activity, SalesOrderViewActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVCancelOrder.setOnClickListener(v13 -> dialog.dismiss());


                salesOrderBuilder.setCancelable(false);
                dialog = salesOrderBuilder.create();
                Window salesOrderWindow = dialog.getWindow();
                salesOrderWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                salesOrderWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;


            case R.id.tVSalesChallan:
                View saleChallanDialog = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder saleChallanBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                saleChallanBuilder.setView(saleChallanDialog);

                TextView tVCancelChallan = saleChallanDialog.findViewById(R.id.tVCancel);
                tVCancelChallan.setText(R.string.cancel);
                TextView tVInvoiceChallan = saleChallanDialog.findViewById(R.id.tVInvoice);
                tVInvoiceChallan.setText(R.string.salesChallan);
                TextView tVShowChallan = saleChallanDialog.findViewById(R.id.tVShow);
                tVShowChallan.setText(getString(R.string.view));
                TextView tVCreateChallan = saleChallanDialog.findViewById(R.id.tVCreate);
                tVCreateChallan.setText(getString(R.string.createChallan));

                tVCreateChallan.setOnClickListener(v14 -> {
                    Intent intent = new Intent(activity, SalesChallanActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVShowChallan.setOnClickListener(v18 -> {
                    Intent intent = new Intent(activity, SalesChallanViewActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVCancelChallan.setOnClickListener(v13 -> dialog.dismiss());


                saleChallanBuilder.setCancelable(false);
                dialog = saleChallanBuilder.create();
                Window challanWindow = dialog.getWindow();
                challanWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                challanWindow.setGravity(Gravity.CENTER);
                dialog.show();

                break;


            case R.id.tVSalesInvoice:
                View bottomDialog = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                builder.setView(bottomDialog);

                TextView tVCancel = bottomDialog.findViewById(R.id.tVCancel);
                tVCancel.setText(R.string.cancel);
                TextView tVInvoice = bottomDialog.findViewById(R.id.tVInvoice);
                tVInvoice.setText(R.string.invoice);
                TextView tVShow = bottomDialog.findViewById(R.id.tVShow);
                tVShow.setText(getString(R.string.view));
                TextView tVCreate = bottomDialog.findViewById(R.id.tVCreate);
                tVCreate.setText(getString(R.string.createInvoice));

                tVCreate.setOnClickListener(v14 -> {
                    Intent intent = new Intent(activity, SalesInvoiceActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVShow.setOnClickListener(v18 -> {
                    Intent intent = new Intent(activity, InvoiceViewActivity.class);
                    intent.putExtra(AppTexts.salesInvoice, AppTexts.saleInvoice);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVCancel.setOnClickListener(v13 -> dialog.dismiss());


                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                dialog.show();

                break;


            case R.id.tVSalesReturn:
                View salesReturn = inflater.inflate(R.layout.layout_return, null);

                AlertDialog.Builder salesReturnBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                salesReturnBuilder.setView(salesReturn);

                TextView tVReturnType = salesReturn.findViewById(R.id.tVReturnType);
                TextView tVReturn = salesReturn.findViewById(R.id.tVReturn);
                TextView tVReturnByBill = salesReturn.findViewById(R.id.tVReturnByBill);
                TextView tVView = salesReturn.findViewById(R.id.tVView);
                TextView tVCancelDialog = salesReturn.findViewById(R.id.tVCancelDialog);

                tVReturnType.setText(R.string.salesReturnType);
                tVReturn.setText(R.string.returnSales);
                tVReturnByBill.setText(R.string.returnSalesByBill);
                tVView.setText(R.string.view);
                tVCancelDialog.setText(R.string.cancel);


                tVReturn.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, SalesReturnActivity.class));
                    dialog.dismiss();
                });

                tVReturnByBill.setOnClickListener(v16 -> {
                    startActivity(new Intent(activity, SalesReturnByBillActivity.class));
                    dialog.dismiss();
                });

                tVView.setOnClickListener(v19 -> {
                    Intent intent = new Intent(activity, InvoiceViewActivity.class);
                    intent.putExtra(AppTexts.salesInvoice, AppTexts.saleReturn);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVCancelDialog.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });


                salesReturnBuilder.setCancelable(false);
                dialog = salesReturnBuilder.create();
                Window salesReturnWindow = dialog.getWindow();
                salesReturnWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                salesReturnWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVPurchaseInvoice:
                View purchaseInvoice = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder purchaseBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                purchaseBuilder.setView(purchaseInvoice);

                TextView cancelPurchase = purchaseInvoice.findViewById(R.id.tVCancel);
                cancelPurchase.setText(R.string.cancel);
                TextView dialogTitle = purchaseInvoice.findViewById(R.id.tVInvoice);
                dialogTitle.setText(R.string.purchaseInvoice);
                TextView tVViewPurchaseInvoice = purchaseInvoice.findViewById(R.id.tVShow);
                tVViewPurchaseInvoice.setText(getString(R.string.view));
                TextView tVCreatePurchaseInvoice = purchaseInvoice.findViewById(R.id.tVCreate);
                tVCreatePurchaseInvoice.setText(getString(R.string.createInvoice));

                tVCreatePurchaseInvoice.setOnClickListener(v1 -> {
                    Intent intent = new Intent(activity, PurchaseInvoiceActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVViewPurchaseInvoice.setOnClickListener(v110 -> {
                    Intent intent = new Intent(activity, InvoiceViewActivity.class);
                    intent.putExtra(AppTexts.salesInvoice, AppTexts.purchaseInvoice

                    );
                    startActivity(intent);
                    dialog.dismiss();
                });

                cancelPurchase.setOnClickListener(v12 -> dialog.dismiss());


                purchaseBuilder.setCancelable(false);
                dialog = purchaseBuilder.create();
                Window purchaseWindow = dialog.getWindow();
                purchaseWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                purchaseWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVPurchaseReturn:
                View purchaseReturn = inflater.inflate(R.layout.layout_return, null);

                AlertDialog.Builder purchaseReturnBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                purchaseReturnBuilder.setView(purchaseReturn);

                TextView tVPurReturnType = purchaseReturn.findViewById(R.id.tVReturnType);
                TextView tVPurReturn = purchaseReturn.findViewById(R.id.tVReturn);
                TextView tVPurReturnByBill = purchaseReturn.findViewById(R.id.tVReturnByBill);
                TextView tVPurCancelDialog = purchaseReturn.findViewById(R.id.tVCancelDialog);
                TextView tVPurchaseView = purchaseReturn.findViewById(R.id.tVView);

                tVPurReturnType.setText(R.string.purchaseReturnType);
                tVPurReturn.setText(R.string.returnPurchase);
                tVPurReturnByBill.setText(R.string.returnPurchaseByBill);
                tVPurchaseView.setText(R.string.view);
                tVPurCancelDialog.setText(R.string.cancel);


                tVPurReturn.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, PurchaseReturnActivity.class));
                    dialog.dismiss();
                });

                tVPurReturnByBill.setOnClickListener(v16 -> {
                    startActivity(new Intent(activity, PurchaseReturnByBillActivity.class));
                    dialog.dismiss();
                });

                tVPurchaseView.setOnClickListener(v111 -> {
                    Intent intent = new Intent(activity, InvoiceViewActivity.class);
                    intent.putExtra(AppTexts.salesInvoice, AppTexts.purchaseReturn);
                    startActivity(intent);
                    dialog.dismiss();
                });

                tVPurCancelDialog.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });


                purchaseReturnBuilder.setCancelable(false);
                dialog = purchaseReturnBuilder.create();
                Window purchaseReturnWindow = dialog.getWindow();
                purchaseReturnWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                purchaseReturnWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVJournalEntry:
                View journalEntry = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder journalEntryBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                journalEntryBuilder.setView(journalEntry);

                TextView tVCancelJournal = journalEntry.findViewById(R.id.tVCancel);
                tVCancelJournal.setText(R.string.cancel);
                TextView tVJournal = journalEntry.findViewById(R.id.tVInvoice);
                tVJournal.setText(R.string.journalEntry);
                TextView tVShowJournal = journalEntry.findViewById(R.id.tVShow);
                tVShowJournal.setText(getString(R.string.view));
                TextView tVCreateJournal = journalEntry.findViewById(R.id.tVCreate);
                tVCreateJournal.setText(getString(R.string.entry));


                tVCreateJournal.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, EntryActivity.class));
                    dialog.dismiss();
                });


                tVCancelJournal.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowJournal.setOnClickListener(v112 -> {
                    Intent intent = new Intent(activity, JournalViewActivity.class);
                    intent.putExtra(AppTexts.journalNDebitCreditNote, AppTexts.journalView);
                    startActivity(intent);
                    dialog.dismiss();
                });


                journalEntryBuilder.setCancelable(false);
                dialog = journalEntryBuilder.create();
                Window journalEntryWindow = dialog.getWindow();
                journalEntryWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                journalEntryWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVPaymentEntry:
                View paymentEntry = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder paymentEntryBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                paymentEntryBuilder.setView(paymentEntry);

                TextView tVCancelPayment = paymentEntry.findViewById(R.id.tVCancel);
                tVCancelPayment.setText(R.string.cancel);
                TextView tVPayment = paymentEntry.findViewById(R.id.tVInvoice);
                tVPayment.setText(R.string.paymentEntry);
                TextView tVShowPayment = paymentEntry.findViewById(R.id.tVShow);
                tVShowPayment.setText(getString(R.string.view));
                TextView tVCreatePayment = paymentEntry.findViewById(R.id.tVCreate);
                tVCreatePayment.setText(getString(R.string.entry));


                tVCreatePayment.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, PaymentDoubleEntryActivity.class));
                    dialog.dismiss();
                });


                tVCancelPayment.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowPayment.setOnClickListener(v115 -> {
                    Intent intent = new Intent(activity, JournalViewActivity.class);
                    intent.putExtra(AppTexts.journalNDebitCreditNote, AppTexts.paymentView);
                    startActivity(intent);
                    dialog.dismiss();
                });


                paymentEntryBuilder.setCancelable(false);
                dialog = paymentEntryBuilder.create();
                Window paymentEntryWindow = dialog.getWindow();
                paymentEntryWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paymentEntryWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVReceiptEntry:
                View receiptEntry = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder receiptEntryBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                receiptEntryBuilder.setView(receiptEntry);

                TextView tVCancelReceipt = receiptEntry.findViewById(R.id.tVCancel);
                tVCancelReceipt.setText(R.string.cancel);
                TextView tVReceipt = receiptEntry.findViewById(R.id.tVInvoice);
                tVReceipt.setText(R.string.receiptEntry);
                TextView tVShowReceipt = receiptEntry.findViewById(R.id.tVShow);
                tVShowReceipt.setText(getString(R.string.view));
                TextView tVCreateReceipt = receiptEntry.findViewById(R.id.tVCreate);
                tVCreateReceipt.setText(getString(R.string.entry));


                tVCreateReceipt.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, ReceiptDoubleEntryActivity.class));
                    dialog.dismiss();
                });


                tVCancelReceipt.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowReceipt.setOnClickListener(v116 -> {
                    Intent intent = new Intent(activity, JournalViewActivity.class);
                    intent.putExtra(AppTexts.journalNDebitCreditNote, AppTexts.receiptView);
                    startActivity(intent);
                    dialog.dismiss();
                });


                receiptEntryBuilder.setCancelable(false);
                dialog = receiptEntryBuilder.create();
                Window receiptEntryWindow = dialog.getWindow();
                receiptEntryWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                receiptEntryWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVPurchaseWOItems:
                View purchaseWOItems = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder purchaseWOItemsBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                purchaseWOItemsBuilder.setView(purchaseWOItems);

                TextView tVCancelPurchaseWOItems = purchaseWOItems.findViewById(R.id.tVCancel);
                tVCancelPurchaseWOItems.setText(R.string.cancel);
                TextView tVPurchaseWOItems = purchaseWOItems.findViewById(R.id.tVInvoice);
                tVPurchaseWOItems.setText(R.string.purchaseWithoutItems);
                TextView tVShowPurchaseWOItems = purchaseWOItems.findViewById(R.id.tVShow);
                tVShowPurchaseWOItems.setText(getString(R.string.view));
                TextView tVCreatePurchaseWOItems = purchaseWOItems.findViewById(R.id.tVCreate);
                tVCreatePurchaseWOItems.setText(getString(R.string.entry));


                tVCreatePurchaseWOItems.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, PurchaseWOItemsActivity.class));
                    dialog.dismiss();
                });


                tVCancelPurchaseWOItems.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowPurchaseWOItems.setOnClickListener(v117 -> {
                    Toast.makeText(activity, "Sorry, Feature not Available currently, will be soon available", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });


                purchaseWOItemsBuilder.setCancelable(false);
                dialog = purchaseWOItemsBuilder.create();
                Window purchaseWOItemsWindow = dialog.getWindow();
                purchaseWOItemsWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                purchaseWOItemsWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVSalesWOItem:
                View saleWOItems = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder saleWOItemsBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                saleWOItemsBuilder.setView(saleWOItems);

                TextView tVCancelSaleWOItems = saleWOItems.findViewById(R.id.tVCancel);
                tVCancelSaleWOItems.setText(R.string.cancel);
                TextView tVSaleWOItems = saleWOItems.findViewById(R.id.tVInvoice);
                tVSaleWOItems.setText(R.string.salesWithoutItems);
                TextView tVShowSaleWOItems = saleWOItems.findViewById(R.id.tVShow);
                tVShowSaleWOItems.setText(getString(R.string.view));
                TextView tVCreateSaleWOItems = saleWOItems.findViewById(R.id.tVCreate);
                tVCreateSaleWOItems.setText(getString(R.string.entry));


                tVCreateSaleWOItems.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, SalesWOItemsActivity.class));
                    dialog.dismiss();
                });


                tVCancelSaleWOItems.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowSaleWOItems.setOnClickListener(v118 -> {
                    Toast.makeText(activity, "Sorry, Feature not Available currently, will be soon available", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });

                saleWOItemsBuilder.setCancelable(false);
                dialog = saleWOItemsBuilder.create();
                Window saleWOItemsWindow = dialog.getWindow();
                saleWOItemsWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                saleWOItemsWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVDebitNoteItem:
                View debitWOItems = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder debitWOItemsBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                debitWOItemsBuilder.setView(debitWOItems);

                TextView tVCancelDebitWOItems = debitWOItems.findViewById(R.id.tVCancel);
                tVCancelDebitWOItems.setText(R.string.cancel);
                TextView tVDebitWOItems = debitWOItems.findViewById(R.id.tVInvoice);
                tVDebitWOItems.setText(R.string.debitNote);
                TextView tVShowDebitWOItems = debitWOItems.findViewById(R.id.tVShow);
                tVShowDebitWOItems.setText(getString(R.string.view));
                TextView tVCreateDebitWOItems = debitWOItems.findViewById(R.id.tVCreate);
                tVCreateDebitWOItems.setText(getString(R.string.entry));


                tVCreateDebitWOItems.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, DebitNoteWOItems.class));
                    dialog.dismiss();
                });


                tVCancelDebitWOItems.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowDebitWOItems.setOnClickListener(v113 -> {
                    Intent intent = new Intent(activity, JournalViewActivity.class);
                    intent.putExtra(AppTexts.journalNDebitCreditNote, AppTexts.debitNote);
                    startActivity(intent);
                    dialog.dismiss();
                });

                debitWOItemsBuilder.setCancelable(false);
                dialog = debitWOItemsBuilder.create();
                Window debitWOItemsWindow = dialog.getWindow();
                debitWOItemsWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                debitWOItemsWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVCreditNoteItem:
                View creditWOItems = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder creditWOItemsBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                creditWOItemsBuilder.setView(creditWOItems);

                TextView tVCancelCreditWOItems = creditWOItems.findViewById(R.id.tVCancel);
                tVCancelCreditWOItems.setText(R.string.cancel);
                TextView tVCreditWOItems = creditWOItems.findViewById(R.id.tVInvoice);
                tVCreditWOItems.setText(R.string.creditNote);
                TextView tVShowCreditWOItems = creditWOItems.findViewById(R.id.tVShow);
                tVShowCreditWOItems.setText(getString(R.string.view));
                TextView tVCreateCreditWOItems = creditWOItems.findViewById(R.id.tVCreate);
                tVCreateCreditWOItems.setText(getString(R.string.entry));


                tVCreateCreditWOItems.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, CreditNoteWOItems.class));
                    dialog.dismiss();
                });


                tVCancelCreditWOItems.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowCreditWOItems.setOnClickListener(v114 -> {
                    Intent intent = new Intent(activity, JournalViewActivity.class);
                    intent.putExtra(AppTexts.journalNDebitCreditNote, AppTexts.creditNote);
                    startActivity(intent);
                    dialog.dismiss();
                });

                creditWOItemsBuilder.setCancelable(false);
                dialog = creditWOItemsBuilder.create();
                Window creditWOItemsWindow = dialog.getWindow();
                creditWOItemsWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                creditWOItemsWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVCashWithdraw:
                View cashWithdraw = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder cashWithdrawBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                cashWithdrawBuilder.setView(cashWithdraw);

                TextView tVCancelCashWithdraw = cashWithdraw.findViewById(R.id.tVCancel);
                tVCancelCashWithdraw.setText(R.string.cancel);
                TextView tVCashWithdraw = cashWithdraw.findViewById(R.id.tVInvoice);
                tVCashWithdraw.setText(R.string.cashWithdraw);
                TextView tVShowCashWithdraw = cashWithdraw.findViewById(R.id.tVShow);
                tVShowCashWithdraw.setText(getString(R.string.view));
                TextView tVCreateCashWithdraw = cashWithdraw.findViewById(R.id.tVCreate);
                tVCreateCashWithdraw.setText(getString(R.string.entry));


                tVCreateCashWithdraw.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, CashWithdrawlActivity.class));
                    dialog.dismiss();
                });


                tVCancelCashWithdraw.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowCashWithdraw.setOnClickListener(v119 -> {
                    Toast.makeText(activity, "Sorry, Feature not Available currently, will be soon available", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });

                cashWithdrawBuilder.setCancelable(false);
                dialog = cashWithdrawBuilder.create();
                Window cashWithdrawWindow = dialog.getWindow();
                cashWithdrawWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cashWithdrawWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVCashDeposit:
                View cashDeposit = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder cashDepositBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                cashDepositBuilder.setView(cashDeposit);

                TextView tVCancelCashDeposit = cashDeposit.findViewById(R.id.tVCancel);
                tVCancelCashDeposit.setText(R.string.cancel);
                TextView tVCashDeposit = cashDeposit.findViewById(R.id.tVInvoice);
                tVCashDeposit.setText(R.string.cashWithdraw);
                TextView tVShowCashDeposit = cashDeposit.findViewById(R.id.tVShow);
                tVShowCashDeposit.setText(getString(R.string.view));
                TextView tVCreateCashDeposit = cashDeposit.findViewById(R.id.tVCreate);
                tVCreateCashDeposit.setText(getString(R.string.entry));

                tVCreateCashDeposit.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, CashDepositActivity.class));
                    dialog.dismiss();
                });


                tVCancelCashDeposit.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowCashDeposit.setOnClickListener(v120 -> {
                    Toast.makeText(activity, "Sorry, Feature not Available currently, will be soon available", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });

                cashDepositBuilder.setCancelable(false);
                dialog = cashDepositBuilder.create();
                Window cashDepositWindow = dialog.getWindow();
                cashDepositWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                cashDepositWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVBankTransfer:
                View bankTransfer = inflater.inflate(R.layout.layout_sales_invoice_dialog, null);

                AlertDialog.Builder bankTransferBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                bankTransferBuilder.setView(bankTransfer);

                TextView tVCancelBankTransfer = bankTransfer.findViewById(R.id.tVCancel);
                tVCancelBankTransfer.setText(R.string.cancel);
                TextView tVBankTransfer = bankTransfer.findViewById(R.id.tVInvoice);
                tVBankTransfer.setText(R.string.bankTransfer);
                TextView tVShowBankTransfer = bankTransfer.findViewById(R.id.tVShow);
                tVShowBankTransfer.setText(getString(R.string.view));
                TextView tVCreateBankTransfer = bankTransfer.findViewById(R.id.tVCreate);
                tVCreateBankTransfer.setText(getString(R.string.entry));

                tVCreateBankTransfer.setOnClickListener(v15 -> {
                    startActivity(new Intent(activity, BankTransferActivity.class));
                    dialog.dismiss();
                });


                tVCancelBankTransfer.setOnClickListener(v17 -> {
                    dialog.dismiss();
                });

                tVShowBankTransfer.setOnClickListener(v121 -> {
                    Toast.makeText(activity, "Sorry, Feature not Available currently, will be soon available", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });

                bankTransferBuilder.setCancelable(false);
                dialog = bankTransferBuilder.create();
                Window bankTransferWindow = dialog.getWindow();
                bankTransferWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                bankTransferWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVPartyStatement:
                startActivity(new Intent(activity, PartyStatementActivity.class));
                break;

            case R.id.tVPartyOutstandingPayable:
                startActivity(new Intent(activity, PartyOutstandingPayableActivity.class));
                break;

            case R.id.tVPartyOutstandingReceivable:
                startActivity(new Intent(activity, PartyOutstandingReceivableActivity.class));
                break;

            case R.id.tVPartyAgedPayable:
                startActivity(new Intent(activity, PartyAgedPayableActivity.class));
                break;

            case R.id.tVPartyAgedReceivable:
                startActivity(new Intent(activity, PartyAgedReceivableActivity.class));
                break;

            case R.id.tVSupplierTxnSummary:
                startActivity(new Intent(activity, SupplierTransactionSummaryActivity.class));
                break;

            case R.id.tVCustomerTxnSummary:
                startActivity(new Intent(activity, CustomerTransactionSummaryActivity.class));
                break;

            case R.id.tVBankStatement:
                startActivity(new Intent(activity, BankStatementActivity.class));
                break;
            case R.id.tVBankBalance:
                startActivity(new Intent(activity, BankBalanceActivity.class));
                break;

            case R.id.tVPostDatedChequeReport:
                startActivity(new Intent(activity, PostdatedChequeReportActivity.class));
                break;

            case R.id.tVAccountStatement:
                startActivity(new Intent(activity, AccountStatementLedgerActivity.class));
                break;

            case R.id.tVCashBook:
                startActivity(new Intent(activity, CashBookReportActivity.class));
                break;

            case R.id.tVJournalBook:
                Intent intent = new Intent(activity, JournalViewActivity.class);
                intent.putExtra(AppTexts.journalNDebitCreditNote, AppTexts.journalBook);
                startActivity(intent);
                break;

            case R.id.tVItemStatement:
                startActivity(new Intent(activity, ItemStatementActivity.class));
                break;

            case R.id.tVItemSummary:
                startActivity(new Intent(activity, ItemSummaryActivity.class));
                break;

            case R.id.tVItemClosingBalance:
                startActivity(new Intent(activity, ItemClosingBalanceActivity.class));
                break;

            case R.id.tVSalesTaxRegister:
                Toast.makeText(activity, "sorry, the report is currently unavailabe......", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tVPurchaseTaxRegister:
                Toast.makeText(activity, "sorry, the report is currently unavailabe.......", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tVBalanceSheet:
                startActivity(new Intent(activity, BalanceSheetActivity.class));
                break;

            case R.id.tVProfitAndLossAccount:
                startActivity(new Intent(activity, ProfitAndLossActivity.class));
                break;

            case R.id.tVTrialBalance:
                Toast.makeText(activity, "sorry, the report is currently unavailabe........", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    getAccountCustomer() {
        progressDialog.show();
        String url = APIs.customerMasterList + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest accountCustomerRequest = new APIRequest(
                Request.Method.GET,
                url,
                accountCustomerResponse(),
                activityErrorListener()
        );
        requestQueue.add(accountCustomerRequest);
        AppUtil.customizeRequest(accountCustomerRequest);
    }


    private Response.Listener<JSONObject> accountCustomerResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    customerSupplierLedgerDTOS = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        CustomerSupplierLedgerDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), CustomerSupplierLedgerDTO.class);
                        customerSupplierLedgerDTOS.add(dto);
                    }
                    if (customerSupplierLedgerDTOS.size() > 0) {
//                        lLNoData.setVisibility(View.GONE);
                        rVCustomer.setVisibility(View.VISIBLE);
                        accountCustomerSupplierAdapter = new AccountCustomerSupplierAdapter(activity, customerSupplierLedgerDTOS);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVCustomer.setLayoutManager(manager);
                        rVCustomer.setAdapter(accountCustomerSupplierAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
//                        lLNoData.setVisibility(View.VISIBLE);
                        rVCustomer.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }

    private void getAccountSupplier() {
        progressDialog.show();
        String url = APIs.supplierMasterList + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest accountSupplierRequest = new APIRequest(
                Request.Method.GET,
                url,
                accountSupplierResponse(),
                activityErrorListener()
        );
        requestQueue.add(accountSupplierRequest);
        AppUtil.customizeRequest(accountSupplierRequest);
    }


    private Response.Listener<JSONObject> accountSupplierResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    customerSupplierLedgerDTOS = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        CustomerSupplierLedgerDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), CustomerSupplierLedgerDTO.class);
                        customerSupplierLedgerDTOS.add(dto);
                    }
                    if (customerSupplierLedgerDTOS.size() > 0) {
//                        lLNoData.setVisibility(View.GONE);
                        rVSupplier.setVisibility(View.VISIBLE);
                        accountCustomerSupplierAdapter = new AccountCustomerSupplierAdapter(activity, customerSupplierLedgerDTOS);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVSupplier.setLayoutManager(manager);
                        rVSupplier.setAdapter(accountCustomerSupplierAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
//                        lLNoData.setVisibility(View.VISIBLE);
                        rVSupplier.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }

    private void getAccountMaster(){
        progressDialog.show();
        String url = APIs.accountMasterList + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest accountMasterRequest = new APIRequest(
                Request.Method.GET,
                url,
                accountMasterResponse(),
                activityErrorListener()
        );
        requestQueue.add(accountMasterRequest);
        AppUtil.customizeRequest(accountMasterRequest);
    }


    private Response.Listener<JSONObject> accountMasterResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    customerSupplierLedgerDTOS = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        CustomerSupplierLedgerDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), CustomerSupplierLedgerDTO.class);
                        customerSupplierLedgerDTOS.add(dto);
                    }
                    if (customerSupplierLedgerDTOS.size() > 0) {
//                        lLNoData.setVisibility(View.GONE);
                        rVAccounts.setVisibility(View.VISIBLE);
                        accountCustomerSupplierAdapter = new AccountCustomerSupplierAdapter(activity, customerSupplierLedgerDTOS);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVAccounts.setLayoutManager(manager);
                        rVAccounts.setAdapter(accountCustomerSupplierAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
//                        lLNoData.setVisibility(View.VISIBLE);
                        rVAccounts.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }


    private void getDashboardTransaction() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        DashboardTransactionAdapter adapter = new DashboardTransactionAdapter(activity, getDashboardTransactionsList());
        rVTransactionsDetails.setLayoutManager(manager);
        rVTransactionsDetails.setAdapter(adapter);
    }

    private List<DashboardTransactionDTO> getDashboardTransactionsList() {
        List<DashboardTransactionDTO> dashboardTransactionDTOList = new ArrayList<>();
        try {
            JSONArray dashboardArray = new JSONArray(AppTexts.dashboardTransactionArray);
            System.out.println("arrayLength:: " + dashboardArray.length());
            for (int i = 0; i < dashboardArray.length(); i++) {
                JSONObject object = dashboardArray.getJSONObject(i);
                DashboardTransactionDTO dashboardDTO = new Gson().fromJson(object.toString(), DashboardTransactionDTO.class);
                dashboardTransactionDTOList.add(dashboardDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dashboardTransactionDTOList;
    }


    private void getAccountData() {
        progressDialog.show();
        String url = APIs.dailyCashSummary + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "") + "/" + currentNepaliDate;
        APIRequest summaryAccountRequest = new APIRequest(
                Request.Method.GET,
                url,
                summaryAccountResponse(),
                activityErrorListener()
        );
        requestQueue.add(summaryAccountRequest);
        AppUtil.customizeRequest(summaryAccountRequest);


//        LinearLayoutManager manager = new LinearLayoutManager(activity);
//        DashboardAdapter adapter = new DashboardAdapter(activity, getDashboardAccountList());
//        rVAccountDetails.setLayoutManager(manager);
//        rVAccountDetails.setAdapter(adapter);
    }


    private Response.Listener<JSONObject> summaryAccountResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    dashboardDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        DashboardDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), DashboardDTO.class);
                        dashboardDTOList.add(dto);
                    }
                    if (dashboardDTOList.size() > 0) {
                        rVAccountDetails.setVisibility(View.VISIBLE);
                        dashboardAdapter = new DashboardAdapter(activity, dashboardDTOList);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVAccountDetails.setLayoutManager(manager);
                        rVAccountDetails.setAdapter(dashboardAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        rVAccountDetails.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }



    private void getTransactionData() {
        progressDialog.show();
        String url = APIs.dailyTransactionSummary + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "") + "/" + currentNepaliDate;
        APIRequest summaryTransactionRequest = new APIRequest(
                Request.Method.GET,
                url,
                summaryTransactionResponse(),
                activityErrorListener()
        );
        requestQueue.add(summaryTransactionRequest);
        AppUtil.customizeRequest(summaryTransactionRequest);

//        LinearLayoutManager manager = new LinearLayoutManager(activity);
//        DashboardAdapter adapter = new DashboardAdapter(activity, getDashboardTransactionList());
//        rVTransactionDetails.setLayoutManager(manager);
//        rVTransactionDetails.setAdapter(adapter);
    }

    private Response.Listener<JSONObject> summaryTransactionResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    dashboardDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        DashboardDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), DashboardDTO.class);
                        dashboardDTOList.add(dto);
                    }
                    if (dashboardDTOList.size() > 0) {
                        rVTransactionDetails.setVisibility(View.VISIBLE);
                        dashboardAdapter = new DashboardAdapter(activity, dashboardDTOList);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVTransactionDetails.setLayoutManager(manager);
                        rVTransactionDetails.setAdapter(dashboardAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        rVTransactionDetails.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }

    private void getInventoryData() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        DashboardAdapter adapter = new DashboardAdapter(activity, getDashboardInventoryList());
        rVInventoryDetails.setLayoutManager(manager);
        rVInventoryDetails.setAdapter(adapter);
    }

    private List<DashboardDTO> getDashboardInventoryList() {
        List<DashboardDTO> dashboardDTOList = new ArrayList<>();
        try {
            JSONArray dashboardArray = new JSONArray(AppTexts.inventoryAccountArray);
            System.out.println("arrayLength:: " + dashboardArray.length());
            for (int i = 0; i < dashboardArray.length(); i++) {
                JSONObject object = dashboardArray.getJSONObject(i);
                DashboardDTO dashboardDTO = new Gson().fromJson(object.toString(), DashboardDTO.class);
                dashboardDTOList.add(dashboardDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dashboardDTOList;
    }

    private void getItemProducts() {
        progressDialog.show();
        String url = APIs.itemMasterListByItemType + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "") + "/" + false;
        APIRequest accountMasterRequest = new APIRequest(
                Request.Method.GET,
                url,
                itemProductResponse(),
                activityErrorListener()
        );
        requestQueue.add(accountMasterRequest);
        AppUtil.customizeRequest(accountMasterRequest);

    }



    private Response.Listener<JSONObject> itemProductResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    dashboardItemDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        DashboardItemDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), DashboardItemDTO.class);
                        dashboardItemDTOList.add(dto);
                    }
                    if (dashboardItemDTOList.size() > 0) {
                        rVItemsProduct.setVisibility(View.VISIBLE);
                        dashboardItemsProductAdapter = new DashboardItemsProductAdapter(activity, dashboardItemDTOList);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVItemsProduct.setLayoutManager(manager);
                        rVItemsProduct.setAdapter(dashboardItemsProductAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        rVItemsProduct.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }


    private void getItemServices() {
        progressDialog.show();
        String url = APIs.itemMasterListByItemType + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "") + "/" + true;
        APIRequest accountMasterRequest = new APIRequest(
                Request.Method.GET,
                url,
                itemServiceResponse(),
                activityErrorListener()
        );
        requestQueue.add(accountMasterRequest);
        AppUtil.customizeRequest(accountMasterRequest);
    }

    private Response.Listener<JSONObject> itemServiceResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    dashboardItemDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        DashboardItemDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), DashboardItemDTO.class);
                        dashboardItemDTOList.add(dto);
                    }
                    if (dashboardItemDTOList.size() > 0) {
                        rVItemsServices.setVisibility(View.VISIBLE);
                        dashboardItemsProductAdapter = new DashboardItemsProductAdapter(activity, dashboardItemDTOList);

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        rVItemsServices.setLayoutManager(manager);
                        rVItemsServices.setAdapter(dashboardItemsProductAdapter);
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        rVItemsServices.setVisibility(View.GONE);
                    }
                } else {
                    progressDialog.dismiss();
                    String message = response.getString(AppTexts.message);
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }





    private List<DashboardItemServiceDTO> getItemServiceList() {
        List<DashboardItemServiceDTO> dashboardItemServiceDTOList = new ArrayList<>();
        try {
            JSONArray dashboardArray = new JSONArray(AppTexts.itemsServiceArray);
            System.out.println("arrayLength:: " + dashboardArray.length());
            for (int i = 0; i < dashboardArray.length(); i++) {
                JSONObject object = dashboardArray.getJSONObject(i);
                DashboardItemServiceDTO dashboardItemsProductDTO = new Gson().fromJson(object.toString(), DashboardItemServiceDTO.class);
                dashboardItemServiceDTOList.add(dashboardItemsProductDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dashboardItemServiceDTOList;
    }

    private void getItemUnit() {
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        DashboardItemUnitAdapter adapter = new DashboardItemUnitAdapter(activity, getItemUnitList());
        rVItemsUnit.setLayoutManager(manager);
        rVItemsUnit.setAdapter(adapter);
    }

    private List<UnitsDTO> getItemUnitList() {
        List<UnitsDTO> unitsDTOS = new ArrayList<>();
        try {
            JSONArray dashboardArray = new JSONArray(AppTexts.itemUnitArray);
            System.out.println("arrayLength:: " + dashboardArray.length());
            for (int i = 0; i < dashboardArray.length(); i++) {
                JSONObject object = dashboardArray.getJSONObject(i);
                UnitsDTO unitsDTO = new Gson().fromJson(object.toString(), UnitsDTO.class);
                unitsDTOS.add(unitsDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return unitsDTOS;
    }


    private void nepaliCalender() {
        com.hornet.dateconverter.DatePicker.DatePickerDialog dpd =
                com.hornet.dateconverter.DatePicker.DatePickerDialog.newInstance(nepaliDateListener);
        DateConverter dc = new DateConverter();
        Calendar cal = Calendar.getInstance();


        com.hornet.dateconverter.DatePicker.DatePickerDialog dialog =
                com.hornet.dateconverter.DatePicker.DatePickerDialog.newInstance(nepaliDateListener);
        dialog.setVersion(DatePickerDialog.Version.VERSION_1);
//            dialog.setMaxDate();
//            dialog.setMinDate(sModel);
        dialog.show(getSupportFragmentManager(), "");
    }


    private com.hornet.dateconverter.DatePicker.DatePickerDialog.OnDateSetListener nepaliDateListener = new com.hornet.dateconverter.DatePicker.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(com.hornet.dateconverter.DatePicker.DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;

            NumberFormat f = new DecimalFormat("00");
            String yearMonth = "", yearDay = "";
            int month = monthOfYear;
            if (month < 10) {
                yearMonth = f.format(month);
            } else {
                yearMonth = String.valueOf(monthOfYear);
            }

            if (dayOfMonth < 10) {
                yearDay = f.format(dayOfMonth);
            } else {
                yearDay = String.valueOf(dayOfMonth);
            }
            String selectedDate = year + "-" + yearMonth + "-" + yearDay;
            if (tVAsOfDate.isSelected()) {
                tVAsOfDate.setText(selectedDate);
            } else {
                tVDate.setText(selectedDate);
            }

            getValues();

        }
    };

    private void getValues() {
        asOfDate = tVAsOfDate.getText().toString();
        //getting date month and year from the dates in textview and converting to english
        //from date conversion
        int yearFrom = Integer.parseInt(asOfDate.substring(0, 4));
        int monthFrom = Integer.parseInt(asOfDate.substring(5, 7));
        int dayFrom = Integer.parseInt(asOfDate.substring(8, 10));

        Model model1 = new DateConverter().getEnglishDate(yearFrom, monthFrom, dayFrom);
        calendar.set(model1.getYear(), model1.getMonth(), model1.getDay());

        String date = df.format(calendar.getTime());
        System.out.println("asOfDate::: " + date);
//            getLedger(fromDate);


    }

    @Override
    public void onBackPressed() {
        if (closeCount == 0) {
            closeCount++;
            Toast.makeText(activity, "Press back again to close.", Toast.LENGTH_SHORT).show();
        } else {
           finishAffinity();
        }

    }
}
