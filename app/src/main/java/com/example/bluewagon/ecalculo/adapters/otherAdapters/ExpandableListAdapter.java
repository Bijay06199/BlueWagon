package com.example.bluewagon.ecalculo.adapters.otherAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.example.bluewagon.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        System.out.println("pos::: " + groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblListItem);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_child, parent, false);
        }
        TextView txtListChild = convertView.findViewById(R.id.lblListChildItem);
        txtListChild.setTypeface(null, Typeface.BOLD);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return this.listDataChild.get(listDataHeader.get(groupPosition)).size();
        } catch (Exception e) {
            return 0;
        }
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
}