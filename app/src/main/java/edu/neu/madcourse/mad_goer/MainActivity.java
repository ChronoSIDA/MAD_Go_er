package edu.neu.madcourse.mad_goer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.neu.madcourse.mad_goer.databinding.ActivityMainBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.EventType;
import edu.neu.madcourse.mad_goer.messages.User;

public class MainActivity extends AppCompatActivity{
    private NotificationManagerCompat notificationManagerCompat;

    private ActivityMainBinding binding;
    private static final String CURRENT_USER = "CURRENT_USER";
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newEventName;
    private EditText newEventType;
    private Button newEventSave, newEventCancel;

    //for gofragments
    //key is "eventID", value is Event
    //would need all eventID under currentUser's personal eventmap
    //we already have userlist from firebase, userlist contains User object, find currentUser from the UserList,
    // and inside Userobject there is a personalEventMap,
    // which is what we need to pass in the recyclerView
    //easier way is to call getHostEvent()...methods in user to return filtered hashMap
    private HashMap<String,Event> eventMap;
    private ArrayList<User> userList;
    //this is user's personal eventmap, key is "eventID", value is "eventtype(host/attending/saved/past)"
    private Map<String,String> personalEventMap;
    private String currentUserName;
    private User currentUser;
    private ArrayList<ArrayList<Event>> listofEventLists;

    DatabaseReference databaseUserRef = FirebaseDatabase.getInstance().getReference("User");
    DatabaseReference databaseEventRef = FirebaseDatabase.getInstance().getReference("Event");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LoginActivity loginactivity = new LoginActivity();
        currentUserName = loginactivity.getCurrentUserName();
        currentUser = loginactivity.getCurrentUser();


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManagerCompat = NotificationManagerCompat.from(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        ImageButton plus = findViewById(R.id.btn_create_event);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDialog();
            }
        });
       // TextView senderOnSendPage = (TextView) findViewById(R.id.title_sender2);
        String urlJson = "https://goerapp-4e3c7-default-rtdb.firebaseio.com/User/" + "" +".json";

        StringRequest request = new StringRequest(Request.Method.GET, urlJson, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(this);
        rQueue.add(request);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_go, R.id.navigation_home, R.id.navigation_comment, R.id.navigation_setting)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //get userList from remote
        databaseUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                userList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //logic: if anything changed, clear the eventMap, and read everything from firebase again
        //TODO: now the field eventMap is updated, we need to pass it to other fragments
        //logic: create a method in mainactivity to return the updated eventMap
        //in other fragments, call getTotalEvent() method
        //e.g.    MainActivity activity = (MainActivity) getActivity();
        //        msgData = activity.getHistoryData();
        databaseEventRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                eventMap.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    String eventkey = event.getEventID();
                    eventMap.put(eventkey,event);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                eventMap.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    String eventkey = event.getEventID();
                    eventMap.put(eventkey,event);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                eventMap.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    String eventkey = event.getEventID();
                    eventMap.put(eventkey,event);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                eventMap.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    String eventkey = event.getEventID();
                    eventMap.put(eventkey,event);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                    }
                },
                100);


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        navController.navigate(R.id.navigation_home);
                        return true;
                    case R.id.navigation_go:
                        navController.navigate(R.id.navigation_go);
                        return true;
                    case R.id.navigation_comment:
                        navController.navigate(R.id.navigation_comment);
                        return true;
                    case R.id.navigation_setting:
                        navController.navigate(R.id.navigation_setting);
                        return true;
                }
                return true;
            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(CURRENT_USER, currentUserName);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void addNewEvent(String name, EventType type) {
       // eventMap.put(new Event(name, type));
    }

    public void addEvent() {

    }

    public HashMap<String, Event> getTotalEvents(){
        return eventMap;
    }
    public ArrayList<User> getUserList(){return userList;}
    public String getCurrentUserName(){return this.currentUserName;}

    public ArrayList<ArrayList<Event>> getListofEventLists() {

        if (currentUser != null) {
            //Key is eventID, value is "saved"/"Host"/"past"
            personalEventMap = currentUser.getTotalPersonalEvents();
        }
        //find a myEventMap<String,Event> of eventID in personalEventMap<String,string> from eventMap<String, Event>
        //key is eventID
        Set<String> eventIDkeySet = personalEventMap.keySet();

        listofEventLists = new ArrayList<ArrayList<Event>>();

        for (String key : eventIDkeySet) {

            //for all eventstatus, all to the first list
            listofEventLists.get(0).add(eventMap.get(key));

            //if value is "host", add eventobj to first list in listoflists
            if (personalEventMap.get(key).equals("host")) {
                listofEventLists.get(1).add(eventMap.get(key));
            }
            if (personalEventMap.get(key).equals("going")) {
                listofEventLists.get(2).add(eventMap.get(key));
            }
            if (personalEventMap.get(key).equals("saved")) {
                listofEventLists.get(3).add(eventMap.get(key));
            }
            if (personalEventMap.get(key).equals("past")) {
                listofEventLists.get(4).add(eventMap.get(key));
            }

        }
        return listofEventLists;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void createNewDialog(){
        dialogBuilder= new AlertDialog.Builder(this);
        final View EventPopupView = getLayoutInflater().inflate(R.layout.event_popup, null);
        newEventName = (EditText) EventPopupView.findViewById(R.id.newEventName);
       // newEventType = (EditText) EventPopupView.findViewById(R.id.newEventCategory);
        newEventSave = (Button) EventPopupView.findViewById(R.id.btn_cancel_filter);
        newEventCancel = (Button) EventPopupView.findViewById(R.id.btn_apply_filter);

        dialogBuilder.setView(EventPopupView);
        dialog= dialogBuilder.create();
        dialog.show();

        EnumSet<EventType> categories = EnumSet.allOf(EventType.class);

        Spinner newEventSpinner = (Spinner) findViewById(R.id.spinner_category_filter);
        ArrayList<EventType> category_list = new ArrayList<>(categories.size());
        for (EventType t: categories) {
            category_list.add(t);
        }

        // add enum values to the arrayList
        ArrayAdapter<EventType> dataAdapter = new ArrayAdapter<EventType>(this, android.R.layout.simple_spinner_item, category_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newEventSpinner.setAdapter(dataAdapter);


        newEventSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(newEventName!=null && newEventType!=null) {

                    String type = newEventSpinner.getSelectedItem().toString();

                    addNewEvent(newEventName.getText().toString(), EventType.valueOf(type));
                    Snackbar.make(view, "Event created successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    // add intent
                    //TO DO for Yang: required to pass userList

                }else{
                    Snackbar.make(view, "Event creation failed, try again later", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        newEventCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Snackbar.make(view, "Link canceled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public ArrayAdapter<Event> getArrayAdapter() {
        //event_list contains all Event objects under this user
        ArrayList<Event> event_list = getListofEventLists().get(0);

        //TO DO: pass event information of this user
        for (Event e: event_list) {
            event_list.add(e);
        }
        ArrayAdapter<Event> dataAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_spinner_item, event_list);

        return dataAdapter;
    }
}