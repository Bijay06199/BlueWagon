package com.example.bluewagon.ecalculo.activities.reports;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class PartyOutstandingPayableActivity extends AppCompatActivity implements View.OnClickListener {

    private PartyOutstandingPayableActivity activity = PartyOutstandingPayableActivity.this;

    private DateFormat df;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private Calendar calendar;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private TextView tVAsOfDate, tVCancel, tVTitle, tVTotalAmount;
    private LinearLayout lLAsOfDate;
    private HorizontalScrollView scrollMain, scrollTotalMain;
    private RecyclerView rVPartyOutstandingPayable;
    private Button buttonSearch;
    private String asOfDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_outstanding_payable);
        init();
        setDate();
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void init() {
        tVAsOfDate = findViewById(R.id.tVAsOfDate);
        tVCancel = findViewById(R.id.tVCancel);
        tVTitle = findViewById(R.id.tVTitle);
        tVTotalAmount = findViewById(R.id.tVTotalAmount);

        lLAsOfDate = findViewById(R.id.lLAsOfDate);

        scrollMain = findViewById(R.id.scrollMain);
        scrollTotalMain = findViewById(R.id.scrollTotalMain);

        rVPartyOutstandingPayable = findViewById(R.id.rVPartyOutstandingPayable);

        buttonSearch = findViewById(R.id.buttonSearch);


        requestQueue = Volley.newRequestQueue(activity);

        calendar = Calendar.getInstance();


        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        df = new SimpleDateFormat("yyyy-MM-dd");

        tVTitle.setText("Party Outstanding Payable");
        rVPartyOutstandingPayable.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;
                scrollMain.scrollTo(scrollX, 0);
                scrollTotalMain.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        initListeners();
    }

    private void initListeners() {
        buttonSearch.setOnClickListener(activity);
        lLAsOfDate.setOnClickListener(activity);
        tVCancel.setOnClickListener(activity);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancel:
                onBackPressed();
                break;
            case R.id.lLAsOfDate:
                nepaliCalender();
                break;
            case R.id.buttonSearch:
                getValues();
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
            tVAsOfDate.setText(selectedDate);

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
        System.out.println("asOfDate::: " + date );
//            getLedger(fromDate);

    }

}
