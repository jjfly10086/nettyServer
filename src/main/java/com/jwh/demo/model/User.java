package com.jwh.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pass")
    private String userPass;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "header_image")
    private String headerImage;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "tel_phone")
    private String telPhone;

    @Column(name = "address")
    private String address;

    @Column(name = "created_time")
    private Date createdTime;

    public User() {

    }

    public User(Long id,String userName, String userPass, String nickName, String headerImage, Integer gender, String telPhone, String address, Date createdTime) {
        this.id = id;
        this.userName = userName;
        this.userPass = userPass;
        this.nickName = nickName;
        this.headerImage = headerImage;
        this.gender = gender;
        this.telPhone = telPhone;
        this.address = address;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
