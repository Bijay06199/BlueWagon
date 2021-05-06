package com.example.bluewagon.ecalculo.adapters.dashboardAccount;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.CustomerSupplierLedgerDTO;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AccountCustomerSupplierAdapter extends RecyclerView.Adapter<AccountCustomerSupplierAdapter.ViewHolder> {

    private Context context;
    private List<CustomerSupplierLedgerDTO> dashboardAccountDTOList;
    private LayoutInflater inflater;
    private AlertDialog dialog;

    public AccountCustomerSupplierAdapter(Context context, List<CustomerSupplierLedgerDTO> dashboardAccountDTOList) {
        this.context = context;
        this.dashboardAccountDTOList = dashboardAccountDTOList;
        inflater = LayoutInflater.from(context);
        System.out.println("size:::: " + dashboardAccountDTOList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_account_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerSupplierLedgerDTO dto = dashboardAccountDTOList.get(position);

        holder.tVCustomerSupplierName.setText(dto.getName());
        String drCr = dto.getDrCr();
        if (drCr.equals("Cr")){
            holder.tVAmount.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
            holder.tVAmount.setTextColor(ContextCompat.getColor(context, R.color.creditColor));
        }else{
            holder.tVAmount.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
            holder.tVAmount.setTextColor(ContextCompat.getColor(context, R.color.debitColor));
        }


        holder.iVInfo.setOnClickListener(v -> {
            View infoView = inflater.inflate(R.layout.layout_dashboard_account_info, null);

            AlertDialog.Builder journalEntryBuilder = new AlertDialog.Builder(context, R.style.MaterialDialogSheet);
            journalEntryBuilder.setView(infoView);

            //all stuff here

            TextView tVInfoName = infoView.findViewById(R.id.tVInfoName);
            tVInfoName.setText(dto.getName());


            TextView tVEdit = infoView.findViewById(R.id.tVEdit);
            TextView tVDelete = infoView.findViewById(R.id.tVDelete);
            TextView tVCall = infoView.findViewById(R.id.tVCall);
            TextView tVCancel = infoView.findViewById(R.id.tVCancel);


            tVCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });




            journalEntryBuilder.setCancelable(false);
            dialog = journalEntryBuilder.create();
            Window journalEntryWindow = dialog.getWindow();
            journalEntryWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            journalEntryWindow.setGravity(Gravity.CENTER);
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return dashboardAccountDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVCustomerSupplierName, tVAmount;
        ImageView iVInfo;
        CardView cVDataLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVCustomerSupplierName = itemView.findViewById(R.id.tVCustomerSupplierName);
            tVAmount = itemView.findViewById(R.id.tVAmount);
            iVInfo = itemView.findViewById(R.id.iVInfo);

            cVDataLayout = itemView.findViewById(R.id.cVDataLayout);
        }
    }
}
