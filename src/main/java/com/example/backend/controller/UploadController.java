package com.example.backend.controller;

import com.example.backend.pojo.Project;
import com.example.backend.pojo.Result;
import com.example.backend.pojo.User;
import com.example.backend.pojo.UserThreadLocal;
import com.example.backend.service.Impl.UserService;
import com.example.backend.service.UploadService;
import com.example.backend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class UploadController
{
    @Autowired
    UploadService uploadService;
    @Autowired
    UserService userService;

    @PostMapping("/uploadPicture")
    public Result uploadPicture(@RequestParam("file") MultipartFile multipartFile, String uid, HttpServletRequest req)
    {
        String account = UserThreadLocal.getAccount();
        User user = userService.findUserBYA(account);
        UserThreadLocal.localVar.set(user);
        String f_userAccount = UserThreadLocal.getAccount();

        String msg = uploadService.uploadPicture(multipartFile, uid, f_userAccount,req);
        if(msg.startsWith("h"))
        {
            return new Result(true,200,"头像上传成功",msg);
        }
        return new Result(false,401,"头像上传失败",msg);
    }

}
