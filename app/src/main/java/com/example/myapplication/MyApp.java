package com.example.myapplication;

import android.app.Application;

public class MyApp extends Application {
    //作为共享类

    public String userID;

    public final String ip = "http://192.168.0.104:8088";

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public String getIP(){
        return ip;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
