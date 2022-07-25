package edu.neu.madcourse.mad_goer.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Event;


public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView nameTV;
    public TextView timeTV;
    public ImageView myIV;

    public EventViewHolder(@NonNull View itemView){
        super(itemView);
        this.nameTV = (TextView)itemView.findViewById(R.id.name);
        this.timeTV = (TextView)itemView.findViewById(R.id.time);
        this.myIV = (ImageView)itemView.findViewById(R.id.imageView);
    }
    public void bindThisData(Event theEventToBind) {
//        nameTV.setText(theEventToBind.getSendName() + " sent to " + theEventToBind.getReceiveName());
//        timeTV.setText(String.valueOf(theEventToBind.getTimeStemp()));


        }

    }


