package com.example.popra.controller;

import com.example.popra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //регистрация ввод
    @PostMapping("/registration")
    // @ResponseBody
    public String postToRegisterPage(
            @RequestParam(value = "username") String login,
            @RequestParam(value = "password") String pass) {

        boolean created = userService.createNewUser(login, pass);
        String answerPage;

        answerPage = created ? "redirect:/?regOk" :  "redirect:/registration?error";

        return answerPage;
    }

}
