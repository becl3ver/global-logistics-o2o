package com.example.globallogisticso2o.domain;

import java.util.ArrayList;

public class PostData {
    private int category; // 게시판 분류
    private long postId, uid; // 글 번호, 유저번호(id랑 별개)

    private String postTitle;
    private String postContent;
    private String nickname;
    private String postDate; // 글 이름, 글 본문, 닉네임

    private ArrayList<CommentData> comments;

    public PostData() {}

    public PostData(String postTitle, String postContent, long uid) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.uid = uid;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public ArrayList<CommentData> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentData> comments) {
        this.comments = comments;
    }
}
