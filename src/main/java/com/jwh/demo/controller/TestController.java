package com.jwh.demo.controller;

import com.jwh.demo.CommonRequest;
import com.jwh.demo.dao.UserRepository;
import com.jwh.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);



    @Autowired
    private UserRepository userRepository;

    public Object findAll(CommonRequest request){
        return userRepository.findAll();
    }

    public void insert(CommonRequest request){
        User user = new User();
        user.setId(1L);
        user.setUserName("1");
        user.setUserPass("11111");
        userRepository.save(user);
    }
}
