package com.example.myapplication.Address;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Order.OrderAdapter;
import com.example.myapplication.Order.ShoppingCarData;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private AddressActivity context;
    private List<AddressData> data = new ArrayList<>();

    public AddressAdapter(AddressActivity context) {
        this.context = context;
    }

    public void add(AddressData list){
        data.add(list);
        this.notifyDataSetChanged();
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
            vh.item_address = convertView.findViewById(R.id.item_address);

            String address = data.get(position).getAddress().replace("\"","");

            vh.address_detail.setText(address);

            vh.item_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.returnAddress(address);
                }
            });


        } else {
            vh = (AddressAdapter.ViewHold) convertView.getTag();
        }



        return convertView;
    }

    public class ViewHold{
        TextView address_detail;
        LinearLayout item_address;
    }
}
