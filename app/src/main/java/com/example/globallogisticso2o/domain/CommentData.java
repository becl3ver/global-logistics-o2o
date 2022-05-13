package com.example.globallogisticso2o.domain;

import java.util.ArrayList;

public class CommentData {
    private String comment;

    private ArrayList<String> nestedComment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getNestedComment() {
        return nestedComment;
    }

    public void setNestedComment(ArrayList<String> nestedComment) {
        this.nestedComment = nestedComment;
    }
}
