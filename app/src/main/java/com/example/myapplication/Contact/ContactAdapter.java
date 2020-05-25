package com.example.myapplication.Contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.MyApp;
import com.example.myapplication.Order.OrderActivity;
import com.example.myapplication.Order.OrderAdapter;
import com.example.myapplication.R;

public class ContactAdapter extends BaseAdapter {

    private Cursor cursor;
    private ContactActivity context;

    public ContactAdapter(Cursor cursor, ContactActivity context) {
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View convertView, ViewGroup parent) {
        cursor.moveToPosition(position);
        ContactAdapter.ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_contact, null);
            vh = new ContactAdapter.ViewHold();
            convertView.setTag(vh);

            vh.phone_view = convertView.findViewById(R.id.phone_view);
            vh.phone_name = convertView.findViewById(R.id.phone_name);
            vh.phone_number = convertView.findViewById(R.id.phone_number);

            String number = cursor
                    .getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds
                                    .Phone.NUMBER))
                    .replace("-", "")
                    .replace(" ", "");
            String name = cursor
                    .getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds
                                    .Phone.DISPLAY_NAME));

            vh.phone_name.setText("联系人：" + name);
            vh.phone_number.setText("电话：" + number);

            vh.phone_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.returnPhone(number);
                }
            });

        } else {
            vh = (ContactAdapter.ViewHold) convertView.getTag();
        }



        return convertView;
    }

    public class ViewHold {
        TextView phone_name;
        TextView phone_number;
        LinearLayout phone_view;
    }
}
