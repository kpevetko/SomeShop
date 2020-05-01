package com.example.popra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface UserService {
    boolean createNewUser(String name,String pass);

    boolean validate(String name);
}
