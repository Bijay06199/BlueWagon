package com.example.bluewagon.ecalculo.activities.creation;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.example.bluewagon.ecalculo.utilities.SoftKeyboardStateHelper;

import java.util.ArrayList;
import java.util.List;

public class CreateIncomeActivity extends AppCompatActivity implements View.OnClickListener{
    private CreateIncomeActivity activity = CreateIncomeActivity.this;

    private LinearLayout lLAddIncomeBack;
    private EditText eTIncomeName, eTOpeningBalance ;
    private TextView tVAccountGroup, tVAccountCategory, tVSaveIncome, tVCancelIncome;
    private CardView cVCustomerBottomButtons;
    private Spinner balanceTypeSpinnerIncome;

    private androidx.appcompat.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_income);
        init();
        populateDebitCreditSpinner();
    }

    private void init(){
        eTIncomeName = findViewById(R.id.eTIncomeName);
        eTOpeningBalance = findViewById(R.id.eTOpeningBalance);

        tVAccountGroup = findViewById(R.id.tVAccountGroup);
        tVAccountCategory = findViewById(R.id.tVAccountCategory);
        tVSaveIncome = findViewById(R.id.tVSaveIncome);
        tVCancelIncome = findViewById(R.id.tVCancelIncome);

        cVCustomerBottomButtons = findViewById(R.id.cVCustomerBottomButtons);

        balanceTypeSpinnerIncome = findViewById(R.id.balanceTypeSpinnerIncome);

        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);

        SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                cVCustomerBottomButtons.setVisibility(View.GONE);
            }

            @Override
            public void onSoftKeyboardClosed() {
                cVCustomerBottomButtons.setVisibility(View.VISIBLE);
            }
        };

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(activity, findViewById(R.id.rLIncomeCreation));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);

        initListeners();
    }

    private void initListeners(){
        tVCancelIncome.setOnClickListener(activity);
        tVSaveIncome.setOnClickListener(activity);
    }

    private void populateDebitCreditSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select Balance Type");
        spinnerArray.add("Dr");
        spinnerArray.add("Cr");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        balanceTypeSpinnerIncome.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelIncome:
                onBackPressed();
                break;

            case R.id.tVSaveIncome :
                getValues();
                break;
        }
    }

    private void getValues(){
        String incomeName = eTIncomeName.getText().toString();
        String openingBalance = eTOpeningBalance.getText().toString();
        String accountGroup = tVAccountGroup.getText().toString();
        String accountCategory = tVAccountCategory.getText().toString();
    }
}
