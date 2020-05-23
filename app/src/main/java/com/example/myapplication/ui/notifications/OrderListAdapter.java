package com.example.myapplication.ui.notifications;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.BaseData;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OrderData> data = new ArrayList<>();

    public OrderListAdapter(Context context) {
        super();
        this.context = context;
    }

    public void updateData(ArrayList<OrderData> lists) {
        data.clear();
        data.addAll(lists);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.status = convertView.findViewById(R.id.status);
            vh.time = convertView.findViewById(R.id.time);
            vh.total_money = convertView.findViewById(R.id.total_money);

            vh.status.setText("状态：已完成");
            vh.time.setText(data.get(position).getTime().toString());
            vh.total_money.setText(String.valueOf(data.get(position).getMoney()));

        } else {
            vh = (ViewHold) convertView.getTag();
        }


        return convertView;
    }

    public class ViewHold {
        TextView status;
        TextView time;
        TextView total_money;

    }

}
