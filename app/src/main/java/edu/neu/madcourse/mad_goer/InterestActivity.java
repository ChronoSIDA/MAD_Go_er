package edu.neu.madcourse.mad_goer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.mad_goer.messages.User;


public class InterestActivity extends AppCompatActivity {
    private User user;
    private Button btn;
    private TextView skip;
    private CheckBox music_cb;
    private CheckBox edu_cb;
    private CheckBox sports_cb;
    private CheckBox fitness_cb;
    private CheckBox tech_cb;
    private CheckBox travel_cb;
    private CheckBox outdoor_cb;
    private CheckBox game_cb;
    private CheckBox art_cb;
    private CheckBox cult_cb;
    private CheckBox career_cb;
    private CheckBox business_cb;
    private CheckBox community_cb;
    private CheckBox dance_cb;
    private CheckBox health_cb;
    private CheckBox hobby_cb;
    private CheckBox movement_cb;
    private CheckBox language_cb;
    private CheckBox family_cb;
    private CheckBox pet_cb;
    private CheckBox religion_cb;
    private CheckBox science_cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);


        btn = (Button) findViewById(R.id.btn_go_interest);
        skip = (TextView) findViewById(R.id.id_skip_interest);
        music_cb = findViewById(R.id.cbox_music_interest);
        edu_cb = findViewById(R.id.cbox_sport_interest);
        sports_cb = findViewById(R.id.cbox_sport_interest);
        fitness_cb = findViewById(R.id.cbox_fitness_interest);
        tech_cb = findViewById(R.id.cbox_tech_interest);
        travel_cb = findViewById(R.id.cbox_travel_interest);
        outdoor_cb = findViewById(R.id.cbox_outdoor_interest);
        game_cb = findViewById(R.id.cbox_game_interest);
        art_cb = findViewById(R.id.cbox_art_interest);
        cult_cb = findViewById(R.id.cbox_culture_interest);
        career_cb = findViewById(R.id.cbox_career_interest);
        business_cb = findViewById(R.id.cbox_business_interest);
        community_cb = findViewById(R.id.cbox_community_interest);
        dance_cb = findViewById(R.id.cbox_dance_interest);
        health_cb = findViewById(R.id.cbox_health_interest);
        hobby_cb = findViewById(R.id.cbox_hobby_interest);
        movement_cb = findViewById(R.id.cbox_movement_interest);
        language_cb = findViewById(R.id.cbox_language_interest);
        family_cb = findViewById(R.id.cbox_family_interest);
        pet_cb = findViewById(R.id.cbox_pets_interest);
        religion_cb = findViewById(R.id.cbox_religion_interest);
        science_cb = findViewById(R.id.cbox_science_interest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: user interest list add the chosen categories

            }
        });

    }

    public void mainActivityNav(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
