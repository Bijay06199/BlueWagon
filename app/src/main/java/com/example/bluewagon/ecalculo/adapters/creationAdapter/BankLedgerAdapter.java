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
import com.example.bluewagon.ecalculo.dtos.BankLedgerDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class BankLedgerAdapter extends RecyclerView.Adapter<BankLedgerAdapter.ViewHolder> {
    private Context context;
    private List<BankLedgerDTO> bankLedgerDTOList;
    private LayoutInflater inflater;
    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;

    public BankLedgerAdapter(Context context, List<BankLedgerDTO> bankLedgerDTOList) {
        this.context = context;
        this.bankLedgerDTOList = bankLedgerDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_bank_ledger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankLedgerDTO dto = bankLedgerDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        holder.tVSNo.setText(String.valueOf(position + 1));
        holder.tVBankName.setText(dto.getName());
        holder.tVAccountNumber.setText(dto.getAccountNumber());
        holder.tVBranch.setText(dto.getBranch());
        holder.tVAccountType.setText(dto.getType());
        holder.tVClosingBalance.setText(AppUtil.formattedAmounts(dto.getClosingBalance()));
        holder.tVAccountDrCr.setText(dto.getDrCr());
    }

    @Override
    public int getItemCount() {
        return bankLedgerDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVBankName, tVAccountNumber, tVBranch, tVAccountType, tVClosingBalance, tVAccountDrCr;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVBankName = itemView.findViewById(R.id.tVBankName);
            tVAccountNumber = itemView.findViewById(R.id.tVAccountNumber);
            tVBranch = itemView.findViewById(R.id.tVBranch);
            tVAccountType = itemView.findViewById(R.id.tVAccountType);
            tVClosingBalance = itemView.findViewById(R.id.tVClosingBalance);
            tVAccountDrCr = itemView.findViewById(R.id.tVAccountDrCr);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
