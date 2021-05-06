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
import android.widget.Toast;

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
import com.example.bluewagon.ecalculo.adapters.creationAdapter.AccountGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.AccountGroupDTO;
import com.example.bluewagon.ecalculo.dtos.AccountNatureDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountGroupActivity extends AppCompatActivity implements View.OnClickListener {
    private AccountGroupActivity activity = AccountGroupActivity.this;

    private TextView tVAddAccountGroup, tVCancelAccountGroup;
    private RecyclerView rVAccountGroupList;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private List<AccountGroupDTO> accountGroupDTOList;
    private LinearLayout lLNoData;
    private AccountGroupAdapter accountGroupAdapter;
    private HorizontalScrollView scrollMain;
    private CardView cVAddIcon;

    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    private List<AccountNatureDTO> accountNatureDTOList;
    private Spinner accountNature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_group);
        init();
        getAccountGroupList();
    }

    private void init() {
        tVAddAccountGroup = findViewById(R.id.tVAddAccountGroup);
        tVCancelAccountGroup = findViewById(R.id.tVCancelAccountGroup);

        rVAccountGroupList = findViewById(R.id.rVAccountGroupList);

        lLNoData = findViewById(R.id.lLNoData);
        scrollMain = findViewById(R.id.scrollMain);
        cVAddIcon = findViewById(R.id.cVAddIcon);

        inflater = LayoutInflater.from(activity);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        rVAccountGroupList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


        onClickListeners();
    }

    private void onClickListeners() {
        tVAddAccountGroup.setOnClickListener(activity);
        tVCancelAccountGroup.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }

    private void getAccountGroupList() {
        progressDialog.show();
        String url = APIs.accountHeadList + preferences.getString(AppTexts.orgId, "");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountGroupResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }


    private Response.Listener<JSONObject> accountGroupResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    accountGroupDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        AccountGroupDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), AccountGroupDTO.class);
                        accountGroupDTOList.add(dto);
                    }
                    if (accountGroupDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVAccountGroupList.setVisibility(View.VISIBLE);
                        accountGroupAdapter = new AccountGroupAdapter(activity, accountGroupDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVAccountGroupList.setLayoutManager(manager);
                        rVAccountGroupList.setAdapter(accountGroupAdapter);
                        rVAccountGroupList.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVAccountGroupList.setVisibility(View.GONE);
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
                getAccountNature();
                View accountHeadView = inflater.inflate(R.layout.layout_add_head_list, null);
                AlertDialog.Builder accountHeadBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                accountHeadBuilder.setView(accountHeadView);
                TextView tVSaveAccountSubGroup = accountHeadView.findViewById(R.id.tVSaveAccountSubGroup);
                TextView tVCancelAccountSubGroup = accountHeadView.findViewById(R.id.tVCancelAccountSubGroup);


                EditText eTAccountGroup = accountHeadView.findViewById(R.id.eTAccountGroup);
                accountNature = accountHeadView.findViewById(R.id.accountNature);

                tVSaveAccountSubGroup.setOnClickListener(v1 -> {
                    String accountGroup = eTAccountGroup.getText().toString();
                    String accountNatureName = accountNature.getSelectedItem().toString();
                    int accountNatureId = (int) accountNature.getSelectedItemId();

                    if (dataValidation(accountGroup, accountNatureName)) {
                        //send data to server
                        if (AppUtil.isConnectionAvailable(activity)) {
                            dialog.dismiss();
                            sendData(accountGroup, accountNatureId);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(activity, "Offline Mode!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String message = "Please enter all the required fields.";
                        AppUtil.showErrorTitleBox(activity, message);
                    }
                });


                tVCancelAccountSubGroup.setOnClickListener(v12 -> dialog.dismiss());

                accountHeadBuilder.setCancelable(true);
                dialog = accountHeadBuilder.create();
                Window accountGroupWindow = dialog.getWindow();
                assert accountGroupWindow != null;
                accountGroupWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                accountGroupWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;

            case R.id.tVCancelAccountGroup:
                onBackPressed();
                break;
        }
    }


    private void sendData(String name, int natureId) {
        String url = APIs.createAccountHead + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", name);
            postObject.put("accountNatureId", natureId);
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
                        getAccountGroupList();
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

    private void getAccountNature() {
        progressDialog.show();
        String url = APIs.accountNatureList;
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountNatureResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }


    private Response.Listener<JSONObject> accountNatureResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                accountNatureDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountNatureList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountNatureList.length(); i++) {
                        String object = accountNatureList.getJSONObject(i).toString();
                        accountNatureDTOList.add(new Gson().fromJson(object, AccountNatureDTO.class));
                    }
                    populateAccountNature(accountNatureDTOList);
                    System.out.println("categoriesList::: " + accountNatureDTOList.size());

                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }

    private void populateAccountNature(List<AccountNatureDTO> accNatureList) {
        System.out.println("accountNatureList:::: " + accNatureList.size());
        List<String> categoriesStringList = new ArrayList<>();
        categoriesStringList.add("Select Account Nature");
        HashMap<String, Integer> accountNatureHashMap = new HashMap<>();
        int layout = android.R.layout.simple_spinner_item;
        int dropdown = android.R.layout.simple_spinner_dropdown_item;

        for (int i = 0; i < accNatureList.size(); i++) {
            categoriesStringList.add(accNatureList.get(i).getName());
            accountNatureHashMap.put(accNatureList.get(i).getName(), accNatureList.get(i).getId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, layout, categoriesStringList);
        adapter.setDropDownViewResource(dropdown);
        accountNature.setAdapter(adapter);
    }

    private boolean dataValidation(String accountGroup, String accountMainGroup) {
        return !accountGroup.isEmpty() && !accountMainGroup.equals("Select Account Nature");
    }


    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }


}