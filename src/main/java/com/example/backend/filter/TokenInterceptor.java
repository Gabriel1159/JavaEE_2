package com.example.backend.filter;

import com.example.backend.pojo.User;
import com.example.backend.pojo.UserThreadLocal;
import com.example.backend.service.Impl.UserService;
import com.example.backend.util.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {
//    private static List<String> urlPass = new ArrayList<String>();
//    static{
//        urlPass.add("userLogin");
//        urlPass.add("userRegister");
//    }
//    @Autowired
//    UserService userService;
//    private static Logger logger = Logger.getLogger(TokenInterceptor.class);
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
//
//        StringBuffer sb = new StringBuffer();
//        Map<String,String[]> requestMap = request.getParameterMap();
//
//        for(String i : requestMap.keySet())
//        {
//            sb.append(i).append(":");
//            String[] list = requestMap.get(i);
//            for(String j:list)
//            {
//                sb.append(j).append(", ");
//            }
//        }
//        logger.debug("url: {" + request.getRequestURL() + "} param: " + sb);
//        String url = request.getRequestURI() ;
//        if (url.startsWith("/") && url.length() > 1) {
//            url = url.substring(1);
//        }
//
//        if(url.equals("userRegister") || url.equals("userLogin") || url.equals("downloadAvatar") || url.equals("userSendMail_R"))
//        {
//            return true;
//        }
//        String account = TokenUtil.verifyToken(request);
//        if(!account.equals("-3"))
//        {
//            User user = userService.findUserBYA(account);
//            UserThreadLocal.localVar.set(user);
//            return true;
//        }
//        handleFalseResponse(response);
//        return false;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
//
//    private void handleFalseResponse(HttpServletResponse response)
//            throws Exception {
//        response.setStatus(403);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"code\":\"403\",\"msg\":\"token 错误或已失效\"}");
//        response.getWriter().flush();
//    }
//
//    private boolean isInclude(String url) {
//        for (String i : this.urlPass) {
//            if (i.equals(url)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
