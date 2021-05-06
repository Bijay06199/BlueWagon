package com.example.bluewagon.ecalculo.adapters.transactionAdapter.view;

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
import com.example.bluewagon.ecalculo.dtos.SalesChallanDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class SalesChallanAdapter extends RecyclerView.Adapter<SalesChallanAdapter.ViewHolder> {

    private Context context;
    private List<SalesChallanDTO> salesChallanDTOList;
    private LayoutInflater inflater;

    public SalesChallanAdapter(Context context, List<SalesChallanDTO> salesChallanDTOList) {
        this.context = context;
        this.salesChallanDTOList = salesChallanDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_view_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        SalesChallanDTO dto = salesChallanDTOList.get(position);
        String address = dto.getAddress();
        holder.tVNepaliDate.setText(dto.getPurchaseSalesDateNepali());
        holder.tVEngDate.setText(dto.getPurchaseSalesDate());
        holder.tVBillNumber.setText(String.valueOf(dto.getChallanNo()));
        holder.tVCustomer.setText(dto.getAccountName());
        if (address!=null && !address.isEmpty()){
            holder.tVAddress.setText(address);
        }else{
            holder.tVAddress.setText("--");
        }
        holder.tVStore.setText(dto.getWarehouse().getName());
        holder.tVSubTotal.setText(AppUtil.formattedAmounts(dto.getSubTotalAmount()));
        holder.tVNonTaxable.setText(AppUtil.formattedAmounts(dto.getNonTaxableAmount()));
        holder.tVTaxable.setText(AppUtil.formattedAmounts(dto.getTaxableAmount()));
        holder.tVVat.setText(AppUtil.formattedAmounts(dto.getVatAmount()));
        holder.tVTotal.setText(AppUtil.formattedAmounts(dto.getTotalBillAmount()));
    }

    @Override
    public int getItemCount() {
        return salesChallanDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVNepaliDate, tVEngDate, tVBillNumber, tVCustomer, tVAddress, tVStore, tVSubTotal, tVNonTaxable, tVTaxable, tVVat, tVTotal;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVNepaliDate = itemView.findViewById(R.id.tVNepaliDate);
            tVEngDate = itemView.findViewById(R.id.tVEngDate);
            tVBillNumber = itemView.findViewById(R.id.tVBillNumber);
            tVCustomer = itemView.findViewById(R.id.tVCustomer);
            tVAddress = itemView.findViewById(R.id.tVAddress);
            tVStore = itemView.findViewById(R.id.tVStore);
            tVSubTotal = itemView.findViewById(R.id.tVSubTotal);
            tVNonTaxable = itemView.findViewById(R.id.tVNonTaxable);
            tVTaxable = itemView.findViewById(R.id.tVTaxable);
            tVVat = itemView.findViewById(R.id.tVVat);
            tVTotal = itemView.findViewById(R.id.tVTotal);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);


            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
