package com.example.backend.pojo;

import java.util.Date;

public class Comment {
    private String c_id;
    private String c_tid;
    private String c_uid;
    private String c_photo;
    private Date c_time;
    private String c_content;

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_tid() {
        return c_tid;
    }

    public void setC_tid(String c_tid) {
        this.c_tid = c_tid;
    }

    public String getC_uid() {
        return c_uid;
    }

    public void setC_uid(String c_uid) {
        this.c_uid = c_uid;
    }

    public String getC_photo() {
        return c_photo;
    }

    public void setC_photo(String c_photo) {
        this.c_photo = c_photo;
    }

    public Date getC_time() {
        return c_time;
    }

    public void setC_time(Date c_time) {
        this.c_time = c_time;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }
}
