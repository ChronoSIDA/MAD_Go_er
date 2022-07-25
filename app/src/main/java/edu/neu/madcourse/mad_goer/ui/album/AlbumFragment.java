package edu.neu.madcourse.mad_goer.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.neu.madcourse.mad_goer.databinding.Fragment3AlbumBinding;
import edu.neu.madcourse.mad_goer.MainActivity;

public class AlbumFragment extends Fragment {

    private Fragment3AlbumBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = Fragment3AlbumBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.idRVCourse;
        TextView totalSentCount = binding.idTotalSent;

        MainActivity activity = (MainActivity) getActivity();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}