package com.example.backend.mapper;

import com.example.backend.pojo.Comment;
import com.example.backend.pojo.Likes;
import com.example.backend.pojo.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface TopicMapper {
    Topic getTopicById(@Param("u_id") String u_id, @Param("t_id") String t_id);

    int deleteTopicById(@Param("u_id") String u_id, @Param("t_id") String t_id);

    int addTopic(Topic topic);

    int getLikes(String t_id);

    int addLike(Likes likes);

    int deleteLike(Likes likes);

    int addComment(Comment comment);

    int deleteComment(@Param("c_id")String c_id, @Param("c_tid") String c_tid, @Param("c_uid") String c_uid);

    List<Comment> getCommentList(@Param("c_tid") String c_tid);

    List<Topic> getTopicByUId(String u_id);


    ArrayList<Topic> searchTopics(String key);

    ArrayList<Topic> getTopics();

    ArrayList<Topic> getCourseTopics();

    ArrayList<Topic> getQuesTopics();

    ArrayList<Topic> getAroundTopics();

    ArrayList<Topic> getResourceTopics();
}
