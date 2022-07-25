package edu.neu.madcourse.mad_goer.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.mad_goer.databinding.FragmentHistoryBinding;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.FragmentHistoryBinding;
import edu.neu.madcourse.mad_goer.messages.message;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private ArrayList<message> msgData;
    private int totalSent;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.idRVCourse;
        TextView totalSentCount = binding.idTotalSent;

        MainActivity activity = (MainActivity) getActivity();
        msgData = activity.getHistoryData();
        totalSent = activity.getTotalSent();
        totalSentCount.setText(Integer.toString(totalSent));

        MessageAdapter messageAdapter = new MessageAdapter(msgData,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        //added Jul14
        //will auto show cardview from bottom
        recyclerView.scrollToPosition(msgData.size()-1);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}