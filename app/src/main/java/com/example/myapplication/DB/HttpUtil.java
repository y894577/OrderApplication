package com.example.myapplication.DB;

import java.io.*;
import java.net.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static void setHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
