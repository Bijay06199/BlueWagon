package com.example.bluewagon.ecalculo.activities.transactions.entry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.activities.creation.AddCustomerActivity;
import com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry.AddItemServiceAdapter;
import com.example.bluewagon.ecalculo.dtos.AddItemServiceDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.example.bluewagon.ecalculo.utilities.SoftKeyboardStateHelper;
import com.google.gson.Gson;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class SalesOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private SalesOrderActivity activity = SalesOrderActivity.this;
    private CardView cVNarration, cVItemServiceDetails, cVBottomButtons;
    private LayoutInflater inflater;
    private TextView tVNarration, tVParty, tVDate, tVCancelSalesInvoice, tVSaveSalesInvoice;

    private Calendar calendar, calenderTemp2, calenderTemp1;
    private DateFormat df;

    private ImageView iVShowDiscountDetails;
    private SharedPreferences preferences;
    private RequestQueue requestQueue;
    private String dateYear, dateMonth, dateDay, dueYear, dueMonth, dueDay;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private AlertDialog dialog;

    private List<AddItemServiceDTO> addItemServiceDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_creation);
        init();
        getSalesNumber();
        setDate();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        tVNarration = findViewById(R.id.tVNarration);
        tVParty = findViewById(R.id.tVParty);
        tVDate = findViewById(R.id.tVDate);
        tVCancelSalesInvoice = findViewById(R.id.tVCancelSalesInvoice);
        tVSaveSalesInvoice = findViewById(R.id.tVSaveSalesInvoice);

        cVNarration = findViewById(R.id.cVNarration);
        cVItemServiceDetails = findViewById(R.id.cVItemServiceDetails);
        cVBottomButtons = findViewById(R.id.cVBottomButtons);

        iVShowDiscountDetails = findViewById(R.id.iVShowDiscountDetails);


        inflater = LayoutInflater.from(activity);
        df = new SimpleDateFormat("yyyy-MM-dd");
        addItemServiceDTOS = new ArrayList<>();

        calendar = Calendar.getInstance();
        calenderTemp2 = Calendar.getInstance();
        calenderTemp1 = Calendar.getInstance();

        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        requestQueue = Volley.newRequestQueue(activity);


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

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(activity, findViewById(R.id.rLSales));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);


        initListeners();
        setUIView();
    }

    private void getSalesNumber(){
        progressDialog.show();
        String url = APIs.salesOrderLoadPage + preferences.getString(AppTexts.orgId, "") + "/" + 0 + "/" + preferences.getString(AppTexts.fyId, "");
        APIRequest salesOrderNumberRequest = new APIRequest(
                Request.Method.GET,
                url,
                salesOrderNumberResponse(),
                activityErrorListener()
        );
        requestQueue.add(salesOrderNumberRequest);
        AppUtil.customizeRequest(salesOrderNumberRequest);
    }


    private Response.Listener<JSONObject> salesOrderNumberResponse() {
        return response -> {
            System.out.println("response:: " + response);
            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {


                    
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

    private void initListeners() {
        cVNarration.setOnClickListener(activity);
        tVCancelSalesInvoice.setOnClickListener(activity);
        tVSaveSalesInvoice.setOnClickListener(activity);
        tVDate.setOnClickListener(activity);
        tVParty.setOnClickListener(activity);
    }

    private void setUIView() {
        //do the stuff here for views visible and gone according to the selection
//        if (tVParty.getText().toString().equalsIgnoreCase("Select")){
//           rLSupplier.setVisibility(View.VISIBLE);
//        }
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tVCancelSalesInvoice:
                onBackPressed();
                break;

            case R.id.cVNarration:
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
                String previousNarration = tVNarration.getText().toString();
                if (previousNarration.equalsIgnoreCase("Narration")) {
                    eTNarrationText.setText("");
                } else {
                    eTNarrationText.setText(previousNarration);
                }

                tVCancel.setOnClickListener(v1 -> dialog.dismiss());

                tVOk.setOnClickListener(v12 -> {
                    String narration = eTNarrationText.getText().toString();
                    tVNarration.setText(narration);
                    dialog.dismiss();
                });

                builder.setCancelable(false);
                dialog = builder.create();
                Window window = dialog.getWindow();
                Objects.requireNonNull(window).setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;


            case R.id.tVParty:
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

                rLAddParty.setOnClickListener(v13 -> {
                    startActivity(new Intent(activity, AddCustomerActivity.class));
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
                Objects.requireNonNull(addPartyWindow).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addPartyWindow.setGravity(Gravity.TOP);
                dialog.show();
                break;

            case R.id.tVDate:
                tVDate.setSelected(true);
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
            String yearMonth , yearDay ;
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
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppTexts.addSalesInvoiceServiceIntent) {
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
                cVItemServiceDetails.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(activity, error);
        };
    }
}
