package edu.neu.madcourse.mad_goer;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.ui.PasswordDialog;


public class EventDetailActivity extends AppCompatActivity {
    private Event event;
    private Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
//        inputText = findViewById(R.id.city_et);

        joinBtn = (Button) findViewById(R.id.join_btn);
        joinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                passwordDialog();
            }
        });

    }

    public void passwordDialog(){
        PasswordDialog passwordDialog = new PasswordDialog();
        passwordDialog.show(getSupportFragmentManager(), "password dialog");
    }
}
