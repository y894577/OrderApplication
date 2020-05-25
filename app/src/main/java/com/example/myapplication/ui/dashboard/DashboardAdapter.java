package com.example.myapplication.ui.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

public class DashboardAdapter extends BaseAdapter {
    private Context context;
    private String[] strings;
    public static int mPosition;
    public DashboardAdapter(Context context, String[] strings){
        this.context =context;
        this.strings = strings;
    }


    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.fragment_dashboard, null);
        return convertView;

    }
}
