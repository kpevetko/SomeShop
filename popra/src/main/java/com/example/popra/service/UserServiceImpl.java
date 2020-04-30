package com.example.popra.service;

import com.example.popra.model.Authority;
import com.example.popra.model.User;
import com.example.popra.repository.AuthoritiesRepository;
import com.example.popra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthoritiesRepository authoritiesRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthoritiesRepository authoritiesRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
    }


    @Override
    public boolean createNewUser(String name, String pass) {
        try {
            if(!validate(name)){
                return false;
            }
            User user = new User();
            user.setUsername(name);
            user.setPassword(pass);
            user.setEnabled(true);
            Authority authority = new Authority();
            authority.setUsername(name);
            authority.setAuthority("ROLE_USER");
            authoritiesRepository.save(authority);
            userRepository.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(String name) {
        return userRepository.findByUsername(name);
    }
}
