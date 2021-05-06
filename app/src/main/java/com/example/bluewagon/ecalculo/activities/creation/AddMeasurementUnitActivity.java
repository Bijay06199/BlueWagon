package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.bluewagon.ecalculo.adapters.creationAdapter.MeasurementUnitAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.UnitsDTO;
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

public class AddMeasurementUnitActivity extends AppCompatActivity implements View.OnClickListener {
    private AddMeasurementUnitActivity activity = AddMeasurementUnitActivity.this;
    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVMeasurementUnit;

    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog descDialog, dialog;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private TextView tVCancelMeasurementUnit;

    private List<UnitsDTO> measurementUnitDTOList;
    private MeasurementUnitAdapter measurementUnitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement_unit);
        init();
        getMeasurementUnitList();
    }

    private void init() {
        tVCancelMeasurementUnit = findViewById(R.id.tVCancelMeasurementUnit);

        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);
        rVMeasurementUnit = findViewById(R.id.rVMeasurementUnit);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        inflater = LayoutInflater.from(activity);


        rVMeasurementUnit.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tVCancelMeasurementUnit.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelMeasurementUnit:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                View measurementView = inflater.inflate(R.layout.layout_measurement_unit, null);
                AlertDialog.Builder measurementBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                measurementBuilder.setView(measurementView);

                TextView tVSaveUnits = measurementView.findViewById(R.id.tVSaveUnits);
                TextView tVCancelUnits = measurementView.findViewById(R.id.tVCancelUnits);

                EditText unitName = measurementView.findViewById(R.id.eTUnitName);
                EditText unitShortName = measurementView.findViewById(R.id.eTUnitShortName);
                TextView description = measurementView.findViewById(R.id.tVNarration);

                tVCancelUnits.setOnClickListener(view -> dialog.dismiss());

                description.setOnClickListener(view -> {
                    View narrationDialog = inflater.inflate(R.layout.layout_narration_dialog, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                    builder.setView(narrationDialog);

                    TextView tVCancel = narrationDialog.findViewById(R.id.tVCancel);
                    TextView tVHeading = narrationDialog.findViewById(R.id.tVHeading);
                    TextView tVOk = narrationDialog.findViewById(R.id.tVOk);
                    final EditText eTNarrationText = narrationDialog.findViewById(R.id.eTNarrationText);


                    tVHeading.setText(R.string.description);

                    //making the editText view of certain size and making cursor on desired pos
                    eTNarrationText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    eTNarrationText.setSingleLine(false);
                    eTNarrationText.setLines(5);
                    eTNarrationText.setMaxLines(5);
                    eTNarrationText.setGravity(Gravity.START | Gravity.TOP);

                    //setting the previous narration on editText, if initial ignore else show same
                    String previousNarration = description.getText().toString();
                    if (previousNarration.equalsIgnoreCase("Narration")) {
                        eTNarrationText.setText("");
                    } else {
                        eTNarrationText.setText(previousNarration);
                    }

                    tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                    tVOk.setOnClickListener(v12 -> {
                        String narration = eTNarrationText.getText().toString();
                        description.setText(narration);
                        descDialog.dismiss();
                    });

                    builder.setCancelable(false);
                    descDialog = builder.create();
                    Window window = descDialog.getWindow();
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    descDialog.show();
                });

                tVSaveUnits.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = unitName.getText().toString();
                        String shortName = unitShortName.getText().toString();
                        String desc = description.getText().toString();

                        if (!name.isEmpty() || !shortName.isEmpty()) {
                            dialog.dismiss();
                            saveUnits(name, shortName, desc);
                        } else {
                            AppUtil.showErrorTitleBox(activity, "Please enter the required Fields.");
                        }
                    }
                });


                measurementBuilder.setCancelable(false);
                dialog = measurementBuilder.create();
                Window measurementWindow = dialog.getWindow();
                assert measurementWindow != null;
                measurementWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                measurementWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;
        }
    }


    private void getMeasurementUnitList() {
        progressDialog.show();
        String url = APIs.measurementUnit + preferences.getString(AppTexts.orgId, "");
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
                    measurementUnitDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        UnitsDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), UnitsDTO.class);
                        measurementUnitDTOList.add(dto);
                    }
                    if (measurementUnitDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVMeasurementUnit.setVisibility(View.VISIBLE);
                        measurementUnitAdapter = new MeasurementUnitAdapter(activity, measurementUnitDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVMeasurementUnit.setLayoutManager(manager);
                        rVMeasurementUnit.setAdapter(measurementUnitAdapter);
                        rVMeasurementUnit.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVMeasurementUnit.setVisibility(View.GONE);
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


    private void saveUnits(String name, String shortName, String description) {
        progressDialog.show();
        String url = APIs.measurementUnit + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getInt(AppTexts.userId, 0);
        System.out.println("url::: " + url);
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", name);
            postObject.put("shortName", shortName);
            postObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest itemGroupRequest = new APIRequest(
                Request.Method.POST,
                url,
                postObject,
                measurementResponse(),
                activityErrorListener());
//        AppUtil.customizeRequest(itemGroupRequest);
        requestQueue.add(itemGroupRequest);
    }

    private Response.Listener<JSONObject> measurementResponse() {
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
                        getMeasurementUnitList();
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
