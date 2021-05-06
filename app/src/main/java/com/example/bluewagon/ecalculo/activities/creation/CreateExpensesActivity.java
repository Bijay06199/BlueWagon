package com.example.bluewagon.ecalculo.activities.creation;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class CreateExpensesActivity extends AppCompatActivity implements View.OnClickListener {

    private CreateExpensesActivity activity = CreateExpensesActivity.this;

    private EditText eTExpensesName, eTOpeningBalance;
    private TextView tVAccountGroup, tVAccountCategory, tVSaveExpenses, tVCancelExpenses;
    private CardView cVCustomerBottomButtons;
    private Spinner balanceTypeSpinnerExpenses;

    private androidx.appcompat.app.AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_expenses);
        init();
        populateDebitCreditSpinner();
    }

    private void init() {
        eTExpensesName = findViewById(R.id.eTExpensesName);
        eTOpeningBalance = findViewById(R.id.eTOpeningBalance);

        tVAccountGroup = findViewById(R.id.tVAccountGroup);
        tVAccountCategory = findViewById(R.id.tVAccountCategory);
        tVSaveExpenses = findViewById(R.id.tVSaveExpenses);

        cVCustomerBottomButtons = findViewById(R.id.cVCustomerBottomButtons);

        balanceTypeSpinnerExpenses = findViewById(R.id.balanceTypeSpinnerExpenses);

        tVCancelExpenses = findViewById(R.id.tVCancelExpenses);

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

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(activity, findViewById(R.id.rLExpensesCreation));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);

        initListeners();
    }

    private void initListeners(){
        tVCancelExpenses.setOnClickListener(activity);
        tVSaveExpenses.setOnClickListener(activity);
    }

    private void populateDebitCreditSpinner() {
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Select Balance Type");
        spinnerArray.add("Dr");
        spinnerArray.add("Cr");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        balanceTypeSpinnerExpenses.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelExpenses:
                onBackPressed();
                break;

            case R.id.tVSaveExpenses:
                getValues();
                break;
        }
    }


    private void getValues(){
        String expenses = eTExpensesName.getText().toString();
        String accountGroup = tVAccountGroup.getText().toString();
        String accountCategory = tVAccountCategory.getText().toString();
        String openingBalance = eTOpeningBalance.getText().toString();
    }
}
