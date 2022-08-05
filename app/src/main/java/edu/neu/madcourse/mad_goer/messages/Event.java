package edu.neu.madcourse.mad_goer.messages;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

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
    private LatLng location;

    private String desc;
    private EventType category;
    private int Capacity;
    private int duration;

    //to link host
    private User host;

    public Event() {
    }

    public Event(String eventID, String eventName, Date startDate, boolean inPerson, boolean isPublic, String eventPassword, String link, LatLng location, String desc, EventType category, User host, ArrayList<User> attendingList, int save, int capacity) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.startDate = startDate;
        this.inPerson = inPerson;
        this.isPublic = isPublic;
        this.eventPassword = eventPassword;
        this.link = link;
        this.location = location;
        this.desc = desc;
        this.category = category;
        this.host = host;
        this.attendingList = attendingList;
        this.save = save;
        Capacity = capacity;
    }

    private ArrayList<User> attendingList = new ArrayList<>();
    private int save;

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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int dura) {
        duration = dura;
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
