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
import com.example.bluewagon.ecalculo.adapters.creationAdapter.ItemGrpAdapter;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.ItemSubGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.ItemGroupDTO;
import com.example.bluewagon.ecalculo.dtos.ItemSubGroupDTO;
import com.example.bluewagon.ecalculo.listeners.ItemGroupSelectedListener;
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

public class ItemSubGroupActivity extends AppCompatActivity implements View.OnClickListener, ItemGroupSelectedListener {

    private ItemSubGroupActivity activity = ItemSubGroupActivity.this;
    private TextView tVCancelItemSubCategory, tVItemGroup;
    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVItemSubCategory;

    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog accountGroup, dialog;
    private ItemSubGroupAdapter itemSubGroupAdapter;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private List<ItemSubGroupDTO> itemSubGroupDTOList;

    private List<ItemGroupDTO> itemGroupDTOList;
    private ItemGrpAdapter itemGroupAdapter;
    private int selectedGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_sub_group);
        init();
        getItemSubGroupList();
    }

    private void init() {
        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);
        rVItemSubCategory = findViewById(R.id.rVItemSubCategory);
        tVCancelItemSubCategory = findViewById(R.id.tVCancelItemSubCategory);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        inflater = LayoutInflater.from(activity);


        rVItemSubCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tVCancelItemSubCategory.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelItemSubCategory:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                getItemGroup();
                View subGroupView = inflater.inflate(R.layout.layout_item_sub_category, null);
                AlertDialog.Builder subGroupBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                subGroupBuilder.setView(subGroupView);
                TextView tVSaveItemCat = subGroupView.findViewById(R.id.tVSaveItemCat);
                TextView tVCancelItemCat = subGroupView.findViewById(R.id.tVCancelItemCat);
                tVItemGroup = subGroupView.findViewById(R.id.tVItemGroup);
                EditText eTItemSubGroup = subGroupView.findViewById(R.id.eTItemSubGroup);

                tVItemGroup.setOnClickListener(view -> populateItemGroup(itemGroupDTOList));


                tVCancelItemCat.setOnClickListener(view -> dialog.dismiss());

                tVSaveItemCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subGroupName = eTItemSubGroup.getText().toString();
                        String groupItem = tVItemGroup.getText().toString();
                        if (!subGroupName.isEmpty() && !groupItem.equals("Select")) {
                            saveData(groupItem, subGroupName);
                        } else {
                            AppUtil.showErrorTitleBox(activity, "Please enter all the required Fields");
                        }
                    }
                });

                subGroupBuilder.setCancelable(false);
                dialog = subGroupBuilder.create();
                Window measurementWindow = dialog.getWindow();
                assert measurementWindow != null;
                measurementWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                measurementWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;
        }
    }

    private void getItemSubGroupList() {
        progressDialog.show();
        String url = APIs.itemSubGroup + preferences.getString(AppTexts.orgId, "");
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
                    itemSubGroupDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        ItemSubGroupDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), ItemSubGroupDTO.class);
                        itemSubGroupDTOList.add(dto);
                    }
                    if (itemSubGroupDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVItemSubCategory.setVisibility(View.VISIBLE);
                        itemSubGroupAdapter = new ItemSubGroupAdapter(activity, itemSubGroupDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVItemSubCategory.setLayoutManager(manager);
                        rVItemSubCategory.setAdapter(itemSubGroupAdapter);
                        rVItemSubCategory.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVItemSubCategory.setVisibility(View.GONE);
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

    private void saveData(String itemGroup, String subGroupName) {
        progressDialog.show();
        String url = APIs.itemSubGroup + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        System.out.println("url::: " + url);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", subGroupName);
            postObject.put("groupId", selectedGroupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest itemGroupRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                submitItemSubGroupResponse(),
                activityErrorListener());
//        AppUtil.customizeRequest(itemGroupRequest);
        requestQueue.add(itemGroupRequest);
    }

    private Response.Listener<JSONObject> submitItemSubGroupResponse() {
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
                        getItemSubGroupList();
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


    private void getItemGroup() {
        progressDialog.show();
        String url = APIs.itemGroup + preferences.getString(AppTexts.orgId, "");
        APIRequest itemGroupList = new APIRequest(
                Request.Method.GET,
                url,
                itemGroupResponse(),
                activityErrorListener()
        );
        requestQueue.add(itemGroupList);
//        AppUtil.customizeRequest(itemGroupList);
    }


    private Response.Listener<JSONObject> itemGroupResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                itemGroupDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray itemGroupList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < itemGroupList.length(); i++) {
                        String object = itemGroupList.getJSONObject(i).toString();
                        itemGroupDTOList.add(new Gson().fromJson(object, ItemGroupDTO.class));
                    }

                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }


    private void populateItemGroup(List<ItemGroupDTO> itemGroupDTOList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        accountGroupSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVCategories = view.findViewById(R.id.rVAccountGroup);
        rVCategories.setLayoutManager(new LinearLayoutManager(activity));
        itemGroupAdapter = new ItemGrpAdapter(activity, itemGroupDTOList, activity);
        rVCategories.setAdapter(itemGroupAdapter);

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
                if (itemGroupAdapter != null) {
                    itemGroupAdapter.getFilter().filter(s.toString());
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
    public void onItemGroupSelected(String itemGroupName, int itemGroupCode) {
        System.out.println("name::: " + itemGroupName);
        System.out.println("code::: " + itemGroupCode);

        selectedGroupId = itemGroupCode;

        tVItemGroup.setText(itemGroupName);
        accountGroup.dismiss();

    }
}