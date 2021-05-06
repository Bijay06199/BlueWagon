package com.example.bluewagon.ecalculo.activities.others;

import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.SoftKeyboardStateHelper;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BackupClass extends AppCompatActivity implements View.OnClickListener {
    private BackupClass activity = BackupClass.this;
    private LinearLayout lLItemDetails, lLImagePreview;
    private CardView cVStockDetails, cVCriticalItem, cVCriticalItemsDetail, cVPricingDetail, cVBottomButtons;
    private ImageView  iVCriticalItem, iVItemImage, iVImagePreview;
    private TextView  tVItem, tVService, tVCancel, tVAddImage;
    private TextInputLayout tLPurchaseRate;
    private File path;
    private Bitmap selectedImage, resized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        init();
    }

    private void init() {
        tVCancel = findViewById(R.id.tVCancel);
        lLItemDetails = findViewById(R.id.lLItemDetails);
        lLImagePreview = findViewById(R.id.lLImagePreview);

        cVStockDetails = findViewById(R.id.cVStockDetails);
        cVCriticalItemsDetail = findViewById(R.id.cVCriticalItemsDetail);
        cVPricingDetail = findViewById(R.id.cVPricingDetail);
        cVBottomButtons = findViewById(R.id.cVBottomButtons);

        iVItemImage = findViewById(R.id.iVItemImage);
        iVImagePreview = findViewById(R.id.iVImagePreview);

        tVItem = findViewById(R.id.tVItem);
        tVService = findViewById(R.id.tVService);
        tVCancel = findViewById(R.id.tVCancel);
        tVAddImage = findViewById(R.id.tVAddImage);

        tLPurchaseRate = findViewById(R.id.tLPurchaseRate);


        SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                cVBottomButtons.setVisibility(View.GONE);
            }

            @Override
            public void onSoftKeyboardClosed() {
                cVBottomButtons.setVisibility(View.VISIBLE);
            }
        };

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(activity, findViewById(R.id.rLMain));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);


        initListeners();
    }

    private void initListeners() {
        tVCancel.setOnClickListener(activity);
        cVCriticalItem.setOnClickListener(activity);
        tVItem.setOnClickListener(activity);
        tVService.setOnClickListener(activity);
        tVAddImage.setOnClickListener(activity);
        iVItemImage.setOnClickListener(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancel:
                onBackPressed();
                break;
                
            case R.id.tVService:
                tVItem.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVService.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVItem.setTextColor(ContextCompat.getColor(activity, R.color.white));
                tVService.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                lLItemDetails.setVisibility(View.GONE);
                tLPurchaseRate.setVisibility(View.GONE);
                break;

            case R.id.tVItem:
                tVItem.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                tVService.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                tVItem.setTextColor(ContextCompat.getColor(activity, R.color.lightBlack));
                tVService.setTextColor(ContextCompat.getColor(activity, R.color.white));
                lLItemDetails.setVisibility(View.VISIBLE);
                tLPurchaseRate.setVisibility(View.VISIBLE);
                break;

            case R.id.tVAddImage:
                CropImage.activity().start(activity);
                break;

            case R.id.iVItemImage:
                try {
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File directory = cw.getDir(AppTexts.dirNameforImages, MODE_PRIVATE);
                    path = new File(directory, AppTexts.name + "." + "test" + AppTexts.extension);
                    System.out.println("path got::" + path);
                    System.out.println("pathExists::: " + path.exists());
                    if (path != null && path.exists()) {
                        FileInputStream fi = new FileInputStream(path);
                        selectedImage = BitmapFactory.decodeStream(fi);
                        lLImagePreview.setVisibility(View.VISIBLE);
                        iVImagePreview.setImageBitmap(selectedImage);

                    } else {
                        Toast.makeText(activity, "Couldnot load Image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                break;
        }
    }


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





    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
