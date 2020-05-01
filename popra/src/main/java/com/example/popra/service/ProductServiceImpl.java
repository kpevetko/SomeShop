package com.example.popra.service;

import com.example.popra.model.Product;
import com.example.popra.model.Purchase;
import com.example.popra.repository.ProductRepository;
import com.example.popra.repository.PurchasesRepository;
import com.example.popra.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final PurchasesRepository purchasesRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PurchasesRepository purchasesRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.purchasesRepository = purchasesRepository;
        this.userRepository = userRepository;
    }


    @Override
    public boolean createNewProduct(String name, String description, Integer cost, Integer number) {
        logger.info(String.format("Создания нового товара [название: %s описание: %s стоимость: %s количество%s] (начало)"
                , name, description, cost, number));
        try {
            if (name != null && description != null && cost != null && number != null) {
                Product product = new Product();
                product.setName(name);
                product.setDescription(description);
                product.setCost(cost);
                product.setNumber(number);
                productRepository.save(product);
                logger.info(String.format("Создания нового товара [название: %s описание: %s стоимость: %s количество%s] (успешно," +
                        " товар добавлен в БД)", name, description, cost, number));
            } else {
                logger.info(String.format("Создания нового товара [название: %s описание: %s стоимость: %s количество%s] (неудачно, " +
                                "одно из полей объявлено NULL)"
                        , name, description, cost, number));
                return false;
            }
        } catch (Exception e) {
            logger.error(String.format("Создания нового товара [название: %s описание: %s стоимость: %s количество%s] (ошибка, " +
                            "[%s] %s)"
                    , name, description, cost, number, e.getMessage(), e));
            return false;
        }

        return true;
    }

    @Override
    public boolean updateProduct(Integer id, String name, String description, Integer cost, Integer number) {
        logger.info(String.format("Изменение товара [название: %s описание: %s стоимость: %s количество%s] (начало)"
                , name, description, cost, number));
        try {
            if (id != null && name != null && description != null && cost != null && number != null) {
                productRepository.updateProductById(id, name, description, cost, number);
                logger.info(String.format("Изменение товара [название: %s описание: %s стоимость: %s количество%s] (успешно," +
                        " товар обновлен)", name, description, cost, number));
            } else {
                logger.info(String.format("Изменение товара [название: %s описание: %s стоимость: %s количество%s] (неудачно, " +
                                "одно из полей объявлено NULL)"
                        , name, description, cost, number));
                return false;
            }
        } catch (Exception e) {
            logger.error(String.format("Изменение товара [название: %s описание: %s стоимость: %s количество%s] (ошибка, " +
                            "[%s] %s)"
                    , name, description, cost, number, e.getMessage(), e));
            return false;
        }

        return true;
    }


    //показывает все продукты сортируя их
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAllByNumberGreaterThanOrderByIdAsc(0);
    }

    @Override
    public boolean deleteProduct(Integer id) {
        logger.info(String.format("Удаление товара [id: %s] (начало)", id));
        try {
            productRepository.deleteById(id);
            logger.info(String.format("Удаление товара [id: %s] (успешно)", id));
        } catch (Exception e) {
            logger.error(String.format("Удаление товара [id: %s] (ошибка, [%s] %s)", id, e.getMessage(), e));
            return false;
        }
        return true;
    }

    @Override
    public boolean buyProduct(String name, Integer numOf) {
        logger.info(String.format("Покупка товара [название: %s количество: %s] (начало)", name, numOf));
        try {
            Product product = productRepository.getProductByName(name);
            if (product.getNumber() >= numOf) {
                Purchase purchase = new Purchase();
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
                    UserDetails userDetail = (UserDetails) auth.getPrincipal();
                    purchase.setIdUser(userRepository.findByUsername(userDetail.getUsername()).getId());
                }
                purchase.setIdProduct(product.getId());
                purchase.setDate(new Date());
                purchase.setCostAll(product.getCost() * numOf);
                purchase.setNumberAll(numOf);
                productRepository.updateProductByName(name, numOf);
                purchasesRepository.save(purchase);
                logger.info(String.format("Покупка товара [название: %s количество: %s] (успешно)", name, numOf));
            } else {
                logger.info(String.format("Покупка товара [название: %s количество: %s] (неудачно, недостаточно товара)", name, numOf));
                return false;
            }
        } catch (Exception e) {
            logger.error(String.format("Покупка товара [название: %s количество: %s] (ошибка, [%s] %s)", name, numOf, e.getMessage(), e));
            return false;
        }

        return true;
    }
}
