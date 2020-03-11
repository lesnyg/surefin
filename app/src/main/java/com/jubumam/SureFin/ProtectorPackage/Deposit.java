package com.jubumam.SureFin.ProtectorPackage;

import java.text.DecimalFormat;

public class Deposit {          //정산모델
    private String date;
    private int settlement;
    private int charge;
    private int deposit;
    private int balance;
    private DecimalFormat moneyfm;

    public Deposit(String date, int settlement, int charge, int deposit, int balance) {
        this.date = date;
        this.settlement = settlement;
        this.charge = charge;
        this.deposit = deposit;
        this.balance = balance;
        moneyfm = new DecimalFormat("###,###");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSettlement() {
        return settlement;
    }

    public void setSettlement(int settlement) {
        this.settlement = settlement;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
