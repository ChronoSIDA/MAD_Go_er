package edu.neu.madcourse.mad_goer.messages;

import java.util.Date;

public class Comment {

    private String comment;
    private String userID;
    private Date timeStamp;
    private String eventName;
    private int likes;

    public Comment() {
    }

    public Comment(String comment, String userID, Date timeStamp, String eventName) {
        this.comment = comment;
        this.userID = userID;
        this.timeStamp = timeStamp;
        this.eventName = eventName;
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

    public String getEventName(){
        return this.eventName;
    }

    public int getLikes() {
        return this.likes;
    }
    public void likesPlusOne(){
        this.likes += 1;
    }

}

