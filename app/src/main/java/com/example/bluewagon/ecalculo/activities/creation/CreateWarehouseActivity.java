package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
import com.example.bluewagon.ecalculo.adapters.creationAdapter.WarehouseAdapter;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.WarehouseGrpAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.WarehouseDTO;
import com.example.bluewagon.ecalculo.dtos.WarehouseGroupDTO;
import com.example.bluewagon.ecalculo.listeners.WarehouseGroupSelectedListener;
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

public class CreateWarehouseActivity extends AppCompatActivity implements View.OnClickListener, WarehouseGroupSelectedListener {

    private CreateWarehouseActivity activity = CreateWarehouseActivity.this;
    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVWarehouse;

    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog dialog, warehouseGroup;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private TextView tVCancelWarehouse, tVWarehouseGroup;

    private List<WarehouseDTO> warehouseDTOList;
    private WarehouseAdapter warehouseAdapter;
    private List<WarehouseGroupDTO> warehouseGroupDTOList;
    private WarehouseGrpAdapter warehouseGrpAdapter;
    private int warehouseGrpId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_warehouse);
        init();
        getWarehouseList();
    }

    private void init(){
        tVCancelWarehouse = findViewById(R.id.tVCancelWarehouse);

        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);
        rVWarehouse = findViewById(R.id.rVWarehouse);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        inflater = LayoutInflater.from(activity);


        rVWarehouse.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void initListeners(){
        tVCancelWarehouse.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cVAddIcon:
                getWarehouseGroup();
                View warehouseView = inflater.inflate(R.layout.layout_create_warehouse, null);
                AlertDialog.Builder warehouseBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                warehouseBuilder.setView(warehouseView);

                tVWarehouseGroup = warehouseView.findViewById(R.id.tVWarehouseGroup);
                TextView tVSaveWarehouse = warehouseView.findViewById(R.id.tVSaveWarehouse);
                TextView tVCancelWarehouse = warehouseView.findViewById(R.id.tVCancelWarehouse);
                CheckBox cBMaster = warehouseView.findViewById(R.id.cBMaster);

                EditText eTWarehouse = warehouseView.findViewById(R.id.eTWarehouse);
                EditText eTWarehouseLocation = warehouseView.findViewById(R.id.eTWarehouseLocation);

                tVWarehouseGroup.setOnClickListener(view -> populateWarehouseGroup(warehouseGroupDTOList));

                tVCancelWarehouse.setOnClickListener(view -> dialog.dismiss());

                tVSaveWarehouse.setOnClickListener(v1 -> {
                    String groupName = tVWarehouseGroup.getText().toString();
                    String name = eTWarehouse.getText().toString();
                    String location = eTWarehouseLocation.getText().toString();
                    boolean master = cBMaster.isChecked();

                    if (validation(groupName, name)){
                        dialog.dismiss();
                        sendData(groupName, name, location, master);
                    }
                });

                warehouseBuilder.setCancelable(true);
                dialog = warehouseBuilder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;

            case R.id.tVCancelWarehouse:
                onBackPressed();
                break;

        }
    }

    private boolean validation(String group, String name){
        return !group.equals("Select") || !name.isEmpty();
    }


    private void getWarehouseGroup(){
        progressDialog.show();
        String url = APIs.warehouseGroupList + preferences.getString(AppTexts.orgId, "");
        APIRequest itemSubGroupList = new APIRequest(
                Request.Method.GET,
                url,
                itemGroupResponse(),
                activityErrorListener()
        );
        requestQueue.add(itemSubGroupList);
//        AppUtil.customizeRequest(itemSubGroupList);
    }


    private Response.Listener<JSONObject> itemGroupResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                warehouseGroupDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray itemGroupList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < itemGroupList.length(); i++) {
                        String object = itemGroupList.getJSONObject(i).toString();
                        warehouseGroupDTOList.add(new Gson().fromJson(object, WarehouseGroupDTO.class));
                    }

                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }


    private void populateWarehouseGroup(List<WarehouseGroupDTO> warehouseGroupDTOList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        accountGroupSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVCategories = view.findViewById(R.id.rVAccountGroup);
        rVCategories.setLayoutManager(new LinearLayoutManager(activity));
        warehouseGrpAdapter = new WarehouseGrpAdapter(activity, warehouseGroupDTOList, activity);
        rVCategories.setAdapter(warehouseGrpAdapter);

        warehouseGroup = (new AlertDialog.Builder(activity).setView(view)).create();
        warehouseGroup.show();
    }


    private void accountGroupSearchListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (warehouseGrpAdapter != null) {
                    warehouseGrpAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void sendData(String groupName, String name, String location, boolean master){
        progressDialog.show();
        String url = APIs.warehouse + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0) ;
        System.out.println("url::: " + url);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("warehouseGroupId", warehouseGrpId);
            postObject.put("name", name);
            postObject.put("location", location);
            postObject.put("isMasterWarehouse", master);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest itemGroupRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                submitWarehouseResponse(),
                activityErrorListener());
//        AppUtil.customizeRequest(itemGroupRequest);
        requestQueue.add(itemGroupRequest);
    }

    private Response.Listener<JSONObject> submitWarehouseResponse() {
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
                        getWarehouseList();
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


    private void getWarehouseList(){
        progressDialog.show();
        String url = APIs.warehouse + preferences.getString(AppTexts.orgId, "");
        APIRequest itemSubGroupList = new APIRequest(
                Request.Method.GET,
                url,
                warehouseResponse(),
                activityErrorListener()
        );
        requestQueue.add(itemSubGroupList);
//        AppUtil.customizeRequest(itemSubGroupList);
    }


    private Response.Listener<JSONObject> warehouseResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    warehouseDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        WarehouseDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), WarehouseDTO.class);
                        warehouseDTOList.add(dto);
                    }
                    if (warehouseDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVWarehouse.setVisibility(View.VISIBLE);
                        warehouseAdapter = new WarehouseAdapter(activity, warehouseDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVWarehouse.setLayoutManager(manager);
                        rVWarehouse.setAdapter(warehouseAdapter);
                        rVWarehouse.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVWarehouse.setVisibility(View.GONE);
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


    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }

    @Override
    public void onWarehouseGroupSelected(String warehouseGroupName, int warehouseGroupId) {
        warehouseGrpId = warehouseGroupId;
        tVWarehouseGroup.setText(warehouseGroupName);
        warehouseGroup.dismiss();
    }
}
