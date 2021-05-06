package com.example.bluewagon.ecalculo.adapters.creationAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.dtos.AccountLedgerDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AccountLedgerAdapter extends RecyclerView.Adapter<AccountLedgerAdapter.ViewHolder> {

    private Context context;
    private List<AccountLedgerDTO> accountLedgerDTOList;
    private LayoutInflater inflater;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;


    public AccountLedgerAdapter(Context context, List<AccountLedgerDTO> accountLedgerDTOList) {
        this.context = context;
        this.accountLedgerDTOList = accountLedgerDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_account_ledger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountLedgerDTO dto = accountLedgerDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        holder.tVSNo.setText(String.valueOf(position + 1));
        holder.tVAccountNature.setText(dto.getAccountNatureName());
        holder.tVAccountType.setText(dto.getAccountTypeName());
        holder.tVAccountGroup.setText(dto.getAccountHeadName());
        holder.tVAccountSubGroup.setText(dto.getAccountSubHeadName());
        holder.tVAccountName.setText(dto.getName());
        holder.tVShortName.setText(dto.getShortName());
        holder.tVClosingBalance.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
        holder.tVAccountDrCr.setText(dto.getDrCr());

    }

    @Override
    public int getItemCount() {
        return accountLedgerDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tVSNo, tVAccountNature, tVAccountType, tVAccountGroup, tVAccountSubGroup, tVAccountName, tVShortName, tVClosingBalance, tVAccountDrCr;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVAccountNature = itemView.findViewById(R.id.tVAccountNature);
            tVAccountType = itemView.findViewById(R.id.tVAccountType);
            tVAccountGroup = itemView.findViewById(R.id.tVAccountGroup);
            tVAccountSubGroup = itemView.findViewById(R.id.tVAccountSubGroup);
            tVAccountName = itemView.findViewById(R.id.tVAccountName);
            tVShortName = itemView.findViewById(R.id.tVShortName);
            tVClosingBalance = itemView.findViewById(R.id.tVClosingBalance);
            tVAccountDrCr = itemView.findViewById(R.id.tVAccountDrCr);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
