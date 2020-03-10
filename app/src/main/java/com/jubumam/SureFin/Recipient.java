package com.jubumam.SureFin;
import android.graphics.Bitmap;

public class Recipient {
    private String personId;
    private int photoId;
    private String recipiId;
    private String name;
    private String indiviPay;
    private String rating;
    private Bitmap bitmap;
    private String adress;
    private String number;
    private String gender;
    private String responsibility;


    public Recipient(Bitmap bitmap,String recipiId,String name, String number, String adress,String gender){
        this.bitmap = bitmap;
        this.recipiId = recipiId;
        this.name = name;
        this.number = number;
        this.adress = adress;
        this.gender = gender;
    }
/*
    public Recipient(Bitmap bitmap, String name, String indiviPay, String rating) {
        this.bitmap = bitmap;
        this.name = name;
        this.indiviPay = indiviPay;
        this.rating = rating;
    }

 */

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public Recipient(int photoId,Bitmap bitmap){
        this.photoId = photoId;
        this.bitmap = bitmap;
    }
    public Recipient(){

    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIndiviPay() {
        return indiviPay;
    }

    public void setIndiviPay(String indiviPay) {
        this.indiviPay = indiviPay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRecipiId() {
        return recipiId;
    }

    public void setRecipiId(String recipiId) {
        this.recipiId = recipiId;
    }
}
