package com.example.backend.service.Impl;

import com.example.backend.mapper.TopicMapper;
import com.example.backend.pojo.Comment;
import com.example.backend.pojo.Likes;
import com.example.backend.pojo.Topic;
import com.example.backend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicMapper topicMapper;

    @Override
    public Topic getTopicById(String u_id, String t_id) {
        Topic topic = topicMapper.getTopicById(u_id,t_id);
        int likeNum = topicMapper.getLikes(t_id);
        topic.setT_likes(likeNum);
        List<Comment> comment_list = topicMapper.getCommentList(t_id);
        topic.setComment_number(comment_list.size());
        topic.setComment_list(comment_list);
        return topic;
    }

    @Override
    public int addTopic(Topic topic) {
        return topicMapper.addTopic(topic);
    }

    @Override
    public int deleteTopic(String t_id, String u_id) {
        return topicMapper.deleteTopicById(u_id,t_id);
    }

    @Override
    public int Like(String type, int t_id, String u_id) {
        Likes like = new Likes();
        like.setT_id(String.valueOf(t_id));
        like.setL_uid(u_id);
        if(type.equals("like"))
        {
            if(topicMapper.addLike(like)>0)
            {
                return 0;
            }
            return 2;
        }
        else
        {
            if(topicMapper.deleteLike(like)>0)
            {
                return 1;
            }
            return 2;
        }
    }

    @Override
    public int addComment(int t_id, String u_id, String c_content) {
        Date date = new Date(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setC_content(c_content);
        comment.setC_tid(String.valueOf(t_id));
        comment.setC_uid(u_id);
        comment.setC_time(date);
        return topicMapper.addComment(comment);
    }

    @Override
    public int deleteComment(int del_c_id, int t_id, String u_id) {
        return topicMapper.deleteComment(String.valueOf(del_c_id),String.valueOf(t_id),u_id);
    }

    @Override
    public ArrayList<Topic> searchTopics(String key) {
        return topicMapper.searchTopics(key);
    }

    @Override
    public ArrayList<Topic> getTopics() {
        return topicMapper.getTopics();
    }

    @Override
    public ArrayList<Topic> getCourseTopics() {
        return topicMapper.getCourseTopics();
    }

    @Override
    public ArrayList<Topic> getQuesTopics() {
        return topicMapper.getQuesTopics();
    }

    @Override
    public ArrayList<Topic> getAroundTopics() {
        return topicMapper.getAroundTopics();
    }

    @Override
    public ArrayList<Topic> getResourceTopics() {
        return topicMapper.getResourceTopics();
    }


}
