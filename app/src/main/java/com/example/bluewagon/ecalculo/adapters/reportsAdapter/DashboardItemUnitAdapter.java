package com.example.bluewagon.ecalculo.adapters.reportsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.UnitsDTO;

import java.util.List;

public class DashboardItemUnitAdapter extends RecyclerView.Adapter<DashboardItemUnitAdapter.ViewHolder> {
    private Context context;
    private List<UnitsDTO> unitsDTOList;
    private LayoutInflater inflater;

    public DashboardItemUnitAdapter(Context context, List<UnitsDTO> unitsDTOList) {
        this.context = context;
        this.unitsDTOList = unitsDTOList;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_dashboard_item_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UnitsDTO dto = unitsDTOList.get(position);

        holder.tVUnitName.setText(dto.getName());
        holder.tVShortName.setText(dto.getShortName());
    }

    @Override
    public int getItemCount() {
        return unitsDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVUnitName, tVShortName;
        CardView cVDataLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVUnitName = itemView.findViewById(R.id.tVUnitName);
            tVShortName = itemView.findViewById(R.id.tVShortName);

            cVDataLayout = itemView.findViewById(R.id.cVDataLayout);
        }
    }
}
