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
    @GetMapping("/signIn")
    public String enterPage() {
        return "signIn";
    }

    //регистрация показ
    @GetMapping("/signUp")
    public String takeRegisterPage() {
        return "signUp";
    }

    @PostMapping("/signIn")
    public String a(){
        System.out.println("ad");
        return "";
    }

// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
//            UserDetails userDetail = (UserDetails) auth.getPrincipal();
//            System.out.println(userDetail.getUsername());
//        }

    //регистрация ввод
    @PostMapping("/signUp")
    @ResponseBody
    public ResponseEntity<?> putRegisterPage(
            @RequestParam(value = "username") String login,
            @RequestParam(value = "password") String pass) {

        boolean created = userService.createNewUser(login, pass);

        return created ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
