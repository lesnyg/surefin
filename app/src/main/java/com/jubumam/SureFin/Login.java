package com.jubumam.SureFin;

public class Login {
    private static String phone;
    private static String responsibility;
    private static Login login;

    public Login() {
    }

    public Login(String phone, String responsibility) {
        this.phone = phone;
        this.responsibility = responsibility;
    }

    static Login getInstance(){
        if(login==null) {
            login = new Login(phone, responsibility);
        }
        return login;
    }

    public String getPersonId() {
        return phone;
    }

    public void setPersonId(String personId) {
        this.phone = personId;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
}
