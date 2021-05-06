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
import com.example.bluewagon.ecalculo.dtos.AddItemServiceDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AddItemServiceAdapter extends RecyclerView.Adapter<AddItemServiceAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<AddItemServiceDTO> addItemServiceDTOList;


    public AddItemServiceAdapter(Context context, List<AddItemServiceDTO> addItemServiceDTOList) {
        this.context = context;
        this.addItemServiceDTOList = addItemServiceDTOList;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_add_item_service, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddItemServiceDTO itemServiceDTO = addItemServiceDTOList.get(position);

        holder.tVProductServiceName.setText(itemServiceDTO.getProductServiceName());
        holder.tVUnit.setText("Unit: " + itemServiceDTO.getUnit());
        holder.tVQuantity.setText("Qty: " + AppUtil.formattedAmounts(itemServiceDTO.getQuantity()));
        holder.tVRate.setText("Rate: " + AppUtil.formattedAmounts(itemServiceDTO.getRate()));
        holder.tVSubTotal.setText("Sub-Total: " + AppUtil.formattedAmounts(itemServiceDTO.getSubTotal()));

        //animation extra touch;
        if (position == addItemServiceDTOList.size() - 1) {
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
        return addItemServiceDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView rootView;
        ImageButton btnDelete;
        TextView tVProductServiceName, tVUnit, tVQuantity, tVRate, tVSubTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.rootView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tVProductServiceName = itemView.findViewById(R.id.tVProductServiceName);
            tVUnit = itemView.findViewById(R.id.tVUnit);
            tVQuantity = itemView.findViewById(R.id.tVQuantity);
            tVRate = itemView.findViewById(R.id.tVRate);
            tVSubTotal = itemView.findViewById(R.id.tVSubTotal);
        }
    }
}
