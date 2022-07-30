package edu.neu.madcourse.mad_goer.ui.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.Fragment2GoBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.ui.recycleview.EventAdapter;

public class GoFragment extends Fragment {

    private Fragment2GoBinding binding;
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);

    //key is "eventID", value is Event
    //would need all eventID under currentUser's personal eventmap
    //we already have userlist from firebase, userlist contains User object, find currentUser from the UserList,
    // and inside Userobject there is a personalEventMap,
    // which is what we need to pass in the recyclerView
    //easier way is to call getHostEvent()...methods in user to return filtered hashMap
    private HashMap<String,Event> eventMap;
    private ArrayList<User> userList;
    //this is user's personal eventmap, key is "eventID", value is "eventtype(host/attending/saved/past)"
    private Map<String,String> personalEventMap;
    private String currentUser;
    private HashMap<String,Event> myEventMap;
    private HashMap<String,Event> myhostevents;
    private HashMap<String,Event> mygoingevents;
    private HashMap<String,Event> mysavedevents;
    private HashMap<String,Event> mypastevents;

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private boolean isUserScrolling = false;
    private boolean isListGoingUp = true;

    private EventAdapter eventAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment2GoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = binding.rvGofrag;
        tabLayout = binding.tabLayoutGo;

        updatemyEventMap();
        /*
        move to getmyEventMap() function
        MainActivity activity = (MainActivity) getActivity();
        eventMap = activity.getTotalEvents();

        currentUser=activity.getCurrentUser();
        userList= activity.getUserList();

        Optional<User> matchingObject= userList.stream().filter(p->p.getUserID().equals(currentUser)).findAny();
        User curUser = matchingObject.get();

        if(curUser!= null) {
            personalEventMap =curUser.getTotalPersonalEvents();
        }
        //find a myEventMap<String,Event> of eventID in personalEventMap<String,string> from eventMap<String, Event>
        Set<String> eventIDkeySet = personalEventMap.keySet();
        myEventMap = new HashMap<>();

        for(String key : eventIDkeySet){
            myEventMap.put(key,eventMap.get(key));
        }
        */

        eventAdapter = new EventAdapter(myEventMap,getContext());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isUserScrolling = false;
                int position = tab.getPosition();
                if(position == 0){
                    recyclerView.smoothScrollToPosition();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(eventMap.size()-1);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updatemyEventMap(){
        MainActivity activity = (MainActivity) getActivity();
        eventMap = activity.getTotalEvents();
        currentUser=activity.getCurrentUser();
        userList= activity.getUserList();

        Optional<User> matchingObject= userList.stream().filter(p->p.getUserID().equals(currentUser)).findAny();
        User curUser = matchingObject.get();

        if(curUser!= null) {
            //Key is eventID, value is "saved"/"Host"/"past"
            personalEventMap =curUser.getTotalPersonalEvents();
        }
        //find a myEventMap<String,Event> of eventID in personalEventMap<String,string> from eventMap<String, Event>
        //key is eventID
        Set<String> eventIDkeySet = personalEventMap.keySet();
        myEventMap = new HashMap<>();



        for(String key : eventIDkeySet){
            //myEventMap(key:eventID,value:eventObj)
            //which is my all events
            myEventMap.put(key,eventMap.get(key));

            if(personalEventMap.get(key).equals("host")){

            }
            if(personalEventMap.get(key).equals("going")){

            }
            if(personalEventMap.get(key).equals("saved")){

            }
            if(personalEventMap.get(key).equals("past")){

            }
        }
    }

}