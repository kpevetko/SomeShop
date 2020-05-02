package com.example.popra.controller;

import com.example.popra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mainPage() {
        return "index";
    }

    //вход показ
    @GetMapping("/login")
    public String enterPage() {
        return "login";
    }

    //регистрация показ
    @GetMapping("/registration")
    public String takeRegisterPage() {
        return "registration";
    }

    @PostMapping("/login")
    public String a(){
        System.out.println("ad");
        return "";
    }



    //регистрация ввод
    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<?> putRegisterPage(
            @RequestParam(value = "username") String login,
            @RequestParam(value = "password") String pass) {

        boolean created = userService.createNewUser(login, pass);

        return created ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
