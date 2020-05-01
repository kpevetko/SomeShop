package com.example.popra.repository;

import com.example.popra.model.Authority;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "prototype")
public interface AuthoritiesRepository extends CrudRepository<Authority, Long> {

}
