package edu.neu.madcourse.mad_goer.messages;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    private String eventID;
    private String eventName;

    private Date startDate;
    private Date endDate;

    //if false then virtual
    private boolean inPerson;

    //if ture, public, if false private
    private boolean isPublic;
    //only for private event
    private int eventPassword;

    //vitual: link
    private String link;
    //inperson:
    private Location location;

    private String desc;
    private EventType category;

    //to link host
    private User user;

    private ArrayList<User> attendingList;
    private int save;
    private int Capacity;


    // TODO: add theme icon

    public Event() {

    }
}
