package edu.neu.madcourse.mad_goer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.EventType;
import edu.neu.madcourse.mad_goer.messages.User;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Event event;
    private Button cancel;
    private Button create;
    private TextView eventNameTV;
    private TextView date;
    private TextView time;
    private ImageView categoryIV;
    private TextView categoryTV;
    private Switch isPublicTV;
    private Switch isVirtualTV;
    private EditText addressTV;
    private EditText urlTV;
    private EditText descriptionTV;
    private String googleMapApiKey = "AIzaSyDH7mSYIFMEf64MuDURoVh6Fxh6dTyhipo";

    //TODO: QUESTION: where is create event activity called? I need to pass userList from outside to this activity
    //added by Yang Yang, pass in UserList from mainactivity, and connect it to firebase, so when a new event
    //is created, add it to the user's eventlist(host) in firebase
    //Two things added: 1. userlist    2. firebase


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Initialize the SDK
        Places.initialize(getApplicationContext(), googleMapApiKey);

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.places_fragment_create);

        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);

        autocompleteFragment.setCountries("US");

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                // TODO: add the displayed name to the location box.
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
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
                //TODO
                //upload this event onto firebase
                if(date!= null&&)
            }
        });
        eventNameTV = (TextView) findViewById(R.id.txt_event_name_create);
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
        categoryIV = (ImageView) findViewById(R.id.img_category_create);
        categoryIV.setImageDrawable(getImageByType(event.getCategory()));
        addressTV = (EditText) findViewById(R.id.id_islocation_create);
        urlTV = (EditText) findViewById(R.id.id_isurl_create);
        descriptionTV = (EditText) findViewById(R.id.id_desc_create);
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
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


    //identify the event type by enum, and return the sticker associated with that type
    public Drawable getImageByType(EventType type){
        Drawable typeImage;
        if(type ==null){
            return null;
        }
        switch(type) {
            case EDUCATION:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Art);
                break;
            case SPORTS:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Sports);
                break;
            case FITNESS:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Fitness);
                break;
            case TECHNOLOGY:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Technology);
                break;
            case TRAVEL:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Travel);
                break;
            case OUTDOOR:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Outdoor);
                break;
            case GAMES:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Games);
                break;
            case ART:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Art);
                break;
            case CULTURE:
                typeImage = getResources().getDrawable(R.drawable.Sticker_culture);
                break;
            case CAREER:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Career);
                break;
            case BUSINESS:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Business);
                break;
            case COMMUNITY:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Community);
                break;
            case DANCING:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Dancing);
                break;
            case HEALTH:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Health);
                break;
            case HOBBIES:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Hobbies);
                break;
            case MOVEMENT:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Movement);
                break;
            case LANGUAGE:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Language);
                break;
            case MUSIC:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Music);
                break;
            case FAMILY:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Family);
                break;
            case PETS:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Pets);
                break;
            case RELIGION:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Religion);
                break;
            case SCIENCE:
                typeImage = getResources().getDrawable(R.drawable.Sticker_Science);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return typeImage;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        event.setStartDate(getDate(year, month, dayOfMonth));
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
}
