package com.example.bluewagon.ecalculo.adapters.otherAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bluewagon.R;
import com.example.bluewagon.ecalculo.activities.others.CustomExpListView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterParent extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private List<Integer> listIcons;
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<String>> listDataChildTwo;
    private ExpandableListView.OnChildClickListener onChildClickListener;
    private OnChildClickListener onThirdChildListener;

    public ExpandableListAdapterParent(Context context, List<String> listDataHeader,
                                       HashMap<String, List<String>> listChildData, HashMap<String, List<String>> listDataChildTwo, List<Integer> listIcons) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.listDataChildTwo = listDataChildTwo;
        this.listIcons = listIcons;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public void setOnChildClickListener(ExpandableListView.OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public void setOnThirdChildClickListener(OnChildClickListener onThirdChildListener) {
        this.onThirdChildListener = onThirdChildListener;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        ImageView iVUp = convertView.findViewById(R.id.iVUp);

        if (groupPosition ==1 || groupPosition==2){
            iVUp.setVisibility(View.GONE);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        int icons = listIcons.get(groupPosition);
        ImageView iVIconImage = convertView.findViewById(R.id.iVIconImage);
        iVIconImage.setImageResource(icons);

        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final CustomExpListView secondLevelExpListView = new CustomExpListView(context);
        final String parentNode = (String) getGroup(groupPosition);
        secondLevelExpListView.setAdapter(new ExpandableListAdapter(context, listDataChild.get(parentNode), listDataChildTwo));
        secondLevelExpListView.setGroupIndicator(null);

        secondLevelExpListView.setOnGroupClickListener((parent1, v, innerPos, id) -> {
            if (onChildClickListener != null)
                onChildClickListener.onChildClick(parent1, v, groupPosition, innerPos, id);
            return false;
        });

        secondLevelExpListView.setOnChildClickListener((parent12, v, groupPosition1, childPosition1, id) -> {
            String groupName = listDataChild.get(parentNode).get(groupPosition1);

            if (onThirdChildListener != null)
                onThirdChildListener.onChildClick(groupName, childPosition1);
            return true;
        });

        return secondLevelExpListView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listDataChild.containsKey(listDataHeader.get(groupPosition)))
            return 1;
        else
            return 0;

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnChildClickListener {
        void onChildClick(String groupName, int childPos);
    }
}