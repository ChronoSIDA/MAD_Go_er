package edu.neu.madcourse.mad_goer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import edu.neu.madcourse.mad_goer.helper.InputFilterMinMax;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.EventType;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.messages.LatLng;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Event event;
    private Button cancel;
    private Button create;
    private TextView eventNameTV;
    private TextView date;
    private TextView time;
    private Calendar calendar;
    private long dateInTimestamp;
    private long timeInTimestamp;
    private ImageView categoryIV;
    private TextView categoryTV;
    private EditText isLocationEditField;
    private EditText passwordTV;
    private Switch isPublicTV;
    private Switch isVirtualTV;
    private EditText addressTV;
    private EditText urlTV;
    private EditText duration;
    private EditText capacity;
    private EditText descriptionTV;
    private String googleMapApiKey = "AIzaSyDH7mSYIFMEf64MuDURoVh6Fxh6dTyhipo";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private String currentUserName;
    private String eventName;
    private String eventType;
    private String eventID;
    private int iconID;
    private User currentUser;

    private LatLng locationSet;
    private String actualLocation;
    private RadioButton isPublic, isPrivate, inPerson, virtual;
    private RadioGroup rg1, rg2;


    DatabaseReference databaseUserRef = FirebaseDatabase.getInstance().getReference("User");
    DatabaseReference databaseEventRef = FirebaseDatabase.getInstance().getReference("Event");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //get the user, add eventID to user's event list
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        currentUserName = extras.getString("nameTxt");
        eventName = extras.getString("eventName");
        eventType = extras.getString("eventType");

        EventType randomTemp = EventType.valueOf(eventType.toUpperCase());
        System.out.println(randomTemp);

        event = new Event(eventName, randomTemp);
        calendar = Calendar.getInstance();



        DatabaseReference curUserRef = databaseUserRef.child(currentUserName);
        //read the user once from firebase, and save it to our user field.
        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("failed");
            }
        });



        // Initialize place API
        if (!Places.isInitialized()) {
            Places.initialize(this, googleMapApiKey);
        }


        isLocationEditField = (EditText) findViewById(R.id.id_islocation_create);
        isLocationEditField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY,
                        Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG))
                        .setCountry("US")
                        .build(v.getContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });


        cancel = (Button) findViewById(R.id.btn_back_create);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "If you cancel, no changes will be saved, are you sure to cancel?", Snackbar.LENGTH_LONG);
                snackbar.setAction("Confirm", view -> {
                    snackbar.dismiss();
                    finish();
                });
                snackbar.show();
            }
        });


        create = (Button) findViewById(R.id.btn_create_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValid()) {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    // ID = create time + name
                    eventID = timestamp.getTime() + eventName;
                    event.setEventID(eventID);
                    event.setEventName(eventName);
                    event.setHost(currentUser);
                    event.setIconID(iconID);

                    Long startDateTimestamp = calendar.getTimeInMillis();
                    Long durationTimeStamp = TimeUnit.HOURS.toMillis(Long.parseLong(duration.getText().toString()));
                    Long endDateTimestamp = startDateTimestamp + durationTimeStamp;



                    event.setStartDate(startDateTimestamp);
                    // startDate + duration1
                    event.setEndDate(endDateTimestamp);
                    //add event to user, add user to event
                    currentUser.addEvent(event.getEventID(), "host");
                    currentUser.addEvent(event.getEventID(), "going");
                    event.addUserToAttendingList(currentUserName);
                    event.setActualLocation(actualLocation);
                    //update user in fb, push event to db
                    databaseUserRef.child(currentUser.getUserID()).setValue(currentUser);

                    databaseEventRef.child(event.getEventID()).setValue(event);
                    Toast.makeText(CreateEventActivity.this, "Event created successfully", Toast.LENGTH_SHORT).show();
                    //or maybe go to detail page?
                    finish();
                } else {
                    Toast.makeText(CreateEventActivity.this, "Make sure all fields are completed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        eventNameTV = (TextView) findViewById(R.id.id_event_name_name_create);
        eventNameTV.setText(eventName);
        date= (TextView) findViewById(R.id.id_date_create);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        time= (TextView) findViewById(R.id.id_time_create);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        passwordTV=(EditText)findViewById(R.id.id_password_create);
        categoryIV = (ImageView) findViewById(R.id.img_category_create);
        categoryIV.setImageDrawable(getImageByType(eventType));
        addressTV = (EditText) findViewById(R.id.id_islocation_create);
        urlTV = (EditText) findViewById(R.id.id_isurl_create);
        descriptionTV = (EditText) findViewById(R.id.id_desc_create);
        duration =(EditText)findViewById(R.id.id_duration_create);
        duration.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "20")});
        capacity =(EditText)findViewById(R.id.id_capacity_create);
        capacity.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200")});
        rg2 = (RadioGroup) findViewById(R.id.radioGroup2);
        isPublic = (RadioButton) findViewById(R.id.id_radio_public_filter);
        isPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setPublic(true);
                passwordTV.setVisibility(View.INVISIBLE);
            }
        });
        isPrivate = (RadioButton) findViewById(R.id.id_radio_private_filter);
        isPrivate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                event.setPublic(false);
                passwordTV.setVisibility(View.VISIBLE);
            }
        });
        rg1= (RadioGroup)findViewById(R.id.radioGroup);
        inPerson = (RadioButton) findViewById(R.id.id_radio_inperson);
        virtual = (RadioButton) findViewById(R.id.id_radio_virtual);
        inPerson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                event.setInPerson(true);
                addressTV.setVisibility(View.VISIBLE);
                urlTV.setVisibility(View.INVISIBLE);
            }
        });
        virtual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                event.setInPerson(false);
                urlTV.setVisibility(View.VISIBLE);
                addressTV.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                isLocationEditField.setText(place.getAddress().toString());
                actualLocation = place.getAddress().toString();
                locationSet = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                System.out.println(status);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    public void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, this,
                9,
                0,
                true
        );
        timePickerDialog.show();
    }
    public Boolean checkValid(){
//        String str = "Check the ";
        //check whether this event is complete, if so return true, return false otherwise.
        if(isPrivate.isChecked()){
            if(passwordTV.getText().toString().equals("")){
//                str += "passwaord ";
                return false;
            }else{
                event.setEventPassword(passwordTV.getText().toString());
            }
        }
        if(virtual.isChecked()){
            if(urlTV.getText().toString().equals("")){
//                str += "meeting url ";
                return false;
            }else{
                event.setLink(urlTV.getText().toString());
            }
        }
        if(inPerson.isChecked()){
            if(addressTV.getText().toString().equals("")){
//                str+= "location ";
                return false;
            }else{
                event.setLocation(locationSet);
            }
        }
        if(date.getText().toString().equals("")){
//            str+= "date ";
            return false;
        }
        if(time.getText().toString().equals("")){
//            str+= "time ";
            return false;
        }
        if(duration.getText().toString().equals("")){
//            str+= "duration ";
            return false;
        }else{
            event.setDuration(Integer.parseInt(duration.getText().toString()));
        }
        if(capacity.getText().toString().equals("")){
//            str+= "duration ";
            return false;
        }else{
            event.setCapacity(Integer.parseInt(capacity.getText().toString()));
        }
        return true;
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");
        String stringDate= DateFor.format(getDate(year, month, dayOfMonth));
        date.setText(" "+stringDate);

        // get date in timestamp
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        time(hourOfDay, minute);
//        DateTimeFormatter FOMATTER = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            FOMATTER = DateTimeFormatter.ofPattern("hh:mm a");
//        }
//        String timeString = FOMATTER.format(time);
        StringBuilder sb = new StringBuilder()
                .append(pad(hourOfDay)).append(":")
                .append(pad(minute));
        time.setText(" "+sb.toString());

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "If you leave right now, no changes will be saved, do you confirm to continue?", Snackbar.LENGTH_LONG);
        snackbar.setAction("Confirm", view -> {
            snackbar.dismiss();
            finish();
        });
        snackbar.show();
    }

//    private TextWatcher filterTextWatcher = new TextWatcher() {
//        public void afterTextChanged(Editable s) {
//            if (!s.toString().equals("")) {
//                mAutoCompleteAdapter.getFilter().filter(s.toString());
//                if (recyclerView.getVisibility() == View.GONE) {recyclerView.setVisibility(View.VISIBLE);}
//            } else {
//                if (recyclerView.getVisibility() == View.VISIBLE) {recyclerView.setVisibility(View.GONE);}
//            }
//        }
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//        public void onTextChanged(CharSequence s, int start, int before, int count) { }
//    };

    public void  click(Place place) {
        Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
