package com.example.bluewagon.ecalculo.activities.transactions.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.view.SalesChallanAdapter;
import com.example.bluewagon.ecalculo.dtos.SalesChallanDTO;
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

public class SalesChallanViewActivity extends AppCompatActivity implements View.OnClickListener {
    private SalesChallanViewActivity activity = SalesChallanViewActivity.this;
    private Button buttonSearch;
    private TextView tVTitle, tVDateFrom, tVDateTo, tVCancelSalesInvoice, tVTotal;
    private LinearLayout lLDateTo, lLDateFrom, lLNoData;

    private Calendar calendar, calenderTemp2, calenderTemp1;
    private String dateFrom, dateTo, currentFY, rDate;
    private DateFormat df;
    private HorizontalScrollView scrollMain;

    private RequestQueue requestQueue;
    private RecyclerView rVSalesChallan;
    private SharedPreferences preferences;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private int scrollX = 0;

    private List<SalesChallanDTO> salesChallanDTOList;
    private SalesChallanAdapter salesChallanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_challan_view);
        init();
        initRecyclerView();
        checkAndSetDates();
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void init() {
        tVTitle = findViewById(R.id.tVTitle);
        tVDateFrom = findViewById(R.id.tVDateFrom);
        tVDateTo = findViewById(R.id.tVDateTo);
        tVTotal = findViewById(R.id.tVTotal);
        tVCancelSalesInvoice = findViewById(R.id.tVCancelSalesInvoice);
        rVSalesChallan = findViewById(R.id.rVSalesChallan);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);

        lLDateTo = findViewById(R.id.lLDateTo);
        lLDateFrom = findViewById(R.id.lLDateFrom);
        rDate = getString(R.string.dateHint);

        buttonSearch = findViewById(R.id.buttonSearch);

        df = new SimpleDateFormat("yyyy-MM-dd");

        requestQueue = Volley.newRequestQueue(activity);

        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        calendar = Calendar.getInstance();
        calenderTemp2 = Calendar.getInstance();
        calenderTemp1 = Calendar.getInstance();

        tVTitle.setText("Sales Challan View");

        rVSalesChallan.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;
                scrollMain.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        initListeners();
    }


    private void initListeners() {
        tVCancelSalesInvoice.setOnClickListener(activity);
        lLDateFrom.setOnClickListener(activity);
        lLDateTo.setOnClickListener(activity);
        buttonSearch.setOnClickListener(activity);
    }

    private void initRecyclerView() {
        salesChallanDTOList = new ArrayList<>();
        salesChallanAdapter = new SalesChallanAdapter(activity, salesChallanDTOList);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rVSalesChallan.setLayoutManager(manager);
        rVSalesChallan.setAdapter(salesChallanAdapter);
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

        getInvoiceView(currentDateNepali, currentDateNepali);
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
            initRecyclerView();
            getInvoiceView(dateFrom, dateTo);
        }
    }

    private void getInvoiceView(String startDate, String endDate) {
        progressDialog.show();
        String url = APIs.salesChallan + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0) + "/" + startDate + "/" + endDate;
        APIRequest salesInvoiceRequest = new APIRequest(
                Request.Method.GET,
                url,
                salesInvoiceResponse(),
                activityErrorListener()
        );
        requestQueue.add(salesInvoiceRequest);
        AppUtil.customizeRequest(salesInvoiceRequest);
    }

    private Response.Listener<JSONObject> salesInvoiceResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {

                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        SalesChallanDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), SalesChallanDTO.class);
                        salesChallanDTOList.add(dto);
                    }
                    if (salesChallanDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        rVSalesChallan.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.VISIBLE);
                        salesChallanAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        rVSalesChallan.setVisibility(View.GONE);
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


    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }
}