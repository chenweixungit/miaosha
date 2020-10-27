package com.miaoshaproject.service;

import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.scripting.bsh.BshScriptUtils;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BussinessException;
    UserModel validateLogin(String telephone, String encrypedPassword) throws BussinessException;
}
