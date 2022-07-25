package edu.neu.madcourse.mad_goer.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.team36_a8.R;
import edu.neu.madcourse.team36_a8.messages.message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Viewholder> {
    private final ArrayList<message> msgList;
    private final Context context;


    public MessageAdapter(ArrayList<message> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new Viewholder(view);
       // return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        message msg = msgList.get(position);

        holder.senderName.setText(msg.getSendName());
        holder.receiverName.setText(msg.getReceiveName());

        if(msg.getStickerID().equals("5520a8s01")){
            holder.stickerIV.setImageResource(R.drawable.sticker_01);
        } else if(msg.getStickerID().equals("5520a8s02")){
            holder.stickerIV.setImageResource(R.drawable.sticker_02);
        } else if(msg.getStickerID().equals("5520a8s03")){
            holder.stickerIV.setImageResource(R.drawable.sticker_03);
        } else if(msg.getStickerID().equals("5520a8s04")){
            holder.stickerIV.setImageResource(R.drawable.sticker_04);
        } else if(msg.getStickerID().equals("5520a8s05")){
            holder.stickerIV.setImageResource(R.drawable.sticker_05);
        } else if(msg.getStickerID().equals("5520a8s06")){
            holder.stickerIV.setImageResource(R.drawable.sticker_06);
        } else if(msg.getStickerID().equals("5520a8s07")){
            holder.stickerIV.setImageResource(R.drawable.sticker_07);
        } else if(msg.getStickerID().equals("5520a8s08")){
            holder.stickerIV.setImageResource(R.drawable.sticker_08);
        } else if(msg.getStickerID().equals("5520a8s09")){
            holder.stickerIV.setImageResource(R.drawable.sticker_09);
        }
        holder.timeStamp.setText(msg.getTimeStemp());
//        holder.stickerSent.setText();
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private ImageView stickerIV;
        private TextView senderName, receiverName,timeStamp;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            stickerIV=itemView.findViewById(R.id.stickerImage);
            senderName = itemView.findViewById(R.id.SenderName);
            receiverName = itemView.findViewById(R.id.ReceiverName);
            timeStamp = itemView.findViewById(R.id.timeStamp);
        }
    }
}
