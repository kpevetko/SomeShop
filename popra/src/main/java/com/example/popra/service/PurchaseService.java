package com.example.popra.service;

import com.example.popra.model.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface PurchaseService {

    Iterable<Purchase> findAll();
}
