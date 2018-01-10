package com.jwh.demo.controller;

import com.jwh.demo.CommonRequest;
import com.jwh.demo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public Object findAll(CommonRequest request){
        return userRepository.findAll();
    }
}
