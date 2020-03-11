package com.jubumam.SureFin.ProtectorPackage;

public class Protector {
    private static String recipiId;
    private static String recipientName;
    private static String recipientPhone;
    private static String rating;
    private static String centerName;
    private static Protector protector;

    public Protector(String recipiId, String recipientName, String recipientPhone, String centerName, String rating) {
        this.recipiId = recipiId;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.centerName = centerName;
        this.rating = rating;
    }

    public static Protector getInstance() {
        if (protector == null) {
            protector = new Protector(recipiId,recipientName, recipientPhone, centerName, rating);
        }
        return protector;
    }

    public  String getRecipiId() {
        return recipiId;
    }

    public void setRecipiId(String recipiId) {
        Protector.recipiId = recipiId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        Protector.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        Protector.recipientPhone = recipientPhone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        Protector.rating = rating;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        Protector.centerName = centerName;
    }
}
