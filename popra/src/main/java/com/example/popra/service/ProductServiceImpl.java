package com.example.popra.service;

import com.example.popra.model.Product;
import com.example.popra.model.Purchase;
import com.example.popra.repository.ProductRepository;
import com.example.popra.repository.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final PurchasesRepository purchasesRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PurchasesRepository purchasesRepository) {
        this.productRepository = productRepository;
        this.purchasesRepository = purchasesRepository;
    }

    @Override
    public boolean createNewProduct(String name, String description, Integer cost, Integer number) {
        try {
            if (name != null && description != null && cost != null && number != null) {
                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setCost(cost);
                product.setNumber(number);
                productRepository.save(product);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean updateProduct(Integer id, String name, String description, Integer cost, Integer number) {
        try {
            if (id != null && name != null && description != null && cost != null && number != null) {
                productRepository.updateProductById(id, name, description, cost, number);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    //показывает все продукты сортируя их
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAllByOrderByIdAsc();
    }

    @Override
    public boolean deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean buyProduct(String name, Integer numOf) {
        try {
            Product product = productRepository.getProductByName(name);
            if (product.getNumber() >= numOf) {
                productRepository.updateProductByName(name, numOf);
                Purchase purchase = new Purchase();
                purchase.setIdUser(1);//ПЕРЕДЕЛАТЬ СТАТИКУ ЭТУ НА НОРМАЛЬНЫЙ КОД, ПОДСТАВЛЯТЬ НУЖНО ИД ЮЗЕРА
                purchase.setIdProduct(product.getId());
                purchase.setDate(new Date());
                purchase.setCostAll(product.getCost()*numOf);
                purchase.setNumberAll(numOf);
                purchasesRepository.save(purchase);

            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
