package com.example.backend.util;

import com.example.backend.service.Impl.InvitationService;
import com.example.backend.service.Impl.TeamService;
import com.example.backend.service.Impl.UserService;
import com.example.backend.service.Impl.MailService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContainerGetter implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //通过属性名获取对象的方法
    public static Object getbyName(String name){
        Object bean = applicationContext.getBean(name);
        return bean;
    }

    //通过属性类获取对象的方法
    public static Object getByClass(Class clazz){
        Object bean = applicationContext.getBean(clazz);
        return bean;
    }

    public static UserService getUserService()
    {
        return (UserService)getbyName("UserService");
    }

    public static TeamService getTeamService()
    {
        return (TeamService) getbyName("TeamService");
    }

    public static MailService getMailService(){
        return (MailService) getbyName("MailService");
    }

    public static InvitationService getInvitationService()
    {
        return (InvitationService) getbyName("InvitationService");
    }
}
