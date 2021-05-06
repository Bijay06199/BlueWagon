package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.activities.creation.AddItemServiceActivity;
import com.example.bluewagon.ecalculo.activities.creation.AddSupplierActivity;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddItemServiceAdapter;
import com.example.bluewagon.ecalculo.dtos.AddItemServiceDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.example.bluewagon.ecalculo.utilities.SoftKeyboardStateHelper;
import com.google.gson.Gson;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PurchaseReturnActivity extends AppCompatActivity implements View.OnClickListener {
    private PurchaseReturnActivity activity = PurchaseReturnActivity.this;
    private Calendar calendar1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    private TextView tVCancelPurchaseReturn, tVPurchaseReturnNo, tVAddItemNServicePurReturn, tPurchaseReturnNarration, tVPurchaseReturnDate, tVPurchaseReturnParty;
    private CardView cVBottomButtonPurchase, cVAddStockPurchaseReturn, cVReturnTaxNDiscount, cVPurchaseReturnNarration, cVItemServicePurchaseDetail, cVTaxNDiscountPurReturnDetails;
    private ImageView iVShowItemServicePurReturn, iVShowDiscountPurchaseDetails;
    private RecyclerView rVPurchaseReturnItemServiceDetails;
    private List<AddItemServiceDTO> addItemServiceDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_return);
        init();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVCancelPurchaseReturn = findViewById(R.id.tVCancelPurchaseReturn);
        tVPurchaseReturnNo = findViewById(R.id.tVPurchaseReturnNo);
        tVAddItemNServicePurReturn = findViewById(R.id.tVAddItemNServicePurReturn);
        tPurchaseReturnNarration = findViewById(R.id.tPurchaseReturnNarration);
        tVPurchaseReturnDate = findViewById(R.id.tVPurchaseReturnDate);
        tVPurchaseReturnParty = findViewById(R.id.tVPurchaseReturnParty);


        cVBottomButtonPurchase = findViewById(R.id.cVBottomButtonPurchase);
        cVAddStockPurchaseReturn = findViewById(R.id.cVAddStockPurchaseReturn);
        cVReturnTaxNDiscount = findViewById(R.id.cVReturnTaxNDiscount);
        cVPurchaseReturnNarration = findViewById(R.id.cVPurchaseReturnNarration);
        cVItemServicePurchaseDetail = findViewById(R.id.cVItemServicePurchaseDetail);
        cVTaxNDiscountPurReturnDetails = findViewById(R.id.cVTaxNDiscountPurReturnDetails);


        iVShowItemServicePurReturn = findViewById(R.id.iVShowItemServicePurReturn);
        iVShowDiscountPurchaseDetails = findViewById(R.id.iVShowDiscountPurchaseDetails);

        rVPurchaseReturnItemServiceDetails = findViewById(R.id.rVPurchaseReturnItemServiceDetails);

        inflater = LayoutInflater.from(activity);
        df = new SimpleDateFormat("yyyy-MM-dd");
        addItemServiceDTOS = new ArrayList<>();

        calendar1 = Calendar.getInstance();

        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);


        SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                cVBottomButtonPurchase.setVisibility(View.GONE);
            }

            @Override
            public void onSoftKeyboardClosed() {
                cVBottomButtonPurchase.setVisibility(View.VISIBLE);
            }
        };

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(activity, findViewById(R.id.rLPurchaseReturn));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);

        initListeners();

    }

    private void initListeners() {
        tVCancelPurchaseReturn.setOnClickListener(activity);
        cVAddStockPurchaseReturn.setOnClickListener(activity);
        cVReturnTaxNDiscount.setOnClickListener(activity);
        cVPurchaseReturnNarration.setOnClickListener(activity);
        tVPurchaseReturnDate.setOnClickListener(activity);
        tVAddItemNServicePurReturn.setOnClickListener(activity);
        tVPurchaseReturnParty.setOnClickListener(activity);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calendar1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calendar1.getTime());
        tVPurchaseReturnDate.setText(currentDateNepali);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelPurchaseReturn:
               onBackPressed();
                break;

            case R.id.cVAddStockPurchaseReturn:
                iVShowItemServicePurReturn.animate().rotation(iVShowItemServicePurReturn.getRotation() + 180).start();
                if (cVItemServicePurchaseDetail.getVisibility() == View.VISIBLE) {
                    cVItemServicePurchaseDetail.setVisibility(View.GONE);
                } else {
                    cVItemServicePurchaseDetail.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.cVReturnTaxNDiscount:
                iVShowDiscountPurchaseDetails.animate().rotation(iVShowDiscountPurchaseDetails.getRotation() + 180).start();
                if (cVTaxNDiscountPurReturnDetails.getVisibility() == View.VISIBLE) {
                    cVTaxNDiscountPurReturnDetails.setVisibility(View.GONE);
                } else {
                    cVTaxNDiscountPurReturnDetails.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.tVAddItemNServicePurReturn:
                Intent intent = new Intent(activity, AddItemServiceActivity.class);
                startActivityForResult(intent, AppTexts.addPurchaseReturnServiceIntent);
                break;

            case R.id.tVPurchaseReturnParty:
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View addPartyDialog = inflater.inflate(R.layout.layout_add_party, null);

                AlertDialog.Builder addPartyBuilder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                addPartyBuilder.setView(addPartyDialog);

                EditText eTSearchItem = addPartyDialog.findViewById(R.id.eTSearchItem);
                RecyclerView rVParty = addPartyDialog.findViewById(R.id.rVParty);
                RelativeLayout rLAddParty = addPartyDialog.findViewById(R.id.rLAddParty);

//                if (eTSearchItem.requestFocus()) {
//                    new Handler().postDelayed(() -> inputMethodManager.showSoftInput(eTSearchItem, InputMethodManager.SHOW_IMPLICIT), 150);
//                }
//
                rLAddParty.setOnClickListener(v13 -> {
                    startActivity(new Intent(activity, AddSupplierActivity.class));
                    dialog.dismiss();
                });
//
//                eTSearchItem.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                    }
//                });

                addPartyBuilder.setCancelable(true);
                dialog = addPartyBuilder.create();
                Window addPartyWindow = dialog.getWindow();
                addPartyWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addPartyWindow.setGravity(Gravity.TOP);
                dialog.show();
                break;

            case R.id.cVPurchaseReturnNarration:
                View narrationDialog = inflater.inflate(R.layout.layout_narration_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MaterialDialogSheet);
                builder.setView(narrationDialog);

                TextView tVCancel = narrationDialog.findViewById(R.id.tVCancel);
                TextView tVOk = narrationDialog.findViewById(R.id.tVOk);
                TextView tVHeading = narrationDialog.findViewById(R.id.tVHeading);
                final EditText eTNarrationText = narrationDialog.findViewById(R.id.eTNarrationText);

                tVHeading.setText(R.string.narration);

                //making the editText view of certain size and making cursor on desired pos
                eTNarrationText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                eTNarrationText.setSingleLine(false);
                eTNarrationText.setLines(5);
                eTNarrationText.setMaxLines(5);
                eTNarrationText.setGravity(Gravity.START | Gravity.TOP);

                //setting the previous narration on editText, if initial ignore else show same
                String previousNarration = tPurchaseReturnNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tPurchaseReturnNarration.setText(narration);
                    dialog.dismiss();
                });

                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;

            case R.id.tVPurchaseReturnDate:
                nepaliCalender();
                break;
        }
    }

    private void nepaliCalender() {
        com.hornet.dateconverter.DatePicker.DatePickerDialog dpd =
                com.hornet.dateconverter.DatePicker.DatePickerDialog.newInstance(nepaliDateListener);
        DateConverter dc = new DateConverter();
        Calendar cal = Calendar.getInstance();


        com.hornet.dateconverter.DatePicker.DatePickerDialog dialog =
                com.hornet.dateconverter.DatePicker.DatePickerDialog.newInstance(nepaliDateListener);
        dialog.setVersion(DatePickerDialog.Version.VERSION_1);
//            dialog.setMaxDate();
//            dialog.setMinDate(sModel);
        dialog.show(getSupportFragmentManager(), "");
    }

    private com.hornet.dateconverter.DatePicker.DatePickerDialog.OnDateSetListener nepaliDateListener = new com.hornet.dateconverter.DatePicker.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(com.hornet.dateconverter.DatePicker.DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear + 1;

            NumberFormat f = new DecimalFormat("00");
            String yearMonth = "", yearDay = "";
            int month = monthOfYear;
            if (month < 10) {
                yearMonth = f.format(month);
            } else {
                yearMonth = String.valueOf(monthOfYear);
            }

            if (dayOfMonth < 10) {
                yearDay = f.format(dayOfMonth);
            } else {
                yearDay = String.valueOf(dayOfMonth);
            }

            String selectedDate = year + "-" + yearMonth + "-" + yearDay;
            tVPurchaseReturnDate.setText(selectedDate);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppTexts.addPurchaseReturnServiceIntent) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                System.out.println("data:::: " + data.getStringExtra("product"));
                JSONObject jObj = new JSONObject();
                try {
                    jObj.put("productServiceName", data.getStringExtra("product"));
                    jObj.put("quantity", data.getStringExtra("qty"));
                    jObj.put("unit", data.getStringExtra("unit"));
                    jObj.put("rate", data.getStringExtra("rate"));
                    jObj.put("subTotal", data.getStringExtra("total"));
                    AddItemServiceDTO addItemServiceDTO = new Gson().fromJson(jObj.toString(), AddItemServiceDTO.class);
                    addItemServiceDTOS.add(addItemServiceDTO);
                } catch (Exception e) {
                    System.out.println("Error:" + e);
                }

                System.out.println("list:::: " + addItemServiceDTOS);

                LinearLayoutManager manager = new LinearLayoutManager(activity);
                AddItemServiceAdapter addAccountsAdapter = new AddItemServiceAdapter(activity,addItemServiceDTOS);
                rVPurchaseReturnItemServiceDetails.setLayoutManager(manager);
                rVPurchaseReturnItemServiceDetails.setAdapter(addAccountsAdapter);
                cVItemServicePurchaseDetail.setVisibility(View.VISIBLE);
            }
        }
    }
}
