package com.jwh.demo.controller;

import com.jwh.demo.CommonRequest;
import com.jwh.demo.model.User;
import com.jwh.demo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.Future;


@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    public Object findAll(CommonRequest request){
        return userService.findAll();
    }

    public void insert(CommonRequest request) throws Exception{
        User user = request.getJson().toJavaObject(User.class);
        userService.insert(user);
    }

    public User detail(CommonRequest request){
        Long id = request.getJson().getLong("id");
        return userService.detail(id);
    }

    public void test(CommonRequest request){
        logger.info(Thread.currentThread().getName());
    }

    public List<User> get(CommonRequest request) throws Exception{
        Long id = request.getJson().getLong("id");
        List<User> users = userService.get(id);
        return users;
    }
}
