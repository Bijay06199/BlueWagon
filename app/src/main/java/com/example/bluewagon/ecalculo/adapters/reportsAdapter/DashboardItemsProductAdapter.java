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
import com.example.bluewagon.ecalculo.dtos.DashboardItemDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class DashboardItemsProductAdapter extends RecyclerView.Adapter<DashboardItemsProductAdapter.ViewHolder> {

    private Context context;
    private List<DashboardItemDTO> dashboardItemDTOList;
    private LayoutInflater inflater;

    public DashboardItemsProductAdapter(Context context, List<DashboardItemDTO> dashboardItemDTOList) {
        this.context = context;
        this.dashboardItemDTOList = dashboardItemDTOList;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_dashboard_item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardItemDTO dto = dashboardItemDTOList.get(position);

        holder.tVProductName.setText(dto.getName());
        holder.tVItemCode.setText(dto.getItemCode());
        holder.tVUnit.setText(dto.getUnitName());
        holder.tVClosingStock.setText(AppUtil.formattedAmounts(dto.getClosingStock()));
        holder.tVPurchaseRate.setText(AppUtil.formattedAmounts(dto.getPurchaseRate()));
        holder.tVMrp.setText(AppUtil.formattedAmounts(dto.getMrp()));
        holder.tVSalesRate.setText(AppUtil.formattedAmounts(dto.getSalesRate()));
        holder.tVWholesaleRate.setText(AppUtil.formattedAmounts(dto.getWholesaleRate()));
    }

    @Override
    public int getItemCount() {
        return dashboardItemDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVProductName, tVItemCode, tVUnit, tVClosingStock, tVPurchaseRate, tVMrp, tVSalesRate, tVWholesaleRate;
        CardView cVDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVProductName = itemView.findViewById(R.id.tVProductName);
            tVItemCode = itemView.findViewById(R.id.tVItemCode);
            tVUnit = itemView.findViewById(R.id.tVUnit);
            tVClosingStock = itemView.findViewById(R.id.tVClosingStock);
            tVPurchaseRate = itemView.findViewById(R.id.tVPurchaseRate);
            tVMrp = itemView.findViewById(R.id.tVMrp);
            tVSalesRate = itemView.findViewById(R.id.tVSalesRate);
            tVWholesaleRate = itemView.findViewById(R.id.tVWholesaleRate);

            cVDataLayout = itemView.findViewById(R.id.cVDataLayout);
        }
    }
}
