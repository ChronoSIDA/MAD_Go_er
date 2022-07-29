package edu.neu.madcourse.mad_goer.ui.comment.messageRecycleview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Comment;


public class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView eventNameTV,userIDTV, postDateTV, commentTV;

    public CommentViewHolder(@NonNull View itemView){
        super(itemView);
        eventNameTV = itemView.findViewById(R.id.eventName_comment);
        userIDTV = itemView.findViewById(R.id.userID_comment);
        postDateTV = itemView.findViewById(R.id.postDate_comment);
        commentTV = itemView.findViewById(R.id.comment_comment);
    }

    public void bindThisData(Comment theMsgToBind) {
        eventNameTV.setText(theMsgToBind.getEvent().getEventName());
        userIDTV.setText(theMsgToBind.getUser().getUserID());
        postDateTV.setText(theMsgToBind.getTime().toString());
        commentTV.setText(theMsgToBind.getComment());
    }

    }


