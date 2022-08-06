package edu.neu.madcourse.mad_goer.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.neu.madcourse.mad_goer.InterestActivity;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.databinding.Fragment4SettingBinding;

public class SettingFragment extends Fragment {

    private Fragment4SettingBinding binding;
    private Button button_myHost, button_interest, button_accountSec,
            button_emergencyCont, button_language, button_aboutGoer,
            button_switchAccount, button_logOut;
    private TextView txt_username, txt_privacy, txt_system;
    private String curUserName;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = Fragment4SettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity activity = (MainActivity) getActivity();
        curUserName = activity.getCurrentUserName();


        button_myHost = binding.buttonMyHostSetting;
        button_myHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button_interest = binding.buttonInterestSetting;
//        button_interest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        button_accountSec = binding.buttonAccountSecSetting;
        button_emergencyCont = binding.buttonEmergencyContSetting;
        button_language = binding.buttonLanguageSetting;
        button_aboutGoer = binding.buttonAboutGoerSetting;
      //  button_switchAccount = binding.buttonSwitchAccountSetting;
        button_logOut = binding.buttonLogOutSetting;
       // txt_username = binding.txtUsernameSetting;
        txt_privacy = binding.txtPrivacySetting;
        txt_system = binding.txtSystemSetting;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



//    public void clickInterest(View view){
//        Intent intent = new Intent(getActivity(), InterestActivity.class);
//        intent.putExtra("nameTxt", curUserName );
//        startActivity(intent);
//    }


}