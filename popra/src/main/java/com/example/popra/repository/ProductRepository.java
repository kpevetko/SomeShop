package com.example.popra.repository;

import com.example.popra.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Transactional
    @Modifying
    @Query("update Product p set p.name= ?2, p.description = ?3, p.cost = ?4, p.number = ?5 where p.id = ?1")
    void updateProductById(int id, String name, String description, int cost, int number);

    @Transactional
    void deleteById(int id);

    Iterable<Product> findAllByOrderByIdAsc();

    Product getProductByName(String name);

    @Modifying
    @Query("update Product p set p.number = (p.number - ?2) where p.name = ?1")
    void updateProductByName(String name, int number);
}
