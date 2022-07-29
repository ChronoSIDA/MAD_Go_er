package edu.neu.madcourse.mad_goer.ui.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.databinding.Fragment3CommentBinding;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.messages.Comment;
import edu.neu.madcourse.mad_goer.messages.Event;
import edu.neu.madcourse.mad_goer.messages.EventType;
import edu.neu.madcourse.mad_goer.messages.User;

public class CommentFragment extends Fragment {

    private Fragment3CommentBinding binding;
    private EditText commentTV;
    private Button sendBtn;
    private Event event;
    private Spinner eventSpinner;
    private User curUser;

    public CommentFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment3CommentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.recyclerView;
        MainActivity activity = (MainActivity) getActivity();

        sendBtn = binding.btnSendMsg;
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadComment();
            }
        });
        commentTV = binding.editCommentComment;
        eventSpinner = binding.idEventNameComment;
        ArrayList<Event> event_list = new ArrayList<Event>(curUser.getTotalPersonalEvents().values());
        //TO DO: pass event information of this user
        for (Event e: event_list) {
            event_list.add(e);
        }
        ArrayAdapter<Event> dataAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_spinner_item, event_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(dataAdapter);

        return root;
    }
    public void uploadComment(){
        String thisComment = String.valueOf(commentTV.getText());
        if(thisComment== null){
            Snackbar.make(getView(), "Please leave a comment.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else {
            Date timestamp = Calendar.getInstance().getTime();
            Comment newComment = new Comment(thisComment, curUser, timestamp, (Event)eventSpinner.getSelectedItem());
            //TO DO: push this new comment to database

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}