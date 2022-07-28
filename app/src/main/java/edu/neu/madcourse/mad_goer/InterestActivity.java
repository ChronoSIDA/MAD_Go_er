package edu.neu.madcourse.mad_goer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.mad_goer.messages.EventType;
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
        music_cb = (CheckBox) findViewById(R.id.cbox_music_interest);
        edu_cb = (CheckBox) findViewById(R.id.cbox_sport_interest);
        sports_cb = (CheckBox) findViewById(R.id.cbox_sport_interest);
        fitness_cb = (CheckBox) findViewById(R.id.cbox_fitness_interest);
        tech_cb = (CheckBox) findViewById(R.id.cbox_tech_interest);
        travel_cb = (CheckBox) findViewById(R.id.cbox_travel_interest);
        outdoor_cb = (CheckBox) findViewById(R.id.cbox_outdoor_interest);
        game_cb = (CheckBox) findViewById(R.id.cbox_game_interest);
        art_cb = (CheckBox) findViewById(R.id.cbox_art_interest);
        cult_cb = (CheckBox) findViewById(R.id.cbox_culture_interest);
        career_cb = (CheckBox) findViewById(R.id.cbox_career_interest);
        business_cb = (CheckBox) findViewById(R.id.cbox_business_interest);
        community_cb = (CheckBox) findViewById(R.id.cbox_community_interest);
        dance_cb = (CheckBox) findViewById(R.id.cbox_dance_interest);
        health_cb = (CheckBox) findViewById(R.id.cbox_health_interest);
        hobby_cb = (CheckBox) findViewById(R.id.cbox_hobby_interest);
        movement_cb = (CheckBox) findViewById(R.id.cbox_movement_interest);
        language_cb = (CheckBox) findViewById(R.id.cbox_language_interest);
        family_cb = (CheckBox) findViewById(R.id.cbox_family_interest);
        pet_cb = (CheckBox) findViewById(R.id.cbox_pets_interest);
        religion_cb = (CheckBox) findViewById(R.id.cbox_religion_interest);
        science_cb = (CheckBox) findViewById(R.id.cbox_science_interest);

        //add interest type into users' interest list when clicking
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(music_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.MUSIC);
                }

                if(edu_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.EDUCATION);
                }

                if(fitness_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.FITNESS);
                }

                if(sports_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.SPORTS);
                }

                if(tech_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.TECHNOLOGY);
                }

                if(travel_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.TRAVEL);
                }

                if(outdoor_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.OUTDOOR);
                }

                if(art_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.ART);
                }

                if(cult_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.CULTURE);
                }

                if(game_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.GAMES);
                }

                if(career_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.CAREER);
                }

                if(business_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.BUSINESS);
                }

                if(community_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.COMMUNITY);
                }

                if(dance_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.DANCING);
                }

                if(health_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.HEALTH);
                }

                if(hobby_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.HOBBIES);
                }

                if(movement_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.MOVEMENT);
                }

                if(family_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.FAMILY);
                }

                if(language_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.LANGUAGE);
                }

                if(pet_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.PETS);
                }

                if(science_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.SCIENCE);
                }

                if(religion_cb.isChecked()){
                    user.getInterestedTypeList().add(EventType.RELIGION);
                }
            }
        });

    }

    public void returnMain(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
