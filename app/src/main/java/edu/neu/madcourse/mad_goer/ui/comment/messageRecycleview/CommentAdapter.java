package edu.neu.madcourse.mad_goer.ui.comment.messageRecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Comment;


public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder>{

    //private final ArrayList<Event> messageList;
    private final HashMap<String, Comment> commentList;
    private final Context context;

    public CommentAdapter(List<Comment> messageList, Context context) {
        this.commentList = messageList;
        this.context = context;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new CommentViewHolder(view);
        // return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, null));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        //on item selected
        Comment cmt = commentList.get(position);
        holder.bindThisData(cmt);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

}
