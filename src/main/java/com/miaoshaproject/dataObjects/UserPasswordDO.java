package com.miaoshaproject.dataObjects;

import org.springframework.stereotype.Component;

@Component
public class UserPasswordDO {
    private Integer id;

    private String encryedPassword;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncryedPassword() {
        return encryedPassword;
    }

    public void setEncryedPassword(String encryedPassword) {
        this.encryedPassword = encryedPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}