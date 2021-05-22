package com.example.adminpanel;

public class OrderListModelClass {

    private String orderNo;
    private String orderType;
    private String price;
    private String imgUrl;
    private String ordername;
    private String orderCity;
    private String orderAddress;
    private String orderMobile;
    private String orderMail;

    public OrderListModelClass() {
    }

    public OrderListModelClass(String orderNo, String orderType, String price, String imgUrl, String ordername, String orderCity, String orderAddress, String orderMobile, String orderMail) {
        this.orderNo = orderNo;
        this.orderType = orderType;
        this.price = price;
        this.imgUrl = imgUrl;
        this.ordername = ordername;
        this.orderCity = orderCity;
        this.orderAddress = orderAddress;
        this.orderMobile = orderMobile;
        this.orderMail = orderMail;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrderCity() {
        return orderCity;
    }

    public void setOrderCity(String orderCity) {
        this.orderCity = orderCity;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderMobile() {
        return orderMobile;
    }

    public void setOrderMobile(String orderMobile) {
        this.orderMobile = orderMobile;
    }

    public String getOrderMail() {
        return orderMail;
    }

    public void setOrderMail(String orderMail) {
        this.orderMail = orderMail;
    }
}
