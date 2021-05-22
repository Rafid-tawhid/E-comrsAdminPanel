package com.example.adminpanel;

import com.google.firebase.database.Exclude;

public class Upload {
    private String name;
    private String type;
    private String details;
    private String price;
    private String imgUrl;
    private String key;
    private int quantity;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public Upload() {
    }

    public Upload(String name, String type, String details, String price, String imgUrl, String key, int quantity) {
        this.name = name;
        this.type = type;
        this.details = details;
        this.price = price;
        this.imgUrl = imgUrl;
        this.key = key;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
