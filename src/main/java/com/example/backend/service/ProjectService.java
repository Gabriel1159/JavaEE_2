package com.example.backend.service;

import com.example.backend.mapper.ProjectMapper;
import com.example.backend.pojo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    public List<Project> showProjects() {
        try {
            return projectMapper.showProjects();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addProject(Project project) {
        try {
            return projectMapper.addProject(project);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int renameProject(String newName, String proID) {
        try {
            return projectMapper.renameProject(newName, proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int reDescribeProject(String newDes, String proID) {
        try {
            return projectMapper.reDescribeProject(newDes, proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int deleteProject(String proID) {
        try {
            return projectMapper.deleteProject(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Project> showProjectsDustbin() {
        try {
            return projectMapper.showProjectsDustbin();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int deleteProjectsInDustbin(String proID) {
        try {
            return projectMapper.deleteProjectInDustbin(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int findProByID(String proID) {
        try {
            return projectMapper.findProByID(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int checkName(String name, String id) {
        try {
            return projectMapper.checkName(name, id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int checkDescribe(String des, String pid)
    {
        try {
            return projectMapper.checkDescribe(des, pid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int findProByIDNotDeleted(String proID) {
        try {
            return projectMapper.findProByIDNotDeleted(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int findProByIDDeleted(String proID) {
        try {
            return projectMapper.findProByIDDeleted(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int findTeamIDbyProID(String id) {
        try {
            return projectMapper.findTeamIDbyProID(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int dustbinToProject(String proID) {
        try {
            return projectMapper.dustbinToProject(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Project searchProjectByProID(String proID) {
        try {
            return projectMapper.searchProjectByProID(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getProTexts(String proID) {
        try {
            return projectMapper.getProTexts(proID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateProTexts(String proID, int textID) {
        try {
            return projectMapper.updateProTexts(proID, textID);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Project getProjectByID(String projectId) {
        try {
            return projectMapper.getProjectByID(projectId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Project> vagueFind(String searchText) {
        try {
            return projectMapper.vagueFind(searchText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Project> accurateFind(String name) {
        try {
            return projectMapper.accurateFind(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Project> showProjectsInTeam(int tid) {
        try {
            return projectMapper.showProjectsInTeam(tid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}