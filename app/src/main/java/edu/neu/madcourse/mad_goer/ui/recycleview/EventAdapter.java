package edu.neu.madcourse.mad_goer.ui.recycleview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
        Context context = holder.eventIcon.getContext();
        String getCategory = "sticker_" + event.getCategory().toString().toLowerCase();
        int id = context.getResources().getIdentifier(getCategory, "drawable", context.getPackageName());
        holder.eventIcon.setImageResource(id);
        holder.eventName.setText(event.getEventName());
        holder.hostName.setText("Host: " + event.getHost().getUserID());


        SimpleDateFormat DateFor = new SimpleDateFormat("E, MMM dd, Y | hh:mm a z");
        String stringDate= DateFor.format(event.getStartDate());

        holder.eventDate.setText(stringDate);
        if(event.isInPerson()){
            holder.eventLocation.setText(event.getActualLocation());
        } else {
            holder.eventLocation.setText(event.getLink());
        }
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
