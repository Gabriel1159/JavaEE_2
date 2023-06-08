package com.example.backend.pojo;


public class UserThreadLocal {
    public static ThreadLocal<User> localVar = new ThreadLocal<>();

    public static void print(String str) {
        System.out.println(str + " :" + localVar.get().toString());
    }
    public static String getAccount()
    {
        return (String) localVar.get().getF_account();
    }


}
