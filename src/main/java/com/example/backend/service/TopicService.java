package com.example.backend.service;

import com.example.backend.pojo.Topic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface TopicService {

    Topic getTopicById(String u_id, String t_id);



    int addTopic(Topic topic);

    int deleteTopic(String t_id, String u_id);


    int Like(String type, int t_id, String u_id);

    int addComment(int t_id, String u_id, String c_content);

    int deleteComment(int del_c_id, int t_id, String u_id);

    ArrayList<Topic> searchTopics(String key);

    ArrayList<Topic> getTopics();

    ArrayList<Topic> getCourseTopics();

    ArrayList<Topic> getQuesTopics();

    ArrayList<Topic> getAroundTopics();

    ArrayList<Topic> getResourceTopics();
}
