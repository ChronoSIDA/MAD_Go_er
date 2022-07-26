package edu.neu.madcourse.mad_goer.ui.recycleview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Event;


public class EventViewHolder extends RecyclerView.ViewHolder {

    private ImageView eventIcon;
    private TextView eventName,hostName, eventDate,eventLocation;


    public EventViewHolder(@NonNull View itemView){
        super(itemView);
        eventIcon=itemView.findViewById(R.id.eventIcon);
        eventName = itemView.findViewById(R.id.eventName);
        hostName = itemView.findViewById(R.id.hostName);
        eventDate = itemView.findViewById(R.id.eventDate);
        eventLocation = itemView.findViewById(R.id.eventLocation);

    }

    public void bindThisData(Event theEventToBind) {
        eventName.setText(theEventToBind.getEventName());
        hostName.setText(theEventToBind.getHost().getUserID());
        eventDate.setText(theEventToBind.getStartDate().toString());
        eventLocation.setText(theEventToBind.getLocation().toString());
    }

    }


