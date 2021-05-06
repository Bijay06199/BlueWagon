package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AccountMasterAdapter;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddAccountsAdapter;
import com.example.bluewagon.ecalculo.dtos.AccountMasterDTO;
import com.example.bluewagon.ecalculo.dtos.AddAccountsDTO;
import com.example.bluewagon.ecalculo.listeners.AccountMasterSelectedListener;
import com.example.bluewagon.ecalculo.listeners.EntryDeletedListener;
import com.example.bluewagon.ecalculo.listeners.EntryEditedListener;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class EntryActivity extends AppCompatActivity implements View.OnClickListener, AccountMasterSelectedListener, EntryEditedListener, EntryDeletedListener {
    private EntryActivity activity = EntryActivity.this;
    private TextView tVJournalDate, tVJournalNumber, tVAddJournalAccounts, tVCancelJournal, tVSaveJournal, tVAccountName,
            tVClosingBalance, tVNarration, tVDebitAmount, tVCreditAmount, tVDifference;
    private CardView cVNarration;
    private ImageView iVAddJournalAccounts;
    private RecyclerView rVJournalEntryAccounts;

    private Calendar calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private AlertDialog dialog, accountMasterDialog;
    private LayoutInflater inflater;
    private List<AddAccountsDTO> addAccountsDTOS;

    private SharedPreferences preferences;
    private RequestQueue requestQueue;
    private List<AccountMasterDTO> accountMasterDTOList;
    private AccountMasterAdapter accountMasterAdapter;
    private int accountId, editPosition;
    private double difference;
    private String editString = "";
    private AddAccountsAdapter addAccountsAdapter;

    private JSONArray journalArraySubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);
        init();
        getVoucherNumber();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVJournalDate = findViewById(R.id.tVJournalDate);
        tVJournalNumber = findViewById(R.id.tVJournalNumber);
        tVAddJournalAccounts = findViewById(R.id.tVAddJournalAccounts);
        tVCancelJournal = findViewById(R.id.tVCancelJournal);
        tVSaveJournal = findViewById(R.id.tVSaveJournal);
        tVAccountName = findViewById(R.id.tVAccountName);
        tVClosingBalance = findViewById(R.id.tVClosingBalance);
        tVNarration = findViewById(R.id.tVNarration);
        tVDebitAmount = findViewById(R.id.tVDebitAmount);
        tVCreditAmount = findViewById(R.id.tVCreditAmount);
        tVDifference = findViewById(R.id.tVDifference);

        cVNarration = findViewById(R.id.cVNarration);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        requestQueue = Volley.newRequestQueue(activity);

        iVAddJournalAccounts = findViewById(R.id.iVAddJournalAccounts);

        rVJournalEntryAccounts = findViewById(R.id.rVJournalEntryAccounts);

        df = new SimpleDateFormat("yyyy-MM-dd");
        addAccountsDTOS = new ArrayList<>();
        journalArraySubmit = new JSONArray();

        calenderTemp1 = Calendar.getInstance();

        inflater = LayoutInflater.from(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);


        initListeners();
    }

    private void initListeners() {
        tVJournalDate.setOnClickListener(activity);
        tVCancelJournal.setOnClickListener(activity);
        tVSaveJournal.setOnClickListener(activity);
        tVAddJournalAccounts.setOnClickListener(activity);
        cVNarration.setOnClickListener(activity);
    }


    private void getVoucherNumber() {
        progressDialog.show();
        String url = APIs.journalVoucherNumber + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest voucherNumber = new APIRequest(
                Request.Method.GET,
                url,
                voucherNumberResponse(),
                activityErrorResponse()
        );
        requestQueue.add(voucherNumber);
//        AppUtil.customizeRequest(voucherNumber);
    }

    @SuppressLint("SetTextI18n")
    private Response.Listener<JSONObject> voucherNumberResponse() {
        return response -> {
            System.out.println("response:: " + response);
//            progressDialog.dismiss();
            try {
                String message = response.getString(AppTexts.message);
                if (response.getString(AppTexts.status).equalsIgnoreCase(AppTexts.success)) {
                    String voucherNumber = response.getString(AppTexts.data);
                    tVJournalNumber.setText(voucherNumber);
                    tVNarration.setText(message + voucherNumber);
                } else {
                    progressDialog.dismiss();
                    AppUtil.showErrorTitleBox(activity, message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
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
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelJournal:
                onBackPressed();
                finish();
                break;

            case R.id.tVSaveJournal:
                if (addAccountsDTOS.size() > 0) {
                    if (difference > 0) {
                        AppUtil.showErrorTitleBox(activity, "Debit and Credit are not equal. \n Please ensure both are equal to proceed");
                    } else {
                        makeJsonSubmit();
                        sendJournalEntry();
                    }
                } else {
                    AppUtil.showAlertBox(activity, "Error!", "No Items found for Journal Entry");
                }
                break;

            case R.id.tVJournalDate:
                nepaliCalender();
                break;

            case R.id.cVAddJournalAccounts:
                iVAddJournalAccounts.animate().rotation(iVAddJournalAccounts.getRotation() + 180).start();
                if (rVJournalEntryAccounts.getVisibility() == View.VISIBLE) {
                    rVJournalEntryAccounts.setVisibility(View.GONE);
                } else {
                    rVJournalEntryAccounts.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tVAddJournalAccounts:
                getAccountList();
                showJournalDialog();
                break;

            case R.id.cVNarration:
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
                String previousNarration = tVNarration.getText().toString();
                eTNarrationText.setText(previousNarration);


                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVNarration.setText(narration);
                    dialog.dismiss();
                });

                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                Objects.requireNonNull(window).setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;
        }
    }

    private void showJournalDialog() {
        AlertDialog.Builder entryDialogBuilder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
        View paymentEntryDialog = inflater.inflate(R.layout.layout_add_account_journal, null);


        EditText eTNarration = paymentEntryDialog.findViewById(R.id.eTNarration);
        TextView tVCancelPaymentEntry = paymentEntryDialog.findViewById(R.id.tVCancelPaymentEntry);
        TextView tVSavePaymentEntry = paymentEntryDialog.findViewById(R.id.tVSavePaymentEntry);
        tVClosingBalance = paymentEntryDialog.findViewById(R.id.tVClosingBalance);
        tVAccountName = paymentEntryDialog.findViewById(R.id.tVAccountName);
        EditText eTDebitAmount = paymentEntryDialog.findViewById(R.id.eTDebitAmount);
        EditText eTCreditAmount = paymentEntryDialog.findViewById(R.id.eTCreditAmount);
        tVSavePaymentEntry.setText(R.string.save);

        if (editString.equalsIgnoreCase(AppTexts.edit)) {
            for (int i = 0; i < addAccountsDTOS.size(); i++) {
                if (i == editPosition) {
                    tVSavePaymentEntry.setText(R.string.update);
                    double savedCredit = addAccountsDTOS.get(i).getCreditAmount();
                    double savedDebit = addAccountsDTOS.get(i).getDebitAmount();
                    tVAccountName.setText(addAccountsDTOS.get(i).getAccountName());
                    tVClosingBalance.setText(String.valueOf(addAccountsDTOS.get(i).getClosingBalance()));
                    if (savedCredit > 0) {
                        eTCreditAmount.setText(String.valueOf(savedCredit));
                    }

                    if (savedDebit > 0) {
                        eTDebitAmount.setText(String.valueOf(savedDebit));
                    }
                    String narration = addAccountsDTOS.get(i).getNarration();
                    if (!narration.isEmpty()) {
                        eTNarration.setText(narration);
                    }
                }
            }
        }

        tVAccountName.setOnClickListener(v15 -> populateAccountMaster(accountMasterDTOList));


        tVSavePaymentEntry.setOnClickListener(v13 -> {
            String name = tVAccountName.getText().toString();
            String debit = eTDebitAmount.getText().toString();
            String credit = eTCreditAmount.getText().toString();

            if (name.equals("Select") && eTDebitAmount.getText().toString().equals("") && eTCreditAmount.getText().toString().equals("")) {
                String message = "Please Fill all the required details to proceed.";
                AppUtil.showErrorTitleBox(activity, message);
            } else if (name.equals("Select")) {
                String message = "Please Select an Account Name.";
                AppUtil.showErrorTitleBox(activity, message);
            } else if (debit.equals("") && credit.equals("")) {
                String message = "Please either enter debit amount or credit amount.";
                AppUtil.showErrorTitleBox(activity, message);
            } else if (!debit.isEmpty() && !credit.isEmpty()) {
                String message = "Please  enter either debit amount or credit amount at a time.";
                AppUtil.showErrorTitleBox(activity, message);
            } else {
                if (editString.equalsIgnoreCase(AppTexts.edit)) {
                    addAccountsDTOS.remove(editPosition);
                    addAccountsAdapter.resetList(addAccountsDTOS);
                    editString = "";
                }

                JSONObject jObj = new JSONObject();
                try {
                    jObj.put("accountId", accountId);
                    jObj.put("accountName", name);
                    jObj.put("closingBalance", tVClosingBalance.getText().toString());
                    if (debit.isEmpty()) {
                        jObj.put("debitAmount", 0);
                    } else {
                        jObj.put("debitAmount", debit);
                    }
                    if (credit.isEmpty()) {
                        jObj.put("creditAmount", 0);
                    } else {
                        jObj.put("creditAmount", credit);
                    }
                    jObj.put("narration", eTNarration.getText().toString());
                    AddAccountsDTO addAccountsDTO = new Gson().fromJson(jObj.toString(), AddAccountsDTO.class);
                    addAccountsDTOS.add(addAccountsDTO);
                } catch (Exception e) {
                    System.out.println("Error:" + e);
                }

                LinearLayoutManager manager = new LinearLayoutManager(activity);
                addAccountsAdapter = new AddAccountsAdapter(activity, addAccountsDTOS, activity, activity);
                rVJournalEntryAccounts.setLayoutManager(manager);
                rVJournalEntryAccounts.setAdapter(addAccountsAdapter);
                rVJournalEntryAccounts.setVisibility(View.VISIBLE);
                showDrCr(addAccountsDTOS);
                dialog.dismiss();
            }
        });

        tVCancelPaymentEntry.setOnClickListener(v -> {
            if (editString.equalsIgnoreCase(AppTexts.edit)) {
                editString = "";
            }
            dialog.dismiss();
        });

        entryDialogBuilder.setCancelable(false);
        entryDialogBuilder.setView(paymentEntryDialog);
        dialog = entryDialogBuilder.create();
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @SuppressLint("SetTextI18n")
    private void showDrCr(List<AddAccountsDTO> addAccountsDTOList) {
        double txnDebitAmount = 0;
        double txnCreditAmount = 0;
        tVDebitAmount.setText("0.00");
        tVCreditAmount.setText("0.00");
        tVDifference.setText("0.00");
        for (int i = 0; i < addAccountsDTOList.size(); i++) {
            double debitAmount = addAccountsDTOList.get(i).getDebitAmount();
            double creditAmount = addAccountsDTOList.get(i).getCreditAmount();

            if (debitAmount > 0) {
                txnDebitAmount = txnDebitAmount + debitAmount;
                tVDebitAmount.setText(AppUtil.formattedAmounts(txnDebitAmount) + "");
            }

            if (creditAmount > 0) {
                txnCreditAmount = txnCreditAmount + creditAmount;
                tVCreditAmount.setText(AppUtil.formattedAmounts(txnCreditAmount) + "");
            }

            difference = txnDebitAmount - txnCreditAmount;
            if (difference < 0 || difference > 0) {
                tVDifference.setText(AppUtil.formattedAmounts(difference) + " ");
                tVDifference.setTextColor(getResources().getColor(R.color.creditColor));
            } else {
                tVDifference.setText(AppUtil.formattedAmounts(difference) + " ");
                tVDifference.setTextColor(getResources().getColor(R.color.debitColor));
            }

        }
    }

    private void makeJsonSubmit() {
        int listSize = addAccountsDTOS.size();
        for (int i = 0; i < listSize; i++) {
            AddAccountsDTO dto = addAccountsDTOS.get(i);
            double debit = dto.getDebitAmount();
            double credit = dto.getCreditAmount();
            JSONObject accountMaster = new JSONObject();
            try {
                accountMaster.put("id", dto.getAccountId());
                accountMaster.put("name", dto.getAccountName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject body = new JSONObject();
            try {
                body.put("accountMaster", accountMaster);
                if (credit > 0) {
                    body.put("txnAmount", credit);
                    body.put("drCr", "Cr");
                }
                if (debit > 0) {
                    body.put("txnAmount", debit);
                    body.put("drCr", "Dr");
                }
                body.put("narration3", dto.getNarration());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            journalArraySubmit.put(body);
        }
    }

    private void sendJournalEntry() {
        progressDialog.show();
        String url = APIs.saveJournalEntry + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0) + "/" + preferences.getString(AppTexts.fyId, "");
        String remarks = tVNarration.getText().toString();
        String date = tVJournalDate.getText().toString();

        int yearFrom = Integer.parseInt(date.substring(0, 4));
        int monthFrom = Integer.parseInt(date.substring(5, 7));
        int dayFrom = Integer.parseInt(date.substring(8, 10));

        Model model1 = new DateConverter().getEnglishDate(yearFrom, monthFrom, dayFrom);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());

        String currentDate = df.format(calenderTemp1.getTime());

        JSONObject obj = new JSONObject();
        try {
            obj.put("transactionDate", currentDate);
            obj.put("narration1", remarks);
            obj.put("transactionDetailDTOList", journalArraySubmit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIRequest journalRequest = new APIRequest(
                Request.Method.POST,
                url,
                obj,
                journalResponse(),
                activityErrorResponse()
        );
        requestQueue.add(journalRequest);
//        AppUtil.customizeRequest(journalRequest);
    }

    private Response.Listener<JSONObject> journalResponse() {
        return response -> {
            System.out.println(AppTexts.response + response);
            progressDialog.dismiss();
            try {
                String message = response.getString("message");
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
                    builder.setTitle(AppTexts.success);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(AppTexts.ok, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        recreate();
                    });
                    builder.show();
                } else {
                    AppUtil.infoDialog(activity, AppTexts.error, message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }


    private void getAccountList() {
        progressDialog.show();
        String url = APIs.allAccountMasterListForTransaction + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        APIRequest itemServiceResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountListResponse(),
                activityErrorResponse()
        );
        requestQueue.add(itemServiceResponse);
//        AppUtil.customizeRequest(itemServiceResponse);
    }

    private Response.Listener<JSONObject> accountListResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                accountMasterDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountNatureList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountNatureList.length(); i++) {
                        String object = accountNatureList.getJSONObject(i).toString();
                        accountMasterDTOList.add(new Gson().fromJson(object, AccountMasterDTO.class));
                    }

                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }


    private void populateAccountMaster(List<AccountMasterDTO> accountMasterDTOList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        accountMasterSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVCategories = view.findViewById(R.id.rVAccountGroup);
        rVCategories.setLayoutManager(new LinearLayoutManager(activity));
        accountMasterAdapter = new AccountMasterAdapter(activity, accountMasterDTOList, activity,"journalEntry");
        rVCategories.setAdapter(accountMasterAdapter);

        accountMasterDialog = (new AlertDialog.Builder(activity).setView(view)).create();
        accountMasterDialog.show();
    }


    private void accountMasterSearchListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (accountMasterAdapter != null) {
                    accountMasterAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        String yearMonth, yearDay;
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

    private Response.ErrorListener activityErrorResponse() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.infoDialog(activity, AppTexts.error, AppUtil.errorListener(error));
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onAccountSelected(String accountName, int id, double closingBalance) {
        System.out.println("name::: " + accountName);
        System.out.println("id::: " + id);
        System.out.println("closing::: " + closingBalance);
        tVAccountName.setText(accountName);
        tVClosingBalance.setText(String.valueOf(closingBalance));
        accountId = id;
        accountMasterDialog.dismiss();
    }

    @Override
    public void onEntryEdited(int position) {
        editString = AppTexts.edit;
        editPosition = position;
        System.out.println("pos:::: " + position);
        for (int i = 0; i < accountMasterDTOList.size(); i++) {
            if (i == position) {
                showJournalDialog();
            }
        }
    }

    @Override
    public void onEntryDeleted(int position) {
        addAccountsDTOS.remove(position);
        System.out.println("list:::: " + addAccountsDTOS.toString());
        addAccountsAdapter.resetList(addAccountsDTOS);
        showDrCr(addAccountsDTOS);
    }
}
