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
import com.example.bluewagon.ecalculo.dtos.ItemServiceDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class AddItemServiceAdapter extends RecyclerView.Adapter<AddItemServiceAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ItemServiceDTO> itemServiceDTOList;

    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;

    public AddItemServiceAdapter(Context context, List<ItemServiceDTO> itemServiceDTOList) {
        this.context = context;
        this.itemServiceDTOList = itemServiceDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_additems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemServiceDTO dto = itemServiceDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position+1));
        holder.tVGroup.setText(dto.getGroupName());
        holder.tVSubGroup.setText(dto.getSubGroupName());
        holder.tVName.setText(dto.getName());
        holder.tVCode.setText(dto.getItemCode());
        holder.tVAvailableQty.setText(String.valueOf(dto.getAvailableQty()));
        holder.tVUnit.setText(dto.getUnitName());
        holder.tVStatus.setText(dto.getStatus());
    }

    @Override
    public int getItemCount() {
        return itemServiceDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVGroup, tVSubGroup, tVName, tVCode, tVAvailableQty, tVUnit, tVStatus;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVGroup = itemView.findViewById(R.id.tVGroup);
            tVSubGroup = itemView.findViewById(R.id.tVSubGroup);
            tVName = itemView.findViewById(R.id.tVName);
            tVCode = itemView.findViewById(R.id.tVCode);
            tVAvailableQty = itemView.findViewById(R.id.tVAvailableQty);
            tVUnit = itemView.findViewById(R.id.tVUnit);
            tVStatus = itemView.findViewById(R.id.tVStatus);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
