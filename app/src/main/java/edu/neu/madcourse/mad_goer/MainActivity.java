package edu.neu.madcourse.mad_goer;

import static edu.neu.madcourse.team36_a8.R.drawable.ic_noti_emoji;

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

import edu.neu.madcourse.team36_a8.databinding.ActivityMainBinding;
import edu.neu.madcourse.team36_a8.messages.User;
import edu.neu.madcourse.team36_a8.messages.message;
import edu.neu.madcourse.team36_a8.ui.history.HistoryFragment;
import edu.neu.madcourse.team36_a8.ui.receive.ReceiveFragment;

public class MainActivity extends AppCompatActivity{
    private NotificationManagerCompat notificationManagerCompat;

    private ActivityMainBinding binding;
    private ImageView selectedSticker;
    private ImageView restoreSticker;
    ReceiveFragment receiveFragment;
    HistoryFragment historyFragment;
    private static final String CURRENT_USER = "CURRENT_USER";
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);


    private User curUser;
    private String currentUserID;
    private String otherUserID;
    private String stickerIdSend;
    private String sentTime;


    String sendNameMsg;
    String receiveNameMsg;
    String stickerIdMsg;
    String timestampMsg;
    int sentCount;


    private ArrayList<String> userList;
    private ArrayList<String> messageData;
    private Spinner UserListSpinner;

    private ArrayList<message> msgList;

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
        currentUserID = extras.getString("DBcurrentUserID");
        dbMsgUnderUserRef = FirebaseDatabase.getInstance().getReference("messages").child("currentUserID");
        curUser = new User(currentUserID);
        TextView senderOnSendPage = (TextView) findViewById(R.id.title_sender2);
        senderOnSendPage.setText(currentUserID);
        String urlJson = "https://team36a8-default-rtdb.firebaseio.com/users/" + currentUserID+".json";

        StringRequest request = new StringRequest(Request.Method.GET, urlJson, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    sentCount = (int) obj.get("totalMsgSent");
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


        msgList = new ArrayList<message>();



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_send, R.id.navigation_receive, R.id.navigation_history, R.id.navigation_logout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



        //link the spinner to xml element
        UserListSpinner = (Spinner) findViewById(R.id.spinner_userslist);


        userList = new ArrayList<>();
        messageData = new ArrayList<>();

        databaseUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User tmp = snapshot.getValue(User.class);
                    String name = tmp.getUserName();
                    if(name != null){
                        userList.add(name);
                    }
                }
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

                        generateUserList();
                    }
                },
                100);


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_send:
                        navController.navigate(R.id.navigation_send);
                        return true;
                    case R.id.navigation_receive:
                        navController.navigate(R.id.navigation_receive);
                        removeBadge(1);
                        return true;
                    case R.id.navigation_history:
                        navController.navigate(R.id.navigation_history);
                        return true;
                    case R.id.navigation_logout:
                        Toast.makeText(MainActivity.this,"Logged out",Toast.LENGTH_SHORT).show();
                        Intent switchActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(switchActivityIntent);
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
                messageData.clear();
                message latestMsg = snapshot.getValue(message.class);
                generateMessage(latestMsg);
                newMessageInSendPage();
                if (latestMsg.getReceiveName().equals(currentUserID)) {
                    sendNotification(latestMsg);
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

        //this is for history module, have a listener for all msg under current user, once there is a new message
        //add it to msglist
        //msglist will be passed to an adapter and then to recycleview
        historyFragment = new HistoryFragment();
        databaseReference.child("messages").child(currentUserID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                message tmp = snapshot.getValue(message.class);
                msgList.add(tmp);
                historyFragment = new HistoryFragment();
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

    public void sendNotification(message lastMessage){
//        LayoutInflater layoutInflater =LayoutInflater.from(this);
//        View v = (View) this.findViewById(R.id.activity)

        String channel = NotiApp.CHANNEL_1_ID;
        String title = "Stick It To 'em";
        String message = "New Sticker From " + lastMessage.getSendName();


        Bitmap bigIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                getStickerById(lastMessage.getStickerID()));

        Notification notification = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(ic_noti_emoji)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bigIcon)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification);
    }

    public int getStickerById(String id){
        int thisSticker =  R.id.sticker_1;
        switch (id){
            case "5520a8s01":
                thisSticker = R.drawable.sticker_01;
                break;
            case "5520a8s02":
                thisSticker = R.drawable.sticker_02;
                break;
            case "5520a8s03":
                thisSticker = R.drawable.sticker_03;
                break;
            case "5520a8s04":
                thisSticker = R.drawable.sticker_04;
                break;
            case "5520a8s05":
                thisSticker = R.drawable.sticker_05;
                break;
            case "5520a8s06":
                thisSticker = R.drawable.sticker_06;
                break;
            case "5520a8s07":
                thisSticker = R.drawable.sticker_07;
                break;
            case "5520a8s08":
                thisSticker = R.drawable.sticker_08;
                break;
            case "5520a8s09":
                thisSticker = R.drawable.sticker_09;
                break;
        }
        return thisSticker;
    }
    // Part I: send stickers
    public void onSelectSticker(View view){
        Drawable highlight = getResources().getDrawable(R.drawable.highlight_selected);
        restoreAllHighlight();
        switch (view.getId()){
            case R.id.sticker_1:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_1);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s01";
                break;
            case R.id.sticker_2:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_2);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s02";
                break;
            case R.id.sticker_3:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_3);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s03";
                break;
            case R.id.sticker_4:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_4);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s04";
                break;
            case R.id.sticker_5:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_5);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s05";
                break;
            case R.id.sticker_6:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_6);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s06";
                break;
            case R.id.sticker_7:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_7);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s07";
                break;
            case R.id.sticker_8:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_8);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s08";
                break;
            case R.id.sticker_9:
                selectedSticker = (ImageView) view.findViewById(R.id.sticker_9);
                selectedSticker.setBackground(highlight);
                stickerIdSend = "5520a8s09";
                break;
        }
    }


    public void generateUserList(){
        // ** please edit this function
        // .. generate user list:
        // TODO: userlit有数据但是不显示
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UserListSpinner.setAdapter(dataAdapter);
    }

    public void generateMessage(message msg){
        sendNameMsg  = msg.getSendName();
        receiveNameMsg = msg.getReceiveName();
        stickerIdMsg = msg.getStickerID();
        timestampMsg = msg.getTimeStemp();
        messageData.add(currentUserID);
        messageData.add(sendNameMsg);
        messageData.add(receiveNameMsg);
        messageData.add(stickerIdMsg);
        messageData.add(timestampMsg);

        receiveFragment = new ReceiveFragment();
    }

    public ArrayList getMessageData(){



        return messageData;
    }

    public ArrayList<message> getHistoryData(){
        return msgList;
    }

    public int getTotalSent(){
        return sentCount;
    }


    public void sendSticker(View view){
        otherUserID = UserListSpinner.getSelectedItem().toString();

        if(stickerIdSend == null){
            Toast.makeText(MainActivity.this,"Please select a sticker to send",Toast.LENGTH_SHORT).show();
            return;
        } else if (otherUserID == null){
            Toast.makeText(MainActivity.this,"Please select a receiver to send",Toast.LENGTH_SHORT).show();
            return;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            sentTime = df.format(timestamp.getTime());

            message msg = new message(currentUserID, otherUserID, stickerIdSend, sentTime);
            //push() ensures a unique and chronological key

            databaseReference.child("messages").child(currentUserID).push().setValue(msg);
            databaseReference.child("messages").child(otherUserID).push().setValue(msg);
            sentCount += 1;

//            Map<String, Object> map = new HashMap<>();
//            map.put("totalMsgSet", sentCount);
            databaseReference.child("users").child(currentUserID).child("totalMsgSent").setValue(sentCount);

            //TODO: need to have a textview in stick em to diplay msgsent in total
            //curUser.setTotalMsgSent(curUser.getTotalMsgSent()+1);

            Toast.makeText(MainActivity.this,"Sticker sent!",Toast.LENGTH_SHORT).show();
        }
    }


    public void restoreAllHighlight(){
        restoreSticker = (ImageView) findViewById(R.id.sticker_1);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_2);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_3);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_4);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_5);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_6);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_7);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_8);
        restoreSticker.setBackground(null);
        restoreSticker = (ImageView) findViewById(R.id.sticker_9);
        restoreSticker.setBackground(null);
    }

    public void newMessageInSendPage(){
        // put little badge in History Nav

        BottomNavigationView navView = findViewById(R.id.nav_view);

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.noti_badge, itemView, true);
    }

    public void removeBadge(int index) {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        if(navView != null){
            BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
            View v = bottomNavigationMenuView.getChildAt(index);
            BottomNavigationItemView itemView = (BottomNavigationItemView) v;
            if(itemView.getChildCount() == 3){
                itemView.removeViewAt(itemView.getChildCount()-1);
            }
        }
    }


    public void getData() {
        databaseReference.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                userList.clear();

                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    User tmp = snapshot.getValue(User.class);
                    String name = tmp.getUserName();
                    if (name != null) {
                        userList.add(name);
                    }
                }
            }
        });

        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        UserListSpinner = (Spinner) findViewById(R.id.spinner_userslist);
                        TextView sender = (TextView) findViewById(R.id.title_sender2);
                        sender.setText(currentUserID);
                        generateUserList();
                    }
                },
                100);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(CURRENT_USER, currentUserID);
        super.onSaveInstanceState(savedInstanceState);
    }
}