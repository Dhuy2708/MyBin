package com.demo_api.mybin.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User {
    private int id;
    private String userName;
    private String password;
    private String avatar;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    public User() {
    }

    public User(int id, String userName, String password, String avatar,
                String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

//    public User(int id, String avatar, String name, String email, String phoneNumber, String address) {
//        this.id = id;
//        this.avatar = avatar;
//        this.name = name;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//    }

    public User(int id, String userName, String password, String email, String phoneNumber, String address){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(int id, String userName, String password, String email, String name, String phoneNumber, String address){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
