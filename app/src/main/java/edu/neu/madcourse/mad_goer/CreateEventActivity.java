package edu.neu.madcourse.mad_goer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    //TODO: QUESTION: where is create event activity called? I need to pass userList from outside to this activity
    //added by Yang Yang, pass in UserList from mainactivity, and connect it to firebase, so when a new event
    //is created, add it to the user's eventlist(host) in firebase
    //Two things added: 1. userlist    2. firebase
    private ArrayList<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

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
                //TO DO: upload this event to firebase

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
                typeImage = getDrawable(R.drawable.Sticker_Education);
                break;
            case SPORTS:
                typeImage = getDrawable(R.drawable.Sticker_Sports);
                break;
            case FITNESS:
                typeImage = getDrawable(R.drawable.Sticker_Fitness);
                break;
            case TECHNOLOGY:
                typeImage = getDrawable(R.drawable.Sticker_Technology);
                break;
            case TRAVEL:
                typeImage = getDrawable(R.drawable.Sticker_Travel);
                break;
            case OUTDOOR:
                typeImage = getDrawable(R.drawable.Sticker_Outdoor);
                break;
            case GAMES:
                typeImage = getDrawable(R.drawable.Sticker_Games);
                break;
            case ART:
                typeImage = getDrawable(R.drawable.Sticker_Art);
                break;
            case CULTURE:
                typeImage = getDrawable(R.drawable.Sticker_culture);
                break;
            case CAREER:
                typeImage = getDrawable(R.drawable.Sticker_Career);
                break;
            case BUSINESS:
                typeImage = getDrawable(R.drawable.Sticker_Business);
                break;
            case COMMUNITY:
                typeImage = getDrawable(R.drawable.Sticker_Community);
                break;
            case DANCING:
                typeImage = getDrawable(R.drawable.Sticker_Dancing);
                break;
            case HEALTH:
                typeImage = getDrawable(R.drawable.Sticker_Health);
                break;
            case HOBBIES:
                typeImage = getDrawable(R.drawable.Sticker_Hobbies);
                break;
            case MOVEMENT:
                typeImage = getDrawable(R.drawable.Sticker_Movement);
                break;
            case LANGUAGE:
                typeImage = getDrawable(R.drawable.Sticker_Language);
                break;
            case MUSIC:
                typeImage = getDrawable(R.drawable.Sticker_Music);
                break;
            case FAMILY:
                typeImage = getDrawable(R.drawable.Sticker_Family);
                break;
            case PETS:
                typeImage = getDrawable(R.drawable.Sticker_Pets);
                break;
            case RELIGION:
                typeImage = getDrawable(R.drawable.Sticker_Religion);
                break;
            case SCIENCE:
                typeImage = getDrawable(R.drawable.Sticker_Science);
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
}
