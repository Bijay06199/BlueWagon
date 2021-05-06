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
import com.example.bluewagon.ecalculo.dtos.AddPaymentReceiptDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AddReceiptAdapter extends RecyclerView.Adapter<AddReceiptAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<AddPaymentReceiptDTO> addPaymentReceiptDTOList;

    public AddReceiptAdapter(Context context, List<AddPaymentReceiptDTO> addPaymentReceiptDTOList) {
        this.context = context;
        this.addPaymentReceiptDTOList = addPaymentReceiptDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_add_receipt, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddPaymentReceiptDTO addPaymentReceiptDTO = addPaymentReceiptDTOList.get(position);

        holder.tVName.setText(addPaymentReceiptDTO.getAccountName());
        holder.tVClosingBalance.setText(AppUtil.formattedAmounts(addPaymentReceiptDTO.getClosingBalance()));
        holder.tVPaidAmount.setText("Amount: " + AppUtil.formattedAmounts(addPaymentReceiptDTO.getPaidAmount()));
        holder.tVPaidTo.setText("To: " + addPaymentReceiptDTO.getPaidTo());

        //animation extra touch;
        if (position == addPaymentReceiptDTOList.size() - 1) {
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
        return addPaymentReceiptDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        ImageButton btnDelete;
        TextView tVName, tVClosingBalance, tVPaidAmount, tVPaidTo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tVName = itemView.findViewById(R.id.tVName);
            tVClosingBalance = itemView.findViewById(R.id.tVClosingBalance);
            tVPaidAmount = itemView.findViewById(R.id.tVPaidAmount);
            tVPaidTo = itemView.findViewById(R.id.tVPaidTo);
        }
    }
}
