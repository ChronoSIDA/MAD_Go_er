package edu.neu.madcourse.mad_goer.messages;

import android.os.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

    private String userID;

    private ArrayList<EventType> myInterests;

    private ArrayList<String> hostEventList;
    private ArrayList<String> attendEventList;
    private ArrayList<String> savedEventList;
    private ArrayList<String> pastEventList;



    //must keep the empty constructor, otherwise generate "Class does not define a no-argument constructor." error
    public User() {
    }

    public User(String userID){
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public ArrayList<String> getAttendEventList() {
        return attendEventList;
    }

    public void setAttendEventList(ArrayList<String> attendEventList) {
        this.attendEventList = attendEventList;
    }

    public ArrayList<String> getSavedEventList() {
        return savedEventList;
    }

    public void setSavedEventList(ArrayList<String> savedEventList) {
        this.savedEventList = savedEventList;
    }

    public ArrayList<String> getPastEventList() {
        return pastEventList;
    }

    public void setPastEventList(ArrayList<String> pastEventList) {
        this.pastEventList = pastEventList;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<EventType> getMyInterests() {
        return myInterests;
    }

    public void setMyInterests(ArrayList<EventType> myInterests) {
        this.myInterests = myInterests;
    }

    public ArrayList<String> getHostEventList() {
        return hostEventList;
    }

    public void setHostEventList(ArrayList<String> hostEventList) {
        this.hostEventList = hostEventList;
    }
}
