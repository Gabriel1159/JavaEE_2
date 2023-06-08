package com.example.backend.pojo;

import com.mysql.cj.conf.PropertyDefinitions;

import java.util.ArrayList;
import java.util.Date;

public class Project {
    private String f_proName;
    private String f_proDescription;
    private String f_proID;
    private String f_proCreator;
    private short f_proStatus;

    private int f_teamID;

    private ArrayList<String> f_texts;

    private String f_createDate;

    /*  f_proStatus==0   normal*/
    /*  f_proStatus==1   deleted but in trash-bin*/

    public Project(String f_proName, String f_proDescription, String f_proID, String f_proCreator, String createDate) {
        this.f_proName = f_proName;
        this.f_proDescription = f_proDescription;
        this.f_proID = f_proID;
        this.f_proCreator = f_proCreator;
        this.f_createDate = createDate;
    }

    public String getF_proName() {
        return f_proName;
    }

    public void setF_proName(String f_proName) {
        this.f_proName = f_proName;
    }

    public String getF_proDescription() {
        return f_proDescription;
    }

    public void setF_proDescription(String f_proDescription) {
        this.f_proDescription = f_proDescription;
    }

    public String getF_proID() {
        return f_proID;
    }

    public void setF_proID(String f_proID) {
        this.f_proID = f_proID;
    }

    public String getF_proCreator() {
        return f_proCreator;
    }

    public void setF_proCreator(String f_proCreator) {
        this.f_proCreator = f_proCreator;
    }

    public short getF_proStatus() {
        return f_proStatus;
    }

    public void setF_proStatus(short f_proStatus) {
        this.f_proStatus = f_proStatus;
    }

    public int getF_teamID() {
        return f_teamID;
    }

    public void setF_teamID(int f_teamID) {
        this.f_teamID = f_teamID;
    }

    public ArrayList<String> getF_texts() {
        return f_texts;
    }

    public void setF_texts(ArrayList<String> f_texts) {
        this.f_texts = f_texts;
    }

    public String getCreateDate() {
        return f_createDate;
    }

    public void setCreateDate(String createDate) {
        this.f_createDate = createDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "f_proName='" + f_proName + '\'' +
                ", f_proDescription='" + f_proDescription + '\'' +
                ", f_proID='" + f_proID + '\'' +
                ", f_proCreator='" + f_proCreator + '\'' +
                ", f_proStatus=" + f_proStatus +
                ", f_teamID=" + f_teamID +
                '}';
    }
}
