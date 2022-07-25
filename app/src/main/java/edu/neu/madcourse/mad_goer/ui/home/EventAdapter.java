package edu.neu.madcourse.mad_goer.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Event event = eventList.get(position);
//
//        holder.senderName.setText(msg.getSendName());
//        holder.receiverName.setText(msg.getReceiveName());
//
//
//        holder.timeStamp.setText(msg.getTimeStemp());
//        holder.stickerSent.setText();
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
//        private ImageView stickerIV;
//        private TextView senderName, receiverName,timeStamp;

        public Viewholder(@NonNull View itemView){
            super(itemView);
//            stickerIV=itemView.findViewById(R.id.stickerImage);
//            senderName = itemView.findViewById(R.id.SenderName);
//            receiverName = itemView.findViewById(R.id.ReceiverName);
//            timeStamp = itemView.findViewById(R.id.timeStamp);
        }
    }
}
