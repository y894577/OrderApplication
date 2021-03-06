package com.example.myapplication.Contact;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ContactActivity extends AppCompatActivity {
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ListView contact_list = findViewById(R.id.contact_list);


        final Cursor cursor = getContentResolver()
                .query(ContactsContract.CommonDataKinds
                        .Phone.CONTENT_URI, null, null, null, null);

        contactAdapter = new ContactAdapter(cursor, ContactActivity.this);

        //加载listview
        contact_list.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();


    }

    public void returnPhone(String phone) {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        setResult(1, intent);

        finish();
    }

}
