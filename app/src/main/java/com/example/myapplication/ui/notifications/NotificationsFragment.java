package com.example.myapplication.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MyApp;
import com.example.myapplication.Order.OrderActivity;
import com.example.myapplication.Order.OrderAdapter;
import com.example.myapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private ListView order_list;
    private MyApp myApp;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        order_list = root.findViewById(R.id.order_list);

        OrderListAdapter orderListAdapter = new OrderListAdapter(getContext());

        order_list.setAdapter(orderListAdapter);

        ArrayList<OrderData> lists = new ArrayList<>();

        myApp = (MyApp) getActivity().getApplication();
        String userID = myApp.getUserID();
        String ip = myApp.getIP();

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("userID", "123456")
                .build();
        Request request = new Request.Builder()
                .url(ip + "/showAllOrder")
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
                    String orderID = item.get("orderID").getAsString();
                    String userID = item.get("userID").getAsString();
                    float money = Float.parseFloat(item.get("money").toString());
                    String time = item.get("time").getAsString().substring(0, 10);
                    OrderData data = new OrderData(orderID, userID, money, time);
                    lists.add(data);
                }
                showOrderList(lists, orderListAdapter);

            }
        });

        return root;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void showOrderList(final ArrayList<OrderData> orderData, OrderListAdapter orderListAdapter) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                orderListAdapter.updateData(orderData);
            }
        });

    }
}