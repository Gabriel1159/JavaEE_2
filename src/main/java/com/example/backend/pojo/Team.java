package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private int f_tid = 0;
    private String f_tName = "";
    private int f_bid = 0;
    private String f_superUserStr = "[]";
    private String f_userStr = "[]";
    private String f_pids = "[]";
    private String f_Npids = "[]";

    public Team(String tName, int bid, String userStr) {
        this.f_tName = tName;
        this.f_bid = bid;
        this.f_userStr = userStr;
    }

    public Team(String tName, int bid) {
        this.f_tName = tName;
        this.f_bid = bid;
    }

    public int getTid() {
        return f_tid;
    }

    public void setF_userStr(String f_userStr) {
        this.f_userStr = f_userStr;
    }

    public void setF_superUserStr(String f_superUserStr) {
        this.f_superUserStr = f_superUserStr;
    }

    public String gettName() {
        return f_tName;
    }

    public Integer getBid() {
        return f_bid;
    }

    public String getSuperUserStr() {
        return f_superUserStr;
    }

    public String getUserStr() {
        return f_userStr;
    }

    public String getF_pids() {
        return f_pids;
    }

    public void setF_pids(String f_pids) {
        this.f_pids = f_pids;
    }

    public String getF_Npids() {
        return f_Npids;
    }

    public void setF_Npids(String f_Npids) {
        this.f_Npids = f_Npids;
    }

    public String getF_tName() {
        return f_tName;
    }

    public void setF_tName(String f_tName) {
        this.f_tName = f_tName;
    }
}
