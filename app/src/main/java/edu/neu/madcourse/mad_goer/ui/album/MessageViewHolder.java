package edu.neu.madcourse.mad_goer.ui.album;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.team36_a8.R;
import edu.neu.madcourse.team36_a8.messages.message;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView nameTV;
    public TextView timeTV;
    public ImageView myIV;

    public MessageViewHolder(@NonNull View itemView){
        super(itemView);
        this.nameTV = (TextView)itemView.findViewById(R.id.name);
        this.timeTV = (TextView)itemView.findViewById(R.id.time);
        this.myIV = (ImageView)itemView.findViewById(R.id.imageView);
    }
    public void bindThisData(message theMessageToBind) {
        nameTV.setText(theMessageToBind.getSendName() + " sent to " + theMessageToBind.getReceiveName());
        timeTV.setText(String.valueOf(theMessageToBind.getTimeStemp()));
       int stickerID;
        switch(theMessageToBind.getStickerID()){
            case "5520a8s01":
                stickerID = R.id.sticker_1;
                break;
            case "5520a8s02":
                stickerID = R.id.sticker_2;
                break;
            case "5520a8s03":
                stickerID = R.id.sticker_3;
                break;
            case "5520a8s04":
                stickerID = R.id.sticker_4;
                break;
            case "5520a8s05":
                stickerID = R.id.sticker_5;
                break;
            case "5520a8s06":
                stickerID = R.id.sticker_6;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + theMessageToBind.getStickerID());
        }
        myIV = ((ImageView) itemView.findViewById(stickerID));
    }
}
