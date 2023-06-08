package com.example.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MailMapper {

    public String getCode(@Param("mail") String mail);
    public int insertCode(@Param("mail") String mail, @Param("code") String code);
    public int updateCode(@Param("mail") String mail, @Param("code") String code);
    public int deleteCode(@Param("mail") String mail);
    public int deleteSpeCode(@Param("mail") String mail, @Param("code") String code);
}
