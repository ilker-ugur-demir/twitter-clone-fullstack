package com.workintech.twitter.service;

import com.workintech.twitter.dto.RetweetRequest;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public RetweetService(RetweetRepository retweetRepository,
                          UserRepository userRepository,
                          TweetRepository tweetRepository) {
        this.retweetRepository = retweetRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public Retweet retweet(RetweetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Tweet tweet = tweetRepository.findById(request.getTweetId())
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        retweet.setCreatedAt(LocalDateTime.now());

        return retweetRepository.save(retweet);
    }

    public void delete(Long id) {
        Retweet retweet = retweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retweet bulunamadı"));

        retweetRepository.delete(retweet);
    }
}