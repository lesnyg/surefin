package com.jubumam.SureFin.NokPackage;

public class Nok {
    private static String nokName;
    private static String nokPhone;
    private static String recipientName;
    private static String recipientPhone;
    private static String rating;
    private static String centerName;
    private static Nok nok;

    public Nok(String recipientName, String recipientPhone, String centerName, String rating) {
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.centerName = centerName;
        this.rating = rating;
    }

    public static Nok getInstance() {
        if (nok == null) {
            nok = new Nok(recipientName, recipientPhone, centerName, rating);
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

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        Nok.centerName = centerName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        Nok.rating = rating;
    }
}
