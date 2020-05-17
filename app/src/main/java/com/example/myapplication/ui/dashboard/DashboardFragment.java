package com.example.myapplication.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BaseData;
import com.example.myapplication.BottomBar;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Order.OrderActivity;
import com.example.myapplication.R;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public static final String TAG = "MyFragment";

    private ListView lv_left;
    private ListView lv_right;
    private TextView tv_title;
    private TextView calculate;
    private ImageView add_to_car;
    private String msg;

    private ArrayList<String> showTitle;
    private ArrayList<BaseData> lists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        //加载列表

        lv_left = root.findViewById(R.id.lv_left);

        lv_right = root.findViewById(R.id.lv_right);

        LeftAdapter leftAdapter = new LeftAdapter(getContext());

        lv_left.setAdapter(leftAdapter);

        RightAdapter rightAdapter = new RightAdapter(getContext());

        lv_right.setAdapter(rightAdapter);





        try {
            ArrayList<String> title = new ArrayList<>();
            ArrayList<BaseData> lists = new ArrayList<BaseData>();
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://192.168.0.104:8088/getAllTag")
                    .get()
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    JsonParser parser = new JsonParser();
                    String tags = response.body().string();

                    JsonArray obj = new JsonParser().parse(tags).getAsJsonArray();
                    for (int i = 0; i < obj.size(); i++) {
                        String tag = obj.get(i).getAsString();
                        title.add(tag);
                        Request request_other = new Request.Builder()
                                .url("http://192.168.0.104:8088/getGoodsListByTag?tag=" + tag)
                                .get()
                                .build();
                        client.newCall(request_other).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String res = response.body().string();
                                JsonArray obj = new JsonParser().parse(res).getAsJsonArray();
                                for (int i = 0; i < obj.size(); i++) {
                                    JsonObject item = obj.get(i).getAsJsonObject();
                                    String id = item.get("id").toString();
                                    String name = item.get("name").toString();
                                    float price = Float.parseFloat(item.get("price").toString());
                                    String tag = item.get("tag").toString();
                                    BaseData data = new BaseData(id, name, price, tag);
                                    lists.add(data);
//                                    Log.d("data", name + id + tag);
                                }
                                showResponse(lists, root, rightAdapter);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }



        //定义button

        calculate = root.findViewById(R.id.calculate);
        add_to_car = root.findViewById(R.id.add_to_car);


        return root;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //跳转到结算页面
        calculate.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderActivity.class);
            startActivity(intent);
        });


    }

    private void updateLeftListview(int firstVisibleItem, int currentPosition) {
        if (showTitle.contains(firstVisibleItem + "")) {

        }
    }


    private void showResponse(final ArrayList<BaseData> response, View root, RightAdapter rightAdapter) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                Log.d("msg", response.get(0).getTag());
                rightAdapter.updateData(response);

                showTitle = new ArrayList<String>();
                for (int i = 0; i < response.size(); i++) {
                    if (i == 0) {
                        showTitle.add(i + "");
                    } else if (!TextUtils.equals(response.get(i).getTag(),
                            response.get(i - 1).getTag())) {
                        showTitle.add(i + 1 + "");
                    }
                }
                lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        int firstVisibleItem = lv_right.getFirstVisiblePosition();
//                右边Listview当前第一个可见条目的position
                        updateLeftListview(firstVisibleItem, arg2);
                        lv_right.setSelection(Integer.parseInt(showTitle.get(arg2)));
                    }
                });
            }
        });
    }


}