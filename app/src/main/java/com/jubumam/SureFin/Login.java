package com.jubumam.SureFin;

import java.util.List;

public class Login {
    private static String personId;     //직원코드
    private static String phone;        //직원전화번호
    private static String responsibility;   //직원이름
    private static String department;   //직원부서
    private static Login login;

    public Login() {
    }

    public Login(String phone, String responsibility) {
        this.phone = phone;
        this.responsibility = responsibility;
    }

    public Login(String personId, String phone, String responsibility, String department) {
        this.personId = personId;
        this.phone = phone;
        this.responsibility = responsibility;
        this.department = department;
    }

    public static Login getInstance() {
        if (login == null) {
            login = new Login(personId, phone, responsibility, department);
        }
        return login;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        Login.department = department;
    }
}
