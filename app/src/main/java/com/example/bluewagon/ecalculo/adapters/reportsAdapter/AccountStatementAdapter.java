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
import com.example.bluewagon.ecalculo.dtos.AccountStatementLedgerDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AccountStatementAdapter extends RecyclerView.Adapter<AccountStatementAdapter.ViewHolder> {

    private Context context;
    private List<AccountStatementLedgerDTO> accountStatementLedgerDTOList;
    private LayoutInflater inflater;
    private android.app.AlertDialog dialog;


    public AccountStatementAdapter(Context context, List<AccountStatementLedgerDTO> accountStatementLedgerDTOList) {
        this.context = context;
        this.accountStatementLedgerDTOList = accountStatementLedgerDTOList;
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
        AccountStatementLedgerDTO dto = accountStatementLedgerDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVNepaliDate.setText(dto.getTransactionDateNepali());
        holder.tVEngDate.setText(dto.getTransactionDate());
        holder.tVVoucherType.setText(dto.getEntryType());
        holder.tVVoucherNumber.setText(dto.getVoucher());
        holder.tVAccount.setText(dto.getAccountName());
        holder.tVParticular.setText(dto.getRemarks());
        holder.tVOpening.setText(AppUtil.formattedAmounts(dto.getOpeningBalance()));
        holder.tVDebit.setText(AppUtil.formattedAmounts(dto.getDebitBalance()));
        holder.tVCredit.setText(AppUtil.formattedAmounts(dto.getCreditBalance()));
        holder.tVBalance.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
        holder.tVDrCr.setText(dto.getDrCr());
        if (position == 0 || position == accountStatementLedgerDTOList.size() - 1 || position == accountStatementLedgerDTOList.size() - 2) {
            System.out.println("position:::: " + position);
            holder.iVViewVoucher.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return accountStatementLedgerDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVNepaliDate, tVEngDate, tVVoucherType, tVVoucherNumber, tVAccount, tVParticular, tVOpening, tVDebit, tVCredit, tVBalance, tVDrCr;
        ImageView iVViewVoucher;
        LinearLayout lLDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVNepaliDate = itemView.findViewById(R.id.tVNepaliDate);
            tVEngDate = itemView.findViewById(R.id.tVEngDate);
            tVVoucherType = itemView.findViewById(R.id.tVVoucherType);
            tVVoucherNumber = itemView.findViewById(R.id.tVVoucherNumber);
            tVAccount = itemView.findViewById(R.id.tVAccount);
            tVParticular = itemView.findViewById(R.id.tVParticular);
            tVOpening = itemView.findViewById(R.id.tVOpening);
            tVDebit = itemView.findViewById(R.id.tVDebit);
            tVCredit = itemView.findViewById(R.id.tVCredit);
            tVBalance = itemView.findViewById(R.id.tVBalance);
            tVDrCr = itemView.findViewById(R.id.tVDrCr);

            iVViewVoucher = itemView.findViewById(R.id.iVViewVoucher);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
