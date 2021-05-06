package com.example.bluewagon.ecalculo.adapters.reportsAdapter;

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
import com.example.bluewagon.ecalculo.dtos.DashboardTransactionDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class DashboardTransactionAdapter extends RecyclerView.Adapter<DashboardTransactionAdapter.ViewHolder> {

    private Context context;
    private List<DashboardTransactionDTO> dashboardTransactionDTOList;
    private LayoutInflater inflater;

    public DashboardTransactionAdapter(Context context, List<DashboardTransactionDTO> dashboardTransactionDTOList) {
        this.context = context;
        this.dashboardTransactionDTOList = dashboardTransactionDTOList;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_transactions_details, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashboardTransactionDTO dto = dashboardTransactionDTOList.get(position);

        holder.tVDueDays.setText("Due Day: " +dto.getDueDays());
        holder.tVTransactionType.setText(dto.getTransactionType());
        holder.tVDate.setText("Date: " + dto.getDate());
        holder.tVPartyName.setText("Party: " + dto.getParty());
        holder.tVName.setText(dto.getName());
        holder.tVBillNumber.setText("Bill No: "+ dto.getBillType() + "-" + dto.getBillNumber());
        holder.tVBillAmount.setText("Rs. " + AppUtil.formattedAmounts(dto.getBillAmount()));



    }

    @Override
    public int getItemCount() {
        return dashboardTransactionDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVDueDays, tVTransactionType, tVDate, tVBillNumber, tVPartyName, tVName, tVBillAmount;
        CardView cVDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVDueDays = itemView.findViewById(R.id.tVDueDays);
            tVTransactionType = itemView.findViewById(R.id.tVTransactionType);
            tVDate = itemView.findViewById(R.id.tVDate);
            tVBillNumber = itemView.findViewById(R.id.tVBillNumber);
            tVPartyName = itemView.findViewById(R.id.tVPartyName);
            tVName = itemView.findViewById(R.id.tVName);
            tVBillAmount = itemView.findViewById(R.id.tVBillAmount);

            cVDataLayout = itemView.findViewById(R.id.cVDataLayout);
        }
    }
}
