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
import com.example.bluewagon.ecalculo.dtos.WarehouseGroupDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class WarehouseGroupAdapter extends RecyclerView.Adapter<WarehouseGroupAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<WarehouseGroupDTO> warehouseGroupDTOList;

    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;


    public WarehouseGroupAdapter(Context context, List<WarehouseGroupDTO> warehouseGroupDTOList) {
        this.context = context;
        this.warehouseGroupDTOList = warehouseGroupDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_warehouse_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WarehouseGroupDTO dto = warehouseGroupDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position + 1));
        holder.tVWarehouseGroup.setText(dto.getName());
    }

    @Override
    public int getItemCount() {
        return warehouseGroupDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVWarehouseGroup;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVWarehouseGroup = itemView.findViewById(R.id.tVWarehouseGroup);


            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
