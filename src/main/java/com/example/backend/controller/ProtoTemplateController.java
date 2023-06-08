package com.example.backend.controller;

import com.example.backend.pojo.ProtoTemplate;
import com.example.backend.pojo.Prototype;
import com.example.backend.pojo.Result;
import com.example.backend.service.Impl.ProtoTemplateService;
import com.example.backend.service.PrototypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller("ProtoTemplateController")
public class ProtoTemplateController {

    @Resource
    private ProtoTemplateService protoTemplateService;

    @Resource
    private PrototypeService prototypeService;

    @PostMapping("/reqAllProtoTemplate")
    @ResponseBody
    public Result reqAllProtoTemplate()
    {
        try {
            return new Result(true,200,"请求成功",protoTemplateService.reqAllTemplate());
        }catch (Exception e)
        {
            return new Result(false,404,"数据库错误",null);
        }
    }

    @PostMapping("/addProtoTemplate")
    @ResponseBody
    public Result addProtoTemplate(HttpServletRequest req)
    {
        String protoId = req.getParameter("protoId");
        Prototype prototype = prototypeService.getPrototypeByID(protoId);
        if(prototype != null)
        {
            ProtoTemplate template = new ProtoTemplate(prototype.getF_prototypeName(),prototype.getF_prototypeMap(),prototype.getF_width(),prototype.getF_height());
            if(!protoTemplateService.exist(template))
            {
                protoTemplateService.insertTemplate(template);
                return new Result(true,200,"添加新模板成功",template.getF_tpId());
            }
            return new Result(false,403,"模板已存在",-1);
        }
        return new Result(false,403,"原型不存在",-2);
    }

    @PostMapping("/delProtoTemplate")
    @ResponseBody
    public Result delProtoTemplate(HttpServletRequest req)
    {
        String tpId = req.getParameter("tpId");
        int templateId;
        try {
            templateId = Integer.parseInt(tpId);
        }catch (Exception e)
        {
            return new Result(false,403,"参数错误",-1);
        }
        if(protoTemplateService.reqTemplateBY_I(templateId) == null)
        {
            return new Result(false,403,"目标模板不存在",-2);
        }
        try {
            protoTemplateService.delTemplate(templateId);
            return new Result(true,200,"删除成功",0);
        }catch (Exception e)
        {
            return new Result(false,403,"数据库错误",-3);
        }
    }
}
