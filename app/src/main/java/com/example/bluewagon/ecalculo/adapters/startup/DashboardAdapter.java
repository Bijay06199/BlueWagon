package com.example.bluewagon.ecalculo.adapters.startup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.DashboardDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private Context context;
    private List<DashboardDTO> dashboardDTOList;
    private LayoutInflater inflater;

    public DashboardAdapter(Context context, List<DashboardDTO> dashboardDTOList) {
        this.context = context;
        this.dashboardDTOList = dashboardDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_dashboard_items, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardDTO dto = dashboardDTOList.get(position);

        holder.tVReportType.setText(dto.getName());
        double balance = dto.getBalance();
        String balanceDrCr = Double.toString(balance);
        String formattedAmount = AppUtil.formattedAmounts(balance);

        String prefix = "";
        String suffix = "";

        if (!formattedAmount.equalsIgnoreCase("0.00")) {
            prefix = balanceDrCr.startsWith("-") ? " Dr" : " Cr";
            suffix = "Rs. ";
        }


        holder.tVBalance.setText(suffix + formattedAmount + prefix);
    }

    @Override
    public int getItemCount() {
        return dashboardDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVReportType, tVBalance;
        CardView cVDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVReportType = itemView.findViewById(R.id.tVReportType);
            tVBalance = itemView.findViewById(R.id.tVBalance);

            cVDataLayout = itemView.findViewById(R.id.cVDataLayout);
        }
    }
}
