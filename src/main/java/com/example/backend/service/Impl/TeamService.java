package com.example.backend.service.Impl;

import com.example.backend.mapper.TeamMapper;
import com.example.backend.pojo.Team;
import com.example.backend.service.TeamServiceIF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("TeamService")
public class TeamService implements TeamServiceIF {

    @Resource
    private TeamMapper teamMapper;

    @Override
    public int addTeam(Team team) {
        try {
            teamMapper.addTeam(team);
        }catch (Exception e)
        {
            return -1;
        }
        return 0;
    }

    @Override
    public int delTeam(Team team) {
        try {
            teamMapper.delTeam(team);
        }catch (Exception e)
        {
            return -1;
        }
        return 0;
    }

    @Override
    public int updateTeam(Team team) {
        try {
            teamMapper.updateTeam(team);
        }catch (Exception e)
        {
            return -1;
        }
        return 0;
    }

    @Override
    public Team findTeamBYI(int tid) {
        try {
            return teamMapper.findTeamBYI(tid);
        }catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public Team findTeamBYN(String tName) {
        try {
            return teamMapper.findTeamBYN(tName);
        }catch (Exception e)
        {
            return null;
        }
    }

}
