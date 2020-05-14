package com.example.myapplication.ui.dashboard;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.BaseData;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RightAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BaseData> data = new ArrayList<>();

    public RightAdapter(Context context) {
        super();
        this.context = context;
    }


    public void updateData(ArrayList<BaseData> lists) {
        data.clear();
        data.addAll(lists);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_right, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            vh.tv_right = (TextView) convertView.findViewById(R.id.tv_right);
        } else {
            vh = (ViewHold) convertView.getTag();
        }
        vh.tv_content.setText(data.get(position).getName());
        if (position == 0) {//如果是第一个  需要显示标题
//            vh.tv_right.setVisibility(View.VISIBLE);
            vh.tv_right.setText(data.get(position).getTitle());
        } else if (!TextUtils.equals(data.get(position).getTitle(),
                data.get(position - 1).getTitle())) {//如果这个标题和上一个不一样   也需要将标题显示出来
//            vh.tv_right.setVisibility(View.VISIBLE);
            vh.tv_content.setText(data.get(position).getTitle());
        } else {
//            vh.tv_right.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHold {
        TextView tv_content;
        TextView tv_right;
    }

}