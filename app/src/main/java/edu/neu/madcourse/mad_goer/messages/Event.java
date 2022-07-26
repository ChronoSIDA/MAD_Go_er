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
    private User host;

    private ArrayList<User> attendingList;
    private int save;
    private int Capacity;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getEventPassword() {
        return eventPassword;
    }

    public void setEventPassword(int eventPassword) {
        this.eventPassword = eventPassword;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EventType getCategory() {
        return category;
    }

    public void setCategory(EventType category) {
        this.category = category;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public ArrayList<User> getAttendingList() {
        return attendingList;
    }

    public void setAttendingList(ArrayList<User> attendingList) {
        this.attendingList = attendingList;
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

// TODO: add theme icon

    public Event(String name, EventType eventType) {
        this.eventName = name;
        this.category = eventType;
    }
}
