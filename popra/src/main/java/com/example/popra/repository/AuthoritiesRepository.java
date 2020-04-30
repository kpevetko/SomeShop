package com.example.popra.repository;

import com.example.popra.model.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends CrudRepository<Authority, Long> {

}
