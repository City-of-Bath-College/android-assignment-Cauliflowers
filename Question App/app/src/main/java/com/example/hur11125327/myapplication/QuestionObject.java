package com.example.hur11125327.myapplication;

/**Created by hur11125327 on 01/11/2015.**/

public class QuestionObject {
    //Variable Declaration.
    private String question;
    private boolean answer;
    private int picture;

    public QuestionObject(String question, boolean answer, int picture){
        this.question = question;
        this.answer = answer;
        this.picture = picture;
    }
    public String getQuestion(){
        return question;
    }
    public boolean isAnswer(){
        return answer;
    }
    public int getPicture(){
        return picture;
    }
}