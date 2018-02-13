package com.jwh.demo.service.impl;

import com.jwh.demo.annotation.Async;
import com.jwh.demo.annotation.TestAnnotation;
import com.jwh.demo.dao.UserRepository;
import com.jwh.demo.model.User;
import com.jwh.demo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @TestAnnotation
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Async
    public void insert(User user) throws Exception{
//        userRepository.insert(user.getUserName(),user.getUserPass(),
//                user.getNickName(),user.getHeaderImage(),
//                user.getGender(),user.getTelPhone(),
//                user.getAddress(),user.getCreatedTime());
        logger.info("insert method invoke start~");
        TimeUnit.SECONDS.sleep(10);
        logger.info("insert method invoke end~");
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

    @Override
    public List<User> get(Long id) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(8);
        List<Future<User>> futures = new ArrayList<>();
        for(int i=0;i<8;i++){
            Future<User> future = service.submit(new Callable<User>() {
                @Override
                public User call() throws Exception {
                    User user = userRepository.findById(id).get();
                    TimeUnit.SECONDS.sleep(10);
                    return user;
                }
            });
            futures.add(future);
        }
        List<User> users = new ArrayList<>();
        for(int i=0;i<8;i++){
            users.add(futures.get(i).get());
        }
        return users;
    }
}
