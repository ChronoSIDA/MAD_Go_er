package edu.neu.madcourse.mad_goer.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
    private HashMap<String,Event> eventMap = new HashMap<>();
    private RecyclerView recyclerView;
    private ArrayList<String> eventResultAutocomplete = new ArrayList<>();
    private String[] eventNamesAutocomplete;
    private ArrayList<Event> eventList = new ArrayList<>();
    private Button btn_filter_home;
    private View filterView;
    private Button btn_cancel, btn_apply;
    int prog;

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
                for(String listItem : eventNamesAutocomplete) {
                    if (listItem.contains(temp)) {
                        eventResultAutocomplete.add(listItem);
                    }
                }
                System.out.println(eventResultAutocomplete);
                //we have eventMap(key: eventID, value: event object)
                //and we have array of eventNames eventResultAutocomplete
//
//                if(eventResultAutocomplete.size() == 0){
//                    eventResultAutocomplete.addAll(Arrays.asList(eventNamesAutocomplete));
//                }
//
                ArrayList<Event> autoList = new ArrayList<>();
                Collection<Event> values = eventMap.values();
                for(int i = 0; i<eventResultAutocomplete.size();i++) {
                    for (Event e : values) {
                        if(e.getEventName().equals(eventResultAutocomplete.get(i))){
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

        btn_filter_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFiterAlertBox();

            }
        });

        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openFiterAlertBox(){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.filter_popup,null);
        builder.setView(view);
        Boolean isPub = false;
        Boolean isPri = false;
        Boolean inPer = false;
        Boolean vir = false;
        Boolean dist= false;

        Button btn_apply = (Button) view.findViewById(R.id.btn_apply_filter);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel_filter);

//        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
//            public void onClick(DialogInterface dialog, int which){
//                dialog.dismiss();
//            }
//        });
        CheckBox isPublic = (CheckBox)view.findViewById(R.id.id_radio_public_filter);
        CheckBox isPrivate = (CheckBox) view.findViewById(R.id.id_radio_private_filter);
        CheckBox inPerson = (CheckBox) view.findViewById(R.id.id_radio_inperson_filter);
        CheckBox virtual = (CheckBox) view.findViewById(R.id.id_radio_virtual_filter);
        CheckBox distance = (CheckBox) view.findViewById(R.id.checkBox_distance_filter);
        SeekBar distanceBar = (SeekBar) view.findViewById(R.id.seekBar);
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
              //  filterByPreference(isPublic.isChecked(), isPrivate.isChecked(),inPerson.isChecked(),virtual.isChecked(), distance.isChecked(), prog);
            }
        });
    }
//TO DO: filter events by parameters
    public void filterByPreference(Boolean isPublic, Boolean isPrivate, Boolean inPerson, Boolean virtual, Boolean distance, int progress){
        int distanceRange = findIntByProgress(progress);
        ArrayList<Event> filterList = new ArrayList<>();

        if (eventMap != null) {
            Collection<Event> values = eventMap.values();
            for (Event e : values) {
                if (!e.isPast()){
                    filterList.add(e);
                }
            }
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

        //added Jul14
        //will auto show cardview from bottom
        recyclerView.scrollToPosition(eventMap.size()-1);


        //when clicked something in recycleview(aka the event list), get the event id from the item clicked
        //and pass the eventid to intent, and open new activity of(eventdetailactivity)
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position){

                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                Collection<Event> values = eventMap.values();
                ArrayList<Event> eventList = new ArrayList<>(values);

                intent.putExtra("eventID", eventList.get(position).getEventID());

                intent.putExtra("nameTxt", nameTxt);

                // public or private:
                if(eventList.get(position).isPublic()){
                    startActivity(intent);
                } else {
                    activity.verifyPassword(eventList.get(position), intent);
//                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                    alertDialog.setTitle("PASSWORD");
//                    alertDialog.setMessage("Enter Password");
//                    final EditText input = new EditText(getActivity());
//                    alertDialog.setView(input);
//                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"CONFIRM",new View.OnClickListener(){
//                        @Override
//                        public void onClick(View view) {
//                            if(input.getText().toString().equals(eventMap.get(position).getEventPassword()){
//                                startActivity(intent);
//                            }else{
//
//                            }
//                        }
//                    });
//
//                    alertDialog.setButton(());

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


