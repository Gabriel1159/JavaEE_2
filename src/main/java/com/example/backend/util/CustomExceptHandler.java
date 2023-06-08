package com.example.backend.util;
import com.example.backend.pojo.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptHandler {
    private static  Logger logger = Logger.getLogger(CustomExceptHandler.class);


    @ExceptionHandler(value = {NullPointerException.class})
    Result handleNullPointException(NullPointerException nullPointerException,HttpServletRequest request)
    {
        logger.error("url: {" + request.getRequestURL() + "}, msg: {" + nullPointerException.getMessage() + "}");
        Result result = new Result(false,501,nullPointerException.getMessage(),request.getRequestURL());
        return result;
    }

    @ExceptionHandler(value = {Exception.class})
    Result handleException(Exception e, HttpServletRequest request)
    {
        logger.error("url: {" + request.getRequestURL() + "}, msg: {" + e.getMessage() + "}");
        Result result = new Result(false,500,e.getMessage(),request.getRequestURL());
        return result;
    }

}
