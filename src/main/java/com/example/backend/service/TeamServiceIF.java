package com.example.backend.service;

import com.example.backend.pojo.Team;

public interface TeamServiceIF {
    int addTeam(Team team);
    int delTeam(Team team);
    int updateTeam(Team team);
    Team findTeamBYI(int tid);
    Team findTeamBYN(String tname);
}
