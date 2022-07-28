package edu.neu.madcourse.mad_goer.ui.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private RecyclerView recyclerView;
    private String currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment2GoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.rvGofrag;

        MainActivity activity = (MainActivity) getActivity();

        eventMap = activity.getTotalEvents();
        currentUser=activity.getCurrentUser();
        userList= activity.getUserList();
        Optional<User> matchingObject= userList.stream().filter(p->p.getUserID().equals(currentUser)).findAny();
        User curUser = matchingObject.get();

        if(curUser!= null) {
            personalEventMap =curUser.getTotalPersonalEvents();
        }
        //TODO: find the event obj from

        EventAdapter eventAdapter = new EventAdapter(eventMap,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
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
}