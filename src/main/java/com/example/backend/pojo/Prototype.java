package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prototype {
    private String f_prototypeId;
    private String f_projectId;
    private String f_prototypeName;
    private String f_prototypeMap;
    private String f_createDate;
    private String f_creator;
    private String f_modifyDate;
    private String f_modifier;
    private int f_width;
    private int f_height;
    private int f_type = 1;

    public Prototype(String f_prototypeId, String f_projectId, String f_prototypeName, String f_prototypeMap, String f_createDate, String f_creator, String f_modifyDate, String f_modifier, int f_width, int f_height) {
        this.f_prototypeId = f_prototypeId;
        this.f_projectId = f_projectId;
        this.f_prototypeName = f_prototypeName;
        this.f_prototypeMap = f_prototypeMap;
        this.f_createDate = f_createDate;
        this.f_creator = f_creator;
        this.f_modifyDate = f_modifyDate;
        this.f_modifier = f_modifier;
        this.f_width = f_width;
        this.f_height = f_height;
    }
}
