package com.example.bluewagon.ecalculo.adapters.creationAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.AccountGroupDTO;
import com.example.bluewagon.ecalculo.dtos.AccountSubGroupDTO;
import com.example.bluewagon.ecalculo.listeners.AccountGroupSelectedListener;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AccountSubGroupAdapter extends RecyclerView.Adapter<AccountSubGroupAdapter.ViewHolder> implements AccountGroupSelectedListener {


    private Context context;
    private List<AccountSubGroupDTO> accountSubGroupDTOSList;
    private LayoutInflater inflater;
    private AlertDialog dialog, accountGroup;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private List<AccountGroupDTO> accountGroupDTOList;
    private SharedPreferences preferences;
    private accGroupAdapter accountGroupAdapter;
    private AccountGroupSelectedListener listener;
    private TextView tVAccountMainGroup;
    private int accountHeadId;


    public AccountSubGroupAdapter(Context context, List<AccountSubGroupDTO> accountSubGroupDTOSList) {
        this.context = context;
        this.accountSubGroupDTOSList = accountSubGroupDTOSList;
        inflater = LayoutInflater.from(context);
        listener = this;

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_account_sub_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountSubGroupDTO dto = accountSubGroupDTOSList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position+1));
        holder.tVAccountSubHeadName.setText(dto.getName());
        holder.tVAccountHeadName.setText(dto.getHeadName());
        holder.tVAccountNature.setText(dto.getNatureName());

        holder.iVDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Delete");
                alertDialogBuilder.setMessage("Are you sure, You want to delete the Account Sub-Group?");
                alertDialogBuilder.setPositiveButton("yes",
                        (dialog, arg1) -> {
                            //remarks not ready need to add hai ............
                            callDeleteApi(dto.getId());
                            dialog.dismiss();
                        });

                alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.iVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccountHead();
                View accountHeadView = inflater.inflate(R.layout.layout_account_sub_group, null);
                AlertDialog.Builder accountHeadBuilder = new AlertDialog.Builder(context, R.style.MaterialDialogSheet);
                accountHeadBuilder.setView(accountHeadView);
                TextView tVSaveAccountSubGroup = accountHeadView.findViewById(R.id.tVSaveAccountSubGroup);
                TextView tVCancelAccountSubGroup = accountHeadView.findViewById(R.id.tVCancelAccountSubGroup);
                tVAccountMainGroup = accountHeadView.findViewById(R.id.tVAccountMainGroup);

                EditText eTAccountSubGroup = accountHeadView.findViewById(R.id.eTAccountSubGroup);
                EditText eTAccountShortName = accountHeadView.findViewById(R.id.eTAccountShortName);

                eTAccountSubGroup.setText(dto.getName());
                eTAccountShortName.setText(dto.getShortName());
                tVAccountMainGroup.setText(dto.getNatureName());

                tVAccountMainGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        populateAccountGroup(accountGroupDTOList);
                    }
                });

                tVSaveAccountSubGroup.setOnClickListener(v1 -> {
                    dialog.cancel();
                    String accountGroup = tVAccountMainGroup.getText().toString();
                    String shortName = eTAccountShortName.getText().toString();
                    String subGroup = eTAccountSubGroup.getText().toString();


                    if (dataValidation(accountGroup, subGroup)) {
                        updateGroup(accountGroup, shortName, dto.getId());
                    }
                });

                tVCancelAccountSubGroup.setOnClickListener(v12 -> dialog.cancel());

                accountHeadBuilder.setCancelable(true);
                dialog = accountHeadBuilder.create();
                Window accountHeadWindow = dialog.getWindow();
                assert accountHeadWindow != null;
                accountHeadWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                accountHeadWindow.setGravity(Gravity.CENTER);
                dialog.show();
            }
        });
    }


    private boolean dataValidation(String accountGroup, String accountMainGroup) {
        return !accountGroup.isEmpty() && !accountMainGroup.equals("Select");
    }


    private void updateGroup(String name, String shortName, int groupId) {
        String url = APIs.updateAccountGroup + groupId;
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", name);
            postObject.put("shortName", shortName);
            postObject.put("headId", accountHeadId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIRequest loginRequest = new APIRequest(
                Request.Method.PUT,
                url,
                postObject,
                submitAccountHead(),
                activityErrorListener());
//        AppUtil.customizeRequest(loginRequest);
        requestQueue.add(loginRequest);
    }

    private Response.Listener<JSONObject> submitAccountHead() {
        return response -> {
            System.out.println("submitResponse:: " + response);
            try {
                String message = response.getString("message");
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    builder.setTitle(AppTexts.success);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(AppTexts.ok, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        notifyDataSetChanged();
                    });
                    builder.show();
                } else {
                    AppUtil.infoDialog(context, AppTexts.error, message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AppUtil.somethingWrongDialog(context);
            }
        };
    }


    private void getAccountHead() {
        progressDialog.show();
        String url = APIs.accountHeadList + preferences.getString(AppTexts.orgId, "");
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountNatureResponse(),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }

    private Response.Listener<JSONObject> accountNatureResponse() {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                accountGroupDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountNatureList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountNatureList.length(); i++) {
                        String object = accountNatureList.getJSONObject(i).toString();
                        accountGroupDTOList.add(new Gson().fromJson(object, AccountGroupDTO.class));
                    }

                } else {
                    AppUtil.somethingWrongDialog(context);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }

    private void populateAccountGroup(List<AccountGroupDTO> accGroupList) {
        View view = inflater.inflate(R.layout.layout_select_account_head, null);
        accountGroupSearchListener((EditText) view.findViewById(R.id.eTSearchAccountGroup));
        RecyclerView rVCategories = view.findViewById(R.id.rVAccountGroup);
        rVCategories.setLayoutManager(new LinearLayoutManager(context));
        accountGroupAdapter = new accGroupAdapter(context, accGroupList, listener);
        rVCategories.setAdapter(accountGroupAdapter);

        accountGroup = (new AlertDialog.Builder(context).setView(view)).create();
        accountGroup.show();
    }


    private void accountGroupSearchListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (accountGroupAdapter != null) {
                    accountGroupAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void callDeleteApi(int id){
        String url = APIs.deleteAccountSubGroup + id + "/" +preferences.getInt(AppTexts.userId,0) + "/"+ "test" ;//test indicates reason

        APIRequest loginRequest = new APIRequest(
                Request.Method.DELETE,
                url,
                null,
                deleteAccountHead(),
                activityErrorListener());
//        AppUtil.customizeRequest(loginRequest);
        requestQueue.add(loginRequest);
    }

    private Response.Listener<JSONObject> deleteAccountHead() {
        return response -> {
            System.out.println("submitResponse:: " + response);
            try {
                String message = response.getString("message");
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    builder.setTitle(AppTexts.success);
                    builder.setMessage(message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(AppTexts.ok, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
//                        notifyDataSetChanged();
                    });
                    builder.show();
                } else {
                    AppUtil.infoDialog(context, AppTexts.error, message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AppUtil.somethingWrongDialog(context);
            }
        };
    }



    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(context, error);
        };
    }



    @Override
    public int getItemCount() {
        return accountSubGroupDTOSList.size();
    }

    @Override
    public void onAccountGroupSelected(String subHeadName, int subHeadCode) {
        System.out.println("name:::: " + subHeadName);
        System.out.println("code:::: " + subHeadCode);
        tVAccountMainGroup.setText(subHeadName);
        accountHeadId = subHeadCode;
        accountGroup.dismiss();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVAccountSubHeadName, tVAccountHeadName, tVAccountNature;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVAccountSubHeadName = itemView.findViewById(R.id.tVAccountSubHeadName);
            tVAccountHeadName = itemView.findViewById(R.id.tVAccountHeadName);
            tVAccountNature = itemView.findViewById(R.id.tVAccountNature);
            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
