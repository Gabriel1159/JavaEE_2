package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtoTemplate {
    private int f_tpId;
    private String f_tpName;
    private String f_tpMap;
    private int f_tpWidth;
    private int f_tpHeight;
    private int f_type = 0;

    public ProtoTemplate(String f_tpName, String f_tpMap, int f_tpWidth, int f_tpHeight) {
        this.f_tpName = f_tpName;
        this.f_tpMap = f_tpMap;
        this.f_tpWidth = f_tpWidth;
        this.f_tpHeight = f_tpHeight;
    }
}
