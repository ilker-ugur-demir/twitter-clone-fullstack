package com.workintech.twitter.service;

import com.workintech.twitter.dto.TweetRequest;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository,
                        UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    // ✅ Tweet oluştur
    public Tweet create(TweetRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User bulunamadı"));

        Tweet tweet = new Tweet();
        tweet.setContent(request.getContent());
        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }

    // ✅ Kullanıcının tweetleri
    public List<Tweet> findByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    // ✅ Tweet sil
    public void delete(Long tweetId, Long userId) {

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        // sadece sahibi silebilir
        if (!tweet.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bu tweeti silemezsin");
        }

        tweetRepository.delete(tweet);
    }

    // ✅ Tweet update (bonus ama önemli)
    public Tweet update(Long id, TweetRequest request) {

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        tweet.setContent(request.getContent());

        return tweetRepository.save(tweet);
    }
}