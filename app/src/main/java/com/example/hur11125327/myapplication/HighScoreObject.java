package com.example.hur11125327.myapplication;

/**Created by hur11125327 on 08/11/2015.**/

public class HighScoreObject {
    private String name;
    private Integer score;
    private Long timestamp;

    public HighScoreObject(Integer score, String name, Long timestamp){
        this.score = score;
        this.name = name;
        this.timestamp = timestamp;
    }


    public HighScoreObject(){}

    public String getName() {
        return name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Integer getScore() {
        return score;
    }
}