package edu.neu.madcourse.mad_goer.messages;

import java.util.Date;

public class Comment {

    private String comment;
    private String userID;
    private Date timeStamp;
    private Event event;
    private int likes;

    public Comment() {
    }

    public Comment(String comment, String userID, Date timeStamp, Event event) {
        this.comment = comment;
        this.userID = userID;
        this.timeStamp = timeStamp;
        this.event = event;
        this.likes = 0;
    }

    public String getComment(){
        return this.comment;
    }
    public String getUser(){
        return this.userID;
    }

    public Date getTime(){
        return this.timeStamp;
    }

    public Event getEvent(){
        return this.event;
    }

    public int getLikes() {
        return this.likes;
    }
    public void likesPlusOne(){
        this.likes += 1;
    }

}

