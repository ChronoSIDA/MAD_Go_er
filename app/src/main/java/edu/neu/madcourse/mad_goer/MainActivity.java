package edu.neu.madcourse.mad_goer;

import static edu.neu.madcourse.mad_goer.R.drawable.ic_noti_emoji;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.neu.madcourse.mad_goer.databinding.ActivityMainBinding;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.messages.message;
import edu.neu.madcourse.mad_goer.ui.album.AlbumFragment;
import edu.neu.madcourse.mad_goer.ui.go.GoFragment;

public class MainActivity extends AppCompatActivity{
    private NotificationManagerCompat notificationManagerCompat;

    private ActivityMainBinding binding;
    private static final String CURRENT_USER = "CURRENT_USER";
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://team36a8-default-rtdb.firebaseio.com/");
    DatabaseReference databaseUserInfo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://team36a8-default-rtdb.firebaseio.com/users/");
    DatabaseReference databaseUserRef = FirebaseDatabase.getInstance().getReference("users");
    DatabaseReference databaseMsgRef = FirebaseDatabase.getInstance().getReference("messages");
    DatabaseReference dbMsgUnderUserRef;

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
        String urlJson = "https://team36a8-default-rtdb.firebaseio.com/users/" + "" +".json";

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

        databaseMsgRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

        //this part is for message module
        //go the messages in database, then go to current user

        databaseReference.child("messages").child(currentUserID).limitToLast(1).addChildEventListener(new ChildEventListener(){

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
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

        //this is for history module, have a listener for all msg under current user, once there is a new message
        //add it to msglist
        //msglist will be passed to an adapter and then to recycleview
        albumFragment = new AlbumFragment();
        databaseReference.child("messages").child(currentUserID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                message tmp = snapshot.getValue(message.class);
                msgList.add(tmp);
                albumFragment = new AlbumFragment();
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
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(CURRENT_USER, currentUserID);
        super.onSaveInstanceState(savedInstanceState);
    }
}