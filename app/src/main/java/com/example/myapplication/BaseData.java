package com.example.myapplication;

public class BaseData {
    private String id;
    private String name;
    private float price;
    private String tag;

    public BaseData(String id,String name, float price, String tag) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.tag = tag;
    }

    public BaseData() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}