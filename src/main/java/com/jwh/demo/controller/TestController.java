package com.jwh.demo.controller;

import com.jwh.demo.CommonRequest;
import com.jwh.demo.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);



    @Autowired
    private UserRepository userRepository;

    public Object test(CommonRequest request){
        return userRepository.findAll();
    }
}
