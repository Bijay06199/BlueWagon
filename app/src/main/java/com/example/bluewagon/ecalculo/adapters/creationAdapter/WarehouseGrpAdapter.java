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
import com.example.bluewagon.ecalculo.dtos.WarehouseGroupDTO;
import com.example.bluewagon.ecalculo.listeners.WarehouseGroupSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class WarehouseGrpAdapter extends RecyclerView.Adapter<WarehouseGrpAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<WarehouseGroupDTO> warehouseGroupDisplayList;
    private List<WarehouseGroupDTO> warehouseGroupOriginalList;
    private LayoutInflater inflater;
    private WarehouseGroupSelectedListener listener;

    public WarehouseGrpAdapter(Context context, List<WarehouseGroupDTO> warehouseGroupDTOList, WarehouseGroupSelectedListener listener) {
        this.context = context;
        this.warehouseGroupDisplayList = warehouseGroupDTOList;
        this.warehouseGroupOriginalList = warehouseGroupDTOList;
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
        WarehouseGroupDTO dto = warehouseGroupDisplayList.get(position);
        holder.tVAccountGroupName.setText(dto.getName());


        holder.tVAccountGroupName.setOnClickListener(view -> listener.onWarehouseGroupSelected(dto.getName(),dto.getId()));
    }

    @Override
    public int getItemCount() {
        return warehouseGroupDisplayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                warehouseGroupDisplayList = (ArrayList<WarehouseGroupDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<WarehouseGroupDTO> filteredArrList = new ArrayList<>();
                if (warehouseGroupOriginalList == null) {
                    warehouseGroupOriginalList = new ArrayList<>(warehouseGroupDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = warehouseGroupOriginalList.size();
                    results.values = warehouseGroupOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < warehouseGroupOriginalList.size(); i++) {
                        WarehouseGroupDTO data = warehouseGroupOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(warehouseGroupOriginalList.get(i));
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
