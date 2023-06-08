package com.example.backend.service.Impl;

import com.example.backend.mapper.ProtoTemplateMapper;
import com.example.backend.pojo.ProtoTemplate;
import com.example.backend.service.ProtoTemplateServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ProtoTemplateService implements ProtoTemplateServiceIF {

    @Resource
    private ProtoTemplateMapper protoTemplateMapper;

    @Override
    public ArrayList<ProtoTemplate> reqAllTemplate() {
        return protoTemplateMapper.reqAllTemplate();
    }

    @Override
    public int insertTemplate(ProtoTemplate protoTemplate) {
        return protoTemplateMapper.insertTemplate(protoTemplate);
    }

    @Override
    public int delTemplate(int tpId) {
        return protoTemplateMapper.delTemplate(tpId);
    }

    @Override
    public ProtoTemplate reqTemplateBY_I(int tpId) {
        return protoTemplateMapper.reqTemplateBY_I(tpId);
    }

    @Override
    public boolean exist(ProtoTemplate protoTemplate) {
        return protoTemplateMapper.exist(protoTemplate) == 1;
    }


}
