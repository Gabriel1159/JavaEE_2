package com.example.backend.service;

public interface MailServiceIF {
    public String getCode(String mail);
    public int insertCode(String mail,String code);
    public int updateCode(String mail,String code);
    public int deleteCode(String mail);
    public int deleteSpeCode(String mail, String code);
}
