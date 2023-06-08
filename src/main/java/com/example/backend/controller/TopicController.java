package com.example.backend.controller;

import com.example.backend.pojo.Topic;
import com.example.backend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
@Controller
@ResponseBody
public class TopicController {
    @Autowired
    TopicService topicService;

    @GetMapping("/single/{t_id}/{u_id}")
    public Topic getTopicById(@PathVariable("t_id")String t_id, @PathVariable("u_id")String u_id)
    {
        return topicService.getTopicById(u_id, t_id);
    }

    @PostMapping("/publish")
    public int addTopic(String u_id,String t_title,String t_introduce,String t_content,int t_kind)
    {
        Topic topic = new Topic();
        topic.setT_uid(u_id);
        topic.setT_title(t_title);
        topic.setT_introduce(t_introduce);
        topic.setT_content(t_content);
        topic.setT_kind(t_kind);


        Date date = new Date(System.currentTimeMillis());
        topic.setCreate_time(date);
        if(topicService.addTopic(topic)>0)
        {
            return 0;
        }
        return 1;
    }
    @PostMapping("/delete_tiezi")
    public int deleteTopic(int t_id, String u_id)
    {
        if(topicService.deleteTopic(String.valueOf(t_id),u_id)>0)
        {
            return 0;
        }
        return 1;
    }

    @PostMapping("/like")
    public int Like(String type,int t_id,String u_id)
    {
        return topicService.Like(type,t_id,u_id);
    }
    @PostMapping("/comment")
    public int addComment(int t_id,String u_id,String c_content)
    {
        if(topicService.addComment(t_id,u_id,c_content)>0)
        {
            return 0;
        }
        return 1;
    }
    @PostMapping("/delete_comment")
    public int deleteComment(int del_c_id,int t_id,String u_id)
    {
        if(topicService.deleteComment(del_c_id,t_id,u_id)>0)
        {
            return 0;
        }
        return 1;
    }

    @PostMapping("/search")
    public ArrayList<Topic> searchTopics(String key)
    {
        return topicService.searchTopics(key);
    }

    @PostMapping("/home")
    public ArrayList<Topic> recommendTopics()
    {
        ArrayList<Topic> ret = topicService.getTopics();
        ret.sort(Comparator.comparing(Topic::getT_likes).reversed());
        return ret;
    }

    @PostMapping("/course")
    public ArrayList<Topic> courseTopics()
    {
        ArrayList<Topic> ret = topicService.getCourseTopics();
        ret.sort(Comparator.comparing(Topic::getT_likes).reversed());
        return ret;
    }

    @PostMapping("/question")
    public ArrayList<Topic> quesTopics()
    {
        ArrayList<Topic> ret = topicService.getQuesTopics();
        ret.sort(Comparator.comparing(Topic::getT_likes).reversed());
        return ret;
    }

    @PostMapping("/course")
    public ArrayList<Topic> aroundTopics()
    {
        ArrayList<Topic> ret = topicService.getAroundTopics();
        ret.sort(Comparator.comparing(Topic::getT_likes).reversed());
        return ret;
    }

    @PostMapping("/resource")
    public ArrayList<Topic> resourceTopics()
    {
        ArrayList<Topic> ret = topicService.getResourceTopics();
        ret.sort(Comparator.comparing(Topic::getT_likes).reversed());
        return ret;
    }
}
