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
import com.example.bluewagon.ecalculo.dtos.DashboardItemServiceDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class DashboardItemServiceAdapter extends RecyclerView.Adapter<DashboardItemServiceAdapter.ViewHolder> {

    private Context context;
    private List<DashboardItemServiceDTO> dashboardItemServiceDTOS;
    private LayoutInflater inflater;

    public DashboardItemServiceAdapter(Context context, List<DashboardItemServiceDTO> dashboardItemServiceDTOS) {
        this.context = context;
        this.dashboardItemServiceDTOS = dashboardItemServiceDTOS;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_dashboard_item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardItemServiceDTO dto = dashboardItemServiceDTOS.get(position);

        holder.tVServiceName.setText(dto.getServiceName());
        holder.tVSoldQty.setText(AppUtil.formattedAmounts(dto.getSoldQty()));
        holder.tVServiceUnit.setText(dto.getUnit());
    }

    @Override
    public int getItemCount() {
        return dashboardItemServiceDTOS.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVServiceName, tVSoldQty, tVServiceUnit;
        CardView cVDataLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVServiceName = itemView.findViewById(R.id.tVServiceName);
            tVSoldQty = itemView.findViewById(R.id.tVSoldQty);
            tVServiceUnit = itemView.findViewById(R.id.tVServiceUnit);

            cVDataLayout = itemView.findViewById(R.id.cVDataLayout);
        }
    }
}
