package com.example.popra.controller;

import com.example.popra.model.Product;
import com.example.popra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final ProductService productService;

    @Autowired
    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getUserPage(Model model){
        List<Product> allProduct = (List<Product>) productService.findAll();
        model.addAttribute("products", allProduct);
        return "userPage";
    }

    //покупаем продукт
    @PutMapping("/products")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestParam(value = "name", required = false) String  name,
                                           @RequestParam(value = "numOf", required = false) Integer numOf) {
        boolean created = productService.buyProduct(name, numOf);
        return created ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }



}
