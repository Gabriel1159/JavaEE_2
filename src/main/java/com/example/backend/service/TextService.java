package com.example.backend.service;

import com.example.backend.mapper.TextMapper;
import com.example.backend.pojo.Text;
import com.example.backend.pojo.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TextService {
    @Autowired
    TextMapper textMapper;
    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
    public String createText(HttpServletRequest request, HttpServletResponse response)
    {
        String f_project = request.getParameter("f_projectId");
        String f_textName = request.getParameter("f_textName");
        String f_folderLabel = request.getParameter("f_folderLabel");
        int ret = textMapper.searchTextByName(f_textName);
        if(ret!=0)
        {
            return "-1";
        }
        String f_text = request.getParameter("f_text");
        String account = UserThreadLocal.getAccount();
        Date date = new Date();
        String dateString = dateFormat.format(date);
        UUID uuid = UUID.randomUUID();
        String f_textId = uuid.toString();
        Text text = new Text(f_textId,f_project,f_textName,f_text,dateString,account,dateString,account, f_folderLabel);
        try {
            textMapper.uploadText(text);
            return f_textId;
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }
    public String saveText(HttpServletRequest request, HttpServletResponse response)
    {
        String f_textId = request.getParameter("f_textId");
        String f_text = request.getParameter("f_text");
        String account = UserThreadLocal.getAccount();
        Date date = new Date();
        try {
            textMapper.saveText(f_textId,f_text);
            textMapper.setModifier(account,f_textId,dateFormat.format(date));
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public Text downloadText(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            String f_textId = request.getParameter("f_textId");
            String modifier = UserThreadLocal.getAccount();
            Text text = textMapper.downloadText(f_textId);
            Date date = new Date();
            textMapper.setModifier(modifier,f_textId,dateFormat.format(date));
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public Text searchTextByID(String f_textId) {
        try {
            return textMapper.searchTextByID(f_textId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> searchTextByPro(String f_proID) {
        try {
            return textMapper.searchTextByPro(f_proID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteText(String proID) {
        try {
            textMapper.deleteText(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delText(String proID) {
        try {
            textMapper.delText(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recoverText(String proID) {
        try {
            textMapper.recoverText(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delTextInDustbin(String proID) {
        try {
            textMapper.delTextInDustbin(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTextInDustbin(String proID) {
        try {
            textMapper.deleteTextInDustbin(proID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createText(Text text) {
        try {
            textMapper.uploadText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Text> accurateFind(String name) {
        try {
            return textMapper.accurateFind(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delTextByTextID(String f_textId) {
        try {
            textMapper.delTextByTextID(f_textId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Text searchTeamIntroText(String folderName, int tid) {
        try {
            return textMapper.searchTeamIntroText(folderName, tid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Text> searchTextInFolder(String folderName, int tid) {
        try {
            return textMapper.searchTextInFolder(folderName, tid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Text> searchTextObjectByPro(String f_proID) {
        try {
            return textMapper.searchTextObjectByPro(f_proID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int whoUse(String textId)
    {
        return textMapper.whoUse(textId);
    }

    public int newUse(int uid, String textId)
    {
        return textMapper.newUse(uid,textId);
    }
}
