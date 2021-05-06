package com.example.bluewagon.ecalculo.adapters.reportsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.ItemStatementDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class ItemStatementAdapter extends RecyclerView.Adapter<ItemStatementAdapter.ViewHolder> {
    private Context context;
    private List<ItemStatementDTO> itemStatementDTOList;
    private LayoutInflater inflater;

    public ItemStatementAdapter(Context context, List<ItemStatementDTO> itemStatementDTOList) {
        this.context = context;
        this.itemStatementDTOList = itemStatementDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item_statement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        ItemStatementDTO dto = itemStatementDTOList.get(position);
        holder.tVNepaliDate.setText(dto.getNepaliDate());
        holder.tVEngDate.setText(dto.getDate());
        holder.tVVoucherType.setText(dto.getVoucherType());
        holder.tVVoucherNumber.setText(dto.getVoucher());
        holder.tVParticular.setText(dto.getPartyName());
        holder.tVOpening.setText(String.valueOf(dto.getOpeningQuantity()));
        holder.tVIn.setText(String.valueOf(dto.getInQuantity()));
        holder.tVOut.setText(String.valueOf(dto.getOutQuantity()));
        holder.tVBalance.setText(AppUtil.formattedAmounts(dto.getInOutAmount()));
        holder.tVClosing.setText(String.valueOf(dto.getClosingQuantity()));
        holder.tVClosingAmount.setText(AppUtil.formattedAmounts(dto.getClosingValue()));
    }

    @Override
    public int getItemCount() {
        return itemStatementDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVNepaliDate, tVEngDate, tVVoucherType, tVVoucherNumber, tVParticular, tVOpening, tVIn, tVOut, tVBalance, tVClosing, tVClosingAmount;
        ImageView iVViewVoucher;
        LinearLayout lLDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVNepaliDate = itemView.findViewById(R.id.tVNepaliDate);
            tVEngDate = itemView.findViewById(R.id.tVEngDate);
            tVVoucherType = itemView.findViewById(R.id.tVVoucherType);
            tVVoucherNumber = itemView.findViewById(R.id.tVVoucherNumber);
            tVParticular = itemView.findViewById(R.id.tVParticular);
            tVOpening = itemView.findViewById(R.id.tVOpening);
            tVIn = itemView.findViewById(R.id.tVIn);
            tVOut = itemView.findViewById(R.id.tVOut);
            tVBalance = itemView.findViewById(R.id.tVBalance);
            tVClosing = itemView.findViewById(R.id.tVClosing);
            tVClosingAmount = itemView.findViewById(R.id.tVClosingAmount);

            iVViewVoucher = itemView.findViewById(R.id.iVViewVoucher);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
