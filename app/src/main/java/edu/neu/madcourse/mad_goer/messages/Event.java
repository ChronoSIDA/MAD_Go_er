package edu.neu.madcourse.mad_goer.messages;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    //TODO: add constructor appropriately, eventID = eventName+enddata
    //also notice if private/public, some parameters might not be needed
    private String eventID;
    private String eventName;
    private Date startDate;

    //if false then virtual
    private boolean inPerson;

    //if ture, public, if false private
    private boolean isPublic;
    //only for private event
    private String eventPassword;

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

    public void addUserToAttendingList(User user){
        this.attendingList.add(user);
    }

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

    public String getEventPassword() {
        return eventPassword;
    }

    public void setEventPassword(String eventPassword) {
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

    public Boolean verifyPrivatePassword(String password){
        return this.eventPassword.equals(password);
    }
}
