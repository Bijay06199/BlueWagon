package com.example.bluewagon.ecalculo.adapters.creationAdapter;

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
import com.example.bluewagon.ecalculo.dtos.AccountSubGroupDTO;
import com.example.bluewagon.ecalculo.listeners.AccountSubGroupSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class accSubGroupAdapter extends RecyclerView.Adapter<accSubGroupAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<AccountSubGroupDTO> accountSubGroupDisplayList;
    private List<AccountSubGroupDTO> accountSubGroupOriginalList;
    private LayoutInflater inflater;
    private AccountSubGroupSelectedListener listener;

    public accSubGroupAdapter(Context context, List<AccountSubGroupDTO> accountSubGroupDTOList, AccountSubGroupSelectedListener listener) {
        System.out.println("sizee1::: " + accountSubGroupDTOList.size());
        this.context = context;
        this.accountSubGroupOriginalList = accountSubGroupDTOList;
        this.accountSubGroupDisplayList = accountSubGroupDTOList;
        this.listener = listener;
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
        AccountSubGroupDTO dto = accountSubGroupDisplayList.get(position);
        holder.tVAccountGroupName.setText(dto.getName());

        holder.tVAccountGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccountSubGroupSelected(dto.getName(),dto.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountSubGroupDisplayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                accountSubGroupDisplayList = (ArrayList<AccountSubGroupDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AccountSubGroupDTO> filteredArrList = new ArrayList<>();
                if (accountSubGroupOriginalList == null) {
                    accountSubGroupOriginalList = new ArrayList<>(accountSubGroupDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = accountSubGroupOriginalList.size();
                    results.values = accountSubGroupOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < accountSubGroupOriginalList.size(); i++) {
                        AccountSubGroupDTO data = accountSubGroupOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(accountSubGroupOriginalList.get(i));
                        }
                    }
                    results.count = filteredArrList.size();
                    results.values = filteredArrList;
                }
                return results;
            }
        };
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVAccountGroupName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVAccountGroupName = itemView.findViewById(R.id.tVAccountGroupName);
        }
    }
}
