package edu.neu.madcourse.mad_goer.ui.comment;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private User currentUser;
    private ArrayAdapter<String> spinnerArrayAdapter;

    DatabaseReference databaseCommentRef = FirebaseDatabase.getInstance().getReference("Comment");

    private RecyclerView recyclerView;
    private ArrayList<Comment> comments;

    public CommentFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment3CommentBinding.inflate(inflater, container, false);
        sendBtn = binding.btnSendMsg;
        recyclerView = binding.recyclerView;
        commentTV = binding.editCommentComment;
        eventSpinner = binding.idEventNameComment;
        View root = binding.getRoot();
        MainActivity activity = (MainActivity) getActivity();

        comments = new ArrayList<>();
//        Date datetest = new Date();
//        Comment test = new Comment("this is a comment","myuserid", datetest,"eventname");
//        comments.add(test);






        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadComment();
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
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        currentUser = activity.getCurrentUser();
                        currentUserName = activity.getCurrentUserName();
                        spinnerArrayAdapter = activity.getArrayAdapter();
                        setUpAdapter();
                    }
                },
                300);

        return root;
    }

    private void setUpAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommentAdapter(comments, getContext()));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(spinnerArrayAdapter);

    }

    public void uploadComment(){
        String thisComment = String.valueOf(commentTV.getText());
        if(thisComment== null){
            Snackbar.make(getView(), "Please leave a comment.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Date timestamp = Calendar.getInstance().getTime();
            Comment newComment = new Comment(thisComment, currentUserName, timestamp, (String) eventSpinner.getSelectedItem());
            //push this new comment to database
            databaseCommentRef.push().setValue(newComment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}