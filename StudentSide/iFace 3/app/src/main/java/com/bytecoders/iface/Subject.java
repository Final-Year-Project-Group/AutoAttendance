package com.bytecoders.iface;

public class Subject {
    private String sub_name,sub_code,tech_email,tech_name,sub_id;
    public Subject(){}

    public Subject(String sub_id,String sub_name, String sub_code, String tech_name,String tech_email) {
        this.sub_id=sub_id;
        this.sub_name = sub_name;
        this.sub_code = sub_code;
        this.tech_email = tech_email;
        this.tech_name=tech_name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getTech_email() {
        return tech_email;
    }

    public void setTech_email(String tech_email) {
        this.tech_email = tech_email;
    }

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }
}

