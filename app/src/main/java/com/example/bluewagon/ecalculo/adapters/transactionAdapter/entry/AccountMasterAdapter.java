package com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.AccountMasterDTO;
import com.example.bluewagon.ecalculo.listeners.AccountMasterSelectedListener;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class AccountMasterAdapter extends RecyclerView.Adapter<AccountMasterAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<AccountMasterDTO> accountMasterDisplayList;
    private List<AccountMasterDTO> accountMasterOriginalList;
    private LayoutInflater inflater;
    private AccountMasterSelectedListener listener;
    private String activityName;

    public AccountMasterAdapter(Context context, List<AccountMasterDTO> accountMasterDTOList, AccountMasterSelectedListener listener, String activityName) {
        this.context = context;
        this.accountMasterOriginalList = accountMasterDTOList;
        this.accountMasterDisplayList = accountMasterDTOList;
        this.listener = listener;
        this.activityName = activityName;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_account_group_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountMasterDTO dto = accountMasterDisplayList.get(position);
        holder.tVAccountGroupName.setText(dto.getName());


        holder.tVAccountGroupName.setOnClickListener(view -> {
            if (activityName.equalsIgnoreCase("receiptEntry") || activityName.equalsIgnoreCase("paymentEntry")){
                if (dto.isIsBankAccount()||dto.isIsCashAccount()){
                    listener.onAccountSelected(dto.getName(), dto.getId(), dto.getClosingBalance());
                }else{
                    String message = "Please select Cash or Bank Account";
                    AppUtil.showErrorTitleBox(context,message);
                }
            }else {
                listener.onAccountSelected(dto.getName(), dto.getId(), dto.getClosingBalance());
            }
        });


    }

    @Override
    public int getItemCount() {
        return accountMasterDisplayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                accountMasterDisplayList = (ArrayList<AccountMasterDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AccountMasterDTO> filteredArrList = new ArrayList<>();
                if (accountMasterOriginalList == null) {
                    accountMasterOriginalList = new ArrayList<>(accountMasterDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = accountMasterOriginalList.size();
                    results.values = accountMasterOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < accountMasterOriginalList.size(); i++) {
                        AccountMasterDTO data = accountMasterOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(accountMasterOriginalList.get(i));
                        }
                    }
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVAccountGroupName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVAccountGroupName = itemView.findViewById(R.id.tVAccountGroupName);
        }
    }
}
