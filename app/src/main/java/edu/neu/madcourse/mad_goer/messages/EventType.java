package edu.neu.madcourse.mad_goer.messages;

public enum EventType {EDUCATION("Education"),Sports,Fitness,Technology,Travel,Outdoor,Games,
    Art, Culture, Career, Business, Community, Dancing, Health, Hobbies, Movement,
    Language, Music, Family, Pets, Religion, Science;

    private String category;
    private EventType(String category){
        this.category = category;
    }
}
