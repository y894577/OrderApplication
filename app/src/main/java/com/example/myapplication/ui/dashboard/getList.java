package com.example.myapplication.ui.dashboard;

import android.util.Log;

import com.example.myapplication.BaseData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getList implements Callable<ArrayList<BaseData>> {


    @Override
    public ArrayList<BaseData> call() throws Exception {
        ArrayList<String> title = new ArrayList<>();
        ArrayList<BaseData> lists = new ArrayList<BaseData>();


        try {

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://192.168.0.127:8088/getAllTag")
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            Log.d("msg",response.body().string());

//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//
//                    JsonParser parser = new JsonParser();
//                    String tags = response.body().string();
//
//                    JsonArray obj = new JsonParser().parse(tags).getAsJsonArray();
//
//                    BaseData base = new BaseData("a", "b", 1, "c");
//                    lists.add(base);


//                    for (int i = 0; i < obj.size(); i++) {
//                        String tag = obj.get(i).getAsString();
//                        title.add(tag);
//                        Request request_other = new Request.Builder()
//                                .url("http://192.168.0.127:8088/getGoodsListByTag?tag=" + tag)
//                                .get()
//                                .build();
//                        client.newCall(request_other).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//                                String res = response.body().string();
//                                JsonArray obj = new JsonParser().parse(res).getAsJsonArray();
//
//                                for (int i = 0; i < obj.size(); i++) {
//                                    JsonObject item = obj.get(i).getAsJsonObject();
//                                    String id = item.get("id").toString();
//                                    String name = item.get("name").toString();
//                                    float price = Float.parseFloat(item.get("price").toString());
//                                    String tag = item.get("tag").toString();
//                                    BaseData data = new BaseData(id, name, price, tag);
//                                    lists.add(data);
////                                    Log.d("data", name + id + tag);
//                                }
//                            }
//                        });
//                    }
//
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return lists;
        }
//        Log.d("msg", lists.get(0).getTag());
//        return lists;
    }
}
