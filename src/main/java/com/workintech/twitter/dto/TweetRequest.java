package com.workintech.twitter.dto;

import lombok.Data;

@Data
public class TweetRequest {

    private String content;
    private Long userId;
}