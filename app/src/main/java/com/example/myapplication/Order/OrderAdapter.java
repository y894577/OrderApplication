package com.example.myapplication.Order;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.BaseData;
import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.RightAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<ShoppingCarData> data = new ArrayList<>();

    public OrderAdapter(Context context) {
        super();
        this.context = context;
    }


    public void updateData(ArrayList<ShoppingCarData> lists) {
        data.clear();
        data.addAll(lists);
        this.notifyDataSetChanged();
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order, null);
            vh = new OrderAdapter.ViewHold();
            convertView.setTag(vh);

            vh.sh_content = convertView.findViewById(R.id.sh_content);
            vh.sh_price = convertView.findViewById(R.id.sh_price);
            vh.number = convertView.findViewById(R.id.shopping_number);

            vh.sh_content.setText(data.get(position).getName().replace("\"", ""));
            vh.sh_price.setText("￥ " + data.get(position).getPrice());
            vh.number.setText("数量：" + data.get(position).getNumber());


        } else {
            vh = (OrderAdapter.ViewHold) convertView.getTag();
        }
        return convertView;
    }

    public float getSumMoney() {
        float sum = 0;
        for (int i = 0; i < getCount(); i++) {
            sum += data.get(i).getPrice() * data.get(i).getNumber();
        }
        return sum;
    }

    public class ViewHold {
        TextView sh_content;
        TextView sh_price;
        TextView number;
        TextView sum_money;
    }
}
