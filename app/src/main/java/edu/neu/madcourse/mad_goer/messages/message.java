package edu.neu.madcourse.mad_goer.messages;

import java.util.Date;

public class message {

    private String comment;
    private User user;
    private Date time;
    private Event event;
    private int likes;

    public message() {
    }

    public message(String comment, User user, Date time, Event event) {
        this.comment = comment;
        this.user = user;
        this.time = time;
        this.event = event;
        this.likes = 0;
    }



}

