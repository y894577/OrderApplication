package com.example.myapplication.Order;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.BottomBar;
import com.example.myapplication.MyApp;
import com.example.myapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {

    private ListView shopping_car_list;
    private TextView sum_money;
    private Switch order_switch;
    private TextView order_address;
    private MyApp myApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        OrderAdapter orderAdapter = new OrderAdapter(getBaseContext());
        shopping_car_list = findViewById(R.id.shopping_car_list);
        shopping_car_list.setAdapter(orderAdapter);
        sum_money = findViewById(R.id.sum_money);
        order_switch = findViewById(R.id.order_switch);
        order_address = findViewById(R.id.order_address);
        ArrayList<ShoppingCarData> list = new ArrayList<>();

        myApp = (MyApp) getApplication();
        String userID = myApp.getUserID();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.myapplication.ACTION_ORDER");
        OrderReciver orderReciver = new OrderReciver();
        registerReceiver(orderReciver, intentFilter);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("userID", userID)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.0.104:8088/getShoppingCar")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                JsonArray obj = new JsonParser().parse(res).getAsJsonArray();
                for (int i = 0; i < obj.size(); i++) {
                    JsonObject item = obj.get(i).getAsJsonObject();
                    String carID = item.get("carID").toString();
                    String userID = item.get("userID").toString();
                    String goodsID = item.get("goodsID").toString();
                    int number = Integer.parseInt(item.get("number").toString());
                    float price = Float.parseFloat(item.get("price").toString());
                    String name = item.get("name").toString();

                    ShoppingCarData data = new ShoppingCarData(carID, userID, goodsID, number, price, name);
                    list.add(data);
                }
                setList(list, orderAdapter);
            }
        });

        Button pay_order = findViewById(R.id.pay_order);
        pay_order.setOnClickListener(v -> {
            final String[] item = {"确定"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(OrderActivity.this)
                    .setTitle("确定要下单吗")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {


                            Intent intentorder = new Intent();
                            intentorder.setAction("com.example.myapplication.ACTION_ORDER");
                            intentorder.putExtra("data", "order");
                            sendBroadcast(intentorder);

                            Intent intent = new Intent(OrderActivity.this, OrderService.class);
                            intent.setAction("android.intent.action.RESPOND_VIA_MESSAGE");
                            OrderActivity.this.startService(intent);

//                            OkHttpClient clientOrder = new OkHttpClient();
//                            RequestBody requestBodyOrder = new FormBody.Builder()
//                                    .add("userID", userID)
//                                    .add("sum",sum_money.getText().toString())
//                                    .build();
//                            Request requestOrder = new Request.Builder()
//                                    .url("http://192.168.0.104:8088/submitOrder")
//                                    .post(requestBodyOrder)
//                                    .build();
//                            clientOrder.newCall(requestOrder).enqueue(new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//
//                                }
//
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    Log.d("msg",response.body().string());
//                                }
//                            });
//
//                            OkHttpClient clientCar = new OkHttpClient();
//                            RequestBody requestBodyCar = new FormBody.Builder()
//                                    .add("userID",userID)
//                                    .build();
//                            Request requestCar = new Request.Builder()
//                                    .url("http://192.168.0.104:8088/clearCar")
//                                    .post(requestBodyCar)
//                                    .build();
//                            clientCar.newCall(requestCar).enqueue(new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//
//                                }
//
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    //这个地方需要发送广播，暂时未实现
//                                    Log.d("msg","clear");
//                                }
//                            });
//
//
//
//                            Intent intent = new Intent(OrderActivity.this, BottomBar.class);
//                            startActivity(intent);
                        }
                    });
            dialog.show();


        });

        order_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    order_address.setVisibility(View.VISIBLE);
                    OkHttpClient clientAddress = new OkHttpClient();
                    RequestBody requestBodyAddress = new FormBody.Builder()
                            .add("userID", userID)
                            .build();
                    Request requestOrder = new Request.Builder()
                            .url("http://192.168.0.104:8088/getDefaultAddress")
                            .post(requestBodyAddress)
                            .build();
                    clientAddress.newCall(requestOrder).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            setAddress(response.body().string(), orderAdapter);
                        }
                    });
                } else {
                    order_address.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setList(ArrayList<ShoppingCarData> list, OrderAdapter orderAdapter) {
        this.runOnUiThread(() -> {
            orderAdapter.updateData(list);
            sum_money.setText("共计：" + orderAdapter.getSumMoney() + " 元");
        });
    }

    private void setAddress(String address, OrderAdapter orderAdapter) {
        this.runOnUiThread(() -> {
            order_address.setText("地址：" + address);
        });
    }
}
