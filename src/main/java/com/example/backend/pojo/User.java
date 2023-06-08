package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    private List<Topic> topicList;
    private String u_id;
    private String mail = "";
    private int f_uid;
    private String f_userName = "";
    private String f_realName = "";
    private String f_account = "";
    private String f_pwd = "";
    private String f_location = "";
    private String f_mail = "";
    private String f_phone = "";
    private String f_sex = "N";
    private String f_groupStr = "[]";
    private Date f_birth = new Date();
    private java.sql.Date f_birth_D = new java.sql.Date(0);

    public User(String userName,String realName,String pwd) {
        this.f_userName = userName;
        this.f_realName = realName;
        this.f_pwd = pwd;
    }
    public User(String userName,String pwd)
    {
        this.f_userName = userName;
        this.f_pwd = pwd;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setF_uid(int f_uid) {
        this.f_uid = f_uid;
    }

    public void setF_userName(String f_userName) {
        this.f_userName = f_userName;
    }

    public void setF_realName(String f_realName) {
        this.f_realName = f_realName;
    }

    public void setF_account(String f_account) {
        this.f_account = f_account;
    }

    public void setF_pwd(String f_pwd) {
        this.f_pwd = f_pwd;
    }

    public void setF_location(String f_location) {
        this.f_location = f_location;
    }

    public void setF_mail(String f_mail) {
        this.f_mail = f_mail;
    }

    public void setF_phone(String f_phone) {
        this.f_phone = f_phone;
    }

    public void setF_sex(String f_sex) {
        this.f_sex = f_sex;
    }

    public void setF_groupStr(String f_groupStr) {
        this.f_groupStr = f_groupStr;
    }

    public void setF_birth(Date birth) {
        this.f_birth = birth;
        this.f_birth_D = new java.sql.Date(birth.getTime());
    }

    public void setF_birth_D(java.sql.Date f_birth_D) {
        this.f_birth_D = f_birth_D;
    }

    public int getF_uid() {
        return f_uid;
    }

    public String getF_userName() {
        return f_userName;
    }

    public String getF_realName() {
        return f_realName;
    }

    public String getF_account() {
        return f_account;
    }

    public String getF_pwd() {
        return f_pwd;
    }

    public String getF_location() {
        return f_location;
    }

    public String getF_mail() {
        return f_mail;
    }

    public String getF_phone() {
        return f_phone;
    }

    public String getF_sex() {
        return f_sex;
    }

    public String getF_groupStr() {
        return f_groupStr;
    }

    public Date getF_birth() {
        return f_birth;
    }

    public java.sql.Date getF_birth_D() {
        return f_birth_D;
    }
}
