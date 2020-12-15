package com.example.attendancemarker;

import java.io.Serializable;

public class Teacher implements Serializable {

    private String email,password,affination,fname,lname;

    public Teacher() {

    }

    public Teacher(String affination, String email,String password,String fname, String lname) {
        this.affination = affination;
        this.fname = fname;
        this.lname = lname;
        this.email=email;
        this.password=password;
    }

    public String getAffination() {
        return affination;
    }

    public void setAffination(String affination) {
        this.affination = affination;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}


