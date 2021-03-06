package com.example.popra.service;

import com.example.popra.model.Product;



public interface ProductService {

    boolean createNewProduct(String name, String pass, Integer cost, Integer number);

    boolean updateProduct(Integer id, String name, String pass, Integer cost, Integer number);

    boolean buyProduct(String name, Integer numOf);

    Iterable<Product> findAll();

    boolean deleteProduct(Integer id);

}
