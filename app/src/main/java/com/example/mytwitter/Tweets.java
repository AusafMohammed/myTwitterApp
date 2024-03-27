package com.example.mytwitter;

public class Tweets {
    String name;

    public Tweets(String name, String tweet) {
        this.name = name;
        this.tweet = tweet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    String tweet;
}
