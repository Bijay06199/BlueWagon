package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.activities.creation.AddItemServiceActivity;
import com.example.bluewagon.ecalculo.adapters.creationAdapter.VendorAdapter;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddItemServiceAdapter;
import com.example.bluewagon.ecalculo.dtos.AccountMasterDTO;
import com.example.bluewagon.ecalculo.dtos.AddItemServiceDTO;
import com.example.bluewagon.ecalculo.listeners.VendorSelectedListener;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.example.bluewagon.ecalculo.utilities.SoftKeyboardStateHelper;
import com.google.gson.Gson;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PurchaseInvoiceActivity extends AppCompatActivity implements View.OnClickListener, VendorSelectedListener {
    private PurchaseInvoiceActivity activity = PurchaseInvoiceActivity.this;
    private AlertDialog dialog, vendorDialog;
    private LayoutInflater inflater;
    private Calendar calenderTemp2, calenderTemp1;
    private DateFormat df;
    private androidx.appcompat.app.AlertDialog progressDialog;

    private CardView cVPurchaseNarration, cVPurchaseAddStockDetails, cVPurchaseDiscountNTax, cVPurchaseTaxNDiscountDetails, cVPurchaseItemServiceDetails, cVPurchaseBottomButtons;
    private TextView tVPurchaseNarration, tVPurchaseCancel, tVPurchaseParty, tVDate, tVPurchaseDate, tVPan, tVCurrentBalance;
    private EditText eTReferenceNo;
    private RecyclerView rVPurchaseItemServiceDetails;
    private RelativeLayout rLPurchaseParty, rLPurchaseSupplier;

    private ImageView iVShowPurchaseItemNService, iVShowPurchaseDiscountDetails;

    private String dateYear, dateMonth, dateDay, dueYear, dueMonth, dueDay;
    private List<AddItemServiceDTO> addItemServiceDTOS;
    private SharedPreferences preferences;
    private RequestQueue requestQueue;
    private List<AccountMasterDTO> vendorDTOList;
    private VendorAdapter vendorAdapter;
    private int vendorId;
    private LinearLayout lLVendorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_invoice);
        init();
        getVendorList();
//        setDate();
//        setCreditDays();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVPurchaseNarration = findViewById(R.id.tVPurchaseNarration);
        tVPurchaseCancel = findViewById(R.id.tVPurchaseCancel);
        tVDate = findViewById(R.id.tVDate);
        tVPurchaseDate = findViewById(R.id.tVPurchaseDate);
        tVPurchaseParty = findViewById(R.id.tVPurchaseParty);
        tVPan = findViewById(R.id.tVPan);
        tVCurrentBalance = findViewById(R.id.tVCurrentBalance);

        lLVendorDetails = findViewById(R.id.lLVendorDetails);

        eTReferenceNo = findViewById(R.id.eTReferenceNo);

        cVPurchaseNarration = findViewById(R.id.cVPurchaseNarration);
        cVPurchaseAddStockDetails = findViewById(R.id.cVPurchaseAddStockDetails);
        cVPurchaseDiscountNTax = findViewById(R.id.cVPurchaseDiscountNTax);
        cVPurchaseTaxNDiscountDetails = findViewById(R.id.cVPurchaseTaxNDiscountDetails);
        cVPurchaseItemServiceDetails = findViewById(R.id.cVPurchaseItemServiceDetails);
        cVPurchaseBottomButtons = findViewById(R.id.cVPurchaseBottomButtons);

        rLPurchaseParty = findViewById(R.id.rLPurchaseParty);

        rVPurchaseItemServiceDetails = findViewById(R.id.rVPurchaseItemServiceDetails);

        iVShowPurchaseItemNService = findViewById(R.id.iVShowPurchaseItemNService);
        iVShowPurchaseDiscountDetails = findViewById(R.id.iVShowPurchaseDiscountDetails);


        inflater = LayoutInflater.from(activity);
        df = new SimpleDateFormat("yyyy-MM-dd");

        addItemServiceDTOS = new ArrayList<>();


        calenderTemp2 = Calendar.getInstance();
        calenderTemp1 = Calendar.getInstance();

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                cVPurchaseBottomButtons.setVisibility(View.GONE);
            }

            @Override
            public void onSoftKeyboardClosed() {
                cVPurchaseBottomButtons.setVisibility(View.VISIBLE);
            }
        };

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(activity, findViewById(R.id.rLPurchase));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);


        initListeners();
        setUIView();
    }


    private void initListeners() {
        cVPurchaseNarration.setOnClickListener(activity);
        cVPurchaseAddStockDetails.setOnClickListener(activity);
        cVPurchaseDiscountNTax.setOnClickListener(activity);

        tVPurchaseCancel.setOnClickListener(activity);
        tVDate.setOnClickListener(activity);
        tVPurchaseDate.setOnClickListener(activity);
        tVPurchaseParty.setOnClickListener(activity);
    }

    private void setUIView() {
        //do the stuff here for views visible and gone according to the selection
//        if (tVParty.getText().toString().equalsIgnoreCase("Select")){
//           rLSupplier.setVisibility(View.VISIBLE);
//        }
    }


    private void getVendorList() {
        progressDialog.show();
        setDate();
        String url = APIs.allAccountList + preferences.getString(AppTexts.orgId, "") + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest vendorRequest = new APIRequest(
                Request.Method.GET,
                url,
                vendorResponseList(),
                activityErrorListener()
        );
        requestQueue.add(vendorRequest);
        AppUtil.customizeRequest(vendorRequest);
    }

    private Response.Listener<JSONObject> vendorResponseList() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                vendorDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountNatureList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountNatureList.length(); i++) {
                        String object = accountNatureList.getJSONObject(i).toString();
                        vendorDTOList.add(new Gson().fromJson(object, AccountMasterDTO.class));
                    }
                } else {
                    AppUtil.somethingWrongDialog(activity);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        Model model1 = new DateConverter().getNepaliDate(currentYear, currentMonth, currentDay);
        calenderTemp1.set(model1.getYear(), model1.getMonth(), model1.getDay());
        String currentDateNepali = df.format(calenderTemp1.getTime());
        tVDate.setText(currentDateNepali);
        tVPurchaseDate.setText(currentDateNepali);
    }

//    private void setCreditDays(){
//        String salesDate = tVDate.getText().toString();
//        String dueDate = tVPurchaseDate.getText().toString();
//
//        dateYear = salesDate.substring(0, 4);
//        dateMonth = salesDate.substring(5, 7);
//        dateDay = salesDate.substring(8, 10);
//
//        int year = Integer.parseInt(dateYear);
//        int month = Integer.parseInt(dateMonth);
//        int day = Integer.parseInt(dateDay);
//
//
//        Model englishDateModel = new DateConverter().getEnglishDate(year, month, day);
//        calenderTemp1.set(englishDateModel.getYear(), englishDateModel.getMonth(), englishDateModel.getDay());
//
//
//        dueYear = dueDate.substring(0, 4);
//        dueMonth = dueDate.substring(5, 7);
//        dueDay = dueDate.substring(8, 10);
//
//        int yearDue = Integer.parseInt(dueYear);
//        int monthDue = Integer.parseInt(dueMonth);
//        int dayDue = Integer.parseInt(dueDay);
//
//
//        Model englishDueDateModel = new DateConverter().getEnglishDate(yearDue, monthDue, dayDue);
//        calenderTemp2.set(englishDueDateModel.getYear(), englishDueDateModel.getMonth(), englishDueDateModel.getDay());
//
//        long dateDue = calenderTemp2.getTimeInMillis();
//        long dateSales = calenderTemp1.getTimeInMillis();
//        if (dateSales>dateDue){
//
//            Toast.makeText(activity, "Not valid", Toast.LENGTH_SHORT).show();
//            tVPurchaseCreditDays.setText("0");
//        }else{
//            long difference = dateDue - dateSales;
//            long days = difference/(24*60*60*1000);
//
//            tVPurchaseCreditDays.setText(days + "");
//        }
//    }

    private void populateVendorList(List<AccountMasterDTO> vendorDTOList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        vendorSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVSubGroup = view.findViewById(R.id.rVAccountGroup);
        rVSubGroup.setLayoutManager(new LinearLayoutManager(activity));
        vendorAdapter = new VendorAdapter(activity, vendorDTOList, activity);
        rVSubGroup.setAdapter(vendorAdapter);

        vendorDialog = (new AlertDialog.Builder(activity).setView(view)).create();
        vendorDialog.show();
    }


    private void vendorSearchListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vendorAdapter != null) {
                    vendorAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVPurchaseCancel:
                onBackPressed();
                break;

            case R.id.cVPurchaseNarration:
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
                String previousNarration = tVPurchaseNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVPurchaseNarration.setText(narration);
                    dialog.dismiss();
                });

                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;

            case R.id.tVPurchaseAddItemService:
                Intent intent = new Intent(activity, AddItemServiceActivity.class);
                startActivityForResult(intent, AppTexts.addPurchaseInvoiceServiceIntent);
                break;

            case R.id.tVPurchaseParty:
                populateVendorList(vendorDTOList);
                break;

            case R.id.cVPurchaseAddStockDetails:
                iVShowPurchaseItemNService.animate().rotation(iVShowPurchaseItemNService.getRotation() + 180).start();
                if (cVPurchaseItemServiceDetails.getVisibility() == View.VISIBLE) {
                    cVPurchaseItemServiceDetails.setVisibility(View.GONE);
                } else {
                    cVPurchaseItemServiceDetails.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.cVPurchaseDiscountNTax:
                iVShowPurchaseDiscountDetails.animate().rotation(iVShowPurchaseDiscountDetails.getRotation() + 180).start();
                if (cVPurchaseTaxNDiscountDetails.getVisibility() == View.VISIBLE) {
                    cVPurchaseTaxNDiscountDetails.setVisibility(View.GONE);
                } else {
                    cVPurchaseTaxNDiscountDetails.setVisibility(View.VISIBLE);
                }
                break;

//            case R.id.cVSundry:
//                Intent intent1 = new Intent(activity,AddSundryActivity.class);
//                startActivity(intent1);
//                break;

            case R.id.tVDate:
                tVPurchaseDate.setSelected(false);
                tVDate.setSelected(true);
                nepaliCalender();
                break;

            case R.id.tVPurchaseDate:
                tVPurchaseDate.setSelected(true);
                tVDate.setSelected(false);
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


            if (tVDate.isSelected()) {
                tVDate.setText(selectedDate);
//                setCreditDays();
            }

            if (tVPurchaseDate.isSelected()) {
                tVPurchaseDate.setText(selectedDate);
//                setCreditDays();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppTexts.addPurchaseInvoiceServiceIntent) {
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
                AddItemServiceAdapter addAccountsAdapter = new AddItemServiceAdapter(activity, addItemServiceDTOS);
                rVPurchaseItemServiceDetails.setLayoutManager(manager);
                rVPurchaseItemServiceDetails.setAdapter(addAccountsAdapter);
                cVPurchaseItemServiceDetails.setVisibility(View.VISIBLE);
            }
        }
    }

    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onVendorSelected(int id, String name, String panNo, double closingBalance) {
        String prefix;
        tVPurchaseParty.setText(name);
        vendorId = id;
        if (closingBalance < 0) {
            prefix = " Cr.";
//            tVCurrentBalance.setTextColor(R.color.creditColor);
        } else if (closingBalance > 0) {
            prefix = " Dr.";
//            tVCurrentBalance.setTextColor(R.color.debitColor);
        } else {
            prefix = "";
        }
        String balance = AppUtil.formattedAmounts(closingBalance);

        tVPan.setText(panNo);
        tVCurrentBalance.setText(balance + prefix);
        lLVendorDetails.setVisibility(View.VISIBLE);
        vendorDialog.dismiss();
    }
}
