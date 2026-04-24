package com.workintech.twitter.controller;

import com.workintech.twitter.dto.RegisterRequest;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }
}