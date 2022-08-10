package edu.neu.madcourse.mad_goer.messages;

import java.sql.Timestamp;

public class Comment {

    private String comment;
    private String user;
    private Long time;
    private String eventName;
    private int likes;

    public Comment() {
    }

    public Comment(String comment, String userID, Long time, String eventName) {
        this.comment = comment;
        this.user = userID;
        this.time = time;
        this.eventName = eventName;
        this.likes = 0;
    }

    public String getComment(){
        return this.comment;
    }
    public String getUser(){
        return this.user;
    }

    public Long getTime(){
        return this.time;
    }

    public String getEventName(){
        return this.eventName;
    }

    public int getLikes() {
        return this.likes;
    }
    public void setLikes(int likes) {this.likes = likes;}
    public void likesPlusOne(){
        this.likes += 1;
    }

}

