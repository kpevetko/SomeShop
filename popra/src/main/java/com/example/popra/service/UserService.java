package com.example.popra.service;

public interface UserService {
    boolean createNewUser(String name,String pass);

    boolean validate(String name);
}
