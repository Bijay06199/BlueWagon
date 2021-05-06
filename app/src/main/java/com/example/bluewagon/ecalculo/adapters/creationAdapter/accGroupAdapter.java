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
import com.example.bluewagon.ecalculo.dtos.AccountGroupDTO;
import com.example.bluewagon.ecalculo.listeners.AccountGroupSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class accGroupAdapter extends RecyclerView.Adapter<accGroupAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<AccountGroupDTO> accountGroupDisplayList;
    private List<AccountGroupDTO> accountGroupOriginalList;
    private LayoutInflater inflater;
    private AccountGroupSelectedListener listener;

    public accGroupAdapter(Context context, List<AccountGroupDTO> accountGroupDTOList, AccountGroupSelectedListener listener) {
        this.context = context;
        this.accountGroupOriginalList = accountGroupDTOList;
        this.accountGroupDisplayList = accountGroupDTOList;
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
        AccountGroupDTO dto = accountGroupDisplayList.get(position);

        holder.tVAccountGroupName.setText(dto.getName());

        holder.tVAccountGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccountGroupSelected(dto.getName(),dto.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountGroupDisplayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                accountGroupDisplayList = (ArrayList<AccountGroupDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AccountGroupDTO> filteredArrList = new ArrayList<>();
                if (accountGroupOriginalList == null) {
                    accountGroupOriginalList = new ArrayList<>(accountGroupDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = accountGroupOriginalList.size();
                    results.values = accountGroupOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < accountGroupOriginalList.size(); i++) {
                        AccountGroupDTO data = accountGroupOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(accountGroupOriginalList.get(i));
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
