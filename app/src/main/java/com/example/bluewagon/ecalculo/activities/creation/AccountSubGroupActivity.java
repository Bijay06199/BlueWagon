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
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.bluewagon.ecalculo.adapters.creationAdapter.AccountSubGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.accGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.AccountGroupDTO;
import com.example.bluewagon.ecalculo.dtos.AccountSubGroupDTO;
import com.example.bluewagon.ecalculo.listeners.AccountGroupSelectedListener;
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

public class AccountSubGroupActivity extends AppCompatActivity implements View.OnClickListener, AccountGroupSelectedListener {

    private AccountSubGroupActivity activity = AccountSubGroupActivity.this;
    private TextView tVCancelAccountSubGroup, tVAccountMainGroup;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVAccountSubGroupList;

    private RequestQueue requestQueue;
    private int scrollX = 0, accountHeadId;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog dialog, accountGroup;
    private LinearLayout lLNoData;
    private AccountSubGroupAdapter accountSubGroupAdapter;
    private accGroupAdapter accountGroupAdapter;

    private List<AccountSubGroupDTO> accountSubGroupDTOList;
    private List<AccountGroupDTO> accountGroupDTOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sub_group);
        init();
        getSubGroupList();
    }

    private void init() {
        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        tVCancelAccountSubGroup = findViewById(R.id.tVCancelAccountSubGroup);
        rVAccountSubGroupList = findViewById(R.id.rVAccountSubGroupList);
        lLNoData = findViewById(R.id.lLNoData);
        inflater = LayoutInflater.from(activity);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        rVAccountSubGroupList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tVCancelAccountSubGroup.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }

    private void getSubGroupList() {
        progressDialog.show();
        String url = APIs.accountSubHeadList + preferences.getString(AppTexts.orgId, "");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountSubGroupResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }


    private Response.Listener<JSONObject> accountSubGroupResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    accountSubGroupDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        AccountSubGroupDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), AccountSubGroupDTO.class);
                        accountSubGroupDTOList.add(dto);
                    }
                    if (accountSubGroupDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVAccountSubGroupList.setVisibility(View.VISIBLE);
                        accountSubGroupAdapter = new AccountSubGroupAdapter(activity, accountSubGroupDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVAccountSubGroupList.setLayoutManager(manager);
                        rVAccountSubGroupList.setAdapter(accountSubGroupAdapter);
                        rVAccountSubGroupList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVAccountSubGroupList.setVisibility(View.GONE);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cVAddIcon:
                getAccountHead();
                View accountHeadView = inflater.inflate(R.layout.layout_account_sub_group, null);
                AlertDialog.Builder accountHeadBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                accountHeadBuilder.setView(accountHeadView);
                TextView tVSaveAccountSubGroup = accountHeadView.findViewById(R.id.tVSaveAccountSubGroup);
                TextView tVCancelAccountSubGroup = accountHeadView.findViewById(R.id.tVCancelAccountSubGroup);
                EditText eTAccountSubGroup = accountHeadView.findViewById(R.id.eTAccountSubGroup);
                EditText eTAccountShortName = accountHeadView.findViewById(R.id.eTAccountShortName);

                tVAccountMainGroup = accountHeadView.findViewById(R.id.tVAccountMainGroup);

                tVAccountMainGroup.setOnClickListener(v1 -> populateAccountGroup(accountGroupDTOList));

                tVSaveAccountSubGroup.setOnClickListener(v12 -> {
                   String accountSubGroup = eTAccountSubGroup.getText().toString();
                   String accountShortName = eTAccountShortName.getText().toString();
                   String accountHead = tVAccountMainGroup.getText().toString();
                    if (dataValidation(accountSubGroup, accountHead)) {
                        if (AppUtil.isConnectionAvailable(activity)) {
                            dialog.dismiss();
                            sendData(accountSubGroup, accountShortName, accountHeadId);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(activity, "Offline Mode!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        String message = "Please enter all the required fields.";
                        AppUtil.showErrorTitleBox(activity, message);
                    }
                });

                tVCancelAccountSubGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                accountHeadBuilder.setCancelable(true);
                dialog = accountHeadBuilder.create();
                Window accountSubGroupWindow = dialog.getWindow();
                assert accountSubGroupWindow != null;
                accountSubGroupWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                accountSubGroupWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;


            case R.id.tVCancelAccountSubGroup:
                onBackPressed();
                break;

        }
    }


    private void sendData(String subGroupName, String shortName, int accountHeadId) {
        String url = APIs.createSubAccountHead + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", subGroupName);
            postObject.put("shortName", shortName);
            postObject.put("headId", accountHeadId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest loginRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                submitAccountHead(),
                activityErrorListener());
//        AppUtil.customizeRequest(loginRequest);
        requestQueue.add(loginRequest);
    }

    private Response.Listener<JSONObject> submitAccountHead() {
        return response -> {
            System.out.println("submitResponse:: " + response);
            try {
                String message = response.getString("message");
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
                    builder.setTitle(AppTexts.success);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(AppTexts.ok, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        getSubGroupList();
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


    private boolean dataValidation(String accountGroup, String accountHead) {
        return !accountGroup.isEmpty() && !accountHead.equals("Select");
    }

    private void getAccountHead() {
        progressDialog.show();
        String url = APIs.accountHeadList + preferences.getString(AppTexts.orgId, "");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountHeadResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }

    private Response.Listener<JSONObject> accountHeadResponse() {
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
        tVAccountMainGroup.setText(subHeadName);
        accountHeadId = subHeadCode;
        accountGroup.dismiss();
    }
}
