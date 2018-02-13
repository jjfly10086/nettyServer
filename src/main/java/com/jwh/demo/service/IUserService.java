package com.jwh.demo.service;

import com.jwh.demo.model.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    void insert(User user) throws Exception;

    User detail(Long id);

    List<User> get(Long id) throws Exception;
}
