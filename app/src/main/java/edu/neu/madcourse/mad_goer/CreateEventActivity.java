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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.EventType;
import edu.neu.madcourse.mad_goer.messages.User;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Event event;
    private Button cancel;
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


        cancel = (TextView) findViewById(R.id.btn_cancel_create);
        eventNameTV = (TextView) findViewById(R.id.txt_event_name_create);
        date= (TextView) findViewById(R.id.txt_event_date_create);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        time= (TextView) findViewById(R.id.txt_event_time_create);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        categoryIV = (ImageView) findViewById(R.id.iv_category_create);
        categoryIV.setImageDrawable(getImageByType(event.getCategory()));
        categoryTV = (TextView) findViewById(R.id.txt_categories_create);
        addressTV = (EditText) findViewById(R.id.txt_address_create);
        urlTV = (EditText) findViewById(R.id.txt_url_create);
        descriptionTV = (EditText) findViewById(R.id.txt_description_create);
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

        switch(type){

        }
        Drawable typeImage = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };
        return typeImage;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
