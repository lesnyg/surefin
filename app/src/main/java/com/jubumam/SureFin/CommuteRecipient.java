package com.jubumam.SureFin;

import android.util.Printer;

public class CommuteRecipient {
    private static int personId;
    private static String name;
    private static String rating;
    private static String phoneNumber;
    private static String responsibility;
    private static String commute;
    private static String startTime;
    private static String startWork;
    private static CommuteRecipient commuteRecipient;


    public CommuteRecipient() {
    }


    public CommuteRecipient(int personId, String name, String rating, String phoneNumber, String responsibility, String commute,String startTime,String startWork ) {
        this.personId = personId;
        this.name = name;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.responsibility = responsibility;
        this.commute = commute;
        this.startTime = startTime;
        this.startWork = startWork;
    }

    static CommuteRecipient getInstance(){
        if(commuteRecipient==null) {
            commuteRecipient = new CommuteRecipient(personId, name, rating, phoneNumber, responsibility, commute, startTime,startWork);
        }
        return commuteRecipient;
    }

    public String getStartWork() {
        return startWork;
    }

    public void setStartWork(String startWork) {
        CommuteRecipient.startWork = startWork;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        CommuteRecipient.startTime = startTime;
    }

    public String getCommute() {
        return commute;
    }

    public void setCommute(String commute) {
        CommuteRecipient.commute = commute;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
