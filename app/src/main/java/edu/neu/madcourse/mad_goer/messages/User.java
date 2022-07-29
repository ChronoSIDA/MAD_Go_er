package edu.neu.madcourse.mad_goer.messages;

import android.os.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class User {

    private String userID;

    //key cannot be duplicate, but values can
    //Key is eventID, value is "saved"/"Host"/"past"/"interested"/"attend"
    private HashMap<String,String> myEventList;

    private ArrayList<EventType> interestedTypeList;

    //must keep the empty constructor, otherwise generate "Class does not define a no-argument constructor." error
    public User() {
    }

    public User(String userID){
        this.userID = userID;
        Map<String,String> myEventList = new HashMap<>();
    }

    public String getUserID() {
        return userID;
    }


    public Map<String,String> getAttendEventList() {
        Map<String,String> result = this.myEventList.entrySet().stream().filter(map -> "attending".equals(map.getValue())).collect(Collectors.toMap(p->p.getKey(),p->p.getValue()));
        return result;
    }

    public Map<String,String> getSavedEventList() {
        Map<String,String> result = this.myEventList.entrySet().stream().filter(map -> "saved".equals(map.getValue())).collect(Collectors.toMap(p->p.getKey(),p->p.getValue()));
        return result;
    }

    public Map<String,String> getPastEventList() {
        Map<String,String> result = this.myEventList.entrySet().stream().filter(map -> "past".equals(map.getValue())).collect(Collectors.toMap(p->p.getKey(),p->p.getValue()));
        return result;
    }

    public Map<String,String> getHostEventList() {
        Map<String,String> result = this.myEventList.entrySet().stream().filter(map -> "host".equals(map.getValue())).collect(Collectors.toMap(p->p.getKey(),p->p.getValue()));
        return result;
    }

    public ArrayList<EventType> getInterestedTypeList() {
        return interestedTypeList;
    }

    public Map<String,String> getTotalPersonalEvents(){
        return this.myEventList;
    }

    public void setInterestedTypeList(ArrayList<EventType> interestedTypeList) {
        this.interestedTypeList = interestedTypeList;
    }


    public void setUserID(String userID) {
        this.userID = userID;
    }


}
