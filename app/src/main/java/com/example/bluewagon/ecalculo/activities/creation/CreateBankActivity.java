package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.BankLedgerAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.BankLedgerDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateBankActivity extends AppCompatActivity implements View.OnClickListener {

    private CreateBankActivity activity = CreateBankActivity.this;
    private CardView cVAddIcon;
    private TextView tVCancelBankLedger;

    private HorizontalScrollView scrollMain;
    private RecyclerView rVBankLedger;
    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0, accountHeadId;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog dialog, accountGroup;
    private BankLedgerAdapter bankLedgerAdapter;

    private Spinner accountTypeSpinner, balanceTypeSpinnerBank;

    private List<BankLedgerDTO> bankLedgerDTOList;


    private androidx.appcompat.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bank);
        init();
        getBankLedgerList();
//        populateDebitCreditSpinner();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelBankLedger:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                View bankView = inflater.inflate(R.layout.layout_create_bank, null);
                AlertDialog.Builder bankBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                bankBuilder.setView(bankView);
                TextView tVSaveBank = bankView.findViewById(R.id.tVSaveBank);
                TextView tVCancelBank = bankView.findViewById(R.id.tVCancelBank);

                EditText eTBankName = bankView.findViewById(R.id.eTBankName);
                EditText eTAccountNumber = bankView.findViewById(R.id.eTAccountNumber);
                EditText eTBranch = bankView.findViewById(R.id.eTBranch);
                EditText eTOpeningBalance = bankView.findViewById(R.id.eTOpeningBalance);
                EditText eTOpeningDate = bankView.findViewById(R.id.eTOpeningDate);

                accountTypeSpinner = bankView.findViewById(R.id.accountTypeSpinner);
                balanceTypeSpinnerBank = bankView.findViewById(R.id.balanceTypeSpinnerBank);
                populateDebitCreditSpinner();
                populateAccountTypeSpinner();


                tVCancelBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                tVSaveBank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bankName = eTBankName.getText().toString();
                        String accountNumber = eTAccountNumber.getText().toString();
                        String branch = eTBranch.getText().toString();
                        String openingBalance = eTOpeningBalance.getText().toString();
                        String openingDate = eTOpeningDate.getText().toString();

                        String balanceType = (String) balanceTypeSpinnerBank.getSelectedItem();
                        String accountType = (String) accountTypeSpinner.getSelectedItem();

                        validateData(bankName, accountNumber, branch, openingBalance, balanceType, accountType, openingDate);
                    }
                });

                bankBuilder.setCancelable(false);
                dialog = bankBuilder.create();
                Window accountGroupWindow = dialog.getWindow();
                assert accountGroupWindow != null;
                accountGroupWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                accountGroupWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;
        }
    }

    private void init() {
        cVAddIcon = findViewById(R.id.cVAddIcon);
        tVCancelBankLedger = findViewById(R.id.tVCancelBankLedger);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        rVBankLedger = findViewById(R.id.rVBankLedger);
        scrollMain = findViewById(R.id.scrollMain);
        cVAddIcon = findViewById(R.id.cVAddIcon);

        lLNoData = findViewById(R.id.lLNoData);
        inflater = LayoutInflater.from(activity);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        rVBankLedger.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;
                scrollMain.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        initListeners();
    }

    private void initListeners() {
        tVCancelBankLedger.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }


    private void getBankLedgerList() {
        progressDialog.show();
        String url = APIs.reportBankDetails + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId,"");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                supplierLedgerResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }


    private Response.Listener<JSONObject> supplierLedgerResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    bankLedgerDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        BankLedgerDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), BankLedgerDTO.class);
                        bankLedgerDTOList.add(dto);
                    }
                    if (bankLedgerDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVBankLedger.setVisibility(View.VISIBLE);
                        bankLedgerAdapter = new BankLedgerAdapter(activity, bankLedgerDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVBankLedger.setLayoutManager(manager);
                        rVBankLedger.setAdapter(bankLedgerAdapter);
                        rVBankLedger.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVBankLedger.setVisibility(View.GONE);
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


    private void validateData(String bankName, String accountNumber, String branch, String openingBalance, String balanceType, String accountType, String openingDate) {
        if (bankName.isEmpty()) {
            AppUtil.showErrorTitleBox(activity, "Please Enter Bank Name");
        } else if (accountNumber.isEmpty()) {
            AppUtil.showErrorTitleBox(activity, "Please Enter Account Number");
        } else if (branch.isEmpty()) {
            AppUtil.showErrorTitleBox(activity, "Please Enter Bank Branch");
        } else if (accountNumber.equals("Select Account Type")) {
            AppUtil.showErrorTitleBox(activity, "Please Select Account Type");
        }else{
            if (!openingBalance.isEmpty()) {
                if (balanceType.equals("Select Balance Type")) {
                    AppUtil.showErrorTitleBox(activity, "Please select Transaction type(Dr/Cr)");
                }
            }

            sendData(bankName, accountNumber, branch,openingBalance, balanceType, accountType, openingDate);
        }
    }

    private void sendData(String bankName, String accountNumber, String branch, String openingBalance, String balanceType, String accountType, String openingDate){
        progressDialog.show();
        String url = APIs.createBankDetails + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0) + "/" + preferences.getString(AppTexts.fyId,"");
        System.out.println("url::: " + url);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", bankName);
            postObject.put("branch", branch);
            postObject.put("type", accountType);
            postObject.put("accountNumber", accountNumber);
            postObject.put("openingDate", openingDate);
            postObject.put("openingBalance", openingBalance);
            postObject.put("drCr", balanceType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest bankCreationRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                submitBankDetails(),
                activityErrorListener());
//        AppUtil.customizeRequest(bankCreationRequest);
        requestQueue.add(bankCreationRequest);
    }

    private Response.Listener<JSONObject> submitBankDetails() {
        return response -> {
            System.out.println("submitResponse:: " + response);
            try {
                String message = response.getString("message");
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
                    builder.setTitle(AppTexts.success);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(AppTexts.ok, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        getBankLedgerList();
                    });
                    builder.show();
                } else {
                    progressDialog.dismiss();
                    AppUtil.infoDialog(activity, AppTexts.error, message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AppUtil.somethingWrongDialog(activity);
            }
        };
    }


    private void populateDebitCreditSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select Balance Type");
        spinnerArray.add("Dr");
        spinnerArray.add("Cr");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        balanceTypeSpinnerBank.setAdapter(adapter);
    }


    private void populateAccountTypeSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select Account Type");
        spinnerArray.add("Fixed");
        spinnerArray.add("Saving");
        spinnerArray.add("OD");
        spinnerArray.add("Loan");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
    }

    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }


}
