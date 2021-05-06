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
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddReceiptAdapter;
import com.example.bluewagon.ecalculo.dtos.AddPaymentReceiptDTO;
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

public class ReceiptEntryActivity extends AppCompatActivity implements View.OnClickListener{

    private ReceiptEntryActivity activity = ReceiptEntryActivity.this;
    private TextView tVReceiptDate, tVReceiptNarration, tVAddReceiptAccounts, tVCancelReceipt;
    private ImageView iVAddReceiptAccounts;
    private CardView cVAddReceiptDetails, cVAddReceiptAccounts, cVReceiptNarration;
    private EditText eTPaymentMode;

    private Calendar calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private AlertDialog dialog;
    private LayoutInflater inflater;
    private List<AddPaymentReceiptDTO> addPaymentReceiptDTOS;
    private Gson gson;
    private AddReceiptAdapter addReceiptAdapter;
    private RecyclerView rVReceiptEntryAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_entry);
        init();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init(){
        tVCancelReceipt = findViewById(R.id.tVCancelReceipt);
        tVReceiptDate = findViewById(R.id.tVReceiptDate);
        tVReceiptNarration = findViewById(R.id.tVReceiptNarration);
        tVAddReceiptAccounts = findViewById(R.id.tVAddReceiptAccounts);

        rVReceiptEntryAccounts = findViewById(R.id.rVReceiptEntryAccounts);
        cVAddReceiptAccounts = findViewById(R.id.cVAddReceiptAccounts);
        cVReceiptNarration = findViewById(R.id.cVReceiptNarration);

        iVAddReceiptAccounts = findViewById(R.id.iVAddReceiptAccounts);

        eTPaymentMode = findViewById(R.id.eTPaymentMode);

        df = new SimpleDateFormat("yyyy-MM-dd");
        gson = new Gson();
        addPaymentReceiptDTOS = new ArrayList<>();

        calenderTemp1 = Calendar.getInstance();

        inflater = LayoutInflater.from(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);




        initListeners();
    }

    private void initListeners(){
        tVReceiptDate.setOnClickListener(activity);
        tVCancelReceipt.setOnClickListener(activity);
        cVAddReceiptAccounts.setOnClickListener(activity);
        tVAddReceiptAccounts.setOnClickListener(activity);
        cVReceiptNarration.setOnClickListener(activity);
    }

    private void setDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVReceiptDate.setText(currentDateNepali);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tVCancelReceipt:
                onBackPressed();
                break;

            case R.id.cVAddReceiptAccounts:
                iVAddReceiptAccounts.animate().rotation(iVAddReceiptAccounts.getRotation() + 180).start();
                if (rVReceiptEntryAccounts.getVisibility() == View.VISIBLE) {
                    rVReceiptEntryAccounts.setVisibility(View.GONE);
                } else {
                    rVReceiptEntryAccounts.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tVAddReceiptAccounts:
                AlertDialog.Builder entryDialogBuilder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
                View paymentEntryDialog = inflater.inflate(R.layout.layout_add_payment_entry, null);

                TextView tVTitle = paymentEntryDialog.findViewById(R.id.tVTitle);
                tVTitle.setText("Add Receipt Entry");
                TextView tVCancelPaymentEntry = paymentEntryDialog.findViewById(R.id.tVCancelPaymentEntry);
                TextView tVSavePaymentEntry = paymentEntryDialog.findViewById(R.id.tVSavePaymentEntry);
                TextView tVClosingBalance = paymentEntryDialog.findViewById(R.id.tVClosingBalance);
                EditText eTAccountName = paymentEntryDialog.findViewById(R.id.eTAccountName);
                EditText eTPaidAmount = paymentEntryDialog.findViewById(R.id.eTPaidAmount);
                EditText eTPaidTo = paymentEntryDialog.findViewById(R.id.eTPaidTo);

                tVSavePaymentEntry.setOnClickListener(v13 -> {
                    if (eTAccountName.getText().toString().equals("") && eTPaidAmount.getText().toString().equals("") && eTPaidTo.getText().toString().equals("")) {
                        String message = "Please Fill all the required details to proceed.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTAccountName.getText().toString().equals("")) {
                        String message = "Please Select an Account Name.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTPaidAmount.getText().toString().equals("")) {
                        String message = "Please enter the paid Amount.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTPaidTo.getText().toString().equals("")) {
                        String message = "Please enter Name to whom the amount is paid.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else {
                        JSONObject jObj = new JSONObject();
                        try {
                            jObj.put("accountName", eTAccountName.getText().toString());
                            jObj.put("closingBalance", tVClosingBalance.getText().toString());
                            jObj.put("paidAmount", eTPaidAmount.getText().toString());
                            jObj.put("paidTo", eTPaidTo.getText().toString());
                            AddPaymentReceiptDTO addPaymentReceiptDTO = gson.fromJson(jObj.toString(), AddPaymentReceiptDTO.class);
                            addPaymentReceiptDTOS.add(addPaymentReceiptDTO);
                        } catch (Exception e) {
                            System.out.println("Error:" + e);
                        }
                        System.out.println("size: " + addPaymentReceiptDTOS.size());
                        LinearLayoutManager manager = new LinearLayoutManager(activity);
                        addReceiptAdapter = new AddReceiptAdapter(activity,addPaymentReceiptDTOS);
                        rVReceiptEntryAccounts.setLayoutManager(manager);
                        rVReceiptEntryAccounts.setAdapter(addReceiptAdapter);
                        rVReceiptEntryAccounts.setVisibility(View.VISIBLE);
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

            case R.id.tVReceiptDate:
                nepaliCalender();
                break;

            case R.id.cVPaymentNarration:
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
                String previousNarration = tVReceiptNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVReceiptNarration.setText(narration);
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

        tVReceiptDate.setText(selectedDate);
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
