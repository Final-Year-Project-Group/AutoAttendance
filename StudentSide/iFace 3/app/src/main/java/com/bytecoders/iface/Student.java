package com.bytecoders.iface;

import java.io.Serializable;

public class Student implements Serializable {

    private String std_id,email,password,fname,lname,sub1,sub2,sub3;
    private int rollno,sem;

    public Student() {

    }

    public Student(String std_id,String email,String password,String fname, String lname, int rollno, int sem,String sub1,String sub2,String sub3) {
        this.std_id=std_id;
        this.fname = fname;
        this.lname = lname;
        this.rollno = rollno;
        this.sem = sem;
        this.email=email;
        this.password=password;
        this.sub1=sub1;
        this.sub2=sub2;
        this.sub3=sub3;
    }

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public String getSub1() {
        return sub1;
    }

    public void setSub1(String sub1) {
        this.sub1 = sub1;
    }

    public String getSub2() {
        return sub2;
    }

    public void setSub2(String sub2) {
        this.sub2 = sub2;
    }

    public String getSub3() {
        return sub3;
    }

    public void setSub3(String sub3) {
        this.sub3 = sub3;
    }

//    int CD=0;
//    int CET=0;
//    int EC=0;
//    int EH=0;
//    int OOAD=0;
//    int WT=0;
//
//    public int getCD() {
//        return CD;
//    }
//
//    public int getCET() {
//        return CET;
//    }
//
//    public int getEC() {
//        return EC;
//    }
//
//    public int getEH() {
//        return EH;
//    }
//
//    public int getOOAD() {
//        return OOAD;
//    }
//
//    public int getWT() {
//        return WT;
//    }
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

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
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


//public class Student {
//    int CD=0;
//    int CET=0;
//    int EC=0;
//    int EH=0;
//    int OOAD=0;
//    int WT=0;
//    Student()
//    {
//
//    }
//
//    public int getCD() {
//        return CD;
//    }
//
//    public int getCET() {
//        return CET;
//    }
//
//    public int getEC() {
//        return EC;
//    }
//
//    public int getEH() {
//        return EH;
//    }
//
//    public int getOOAD() {
//        return OOAD;
//    }
//
//    public int getWT() {
//        return WT;
//    }
//}
