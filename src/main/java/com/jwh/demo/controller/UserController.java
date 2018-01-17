package com.jwh.demo.controller;

import com.jwh.demo.CommonRequest;
import com.jwh.demo.model.User;
import com.jwh.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    public Object findAll(CommonRequest request){
        return userService.insertAll();
    }

    public void insert(CommonRequest request){
        User user = request.getJson().toJavaObject(User.class);
        userService.insert(user);
    }

    public User detail(CommonRequest request){
        Long id = request.getJson().getLong("id");
        return userService.detail(id);
    }
}
