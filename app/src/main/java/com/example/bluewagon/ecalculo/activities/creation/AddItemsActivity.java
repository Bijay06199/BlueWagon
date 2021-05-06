package com.example.bluewagon.ecalculo.activities.creation;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.AddItemServiceAdapter;
import com.example.bluewagon.ecalculo.adapters.otherAdapters.FixedGridLayoutManager;
import com.example.bluewagon.ecalculo.dtos.ItemServiceDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddItemsActivity extends AppCompatActivity implements View.OnClickListener {
    private AddItemsActivity activity = AddItemsActivity.this;
    private CardView cVAddIcon;
    private HorizontalScrollView scrollMain;
    private RecyclerView rVItemService;

    private LinearLayout lLNoData;
    private RequestQueue requestQueue;
    private int scrollX = 0;
    private SharedPreferences preferences;
    private LayoutInflater inflater;
    private AlertDialog descDialog, dialog;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private TextView tVCancelItemService;
    private List<ItemServiceDTO> itemServiceDTOList;
    private AddItemServiceAdapter addItemServiceAdapter;
    private ImageView iVItemImage;
    private File path;
    private Bitmap selectedImage, resized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        init();
        getItemService();
    }

    private void init() {
        tVCancelItemService = findViewById(R.id.tVCancelItemService);

        cVAddIcon = findViewById(R.id.cVAddIcon);
        scrollMain = findViewById(R.id.scrollMain);
        lLNoData = findViewById(R.id.lLNoData);
        rVItemService = findViewById(R.id.rVItemService);

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        inflater = LayoutInflater.from(activity);


        rVItemService.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        tVCancelItemService.setOnClickListener(activity);
        cVAddIcon.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelItemService:
                onBackPressed();
                break;

            case R.id.cVAddIcon:
                View itemServiceView = inflater.inflate(R.layout.layout_add_items, null);
                AlertDialog.Builder itemServiceBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                itemServiceBuilder.setView(itemServiceView);

                TextView tVCancel = itemServiceView.findViewById(R.id.tVCancel);
                TextView tVSave = itemServiceView.findViewById(R.id.tVSave);
                TextView tVItem = itemServiceView.findViewById(R.id.tVItem);
                TextView tVService = itemServiceView.findViewById(R.id.tVService);
                TextView tVAddImage = itemServiceView.findViewById(R.id.tVAddImage);

                iVItemImage = itemServiceView.findViewById(R.id.iVItemImage);

                LinearLayout lLItemDetails = itemServiceView.findViewById(R.id.lLItemDetails);
                TextInputLayout tLPurchaseRate = itemServiceView.findViewById(R.id.tLPurchaseRate);

                tVCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                tVItem.setOnClickListener(v1 -> {
                    tVItem.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                    tVService.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                    tVItem.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                    tVService.setTextColor(ContextCompat.getColor(activity, R.color.white));
                    lLItemDetails.setVisibility(View.VISIBLE);
                    tLPurchaseRate.setVisibility(View.VISIBLE);
                });

                tVService.setOnClickListener(v12 -> {
                    tVItem.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                    tVService.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                    tVItem.setTextColor(ContextCompat.getColor(activity, R.color.white));
                    tVService.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                    lLItemDetails.setVisibility(View.GONE);
                    tLPurchaseRate.setVisibility(View.GONE);
                });

                tVAddImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CropImage.activity().start(activity);
                    }
                });

                tVSave.setOnClickListener(v13 -> dialog.dismiss());


                itemServiceBuilder.setCancelable(false);
                dialog = itemServiceBuilder.create();
                Window measurementWindow = dialog.getWindow();
                assert measurementWindow != null;
                measurementWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                measurementWindow.setGravity(Gravity.CENTER);
                dialog.show();
                break;
        }
    }

    //permission for camera to crop image
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "permission granted", Toast.LENGTH_SHORT).show();
                startCamera();
            }
        }
    }


    private void startCamera() {
        try {
            CropImage.activity().start(activity);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Oops!! - your device doesn't support capturing images!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String imgPath = resultUri.getPath();
                selectedImage = BitmapFactory.decodeFile(imgPath);
                resized = getResizedBitmap(selectedImage, 600);
                iVItemImage.setImageBitmap(resized);
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir(AppTexts.dirNameforImages, MODE_PRIVATE);
                path = new File(directory, AppTexts.name + "." + "test" + AppTexts.extension);
                System.out.println("path sent::" + path);
                saveDataToFile();
//                saveImage.setVisibility(View.VISIBLE);
//                deleteImage.setVisibility(View.GONE);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        System.out.println("ratio::" + bitmapRatio);
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
            System.out.println("height::" + height);
            System.out.println("width::" + width);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
            System.out.println("height::" + height);
            System.out.println("width::" + width);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void saveDataToFile() {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            resized.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getItemService() {
        progressDialog.show();
        String url = APIs.item + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest itemServiceRequest = new APIRequest(
                Request.Method.GET,
                url,
                itemServiceResponse(),
                activityErrorListener()
        );
        requestQueue.add(itemServiceRequest);
        AppUtil.customizeRequest(itemServiceRequest);
    }

    private Response.Listener<JSONObject> itemServiceResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    itemServiceDTOList = new ArrayList<>();
                    JSONArray listArray = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < listArray.length(); i++) {
                        ItemServiceDTO dto = new Gson().fromJson(listArray.getJSONObject(i).toString(), ItemServiceDTO.class);
                        itemServiceDTOList.add(dto);
                    }
                    if (itemServiceDTOList.size() > 0) {
                        lLNoData.setVisibility(View.GONE);
                        scrollMain.setVisibility(View.VISIBLE);
                        cVAddIcon.setVisibility(View.VISIBLE);
                        rVItemService.setVisibility(View.VISIBLE);
                        addItemServiceAdapter = new AddItemServiceAdapter(activity, itemServiceDTOList);

                        FixedGridLayoutManager manager = new FixedGridLayoutManager();
                        manager.setTotalColumnCount(1);
                        rVItemService.setLayoutManager(manager);
                        rVItemService.setAdapter(addItemServiceAdapter);
                        rVItemService.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        lLNoData.setVisibility(View.VISIBLE);
                        scrollMain.setVisibility(View.GONE);
                        cVAddIcon.setVisibility(View.GONE);
                        rVItemService.setVisibility(View.GONE);
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
