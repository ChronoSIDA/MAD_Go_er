package edu.neu.madcourse.mad_goer.messages;

import android.graphics.drawable.Drawable;

import edu.neu.madcourse.mad_goer.R;

public enum EventType {EDUCATION("Education"), SPORTS("Sports"),
    FITNESS("Fitness"), TECHNOLOGY("Technology"),
    TRAVEL("Travel"), OUTDOOR("Outdoor"), GAMES("Games"),
    ART("Art"), CULTURE("Culture"), CAREER("Career"),
    BUSINESS("Business"), COMMUNITY("Community"), DANCING("Dancing"),
    HEALTH("Health"), HOBBIES("Hobbies"), MOVEMENT("Movement"),
    LANGUAGE("Language"), MUSIC("Music"), FAMILY("Family"),
    PETS("Pets"), RELIGION("Religion"), SCIENCE("Science");

    private String category;
    //private Drawable pic;
    private EventType(String category){
        this.category = category;
    }
    public String toStringRun(){
        return this.category;
    }
}

//public enum EventType {Education,Sports,Fitness,Technology,Travel,Outdoor,Games,
//    Art, Culture, Career, Business, Community, Dancing, Health, Hobbies, Movement,
//    Language, Music, Family, Pets, Religion, Science}
