package com.example.bluewagon.ecalculo.adapters.transactionAdapter.view;

import android.annotation.SuppressLint;
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
import com.example.bluewagon.ecalculo.dtos.JournalViewDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class JournalViewAdapter extends RecyclerView.Adapter<JournalViewAdapter.ViewHolder> {

    private Context context;
    private List<JournalViewDTO> journalViewDTOList;
    private LayoutInflater inflater;
    private android.app.AlertDialog dialog;

    public JournalViewAdapter(Context context, List<JournalViewDTO> journalViewDTOList) {
        this.context = context;
        this.journalViewDTOList = journalViewDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_journal_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JournalViewDTO dto = journalViewDTOList.get(position);

        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position + 1));
        holder.tVNepaliDate.setText(dto.getTransactionDateNepali());
        holder.tVDate.setText(dto.getTransactionDate());
        holder.tVVoucherNumber.setText(dto.getVoucher());
        holder.tVAccountName.setText(dto.getAccountName());
        holder.tVNarration.setText(dto.getRemarks());
        holder.tVDebitAmount.setText(AppUtil.formattedAmounts(dto.getDebitBalance()));
        holder.tVCreditAmount.setText(AppUtil.formattedAmounts(dto.getCreditBalance()));
    }


    @Override
    public int getItemCount() {
        return journalViewDTOList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVNepaliDate, tVDate, tVVoucherNumber, tVAccountName, tVNarration, tVDebitAmount, tVCreditAmount;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVNepaliDate = itemView.findViewById(R.id.tVNepaliDate);
            tVDate = itemView.findViewById(R.id.tVDate);
            tVVoucherNumber = itemView.findViewById(R.id.tVVoucherNumber);
            tVAccountName = itemView.findViewById(R.id.tVAccountName);
            tVNarration = itemView.findViewById(R.id.tVNarration);
            tVDebitAmount = itemView.findViewById(R.id.tVDebitAmount);
            tVCreditAmount = itemView.findViewById(R.id.tVCreditAmount);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
