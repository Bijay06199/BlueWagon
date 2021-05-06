package com.example.bluewagon.ecalculo.adapters.creationAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.AccountGroupDTO;
import com.example.bluewagon.ecalculo.dtos.AccountNatureDTO;
import com.example.bluewagon.ecalculo.utilities.APIRequest;
import com.example.bluewagon.ecalculo.utilities.APIs;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountGroupAdapter extends RecyclerView.Adapter<AccountGroupAdapter.ViewHolder> {
    private Context context;
    private List<AccountGroupDTO> accountGroupDTOList;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private List<AccountNatureDTO> accountNatureDTOList;
    private Spinner accountNature;
    private int accountNatureId;
    private SharedPreferences preferences;


    public AccountGroupAdapter(Context context, List<AccountGroupDTO> accountGroupDTOList) {
        this.context = context;
        this.accountGroupDTOList = accountGroupDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_account_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountGroupDTO dto = accountGroupDTOList.get(position);

        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position + 1));
        holder.tVAccountName.setText(dto.getName());
        holder.tVAccountNature.setText(dto.getNatureName());

        holder.iVDelete.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Delete");
            alertDialogBuilder.setMessage("Are you sure, You want to delete the Account Group?");
            alertDialogBuilder.setPositiveButton("yes",
                    (dialog, arg1) -> {
                        //remarks not ready need to add hai ............
                        callDeleteApi(dto.getId());
                        dialog.dismiss();
                    });

            alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });


        holder.iVEdit.setOnClickListener(v -> {
            getAccountNature(dto.getAccountNature().getName());
            View accountHeadView = inflater.inflate(R.layout.layout_add_head_list, null);
            AlertDialog.Builder accountHeadBuilder = new AlertDialog.Builder(context, R.style.MaterialDialogSheet);
            accountHeadBuilder.setView(accountHeadView);
            TextView tVSaveAccountSubGroup = accountHeadView.findViewById(R.id.tVSaveAccountSubGroup);
            TextView tVCancelAccountSubGroup = accountHeadView.findViewById(R.id.tVCancelAccountSubGroup);

            EditText eTAccountGroup = accountHeadView.findViewById(R.id.eTAccountGroup);
            eTAccountGroup.setText(dto.getName());

            accountNature = accountHeadView.findViewById(R.id.accountNature);

            tVSaveAccountSubGroup.setOnClickListener(v1 -> {
                dialog.cancel();
                String accountGroup = eTAccountGroup.getText().toString();
                String accountNatureName = accountNature.getSelectedItem().toString();
                accountNatureId = dto.getAccountNature().getId();

                if (dataValidation(accountGroup, accountNatureName)) {
                    updateGroup(accountGroup, accountNatureId, dto.getId());
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
        });
    }

    private void callDeleteApi(int id){
        String url = APIs.deleteAccountGroup + id + "/" +preferences.getInt(AppTexts.userId,0) + "/"+ "test" ;//test indicates reason

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


    private void updateGroup(String name, int natureId, int groupId) {
        String url = APIs.updateAccountGroup + groupId;
        JSONObject postObject = new JSONObject();
        try {
            postObject.put("name", name);
            postObject.put("accountNatureId", natureId);
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


    private void getAccountNature(String natureName) {
        progressDialog.show();
        String url = APIs.accountNatureList;
        APIRequest headListResponse = new APIRequest(
                Request.Method.GET,
                url,
                accountNatureResponse(natureName),
                activityErrorListener()
        );
        requestQueue.add(headListResponse);
//        AppUtil.customizeRequest(headListResponse);
    }


    private Response.Listener<JSONObject> accountNatureResponse(String accountNatureName) {
        return response -> {
            System.out.println("response:: " + response);
            progressDialog.dismiss();
            try {
                accountNatureDTOList = new ArrayList<>();
                if (response.getString(AppTexts.status).equals(AppTexts.success)) {
                    JSONArray accountNatureList = response.getJSONArray(AppTexts.data);
                    for (int i = 0; i < accountNatureList.length(); i++) {
                        String object = accountNatureList.getJSONObject(i).toString();
                        accountNatureDTOList.add(new Gson().fromJson(object, AccountNatureDTO.class));
                    }
                    populateAccountNature(accountNatureDTOList, accountNatureName);
                    System.out.println("categoriesList::: " + accountNatureDTOList.size());

                } else {
                    AppUtil.somethingWrongDialog(context);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
    }

    private void populateAccountNature(List<AccountNatureDTO> accNatureList, String accountNatureName) {
        System.out.println("accountNatureList:::: " + accNatureList.size());
        List<String> categoriesStringList = new ArrayList<>();
        categoriesStringList.add("Select Account Nature");
        HashMap<String, Integer> accountNatureHashMap = new HashMap<>();
        int layout = android.R.layout.simple_spinner_item;
        int dropdown = android.R.layout.simple_spinner_dropdown_item;

        for (int i = 0; i < accNatureList.size(); i++) {
            categoriesStringList.add(accNatureList.get(i).getName());
            accountNatureHashMap.put(accNatureList.get(i).getName(), accNatureList.get(i).getId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, layout, categoriesStringList);

        for (int i = 0; i < categoriesStringList.size(); i++) {
            if (categoriesStringList.get(i).equalsIgnoreCase(accountNatureName)) {
                adapter.setDropDownViewResource(dropdown);
                accountNature.setAdapter(adapter);
                accountNature.setSelection(i);
                break;
            } else {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                accountNature.setAdapter(adapter);
                accountNature.setSelection(0);
            }
        }
    }


    private Response.ErrorListener activityErrorListener() {
        return error -> {
            error.printStackTrace();
            progressDialog.dismiss();
            AppUtil.showErrorDialog(context, error);
        };
    }

    private boolean dataValidation(String accountGroup, String accountMainGroup) {
        return !accountGroup.isEmpty() && !accountMainGroup.equals("Select Account Nature");
    }


    @Override
    public int getItemCount() {
        return accountGroupDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVAccountName, tVAccountNature;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVAccountName = itemView.findViewById(R.id.tVAccountName);
            tVAccountNature = itemView.findViewById(R.id.tVAccountNature);
            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
