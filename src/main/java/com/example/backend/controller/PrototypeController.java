package com.example.backend.controller;

import com.example.backend.pojo.Project;
import com.example.backend.pojo.Prototype;
import com.example.backend.pojo.Result;
import com.example.backend.service.DownloadService;
import com.example.backend.service.PrototypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PrototypeController {
    @Autowired
    DownloadService downloadService;
    @Autowired
    PrototypeService prototypeService;

    @RequestMapping("/downloadPrototype")
    public Result downloadPrototype(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String,Object>  map = prototypeService.downloadPrototype(request,response);
        if(map!=null)
        {
            return new Result(true,200,"原型下载成功",map);
        }
        return new Result(false,404,"下载原型失败",null);
    }

    @PostMapping("/savePrototype")
    public Result savePrototype(HttpServletRequest request, HttpServletResponse response)
    {
        String msg = prototypeService.savePrototype(request,response);
        if(msg.equals("0"))
        {
            return new Result(true,200,"保存成功",null);
        }
        return new Result(false,404,"保存失败",null);
    }

    @PostMapping("/createPrototype")
    public Result createPrototype(HttpServletRequest request, HttpServletResponse response)
    {
        String msg = prototypeService.createPrototype(request,response);
        if(!msg.equals("-1"))
        {
            return new Result(true,200,"创建成功",msg);
        }
        return new Result(false,404,"创建失败",null);
    }

    @GetMapping("/getPrototypes")
    public Result getPrototypes(HttpServletRequest request, HttpServletResponse response)
    {
        List list = prototypeService.getPrototypes(request,response);
        if(list!=null)
        {
            return new Result(true,200,"原型获取成功",list);
        }
        return new Result(false,404,"原型获取失败",null);
    }
    @GetMapping("/getProjectByPrototype")
    public Result getProjectByPrototype(HttpServletRequest request, HttpServletResponse response)
    {
        HashMap<String, String> msg = prototypeService.getProjectByPrototype(request,response);
        if(msg.equals("-1"))
        {
            return new Result(false,404,"获取失败",null);
        }
        return new Result(true,200,"原型获取成功",msg);
    }

    @RequestMapping(value = "/getPrototypeByID", method = RequestMethod.POST)
    public Prototype getPrototypeByID(String prototypeId) {
        return prototypeService.getPrototypeByID(prototypeId);
    }
}
