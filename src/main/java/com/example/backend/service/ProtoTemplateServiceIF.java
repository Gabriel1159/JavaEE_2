package com.example.backend.service;

import com.example.backend.pojo.ProtoTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ProtoTemplateServiceIF {
    public ArrayList<ProtoTemplate> reqAllTemplate();
    public int insertTemplate(ProtoTemplate protoTemplate);
    public int delTemplate(int tpId);
    public ProtoTemplate reqTemplateBY_I(int tpId);
    public boolean exist(ProtoTemplate protoTemplate);
}
