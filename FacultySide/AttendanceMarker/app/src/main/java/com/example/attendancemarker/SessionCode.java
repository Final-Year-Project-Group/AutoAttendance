package com.example.attendancemarker;

public class SessionCode {
    private String class_code,code,pattern_code,start_time,end_time,date;
    public SessionCode(){}
    public SessionCode(String class_code, String code,String pattern_code,String start_time,String end_time,String date) {
        this.class_code = class_code;
        this.code = code;
        this.pattern_code=pattern_code;
        this.start_time=start_time;
        this.end_time=end_time;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPattern_code() {
        return pattern_code;
    }

    public void setPattern_code(String pattern_code) {
        this.pattern_code = pattern_code;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}

