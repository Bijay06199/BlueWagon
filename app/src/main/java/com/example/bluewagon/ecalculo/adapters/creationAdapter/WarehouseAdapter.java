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
import com.example.bluewagon.ecalculo.dtos.WarehouseDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<WarehouseDTO> warehouseDTOList;

    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;


    public WarehouseAdapter(Context context, List<WarehouseDTO> warehouseDTOList) {
        this.context = context;
        this.warehouseDTOList = warehouseDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_warehouse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WarehouseDTO dto = warehouseDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position+1));
        holder.tVWarehouseGroup.setText(dto.getGroupName());
        holder.tVWarehouseLocation.setText(dto.getLocation());
        holder.tVWarehouseName.setText(dto.getName());
    }

    @Override
    public int getItemCount() {
        return warehouseDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVWarehouseGroup, tVWarehouseName, tVWarehouseLocation;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVWarehouseGroup = itemView.findViewById(R.id.tVWarehouseGroup);
            tVWarehouseName = itemView.findViewById(R.id.tVWarehouseName);
            tVWarehouseLocation = itemView.findViewById(R.id.tVWarehouseLocation);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
