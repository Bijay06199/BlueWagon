package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.AccountLedgerAdapter;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.accGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.accSubGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.AccountGroupDTO;
import com.example.bluewagon.ecalculo.dtos.AccountLedgerDTO;
import com.example.bluewagon.ecalculo.dtos.AccountSubGroupDTO;
import com.example.bluewagon.ecalculo.listeners.AccountGroupSelectedListener;
import com.example.bluewagon.ecalculo.listeners.AccountSubGroupSelectedListener;
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

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, AccountGroupSelectedListener, AccountSubGroupSelectedListener {

    private CreateAccountActivity activity = CreateAccountActivity.this;
    private TextView tVCancelAccountCreation, tVAccountGroup, tVAccountSubGroup;
    private CardView cVAddIcon;
    private Spinner balanceTypeSpinnerAccount;

    private HorizontalScrollView scrollMain;
    private RecyclerView rVAccountLedger;
    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0, accountHeadId, accountSubHeadId;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog dialog, accountGroup, accountSubGroup;
    private AccountLedgerAdapter accountLedgerAdapter;

    private List<AccountLedgerDTO> accountLedgerDTOS;

    private androidx.appcompat.app.AlertDialog progressDialog;
    private List<AccountGroupDTO> accountGroupDTOList;
    private List<AccountSubGroupDTO> accountSubGroupDTOList;
    private accGroupAdapter accountGroupAdapter;
    private com.example.bluewagon.ecalculo.adapters.creationAdapter.accSubGroupAdapter accSubGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();
        getMasterList();
//        populateDebitCreditSpinner();
    }

    private void init() {
        tVCancelAccountCreation = findViewById(R.id.tVCancelAccountCreation);
        cVAddIcon = findViewById(R.id.cVAddIcon);

        rVAccountLedger = findViewById(R.id.rVAccountLedger);
        scrollMain = findViewById(R.id.scrollMain);

        lLNoData = findViewById(R.id.lLNoData);
        inflater = LayoutInflater.from(activity);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        rVAccountLedger.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        cVAddIcon.setOnClickListener(activity);
        tVCancelAccountCreation.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelAccountCreation:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                getAccountHeadList();
                getAccountSubHeadList();
                View createAccountView = inflater.inflate(R.layout.layout_create_account, null);
                AlertDialog.Builder accountHeadBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                accountHeadBuilder.setView(createAccountView);
                TextView tVSaveAccount = createAccountView.findViewById(R.id.tVSaveAccount);
                TextView tVCancelAccount = createAccountView.findViewById(R.id.tVCancelAccount);
                tVAccountGroup = createAccountView.findViewById(R.id.tVAccountGroup);
                tVAccountSubGroup = createAccountView.findViewById(R.id.tVAccountSubGroup);

                EditText eTAccountName = createAccountView.findViewById(R.id.eTAccountName);
                EditText eTAccountShortName = createAccountView.findViewById(R.id.eTAccountShortName);
                EditText eTOpeningBalance = createAccountView.findViewById(R.id.eTOpeningBalance);


                tVAccountGroup.setOnClickListener(v13 -> populateAccountGroup(accountGroupDTOList));

                tVAccountSubGroup.setOnClickListener(v14 -> populateAccountSubGroup(accountSubGroupDTOList));


                balanceTypeSpinnerAccount = createAccountView.findViewById(R.id.balanceTypeSpinnerAccount);
                populateDebitCreditSpinner();
                tVSaveAccount.setOnClickListener(v1 -> {
                    String accountName = eTAccountName.getText().toString();
                    String accountShortName = eTAccountShortName.getText().toString();
                    String openingBalance = eTOpeningBalance.getText().toString();
                    String accountGroup = tVAccountGroup.getText().toString();
                    String accountSubGroup = tVAccountSubGroup.getText().toString();
                    String debitCredit = (String) balanceTypeSpinnerAccount.getSelectedItem();

                    dataValidation(accountName, accountGroup, openingBalance, debitCredit, accountShortName, accountSubGroup);
                });


                tVCancelAccount.setOnClickListener(v12 -> dialog.dismiss());

                accountHeadBuilder.setCancelable(false);
                dialog = accountHeadBuilder.create();
                Window accountGroupWindow = dialog.getWindow();
                assert accountGroupWindow != null;
                accountGroupWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                accountGroupWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;
        }
    }


    private void dataValidation(String accountName, String accountGroup, String openingBalance, String debitCredit, String accountShortName, String accountSubGroup) {
        if (accountName.isEmpty()) {
            AppUtil.showErrorTitleBox(activity, "Please Enter Account Name");
        } else if (accountGroup.equals("Select")) {
            AppUtil.showErrorTitleBox(activity, "Please select Account Group");
        } else {
            if (!openingBalance.isEmpty()) {
                if (debitCredit.equalsIgnoreCase("Select Balance Type")) {
                    AppUtil.showErrorTitleBox(activity, "Please select Transaction type(Dr/Cr)");
                }
            }
            saveData(accountName, accountGroup, openingBalance, debitCredit, accountShortName, accountSubGroup);
        }
    }


    private void saveData(String accountName, String accountGroup, String openingBalance, String debitCredit, String accountShortName, String accountSubGroup) {
        progressDialog.show();
        String url = APIs.createAccountMaster + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0) + "/" + preferences.getString(AppTexts.fyId,"");
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", accountName);
            postObject.put("shortName", accountShortName);
            postObject.put("accountHeadId", accountHeadId);
            postObject.put("accountSubHeadId", accountSubHeadId);
            postObject.put("openingBalance", openingBalance);
            postObject.put("drCr", debitCredit);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest loginRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                submitAccountLedger(),
                activityErrorListener());
        AppUtil.customizeRequest(loginRequest);
        requestQueue.add(loginRequest);
    }


    private Response.Listener<JSONObject> submitAccountLedger() {
        return response -> {
            System.out.println("submitResponse:: " + response);
            try {
                String message = response.getString("message");
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    dialog.dismiss();
                    progressDialog.dismiss();
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
                    builder.setTitle(AppTexts.success);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(AppTexts.ok, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        getMasterList();
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


    private void getAccountHeadList() {
        progressDialog.show();
        String url = APIs.accountHeadList + preferences.getString(AppTexts.orgId, "");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountHeadListResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }

    private Response.Listener<JSONObject> accountHeadListResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                accountGroupDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountNatureList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountNatureList.length(); i++) {
                        String object = accountNatureList.getJSONObject(i).toString();
                        accountGroupDTOList.add(new Gson().fromJson(object, AccountGroupDTO.class));
                    }
                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }


    private void populateAccountGroup(List<AccountGroupDTO> accGroupList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        accountGroupSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVCategories = view.findViewById(R.id.rVAccountGroup);
        rVCategories.setLayoutManager(new LinearLayoutManager(activity));
        accountGroupAdapter = new accGroupAdapter(activity, accGroupList, activity);
        rVCategories.setAdapter(accountGroupAdapter);

        accountGroup = (new AlertDialog.Builder(activity).setView(view)).create();
        accountGroup.show();
    }


    private void accountGroupSearchListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (accountGroupAdapter != null) {
                    accountGroupAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void getAccountSubHeadList() {
        progressDialog.show();
        String url = APIs.accountSubHeadList + preferences.getString(AppTexts.orgId, "");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountSubHeadListResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }


    private Response.Listener<JSONObject> accountSubHeadListResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                accountSubGroupDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountSubHeadList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountSubHeadList.length(); i++) {
                        String object = accountSubHeadList.getJSONObject(i).toString();
                        accountSubGroupDTOList.add(new Gson().fromJson(object, AccountSubGroupDTO.class));
                    }
                    System.out.println("accSubGroupSIze::::: " + accountSubGroupDTOList.size());

                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }


    private void populateAccountSubGroup(List<AccountSubGroupDTO> accSubGroupList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        accountSubGroupSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVSubGroup = view.findViewById(R.id.rVAccountGroup);
        rVSubGroup.setLayoutManager(new LinearLayoutManager(activity));
        accSubGroupAdapter = new accSubGroupAdapter(activity, accSubGroupList, activity);
        rVSubGroup.setAdapter(accSubGroupAdapter);

        accountSubGroup = (new AlertDialog.Builder(activity).setView(view)).create();
        accountSubGroup.show();
    }


    private void accountSubGroupSearchListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (accSubGroupAdapter != null) {
                    accSubGroupAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void getMasterList() {
        progressDialog.show();
        String url = APIs.accountMasterList + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountMasterList(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
        AppUtil.customizeRequest(headListResponse);
    }

    private Response.Listener<JSONObject> accountMasterList() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    accountLedgerDTOS = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        AccountLedgerDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), AccountLedgerDTO.class);
                        accountLedgerDTOS.add(dto);
                    }
                    if (accountLedgerDTOS.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVAccountLedger.setVisibility(View.VISIBLE);
                        accountLedgerAdapter = new AccountLedgerAdapter(activity, accountLedgerDTOS);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVAccountLedger.setLayoutManager(manager);
                        rVAccountLedger.setAdapter(accountLedgerAdapter);
                        rVAccountLedger.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVAccountLedger.setVisibility(View.GONE);
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


    private void populateDebitCreditSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select Balance Type");
        spinnerArray.add("Dr");
        spinnerArray.add("Cr");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        balanceTypeSpinnerAccount.setAdapter(adapter);
    }

    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }

    @Override
    public void onAccountGroupSelected(String subHeadName, int subHeadCode) {
        System.out.println("name:::: " + subHeadName);
        System.out.println("code:::: " + subHeadCode);
        tVAccountGroup.setText(subHeadName);
        accountHeadId = subHeadCode;
        accountGroup.dismiss();
    }

    @Override
    public void onAccountSubGroupSelected(String subGroupName, int subGroupCode) {
        System.out.println("name:::: " + subGroupName);
        System.out.println("code:::: " + subGroupCode);
        tVAccountSubGroup.setText(subGroupName);
        accountSubHeadId = subGroupCode;
        accountSubGroup.dismiss();
    }
}
