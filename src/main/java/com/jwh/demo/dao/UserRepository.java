package com.jwh.demo.dao;

import com.jwh.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    //nativeQuery=true原生sql
    @Modifying
    @Query(value = "INSERT INTO `user` (`id`,`user_name`,`user_pass`) VALUES (?1,?2,?3)",nativeQuery = true)
    int insert(Long id,String userName,String userPass);
}
