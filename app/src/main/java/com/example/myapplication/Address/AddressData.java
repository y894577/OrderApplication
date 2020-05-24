package com.example.myapplication.Address;

public class AddressData {
    private String userID;

    private String address;

    private boolean isDefault;

    public AddressData() {
    }

    public AddressData(String userID, String address, boolean isDefault) {
        this.userID = userID;
        this.address = address;
        this.isDefault = isDefault;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
