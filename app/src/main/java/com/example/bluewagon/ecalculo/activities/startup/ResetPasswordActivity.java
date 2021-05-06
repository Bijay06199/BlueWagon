package com.example.bluewagon.ecalculo.activities.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button buttonSubmit;
    private EditText eTCurrentPassword, eTNewPassword, eTConfirmPassword;
    private ResetPasswordActivity activity = ResetPasswordActivity.this;
    private AlertDialog progressDialog;
    private String currentPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
        initToolbar();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbarResetPassword);
        eTCurrentPassword = findViewById(R.id.eTCurrentPassword);
        eTNewPassword = findViewById(R.id.eTNewPassword);
        eTConfirmPassword = findViewById(R.id.eTConfirmPassword);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        String message = "Resetting Password, Please wait...";
        progressDialog = AppUtil.progressDialog(activity, message);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    if (currentPassword.equals(newPassword)) {
                        String message = "Current password and New Password cannot be same, Please try using New Password other than old password.";
                        AppUtil.infoDialog(activity, "Error", message);
                    } else {
                        if (newPassword.equals(confirmPassword)) {
                            progressDialog.show();
//                            changePassword();
                        } else {
                            String message = "New Password and Confirm Password do not match. Please Re-enter the password";
                            AppUtil.infoDialog(activity, "Error", message);
                        }
                    }
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean validation(){
        boolean validate = true;
        currentPassword = eTCurrentPassword.getText().toString();
        newPassword = eTNewPassword.getText().toString();
        confirmPassword = eTConfirmPassword.getText().toString();
        String required = "required!";
        if (currentPassword.equals("")) {
            eTCurrentPassword.setError(required);
            validate = false;
        }
        if (newPassword.equals("")) {
            eTNewPassword.setError(required);
            validate = false;
        }
        if (confirmPassword.equals("")) {
            eTConfirmPassword.setError(required);
            validate = false;
        }
        return validate;
    }

    private void changePassword(){
        APIRequest resetPasswordRequest = new APIRequest(
                Request.Method.POST,
                APIs.resetUserPassword,
                passwordResetResponse(),
                activityErrorResponse()
        );
    }

    private Response.Listener<JSONObject> passwordResetResponse(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        };
    }

    private Response.ErrorListener activityErrorResponse() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                AppUtil.infoDialog(activity, AppTexts.error, AppUtil.errorListener(error));
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(activity, DashboardActivity.class));
        finish();
    }
}
