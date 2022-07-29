package edu.neu.madcourse.mad_goer.messages;

import java.util.Date;

public class Comment {

    private String comment;
    private User user;
    private Date timeStamp;
    private Event event;
    private int likes;

    public Comment() {
    }

    public Comment(String comment, User user, Date timeStamp, Event event) {
        this.comment = comment;
        this.user = user;
        this.timeStamp = timeStamp;
        this.event = event;
        this.likes = 0;
    }

    public String getComment(){
        return this.comment;
    }
    public User getUser(){
        return this.user;
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
}

