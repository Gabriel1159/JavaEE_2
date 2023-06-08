package com.example.backend.util;

import com.example.backend.pojo.Project;
import com.example.backend.pojo.Team;
import com.example.backend.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;

public class StrUtil {
    public static HashMap<String,String> userMap(User user)
    {
        HashMap<String,String> hs = new HashMap<>();
        if(user != null)
        {
            hs.put("account",user.getF_account());
            hs.put("uid",user.getF_uid()+"");
            hs.put("userName",user.getF_userName());
            hs.put("realName",user.getF_realName());
            hs.put("sex",user.getF_sex());
            hs.put("birth_D",user.getF_birth_D().toString());
            hs.put("mail",user.getF_mail());
            hs.put("phone",user.getF_phone());
            hs.put("location",user.getF_location());
            hs.put("groupStr",user.getF_groupStr());
        }
        return hs;
    }
    public static HashMap<String,String> teamMap(Team team) {
        HashMap<String, String> hm = new HashMap<>();
        if (team != null) {
            hm.put("tid", team.getTid() + "");
            hm.put("bid", team.getBid() + "");
            hm.put("superUserStr", team.getSuperUserStr());
            hm.put("userStr", team.getUserStr());
            hm.put("tName", team.gettName());
        }
        return hm;
    }



    //str:原pids id:需要添加的id
    public static String addId(String str,int id)
    {
        StringBuffer sb = new StringBuffer(str);
        sb.deleteCharAt(str.length()-1);
        if(str.equals("[]"))
        {
            sb.append(id+"]");
        }
        else
        {
            sb.append(", "+id+"]");
        }
        return sb.toString();
    }

    public static String addId(String str,String id)
    {
        StringBuffer sb = new StringBuffer(str);
        sb.deleteCharAt(str.length()-1);
        if(str.equals("[]"))
        {
            sb.append(id+"]");
        }
        else
        {
            sb.append(", "+id+"]");
        }
        return sb.toString();
    }

    public static String delId(String str,String id)
    {
        if(str.equals("[]"))
        {
            return "[]";
        }
        ArrayList<String> temp = analyzeStr2(str);
        for(String i: temp)
        {
            if(i.equals(id))
            {
                temp.remove(i);
                break;
            }
        }
        return remakeStr2(temp);
    }
    public static String delId(String str,int id)
    {
        if(str.equals("[]"))
        {
            return "[]";
        }
        ArrayList<Integer> temp = analyzeStr(str);
        for(Integer i: temp)
        {
            if(i == id)
            {
                temp.remove(i);
                break;
            }
        }
        return remakeStr(temp);
    }
    //str:[23,45]
    public static ArrayList<Integer> analyzeStr(String str)
    {

        String temp = str.substring(1,str.length()-1);
        String[] temp2 = temp.split(", ");
        ArrayList<Integer> result = new ArrayList<>();
        if(str.equals("[]"))
        {
            return result;
        }
        try
        {
            for(int i = 0 ; i < temp2.length ; i++)
            {
                result.add(Integer.parseInt(temp2[i]));
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return result;
    }

    public static ArrayList<String> analyzeStr2(String str)
    {
        String temp = str.substring(1,str.length()-1);
        String[] temp2 = temp.split(", ");
        ArrayList<String> result = new ArrayList<>();
        if(str.equals("[]"))
        {
            return result;
        }
        try
        {
            for(int i = 0 ; i < temp2.length ; i++)
            {
                result.add(temp2[i]);
            }
        }catch (NumberFormatException e)
        {
            return new ArrayList<>();
        }
        return result;
    }

    //str:[23,45]
    public static String remakeStr(ArrayList<Integer> list)
    {
        StringBuffer sb = new StringBuffer("[");
        for(int i = 0 ; i < list.size() ; i++)
        {
            if(i != list.size()-1)
            {
                sb.append(list.get(i)+", ");
            }
            else
            {
                sb.append(list.get(i));
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static String remakeStr2(ArrayList<String> list)
    {
        StringBuffer sb = new StringBuffer("[");
        for(int i = 0 ; i < list.size() ; i++)
        {
            if(i != list.size()-1)
            {
                sb.append(list.get(i)+", ");
            }
            else
            {
                sb.append(list.get(i));
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static HashMap<String, String> ProToHash(Project project) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("f_proID", project.getF_proID());
        hm.put("f_proCreator", project.getF_proCreator());
        hm.put("f_proDescription", project.getF_proDescription());
        hm.put("f_proName", project.getF_proName());
        hm.put("f_proStatus", String.valueOf(project.getF_proStatus()));
        hm.put("f_teamID", String.valueOf(project.getF_teamID()));
        return hm;
    }

    public static String makeRandomStr()
    {
        int count = (int)(Math.random()*5);
        int num = (int)(Math.random()*1000000);
        StringBuffer sb = new StringBuffer();
        for(int i = 0 ; i < count ; i++)
        {
            sb.append(strByNum((int)(Math.random()*10)));
        }
        for(int i = count ; i < 6 ; i++)
        {
            sb.append((int)(Math.random()*10));
        }
        return sb.toString();
    }

    public static String strByNum(int num)
    {
        switch (num){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "V";
            case 6:
                return "W";
            case 7:
                return "X";
            case 8:
                return "Y";
            case 9:
                return "Z";
            default:
                return "Q";
        }
    }

    public static String generateCopyName(String originName, int n)
    {
        String ret = "";
        if(n==0)
        {
            ret = originName + " - 副本";
        }
        else {
            ret = originName + " - 副本" + (n+1);
        }
        return ret;
    }
}

