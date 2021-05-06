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
import com.example.bluewagon.ecalculo.dtos.UnitsDTO;
import com.example.bluewagon.ecalculo.utilities.AppTexts;
import com.example.bluewagon.ecalculo.utilities.AppUtil;

import java.util.List;

public class MeasurementUnitAdapter extends RecyclerView.Adapter<MeasurementUnitAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<UnitsDTO> unitsDTOList;

    private RequestQueue requestQueue;
    private androidx.appcompat.app.AlertDialog progressDialog;
    private SharedPreferences preferences;


    public MeasurementUnitAdapter(Context context, List<UnitsDTO> unitsDTOList) {
        this.context = context;
        this.unitsDTOList = unitsDTOList;
        inflater = LayoutInflater.from(context);

        requestQueue = Volley.newRequestQueue(context);
        progressDialog = AppUtil.progressDialog(context, AppTexts.pleaseWait);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_measurement_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UnitsDTO dto = unitsDTOList.get(position);
        if (position % 2 == 1) {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else {
            holder.lLDataLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.tVSNo.setText(String.valueOf(position + 1));
        holder.tVUnitName.setText(dto.getName());
        holder.tVShortName.setText(dto.getShortName());
        holder.tVDescription.setText(dto.getDescription());

    }

    @Override
    public int getItemCount() {
        return unitsDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tVSNo, tVUnitName, tVShortName, tVDescription;
        ImageView iVEdit, iVDelete;
        LinearLayout lLDataLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tVSNo = itemView.findViewById(R.id.tVSNo);
            tVUnitName = itemView.findViewById(R.id.tVUnitName);
            tVShortName = itemView.findViewById(R.id.tVShortName);
            tVDescription = itemView.findViewById(R.id.tVDescription);

            iVEdit = itemView.findViewById(R.id.iVEdit);
            iVDelete = itemView.findViewById(R.id.iVDelete);

            lLDataLayout = itemView.findViewById(R.id.lLDataLayout);
        }
    }
}
