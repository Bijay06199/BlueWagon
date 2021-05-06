package com.example.bluewagon.ecalculo.adapters.reportsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.AccountStatementLedgerDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class BankStatementAdapter extends RecyclerView.Adapter<BankStatementAdapter.ViewHolder> {
    private Context context;
    private List<AccountStatementLedgerDTO> bankStatementList;
    private LayoutInflater inflater;

    public BankStatementAdapter(Context context, List<AccountStatementLedgerDTO> bankStatementList) {
        this.context = context;
        this.bankStatementList = bankStatementList;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_account_statement_ledger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        AccountStatementLedgerDTO dto = bankStatementList.get(position);
        holder.tVNepaliDate.setText(dto.getTransactionDateNepali());
        holder.tVEngDate.setText(dto.getTransactionDate());
        holder.tVVoucherType.setText(dto.getModuleType());
        holder.tVVoucherNumber.setText(dto.getVoucher());
        holder.tVParticular.setText(dto.getAccountName());
        holder.tVChequeDate.setText(dto.getChequeDate());
        holder.tVChequeNumber.setText(dto.getChequeNumber());
        holder.tVDeposited.setText(AppUtil.formattedAmounts(dto.getDebitBalance()));
        holder.tVWithdraw.setText(AppUtil.formattedAmounts(dto.getCreditBalance()));
        holder.tVBalance.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
        holder.tVDrCr.setText(dto.getDrCr());
    }

    @Override
    public int getItemCount() {
        return bankStatementList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVNepaliDate, tVEngDate, tVVoucherType, tVVoucherNumber, tVParticular, tVChequeDate, tVChequeNumber,
                tVDeposited, tVWithdraw, tVBalance, tVDrCr;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVNepaliDate = itemView.findViewById(R.id.tVNepaliDate);
            tVEngDate = itemView.findViewById(R.id.tVEngDate);
            tVVoucherType = itemView.findViewById(R.id.tVVoucherType);
            tVVoucherNumber = itemView.findViewById(R.id.tVVoucherNumber);
            tVParticular = itemView.findViewById(R.id.tVParticular);
            tVChequeDate = itemView.findViewById(R.id.tVChequeDate);
            tVChequeNumber = itemView.findViewById(R.id.tVChequeNumber);
            tVDeposited = itemView.findViewById(R.id.tVDeposited);
            tVWithdraw = itemView.findViewById(R.id.tVWithdraw);
            tVBalance = itemView.findViewById(R.id.tVBalance);
            tVDrCr = itemView.findViewById(R.id.tVDrCr);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
