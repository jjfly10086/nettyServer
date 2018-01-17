package com.jwh.demo.service.impl;

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
    public List<User> insertAll() {
        User user = new User();
        user.setId(10L);
        userRepository.insert(user.getId(),user.getUserName(),user.getUserPass());
        user.setId(5L);
        userRepository.insert(user.getId(),user.getUserName(),user.getUserPass());
//        throw new RuntimeException();
        return userRepository.findAll();
    }

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "tenSecondTerm",key = "#id")
    public User detail(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }
}
