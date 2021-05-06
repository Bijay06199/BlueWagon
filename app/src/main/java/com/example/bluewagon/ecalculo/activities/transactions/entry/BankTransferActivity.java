package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class BankTransferActivity extends AppCompatActivity implements View.OnClickListener {

    private BankTransferActivity activity = BankTransferActivity.this;
    private TextView tVTransferDate, tVChequeDate, tVTransferNumber, tVCancelTransfer, tVSaveTransfer, tVBankTransferNarration;
    private CardView cVTransferNarration;
    private EditText eTBankFrom, eTChequeNumber, eTBankTo;

    private Calendar calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private AlertDialog dialog;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        init();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVTransferDate = findViewById(R.id.tVTransferDate);
        tVChequeDate = findViewById(R.id.tVChequeDate);
        tVTransferNumber = findViewById(R.id.tVTransferNumber);
        tVCancelTransfer = findViewById(R.id.tVCancelTransfer);
        tVSaveTransfer = findViewById(R.id.tVSaveTransfer);
        tVBankTransferNarration = findViewById(R.id.tVBankTransferNarration);

        cVTransferNarration = findViewById(R.id.cVTransferNarration);

        eTBankFrom = findViewById(R.id.eTBankFrom);
        eTChequeNumber = findViewById(R.id.eTChequeNumber);
        eTBankTo = findViewById(R.id.eTBankTo);

        df = new SimpleDateFormat("yyyy-MM-dd");

        calenderTemp1 = Calendar.getInstance();

        inflater = LayoutInflater.from(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        initListeners();
    }

    private void initListeners() {
        tVTransferDate.setOnClickListener(activity);
        tVChequeDate.setOnClickListener(activity);
        cVTransferNarration.setOnClickListener(activity);
        tVCancelTransfer.setOnClickListener(activity);
        tVSaveTransfer.setOnClickListener(activity);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVTransferDate.setText(currentDateNepali);
        tVChequeDate.setText(currentDateNepali);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelTransfer:
                onBackPressed();
                break;

            case R.id.tVTransferDate:
                tVTransferDate.setSelected(true);
                tVChequeDate.setSelected(false);
                nepaliCalender();
                break;

            case R.id.tVChequeDate:
                tVChequeDate.setSelected(true);
                tVTransferDate.setSelected(false);
                nepaliCalender();
                break;

            case R.id.cVTransferNarration:
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
                String previousNarration = tVBankTransferNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVBankTransferNarration.setText(narration);
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


        if (tVTransferDate.isSelected()) {
            tVTransferDate.setText(selectedDate);
//            calenderTemp1.set(model.getYear(), model.getMonth(), model.getDay());
        }

        if (tVChequeDate.isSelected()) {
            tVChequeDate.setText(selectedDate);
//            calenderTemp2.set(model.getYear(), model.getMonth(), model.getDay());
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
