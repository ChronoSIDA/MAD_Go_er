package edu.neu.madcourse.mad_goer.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.FragmentSendBinding;

public class SendFragment extends Fragment {

    private FragmentSendBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity activity = (MainActivity) getActivity();
        activity.getData();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

