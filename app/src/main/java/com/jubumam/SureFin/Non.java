package com.jubumam.SureFin;

public class Non {

    private String classification; //대분류
    private String htitle; //비급여타이틀
    private String memberdivision; //회원구분
    private String utilization; //이용한도
    private String offerDate;   //서비스제공일자

    private String offerTime;   //서비스제공시간

    public Non(String classification, String htitle, String memberdivision, String utilization) {


        this.classification = classification; //대분류
        this.htitle = htitle; //비급여타이틀
        this.memberdivision = memberdivision; //회원구분
        this.utilization = utilization; //이용한도

    }

    public Non(String classification, String htitle, String memberdivision, String utilization, String offerDate,String offerTime) {
        this.classification = classification;
        this.htitle = htitle;
        this.memberdivision = memberdivision;
        this.utilization = utilization;
        this.offerDate = offerDate;
        this.offerTime = offerTime;
    }

    public String getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(String offerTime) {
        this.offerTime = offerTime;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getHtitle() {
        return htitle;
    }

    public void setHtitle(String htitle) {
        this.htitle = htitle;
    }

    public String getMemberdivision() {
        return memberdivision;
    }

    public void setMemberdivision(String memberdivision) {
        this.memberdivision = memberdivision;
    }

    public String getUtilization() {
        return utilization;
    }

    public void setUtilization(String utilization) {
        this.utilization = utilization;
    }
}


