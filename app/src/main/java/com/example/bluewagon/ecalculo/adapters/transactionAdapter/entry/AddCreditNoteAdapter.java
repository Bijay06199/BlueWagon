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
import com.example.bluewagon.ecalculo.dtos.AddAccountsDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AddCreditNoteAdapter extends RecyclerView.Adapter<AddCreditNoteAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<AddAccountsDTO> addAccountsDTOList;

    public AddCreditNoteAdapter(Context context, List<AddAccountsDTO> addAccountsDTOList) {
        this.context = context;
        this.addAccountsDTOList = addAccountsDTOList;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_add_accounts, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddAccountsDTO accountsDTO = addAccountsDTOList.get(position);

        holder.tVName.setText(accountsDTO.getAccountName());
        holder.tVClosingBalance.setText(AppUtil.formattedAmounts(accountsDTO.getClosingBalance()));
        holder.tVCreditAmount.setText(AppUtil.formattedAmounts(accountsDTO.getCreditAmount()) + " Cr");
        holder.tVDebitAmount.setText(AppUtil.formattedAmounts(accountsDTO.getDebitAmount()) + " Dr");
        holder.tVNarration.setText(accountsDTO.getNarration());

        //animation extra touch;
        if (position == addAccountsDTOList.size() - 1) {
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
        return addAccountsDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        ImageButton btnDelete;
        TextView tVName, tVClosingBalance, tVDebitAmount, tVCreditAmount, tVNarration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tVName = itemView.findViewById(R.id.tVName);
            tVClosingBalance = itemView.findViewById(R.id.tVClosingBalance);
            tVDebitAmount = itemView.findViewById(R.id.tVDebitAmount);
            tVCreditAmount = itemView.findViewById(R.id.tVCreditAmount);
            tVNarration = itemView.findViewById(R.id.tVNarration);
        }
    }
}
