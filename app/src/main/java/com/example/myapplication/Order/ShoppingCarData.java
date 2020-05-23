package com.example.myapplication.Order;

public class ShoppingCarData {
    private String carID;
    private String userID;
    private String goodsID;
    private int number;
    private float price;
    private String name;

    public ShoppingCarData(String carID, String userID, String goodsID, int number, float price, String name) {
        this.carID = carID;
        this.userID = userID;
        this.goodsID = goodsID;
        this.number = number;
        this.price = price;
        this.name = name;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
