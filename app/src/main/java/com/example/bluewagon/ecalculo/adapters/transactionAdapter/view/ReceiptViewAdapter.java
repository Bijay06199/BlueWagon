package com.example.bluewagon.ecalculo.adapters.transactionAdapter.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.ReceiptDTO;

import java.util.List;

public class ReceiptViewAdapter extends RecyclerView.Adapter<ReceiptViewAdapter.ViewHolder> {
    private Context context;
    private List<ReceiptDTO> receiptDTOList;
    private LayoutInflater inflater;
    private android.app.AlertDialog dialog;

    public  ReceiptViewAdapter(Context context, List<ReceiptDTO> receiptDTOList){
        this.context = context;
        this.receiptDTOList = receiptDTOList;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_payment_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReceiptDTO dto = receiptDTOList.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVBillDate, tVParty, tVDebitAmount, tVBillNumber, tVName, tVNarration, tVCreditAmount;
        LinearLayout lLDateLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVBillDate = itemView.findViewById(R.id.tVBillDate);
            tVParty = itemView.findViewById(R.id.tVParty);
            tVDebitAmount = itemView.findViewById(R.id.tVDebitAmount);
            tVBillNumber = itemView.findViewById(R.id.tVBillNumber);
            tVName = itemView.findViewById(R.id.tVName);
            tVNarration = itemView.findViewById(R.id.tVNarration);
            tVCreditAmount = itemView.findViewById(R.id.tVCreditAmount);


//            lLDateLayout = itemView.findViewById(R.id.lLDateLayout);
        }
    }
}
