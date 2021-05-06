package com.example.bluewagon.ecalculo.adapters.reportsAdapter;

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
import com.example.bluewagon.ecalculo.dtos.ItemServiceDTO;
import com.example.bluewagon.ecalculo.listeners.ItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class ItemSelectionAdapter extends RecyclerView.Adapter<ItemSelectionAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<ItemServiceDTO> itemServiceDisplayList;
    private List<ItemServiceDTO> itemServiceOriginalList;
    private LayoutInflater inflater;
    private ItemSelectedListener listener;

    public ItemSelectionAdapter(Context context, List<ItemServiceDTO> itemServiceDTOList, ItemSelectedListener listener) {
        this.context = context;
        this.itemServiceOriginalList = itemServiceDTOList;
        this.itemServiceDisplayList = itemServiceDTOList;
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
        ItemServiceDTO dto = itemServiceDisplayList.get(position);
        holder.tVAccountGroupName.setText(dto.getName());

        holder.tVAccountGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelectedListener(dto.getName(), dto.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemServiceDisplayList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemServiceDisplayList = (ArrayList<ItemServiceDTO>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ItemServiceDTO> filteredArrList = new ArrayList<>();
                if (itemServiceOriginalList == null) {
                    itemServiceOriginalList = new ArrayList<>(itemServiceDisplayList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = itemServiceOriginalList.size();
                    results.values = itemServiceOriginalList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < itemServiceOriginalList.size(); i++) {
                        ItemServiceDTO data = itemServiceOriginalList.get(i);
                        if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                            filteredArrList.add(itemServiceOriginalList.get(i));
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
