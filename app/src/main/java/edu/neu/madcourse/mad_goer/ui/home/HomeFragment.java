package edu.neu.madcourse.mad_goer.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import edu.neu.madcourse.mad_goer.EventDetailActivity;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.databinding.Fragment1HomeBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.ui.recycleview.EventAdapter;
import edu.neu.madcourse.mad_goer.ui.recycleview.RecyclerItemClickListener;

public class HomeFragment extends Fragment {

    private User currentUser;
    private String nameTxt;
    private MainActivity activity;

    private Fragment1HomeBinding binding;
    private HashMap<String, Event> eventMap = new HashMap<>();
    private RecyclerView recyclerView;
    private ArrayList<String> eventResultAutocomplete = new ArrayList<>();
    private String[] eventNamesAutocomplete;
    private ArrayList<Event> eventList = new ArrayList<>();
    private Button btn_filter_home;
    private View filterView;
    private Button btn_cancel, btn_apply;
    int prog;
    private ArrayList<Event> currentList;

    // location service
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 99;

    DatabaseReference databaseEventRef = FirebaseDatabase.getInstance().getReference("Event");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment1HomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn_filter_home = binding.btnFilterHome;

        activity = (MainActivity) getActivity();
        //get all value from eventMap, and then get eventname from value


        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        eventMap = activity.getEventMap();
                        nameTxt = activity.getCurrentUserName();
                        setupeventlist();
                        setupRecycleView(eventList);
                    }
                },
                300);

        EditText autoCompleteEditText = binding.autoSearchTV;
        Handler handler = new Handler(Looper.getMainLooper());


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                eventNamesAutocomplete = activity.getAutoSearchList();
                String temp = autoCompleteEditText.getText().toString().trim();
                for (String listItem : eventNamesAutocomplete) {
                    if (listItem.contains(temp)) {
                        eventResultAutocomplete.add(listItem);
                    }
                }
                //we have eventMap(key: eventID, value: event object)
                //and we have array of eventNames eventResultAutocomplete
//
//                if(eventResultAutocomplete.size() == 0){
//                    eventResultAutocomplete.addAll(Arrays.asList(eventNamesAutocomplete));
//                }
//
                ArrayList<Event> autoList = new ArrayList<>();
                Collection<Event> values = eventMap.values();
                for (int i = 0; i < eventResultAutocomplete.size(); i++) {
                    for (Event e : values) {
                        if (e.getEventName().equals(eventResultAutocomplete.get(i))) {
                            autoList.add(e);
                        }
                    }
                }
                setupRecycleView(autoList);
                eventResultAutocomplete.clear();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        };
        autoCompleteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 500);
            }
        });

        locationRequest = LocationRequest.create();
        ActivityCompat.requestPermissions(activity , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        btn_filter_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(v, "Location Access Denied", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ActivityCompat.requestPermissions(activity , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                } else if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(v, "Location Access Denied", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                } else {
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(v.getContext());
                    Task task = fusedLocationProviderClient.getLastLocation();
                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            openFiterAlertBox(location);
                        }
                    });

                }
            }
        });

        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openFiterAlertBox(Location userCurrentLocation){

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.filter_popup,null);
        builder.setView(view);

        Button btn_apply = (Button) view.findViewById(R.id.btn_apply_filter);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel_filter);

        CheckBox isPublic = (CheckBox)view.findViewById(R.id.id_radio_public_filter);
        isPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){

                }else{

                }

            }
        });
        CheckBox isPrivate = (CheckBox) view.findViewById(R.id.id_radio_private_filter);
        isPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){

                }else{

                }

            }
        });
        CheckBox inPerson = (CheckBox) view.findViewById(R.id.id_radio_inperson_filter);
        inPerson.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){

                }else{

                }

            }
        });
        CheckBox virtual = (CheckBox) view.findViewById(R.id.id_radio_virtual_filter);
        virtual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){

                }else{

                }

            }
        });
        CheckBox distance = (CheckBox) view.findViewById(R.id.checkBox_distance_filter);
        SeekBar distanceBar = (SeekBar) view.findViewById(R.id.seekBar);
        distanceBar.setEnabled(false);
        distanceBar.setClickable(false);
        distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    distanceBar.setEnabled(true);
                    distanceBar.setClickable(true);
                }else{
                    distanceBar.setEnabled(false);
                    distanceBar.setClickable(false);
                }

            }
        });
        TextView seekBarText = (TextView) view.findViewById(R.id.seekBarText);
        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String distanceByProgress = "maximum distance.";
                switch(progress){
                    case 0:
                        distanceByProgress = "1 miles.";
                        break;
                    case 1:
                        distanceByProgress = "5 miles.";
                        break;
                    case 2:
                        distanceByProgress = "10 miles.";
                        break;
                    case 3:
                        distanceByProgress = "25 miles.";
                        break;
                    case 4:
                        distanceByProgress = "50 miles.";
                        break;
                    case 5:
                        distanceByProgress = "100 miles.";
                        break;
                    case 6:
                        distanceByProgress = "200 miles.";
                        break;
                    case 7:
                        distanceByProgress = "500 miles.";
                        break;
                    case 8:
                        distanceByProgress = "1000 miles.";
                        break;
                    default:
                        distanceByProgress = "maximum distance.";
                        break;
                }
                prog = progress;
                seekBarText.setText("Find Events within "+distanceByProgress);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog= builder.create();
        dialog.show();
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_apply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //isPublic.isChecked();
                //  filterByPreference(isPublic.isChecked(), isPrivate.isChecked(),inPerson.isChecked(),virtual.isChecked(), distance.isChecked(), prog);
                // prepare a list for recycleview


                Boolean haspublic = isPublic.isChecked();
                Boolean hasprivate = isPrivate.isChecked();
                Boolean hasinperson = inPerson.isChecked();
                Boolean hasvirtual = virtual.isChecked();
                Boolean checkeddistance = distance.isChecked();
                int selectDistance = -1;
                if(checkeddistance == true){
                    selectDistance = findIntByProgress(prog);
                }
                ArrayList<Event> filteredList = filterByPreference(haspublic,hasprivate,hasinperson,hasvirtual,selectDistance,userCurrentLocation);

                setupRecycleView(filteredList);
                dialog.dismiss();
            }
        });
    }
//TO DO: filter events by parameters
    public ArrayList<Event> filterByPreference(Boolean isPublic, Boolean isPrivate, Boolean inPerson, Boolean virtual, int selectDistance, Location userCurrentLocation){

        ArrayList<Event> filteredList = new ArrayList<>(eventList);

        // we already have eventList
        if((isPublic == false && isPrivate == false) || (isPublic == true && isPrivate == true)){
        //do nothing
        }else if(isPublic == false) {
            Iterator<Event> itr = filteredList.iterator();
            while(itr.hasNext()){
                Event event = itr.next();
                if(event.isPublic()){
                    itr.remove();
                }
            }
        }else if(isPrivate == false){
            Iterator<Event> itr = filteredList.iterator();
            while(itr.hasNext()){
                Event event = itr.next();
                if(!event.isPublic()){
                    itr.remove();
                }
            }
        }

        if((inPerson == false && virtual == false) || (inPerson == true && virtual == true)){
            //do nothing
        }else if(inPerson == false) {
            Iterator<Event> itr = filteredList.iterator();
            while(itr.hasNext()){
                Event event = itr.next();
                if(event.isInPerson()){
                    itr.remove();
                }
            }
        }else if(virtual == false){
            Iterator<Event> itr = filteredList.iterator();
            while(itr.hasNext()){
                Event event = itr.next();
                if(!event.isInPerson()){
                    itr.remove();
                }
            }
        }

        //if do not select Distance
        if(selectDistance == -1){
            return filteredList;
        }else{
            Iterator<Event> itr = filteredList.iterator();
            while(itr.hasNext()){
                Event event = itr.next();
                if(event.isInPerson()) {
                    if(userCurrentLocation == null) {
                        LayoutInflater factory = LayoutInflater.from(getActivity());
                        final View v = factory.inflate(R.layout.fragment_1_home,null);
                        if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Snackbar.make(v, "Location Access Denied", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            ActivityCompat.requestPermissions(activity , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                        } else if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Snackbar.make(v, "Location Access Denied", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
                        }
                    } else {
                        if (event.calDistance(userCurrentLocation) > selectDistance) {
                            itr.remove();
                        }
                    }
                }
            }
            return filteredList;
        }
    }

    public int findIntByProgress(int progress){
        int distanceByProgress;
        switch(progress) {
            case 0:
                distanceByProgress = 1;
                break;
            case 1:
                distanceByProgress = 5;
                break;
            case 2:
                distanceByProgress = 10;
                break;
            case 3:
                distanceByProgress = 25;
                break;
            case 4:
                distanceByProgress = 50;
                break;
            case 5:
                distanceByProgress = 100;
                break;
            case 6:
                distanceByProgress = 200;
                break;
            case 7:
                distanceByProgress = 500;
                break;
            case 8:
                distanceByProgress = 1000;
                break;
            default:
                distanceByProgress = Integer.MAX_VALUE;
                break;
        }
        return distanceByProgress;
    }

    public void setupeventlist() {
        if (eventMap != null) {
            Collection<Event> values = eventMap.values();
            //ArrayList<Event> eventList = new ArrayList<>(values);

            for (Event e : values) {
                if (!e.isPast()) {
                    eventList.add(e);
                }
            }
        }
    }

    public void setupRecycleView(ArrayList<Event> list){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.rvHomefrag;
        EventAdapter eventAdapter;
        //convert the eventMap to arraylist to fit in the first parameter type

        eventAdapter = new EventAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);



        currentList = list;

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position){

                Intent intent = new Intent(getContext(), EventDetailActivity.class);

                intent.putExtra("eventID", currentList.get(position).getEventID());
                intent.putExtra("nameTxt", nameTxt);

                // public or private:
                if(currentList.get(position).isPublic()){
                    startActivity(intent);
                } else {
                    activity.verifyPassword(currentList.get(position), intent);
                }
            }
            @Override
            public void onLongItemClick(View view, int position){

            }
        }));
    }

//    public int getProg(){
//        return this.prog;
//    }
//
//    public void setProg(int progress){
//        this.prog = progress;
//    }

    public void applyButtonOnCheck(){
        // should in onCreate set listner
        // private, public, in-person, virtual distance
    }
}


