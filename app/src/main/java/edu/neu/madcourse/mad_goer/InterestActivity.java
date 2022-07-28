package edu.neu.madcourse.mad_goer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.mad_goer.messages.User;


public class InterestActivity extends AppCompatActivity {
    private User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);


        joinBtn = (Button) findViewById(R.id.btn_join_detail);
        hostTV = (TextView) findViewById(R.id.txt_host_detail);



    }
}
