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
import com.example.bluewagon.ecalculo.dtos.CashBookDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class CashBookAdapter extends RecyclerView.Adapter<CashBookAdapter.ViewHolder> {
    private Context context;
    private List<CashBookDTO> cashBookDTOList;
    private LayoutInflater inflater;
    private android.app.AlertDialog dialog;

    public CashBookAdapter(Context context, List<CashBookDTO> cashBookDTOList) {
        this.context = context;
        this.cashBookDTOList = cashBookDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_cash_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CashBookDTO dto = cashBookDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVNepaliDate.setText(dto.getTransactionDateNepali());
        holder.tVEngDate.setText(dto.getTransactionDate());
        holder.tVVoucherType.setText(dto.getModuleType());
        holder.tVVoucherNumber.setText(dto.getVoucher());
        holder.tVParticular.setText(dto.getAccountName());
        holder.tVCashIn.setText(AppUtil.formattedAmounts(dto.getDebitBalance()));
        holder.tVCashOut.setText(AppUtil.formattedAmounts(dto.getCreditBalance()));
        holder.tVBalance.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
        holder.tVDrCr.setText(dto.getDrCr());
//        if (position == 0 || position == cashBookDTOList.size() - 1 || position == cashBookDTOList.size() - 2) {
//            System.out.println("position:::: " + position);
//            holder.iVViewVoucher.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return cashBookDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVNepaliDate, tVEngDate, tVVoucherType, tVVoucherNumber, tVParticular, tVCashIn, tVCashOut, tVBalance, tVDrCr;
        ImageView iVViewVoucher;
        LinearLayout lLDataLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tVNepaliDate = itemView.findViewById(R.id.tVNepaliDate);
            tVEngDate = itemView.findViewById(R.id.tVEngDate);
            tVVoucherType = itemView.findViewById(R.id.tVVoucherType);
            tVVoucherNumber = itemView.findViewById(R.id.tVVoucherNumber);
            tVParticular = itemView.findViewById(R.id.tVParticular);
            tVCashIn = itemView.findViewById(R.id.tVCashIn);
            tVCashOut = itemView.findViewById(R.id.tVCashOut);
            tVBalance = itemView.findViewById(R.id.tVBalance);
            tVDrCr = itemView.findViewById(R.id.tVDrCr);

            iVViewVoucher = itemView.findViewById(R.id.iVViewVoucher);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
