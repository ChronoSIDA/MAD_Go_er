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
import android.widget.TextView;

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
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;

import edu.neu.madcourse.mad_goer.databinding.ActivityMainBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.EventType;
import edu.neu.madcourse.mad_goer.messages.message;
import edu.neu.madcourse.mad_goer.ui.album.AlbumFragment;

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
    private ArrayList<Event> eventList;

    DatabaseReference databaseUserRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference databaseEventRef = FirebaseDatabase.getInstance().getReference("event");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationManagerCompat = NotificationManagerCompat.from(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //use extras to get the passed in userName/userID from login to main activity

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        TextView senderOnSendPage = (TextView) findViewById(R.id.title_sender2);
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
                R.id.navigation_send, R.id.navigation_receive, R.id.navigation_history, R.id.navigation_logout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        databaseUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                    case R.id.navigation_send:
                        navController.navigate(R.id.navigation_home);
                        return true;
                    case R.id.navigation_receive:
                        navController.navigate(R.id.navigation_go);
                        return true;
                    case R.id.navigation_history:
                        navController.navigate(R.id.navigation_album);
                        return true;
                    case R.id.navigation_logout:
                        navController.navigate(R.id.navigation_setting);
                        return true;
                }
                return true;
            }
        });



    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(CURRENT_USER, currentUserID);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void addNewEvent(String name, EventType type) {
        eventList.add(new Event(name, type));
    }

    public void addEvent() {
        ImageButton plus = findViewById(R.id.btn_create_event);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDialog();
            }
        });
    }

    public ArrayList<Event> getTotalEvents(){
        return eventList;
    }


    public void createNewDialog(){
        dialogBuilder= new AlertDialog.Builder(this);
        final View EventPopupView = getLayoutInflater().inflate(R.layout.event_popup, null);
        newEventName = (EditText) EventPopupView.findViewById(R.id.newEventName);
       // newEventType = (EditText) EventPopupView.findViewById(R.id.newEventCategory);
        newEventSave = (Button) EventPopupView.findViewById(R.id.newEventSave);
        newEventCancel = (Button) EventPopupView.findViewById(R.id.newEventCancel);

        dialogBuilder.setView(EventPopupView);
        dialog= dialogBuilder.create();
        dialog.show();

        EnumSet<EventType> categories = EnumSet.allOf(EventType.class);

        Spinner newEventSpinner = (Spinner) findViewById(R.id.spinner_category_popup);
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


}