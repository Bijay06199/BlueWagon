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
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddCashWithdrawlAdapter;
import com.example.bluewagon.ecalculo.dtos.AddBankDTO;
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

public class CashWithdrawlActivity extends AppCompatActivity implements View.OnClickListener{

    private CashWithdrawlActivity activity = CashWithdrawlActivity.this;
    private TextView tVWithdrawDate, tVWithdrawNarration, tVAddBanks, tVCancelPayment, tVChequeDate;
    private ImageView iVAddBank;
    private CardView cVAddWithdrawAccounts, cVWithdrawNarration;
    private EditText eTWithdrawMode;

    private Calendar calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private AlertDialog dialog;
    private LayoutInflater inflater;

    private List<AddBankDTO> addBankDTOS;
    private RecyclerView rVAddBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdrawl);
        init();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVCancelPayment = findViewById(R.id.tVCancelPayment);

        tVWithdrawDate = findViewById(R.id.tVWithdrawDate);
        tVWithdrawNarration = findViewById(R.id.tVWithdrawNarration);
        tVAddBanks = findViewById(R.id.tVAddBanks);

        cVWithdrawNarration = findViewById(R.id.cVWithdrawNarration);
        cVAddWithdrawAccounts = findViewById(R.id.cVAddWithdrawAccounts);

        iVAddBank = findViewById(R.id.iVAddBank);
        rVAddBank = findViewById(R.id.rVAddBank);

        eTWithdrawMode = findViewById(R.id.eTWithdrawMode);

        df = new SimpleDateFormat("yyyy-MM-dd");
        addBankDTOS = new ArrayList<>();

        calenderTemp1 = Calendar.getInstance();

        inflater = LayoutInflater.from(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        initListeners();
    }

    private void initListeners() {
        tVWithdrawDate.setOnClickListener(activity);
        tVCancelPayment.setOnClickListener(activity);
        cVAddWithdrawAccounts.setOnClickListener(activity);
        tVAddBanks.setOnClickListener(activity);
        cVWithdrawNarration.setOnClickListener(activity);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVWithdrawDate.setText(currentDateNepali);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelPayment:
                onBackPressed();
                break;

            case R.id.cVAddWithdrawAccounts:
                iVAddBank.animate().rotation(iVAddBank.getRotation() + 180).start();
                if (rVAddBank.getVisibility() == View.VISIBLE) {
                    rVAddBank.setVisibility(View.GONE);
                } else {
                    rVAddBank.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tVAddBanks:
                AlertDialog.Builder entryDialogBuilder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
                View paymentEntryDialog = inflater.inflate(R.layout.layout_withdrawl_entry, null);

                TextView tVCancelPaymentEntry = paymentEntryDialog.findViewById(R.id.tVCancelPaymentEntry);
                TextView tVSavePaymentEntry = paymentEntryDialog.findViewById(R.id.tVSavePaymentEntry);
                TextView tVClosingBalance = paymentEntryDialog.findViewById(R.id.tVClosingBalance);
                EditText eTBankName = paymentEntryDialog.findViewById(R.id.eTBankName);
                EditText eTAmount = paymentEntryDialog.findViewById(R.id.eTAmount);
                EditText eTChequeNumber = paymentEntryDialog.findViewById(R.id.eTChequeNumber);
                tVChequeDate = paymentEntryDialog.findViewById(R.id.tVChequeDate);

                //set date on cheque date
                setChequeDate();

                tVChequeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tVWithdrawDate.setSelected(false);
                        tVChequeDate.setSelected(true);
                        nepaliCalender();
                    }
                });

                tVSavePaymentEntry.setOnClickListener(v13 -> {
                    if (eTBankName.getText().toString().equals("") && eTAmount.getText().toString().equals("")) {
                        String message = "Please Fill all the required details to proceed.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTBankName.getText().toString().equals("")) {
                        String message = "Please Select an Bank Name.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTAmount.getText().toString().equals("")) {
                        String message = "Please enter the Amount.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else {
                        JSONObject jObj = new JSONObject();
                        try {
                            jObj.put("bankName", eTBankName.getText().toString());
                            jObj.put("closingBalance", tVClosingBalance.getText().toString());
                            jObj.put("amount", eTAmount.getText().toString());
                            jObj.put("chequeDate", tVChequeDate.getText().toString());
                            jObj.put("chequeNumber", eTChequeNumber.getText().toString());
                            AddBankDTO addBankDTO = new Gson().fromJson(jObj.toString(), AddBankDTO.class);
                            addBankDTOS.add(addBankDTO);
                        } catch (Exception e) {
                            System.out.println("Error:" + e);
                        }

                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        AddCashWithdrawlAdapter addCashWithdrawlAdapter = new AddCashWithdrawlAdapter(activity, addBankDTOS);
                        rVAddBank.setLayoutManager(manager);
                        rVAddBank.setAdapter(addCashWithdrawlAdapter);
                        rVAddBank.setVisibility(View.VISIBLE);

                        dialog.dismiss();
                    }
                });

                tVCancelPaymentEntry.setOnClickListener(v14 -> dialog.dismiss());

                entryDialogBuilder.setCancelable(false);
                entryDialogBuilder.setView(paymentEntryDialog);
                dialog = entryDialogBuilder.create();
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                break;

            case R.id.tVWithdrawDate:
                tVWithdrawDate.setSelected(true);
                tVChequeDate.setSelected(false);
                nepaliCalender();
                break;

            case R.id.cVWithdrawNarration:
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
                String previousNarration = tVWithdrawNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVWithdrawNarration.setText(narration);
                    dialog.dismiss();
                });

                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;

        }
    }


    private void setChequeDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVChequeDate.setText(currentDateNepali);
    }

    private void nepaliCalender() {
//        com.hornet.dateconverter.DatePicker.DatePickerDialog dpd =
//                com.hornet.dateconverter.DatePicker.DatePickerDialog.newInstance(nepaliDateListener);
//        DateConverter dc = new DateConverter();
//        Calendar cal = Calendar.getInstance();


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

        if (tVWithdrawDate.isSelected()) {
            tVWithdrawDate.setText(selectedDate);
        }else{
            tVChequeDate.setText(selectedDate);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
