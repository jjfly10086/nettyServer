package com.jwh.demo.service.impl;

import com.jwh.demo.annotation.TestAnnotation;
import com.jwh.demo.dao.UserRepository;
import com.jwh.demo.model.User;
import com.jwh.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @TestAnnotation
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @TestAnnotation
    public void insert(User user) {
        userRepository.insert(user.getUserName(),user.getUserPass(),
                user.getNickName(),user.getHeaderImage(),
                user.getGender(),user.getTelPhone(),
                user.getAddress(),user.getCreatedTime());
    }

    @Override
    @Cacheable(value = "tenSecondTerm",key = "#id")
    @TestAnnotation
    public User detail(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }
}
