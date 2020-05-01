package com.example.popra.service;

import com.example.popra.model.Purchase;
import com.example.popra.repository.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchasesRepository purchasesRepository;

    @Autowired
    public PurchaseServiceImpl(PurchasesRepository purchasesRepository) {
        this.purchasesRepository = purchasesRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<Purchase> findAll() {
        return purchasesRepository.findAllByOrderByIdAsc();
    }
}
