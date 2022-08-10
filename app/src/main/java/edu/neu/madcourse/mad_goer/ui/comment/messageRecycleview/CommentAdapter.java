package edu.neu.madcourse.mad_goer.ui.comment.messageRecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.messages.Comment;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.ui.recycleview.EventAdapter;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder>{

    //private final ArrayList<Event> messageList;
    private final ArrayList<Comment> commentList;
    private final Context context;

    public CommentAdapter(ArrayList<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }


    @NonNull
    @Override
    public CommentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_msg,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.Viewholder holder, int position) {
        //on item selected
        Comment cmt = commentList.get(position);
        holder.bindThisData(cmt);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }



    public class Viewholder extends RecyclerView.ViewHolder {

        DatabaseReference databaseCommentRef = FirebaseDatabase.getInstance().getReference("Comment");

        private TextView eventNameTV, userIDTV, postDateTV, commentTV, likeTV;
        private ImageButton likeBtn;
        //  private Comment comment;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            eventNameTV = itemView.findViewById(R.id.eventname_msg);
            userIDTV = itemView.findViewById(R.id.sendername_msg);
            postDateTV = itemView.findViewById(R.id.timestamp_msg);
            commentTV = itemView.findViewById(R.id.comment_msg);
            likeBtn = itemView.findViewById(R.id.btn_like_msg);
            likeBtn.setImageResource(R.drawable.ic_like_unfilled);
            likeTV = itemView.findViewById(R.id.id_likecount_msg);
        }

        public void bindThisData(Comment comment) {
            eventNameTV.setText(comment.getEventName());
            userIDTV.setText(comment.getUser());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, Y, hh:mm a");
            postDateTV.setText(simpleDateFormat.format(comment.getTime()));
            commentTV.setText(comment.getComment());


            likeTV.setText(String.valueOf(comment.getLikes()));
            if(comment.getLikes() > 0){likeBtn.setImageResource(R.drawable.ic_like_filled);}
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeBtn.setImageResource(R.drawable.ic_like_filled);
                    comment.likesPlusOne();
                    databaseCommentRef.child(String.valueOf(comment.getTime())).setValue(comment);

                }
            });
        }
    }
}


