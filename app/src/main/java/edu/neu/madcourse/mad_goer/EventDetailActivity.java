package edu.neu.madcourse.mad_goer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.User;


public class EventDetailActivity extends AppCompatActivity {
    private Event event;
    private User currentUser;
    private Button joinBtn;
    private Button cancelBtn;
    private TextView hostTV;
    private TextView eventNameTV;
    private TextView timeTV;
    private TextView categoryTV;
    private TextView isPublicTV;
    private TextView isVirtualTV;
    private TextView addressTV;
    private TextView attendingListTV;
    private TextView descriptionTV;
    private String eventID;
    private HashMap<String,Event> eventMap = new HashMap<>();
    private ArrayList<User> userList;
    private MainActivity mainActivity;
    private String currentUserName;
    private ImageButton saveBtn;
    private ImageView picture;
    private int iconID;


    //In event details activity, only need to access users in firebase to add joined event
    DatabaseReference databaseUserRef = FirebaseDatabase.getInstance().getReference("User");
    DatabaseReference databaseEventRef = FirebaseDatabase.getInstance().getReference("Event");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        currentUserName = extras.getString("nameTxt");
        eventID = extras.getString("eventID");

        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        event = eventMap.get(eventID);
                        String temp = checkJoin(event, currentUserName);
                        retrieveDataDisplay(temp);
                    }
                },
                500);


        DatabaseReference curUserRef = databaseUserRef.child(currentUserName);
        //read the user once from firebase, and save it to our user field.
        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                System.out.println(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("failed");
            }
        });

        //geteventmap
        databaseEventRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Event newevent = snapshot.getValue(Event.class);
                //if newevent !past
                eventMap.put(newevent.getEventID(),newevent);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Event removedEvent = snapshot.getValue(Event.class);
                eventMap.remove(removedEvent.getEventID(),removedEvent);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        event = eventMap.get(eventID);


        TextView scrollGoers = (TextView) findViewById(R.id.id_goers_detail);
        scrollGoers.setMovementMethod(new ScrollingMovementMethod());
        TextView scrollDesc = (TextView) findViewById(R.id.id_desc_detail);
        scrollDesc.setMovementMethod(new ScrollingMovementMethod());

        joinBtn = (Button) findViewById(R.id.btn_join_detail);
        cancelBtn = (Button) findViewById(R.id.btn_cancel_detail);
        saveBtn = (ImageButton) findViewById(R.id.btn_save_detail);

        hostTV = (TextView) findViewById(R.id.txt_host_detail);
        eventNameTV = (TextView) findViewById(R.id.id_event_name_detail);
        timeTV = (TextView) findViewById(R.id.id_timestamp_detail);
        categoryTV = (TextView) findViewById(R.id.txt_categories_detail);
        isPublicTV = (TextView) findViewById(R.id.txt_isPublic_detail);
        isVirtualTV = (TextView) findViewById(R.id.txt_location_detail);
        addressTV = (TextView) findViewById(R.id.id_location_detail);
        attendingListTV = (TextView) findViewById(R.id.id_goers_detail);
        descriptionTV = (TextView) findViewById(R.id.id_desc_detail);
        picture = (ImageView) findViewById(R.id.img_categories);




//        joinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                //Logic: This part aims to add the event as "attending" under current currentUser in firebase
////                //TODO: test if currentUser in firebase has updated its eventmap
////                // Get a reference to our posts
////                DatabaseReference ref = databaseUserRef.child(currentUserName);
////                Map<String,Object> userUpdates = new HashMap<>();
////                userUpdates.put(eventID,"attending");
////                ref.push().setValue(userUpdates);


    }

    public void retrieveDataDisplay(String joined){
        joinBtn.setText(joined);
        hostTV.setText("Host: " + event.getHost().getUserID());
        eventNameTV.setText(event.getEventName());
//        timeTV.setText(event.getStartDate().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm");
        timeTV.setText(simpleDateFormat.format(event.getStartDate()));

        categoryTV.setText(event.getCategory().toString());
        isPublicTV.setText(checkPublic(event));
        isVirtualTV.setText(checkVirtual(event));
        attendingListTV.setText(printAttendingList(event));
        addressTV.setText(location(event));
        picture.setImageDrawable(getImageByType(event.getCategory().toString()));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
//                        "If you leave right now, no changes will be saved, do you confirm to continue?", Snackbar.LENGTH_LONG);
//                snackbar.setAction("Confirm", view -> {
//                    snackbar.dismiss();
                    finish();
//                });
//                snackbar.show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!currentUser.getSavedEventList().containsKey(eventID)){
                    currentUser.addEvent(eventID, "saved");
                    databaseUserRef.child(currentUserName).setValue(currentUser);
                    saveBtn.setImageDrawable(getDrawable(R.drawable.ic_save_star_highlight));
                    Toast.makeText(EventDetailActivity.this,
                            "Saved!", Toast.LENGTH_SHORT).show();
                }else{
                    currentUser.removeEvent(eventID,"saved");
                    databaseUserRef.child(currentUserName).setValue(currentUser);
                    saveBtn.setImageDrawable(getDrawable(R.drawable.ic_save_star));
                    Toast.makeText(EventDetailActivity.this,
                            "You don't like it any more!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Logic: This part aims to add the event as "attending" under current currentUser in firebase
//                //TODO: test if currentUser in firebase has updated its eventmap
//                // Get a reference to our posts
//                DatabaseReference ref = databaseUserRef.child(currentUserName);
//                Map<String,Object> userUpdates = new HashMap<>();
//                userUpdates.put(eventID,"attending");
//                ref.push().setValue(userUpdates);


                //check if event attendingList is full
                //if not full: Toast "Congratulations! Successfully Join!
                //if full: Toast "Sorry, this event is full!"

                //
                if(!event.getAttendingList().contains(currentUserName)){
                    if(event.getCapacity() > event.getAttendingList().size()){
                        //add user to event's attending list
                        event.getAttendingList().add(currentUserName);
                        //todo: fix this: eventID is customized, here is adding new data rather than update data
                        databaseEventRef.child(eventID).setValue(event);
                        //add this event under the user, update current user in database

                        currentUser.addEvent(eventID,"going");

                        databaseUserRef.child(currentUserName).setValue(currentUser);

                        Toast.makeText(EventDetailActivity.this,
                                "Congratulations! You will GO to this event!", Toast.LENGTH_SHORT).show();
                        joinBtn.setText("Joined");
                        attendingListTV.setText(printAttendingList(event));
                    }else{
                        Toast.makeText(EventDetailActivity.this,
                                "Sorry, this event is full. Try earlier next time!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //remove user from event attending list
                    event.getAttendingList().remove(currentUserName);
                    databaseEventRef.child(eventID).setValue(event);

                    //remove event from user attending list
                    currentUser.removeEvent(eventID,"going");
                    databaseUserRef.child(currentUserName).setValue(currentUser);

                    Toast.makeText(EventDetailActivity.this,
                            "Cancelled", Toast.LENGTH_SHORT).show();
                    joinBtn.setText("Go");
                    attendingListTV.setText(printAttendingList(event));
                }
            }
        });
    }


    //check joined
    public String checkJoin(Event event, String userName){
        if(event.getAttendingList().contains(userName)){
            return "Joined";
        }else{
            return "GO";
        }
    }

    //public tag text
    public String checkPublic(Event event){
        String check;
        if(this.event.isPublic()){
            check = "Public";
        }else{
            check = "Private";
        }
        return check;
    }

    public String checkVirtual(Event event){
        String method;
        if(this.event.isInPerson()){
            method = "Address: ";
        }else{
            method = "Link: ";
        }
        return method;
    }

    //location bar content
    public String location(Event event){
        String address;
        if(this.event.isInPerson()){
            address = event.getActualLocation();
        }else{
            address = event.getLink();
        }
        return address;
    }

    public void returnBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String printAttendingList(Event event){

        String attendingList = "";

        for(String username: event.getAttendingList()){
            attendingList += (username + " ");
        }
        return attendingList;
    }

    //identify the event type by enum, and return the sticker associated with that type
    public Drawable getImageByType(String type){
        Drawable typeImage;
        if(type == null){
            return null;
        }

        switch(type.toUpperCase()) {
            case "SPORTS":
                typeImage = getResources().getDrawable(R.drawable.sticker_sports);
                iconID = R.drawable.sticker_sports;
                break;
            case "EDUCATION":
                typeImage = getResources().getDrawable(R.drawable.sticker_education);
                iconID = R.drawable.sticker_education;
                break;
            case "FITNESS":
                typeImage = getResources().getDrawable(R.drawable.sticker_fitness);
                iconID = R.drawable.sticker_fitness;
                break;
            case "TECHNOLOGY":
                typeImage = getResources().getDrawable(R.drawable.sticker_technology);
                iconID = R.drawable.sticker_technology;
                break;
            case "TRAVEL":
                typeImage = getResources().getDrawable(R.drawable.sticker_travel);
                iconID = R.drawable.sticker_travel;
                break;
            case "OUTDOOR":
                typeImage = getResources().getDrawable(R.drawable.sticker_outdoor);
                iconID = R.drawable.sticker_outdoor;
                break;
            case "GAMES":
                typeImage = getResources().getDrawable(R.drawable.sticker_games);
                iconID = R.drawable.sticker_games;
                break;
            case "ART":
                typeImage = getResources().getDrawable(R.drawable.sticker_art);
                iconID = R.drawable.sticker_art;
                break;
            case "CULTURE":
                typeImage = getResources().getDrawable(R.drawable.sticker_culture);
                iconID = R.drawable.sticker_culture;
                break;
            case "CAREER":
                typeImage = getResources().getDrawable(R.drawable.sticker_career);
                iconID = R.drawable.sticker_career;
                break;
            case "BUSINESS":
                typeImage = getResources().getDrawable(R.drawable.sticker_business);
                iconID = R.drawable.sticker_business;
                break;
            case "COMMUNITY":
                typeImage = getResources().getDrawable(R.drawable.sticker_community);
                iconID = R.drawable.sticker_community;
                break;
            case "DANCING":
                typeImage = getResources().getDrawable(R.drawable.sticker_dancing);
                iconID = R.drawable.sticker_dancing;
                break;
            case "HEALTH":
                typeImage = getResources().getDrawable(R.drawable.sticker_health);
                iconID = R.drawable.sticker_health;
                break;
            case "HOBBIES":
                typeImage = getResources().getDrawable(R.drawable.sticker_hobbies);
                iconID = R.drawable.sticker_hobbies;
                break;
            case "MOVEMENT":
                typeImage = getResources().getDrawable(R.drawable.sticker_movement);
                iconID = R.drawable.sticker_movement;
                break;
            case "LANGUAGE":
                typeImage = getResources().getDrawable(R.drawable.sticker_language);
                iconID = R.drawable.sticker_language;
                break;
            case "MUSIC":
                typeImage = getResources().getDrawable(R.drawable.sticker_music);
                iconID = R.drawable.sticker_music;
                break;
            case "FAMILY":
                typeImage = getResources().getDrawable(R.drawable.sticker_family);
                iconID = R.drawable.sticker_family;
                break;
            case "PETS":
                typeImage = getResources().getDrawable(R.drawable.sticker_pets);
                iconID = R.drawable.sticker_pets;
                break;
            case "RELIGION":
                typeImage = getResources().getDrawable(R.drawable.sticker_religion);
                iconID = R.drawable.sticker_religion;
                break;
            case "SCIENCE":
                typeImage = getResources().getDrawable(R.drawable.sticker_science);
                iconID = R.drawable.sticker_science;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return typeImage;
    }
}
