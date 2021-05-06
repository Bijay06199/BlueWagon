package com.example.bluewagon.ecalculo.activities.transactions.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.view.JournalViewAdapter;
import com.example.bluewagon.ecalculo.dtos.JournalViewDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class JournalViewActivity extends AppCompatActivity implements View.OnClickListener {
    private JournalViewActivity activity = JournalViewActivity.this;
    private TextView tVTitle, tVDateFrom, tVDateTo, tVCancelJournal, tVDebitTotal, tVCreditTotal;
    private LinearLayout lLDateFrom, lLDateTo, lLNoData;
    private Button buttonSearch;


    private Calendar calendar, calenderTemp2, calenderTemp1;
    private String dateFrom, dateTo, currentFY, rDate, transactionType;
    private DateFormat df;
    private int scrollX = 0;

    private RequestQueue requestQueue;
    private RecyclerView rVJournalBook;
    private SharedPreferences preferences;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private HorizontalScrollView scrollMain, scrollTotal;

    private List<JournalViewDTO> journalViewDTOList;
    private JournalViewAdapter journalViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_view);
        init();
        initRecyclerView();
        checkAndSetDates();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVTitle = findViewById(R.id.tVTitle);
        tVDateFrom = findViewById(R.id.tVDateFrom);
        tVDateTo = findViewById(R.id.tVDateTo);
        tVCancelJournal = findViewById(R.id.tVCancelJournal);
        rVJournalBook = findViewById(R.id.rVJournalBook);
        tVDebitTotal = findViewById(R.id.tVDebitTotal);
        tVCreditTotal = findViewById(R.id.tVCreditTotal);
        scrollMain = findViewById(R.id.scrollMain);
        scrollTotal = findViewById(R.id.scrollTotal);

        lLDateTo = findViewById(R.id.lLDateTo);
        lLDateFrom = findViewById(R.id.lLDateFrom);
        lLNoData = findViewById(R.id.lLNoData);
        rDate = getString(R.string.dateHint);

        buttonSearch = findViewById(R.id.buttonSearch);

        df = new SimpleDateFormat("yyyy-MM-dd");

        String message = AppTexts.pleaseWait;
        progressDialog = AppUtil.progressDialog(activity, message);
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        requestQueue = Volley.newRequestQueue(activity);

        calendar = Calendar.getInstance();
        calenderTemp2 = Calendar.getInstance();
        calenderTemp1 = Calendar.getInstance();

        rVJournalBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;
                scrollMain.scrollTo(scrollX, 0);
                scrollTotal.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        String title = getIntent().getStringExtra(AppTexts.journalNDebitCreditNote);
        assert title != null;
        switch (title) {
            case AppTexts.journalView:
                transactionType = "jou";
                break;
            case AppTexts.paymentView:
                transactionType = "pay";
                break;
            case AppTexts.receiptView:
                transactionType = "rec";
                break;
            case AppTexts.journalBook:
                transactionType = "All";
                break;
        }
        tVTitle.setText(title);
        initListeners();
    }

    private void initListeners() {
        tVCancelJournal.setOnClickListener(activity);
        lLDateFrom.setOnClickListener(activity);
        lLDateTo.setOnClickListener(activity);
        buttonSearch.setOnClickListener(activity);
    }

    private void initRecyclerView() {
        journalViewDTOList = new ArrayList<>();
        journalViewAdapter = new JournalViewAdapter(activity, journalViewDTOList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rVJournalBook.setLayoutManager(manager);
        rVJournalBook.setAdapter(journalViewAdapter);
        rVJournalBook.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
    }

    @SuppressLint("SimpleDateFormat")
    private void checkAndSetDates() {
        progressDialog.show();
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fromDate = preferences.getString(AppTexts.startDate, "");
        String toDate = preferences.getString(AppTexts.endDate, "");

        //load ledger for the first time
        getJournalBook(fromDate, toDate);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVDateFrom.setText(currentDateNepali);
        tVDateTo.setText(currentDateNepali);
    }

    private void getJournalBook(String startDate, String endDate) {
        progressDialog.show();
        //0 is for account master Id
        String url = APIs.journalBook + preferences.getString(AppTexts.orgId, "") + "/" + 0 + "/" + preferences.getString(AppTexts.fyId, "") + "/" + startDate + "/" + endDate + "/" + transactionType;
        APIRequest journalBookRequest = new APIRequest(
                Request.Method.GET,
                url,
                journalBookResponse(),
                activityErrorListener()
        );
        requestQueue.add(journalBookRequest);
        AppUtil.customizeRequest(journalBookRequest);
    }

    private Response.Listener<JSONObject> journalBookResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {

                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        JournalViewDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), JournalViewDTO.class);
                        journalViewDTOList.add(dto);
                    }
                    if (journalViewDTOList.size() > 0) {
                        double totalDebit = 0.00, totalCredit = 0.00;
                        //for total debit and credit amount
                        for (int i = 0; i < journalViewDTOList.size(); i++) {
                            double debitAmount = journalViewDTOList.get(i).getDebitBalance();
                            double creditAmount = journalViewDTOList.get(i).getDebitBalance();

                            totalCredit = totalCredit + creditAmount;
                            totalDebit = totalDebit + debitAmount;
                        }

                        tVDebitTotal.setText(AppUtil.formattedAmounts(totalDebit));
                        tVCreditTotal.setText(AppUtil.formattedAmounts(totalCredit));

                        //display journal book in adapter

                        lLNoData.setVisibility(View.GONE);
                        rVJournalBook.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.VISIBLE);
                        scrollTotal.setVisibility(View.VISIBLE);
                        journalViewAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        scrollTotal.setVisibility(View.GONE);
                        rVJournalBook.setVisibility(View.GONE);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelJournal:
                onBackPressed();
                break;

            case R.id.lLDateFrom:
                lLDateFrom.setSelected(true);
                lLDateTo.setSelected(false);
                nepaliCalender();
                break;

            case R.id.lLDateTo:
                lLDateFrom.setSelected(false);
                lLDateTo.setSelected(true);
                nepaliCalender();
                break;

            case R.id.buttonSearch:
                progressDialog.show();
                dateFrom = tVDateFrom.getText().toString();
                dateTo = tVDateTo.getText().toString();

                if (dateFrom.equals(rDate) || dateTo.equals(rDate)) {
                    if (dateFrom.equals(rDate)) {
                        AppUtil.infoDialog(activity, AppTexts.error, "Please select a date in 'From' section!");
                        tVDateFrom.setText(rDate);
                    }
                } else {
                    Date sDate = null;
                    Date eDate = null;
                    try {
                        sDate = df.parse(dateFrom);
                        eDate = df.parse(dateTo);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar fromDate = Calendar.getInstance();
                    fromDate.setTime(sDate);
                    Calendar toDate = Calendar.getInstance();
                    toDate.setTime(eDate);

                    if (fromDate.after(toDate)) {
                        AppUtil.infoDialog(activity, AppTexts.error, "Please select a date earlier than the one selected in 'From' section!");
                        tVDateTo.setText(rDate);
                    } else {
                        initRecyclerView();
                        getValues();
                    }
                }
                break;
        }
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

            if (lLDateFrom.isSelected()) {
                tVDateFrom.setText(selectedDate);
            }

            if (lLDateTo.isSelected()) {
                tVDateTo.setText(selectedDate);
            }
        }
    };


    private void getValues() {
        dateFrom = tVDateFrom.getText().toString();
        dateTo = tVDateTo.getText().toString();
        //getting date month and year from the dates in textview and converting to english
        //from date conversion
        int yearFrom = Integer.parseInt(dateFrom.substring(0, 4));
        int monthFrom = Integer.parseInt(dateFrom.substring(5, 7));
        int dayFrom = Integer.parseInt(dateFrom.substring(8, 10));

        Model model1 = new DateConverter().getEnglishDate(yearFrom, monthFrom, dayFrom);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());

        String fromDate = df.format(calenderTemp1.getTime());

        //to date conversion
        System.out.println("toDate:: " + dateTo);
        String toYear = dateTo.substring(0, 4);
        String toMonth = dateTo.substring(5, 7);
        String toDate = dateTo.substring(8, 10);

        int yearTo = Integer.parseInt(toYear);
        int monthTo = Integer.parseInt(toMonth);
        int dayTo = Integer.parseInt(toDate);


        Model model2 = new DateConverter().getEnglishDate(yearTo, monthTo, dayTo);
        calenderTemp2.set(model2.getYear(), model2.getMonth(), model2.getDay());
        String convertedEnglishToDate = df.format(calenderTemp2.getTime());
        toDate = convertedEnglishToDate;


        if (fromDate.equals(rDate) || toDate.equals(rDate)) {
            AppUtil.infoDialog(activity, AppTexts.error, "Please select 'From' and 'To' dates");
        } else {
            getJournalBook(fromDate, toDate);
        }
    }

    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }

}
