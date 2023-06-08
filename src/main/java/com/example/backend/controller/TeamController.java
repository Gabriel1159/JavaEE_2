package com.example.backend.controller;

import com.example.backend.pojo.*;
import com.example.backend.service.TextService;
import com.example.backend.util.ContainerGetter;
import com.example.backend.service.Impl.TeamService;
import com.example.backend.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.backend.util.StrUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Controller("TeamController")
public class TeamController {

    @Resource
    private TeamService teamService;

    @Autowired
    private TextService textService;


    //通过bid和tName创建team
    @PostMapping("/teamRegister")
    @ResponseBody
    public int teamRegister(HttpServletRequest req, HttpServletResponse rsp)
    {
        String bid = req.getParameter("bid");
        String tName = req.getParameter("tName");
        if(checkStyle(bid,tName))
        {
            int bossId = Integer.parseInt(bid);
            Team team = new Team(tName,bossId,"["+bid+"]");
            team.setF_superUserStr("["+bid+"]");
            int teamId = -1;
            if(teamService.addTeam(team) != -1)
            {
                teamId = team.getTid();
                UserService userService = ContainerGetter.getUserService();
                User user = userService.findUserBYU(bossId);
                user.setF_groupStr(StrUtil.addId(user.getF_groupStr(),teamId));
                userService.updateUser(user);
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
                // 创建团队介绍文件，三个空文件夹自动创建
                String defaultText = "<h1>团队介绍</h1><p>请在这里写下团队的介绍内容……</p >";
                Text text = new Text(UUID.randomUUID().toString(), "0", "团队介绍", defaultText, dateFormat.format(date), UserThreadLocal.getAccount(), dateFormat.format(date), UserThreadLocal.getAccount(), "团队介绍", teamId);
                textService.createText(text);
            }
            return teamId;

        }
        return -1;
    }



    //通过tid全局查找team信息
    @PostMapping("/teamFindBYI_Global")
    @ResponseBody
    public HashMap<String,String> teamFindBYI_Global(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        try {
            System.out.println(teamService.findTeamBYI(Integer.parseInt(tid)));
            Team team =  teamService.findTeamBYI(Integer.parseInt(tid));
            return StrUtil.teamMap(team);
        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }

    }

    //通过tName全局查找team信息
    @PostMapping("/teamFindBYN_Global")
    @ResponseBody
    public HashMap<String,String> teamFindBYN_Global(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tName = req.getParameter("tName");
        try {
            Team team =  teamService.findTeamBYN(tName);
            return StrUtil.teamMap(team);
        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }

    }

    //通过uid查找team内指定成员信息
    @PostMapping("/normalUserFindBYI")
    @ResponseBody
    public HashMap<String,String> normalUserFindBYI(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        try {
            Team team =  teamService.findTeamBYI(Integer.parseInt(tid));
            int id = Integer.parseInt(uid);
            String userStr = team.getUserStr();
            ArrayList<Integer> userIDs = StrUtil.analyzeStr(userStr);
            for(Integer i : userIDs)
            {
                if(i == id)
                {
                    User user =  ContainerGetter.getUserService().findUserBYU(id);
                    return StrUtil.userMap(user);
                }
            }
        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }
        return new HashMap<>();
    }


    //通过account查找team内指定成员信息
    @PostMapping("/normalUserFindBYA")
    @ResponseBody
    public HashMap<String,String> normalUserFindBYN(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String account = req.getParameter("account");
        try {
            Team team =  teamService.findTeamBYI(Integer.parseInt(tid));
            String userStr = team.getUserStr();
            ArrayList<Integer> userIDs = StrUtil.analyzeStr(userStr);
            UserService userService = ContainerGetter.getUserService();
            User user = userService.findUserBYA(account);
            if( user != null)
            {
                for(Integer i : userIDs)
                {
                    if(userService.findUserBYU(i).getF_account().equals(account))
                    {
                        return StrUtil.userMap(user);
                    }
                }
            }

        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }
        return new HashMap<>();
    }


    //通过uid查找team内指定管理员信息
    @PostMapping("/superUserFindBYI")
    @ResponseBody
    public HashMap<String,String> superUserFindBYI(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        try {
            Team team =  teamService.findTeamBYI(Integer.parseInt(tid));
            int id = Integer.parseInt(uid);
            String userStr = team.getSuperUserStr();
            ArrayList<Integer> userIDs = StrUtil.analyzeStr(userStr);
            for(Integer i : userIDs)
            {
                if(i == id)
                {
                    return StrUtil.userMap(ContainerGetter.getUserService().findUserBYU(id));
                }
            }
        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }
        return new HashMap<>();
    }

    //通过account查找team内指定管理员信息
    @PostMapping("/superUserFindBYA")
    @ResponseBody
    public HashMap<String,String> superUserFindBYN(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String account = req.getParameter("account");
        try {
            Team team =  teamService.findTeamBYI(Integer.parseInt(tid));
            String userStr = team.getSuperUserStr();
            ArrayList<Integer> userIDs = StrUtil.analyzeStr(userStr);
            UserService userService = ContainerGetter.getUserService();
            User user =userService.findUserBYA(account);
            if( user != null)
            {
                for(Integer i : userIDs)
                {
                    if(userService.findUserBYU(i).getF_account().equals(account))
                    {
                        return StrUtil.userMap(user);
                    }
                }
            }

        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }
        return new HashMap<>();
    }


    //通过tid获取team的boss信息
    @PostMapping("/reqBoss")
    @ResponseBody
    public HashMap<String,String> reqBoss(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        try {
            int id = Integer.parseInt(tid);
            return StrUtil.userMap(ContainerGetter.getUserService().findUserBYU(teamService.findTeamBYI(id).getBid()));
        }catch (NumberFormatException e)
        {
            return new HashMap<>();
        }
    }


    //通过tid获取team的管理员列表
    @PostMapping("/reqSuper")
    @ResponseBody
    public ArrayList<Integer> reqSuper(HttpServletRequest req, HttpServletResponse rsp)
    {
        try {
            int teamId = Integer.parseInt(req.getParameter("tid"));
            Team team = teamService.findTeamBYI(teamId);
            if(team != null)
            {
                return StrUtil.analyzeStr(team.getSuperUserStr());
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    //通过tid获取team的所有user列表
    @PostMapping("/reqUser")
    @ResponseBody
    public ArrayList<Integer> reqUser(HttpServletRequest req, HttpServletResponse rsp)
    {
        try {
            int teamId = Integer.parseInt(req.getParameter("tid"));
            Team team = teamService.findTeamBYI(teamId);
            if(team != null)
            {
                return StrUtil.analyzeStr(team.getUserStr());
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    //通过tid获取所有不在team的user列表
    @PostMapping("/reqOuterUser")
    @ResponseBody
    public ArrayList<Integer> reqOuterUser(HttpServletRequest req, HttpServletResponse rsp)
    {
        try {
            int teamId = Integer.parseInt(req.getParameter("tid"));
            Team team = teamService.findTeamBYI(teamId);
            UserService userService = ContainerGetter.getUserService();
            if(team != null)
            {
                ArrayList<Integer> temp =  StrUtil.analyzeStr(team.getUserStr());
                ArrayList<Integer> result = userService.reqAllUser();
                result.removeAll(temp);
                return result;
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    //通过tid获取team的所有正常的project列表
    @PostMapping("/reqNormalPros")
    @ResponseBody
    public ArrayList<String> reqPros(HttpServletRequest req, HttpServletResponse rsp)
    {
        try {
            int teamId = Integer.parseInt(req.getParameter("tid"));
            Team team = teamService.findTeamBYI(teamId);
            if(team != null)
            {
                return StrUtil.analyzeStr2(team.getF_pids());
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    @PostMapping("/reqTDelPros")
    @ResponseBody
    public ArrayList<String> reqTDelPros(HttpServletRequest req, HttpServletResponse rsp)
    {
        try {
            int teamId = Integer.parseInt(req.getParameter("tid"));
            Team team = teamService.findTeamBYI(teamId);
            if(team != null)
            {
                return StrUtil.analyzeStr2(team.getF_Npids());
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
    @PostMapping("/reqAllPros")
    @ResponseBody
    public ArrayList<String> reqAllPros(HttpServletRequest req, HttpServletResponse rsp)
    {
        try {
            int teamId = Integer.parseInt(req.getParameter("tid"));
            Team team = teamService.findTeamBYI(teamId);
            if(team != null)
            {
                ArrayList<String> result =  StrUtil.analyzeStr2(team.getF_pids());
                result.addAll(StrUtil.analyzeStr2(team.getF_Npids()));
                return result;
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
    //通过tid和uid将user加入team
    @PostMapping("/joinTeam")
    @ResponseBody
    public int joinTeam(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        try {
            int teamId = Integer.parseInt(tid);
            int userId = Integer.parseInt(uid);
            if(teamId < 0 || userId < 0)
            {
                return -3;
            }
            Team team = teamService.findTeamBYI(teamId);
            UserService userService = ContainerGetter.getUserService();
            User user = userService.findUserBYU(userId);
            if(team != null && user != null)
            {
                if(!team.getUserStr().equals("[]") && !StrUtil.delId(team.getUserStr(),userId).equals(team.getUserStr()))
                {
                    return -2;
                }
                team.setF_userStr(StrUtil.addId(team.getUserStr(),userId));
                teamService.updateTeam(team);
                user.setF_groupStr(StrUtil.addId(user.getF_groupStr(),teamId));
                userService.updateUser(user);
                return 0;
            }
            return -1;
        }catch (Exception e)
        {
            return -3;
        }
    }

    //通过uid将user踢出team
    @PostMapping("/outTeam")
    @ResponseBody
    public int outTeam(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        boolean flag = true;
        try {
            int teamId = Integer.parseInt(tid);
            int userId = Integer.parseInt(uid);
            if(teamId < 0 || userId < 0)
            {
                return -1;
            }
            Team team = teamService.findTeamBYI(teamId);
            UserService userService = ContainerGetter.getUserService();
            User user = userService.findUserBYU(userId);
            if(team != null && user != null)
            {
                String userStr = team.getUserStr();
                String superStr = team.getSuperUserStr();
                String groupStr = user.getF_groupStr();
                ArrayList<Integer> temp1 = StrUtil.analyzeStr(userStr);
                ArrayList<Integer> temp2 = StrUtil.analyzeStr(superStr);
                ArrayList<Integer> temp3 = StrUtil.analyzeStr(groupStr);
                for(Integer i : temp1)
                {
                    if(i == userId)
                    {
                        temp1.remove(i);
                        flag = false;
                        break;
                    }
                }
                for(Integer i : temp3)
                {
                    if(i == teamId)
                    {
                        temp3.remove(i);
                        flag = false;
                        break;
                    }
                }
                if(flag)
                {
                    return -1;
                }
                for (Integer i : temp2)
                {
                    if(i == userId)
                    {
                        temp2.remove(i);
                        break;
                    }
                }
                team.setF_userStr(StrUtil.remakeStr(temp1));
                team.setF_superUserStr(StrUtil.remakeStr(temp2));
                teamService.updateTeam(team);
                user.setF_groupStr(StrUtil.remakeStr(temp3));
                userService.updateUser(user);
                return 0;
            }
            return -1;
        }catch (Exception e)
        {
            return -1;
        }
    }


    //通过uid将user设为team管理员
    @PostMapping("/addSuperUser")
    @ResponseBody
    public int addSuperUser(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        try {
            int teamId = Integer.parseInt(tid);
            int userId = Integer.parseInt(uid);
            if(teamId < 0 || userId < 0)
            {
                return -1;
            }
            Team team = teamService.findTeamBYI(teamId);
            if(team == null)
            {
                return -1;
            }
            ArrayList<Integer> users = StrUtil.analyzeStr(team.getUserStr());
            ArrayList<Integer> supers = StrUtil.analyzeStr(team.getSuperUserStr());
            boolean flag1 = false;
            boolean flag2 = true;
            for(Integer i : users)
            {
                if(i == userId)
                {
                    for(Integer j : supers)
                    {
                        if(j == userId)
                        {
                            flag2 = false;
                            break;
                        }
                    }
                    if(flag2)
                    {
                        flag1 = true;
                    }
                    break;
                }
            }
            if(flag1)
            {
                String superUserStr = team.getSuperUserStr();
                StringBuffer s = new StringBuffer(superUserStr);
                s.deleteCharAt(superUserStr.length()-1);
                s.append(", "+userId+"]");
                team.setF_superUserStr(s.toString());
                teamService.updateTeam(team);
                return 0;
            }
        }catch (Exception e)
        {
            return -1;
        }
        return -1;
    }


    //通过uid取消user管理员身份
    @PostMapping("/outSuperUser")
    @ResponseBody
    public int outSuperUser(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        try {
            int teamId = Integer.parseInt(tid);
            int userId = Integer.parseInt(uid);
            if(teamId < 0 || userId < 0)
            {
                return -1;
            }
            Team team = teamService.findTeamBYI(teamId);
            if(team == null)
            {
                return -1;
            }
            ArrayList<Integer> users = StrUtil.analyzeStr(team.getUserStr());
            ArrayList<Integer> supers = StrUtil.analyzeStr(team.getSuperUserStr());
            boolean flag = true;
            for(Integer i : users)
            {
                if(i == userId)
                {
                    for(Integer j : supers)
                    {
                        if(j == userId)
                        {
                            flag = false;
                            break;
                        }
                    }
                }
            }
            if(!flag)
            {
                String superUserStr = team.getSuperUserStr();
                ArrayList<Integer> temp = StrUtil.analyzeStr(superUserStr);
                for(Integer i : temp)
                {
                    if(i == userId)
                    {
                        temp.remove(i);
                        break;
                    }
                }
                team.setF_superUserStr(StrUtil.remakeStr(temp));
                teamService.updateTeam(team);
                return 0;
            }
        }catch (Exception e)
        {
            return -1;
        }
        return -1;
    }

    //通过uid判断user对于team的身份: -1表示用户不存在或参数错误，0表示非成员，1表示普通用户，2表示管理员，3表示创建者
    @PostMapping("/userIdentity")
    @ResponseBody
    public int userIdentity(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String uid = req.getParameter("uid");
        try {
            int teamId = Integer.parseInt(tid);
            int userId = Integer.parseInt(uid);
            if(teamId < 0 || userId < 0)
            {
                return -1;
            }
            Team team = teamService.findTeamBYI(teamId);
            if(team != null)
            {
                if(ContainerGetter.getUserService().findUserBYU(userId) == null)
                {
                    return -1;
                }
                if(userId == team.getBid())
                {
                    return 3;
                }
                ArrayList<Integer> supers = StrUtil.analyzeStr(team.getSuperUserStr());
                for( Integer i : supers)
                {
                    if(i == userId)
                    {
                        return 2;
                    }
                }
                ArrayList<Integer> users = StrUtil.analyzeStr(team.getUserStr());
                for( Integer i : users)
                {
                    if(i == userId)
                    {
                        return 1;
                    }
                }
                return 0;

            }
        }catch (Exception e)
        {
            return -1;
        }
        return -1;
    }

    @PostMapping("/teamNameUpdate")
    @ResponseBody
    public Result tempNameUpdate(HttpServletRequest req,HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String tName = req.getParameter("tName");
        try {
            int teamId = Integer.parseInt(tid);
            if(teamId < 0)
            {
                return new Result(false,403,"更新失败", 0);
            }
            Team team = teamService.findTeamBYI(teamId);
            if(team == null)
            {
                return new Result(false,403,"更新失败", 0);
            }
            team.setF_tName(tName);
            teamService.updateTeam(team);
            return new Result(true,200,"更新成功", 1);
        }catch (Exception e)
        {
            return new Result(false,403,"更新失败", 0);
        }
    }

    public boolean checkStyle(String bid, String tName)
    {
        try {
            if(Integer.parseInt(bid) < 0)
            {
                return false;
            }
        }catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    public Team findTeamBYI(int id)
    {
        return teamService.findTeamBYI(id);
    }

    public TeamService getTeamService() {
        return teamService;
    }

}
