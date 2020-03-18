package com.jubumam.SureFin;

import android.graphics.Bitmap;

public class SongyeongOrder {
    private String name ="";        //주문자이름
    private String phoneNumber; //주문자 전화번호
    private String address;     //주문자 주소
    private String quantity;        //주문자 수량
    private String orderPrice;      //금액
    private String paymentmethod ="";   //계산방법
    private String complete = "";   //배달확인
    private String orderTime;   //주문시간
    private String ordermethod;   //주문형태
    private String firstOrder;   //첫주문
    private String mealTime;   //식사시간
    private Bitmap bitmap;   //첫주문

    public SongyeongOrder(String name, String phoneNumber, String address, String quantity, String orderPrice, String paymentmethod, String complete, String orderTime, String ordermethod, String firstOrder) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.paymentmethod = paymentmethod;
        this.complete = complete;
        this.orderTime = orderTime;
        this.ordermethod = ordermethod;
        this.firstOrder = firstOrder;

    }
    public SongyeongOrder(String name, String phoneNumber, String address, String quantity, String orderPrice, String paymentmethod, String complete, String orderTime, String ordermethod, String firstOrder, Bitmap bitmap) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.paymentmethod = paymentmethod;
        this.complete = complete;
        this.orderTime = orderTime;
        this.ordermethod = ordermethod;
        this.firstOrder = firstOrder;
        this.bitmap = bitmap;
    }

    public SongyeongOrder(String name, String phoneNumber, String address, String quantity, String orderPrice, String paymentmethod, String complete, String orderTime, String ordermethod, String firstOrder, String mealTime, Bitmap bitmap) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.paymentmethod = paymentmethod;
        this.complete = complete;
        this.orderTime = orderTime;
        this.ordermethod = ordermethod;
        this.firstOrder = firstOrder;
        this.mealTime = mealTime;
        this.bitmap = bitmap;
    }

    public SongyeongOrder(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrdermethod() {
        return ordermethod;
    }

    public void setOrdermethod(String ordermethod) {
        this.ordermethod = ordermethod;
    }

    public String getFirstOrder() {
        return firstOrder;
    }

    public void setFirstOrder(String firstOrder) {
        this.firstOrder = firstOrder;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }
}
