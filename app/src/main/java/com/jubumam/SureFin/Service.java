package com.jubumam.SureFin;

public class Service {

    private String name;
    private String date;
    private long sumUsingTime1;
    private String count;
    private String nursingCount;
    private String nursingTotal;
    private String nonPay;


    public Service() {
    }

    public Service(String name, String date, long sumUsingTime1, String count, String nursingCount, String nursingTotal, String nonPay) {
        this.name = name;
        this.date = date;
        this.sumUsingTime1 = sumUsingTime1;
        this.count = count;
        this.nursingCount = nursingCount;
        this.nursingTotal = nursingTotal;
        this.nonPay = nonPay;
    }

    public String getNonPay() {
        return nonPay;
    }

    public void setNonPay(String nonPay) {
        this.nonPay = nonPay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getSumUsingTime1() {
        return sumUsingTime1;
    }

    public void setSumUsingTime1(long sumUsingTime1) {
        this.sumUsingTime1 = sumUsingTime1;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNursingCount() {
        return nursingCount;
    }

    public void setNursingCount(String nursingCount) {
        this.nursingCount = nursingCount;
    }

    public String getNursingTotal() {
        return nursingTotal;
    }

    public void setNursingTotal(String nursingTotal) {
        this.nursingTotal = nursingTotal;
    }
}
