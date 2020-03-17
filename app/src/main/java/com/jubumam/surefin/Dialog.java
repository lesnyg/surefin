package com.jubumam.surefin;

public class Dialog {
    private String schedule_time;
    private String schedule_name;
    private String contracttime;
    private String division;

    public Dialog(String schedule_time, String schedule_name, String contracttime, String division) {
        this.schedule_time = schedule_time;
        this.schedule_name = schedule_name;
        this.contracttime = contracttime;
        this.division = division;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getContracttime() {
        return contracttime;
    }

    public void setContracttime(String contracttime) {
        this.contracttime = contracttime;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}
