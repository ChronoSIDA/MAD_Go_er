package edu.neu.madcourse.mad_goer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.mad_goer.messages.User;

//import edu.neu.madcourse.mad_goer.messages.MemoryData;

public class LoginActivity extends AppCompatActivity{
    EditText input_userName;
    Button btn_login;
    private String nameTxt;
    private User currentUser;

    //TODO: *this is alternative solution*
    //TODO: we can get userlist from firebase from loginActivity, also we have user object in login activity
    //TODO: so, wo can create methods of getCurrentUserObj and getUserList in Login Activity, when other activity
    //TODO: want current user obj info, they can just new a LoginActivity, and call its method
    //TODO: if implement this way, we also need to move listener for user from mainactivity to login activity

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://goerapp-4e3c7-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText input_username = findViewById(R.id.input_username);
        final Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //final String nameTxt = input_username.getText().toString();
                nameTxt = input_username.getText().toString();

                //hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                if (nameTxt.isEmpty()){
                    Toast.makeText(LoginActivity.this,"USERNAME REQUIRED",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child("User").hasChild(nameTxt)){
                                Toast.makeText(LoginActivity.this,"Welcome back, " + nameTxt,Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(LoginActivity.this, edu.neu.madcourse.mad_goer.MainActivity.class);
                                intent.putExtra("nameTxt",nameTxt);
                                startActivity(intent);
                            }else{
                                //pass the user to database
                                if(nameTxt == null){
                                    Toast.makeText(LoginActivity.this,"Username cannot be empty",Toast.LENGTH_SHORT).show();
                                } else if(nameTxt.length() < 3 || nameTxt.length() > 10){
                                    Toast.makeText(LoginActivity.this,"Username length cannot be less than 3 characters \nUsername length cannot be longer than 10 characters",Toast.LENGTH_LONG).show();
                                } else {
                                    currentUser = new User(nameTxt);

                                    //added a user object to database under Users
                                    databaseReference.child("User").child(nameTxt).setValue(currentUser);
                                    Toast.makeText(LoginActivity.this,"new account created!",Toast.LENGTH_SHORT).show();

                                    //change launch activity to select interest
                                    Intent intent =  new Intent(LoginActivity.this, edu.neu.madcourse.mad_goer.InterestActivity.class);
                                    intent.putExtra("nameTxt",nameTxt);
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });
//
//        databaseReference.child("User").child(nameTxt).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    System.out.println("firebase Error getting data");
//                }
//                else {
//                    System.out.println("firebase");
//                    for(DataSnapshot snapshot : task.getResult().getChildren()) {
//                        User testUser = snapshot.getValue(User.class);
//                    }
//                }
//            }
//        });


        Button btn_about = findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

//        input_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                }
//            }
//        });
    }

    public String getCurrentUserName() {
        return nameTxt;
    }
    public User getCurrentUser() {
        return this.currentUser;
    }

//    public void hideKeyboard(View view) {
//        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}