package com.example.backend.controller;

import com.example.backend.pojo.*;
import com.example.backend.service.Impl.TeamService;
import com.example.backend.service.Impl.UserService;
import com.example.backend.service.ProjectService;
import com.example.backend.service.PrototypeService;
import com.example.backend.service.TextService;
import com.example.backend.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    PrototypeService prototypeService;

    @Autowired
    TextService textService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<Project> showProjects() {
        return projectService.showProjects();
    }

    @RequestMapping(value = "/searchprojectbyproID", method = RequestMethod.POST)
    public HashMap<String, String> searchProjectByProID(String proID) {
        Project project = projectService.searchProjectByProID(proID);
        HashMap<String, String> hm = new HashMap<>();
        hm.put("f_proID", project.getF_proID());
        hm.put("f_proDescription", project.getF_proDescription());
        hm.put("f_proCreator", project.getF_proCreator());
        hm.put("f_proName", project.getF_proName());
        hm.put("f_proStatus", String.valueOf(project.getF_proStatus()));
        hm.put("f_teamID", String.valueOf(project.getF_teamID()));
        hm.put("f_createDate", project.getCreateDate());
        return hm;
    }

    @RequestMapping(value = "/projectsdustbin", method = RequestMethod.GET)
    public List<Project> showProjectsDustbin() {
        return projectService.showProjectsDustbin();
    }

    @RequestMapping(value = "/addpros", method = RequestMethod.POST)
    public HashMap<String, String> addProjects(String f_proName, String f_proDescription, String f_teamID, HttpServletRequest req) {
        HashMap<String, String> hm = new HashMap<>();
        String f_proID = UUID.randomUUID().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        Date date = new Date();
//        System.out.println(dateFormat.format(date));
        Project project = new Project(f_proName, f_proDescription, f_proID, UserThreadLocal.getAccount(), dateFormat.format(date));
        int teamID = Integer.parseInt(f_teamID);
        project.setF_teamID(teamID);
        try {
            Team team = teamService.findTeamBYI(teamID);
            String slist = team.getF_pids();
            slist = StrUtil.addId(slist, f_proID);
            team.setF_pids(slist);
            teamService.updateTeam(team);
            String ret = String.valueOf(projectService.addProject(project));
            if (!ret.equals("-1")) {
                ret = f_proID;
            }
            hm.put("projectId", ret);
            hm.put("createDate", dateFormat.format(date));
            return hm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/renamepros", method = RequestMethod.POST)
    public int renameProjects(String newName, String proID) {
        int ret = projectService.findProByID(proID);
//        System.out.println("ret1 is "+ret);
        if (ret == 0)           //ID not existed
        {
            return -1;
        }
        ret = projectService.checkName(newName, proID);
//        System.out.println("ret2 is "+ret);
        if (ret != 0)          //new name and old name are the same
        {
            return -2;
        }
        return projectService.renameProject(newName, proID);
    }

    @RequestMapping(value = "/reDescribePros", method = RequestMethod.POST)
    public Result reDescribeProjects(String newDescribe, String proID) {
        int ret = projectService.findProByID(proID);
//        System.out.println("ret1 is "+ret);
        if (ret == 0)           //ID not existed
        {
            return new Result(false, 403, "更新失败", -1);
        }
        ret = projectService.checkDescribe(newDescribe, proID);
//        System.out.println("ret2 is "+ret);
        if (ret != 0)          //new name and old name are the same
        {
            return new Result(false, 403, "更新失败", -2);
        }
        return new Result(true, 200, "更新成功", projectService.reDescribeProject(newDescribe, proID));

    }

    @RequestMapping(value = "/findTeamIDbyProID", method = RequestMethod.POST)
    public int findTeamIDbyProID(String proID) {
        String ID = proID;
        int ret = projectService.findProByID(ID);
//        System.out.println("ret1 is "+ret);
        if (ret == 0)           //ID not existed
        {
            return -1;
        }
        return projectService.findTeamIDbyProID(ID);
    }

    @RequestMapping(value = "/delpros", method = RequestMethod.POST)
    public int deleteProjects(String proID) {
        int ret = projectService.findProByIDNotDeleted(proID);
        if (ret == 0) {
            return -1;
        }
        Team team = teamService.findTeamBYI(projectService.findTeamIDbyProID(proID));
        team.setF_pids(StrUtil.delId(team.getF_pids(), proID));
        team.setF_Npids(StrUtil.addId(team.getF_Npids(), proID));
        teamService.updateTeam(team);
        prototypeService.delPrototype(proID);
        textService.deleteText(proID);
        textService.delText(proID);
        return projectService.deleteProject(proID);
    }

    @RequestMapping(value = "/dustbintoprojects", method = RequestMethod.POST)
    public int dustbinToProject(String proID) {
        int ret = projectService.findProByIDDeleted(proID);
        if (ret == 0) {
            return -1;
        }
        Team team = teamService.findTeamBYI(projectService.findTeamIDbyProID(proID));
        team.setF_pids(StrUtil.addId(team.getF_pids(), proID));
        team.setF_Npids(StrUtil.delId(team.getF_Npids(), proID));
        teamService.updateTeam(team);
        prototypeService.recoverPrototype(proID);
        prototypeService.delPrototypeInDustbin(proID);
        textService.recoverText(proID);
        textService.delTextInDustbin(proID);
        return projectService.dustbinToProject(proID);
    }

    @RequestMapping(value = "/delprosindustbin", method = RequestMethod.POST)
    public int deleteProjectsInDustbin(String proID) {
        int ret = projectService.findProByIDDeleted(proID);
        if (ret == 0) {
            return -1;
        }
        prototypeService.deletePrototypeInDustbin(proID);
        textService.deleteTextInDustbin(proID);
        Team team = teamService.findTeamBYI(projectService.findTeamIDbyProID(proID));
        team.setF_Npids(StrUtil.delId(team.getF_Npids(), proID));
        teamService.updateTeam(team);
        return projectService.deleteProjectsInDustbin(proID);
    }

    //below are second needs
    @RequestMapping(value = "/sortProject", method = RequestMethod.POST)
    public ArrayList<Project> sortProject(int sortMethod) {
        ArrayList<Project> ret = (ArrayList<Project>) projectService.showProjects();
        if (sortMethod == 1) {
            ret.sort(new Comparator<Project>() {
                @Override
                public int compare(Project o1, Project o2) {
                    Project p1 = (Project) o1;
                    Project p2 = (Project) o2;
                    return p1.getCreateDate().compareTo(p2.getCreateDate());
                }
            });
            System.out.println(ret);
        } else {
            ret.sort(new Comparator<Project>() {
                @Override
                public int compare(Project o1, Project o2) {
                    Project p1 = (Project) o1;
                    Project p2 = (Project) o2;
                    return p2.getCreateDate().compareTo(p1.getCreateDate());
                }
            });
            System.out.println(ret);
        }
        return ret;
    }

    @RequestMapping(value = "/copyProject", method = RequestMethod.POST)
    public Project copyProject(String projectId) {
        Project project = projectService.getProjectByID(projectId);
        String pNewName = generateNewProjectName(projectId);
        project.setF_proName(pNewName);
        project.setF_proID(UUID.randomUUID().toString());
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//        System.out.println(dateFormat.format(date));
        project.setCreateDate(dateFormat.format(date));
        projectService.addProject(project);
        // 处理与项目相关的文档和原型
        ArrayList<String> texts = textService.searchTextByPro(projectId);
        if (!texts.isEmpty()) {
            for (String s : texts) {
                Text text = textService.searchTextByID(s);
                text.setF_textId(UUID.randomUUID().toString());
                String copyName = generateNewTextName(s);
                text.setF_textName(copyName);
                text.setF_createDate(dateFormat.format(date));
                text.setF_modifyDate(dateFormat.format(date));
                text.setF_projectId(project.getF_proID());
                text.setF_creator(UserThreadLocal.getAccount());
                text.setF_modifier(UserThreadLocal.getAccount());
                textService.createText(text);
            }
        }

        ArrayList<Prototype> prototypes = prototypeService.getPrototypeByProjectID(projectId);
        if (!prototypes.isEmpty()) {
            for (Prototype p : prototypes) {
                p.setF_projectId(p.getF_prototypeId());
                p.setF_creator(UserThreadLocal.getAccount());
                p.setF_modifier(UserThreadLocal.getAccount());
                p.setF_createDate(dateFormat.format(date));
                p.setF_modifyDate(dateFormat.format(date));
                p.setF_prototypeId(UUID.randomUUID().toString());
                String newPrototypeName = generateNewPrototypeName(p.getF_prototypeId());
                p.setF_prototypeName(newPrototypeName);
                prototypeService.addPrototype(p);
            }
        }

        // 修改team的pids
        int teamID = project.getF_teamID();
        Team team = teamService.findTeamBYI(teamID);
        String slist = team.getF_pids();
        slist = StrUtil.addId(slist, project.getF_proID());
        team.setF_pids(slist);
        teamService.updateTeam(team);

        return project;
    }

    private String generateNewProjectName(String projectId) {
        Project project = projectService.getProjectByID(projectId);
        String pName = project.getF_proName();
        String name = "";
        try {
            name = pName.split(" - ")[0];
        } catch (Exception e) {
            name = pName;
        };
        System.out.println(name);
        int n = projectService.accurateFind(name).size();
        return StrUtil.generateCopyName(name, n);
    }

    private String generateNewTextName(String textID) {
        Text text = textService.searchTextByID(textID);
        String tName = text.getF_textName();
        String name = "";
        try {
            name = tName.split(" - ")[0];
        } catch (Exception e) {
            name = tName;
        };
        System.out.println(name);
        int n = textService.accurateFind(name).size();
        return StrUtil.generateCopyName(name, n);
    }

    private String generateNewPrototypeName(String prototypeId) {
        Prototype prototype = prototypeService.getPrototypeByID(prototypeId);
        String pName = prototype.getF_prototypeName();
        String name = "";
        try {
            name = pName.split(" - ")[0];
        } catch (Exception e) {
            name = pName;
        };
        System.out.println(name);
        int n = prototypeService.accurateFind(name).size();
        return StrUtil.generateCopyName(name, n);
    }

    @RequestMapping(value = "/searchProject", method = RequestMethod.POST)
    public ArrayList<Project> searchProject(String searchText) {
        return projectService.vagueFind(searchText);
    }
}