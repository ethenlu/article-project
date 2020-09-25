package com.example.vo;

import com.example.domain.Job;
import com.example.domain.User;

import java.util.List;

public class Saveredisvo {

    private User user;

    private List<Job> list;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Job> getList() {
        return list;
    }

    public void setList(List<Job> list) {
        this.list = list;
    }

}
