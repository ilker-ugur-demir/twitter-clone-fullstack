package com.workintech.twitter.controller;

import com.workintech.twitter.dto.RetweetRequest;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.service.RetweetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final RetweetService retweetService;

    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public Retweet retweet(@RequestBody RetweetRequest request) {
        return retweetService.retweet(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        retweetService.delete(id);
    }
}