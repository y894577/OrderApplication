package com.example.myapplication.Address;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Order.OrderAdapter;
import com.example.myapplication.Order.ShoppingCarData;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private Context context;
    private List<AddressData> data = new ArrayList<>();

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void updateData(ArrayList<AddressData> lists) {
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
            convertView = View.inflate(context, R.layout.item_address, null);
            vh = new AddressAdapter.ViewHold();
            convertView.setTag(vh);

            vh.address_detail = convertView.findViewById(R.id.address_detail);
            vh.address_button = convertView.findViewById(R.id.address_button);

            vh.address_detail.setText(data.get(position).getAddress().replace("\"",""));

            if(!data.get(position).isDefault()){
                vh.address_button.setVisibility(View.VISIBLE);
            }
            else {
                vh.address_button.setVisibility(View.INVISIBLE);
            }

        } else {
            vh = (AddressAdapter.ViewHold) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHold{
        TextView address_detail;
        Button address_button;
    }
}
