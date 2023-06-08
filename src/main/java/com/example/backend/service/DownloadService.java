package com.example.backend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.mapper.FileMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


@Service
public class DownloadService {
    @Value("${file.upload.address}")
    private String rootPath ;
    @Value("${file.upload.address}")
    String UPLOAD_ADDRESS;
    @Autowired
    FileMapper fileMapper;
    private static Logger logger = Logger.getLogger(DownloadService.class);

    @Value("${file.download.address}")
    String urlDefault;

    public String downloadPicture(HttpServletRequest request, HttpServletResponse response)
    {
        String uid = request.getParameter("uid");
        String url = fileMapper.selectPicture(uid);

        try {
            if(url!=null && url!="")
            {
                String strBackUrl = "http://" + request.getServerName() //服务器地址
                        + ":"
                        + request.getServerPort()
                        + "/"+"downloadAvatar?uid="+uid;
                return  strBackUrl;
            }
            url = fileMapper.selectPicture("1");
            String strBackUrl = "http://" + request.getServerName() //服务器地址
                    + ":"
                    + request.getServerPort()
                    + "/"+"downloadAvatar?uid="+uid;
            return strBackUrl;
        } catch (Exception e) {
            return "-1";
        }
    }
    public String downloadAvatar(HttpServletRequest request, HttpServletResponse response)
    {

        String fileName = "";
        fileName = fileMapper.selectPicture(request.getParameter("uid"));
        String FullPath = rootPath + fileName;
        File packetFile = new File(FullPath);
        String fn = packetFile.getName();
        logger.debug("filename:"+fn);
        File file = new File(FullPath);

        if (file.exists()) {

            response.setHeader("content-type", "application/application/x-jpg");
            response.setContentType("application/x-jpg");

            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("设置下载文件显示中文失败");
                return "-1";
            }

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                logger.debug("下载头像成功!");
            } catch (Exception e) {
                logger.error("下载头像失败");
                return "-1";
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {//对应文件不存在

            return "-1";
        }

        return "0";
    }

//    public JSONObject downloadPrototype(HttpServletRequest request,HttpServletResponse response)
//    {
//        try {
//            String f_prototypeId = request.getParameter("f_prototypeId");
//            String prototypeString = fileMapper.selectPrototype(f_prototypeId);
//            JSONObject f_prototypeMap= JSON.parseObject(prototypeString);
//            return f_prototypeMap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return  JSON.parseObject("{state:failed}");
//        }
//
//    }
}
