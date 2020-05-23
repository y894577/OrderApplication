package com.example.myapplication;

import android.app.Application;

public class MyApp extends Application {
    //作为共享类

    public String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
