package com.jubumam.surefin;

public class Order {
    private String date;
    private String time;
    private String main;
    private String bab;
    private String jug;
    private String gug;
    private String banchan1;
    private String banchan2;
    private String banchan3;
    private String banchan4;
    private String snack;
    private String fruit;

    public Order(String date, String time,String main, String bab, String jug, String gug, String banchan1, String banchan2, String banchan3, String banchan4, String snack, String fruit) {
        this.date = date;
        this.time = time;
        this.main = main;
        this.bab = bab;
        this.jug = jug;
        this.gug = gug;
        this.banchan1 = banchan1;
        this.banchan2 = banchan2;
        this.banchan3 = banchan3;
        this.banchan4 = banchan4;
        this.snack = snack;
        this.fruit = fruit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getBab() {
        return bab;
    }

    public void setBab(String bab) {
        this.bab = bab;
    }

    public String getJug() {
        return jug;
    }

    public void setJug(String jug) {
        this.jug = jug;
    }

    public String getGug() {
        return gug;
    }

    public void setGug(String gug) {
        this.gug = gug;
    }

    public String getBanchan1() {
        return banchan1;
    }

    public void setBanchan1(String banchan1) {
        this.banchan1 = banchan1;
    }

    public String getBanchan2() {
        return banchan2;
    }

    public void setBanchan2(String banchan2) {
        this.banchan2 = banchan2;
    }

    public String getBanchan3() {
        return banchan3;
    }

    public void setBanchan3(String banchan3) {
        this.banchan3 = banchan3;
    }

    public String getBanchan4() {
        return banchan4;
    }

    public void setBanchan4(String banchan4) {
        this.banchan4 = banchan4;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }
}
