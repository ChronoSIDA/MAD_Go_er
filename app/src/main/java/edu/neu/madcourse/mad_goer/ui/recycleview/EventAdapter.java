package edu.neu.madcourse.mad_goer.ui.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Event;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Viewholder>{

    private final ArrayList<Event> eventList;
    private final Context context;


    public EventAdapter(ArrayList<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }


    @NonNull
    @Override
    public EventAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new Viewholder(view);
        // return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, null));
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.Viewholder holder, int position) {

        //on item selected
        Event event = eventList.get(position);

        holder.hostName.setText(event.getHost().getUserID());
        holder.eventName.setText(event.getEventName());
        //event Date is startDate for now
        //TODO: consider change to a range of Date in future
        holder.eventDate.setText(event.getStartDate().toString());
        //TODO:override toString for location, or add a format
        holder.eventLocation.setText(event.getLocation().toString());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private ImageView eventIcon;
        private TextView eventName,hostName, eventDate,eventLocation;

        public Viewholder(@NonNull View itemView){
            super(itemView);

            eventIcon=itemView.findViewById(R.id.eventIcon);
            eventName = itemView.findViewById(R.id.eventName);
            hostName = itemView.findViewById(R.id.hostName);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventLocation = itemView.findViewById(R.id.eventLocation);
        }
    }
}
