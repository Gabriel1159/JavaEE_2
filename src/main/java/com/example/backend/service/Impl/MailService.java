package com.example.backend.service.Impl;

import com.example.backend.mapper.MailMapper;
import com.example.backend.service.MailServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("MailService")
public class MailService implements MailServiceIF {
    @Resource
    private MailMapper mailMapper;

    @Override
    public String getCode(String mail)
    {
        return mailMapper.getCode(mail);
    }

    @Override
    public int insertCode(String mail,String code)
    {
        return mailMapper.insertCode(mail,code);
    }

    @Override
    public int updateCode(String mail,String code)
    {
        return mailMapper.updateCode(mail,code);
    }

    @Override
    public int deleteCode(String mail) {
        return mailMapper.deleteCode(mail);
    }

    @Override
    public int deleteSpeCode(String mail, String code) {
        return mailMapper.deleteSpeCode(mail,code);
    }

}
