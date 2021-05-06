package com.example.bluewagon.ecalculo.activities.creation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.AppUtil;


public class AddItemServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private AddItemServiceActivity activity = AddItemServiceActivity.this;
    private ImageView iVAdd;
    private TextView tVCancelItemService, tVSaveItemService;
    private EditText eTSubTotal, eTPrice, eTUnit, eTQuantity, eTItemName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_service);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        iVAdd = findViewById(R.id.iVAdd);
        tVCancelItemService = findViewById(R.id.tVCancelItemService);
        tVSaveItemService = findViewById(R.id.tVSaveItemService);
        eTSubTotal = findViewById(R.id.eTSubTotal);
        eTPrice = findViewById(R.id.eTPrice);
        eTQuantity = findViewById(R.id.eTQuantity);
        eTUnit = findViewById(R.id.eTUnit);
        eTItemName = findViewById(R.id.eTItemName);


        eTPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    double qty = Double.parseDouble(eTQuantity.getText().toString());
                    double total = qty * Integer.parseInt(s.toString());
                    eTSubTotal.setText(String.valueOf(total));
                } else {
                    eTSubTotal.setText("0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        eTSubTotal.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 0) {
//                    double subtotal = Integer.parseInt(s.toString());
//                    double price = Double.parseDouble(eTPrice.getText().toString());
//                    double qty = subtotal / price;
//                    eTQuantity.setText(String.valueOf(qty));
//                } else {
//                    eTQuantity.setText("0.00");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        initListeners();
    }


    private void initListeners() {
        iVAdd.setOnClickListener(activity);
        tVCancelItemService.setOnClickListener(activity);
        tVSaveItemService.setOnClickListener(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iVAdd:
                Intent intent = new Intent(activity, AddItemsActivity.class);
                startActivity(intent);
                break;

            case R.id.tVCancelItemService:
                onBackPressed();
                break;

            case R.id.tVSaveItemService:
                validateData();
                break;
        }
    }

    private void validateData() {
        String itemServiceName = eTItemName.getText().toString();
        String quantity = eTQuantity.getText().toString();
        String unit = eTUnit.getText().toString();
        String rate = eTPrice.getText().toString();
        String subTotal = eTSubTotal.getText().toString();

        if (itemServiceName.equalsIgnoreCase("") || quantity.equalsIgnoreCase("") || unit.equalsIgnoreCase("") ||
                rate.equalsIgnoreCase("") || subTotal.equalsIgnoreCase("")) {
            AppUtil.showErrorTitleBox(activity,"Please input all the required datas..");
        }else{
            Intent returnIntent = new Intent();
            returnIntent.putExtra("product", itemServiceName);
            returnIntent.putExtra("qty", quantity);
            returnIntent.putExtra("unit", unit);
            returnIntent.putExtra("rate", rate);
            returnIntent.putExtra("total", subTotal);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
