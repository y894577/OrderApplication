package com.example.myapplication.ui.dashboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class LeftAdapter extends BaseAdapter {

    private Context context;
    ArrayList<String> data = new ArrayList<>();

    public void updateData(ArrayList<String> lists){
        data.clear();
        data.addAll(lists);
        this.notifyDataSetChanged();
    }

    public LeftAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_left, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
        } else {
            vh = (ViewHold) convertView.getTag();
        }
        vh.tv_left.setTag(position);
        vh.tv_left.setText(data.get(position));
        return convertView;
    }

    public class ViewHold {
        TextView tv_left;
    }
}