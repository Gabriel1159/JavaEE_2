package com.example.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.pojo.Result;
import com.example.backend.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class DownloadController {
    @Autowired
    DownloadService downloadService;

    @RequestMapping("/downloadPicture")
    public Result downloadPicture(HttpServletRequest request, HttpServletResponse response)
    {

            String msg = downloadService.downloadPicture(request,response);
            if(msg.equals("-1"))
            {
                return new Result(false,501,"头像路径下载失败",null);
            }
            return new Result(true,200,"头像路径下载成功",msg);

    }
    @RequestMapping("/downloadAvatar")
    public  void downloadAvatar(HttpServletRequest request, HttpServletResponse response)
    {
        String msg = downloadService.downloadAvatar(request,response);
        if(msg.equals("-1"))
        {
            System.out.println("头像下载失败");
        }
        System.out.println("头像下载成功");
    }

}
