package edu.neu.madcourse.mad_goer.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.neu.madcourse.mad_goer.EventDetailActivity;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.Fragment1HomeBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.ui.recycleview.EventAdapter;
import edu.neu.madcourse.mad_goer.ui.recycleview.RecyclerItemClickListener;

public class HomeFragment extends Fragment {

    private Fragment1HomeBinding binding;
    private HashMap<String,Event> eventMap;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment1HomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.rvHomefrag;

        //when clicked something in recycleview(aka the event list), get the event id from the item clicked
        //and pass the eventid to intent, and open new activity of(eventdetailactivity)
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),recyclerView,new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Intent intent = new Intent(getContext(), EventDetailActivity.class);
                intent.putExtra("eventID", eventMap.get(position).getEventID());
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position){

            }
        }));



        MainActivity activity = (MainActivity) getActivity();
        eventMap = activity.getTotalEvents();

//       ArrayList<HashMap<String,Event>> arrayList = new ArrayList<>();
//       arrayList.add(eventMap);

        EventAdapter eventAdapter = new EventAdapter(eventMap,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        //added Jul14
        //will auto show cardview from bottom
        recyclerView.scrollToPosition(eventMap.size()-1);


        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

