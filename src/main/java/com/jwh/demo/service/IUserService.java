package com.jwh.demo.service;

import com.jwh.demo.model.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    void insert(User user);

    User detail(Long id);
}
