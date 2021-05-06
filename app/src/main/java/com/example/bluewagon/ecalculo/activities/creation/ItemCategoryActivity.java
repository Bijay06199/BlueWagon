package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.ItemGroupAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.ItemGroupDTO;
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

public class ItemCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemCategoryActivity activity = ItemCategoryActivity.this;
    private TextView tVCancelItemCategory;
    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVItemCategory;

    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    private ItemGroupAdapter itemGroupAdapter;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private List<ItemGroupDTO> itemGroupDTOList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_category);
        init();
        getItemGroupList();
    }

    private void init() {
        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);
        rVItemCategory = findViewById(R.id.rVItemCategory);
        tVCancelItemCategory = findViewById(R.id.tVCancelItemCategory);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        inflater = LayoutInflater.from(activity);


        rVItemCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tVCancelItemCategory.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelItemCategory:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                View itemCatView = inflater.inflate(R.layout.layout_add_item_category, null);
                AlertDialog.Builder itemCatBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                itemCatBuilder.setView(itemCatView);
                TextView tVSaveItemCat = itemCatView.findViewById(R.id.tVSaveItemCat);
                TextView tVCancelItemCat = itemCatView.findViewById(R.id.tVCancelItemCat);

                EditText eTItemCategory = itemCatView.findViewById(R.id.eTItemCategory);


                tVSaveItemCat.setOnClickListener(view -> {
                    String categoryName = eTItemCategory.getText().toString();
                    if (!categoryName.isEmpty()){
                        sendData(categoryName);
                    }else{
                        AppUtil.showErrorTitleBox(activity,"Please enter Item group.");
                    }
                });


                tVCancelItemCat.setOnClickListener(view -> dialog.dismiss());


                itemCatBuilder.setCancelable(false);
                dialog = itemCatBuilder.create();
                Window itemCatWindow = dialog.getWindow();
                assert itemCatWindow != null;
                itemCatWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                itemCatWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;


        }
    }

    private void sendData(String categoryName){
        progressDialog.show();
        String url = APIs.itemGroup + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0) ;
        System.out.println("url::: " + url);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", categoryName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest itemGroupRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                submitItemGroupResponse(),
                activityErrorListener());
//        AppUtil.customizeRequest(itemGroupRequest);
        requestQueue.add(itemGroupRequest);
    }


    private Response.Listener<JSONObject> submitItemGroupResponse() {
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
                        getItemGroupList();
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


    private void getItemGroupList(){
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
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    itemGroupDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        ItemGroupDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), ItemGroupDTO.class);
                        itemGroupDTOList.add(dto);
                    }
                    if (itemGroupDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVItemCategory.setVisibility(View.VISIBLE);
                        itemGroupAdapter = new ItemGroupAdapter(activity, itemGroupDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVItemCategory.setLayoutManager(manager);
                        rVItemCategory.setAdapter(itemGroupAdapter);
                        rVItemCategory.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVItemCategory.setVisibility(View.GONE);
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
}
