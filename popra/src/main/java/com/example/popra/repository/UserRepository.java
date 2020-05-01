package com.example.popra.repository;

import com.example.popra.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "prototype")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String login);
}
