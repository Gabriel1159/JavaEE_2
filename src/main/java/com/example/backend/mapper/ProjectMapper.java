package com.example.backend.mapper;

import com.example.backend.pojo.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProjectMapper {
    int addProject(Project project);

    int renameProject(String newName, String proID);

    int reDescribeProject(String newDes, String proID);

    List<Project> showProjects();

    int deleteProject(String proID);

    List<Project> showProjectsDustbin();

    int deleteProjectInDustbin(String proID);

    int findProByID(String proID);

    int checkName(String name, String id);

    int checkDescribe(String des, String pid);

    int findProByIDNotDeleted(String proID);

    int findProByIDDeleted(String proID);

    int findTeamIDbyProID(String id);

    int dustbinToProject(String proID);

    Project searchProjectByProID(String proID);

    String getProTexts(String proID);

    int updateProTexts(String proID, int textID);

    Project getProjectByID(String projectId);

    ArrayList<Project> vagueFind(String searchText);

    ArrayList<Project> accurateFind(String name);

    ArrayList<String> getAllProName();

    ArrayList<Project> showProjectsInTeam(int tid);
}
