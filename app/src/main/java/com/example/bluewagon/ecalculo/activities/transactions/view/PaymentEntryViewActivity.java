package com.example.bluewagon.ecalculo.activities.transactions.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PaymentEntryViewActivity extends AppCompatActivity implements View.OnClickListener {
    private PaymentEntryViewActivity activity = PaymentEntryViewActivity.this;
    private Button buttonSearch;
    private TextView tVTitle, tVDateFrom, tVDateTo, tVCancelSalesInvoice, tVTotal;
    private LinearLayout lLDateTo, lLDateFrom;

    private Calendar calendar, calenderTemp2, calenderTemp1;
    private String dateFrom, dateTo, currentFY, rDate;
    private DateFormat df;

    private RequestQueue requestQueue;
    private RecyclerView rVBillReport;
    private SharedPreferences preferences;
    private androidx.appcompat.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_entry_view);
        init();
        checkAndSetDates();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVTitle = findViewById(R.id.tVTitle);
        tVDateFrom = findViewById(R.id.tVDateFrom);
        tVDateTo = findViewById(R.id.tVDateTo);
        tVTotal = findViewById(R.id.tVTotal);
        tVCancelSalesInvoice = findViewById(R.id.tVCancelSalesInvoice);
        rVBillReport = findViewById(R.id.rVBillReport);

        lLDateTo = findViewById(R.id.lLDateTo);
        lLDateFrom = findViewById(R.id.lLDateFrom);
        rDate = getString(R.string.dateHint);

        buttonSearch = findViewById(R.id.buttonSearch);

        df = new SimpleDateFormat("yyyy-MM-dd");

        requestQueue = Volley.newRequestQueue(activity);

        String message = "Please Wait. Loading...";
        progressDialog = AppUtil.progressDialog(activity, message);

        calendar = Calendar.getInstance();
        calenderTemp2 = Calendar.getInstance();
        calenderTemp1 = Calendar.getInstance();

        String title = getIntent().getStringExtra(AppTexts.paymentNReceipt);
        System.out.println("title:::: " + title);
        tVTitle.setText(title);
        initListeners();
    }

    private void initListeners() {
        tVCancelSalesInvoice.setOnClickListener(activity);
        lLDateFrom.setOnClickListener(activity);
        lLDateTo.setOnClickListener(activity);
        buttonSearch.setOnClickListener(activity);
    }

    @SuppressLint("SimpleDateFormat")
    private void checkAndSetDates() {
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
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVDateFrom.setText(currentDateNepali);
        tVDateTo.setText(currentDateNepali);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelSalesInvoice:
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
//                progressDialog.show();
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
//                        getValues();
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
//            getLedger(fromDate, toDate);
        }
    }



}
