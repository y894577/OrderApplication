package com.example.myapplication;

import android.app.Application;

public class MyApp extends Application {
    //作为共享类

    public String userID;

    public String userPhone;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
