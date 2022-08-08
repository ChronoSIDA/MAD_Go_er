package edu.neu.madcourse.mad_goer.ui.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.databinding.Fragment2GoBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.ui.recycleview.EventAdapter;

public class GoFragment extends Fragment {

    private Fragment2GoBinding binding;
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);


    private Map<String,String> personalEventMap;
    private ArrayList<ArrayList<Event>> listofEventLists;
    private RecyclerView recyclerView;
    private LinearLayout tabLayout;
    private EventAdapter eventAdapter;
    private LinearLayoutManager linearLayoutManager;
    private User currentUser;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment2GoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        recyclerView = binding.rvGofrag;
        tabLayout = binding.tabLayoutGo;

        MainActivity activity = (MainActivity) getActivity();
        currentUser = activity.getCurrentUser();
        //list is correct
        listofEventLists = activity.getListofEventLists();

        //default display all
        setUpRecyclerView(0);

        TextView textView_all = binding.tvAllGo;
        TextView textView_host = binding.tvHostGo;
        TextView textView_going = binding.tvGoingGo;
        TextView textView_saved = binding.tvSavedGo;
        TextView textView_past = binding.tvPastGo;


        textView_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(0);
            }
        });
        textView_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(1);
            }
        });
        textView_going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(2);
            }
        });
        textView_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(3);
            }
        });
        textView_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(4);
            }
        });



        return root;
    }

    public void setUpRecyclerView(int pos){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        if(listofEventLists != null && listofEventLists.get(0).size() > 0) {
            eventAdapter = new EventAdapter(listofEventLists.get(pos), getContext());
        }
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.scrollToPosition(listofEventLists.get(pos).size()-1);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    //moved to mainactivity as getEventListofUser()
//    public void updatemyEventMap(){
//      ...
//    }
}