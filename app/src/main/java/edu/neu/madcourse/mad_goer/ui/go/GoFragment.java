package edu.neu.madcourse.mad_goer.ui.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private TextView textView_all;
    private TextView textView_host;
    private TextView textView_going;
    private TextView textView_saved;
    private TextView textView_past;
    private LinearLayout tab_all_go;
    private LinearLayout tab_all_host;
    private LinearLayout tab_all_going;
    private LinearLayout tab_all_saved;
    private LinearLayout tab_all_past;

    private ImageView imageView_all;
    private ImageView imageView_host;
    private ImageView imageView_going;
    private ImageView imageView_saved;
    private ImageView imageView_past;

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

        textView_all = binding.tvAllGo;
        textView_host = binding.tvHostGo;
        textView_going = binding.tvGoingGo;
        textView_saved = binding.tvSavedGo;
        textView_past = binding.tvPastGo;
        tab_all_go = binding.tabAllGo;
        tab_all_host = binding.tabHostGo;
        tab_all_going=binding.tabGoingGo;
        tab_all_saved = binding.tabSavedGo;
        tab_all_past=binding.tabPastGo;


        imageView_all = binding.imgAllGo;
        imageView_host = binding.imgHostGo;
        imageView_going = binding.imgGoingGo;
        imageView_saved = binding.imgSavedGo;
        imageView_past = binding.imgPastGo;


        tab_all_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(0);
                changeBackToDefault();
                textView_all.setTextColor(getResources().getColor(R.color.lightRed));
                imageView_all.setVisibility(View.VISIBLE);
            }
        });
        tab_all_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(1);
                changeBackToDefault();
                textView_host.setTextColor(getResources().getColor(R.color.lightRed));
                imageView_host.setVisibility(View.VISIBLE);
            }
        });
        tab_all_going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(2);
                changeBackToDefault();
                textView_going.setTextColor(getResources().getColor(R.color.lightRed));
                imageView_going.setVisibility(View.VISIBLE);
            }
        });
        tab_all_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(3);
                changeBackToDefault();
                textView_saved.setTextColor(getResources().getColor(R.color.lightRed));
                imageView_saved.setVisibility(View.VISIBLE);
            }
        });
        tab_all_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRecyclerView(4);
                changeBackToDefault();
                textView_past.setTextColor(getResources().getColor(R.color.lightRed));
                imageView_past.setVisibility(View.VISIBLE);
            }
        });



        return root;
    }

    public void changeBackToDefault(){
        textView_all.setTextColor(getResources().getColor(R.color.black));
        textView_host.setTextColor(getResources().getColor(R.color.black));
        textView_going.setTextColor(getResources().getColor(R.color.black));
        textView_saved.setTextColor(getResources().getColor(R.color.black));
        textView_past.setTextColor(getResources().getColor(R.color.black));

        imageView_all.setVisibility(View.INVISIBLE);
        imageView_host.setVisibility(View.INVISIBLE);
        imageView_going.setVisibility(View.INVISIBLE);
        imageView_saved.setVisibility(View.INVISIBLE);
        imageView_past.setVisibility(View.INVISIBLE);
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