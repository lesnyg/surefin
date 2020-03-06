package com.jubumam.SureFin;

public class SongyeongOrder {
    private String name ="";        //주문자이름
    private String phoneNumber; //주문자 전화번호
    private String address;     //주문자 주소
    private String quantity;        //주문자 수량
    private String orderPrice;      //금액
    private String paymentmethod ="";   //계산방법
    private String complete = "";   //배달확인
    private String orderTime;   //주문시간

    public SongyeongOrder(String name, String phoneNumber, String address, String quantity, String orderPrice, String paymentmethod, String complete, String orderTime) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.paymentmethod = paymentmethod;
        this.complete = complete;
        this.orderTime = orderTime;
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
}
