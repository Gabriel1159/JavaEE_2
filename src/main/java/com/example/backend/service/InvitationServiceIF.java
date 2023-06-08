package com.example.backend.service;

import com.example.backend.pojo.Invitation;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface InvitationServiceIF {
    public int insertInvitation(Invitation invitation);
    public ArrayList<Invitation> reqInvitation(int to);
    public int delInvitation(int vid);
    public Invitation findInvitation(int from, int to, int tid);
    public Invitation findInvitationBY_V(int vid);
    public int delRelativeInvitationBY_U(int uid);
    public int delRelativeInvitationBY_T(int tid);
}
