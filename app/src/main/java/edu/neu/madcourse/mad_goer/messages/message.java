package edu.neu.madcourse.mad_goer.messages;

public class message {

    private String sendName;
    private String receiveName;
    private String stickerID;
    private String timeStemp;

    public message() {
    }

    public message(String sendName, String receiveName, String stickerID, String timeStemp) {
        this.sendName = sendName;
        this.receiveName = receiveName;
        this.stickerID = stickerID;
        this.timeStemp = timeStemp;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getStickerID() {
        return stickerID;
    }

    public void setStickerID(String stickerID) {
        this.stickerID = stickerID;
    }

    public String getTimeStemp() {
        return timeStemp;
    }

    public void setTimeStemp(String timeStemp) {
        this.timeStemp = timeStemp;
    }
}
