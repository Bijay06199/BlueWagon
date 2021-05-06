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
import com.example.bluewagon.ecalculo.dtos.AccountMasterDTO;
import com.example.bluewagon.ecalculo.listeners.VendorSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<AccountMasterDTO> vendorDisplayList;
    private List<AccountMasterDTO> vendorOriginalList;
    private LayoutInflater inflater;
    private VendorSelectedListener listener;

    public VendorAdapter(Context context, List<AccountMasterDTO> vendorDTOList, VendorSelectedListener listener) {
        this.context = context;
        this.vendorOriginalList = vendorDTOList;
        this.vendorDisplayList = vendorDTOList;
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
        AccountMasterDTO dto = vendorDisplayList.get(position);

        holder.tVAccountGroupName.setText(dto.getName());
        System.out.println("cb::" + dto.getClosingBalance());

        holder.tVAccountGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onVendorSelected(dto.getId(), dto.getName(), dto.getPanNo(), dto.getClosingBalance());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorDisplayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                vendorDisplayList = (ArrayList<AccountMasterDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AccountMasterDTO> filteredArrList = new ArrayList<>();
                if (vendorOriginalList == null) {
                    vendorOriginalList = new ArrayList<>(vendorDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = vendorOriginalList.size();
                    results.values = vendorOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < vendorOriginalList.size(); i++) {
                        AccountMasterDTO data = vendorOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(vendorOriginalList.get(i));
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
