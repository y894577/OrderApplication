package com.example.myapplication.ui.dashboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;


public class LeftAdapter extends BaseAdapter {

    private Context context;
    String data[]={"蔬菜1","水果1","姓氏1","蔬菜2","水果2","姓氏2","蔬菜3","水果3","姓氏3"};

    public LeftAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
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
        vh.tv_left.setText(data[position]);
        return convertView;
    }

    public class ViewHold {
        TextView tv_left;

    }
}