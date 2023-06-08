package com.example.backend.service;
import com.example.backend.mapper.FileMapper;
import com.example.backend.pojo.Picture;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class UploadService {

    @Value("${file.upload.address}")
    String UPLOAD_ADDRESS;
    @Autowired
    FileMapper fileMapper;
    private static Logger logger = Logger.getLogger(UploadService.class);

    SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

    public String uploadPicture(MultipartFile multipartFile, String f_pictureId, String f_userAccount, HttpServletRequest request)
    {
        if(multipartFile==null || multipartFile.isEmpty())
        {
            return "上传文件为空";
        }
        String filePath = new File(UPLOAD_ADDRESS).getAbsolutePath();
        File fileUpload = new File(filePath);
        if(!fileUpload.exists())
        {
            fileUpload.mkdir();
        }
        fileUpload = new File(filePath, multipartFile.getOriginalFilename());
        try
        {
            multipartFile.transferTo(fileUpload);
        }
        catch (IOException e)
        {
            logger.error("文件上传失败");
            throw new RuntimeException(e);
        }
        //判断是创建还是修改头像
        String path = fileMapper.selectPicture(f_pictureId);
        if(path==null || path.equals(""))
        {

            Picture picture = new Picture(f_pictureId,f_userAccount,"/"+multipartFile.getOriginalFilename());
            fileMapper.addPicture(picture);

        }
        else
        {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ADDRESS+path));
            fileMapper.setFilePath(f_pictureId,"/"+multipartFile.getOriginalFilename());

        }
        String strBackUrl = "http://" + request.getServerName() //服务器地址
                + ":"
                + request.getServerPort()
                + "/"+"downloadAvatar?uid="+f_pictureId;          //端口号
        System.out.println(strBackUrl);
        return strBackUrl;




    }
//    public String uploadPrototype(String f_prototypeId, int f_projectId, String prototypeMap,String f_prototypeName,String f_creator)
//    {
//        Date date = new Date();
//        Prototype prototype = new Prototype(f_prototypeId,String.valueOf(f_projectId),prototypeMap.toString(),f_prototypeName,dateFormat.format(date),f_creator,dateFormat.format(date),f_creator);
//        if(fileMapper.addPrototype(prototype)<0)
//        {
//            return "-1";
//        }
//
//        return "0";
//    }
}
