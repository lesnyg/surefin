package com.jubumam.SureFin;

import java.util.Date;

public class CommuteRecipient {
    private static String personId;            //요양사 아이디
    private static String recipiId;            //수급자 아이디
    private static String name;
    private static String rating;
    private static String phoneNumber;
    private static String responsibility;
    private static String commute;
    private static String startTime;
    private static String startWork;
    private static String route;
    private static CommuteRecipient commuteRecipient;

    public CommuteRecipient() {
    }

    public CommuteRecipient(String personId, String recipiId, String name, String rating, String phoneNumber, String responsibility, String commute, String startTime, String startWork, String route) {
        this.personId = personId;
        this.recipiId = recipiId;
        this.name = name;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.responsibility = responsibility;
        this.commute = commute;
        this.startTime = startTime;
        this.startWork = startWork;
        this.route = route;
    }

    public static CommuteRecipient getInstance() {
        if (commuteRecipient == null) {
            commuteRecipient = new CommuteRecipient(personId, recipiId, name, rating, phoneNumber, responsibility, commute, startTime, startWork, route);
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRecipiId() {
        return recipiId;
    }

    public void setRecipiId(String recipiId) {
        CommuteRecipient.recipiId = recipiId;
    }
}
