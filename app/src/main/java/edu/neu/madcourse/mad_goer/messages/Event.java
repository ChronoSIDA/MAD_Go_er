package edu.neu.madcourse.mad_goer.messages;

import android.location.Location;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import edu.neu.madcourse.mad_goer.messages.LatLng;

public class Event {

    //TODO: add constructor appropriately, eventID = eventName+enddata
    //also notice if private/public, some parameters might not be needed
    private Boolean isValid;
    private String eventID;
    private String eventName;
    private Long startDate;
    private Long endDate;

    //if false then virtual
    private boolean inPerson = true;

    //if ture, public, if false private
    private boolean isPublic = true;
    //only for private event
    private String eventPassword;

    //vitual: link
    private String link;
    //inperson:
    private LatLng location;

    private String actualLocationInString;

    private String desc;
    private EventType category;
    private int Capacity;
    private int duration;
    private int iconID;

    private ArrayList<String> attendingList = new ArrayList<>();
    private int save;

    //to link host
    private User host;

    public Event() {
    }

    public Event(Boolean isValid, String eventID, String eventName, Long startDate, Long endDate, boolean inPerson, boolean isPublic, String eventPassword, String link, LatLng location, String actualLocationInString, String desc, EventType category, User host, ArrayList<String> attendingList, int save, int capacity, int iconID, String description) {
        this.isValid = isValid;
        this.eventID = eventID;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inPerson = inPerson;
        this.isPublic = isPublic;
        this.eventPassword = eventPassword;
        this.link = link;
        this.location = location;
        this.actualLocationInString = actualLocationInString;
        this.desc = desc;
        this.category = category;
        this.host = host;
        this.attendingList = attendingList;
        this.save = save;
        Capacity = capacity;
        this.iconID = iconID;
        setDesc(description);
    }



    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public void addUserToAttendingList(String userName){
        this.attendingList.add(userName);
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
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

    public String getActualLocation() {
        return actualLocationInString;
    }

    public void setActualLocation(String actualLocationInString) {
        this.actualLocationInString = actualLocationInString;
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

    public ArrayList<String> getAttendingList() {
        return attendingList;
    }

    public void setAttendingList(ArrayList<String> attendingList) {
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

    public int getIconID(){return iconID;}

    public void setIconID(int iconID){ this.iconID = iconID;}

// TODO: add theme icon

    public Event(String name, EventType eventType) {
        this.eventName = name;
        this.category = eventType;
    }

    public Boolean verifyPrivatePassword(String password){
        return this.eventPassword.equals(password);
    }

    public double calDistance(Location userLocation){
        Location eventLocation = new Location("");
        eventLocation.setLatitude(location.getLatitude());
        eventLocation.setLongitude(location.getLongitude());
        return userLocation.distanceTo(userLocation);
    }

    public Boolean isPast(){
        Long endDate = getEndDate();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long nowStamp = timestamp.getTime();

        if (nowStamp > endDate){
            return true;
        }
        return false;
    }

//    @Override
//    public String toString() {
//        return "Event{" +
//                "attendingList=" + attendingList +
//                '}';
//    }
}
