package com.example.popra.service;

import com.example.popra.model.Purchase;


public interface PurchaseService {

    Iterable<Purchase> findAll();
}
