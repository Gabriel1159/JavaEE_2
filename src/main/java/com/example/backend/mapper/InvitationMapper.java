package com.example.backend.mapper;

import com.example.backend.pojo.Invitation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface InvitationMapper {
    public int insertInvitation(Invitation invitation);
    public ArrayList<Invitation> reqInvitation(@Param("to") int to);
    public int delInvitation(@Param("vid") int vid);
    public Invitation findInvitation(@Param("from") int from, @Param("to") int to, @Param("tid") int tid);
    public Invitation findInvitationBY_V(@Param("vid") int vid);
    public int delRelativeInvitationBY_U(@Param("uid") int uid);
    public int delRelativeInvitationBY_T(@Param("tid") int tid);

}
