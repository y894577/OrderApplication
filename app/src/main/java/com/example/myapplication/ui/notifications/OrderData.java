package com.example.myapplication.ui.notifications;

import java.util.Date;

public class OrderData {
    private String orderID;
    private String userID;
    private float money;
    private String time;

    public OrderData(String orderID, String userID, float money, String time) {
        this.orderID = orderID;
        this.userID = userID;
        this.money = money;
        this.time = time;
    }

    public OrderData() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
