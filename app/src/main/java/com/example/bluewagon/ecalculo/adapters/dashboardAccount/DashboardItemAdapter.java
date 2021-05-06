package com.example.bluewagon.ecalculo.adapters.dashboardAccount;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluewagon.ecalculo.dtos.DashboardItemDTO;

import java.util.List;


public class DashboardItemAdapter extends RecyclerView.Adapter<DashboardItemAdapter.ViewHolder> {
    private Context context;
    private List<DashboardItemDTO> dashboardItemDTOList;
    private LayoutInflater inflater;
    private AlertDialog dialog;


    public DashboardItemAdapter(Context context, List<DashboardItemDTO> dashboardItemDTOList) {
        this.context = context;
        this.dashboardItemDTOList = dashboardItemDTOList;
        inflater = LayoutInflater.from(context);
        System.out.println("size:::: " + dashboardItemDTOList.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
