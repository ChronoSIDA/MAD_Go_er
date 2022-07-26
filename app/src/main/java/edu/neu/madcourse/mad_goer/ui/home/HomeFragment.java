package edu.neu.madcourse.mad_goer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.Fragment1HomeBinding;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.ui.recycleview.EventAdapter;

public class HomeFragment extends Fragment {

    private Fragment1HomeBinding binding;
    private ArrayList<Event> eventList;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment1HomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.rvHomefrag;


        MainActivity activity = (MainActivity) getActivity();
        eventList = activity.getTotalEventList();

        EventAdapter eventAdapter = new EventAdapter(eventList,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        //added Jul14
        //will auto show cardview from bottom
        recyclerView.scrollToPosition(eventList.size()-1);


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

