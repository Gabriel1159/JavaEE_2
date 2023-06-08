package com.example.backend.mapper;

import com.example.backend.pojo.ProtoTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ProtoTemplateMapper {
    public ArrayList<ProtoTemplate> reqAllTemplate();
    public int insertTemplate(ProtoTemplate protoTemplate);
    public int delTemplate(@Param("tpId") int tpId);
    public ProtoTemplate reqTemplateBY_I(@Param("tpId") int tpId);
    public int exist(ProtoTemplate protoTemplate);
}
