package edu.neu.madcourse.mad_goer;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.User;


public class EventDetailActivity extends AppCompatActivity {
    private Event event;
    private User user;
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
    private HashMap<String,Event> eventmap;
    private ArrayList<User> userList;
    private MainActivity mainActivity;
    private String currentUser;
    private ImageButton saveBtn;



    //In event details activity, only need to access users in firebase to add joined event
    DatabaseReference databaseUserRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //also have eventmap from mainactivity
        mainActivity = new MainActivity();
        eventmap = mainActivity.getTotalEvents();
        userList = mainActivity.getUserList();
        currentUser = mainActivity.getCurrentUserName();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        eventID = extras.getString("eventID");
        event = eventmap.get(eventID);


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


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic: This part aims to add the event as "attending" under current user in firebase
                //TODO: test if user in firebase has updated its eventmap
                // Get a reference to our posts
                DatabaseReference ref = databaseUserRef.child(currentUser);
                Map<String,Object> userUpdates = new HashMap<>();
                userUpdates.put(eventID,"attending");
                ref.push().setValue(userUpdates);

                //check if event attendingList is full
                //if not full: Toast "Congratulations! Successfully Join!
                //if full: Toast "Sorry, this event is full!"

                if(!event.getAttendingList().contains(user)){
                    if(event.getCapacity() > event.getAttendingList().size()){
                        event.getAttendingList().add(user);
                        Toast.makeText(EventDetailActivity.this,
                                "Congratulations! You will GO to this event!", Toast.LENGTH_SHORT).show();
                        joinBtn.setText("Joined");
                    }else{
                        Toast.makeText(EventDetailActivity.this,
                                "Sorry, this event is full. Try earlier next time!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    event.getAttendingList().remove(user);
                    Toast.makeText(EventDetailActivity.this,
                            "Cancelled", Toast.LENGTH_SHORT).show();
                    joinBtn.setText("Go");
                }

            }
        });

        joinBtn.setText(checkJoin(event, user));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "If you leave right now, no changes will be saved, do you confirm to continue?", Snackbar.LENGTH_LONG);
                snackbar.setAction("Confirm", view -> {
                    snackbar.dismiss();
                    finish();
                });
                snackbar.show();
            }
        });

        hostTV.setText("Host: " + event.getHost().toString());
        eventNameTV.setText(event.getEventName());
        timeTV.setText(event.getStartDate().toString());
        categoryTV.setText(event.getCategory().toString());
        isPublicTV.setText(checkPublic(event));
        isVirtualTV.setText(checkVirtual(event));
        addressTV.setText(location(event));
        attendingListTV.setText(event.getAttendingList().toString());
        descriptionTV.setText(event.getDesc());

        //TODO:
        //star后加入该user的 saved list
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!user.getSavedEventList().containsKey(eventID)){
                    user.getSavedEventList().put(eventID, "saved");
                }else{
                    user.getSavedEventList().remove(eventID);
                }
            }
        });
    }

    //check joined
    public String checkJoin(Event event, User user){
        if(event.getAttendingList().contains(user)){
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
            address = event.getLocation().toString();
        }else{
            address = event.getLink();
        }
        return address;
    }

    public void returnMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
