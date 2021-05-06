package com.example.bluewagon.ecalculo.adapters.transactionAdapter.entry;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.AddBankDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AddCashWithdrawlAdapter extends RecyclerView.Adapter<AddCashWithdrawlAdapter.ViewHolder> {

    private Context context;
    private List<AddBankDTO> addBankDTOList;
    private LayoutInflater inflater;

    public AddCashWithdrawlAdapter(Context context, List<AddBankDTO> addBankDTOList) {
        this.context = context;
        this.addBankDTOList = addBankDTOList;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_add_banks, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddBankDTO addBankDTO = addBankDTOList.get(position);

        holder.tVBankName.setText(addBankDTO.getBankName());
        holder.tVClosingBalance.setText(AppUtil.formattedAmounts(addBankDTO.getClosingBalance()));
        holder.tVAmount.setText("Amount: " + AppUtil.formattedAmounts(addBankDTO.getAmount()));
        holder.tVChequeDate.setText("Date: " + addBankDTO.getChequeDate());
        holder.tVChequeNumber.setText("Cheque No: " + addBankDTO.getChequeNumber());


        //animation extra touch;
        if (position == addBankDTOList.size() - 1) {
            int colorFrom = context.getResources().getColor(R.color.white);
            int colorTo = context.getResources().getColor(R.color.colorPrimary);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo, colorFrom);
            colorAnimation.setDuration(1500); // milliseconds
            colorAnimation.addUpdateListener(animator -> {
                holder.rootView.setCardBackgroundColor((int) animator.getAnimatedValue());
            });
            colorAnimation.setInterpolator(new DecelerateInterpolator());
            colorAnimation.start();
        }
    }

    @Override
    public int getItemCount() {
        return addBankDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        ImageButton btnDelete;
        TextView tVBankName, tVClosingBalance, tVAmount, tVChequeDate, tVChequeNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tVBankName = itemView.findViewById(R.id.tVBankName);
            tVClosingBalance = itemView.findViewById(R.id.tVClosingBalance);
            tVAmount = itemView.findViewById(R.id.tVAmount);
            tVChequeDate = itemView.findViewById(R.id.tVChequeDate);
            tVChequeNumber = itemView.findViewById(R.id.tVChequeNumber);
        }
    }
}
