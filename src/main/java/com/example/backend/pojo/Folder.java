package com.example.backend.pojo;

import java.util.ArrayList;

public class Folder {
    private String name;
    private String ID;
    private ArrayList<Text> documents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<Text> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Text> documents) {
        this.documents = documents;
    }
}
