package com.workintech.twitter.service;

import com.workintech.twitter.dto.LikeRequest;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public LikeService(LikeRepository likeRepository,
                       UserRepository userRepository,
                       TweetRepository tweetRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public Like like(LikeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Tweet tweet = tweetRepository.findById(request.getTweetId())
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);

        return likeRepository.save(like);
    }

    public void dislike(Long userId, Long tweetId) {
        Like like = likeRepository.findByUserIdAndTweetId(userId, tweetId)
                .orElseThrow(() -> new RuntimeException("Like bulunamadı"));

        likeRepository.delete(like);
    }
}