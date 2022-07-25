package edu.neu.madcourse.mad_goer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.Fragment1HomeBinding;

public class HomeFragment extends Fragment {

    private Fragment1HomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment1HomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity activity = (MainActivity) getActivity();
//        activity.getData();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

