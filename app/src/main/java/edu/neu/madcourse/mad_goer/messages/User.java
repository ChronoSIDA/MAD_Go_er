package edu.neu.madcourse.mad_goer.messages;

import android.os.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class User {

    private String userID;

    //key cannot be duplicate, but values can
    //Key is eventID, value is "saved"/"Host"/"past"/"interested"/"attend"
    private Map<String,String> myEventList = new HashMap<>();
    private ArrayList<EventType> interestedTypeList = new ArrayList<EventType>();


    //must keep the empty constructor, otherwise generate "Class does not define a no-argument constructor." error
    public User() {
    }

    public User(String userID){
        this.userID = userID;
        this.myEventList = new HashMap<>();
        this.interestedTypeList = new ArrayList<EventType>();
    }

    public String getUserID() {
        return userID;
    }



    //Key is eventID, value is "saved"/"Host"/"past"/"interested"/"attend"
    public Map<String,String> getSavedEventList() {
        Map<String,String> result = this.myEventList.entrySet().stream().filter(map -> "saved".equals(map.getValue())).collect(Collectors.toMap(p->p.getKey(),p->p.getValue()));
        return result;
    }



    public ArrayList<EventType> getInterestedTypeList() {
        return interestedTypeList;
    }

    public Map<String,String> getMyEventList(){
        return this.myEventList;
    }


    public void clearInterestList(){
        this.interestedTypeList.clear();
    }

    public void addEvent(String eventID, String eventStatus) {

        if (this.myEventList.get(eventID) == null) {
            this.myEventList.put(eventID, eventStatus);
        } else {
            //add going or hostgoing, saved
            //host going is only added when create, going
            if(this.myEventList.get(eventID).contains(eventStatus)) {
                //do nothing
            }else{
                // do no contain new eventStatus
                String value = this.myEventList.get(eventID);
                value = value+eventStatus;
                this.myEventList.put(eventID,value);
            }
        }
    }

    public void removeEvent(String eventID, String eventStatus){
        if(myEventList.get(eventID).equals(eventStatus)){
            this.myEventList.remove(eventID,eventStatus);
        }
        else{
            String value = this.myEventList.get(eventID);
            if(value.contains(eventStatus)){
                //strings are immutable, sent the value back to the string
                value = value.replace(eventStatus,"");
                this.myEventList.put(eventID,value);
            }
        }
    }

    public void addInterestType(EventType eventType){
        this.interestedTypeList.add(eventType);
    }
}
