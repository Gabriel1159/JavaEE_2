package com.example.backend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.mapper.ProjectMapper;
import com.example.backend.mapper.PrototypeMapper;
import com.example.backend.mapper.TeamMapper;
import com.example.backend.pojo.Prototype;
import com.example.backend.pojo.Result;
import com.example.backend.pojo.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrototypeService {
    @Autowired
    PrototypeMapper prototypeMapper;

    @Autowired
    ProjectMapper projectMapper;
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
    public String createPrototype(HttpServletRequest request, HttpServletResponse response)
    {
        int f_width = Integer.valueOf(request.getParameter("f_width"));
        int f_height = Integer.valueOf(request.getParameter("f_height"));
        String f_prototypeName = request.getParameter("f_prototypeName");
        int ret = prototypeMapper.searchPrototypeByName(f_prototypeName);
        if(ret!=0)
        {
            return "-1";
        }
        String f_projectId = request.getParameter("f_projectId");
        UUID uuid = UUID.randomUUID();
        String f_prototypeId = uuid.toString();
        String account = UserThreadLocal.getAccount();
        Date date = new Date();
        Prototype prototype = new Prototype(f_prototypeId,f_projectId,f_prototypeName,"empty",dateFormat.format(date),account,dateFormat.format(date),account,f_width,f_height);
        try {
            prototypeMapper.creatPrototype(prototype);
            return prototype.getF_prototypeId();
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }

    }

    public String savePrototype(HttpServletRequest request, HttpServletResponse response)
    {
        String f_prototypeId = request.getParameter("f_prototypeId");
        String f_prototypeMap = request.getParameter("f_prototypeMap");
        try {
            prototypeMapper.savePrototype(f_prototypeId,f_prototypeMap);
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public Map<String, Object> downloadPrototype(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            String f_prototypeId = request.getParameter("f_prototypeId");
            Prototype prototype = prototypeMapper.selectPrototype(f_prototypeId);
            if(prototype==null)
            {
                return null;
            }
            String prototypeMap = prototype.getF_prototypeMap();
            if(prototypeMap==null)
            {
                return null;
            }
            List list = new ArrayList<>();
            if(prototypeMap.startsWith("["))
            {
                JSONArray jsonArray = JSONArray.parseArray(prototypeMap);

                for (int i = 0; i < jsonArray.size(); i++) {//遍历操作转化json对象
                    Object tmp = jsonArray.get(i);
                    JSONObject jsonObject =JSONObject.parseObject(tmp.toString());
                    list.add(jsonObject);
                }
            }
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("width",prototype.getF_width());
            map.put("height",prototype.getF_height());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List getPrototypes(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            String f_projectId = request.getParameter("f_projectId");
            List<Prototype> list = prototypeMapper.getPrototypes(f_projectId);
//            if(list.size()==0||list==null)
//            {
//                //111是默认原型模板，数据库不能删也不能改
//                list = prototypeMapper.getPrototypes("111");
//            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public int deletePrototype(String proID) {
        try {
            prototypeMapper.deletePrototype(proID);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void recoverPrototype(String proID) {
        try {
            prototypeMapper.recoverPrototype(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePrototypeInDustbin(String proID) {
        try {
            prototypeMapper.deletePrototypeInDustbin(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delPrototype(String proID) {
        try {
            prototypeMapper.delPrototype(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delPrototypeInDustbin(String proID) {
        try {
            prototypeMapper.delPrototypeInDustbin(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getProjectByPrototype(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            HashMap<String, String> hm = new HashMap<>();
            String f_prototypeId = request.getParameter("f_prototypeId");
            String projectId = prototypeMapper.getProjectByPrototype(f_prototypeId);
            int teamId = projectMapper.findTeamIDbyProID(projectId);
            hm.put("projectId", f_prototypeId);
            hm.put("teamId", String.valueOf(teamId));
            return hm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Prototype> getPrototypeByProjectID(String projectId) {
        try {
            return prototypeMapper.getPrototypeByProjectID(projectId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPrototype(Prototype p) {
        try {
            prototypeMapper.addPrototype(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Prototype getPrototypeByID(String prototypeId) {
        try {
            return prototypeMapper.getPrototypeByID(prototypeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Prototype> accurateFind(String name) {
        try {
            return prototypeMapper.accurateFind(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
