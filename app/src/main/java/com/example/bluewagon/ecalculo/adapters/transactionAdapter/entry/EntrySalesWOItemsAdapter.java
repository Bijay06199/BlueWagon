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
import com.example.bluewagon.ecalculo.dtos.AddEntryDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class EntrySalesWOItemsAdapter extends RecyclerView.Adapter<EntrySalesWOItemsAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<AddEntryDTO> addEntryDTOList;

    public EntrySalesWOItemsAdapter(Context context, List<AddEntryDTO> addEntryDTOList){
        this.context = context;
        this.addEntryDTOList = addEntryDTOList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_entry_sales_wo_items, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddEntryDTO addEntryDTO = addEntryDTOList.get(position);

        holder.tVName.setText(addEntryDTO.getAccountName());
        holder.tVClosingBalance.setText(AppUtil.formattedAmounts(addEntryDTO.getClosingBalance()));
        holder.tVAmount.setText("Amount: " + AppUtil.formattedAmounts(addEntryDTO.getAmount()));


        //animation extra touch;
        if (position == addEntryDTOList.size() - 1) {
            int colorFrom = context.getResources().getColor(R.color.white);
            int colorTo = context.getResources().getColor(R.color.colorPrimary);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo, colorFrom, colorTo, colorFrom);
            colorAnimation.setDuration(2000); // milliseconds
            colorAnimation.addUpdateListener(animator -> {
                holder.rootView.setCardBackgroundColor((int) animator.getAnimatedValue());
            });
            colorAnimation.setInterpolator(new DecelerateInterpolator());
            colorAnimation.start();
        }
    }

    @Override
    public int getItemCount() {
        return addEntryDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        ImageButton btnDelete;
        TextView tVName, tVClosingBalance, tVAmount, tVBillDate, tVBillNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tVName = itemView.findViewById(R.id.tVName);
            tVClosingBalance = itemView.findViewById(R.id.tVClosingBalance);
            tVAmount = itemView.findViewById(R.id.tVAmount);
        }
    }
}
