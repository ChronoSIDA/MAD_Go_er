package edu.neu.madcourse.mad_goer.messages;

import android.os.Message;

import java.util.ArrayList;

public class User {

    private String userName;
    public int totalMsgSent;
    private ArrayList<Message> DBmsgList;

    //must keep the empty constructor, otherwise generate "Class does not define a no-argument constructor." error
    public User() {
    }

    public User(String userName) {
        this.userName = userName;
        this.totalMsgSent = 0;
        this.DBmsgList = null;
    }

    public int getTotalMsgSent() {
        return totalMsgSent;
    }

    public void setTotalMsgSent(int totalMsgSent) {
        this.totalMsgSent = totalMsgSent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
