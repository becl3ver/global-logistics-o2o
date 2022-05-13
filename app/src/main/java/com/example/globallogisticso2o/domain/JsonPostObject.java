package com.example.globallogisticso2o.domain;

import java.util.ArrayList;

public class JsonPostObject {
    private int category;
    private ArrayList<PostData> postDatas;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public ArrayList<PostData> getPostDatas() {
        return postDatas;
    }

    public void setPostDatas(ArrayList<PostData> postDatas) {
        this.postDatas = postDatas;
    }
}