package com.example.globallogisticso2o.domain;

import java.util.ArrayList;

public class PostRequest {
    private String nickname, keyword; // 닉네임, 키워드로 검색
    private int id; // 자기가 작성한 글 검색

    private int category; // 분류
    private ArrayList<Integer> postIds; // 북마크(글 목록)

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public ArrayList<Integer> getPostIds() {
        return postIds;
    }

    public void setPostIds(ArrayList<Integer> postIds) {
        this.postIds = postIds;
    }
}
