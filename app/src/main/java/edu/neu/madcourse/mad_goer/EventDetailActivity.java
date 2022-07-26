package edu.neu.madcourse.mad_goer;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
    private boolean joined;
    private String eventID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //TODO: find the event based on eventID,probably need eventlist from mainactivity;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        eventID = extras.getString("eventID");


        TextView scrollGoers = (TextView) findViewById(R.id.id_goers_detail);
        scrollGoers.setMovementMethod(new ScrollingMovementMethod());
        TextView scrollDesc = (TextView) findViewById(R.id.id_desc_detail);
        scrollDesc.setMovementMethod(new ScrollingMovementMethod());


        joinBtn = (Button) findViewById(R.id.btn_join_detail);
        cancelBtn = (Button) findViewById(R.id.btn_cancel_detail);
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
                //TODO:
                //check if event attendingList is full
                //if not full: Toast "Congratulations! Successfully Join!
                //if full: Toast "Sorry, this event is full!"

                //不知道这里加user写的对不对， getter后面能不能直接add
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

//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO:
//                //return to main activity
//
//            }
//        });

        hostTV.setText("Host: " + event.getHost().toString());
        eventNameTV.setText(event.getEventName());
        timeTV.setText(event.getStartDate().toString());
        categoryTV.setText(event.getCategory().toString());
        isPublicTV.setText(checkPublic(event));
        isVirtualTV.setText(checkVirtual(event));
        addressTV.setText(location(event));
        attendingListTV.setText(event.getAttendingList().toString());
        descriptionTV.setText(event.getDesc());

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
