package edu.neu.madcourse.mad_goer.ui.comment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import android.view.WindowManager;

import edu.neu.madcourse.mad_goer.databinding.Fragment3CommentBinding;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.messages.Comment;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.User;
import edu.neu.madcourse.mad_goer.ui.comment.messageRecycleview.CommentAdapter;

public class CommentFragment extends Fragment {

    private Fragment3CommentBinding binding;
    private EditText commentTV;
    private Button sendBtn;
    private Event event;
    private Spinner eventSpinner;
    private String currentUserName;
    MainActivity activity;
    private ArrayAdapter<String> spinnerArrayAdapter;

    DatabaseReference databaseCommentRef = FirebaseDatabase.getInstance().getReference("Comment");

    private RecyclerView recyclerView;
    private ArrayList<Comment> commentList = new ArrayList<>();

    private int mScrollY = 0;

    public CommentFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment3CommentBinding.inflate(inflater, container, false);
        sendBtn = binding.btnSendMsg;
        recyclerView = binding.recyclerViewComment;
        commentTV = binding.editCommentComment;
        eventSpinner = binding.idEventNameComment;
        View root = binding.getRoot();
        activity = (MainActivity) getActivity();


        databaseCommentRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                int idx = 0;
                for(Comment prevComment : commentList){
                    commentList.get(0).setLikes(1);
                    if(prevComment.getTime().equals(comment.getTime())){
                        idx = commentList.indexOf(prevComment);
                    }
                }
                commentList.add(comment);
                setUpRecycleViewComment(commentList, idx);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                int idx = 0;
                for(Comment prevComment : commentList){
                    commentList.get(0).setLikes(1);
                    if(prevComment.getTime().equals(comment.getTime())){
                        idx = commentList.indexOf(prevComment);
                    }
                }
                commentList.get(idx).setLikes(comment.getLikes());
                setUpRecycleViewComment(commentList, idx);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Comment comment  = snapshot.getValue(Comment.class);
                commentList.remove(comment);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadComment();
                commentTV.setText("");
            }
        });


//        //event_list contains all Event objects under this user
//        ArrayList<Event> event_list = activity.getListofEventLists().get(0);
//
//        //TO DO: pass event information of this user
//        for (Event e: event_list) {
//            event_list.add(e);
//        }

//        ArrayAdapter<Event> dataAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_spinner_item, event_list);
        new Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        currentUserName = activity.getCurrentUserName();
                        //spinnerarrayadapter get event names
                        spinnerArrayAdapter = activity.getArrayAdapter();
                        setUpSpinnerAdapter();
                        //set up rv display comment list
                        setUpRecycleViewComment(commentList, -1);
                    }
                },
                300);



        RecyclerView.OnScrollListener mTotalScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerViewFake, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
            }
        };
        recyclerView.setOnScrollListener(mTotalScrollListener);


        return root;
    }



    public void setUpRecycleViewComment(ArrayList<Comment> list, int idx){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        CommentAdapter commentAdapter = new CommentAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
//        if(idx != -1){
//            recyclerView.scrollToPosition(list.size() - idx);
//        }else {
//            recyclerView.scrollToPosition(list.size()-1);
//        }
        int tmp = mScrollY;
        recyclerView.scrollBy(0, 0);
        mScrollY = 0;
        recyclerView.scrollBy(0, tmp);
    }


    private void setUpSpinnerAdapter(){
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(spinnerArrayAdapter);
    }

    public void uploadComment(){
        String thisComment = String.valueOf(commentTV.getText());
        if(thisComment== null){
            Snackbar.make(getView(), "Please leave a comment.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (eventSpinner.getSelectedItem() == null){
            Snackbar.make(getView(), "Your current have no GO", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Long nowStamp = timestamp.getTime();
            //String commentTime = timestamp.toString();
            Comment newComment = new Comment(thisComment, currentUserName, nowStamp, (String) eventSpinner.getSelectedItem());
            //push this new comment to database
            //databaseEventRef.child(event.getEventID()).setValue(event);
            //custom keyvalue in comment
            databaseCommentRef.child(String.valueOf(nowStamp)).setValue(newComment);
            //databaseCommentRef.push().setValue(newComment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}