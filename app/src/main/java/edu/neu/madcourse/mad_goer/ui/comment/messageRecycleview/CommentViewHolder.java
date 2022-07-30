package edu.neu.madcourse.mad_goer.ui.comment.messageRecycleview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Comment;


public class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView eventNameTV,userIDTV, postDateTV, commentTV;
    private ImageButton likeBtn;
    private Comment comment;

    public CommentViewHolder(@NonNull View itemView){
        super(itemView);
        eventNameTV = itemView.findViewById(R.id.eventname_msg);
        userIDTV = itemView.findViewById(R.id.sendername_msg);
        postDateTV = itemView.findViewById(R.id.timestamp_msg);
        commentTV = itemView.findViewById(R.id.comment_msg);
        likeBtn = itemView.findViewById(R.id.btn_like_msg);
        likeBtn.setImageResource(R.drawable.ic_like_unfilled);
    }

    public void bindThisData(Comment theMsgToBind) {
        eventNameTV.setText(theMsgToBind.getEvent().getEventName());
        userIDTV.setText(theMsgToBind.getUser());
        postDateTV.setText(theMsgToBind.getTime().toString());
        commentTV.setText(theMsgToBind.getComment());
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeBtn.setImageResource(R.drawable.ic_like_filled);
                theMsgToBind.likesPlusOne();
            }
        });
    }
}


