package com.example.bluewagon.ecalculo.adapters.creationAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.ItemGroupDTO;
import com.example.bluewagon.ecalculo.listeners.ItemGroupSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ItemGrpAdapter extends RecyclerView.Adapter<ItemGrpAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<ItemGroupDTO> itemGroupDisplayList;
    private List<ItemGroupDTO> itemGroupOriginalList;
    private LayoutInflater inflater;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;
    private ItemGroupSelectedListener listener;

    public ItemGrpAdapter(Context context, List<ItemGroupDTO> itemGroupDTOList, ItemGroupSelectedListener listener) {
        this.context = context;
        this.itemGroupDisplayList = itemGroupDTOList;
        this.itemGroupOriginalList = itemGroupDTOList;
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
        ItemGroupDTO dto = itemGroupDisplayList.get(position);

        holder.tVAccountGroupName.setText(dto.getName());

        holder.tVAccountGroupName.setOnClickListener(view -> listener.onItemGroupSelected(dto.getName(),dto.getId()));
    }

    @Override
    public int getItemCount() {
        return itemGroupDisplayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemGroupDisplayList = (ArrayList<ItemGroupDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ItemGroupDTO> filteredArrList = new ArrayList<>();
                if (itemGroupOriginalList == null) {
                    itemGroupOriginalList = new ArrayList<>(itemGroupDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = itemGroupOriginalList.size();
                    results.values = itemGroupOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < itemGroupOriginalList.size(); i++) {
                        ItemGroupDTO data = itemGroupOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(itemGroupOriginalList.get(i));
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
        LinearLayout lLDataLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVAccountGroupName = itemView.findViewById(R.id.tVAccountGroupName);
            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
