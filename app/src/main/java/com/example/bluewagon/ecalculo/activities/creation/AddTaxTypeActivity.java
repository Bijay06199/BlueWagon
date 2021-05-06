package com.example.bluewagon.ecalculo.activities.creation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.AppUtil;


public class AddTaxTypeActivity extends AppCompatActivity {
    private AddTaxTypeActivity activity = AddTaxTypeActivity.this;
    private EditText eTTaxName, eTTaxRate;
    private TextView tVSaveTax, tVCancelTax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tax_type);
        init();
    }

    private void init() {
        eTTaxName = findViewById(R.id.eTTaxName);
        eTTaxRate = findViewById(R.id.eTTaxRate);
        tVSaveTax = findViewById(R.id.tVSaveTax);
        tVCancelTax = findViewById(R.id.tVCancelTax);


        tVSaveTax.setOnClickListener(v -> {
            checkValuesToSubmit();
        });

        tVCancelTax.setOnClickListener(v -> {
           onBackPressed();
        });
    }

    private void checkValuesToSubmit() {
        String taxRateName = eTTaxName.getText().toString();
        String taxRate = eTTaxRate.getText().toString();

        if (taxRateName.equals("") && taxRate.equals("")) {
            String message = "Please Fill all the required Fields.";
            AppUtil.showErrorTitleBox(activity, message);
        } else if (taxRateName.equals("")) {
            String message = "Please enter TAX Name to be created.";
            AppUtil.showErrorTitleBox(activity, message);
        } else if (taxRate.equals("")) {
            String message = "Please enter TAX Rate to be created.";
            AppUtil.showErrorTitleBox(activity, message);
        } else {
            createTaxType(taxRateName, taxRate);
        }
    }

    private void createTaxType(String taxName, String rate) {

    }

}
