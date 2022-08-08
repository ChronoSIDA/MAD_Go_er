package edu.neu.madcourse.mad_goer.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private HashMap<String,Event> eventMap;
    private RecyclerView recyclerView;
    private ArrayList<String> eventResultAutocomplete = new ArrayList<>();
    private String[] eventNamesAutocomplete;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment1HomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = (MainActivity) getActivity();
        //get all value from eventMap, and then get eventname from value


        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        eventMap = activity.getEventMap();
                        nameTxt = ((MainActivity) getActivity()).getCurrentUserName();
                        setDataOnDelay();
                    }
                },
                300);

        EditText autoCompleteEditText = binding.autoSearchTV;
        Handler handler = new Handler(Looper.getMainLooper());
        autoCompleteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.sendEmptyMessage(0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(handler.hasMessages(0)){
                            handler.removeCallbacksAndMessages(null);
                        } else {
                            eventNamesAutocomplete = ((MainActivity) getActivity()).getAutoSearchList();
                            String temp = autoCompleteEditText.getText().toString().trim();
                            for(String listItem : eventNamesAutocomplete) {
                                if (listItem.contains(temp)) {
                                    eventResultAutocomplete.add(listItem);
                                }
                            }

                            // TODO 1.append recyclerview
                            // TODO 2.clean eventResultAutocomplete after recyceler view is finished
                        }
                    }
                }, 800);
            }
        });

        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setDataOnDelay(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.rvHomefrag;

        //convert the eventMap to arraylist to fit in the first parameter type
        if(eventMap != null){
            Collection<Event> values = eventMap.values();
            //ArrayList<Event> eventList = new ArrayList<>(values);
            ArrayList<Event> eventList = new ArrayList<>();
            for(Event e: values){
                if(!e.isPast()){
                    eventList.add(e);
                }
            }
            EventAdapter eventAdapter = new EventAdapter(eventList,getContext());
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
    }
}

