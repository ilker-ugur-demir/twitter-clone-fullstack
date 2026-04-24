package com.workintech.twitter.service;

import com.workintech.twitter.dto.CommentRequest;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.CommentRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          TweetRepository tweetRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public Comment create(CommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Tweet tweet = tweetRepository.findById(request.getTweetId())
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setTweet(tweet);

        return commentRepository.save(comment);
    }

    public Comment update(Long id, CommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));

        if (!comment.getUser().getId().equals(request.getUserId())) {
            throw new RuntimeException("Bu yorumu sadece yorum sahibi güncelleyebilir");
        }

        comment.setContent(request.getContent());

        return commentRepository.save(comment);
    }

    public void delete(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));

        Long commentOwnerId = comment.getUser().getId();
        Long tweetOwnerId = comment.getTweet().getUser().getId();

        if (!commentOwnerId.equals(userId) && !tweetOwnerId.equals(userId)) {
            throw new RuntimeException("Bu yorumu sadece tweet sahibi veya yorum sahibi silebilir");
        }

        commentRepository.delete(comment);
    }
}