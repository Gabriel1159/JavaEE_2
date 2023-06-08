package com.example.backend.service.Impl;

import com.example.backend.mapper.InvitationMapper;
import com.example.backend.pojo.Invitation;
import com.example.backend.service.InvitationServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service("InvitationService")
public class InvitationService implements InvitationServiceIF {

    @Resource
    private InvitationMapper invitationMapper;

    @Override
    public int insertInvitation(Invitation invitation) {
        return invitationMapper.insertInvitation(invitation);
    }

    @Override
    public ArrayList<Invitation> reqInvitation(int to) {
        return invitationMapper.reqInvitation(to);
    }

    @Override
    public int delInvitation(int vid) {
        return invitationMapper.delInvitation(vid);
    }

    @Override
    public Invitation findInvitation(int from, int to, int tid) {
        return invitationMapper.findInvitation(from,to,tid);
    }
    @Override
    public Invitation findInvitationBY_V(int vid) {
        return invitationMapper.findInvitationBY_V(vid);
    }

    @Override
    public int delRelativeInvitationBY_U(int uid) {
        return invitationMapper.delRelativeInvitationBY_U(uid);
    }

    @Override
    public int delRelativeInvitationBY_T(int tid) {
        return invitationMapper.delRelativeInvitationBY_T(tid);
    }


}
