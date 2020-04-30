package com.example.popra.controller;

import com.example.popra.model.Product;
import com.example.popra.model.Purchase;
import com.example.popra.service.ProductService;
import com.example.popra.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final PurchaseService purchaseService;

    @Autowired
    public AdminController(ProductService productService, PurchaseService purchaseService) {
        this.productService = productService;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public String getAdminPage() {
        return "adminPage";
    }


    //получаем список всех продуктов к отображению
    @GetMapping("/products")
    public String getProductsOnPage(Model model) {
        List<Product> allProduct = (List<Product>) productService.findAll();
        model.addAttribute("products", allProduct);
        return "productsPage";
    }

    //добавляе новый продукт в БД
    @PostMapping("/products")
    @ResponseBody
    public ResponseEntity<?> addNewProduct(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "desc", required = false) String description,
                                           @RequestParam(value = "cost", required = false) Integer cost,
                                           @RequestParam(value = "number", required = false) Integer number) {
        boolean created = productService.createNewProduct(name, description, cost, number);
        return created ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //обновляет продукт в БД
    @PutMapping("/products")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestParam(value = "id", required = false) Integer id,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "desc", required = false) String description,
                                           @RequestParam(value = "cost", required = false) Integer cost,
                                           @RequestParam(value = "number", required = false) Integer number) {
        boolean created = productService.updateProduct(id, name, description, cost, number);
        return created ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    //удаляет продукт из БД
    @DeleteMapping("/products")
    @ResponseBody
    public ResponseEntity<?> deleteProduct(@RequestParam(value = "id", required = false) String id) {
        boolean created = productService.deleteProduct(Integer.parseInt(id));
        return created ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //показать все совершенные покупки
    @GetMapping("/purchases")
    public String getPurchasesPage(Model model) {
        List<Purchase> allPurchase = (List<Purchase>) purchaseService.findAll();
        model.addAttribute("purchases", allPurchase);
        return "purchasesPage";
    }

}
