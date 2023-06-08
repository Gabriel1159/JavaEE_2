package com.example.backend.controller;

import com.example.backend.pojo.*;
import com.example.backend.service.ProjectService;
import com.example.backend.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class TextController {
    @Autowired
    TextService textService;

    @Autowired
    ProjectService projectService;

    @PostMapping("/createText")
    public Result createText(HttpServletRequest request, HttpServletResponse response)
    {
        String msg = textService.createText(request,response);
        if(msg.equals("-1"))
        {
            Result result = new Result(false,500,"文档上传失败",null);
            return result;
        }
        Result result = new Result(true,200,"文档上传成功",msg);
        return result;
    }

    @PostMapping("/saveText")
    public Result saveText(HttpServletRequest request, HttpServletResponse response)
    {
        String msg = textService.saveText(request,response);
        if(msg.equals("0"))
        {
            return new Result(true,200,"保存成功",null);
        }
        return new Result(false,404,"保存失败",null);
    }

    @GetMapping("downloadText")
    public Result downloadText(HttpServletRequest request, HttpServletResponse response)
    {
        Text text = textService.downloadText(request,response);
        if(text.equals("-1"))
        {
            Result result = new Result(false,500,"文档下载失败",null);
            return result;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("text",text);
        Result result = new Result(true,200,"文档下载成功",text);
        return result;
    }

    @RequestMapping(value="/searchtextbyID", method= RequestMethod.POST)
    public HashMap<String, String> searchTextByID(String f_textId)
    {
        Text text = textService.searchTextByID(f_textId);
        HashMap<String, String> hm = new HashMap<>();
        hm.put("f_text", text.getF_text());
        hm.put("f_textId", text.getF_textId());
        hm.put("f_textName", text.getF_textName());
        hm.put("f_createDate", text.getF_createDate());
        hm.put("f_creator", text.getF_creator());
        hm.put("f_modifier", text.getF_modifier());
        hm.put("f_modifyDate", text.getF_modifyDate());
        hm.put("f_projectId", text.getF_projectId());
        return hm;
    }

    @RequestMapping(value="/searchtextbyPro", method= RequestMethod.POST)
    public Result searchTextByPro(String f_proID)
    {
        try {
            List list = textService.searchTextByPro(f_proID);
            return new Result(true,200,"查询成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,404,"查询失败",null);
        }
    }

    @RequestMapping(value="/addDeptText", method= RequestMethod.POST)
    public String addDeptText(int tid, String textTitle, String content)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        Text text = new Text(UUID.randomUUID().toString(), "0", textTitle, content, dateFormat.format(date), UserThreadLocal.getAccount(), dateFormat.format(date), UserThreadLocal.getAccount(), "部门介绍", tid);
        textService.createText(text);
        return text.getF_textId();
    }

    @RequestMapping(value="/addOtherText", method= RequestMethod.POST)
    public String addOtherText(int tid, String textTitle, String content)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        Text text = new Text(UUID.randomUUID().toString(), "0", textTitle, content, dateFormat.format(date), UserThreadLocal.getAccount(), dateFormat.format(date), UserThreadLocal.getAccount(), "其它文件", tid);
        textService.createText(text);
        return text.getF_textId();
    }

    @RequestMapping(value="/addDataText", method= RequestMethod.POST)
    public String addDataText(int tid, String textTitle, String content)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        Text text = new Text(UUID.randomUUID().toString(), "0", textTitle, content, dateFormat.format(date), UserThreadLocal.getAccount(), dateFormat.format(date), UserThreadLocal.getAccount(), "数据", tid);
        textService.createText(text);
        return text.getF_textId();
    }

    @RequestMapping(value="/delText", method= RequestMethod.POST)
    public int delText(String f_textId)
    {
        try {
            textService.delTextByTextID(f_textId);
            return 1;
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    @RequestMapping(value="/findTextsByTeamID", method= RequestMethod.POST)
    public ArrayList<Object> findTextsByTeamID(int tid)
    {
        ArrayList<Object> ret = new ArrayList<>();
        Text teamIntro = textService.searchTeamIntroText("团队介绍", tid);
        ret.add(teamIntro);
        ArrayList<Text> deptIntro = textService.searchTextInFolder("部门介绍", tid);
        ret.add(deptIntro);
        ArrayList<Text> dataTexts = textService.searchTextInFolder("数据", tid);
        ret.add(dataTexts);
        ArrayList<Text> otherTexts = textService.searchTextInFolder("其它文件", tid);
        ret.add(otherTexts);
//        System.out.println(ret);
        ArrayList<Folder> textsOfProjects = new ArrayList<>();
        ArrayList<Project> pros = (ArrayList<Project>) projectService.showProjectsInTeam(tid);
        if(pros==null)
        {
            textsOfProjects = null;
        }
        else {
            int numOfFolder = pros.size();

            if(numOfFolder>0)
            {
                for(Project project:pros)
                {
                    Folder folder = new Folder();
                    String s = project.getF_proName();
                    folder.setName(s);
                    folder.setID(project.getF_proID());
                    folder.setDocuments(textService.searchTextObjectByPro(project.getF_proID()));
                    textsOfProjects.add(folder);
                }
            }
        }
        ret.add(textsOfProjects);
        return ret;
    }


}
