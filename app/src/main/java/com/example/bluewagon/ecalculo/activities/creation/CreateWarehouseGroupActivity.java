package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
import com.example.bluewagon.ecalculo.adapters.creationAdapter.WarehouseGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.WarehouseGroupDTO;
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

public class CreateWarehouseGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private CreateWarehouseGroupActivity activity = CreateWarehouseGroupActivity.this;
    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVWarehouseGroup;

    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog  dialog;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private TextView tVCancelWarehouseGroup;

    private List<WarehouseGroupDTO> warehouseGroupDTOList;
    private WarehouseGroupAdapter warehouseGroupAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_warehouse_group);
        init();
        getWarehouseGroupList();
    }

    private void init() {
        tVCancelWarehouseGroup = findViewById(R.id.tVCancelWarehouseGroup);

        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);
        rVWarehouseGroup = findViewById(R.id.rVWarehouseGroup);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        inflater = LayoutInflater.from(activity);


        rVWarehouseGroup.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tVCancelWarehouseGroup.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tVCancelWarehouseGroup:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                View warehouseView = inflater.inflate(R.layout.layout_warehouse_group, null);
                AlertDialog.Builder warehouseBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                warehouseBuilder.setView(warehouseView);

                EditText eTWarehouseGroup = warehouseView.findViewById(R.id.eTWarehouseGroup);
                TextView tVSaveWarehouseGroup = warehouseView.findViewById(R.id.tVSaveWarehouseGroup);
                TextView tVCancelWarehouseGroup = warehouseView.findViewById(R.id.tVCancelWarehouseGroup);

                tVCancelWarehouseGroup.setOnClickListener(view -> dialog.dismiss());

                tVSaveWarehouseGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String warehouseGroup = eTWarehouseGroup.getText().toString();
                        if (!warehouseGroup.isEmpty()){
                            dialog.dismiss();
                            sendData(warehouseGroup);
                        }else{
                            AppUtil.showErrorTitleBox(activity, "Please Enter warehouse Group Name");
                        }
                    }
                });


                warehouseBuilder.setCancelable(false);
                dialog = warehouseBuilder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;
        }
    }

    private void getWarehouseGroupList(){
        progressDialog.show();
        String url = APIs.warehouseGroupList + preferences.getString(AppTexts.orgId, "");
        APIRequest itemSubGroupList = new APIRequest(
                Request.Method.GET,
                url,
                itemSubGroupResponse(),
                activityErrorListener()
        );
        requestQueue.add(itemSubGroupList);
//        AppUtil.customizeRequest(itemSubGroupList);
    }

    private Response.Listener<JSONObject> itemSubGroupResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    warehouseGroupDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        WarehouseGroupDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), WarehouseGroupDTO.class);
                        warehouseGroupDTOList.add(dto);
                    }
                    if (warehouseGroupDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVWarehouseGroup.setVisibility(View.VISIBLE);
                        warehouseGroupAdapter = new WarehouseGroupAdapter(activity, warehouseGroupDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVWarehouseGroup.setLayoutManager(manager);
                        rVWarehouseGroup.setAdapter(warehouseGroupAdapter);
                        rVWarehouseGroup.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVWarehouseGroup.setVisibility(View.GONE);
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

    private void sendData(String warehouseGroup){
        progressDialog.show();
        String url = APIs.createWarehouse + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        System.out.println("url::: " + url);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", warehouseGroup);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest itemGroupRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                warehouseGroupResponse(),
                activityErrorListener());
//        AppUtil.customizeRequest(itemGroupRequest);
        requestQueue.add(itemGroupRequest);
    }

    private Response.Listener<JSONObject> warehouseGroupResponse() {
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
                        getWarehouseGroupList();
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


    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }


}
