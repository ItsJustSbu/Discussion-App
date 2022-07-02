package com.example.App2;

public class PostModel {
    String uid, pTitle, post;
    public PostModel() {
    }

    public PostModel(String uId, String pTitle, String post) {
        this.uid = uId;
        this.pTitle = pTitle;
        this.post = post;
    }

    public String getuId() {
        return uid;
    }

    public String getpTitle() {
        return pTitle;
    }

    public String getPost() {
        return post;
    }
}
