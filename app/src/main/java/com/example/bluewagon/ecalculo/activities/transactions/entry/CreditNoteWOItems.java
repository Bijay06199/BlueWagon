package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddCreditNoteAdapter;
import com.example.bluewagon.ecalculo.dtos.AddAccountsDTO;
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

public class CreditNoteWOItems extends AppCompatActivity implements View.OnClickListener {

    private CreditNoteWOItems activity = CreditNoteWOItems.this;
    private TextView tVJournalDate, tVJournalNumber, tVAddJournalAccounts, tVCancelCreditNote, tVSaveCreditNote;
    private CardView cVAddJournalAccounts;
    private ImageView iVAddJournalAccounts;

    private Calendar calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    private RecyclerView rVAddCreditNoteItems;
    private List<AddAccountsDTO> addAccountsDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_note_w_o_items);
        init();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVJournalDate = findViewById(R.id.tVJournalDate);
        tVJournalNumber = findViewById(R.id.tVJournalNumber);
        tVAddJournalAccounts = findViewById(R.id.tVAddJournalAccounts);
        tVCancelCreditNote = findViewById(R.id.tVCancelCreditNote);
        tVSaveCreditNote = findViewById(R.id.tVSaveJournal);

        cVAddJournalAccounts = findViewById(R.id.cVAddJournalAccounts);
        rVAddCreditNoteItems = findViewById(R.id.rVAddCreditNoteItems);

        iVAddJournalAccounts = findViewById(R.id.iVAddJournalAccounts);

        df = new SimpleDateFormat("yyyy-MM-dd");
        addAccountsDTOS = new ArrayList<>();

        calenderTemp1 = Calendar.getInstance();

        inflater = LayoutInflater.from(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        initListeners();
    }

    private void initListeners() {
        tVJournalDate.setOnClickListener(activity);
        tVCancelCreditNote.setOnClickListener(activity);
        cVAddJournalAccounts.setOnClickListener(activity);
        tVAddJournalAccounts.setOnClickListener(activity);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVJournalDate.setText(currentDateNepali);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelCreditNote:
               onBackPressed();
                break;

            case R.id.tVJournalDate:
                nepaliCalender();
                break;

            case R.id.cVAddJournalAccounts:
                iVAddJournalAccounts.animate().rotation(iVAddJournalAccounts.getRotation() + 180).start();
                if (rVAddCreditNoteItems.getVisibility()==View.VISIBLE){
                    rVAddCreditNoteItems.setVisibility(View.GONE);
                }else{
                    rVAddCreditNoteItems.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tVAddJournalAccounts:
                AlertDialog.Builder entryDialogBuilder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
                LayoutInflater inflater = LayoutInflater.from(activity);
                View paymentEntryDialog = inflater.inflate(R.layout.layout_add_account_journal, null);

                TextView tVCancelPaymentEntry = paymentEntryDialog.findViewById(R.id.tVCancelPaymentEntry);
                TextView tVSavePaymentEntry = paymentEntryDialog.findViewById(R.id.tVSavePaymentEntry);
                TextView tVClosingBalance = paymentEntryDialog.findViewById(R.id.tVClosingBalance);
                EditText eTAccountName = paymentEntryDialog.findViewById(R.id.eTAccountName);
                EditText eTDebitAmount = paymentEntryDialog.findViewById(R.id.eTDebitAmount);
                EditText eTCreditAmount = paymentEntryDialog.findViewById(R.id.eTCreditAmount);
                EditText eTNarration = paymentEntryDialog.findViewById(R.id.eTNarration);


                tVSavePaymentEntry.setOnClickListener(v13 -> {

                    System.out.println("account:::: " + eTAccountName.getText().toString());
                    System.out.println("debit:::: " + eTDebitAmount.getText().toString());
                    System.out.println("credit:::: " + eTCreditAmount.getText().toString());


                    if (eTAccountName.getText().toString().equals("") && eTDebitAmount.getText().toString().equals("") && eTCreditAmount.getText().toString().equals("")) {
                        String message = "Please Fill all the required details to proceed.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTAccountName.getText().toString().equals("")) {
                        String message = "Please Select an Account Name.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else if (eTDebitAmount.getText().toString().equals("") && eTCreditAmount.getText().toString().equals("")) {
                        String message = "Please either enter debit amount or credit amount.";
                        AppUtil.showErrorTitleBox(activity, message);
                    } else {
                        System.out.println("account:::: " + eTAccountName.getText().toString());
                        System.out.println("debit:::: " + eTDebitAmount.getText().toString());
                        System.out.println("credit:::: " + eTCreditAmount.getText().toString());

                        if (eTAccountName.getText().toString().equals("") && eTDebitAmount.getText().toString().equals("") && eTCreditAmount.getText().toString().equals("")) {
                            String message = "Please Fill all the required details to proceed.";
                            AppUtil.showErrorTitleBox(activity, message);
                        } else if (eTAccountName.getText().toString().equals("")) {
                            String message = "Please Select an Account Name.";
                            AppUtil.showErrorTitleBox(activity, message);
                        } else if (eTDebitAmount.getText().toString().equals("") && eTCreditAmount.getText().toString().equals("")) {
                            String message = "Please either enter debit amount or credit amount.";
                            AppUtil.showErrorTitleBox(activity, message);
                        } else {
                            JSONObject jObj = new JSONObject();
                            try {
                                jObj.put("accountName", eTAccountName.getText().toString());
                                jObj.put("closingBalance", tVClosingBalance.getText().toString());
                                jObj.put("debitAmount", eTDebitAmount.getText().toString());
                                jObj.put("creditAmount", eTCreditAmount.getText().toString());
                                jObj.put("narration", eTNarration.getText().toString());
                                AddAccountsDTO addAccountsDTO = new Gson().fromJson(jObj.toString(), AddAccountsDTO.class);
                                addAccountsDTOS.add(addAccountsDTO);
                            } catch (Exception e) {
                                System.out.println("Error:" + e);
                            }
                            LinearLayoutManager manager = new LinearLayoutManager(activity);
                            AddCreditNoteAdapter addCreditNoteAdapter = new AddCreditNoteAdapter(activity, addAccountsDTOS);
                            rVAddCreditNoteItems.setLayoutManager(manager);
                            rVAddCreditNoteItems.setAdapter(addCreditNoteAdapter);
                            rVAddCreditNoteItems.setVisibility(View.VISIBLE);

                            dialog.dismiss();
                        }
                    }
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

        tVJournalDate.setText(selectedDate);
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
