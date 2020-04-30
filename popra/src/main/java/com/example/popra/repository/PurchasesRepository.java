package com.example.popra.repository;

import com.example.popra.model.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepository extends CrudRepository<Purchase, Long> {
    Iterable<Purchase> findAllByOrderByIdAsc();


}
