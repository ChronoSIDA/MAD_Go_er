package edu.neu.madcourse.mad_goer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Boolean isLogin;


    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://goerapp-4e3c7-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String nameTxt = extras.getString("nameTxt");
        isLogin = extras.getBoolean("isLogin");


        //method 1:
        DatabaseReference curUserRef = databaseReference.child("User").child(nameTxt);
        //read the user once from firebase, and save it to our user field.
        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                System.out.println(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("failed");
            }
        });


        btn = (Button) findViewById(R.id.btn_go_interest);
        skip = (TextView) findViewById(R.id.id_skip_interest);
        music_cb = (CheckBox) findViewById(R.id.cbox_music_interest);
        edu_cb = (CheckBox) findViewById(R.id.cbox_edu_interest2);
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


        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        if(user.getInterestedTypeList() != null){
                            setInterestList();
                        }
                    }
                },
                100);


        //check box on click change bg color
        Drawable selected = getResources().getDrawable(R.drawable.interest_selected);
        Drawable unSelected = getResources().getDrawable(R.drawable.interest_unselected);
        music_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(music_cb.isChecked()){
                    music_cb.setBackground(selected);
                }else{
                    music_cb.setBackground(unSelected);
                }
            }
        });

        edu_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(edu_cb.isChecked()){
                    edu_cb.setBackground(selected);
                }else{
                    edu_cb.setBackground(unSelected);
                }
            }
        });

        sports_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sports_cb.isChecked()){
                    sports_cb.setBackground(selected);
                }else{
                    sports_cb.setBackground(unSelected);
                }
            }
        });

        fitness_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(fitness_cb.isChecked()){
                    fitness_cb.setBackground(selected);
                }else{
                    fitness_cb.setBackground(unSelected);
                }
            }
        });

        tech_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(tech_cb.isChecked()){
                    tech_cb.setBackground(selected);
                }else{
                    tech_cb.setBackground(unSelected);
                }
            }
        });

        outdoor_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(outdoor_cb.isChecked()){
                    outdoor_cb.setBackground(selected);
                }else{
                    outdoor_cb.setBackground(unSelected);
                }
            }
        });

        travel_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(travel_cb.isChecked()){
                    travel_cb.setBackground(selected);
                }else{
                    travel_cb.setBackground(unSelected);
                }
            }
        });

        game_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(game_cb.isChecked()){
                    game_cb.setBackground(selected);
                }else{
                    game_cb.setBackground(unSelected);
                }
            }
        });

        art_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(art_cb.isChecked()){
                    art_cb.setBackground(selected);
                }else{
                    art_cb.setBackground(unSelected);
                }
            }
        });

        cult_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cult_cb.isChecked()){
                    cult_cb.setBackground(selected);
                }else{
                    cult_cb.setBackground(unSelected);
                }
            }
        });

        career_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(career_cb.isChecked()){
                    career_cb.setBackground(selected);
                }else{
                    career_cb.setBackground(unSelected);
                }
            }
        });

        business_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(business_cb.isChecked()){
                    business_cb.setBackground(selected);
                }else{
                    business_cb.setBackground(unSelected);
                }
            }
        });

        community_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(community_cb.isChecked()){
                    community_cb.setBackground(selected);
                }else{
                    community_cb.setBackground(unSelected);
                }
            }
        });

        dance_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(dance_cb.isChecked()){
                    dance_cb.setBackground(selected);
                }else{
                    dance_cb.setBackground(unSelected);
                }
            }
        });

        health_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(health_cb.isChecked()){
                    health_cb.setBackground(selected);
                }else{
                    health_cb.setBackground(unSelected);
                }
            }
        });

        hobby_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(hobby_cb.isChecked()){
                    hobby_cb.setBackground(selected);
                }else{
                    hobby_cb.setBackground(unSelected);
                }
            }
        });

        movement_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(movement_cb.isChecked()){
                    movement_cb.setBackground(selected);
                }else{
                    movement_cb.setBackground(unSelected);
                }
            }
        });

        language_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(language_cb.isChecked()){
                    language_cb.setBackground(selected);
                }else{
                    language_cb.setBackground(unSelected);
                }
            }
        });

        family_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(family_cb.isChecked()){
                    family_cb.setBackground(selected);
                }else{
                    family_cb.setBackground(unSelected);
                }
            }
        });

        pet_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(pet_cb.isChecked()){
                    pet_cb.setBackground(selected);
                }else{
                    pet_cb.setBackground(unSelected);
                }
            }
        });

        religion_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(religion_cb.isChecked()){
                    religion_cb.setBackground(selected);
                }else{
                    religion_cb.setBackground(unSelected);
                }
            }
        });

        science_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(science_cb.isChecked()){
                    science_cb.setBackground(selected);
                }else{
                    science_cb.setBackground(unSelected);
                }
            }
        });


        //add interest type into users' interest list when clicking
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.clearInterestList();

                if(music_cb.isChecked()){
                    user.addInterestType(EventType.MUSIC);
//                    System.out.println("123");
                }

                if(edu_cb.isChecked()){
                    user.addInterestType(EventType.EDUCATION);
                }

                if(fitness_cb.isChecked()){
                    user.addInterestType(EventType.FITNESS);
                }

                if(sports_cb.isChecked()){
                    user.addInterestType(EventType.SPORTS);
                }

                if(tech_cb.isChecked()){
                    user.addInterestType(EventType.TECHNOLOGY);
                }

                if(travel_cb.isChecked()){
                    user.addInterestType(EventType.TRAVEL);
                }

                if(outdoor_cb.isChecked()){
                    user.addInterestType(EventType.OUTDOOR);
                }

                if(art_cb.isChecked()){
                    user.addInterestType(EventType.ART);
                }

                if(cult_cb.isChecked()){
                    user.addInterestType(EventType.CULTURE);
                }

                if(game_cb.isChecked()){
                    user.addInterestType(EventType.GAMES);
                }

                if(career_cb.isChecked()){
                    user.addInterestType(EventType.CAREER);
                }

                if(business_cb.isChecked()){
                    user.addInterestType(EventType.BUSINESS);
                }

                if(community_cb.isChecked()){
                    user.addInterestType(EventType.COMMUNITY);
                }

                if(dance_cb.isChecked()){
                    user.addInterestType(EventType.DANCING);
                }

                if(health_cb.isChecked()){
                    user.addInterestType(EventType.HEALTH);
                }

                if(hobby_cb.isChecked()){
                    user.addInterestType(EventType.HOBBIES);
                }

                if(movement_cb.isChecked()){
                    user.addInterestType(EventType.MOVEMENT);
                }

                if(family_cb.isChecked()){
                    user.addInterestType(EventType.FAMILY);
                }

                if(language_cb.isChecked()){
                    user.addInterestType(EventType.LANGUAGE);
                }

                if(pet_cb.isChecked()){
                    user.addInterestType(EventType.PETS);
                }

                if(science_cb.isChecked()){
                    user.addInterestType(EventType.SCIENCE);
                }

                if(religion_cb.isChecked()){
                    user.addInterestType(EventType.RELIGION);
                }

                System.out.println(user.getInterestedTypeList());

                //after checking all this, user should also be updated in firebase
                databaseReference.child("User").child(user.getUserID()).setValue(user);

                if(isLogin){
                    Intent intent =  new Intent(InterestActivity.this, edu.neu.madcourse.mad_goer.MainActivity.class);
                    intent.putExtra("nameTxt", nameTxt);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
    }

    public void setInterestList(){
        //check if user interest category contains (for setting -> interest setting)
        if(user.getInterestedTypeList().contains(EventType.MUSIC)){
            music_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.EDUCATION)){
            edu_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.SPORTS)){
            sports_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.FITNESS)){
            fitness_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.TECHNOLOGY)){
            tech_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.TRAVEL)){
            travel_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.OUTDOOR)){
            outdoor_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.GAMES)){
            game_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.ART)){
            art_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.CULTURE)){
            cult_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.CAREER)){
            career_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.BUSINESS)){
            business_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.COMMUNITY)){
            community_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.DANCING)){
            dance_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.HEALTH)){
            health_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.HOBBIES)){
            hobby_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.MOVEMENT)){
            movement_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.LANGUAGE)){
            language_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.FAMILY)){
            family_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.PETS)){
            pet_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.RELIGION)){
            religion_cb.setChecked(true);
        }
        if(user.getInterestedTypeList().contains(EventType.SCIENCE)){
            science_cb.setChecked(true);
        }
    }
}
