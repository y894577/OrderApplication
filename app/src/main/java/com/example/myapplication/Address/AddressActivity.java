package com.example.myapplication.Address;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.myapplication.MyApp;
import com.example.myapplication.Order.OrderAdapter;
import com.example.myapplication.Order.ShoppingCarData;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Serializable;
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

    public LocationClient mLocationClient;
    private MyApp myApp;
    private ListView address_list;
    private FloatingActionButton add_address;
    private LinearLayout current_address;
    private MapView mapView;
    private TextView my_address;
    private StringBuilder currentPosition = new StringBuilder();

    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ArrayList<AddressData> list = new ArrayList<>();

        myApp = (MyApp) getApplication();
        final String userID = myApp.getUserID();
        final String ip = myApp.getIP();

        add_address = findViewById(R.id.add_address);
        address_list = findViewById(R.id.address_list);
        current_address = findViewById(R.id.current_address);

        AddressAdapter addressAdapter = new AddressAdapter(AddressActivity.this);
        address_list.setAdapter(addressAdapter);


        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("userID", userID)
                .build();
        Request request = new Request.Builder()
                .url(ip + "/getAddressList")
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
                        OkHttpClient clientAdd = new OkHttpClient();
                        RequestBody requestBodyAdd = new FormBody.Builder()
                                .add("userID", userID)
                                .add("address", input.getText().toString())
                                .build();
                        Request request = new Request.Builder()
                                .url("http://192.168.0.104:8088/addAddress")
                                .post(requestBodyAdd)
                                .build();

                        AddressData data = new AddressData(userID, input.getText().toString(), false);

                        clientAdd.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Looper.prepare();
                                Toast.makeText(AddressActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        });
                        addressAdapter.add(data);
                    }
                });
                builder.show();
            }
        });


        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        my_address = findViewById(R.id.my_address);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(AddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(AddressActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(AddressActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(AddressActivity.this, permissions, 1);
        } else {
            requestLocation();
        }

        current_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("address", (Serializable) currentPosition);
                setResult(1, intent);
                finish();
            }
        });
    }

    public void returnAddress(String string) {
        Intent intent = new Intent();
        intent.putExtra("address", string);
        setResult(1, intent);
        finish();
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder currentPosition = new StringBuilder();
            if (location == null){
                return;
            }else {
                currentPosition.append(location.getProvince());
                currentPosition.append(location.getCity());
                currentPosition.append(location.getDistrict());
                currentPosition.append(location.getStreet());
                my_address.setText(currentPosition);
            }
        }
    }
}
