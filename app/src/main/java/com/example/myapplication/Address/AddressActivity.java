package com.example.myapplication.Address;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MyApp;
import com.example.myapplication.Order.OrderAdapter;
import com.example.myapplication.Order.ShoppingCarData;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

public class AddressActivity extends AppCompatActivity {

    private MyApp myApp;
    private ListView address_list;
    private FloatingActionButton add_address;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ArrayList<AddressData> list = new ArrayList<>();

        myApp = (MyApp) getApplication();
        String userID = myApp.getUserID();

        add_address = findViewById(R.id.add_address);
        address_list = findViewById(R.id.address_list);
        AddressAdapter addressAdapter = new AddressAdapter(getBaseContext());
        address_list.setAdapter(addressAdapter);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("userID", userID)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.0.104:8088/getAddressList")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("msg", res);
                JsonArray obj = new JsonParser().parse(res).getAsJsonArray();
                for (int i = 0; i < obj.size(); i++) {
                    JsonObject item = obj.get(i).getAsJsonObject();
                    String userID = item.get("userID").toString();
                    String address = item.get("address").toString();
                    boolean isDefault = Boolean.valueOf(item.get("default").toString()).booleanValue();
                    Log.d("default", String.valueOf(isDefault));
                    AddressData data = new AddressData(userID, address, isDefault);
                    list.add(data);
                }
                setList(list, addressAdapter);
            }

            private void setList(ArrayList<AddressData> list, AddressAdapter addressAdapter) {
                runOnUiThread(() -> addressAdapter.updateData(list));
            }
        });

        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(AddressActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                builder.setTitle("请输入地址").setView(input).setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("msg", input.getText().toString());
                    }
                });
                builder.show();
            }
        });
    }
}
