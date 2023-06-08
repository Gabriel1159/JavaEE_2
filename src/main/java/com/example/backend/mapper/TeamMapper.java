package com.example.backend.mapper;

import com.example.backend.pojo.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface TeamMapper {
    int addTeam(Team team);
    int delTeam(Team team);
    int updateTeam(Team team);
    Team findTeamBYI(@Param("f_tid") int f_tid);
    Team findTeamBYN(@Param("f_tName") String f_tName);
}
