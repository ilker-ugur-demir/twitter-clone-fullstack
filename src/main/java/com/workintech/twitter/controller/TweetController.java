package com.workintech.twitter.controller;

import com.workintech.twitter.dto.TweetRequest;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    // ✅ Tweet oluştur
    @PostMapping
    public Tweet create(@RequestBody TweetRequest request) {
        return tweetService.create(request);
    }

    // ✅ Kullanıcının tweetleri
    @GetMapping("/findByUserId")
    public List<Tweet> findByUserId(@RequestParam Long userId) {
        return tweetService.findByUserId(userId);
    }

    // ✅ Tweet sil
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @RequestParam Long userId) {
        tweetService.delete(id, userId);
    }

    // (İstersen bonus)
    @PutMapping("/{id}")
    public Tweet update(@PathVariable Long id,
                        @RequestBody TweetRequest request) {
        return tweetService.update(id, request);
    }
}