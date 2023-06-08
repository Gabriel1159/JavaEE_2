package com.example.backend.controller;


import com.example.backend.mapper.TopicMapper;
import com.example.backend.pojo.*;
import com.example.backend.service.Impl.InvitationService;
import com.example.backend.service.Impl.MailService;
import com.example.backend.service.Impl.TeamService;
import com.example.backend.service.TopicService;
import com.example.backend.util.ContainerGetter;
import com.example.backend.util.StrUtil;
import com.example.backend.service.Impl.UserService;
import com.example.backend.util.TokenUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("UserController")
public class UserController {

    @Autowired
    private TopicMapper topicMapper;

    @Resource
    private UserService userService;


    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.registerTitle}")
    private String registerTitle;

    @Resource
    private JavaMailSender javaMailSender;

    //通过基本信息注册用户
    @PostMapping("/register")
    @ResponseBody
    public int userRegister(HttpServletRequest req, HttpServletResponse rsp)
    {
        String userName = req.getParameter("username");
        String pwd1 = req.getParameter("password_1");
        String pwd2 = req.getParameter("password_2");

        if(!pwd1.equals(pwd2))
        {
            return 2;
        }
        User user = new User(userName,pwd1);
        int uid = userService.addUser(user);
        if(uid != -1)
        {
            return 0;
        }

        return -1;
    }

    //通过account和pwd验证用户登录
    @PostMapping("/login")
    @ResponseBody
    public int userLogin(HttpServletRequest req, HttpServletResponse rsp)
    {

        String userName = req.getParameter("login_name");
        String pwd = req.getParameter("login_code");
        User user;

        user = userService.findUserBYN(userName);
        if(user == null)
        {
            return 1;
        }


        if(!pwd.equals(user.getF_pwd()))
        {
            return 2;
        }
        return 0;

    }
    @PostMapping("/update_info")
    @ResponseBody
    public int updateInfo(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("u_id");
        String mail = req.getParameter("mail");
        String password = req.getParameter("password");


        User user = userService.findUserBYN(uid);
        user.setF_mail(mail);
        user.setF_userName(uid);
        user.setF_pwd(password);
        if(userService.updateUser(user)>0)
        {
            return 0;
        }
        return 1;

    }
    @GetMapping("/space/{u_id}")
    @ResponseBody
    public User getInfo(@PathVariable("u_id")String u_id)
    {


        User user = userService.findUserBYN(u_id);
        List<Topic> topicList = topicMapper.getTopicByUId(user.getU_id());
        user.setTopicList(topicList);
        return user;

    }
    //通过uid更新用户信息
    @PostMapping("/userUpdate")
    @ResponseBody
    public int userUpdate(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String realName = req.getParameter("realName");
        String pwd = req.getParameter("pwd");
        String sex = req.getParameter("sex");
        String mail = req.getParameter("mail");
        String location = req.getParameter("location");
        String birth = req.getParameter("birth");
        String phone = req.getParameter("phone");
        if(checkStyle(uid,userName,realName,pwd,sex,mail,location,birth,phone))
        {
            User user = userService.findUserBYU(Integer.parseInt(uid));
            if(user != null)
            {
                if(userName != null)
                {
                    user.setF_userName(userName);
                }
                if(realName != null)
                {
                    user.setF_realName(realName);
                }
                if(pwd != null)
                {
                    user.setF_pwd(pwd);
                }
                if(sex != null)
                {
                    user.setF_sex(sex);
                }
                if(mail != null)
                {
                    user.setF_mail(mail);
                }
                if(location != null)
                {
                    user.setF_location(location);
                }
                if(birth != null)
                {
                    try {
                        user.setF_birth(new SimpleDateFormat("yyyy-MM-dd").parse(birth));
                    }catch (Exception e)
                    {
                        return -1;
                    }
                }
                if(phone != null)
                {
                    user.setF_phone(phone);
                }
                userService.updateUser(user);
                return 0;
            }
        }
        return -1;
    }
    //通过account和pwd摧毁用户
    @PostMapping("/userDestroy")
    @ResponseBody
    public int userDestroy(HttpServletRequest req,HttpServletResponse rsp)
    {
        String account = req.getParameter("account");
        String pwd = req.getParameter("pwd");
        User user = userService.findUserBYA(account);
        if(user != null && user.getF_pwd().equals(pwd))
        {
            ContainerGetter.getInvitationService().delRelativeInvitationBY_U(user.getF_uid());
            userService.delUser(user);
            return 0;
        }
        return -1;
    }

    //通过uid获取所有team
    @PostMapping("/userGroups")
    @ResponseBody
    public ArrayList<Integer> userGroups(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        try {
            int userId = Integer.parseInt(uid);
            User user = userService.findUserBYU(userId);
            if(user != null)
            {
                return StrUtil.analyzeStr(user.getF_groupStr());
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }


    //通过uid获取所有未删除的project
    @PostMapping("/userNormalPros")
    @ResponseBody
    public ArrayList<String> userNormalPros(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        ArrayList<String> result = new ArrayList<>();
        try {
            int userId = Integer.parseInt(uid);
            User user = userService.findUserBYU(userId);
            if(user != null)
            {
                ArrayList<Integer> teams_I =  StrUtil.analyzeStr(user.getF_groupStr());
                ArrayList<Team> teams = new ArrayList<>();
                for(Integer i: teams_I)
                {
                    teams.add(ContainerGetter.getTeamService().findTeamBYI(i));
                }
                for(Team team : teams)
                {
                    result.addAll(StrUtil.analyzeStr2(team.getF_pids()));
                }
                ArrayList<String> ret = new ArrayList<>();
                for(String s:result)
                {
                    if(!s.equals(""))
                    {
                        ret.add(s);
                    }
                }
                return ret;
            }
        }catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }

    @PostMapping("/userTDelPros")
    @ResponseBody
    public ArrayList<String> userTDelPros(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        ArrayList<String> result = new ArrayList<>();
        try {
            int userId = Integer.parseInt(uid);
            User user = userService.findUserBYU(userId);
            if(user != null)
            {
                ArrayList<Integer> teams_I =  StrUtil.analyzeStr(user.getF_groupStr());
                ArrayList<Team> teams = new ArrayList<>();
                for(Integer i: teams_I)
                {
                    teams.add(ContainerGetter.getTeamService().findTeamBYI(i));
                }
                for(Team team : teams)
                {
                    result.addAll(StrUtil.analyzeStr2(team.getF_Npids()));
                }
                ArrayList<String> ret = new ArrayList<>();
                for(String s:result)
                {
                    if(!s.equals(""))
                    {
                        ret.add(s);
                    }
                }
                return ret;
            }
        }catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }

    @PostMapping("/userAllPros")
    @ResponseBody
    public ArrayList<String> userAllPros(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        ArrayList<String> result = new ArrayList<>();
        try {
            int userId = Integer.parseInt(uid);
            User user = userService.findUserBYU(userId);
            if(user != null)
            {
                ArrayList<Integer> teams_I =  StrUtil.analyzeStr(user.getF_groupStr());
                ArrayList<Team> teams = new ArrayList<>();
                for(Integer i: teams_I)
                {
                    teams.add(ContainerGetter.getTeamService().findTeamBYI(i));
                }
                for(Team team : teams)
                {
                    result.addAll(StrUtil.analyzeStr2(team.getF_Npids()));
                    result.addAll(StrUtil.analyzeStr2(team.getF_pids()));
                }
                ArrayList<String> ret = new ArrayList<>();
                for(String s:result)
                {
                    if(!s.equals(""))
                    {
                        ret.add(s);
                    }
                }
                return ret;
            }
        }catch (NumberFormatException e)
        {
            return result;
        }
        return result;
    }

    //通过account全局查找user信息
    @PostMapping("/userFindBYA_Global")
    @ResponseBody
    public HashMap<String,String> userFindBYA_Global(HttpServletRequest req, HttpServletResponse rsp)
    {
        String account = req.getParameter("account");
        User user = userService.findUserBYA(account);
        return StrUtil.userMap(user);
    }

    @PostMapping("/userSendMail_R")
    @ResponseBody
    public Result userSendMail_R(HttpServletRequest req, HttpServletResponse rsp)
    {
        String mail = req.getParameter("mail");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject(registerTitle);
            String code = StrUtil.makeRandomStr();
            BufferedReader part1 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/verifyPart1.txt"))));
            BufferedReader part2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("src/main/resources/verifyPart2.txt"))));
            StringBuffer html = new StringBuffer();
            while(true)
            {
                String temp = "";
                if((temp = part1.readLine()) != null)
                {
                    html.append(temp);
                }
                else
                {
                    break;
                }
            }
            html.append(code);
            while(true)
            {
                String temp = "";
                if((temp = part2.readLine()) != null)
                {
                    html.append(temp);
                }
                else
                {
                    break;
                }
            }
            mimeMessageHelper.setText(html.toString(),true);
            MailService mailService = ContainerGetter.getMailService();
            if(mailService.getCode(mail) != null)
            {
                if(mailService.updateCode(mail,code) != 1)
                {
                    return new Result(false,404,"数据库异常无法发送验证码",-3);
                }
            }
            else
            {
                if(mailService.insertCode(mail,code) != 1)
                {
                    return new Result(false,404,"数据库异常无法发送验证码",-3);
                }
            }
            javaMailSender.send(mimeMessage);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300000);
                        mailService.deleteSpeCode(mail,code);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return new Result(true,200,"发送成功",code);
        } catch (Exception e)
        {
            return new Result(false,404,"邮箱错误",-3);
        }
    }



    //通过uid全局查找user信息
    @PostMapping("/userFindBYU_Global")
    @ResponseBody
    public HashMap<String,String> userFindBYU_Global(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        HashMap<String,String> hm = new HashMap<>();
        try {
            User user = userService.findUserBYU(Integer.parseInt(uid));
            return StrUtil.userMap(user);
        }catch (NumberFormatException e)
        {
            return hm;
        }
    }

    @PostMapping("/userAddInvitation")
    @ResponseBody
    public Result userAddInvitation(HttpServletRequest req, HttpServletResponse rsp)
    {
        String tid = req.getParameter("tid");
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        int teamId;
        int fromId;
        int toId;
        try {
            teamId = Integer.parseInt(tid);
            fromId = Integer.parseInt(from);
            toId = Integer.parseInt(to);
        }catch (Exception e)
        {
            return new Result(false,403,"参数异常",-1);
        }
        TeamService teamService = ContainerGetter.getTeamService();
        InvitationService invitationService = ContainerGetter.getInvitationService();
        if(userService.findUserBYU(fromId) == null || userService.findUserBYU(toId) == null || teamService.findTeamBYI(teamId) == null)
        {
            return new Result(false,403,"目标用户或目标团队已注销",-2);
        }
        try {
            Invitation invitation;
            if((invitation = invitationService.findInvitation(fromId,toId,teamId)) != null)
            {
                return new Result(true,200,"邀请发送成功",invitation.getF_vid());
            }
            invitation = new Invitation(fromId,toId,teamId);
            invitationService.insertInvitation(invitation);
            return new Result(true,200,"邀请发送成功",invitation.getF_vid());
        }catch (Exception e)
        {
            return new Result(false,403,"数据库异常",-3);
        }
    }

    @PostMapping("/userReqInvitation")
    @ResponseBody
    public Result userReqInvitation(HttpServletRequest req, HttpServletResponse rsp)
    {
        String uid = req.getParameter("uid");
        int userId;
        try{
            userId = Integer.parseInt(uid);
        }catch (NumberFormatException e)
        {
            return new Result(false,403,"参数异常",-1);
        }
        if(userService.findUserBYU(userId) == null)
        {
            return new Result(false,403,"参数异常",-1);
        }
        TeamService teamService = ContainerGetter.getTeamService();
        ArrayList<Invitation> invitations = new ArrayList<>();
        invitations.addAll(ContainerGetter.getInvitationService().reqInvitation(userId));
        ArrayList<Invitation> result = new ArrayList<>();
        for(Invitation invitation:invitations)
        {
            if(teamService.findTeamBYI(invitation.getF_tid()) != null && userService.findUserBYU(invitation.getF_from()) != null)
            {
                result.add(invitation);
            }
        }
        return new Result(true,200,"查询成功",result);
    }

    @PostMapping("/userDelInvitation")
    @ResponseBody
    public Result userDelInvitation(HttpServletRequest req, HttpServletResponse rsp)
    {
        String vid = req.getParameter("vid");
        int invite;
        try {
            invite = Integer.parseInt(vid);
            InvitationService invitationService = ContainerGetter.getInvitationService();
            if(invitationService.findInvitationBY_V(invite) == null)
            {
                return new Result(false,403,"邀请不存在",-2);
            }
            ContainerGetter.getInvitationService().delInvitation(invite);
            return new Result(true,200,"删除成功",1);
        }catch (Exception e)
        {
            return new Result(false,403,"参数异常",-1);
        }
    }

    @PostMapping("/vagueFind")
    @ResponseBody
    public Result vagueFind(HttpServletRequest req,HttpServletResponse rsp)
    {
        String input = req.getParameter("input");
        try {
            ArrayList<Integer> temp = userService.vagueFind(input);
            return new Result(true,200,"查询成功",temp);
        }catch (Exception e)
        {
            return new Result(false,404,"查询失败",new ArrayList<>());
        }
    }

    public boolean checkStyle(String userName,String realName,String pwd,String sex,String mail,String location,String birth,String phone)
    {
        if(userName == null || realName == null || pwd == null)
        {
            return false;
        }
        try {
            if(sex != null)
            {
                if (!sex.equals("M") && !sex.equals("F") && !sex.equals("N"))
                {
                    return false;
                }
            }
            if(birth != null)
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date bir = df.parse(birth);
                Date now = new Date();
                if(bir.compareTo(now) >= 0)
                {
                    return false;
                }
            }

        }catch (NumberFormatException e)
        {
            return false;
        } catch (ParseException e) {
            return false;
        }
        return true;

    }

    public boolean checkStyle(String uid,String userName,String realName,String pwd,String sex,String mail,String location,String birth,String phone)
    {
        boolean result1 = false;
        try {
            Integer.parseInt(uid);
            result1 = true;
        }catch (Exception e)
        {
            result1 = false;
        }
        if(sex != null)
        {
            if (!sex.equals("M") && !sex.equals("F") && !sex.equals("N"))
            {
                return false;
            }
        }
        if(birth != null)
        {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date bir = df.parse(birth);
                Date now = new Date();
                if(bir.compareTo(now) >= 0)
                {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        }
        return result1;

    }

    public UserService getUserService() {
        return userService;
    }


}


