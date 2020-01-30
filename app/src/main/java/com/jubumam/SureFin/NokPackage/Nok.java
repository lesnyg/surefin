package com.jubumam.SureFin.NokPackage;

public class Nok {
    private static String nokName;
    private static String nokPhone;
    private static String recipientName;
    private static String recipientPhone;
    private static Nok nok;

    public Nok(String nokName, String nokPhone, String recipientName, String recipientPhone) {
        this.nokName = nokName;
        this.nokPhone = nokPhone;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    public Nok(String recipientName, String recipientPhone) {
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    static Nok getInstance(){
        if(nok==null) {
            nok = new Nok(recipientName,recipientPhone);
        }
        return nok;
    }

    public String getNokName() {
        return nokName;
    }

    public void setNokName(String nokName) {
        this.nokName = nokName;
    }

    public String getNokPhone() {
        return nokPhone;
    }

    public void setNokPhone(String nokPhone) {
        this.nokPhone = nokPhone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }
}
