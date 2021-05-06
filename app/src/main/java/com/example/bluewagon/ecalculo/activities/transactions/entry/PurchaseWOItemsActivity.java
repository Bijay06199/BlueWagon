package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddEntryAdapter;
import com.example.bluewagon.ecalculo.dtos.AddEntryDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.gson.Gson;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PurchaseWOItemsActivity extends AppCompatActivity implements View.OnClickListener {

    private PurchaseWOItemsActivity activity = PurchaseWOItemsActivity.this;
    private TextView tVPurchaseItemDate, tVItemsNarration, tVAddItem, tVPurchaseBillDate, tVCancelPurchase;
    private ImageView iVAddItems;
    private CardView cVAddItem, cVItemsNarration;
    private EditText eTPaymentMode;
    private String currentDateNepali;
    private RecyclerView rVPaymentEntryAccounts;

    private Calendar calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private AlertDialog dialog;
    private LayoutInflater inflater;

    private List<AddEntryDTO> addEntryDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_w_o_items);
        init();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVCancelPurchase = findViewById(R.id.tVCancelPurchase);
        tVPurchaseItemDate = findViewById(R.id.tVPurchaseItemDate);
        tVItemsNarration = findViewById(R.id.tVItemsNarration);
        tVAddItem = findViewById(R.id.tVAddItem);

        cVAddItem = findViewById(R.id.cVAddItem);
        cVItemsNarration = findViewById(R.id.cVItemsNarration);

        iVAddItems = findViewById(R.id.iVAddItems);
        rVPaymentEntryAccounts = findViewById(R.id.rVPaymentEntryAccounts);

        eTPaymentMode = findViewById(R.id.eTPaymentMode);

        df = new SimpleDateFormat("yyyy-MM-dd");
        addEntryDTOS = new ArrayList<>();

        calenderTemp1 = Calendar.getInstance();

        inflater = LayoutInflater.from(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        initListeners();
    }

    private void initListeners() {
        tVPurchaseItemDate.setOnClickListener(activity);
        tVCancelPurchase.setOnClickListener(activity);
        cVAddItem.setOnClickListener(activity);
        tVAddItem.setOnClickListener(activity);
        cVItemsNarration.setOnClickListener(activity);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        currentDateNepali = df.format(calenderTemp1.getTime());
        tVPurchaseItemDate.setText(currentDateNepali);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelPurchase:
                onBackPressed();
                break;

            case R.id.cVAddItem:
                iVAddItems.animate().rotation(iVAddItems.getRotation() + 180).start();
                if (rVPaymentEntryAccounts.getVisibility() == View.VISIBLE) {
                    rVPaymentEntryAccounts.setVisibility(View.GONE);
                } else {
                    rVPaymentEntryAccounts.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tVPurchaseItemDate:
                tVPurchaseItemDate.setSelected(true);
                nepaliCalender();
                break;

            case R.id.cVItemsNarration:
                View narrationDialog = inflater.inflate(R.layout.layout_narration_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                builder.setView(narrationDialog);

                TextView tVCancel = narrationDialog.findViewById(R.id.tVCancel);
                TextView tVOk = narrationDialog.findViewById(R.id.tVOk);
                TextView tVHeading = narrationDialog.findViewById(R.id.tVHeading);
                final EditText eTNarrationText = narrationDialog.findViewById(R.id.eTNarrationText);

                tVHeading.setText(R.string.narration);

                //making the editText view of certain size and making cursor on desired pos
                eTNarrationText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                eTNarrationText.setSingleLine(false);
                eTNarrationText.setLines(5);
                eTNarrationText.setMaxLines(5);
                eTNarrationText.setGravity(Gravity.START | Gravity.TOP);

                //setting the previous narration on editText, if initial ignore else show same
                String previousNarration = tVItemsNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVItemsNarration.setText(narration);
                    dialog.dismiss();
                });

                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;


            case R.id.tVAddItem:
                AlertDialog.Builder entryDialogBuilder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
                View paymentEntryDialog = inflater.inflate(R.layout.layout_purchase_without_items, null);

                TextView tVCancelPaymentEntry = paymentEntryDialog.findViewById(R.id.tVCancelPaymentEntry);
                TextView tVSavePaymentEntry = paymentEntryDialog.findViewById(R.id.tVSavePaymentEntry);
                TextView tVClosingBalance = paymentEntryDialog.findViewById(R.id.tVClosingBalance);
                tVPurchaseBillDate = paymentEntryDialog.findViewById(R.id.tVPurchaseBillDate);

                EditText eTAccountName = paymentEntryDialog.findViewById(R.id.eTAccountName);
                EditText eTAmount = paymentEntryDialog.findViewById(R.id.eTAmount);
                EditText eTPurchaseBillNumber = paymentEntryDialog.findViewById(R.id.eTPurchaseBillNumber);

                tVPurchaseBillDate.setText(currentDateNepali);

                tVPurchaseBillDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tVPurchaseBillDate.setSelected(true);
                        nepaliCalender();
                    }
                });

                tVSavePaymentEntry.setOnClickListener(v13 -> {

                    JSONObject jObj = new JSONObject();
                    try {
                        jObj.put("accountName", eTAccountName.getText().toString());
                        jObj.put("closingBalance", tVClosingBalance.getText().toString());
                        jObj.put("amount", eTAmount.getText().toString());
                        jObj.put("purchaseBillDate", tVPurchaseBillDate.getText().toString());
                        jObj.put("purchaseBillNumber", eTPurchaseBillNumber.getText().toString());
                        AddEntryDTO addEntryDTO = new Gson().fromJson(jObj.toString(), AddEntryDTO.class);
                        addEntryDTOS.add(addEntryDTO);
                    } catch (Exception e) {
                        System.out.println("Error:" + e);
                    }

                    System.out.println("size: " + addEntryDTOS.size());
                    LinearLayoutManager manager = new LinearLayoutManager(activity);
                    AddEntryAdapter addEntryAdapter = new AddEntryAdapter(activity, addEntryDTOS);
                    rVPaymentEntryAccounts.setLayoutManager(manager);
                    rVPaymentEntryAccounts.setAdapter(addEntryAdapter);
                    rVPaymentEntryAccounts.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                });

                tVCancelPaymentEntry.setOnClickListener(v14 -> dialog.dismiss());

                entryDialogBuilder.setCancelable(false);
                entryDialogBuilder.setView(paymentEntryDialog);
                dialog = entryDialogBuilder.create();
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    private com.hornet.dateconverter.DatePicker.DatePickerDialog.OnDateSetListener nepaliDateListener = (datePickerDialog, year, monthOfYear, dayOfMonth) -> {
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

        if (tVPurchaseItemDate.isSelected()) {
            tVPurchaseItemDate.setText(selectedDate);
        }

        if (tVPurchaseBillDate.isSelected()) {
            tVPurchaseBillDate.setText(selectedDate);
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
