package com.jwh.demo.dao;

import com.jwh.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    //nativeQuery=true原生sql
    @Modifying
    @Query(value = "INSERT INTO `user` (`user_name`,`user_pass`,`nick_name`,`header_image`,`gender`,`tel_phone`,`address`,`created_time`) " +
            "VALUES (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    int insert(String userName,
               String userPass,
               String nickName,
               String headerImage,
               Integer gender,
               String telPhone,
               String address,
               Date createdTime);
}
