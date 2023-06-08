//package com.example.backend.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//@WebFilter(urlPatterns = "/*",filterName = "paramFilter")
//public class ParamFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    private static List<String> urlPass = new ArrayList<String>();
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        //跨域
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, GET");
//        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//
//        ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
//        chain.doFilter(requestWrapper, response);
//        return;
//    }
//    private boolean isInclude(String url) {
//        for (String i : urlPass) {
//            if (i.equals(url)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
