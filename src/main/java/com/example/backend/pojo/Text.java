package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Text {
    private String f_textId;
    private String f_projectId;
    private String f_textName;
    private String f_text;
    private String f_createDate;
    private String f_creator;
    private String f_modifyDate;
    private String f_modifier;
    private String f_folderLabel;
    private int f_teamID;

    public Text(String f_textId, String f_projectId, String f_textName, String f_text, String f_createDate, String f_creator, String f_modifyDate, String f_modifier, String f_folderLabel) {
        this.f_textId = f_textId;
        this.f_projectId = f_projectId;
        this.f_textName = f_textName;
        this.f_text = f_text;
        this.f_createDate = f_createDate;
        this.f_creator = f_creator;
        this.f_modifyDate = f_modifyDate;
        this.f_modifier = f_modifier;
        this.f_folderLabel = f_folderLabel;
    }

    public Text(String f_textId, String f_projectId, String f_textName, String f_text, String f_createDate, String f_creator, String f_modifyDate, String f_modifier, int f_teamID) {
        this.f_textId = f_textId;
        this.f_projectId = f_projectId;
        this.f_textName = f_textName;
        this.f_text = f_text;
        this.f_createDate = f_createDate;
        this.f_creator = f_creator;
        this.f_modifyDate = f_modifyDate;
        this.f_modifier = f_modifier;
        this.f_teamID = f_teamID;
    }
}
