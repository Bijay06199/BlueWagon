package com.example.bluewagon.ecalculo.activities.startup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.LoginSuccessDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginSplashActivity extends AppCompatActivity {

    private LoginSplashActivity activity = LoginSplashActivity.this;
    private EditText eTUsername, eTPassword;
    private CardView cVLogin;
    private String userName, userPass;
    private Switch rememberPassword;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);
        init();
    }

    private void init() {
        eTUsername = findViewById(R.id.eTUsername);
        eTPassword = findViewById(R.id.eTPassword);
        cVLogin = findViewById(R.id.cVLogin);
        rememberPassword = findViewById(R.id.rememberSwitch);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

//        if (preferences.getBoolean(AppTexts.loggedin,true)){
//            startActivity(new Intent(this,DashboardActivity.class));
//        }

        requestQueue = Volley.newRequestQueue(activity);
        progressDialog = AppUtil.progressDialog(activity, AppTexts.pleaseWait);


        cVLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginListener();
            }
        });
    }

    private void loginListener() {
        if (validation()) {
            progressDialog.show();
            if (rememberPassword.isChecked()) {
                Toast.makeText(activity, "Password Saved", Toast.LENGTH_SHORT).show();
            }
            login();
        }
    }

    private boolean validation() {
        boolean validate = true;
        userName = eTUsername.getText().toString();
        userPass = eTPassword.getText().toString();
        String required = "required";
        if (userName.equals("")) {
            eTUsername.setError(required);
            validate = false;
        }
        if (userPass.equals("")) {
            eTPassword.setError(required);
            validate = false;
        }
        return validate;
    }

    private void login() {
        progressDialog.dismiss();
//        if (userName.equalsIgnoreCase("raise") && userPass.equalsIgnoreCase("r@!s3@123")){
//            startActivity(new Intent(activity, DashboardActivity.class));
//            finish();
//        }else{
//            String message = "User Name and Password do not match.\n Please enter correct password and try again.";
//            AppUtil.showErrorTitleBox(activity,message);
//        }

        progressDialog.show();
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("username", userName);
            postObject.put("password", userPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("url::: " + APIs.auth);
        APIRequest loginRequest = new APIRequest(
                Request.Method.POST,
                APIs.auth,
                postObject,
                loginResponse(),
                activityErrorListener()
        );
        requestQueue.add(loginRequest);
        AppUtil.customizeRequest(loginRequest);
    }

    private Response.Listener<JSONObject> loginResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();

            try {
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    LoginSuccessDTO loginSuccessDTO = new Gson().fromJson(response.toString(), LoginSuccessDTO.class);
                    preferences.edit().putString(AppTexts.orgId,loginSuccessDTO.getOrganisationId()).apply();
                    preferences.edit().putInt(AppTexts.userId,loginSuccessDTO.getUser().getId()).apply();
                    preferences.edit().putString(AppTexts.fyId,loginSuccessDTO.getFyId()).apply();
                    preferences.edit().putString(AppTexts.startDate,loginSuccessDTO.getStartDate()).apply();
                    preferences.edit().putString(AppTexts.endDate,loginSuccessDTO.getEndDate()).apply();
                    preferences.edit().putBoolean(AppTexts.loggedin,true).apply();

                    startActivity(new Intent(activity, DashboardActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
