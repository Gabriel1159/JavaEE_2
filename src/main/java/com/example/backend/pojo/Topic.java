package com.example.backend.pojo;

import java.util.Date;
import java.util.List;

public class Topic {
    private String t_id;
    private String t_uid;

    private int t_kind;
    private Date create_time;
    private String t_photo;
    private String t_content;
    private String t_title;
    private String t_introduce;
    private Boolean recommend;
    private int t_likes;

    private int comment_number;

    private List<Comment> comment_list;

    public List<Comment> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<Comment> comment_list) {
        this.comment_list = comment_list;
    }

    public int getT_kind() {
        return t_kind;
    }

    public void setT_kind(int t_kind) {
        this.t_kind = t_kind;
    }

    public int getComment_number() {
        return comment_number;
    }

    public void setComment_number(int comment_number) {
        this.comment_number = comment_number;
    }



    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_uid() {
        return t_uid;
    }

    public void setT_uid(String t_uid) {
        this.t_uid = t_uid;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getT_photo() {
        return t_photo;
    }

    public void setT_photo(String t_photo) {
        this.t_photo = t_photo;
    }

    public String getT_content() {
        return t_content;
    }

    public void setT_content(String t_content) {
        this.t_content = t_content;
    }

    public String getT_title() {
        return t_title;
    }

    public void setT_title(String t_title) {
        this.t_title = t_title;
    }

    public String getT_introduce() {
        return t_introduce;
    }

    public void setT_introduce(String t_introduce) {
        this.t_introduce = t_introduce;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public int getT_likes() {
        return t_likes;
    }

    public void setT_likes(int t_likes) {
        this.t_likes = t_likes;
    }

    public Topic(String t_id, String t_uid, String t_title, String t_introduce, int t_likes) {
        this.t_id = t_id;
        this.t_uid = t_uid;
        this.t_title = t_title;
        this.t_introduce = t_introduce;
        this.t_likes = t_likes;
    }

    public Topic() {
    }
}
