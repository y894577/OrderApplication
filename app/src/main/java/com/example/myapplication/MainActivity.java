package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.Order.OrderReciver;


public class MainActivity extends AppCompatActivity {

    private LoginActivity login;
    private OrderReciver orderReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(orderReciver);
    }


}
