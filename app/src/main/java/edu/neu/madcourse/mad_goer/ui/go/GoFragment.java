package edu.neu.madcourse.mad_goer.ui.go;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.Fragment2GoBinding;

public class GoFragment extends Fragment {

    private Fragment2GoBinding binding;
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment2GoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        MainActivity activity = (MainActivity) getActivity();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}