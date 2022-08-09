package edu.neu.madcourse.mad_goer.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.neu.madcourse.mad_goer.AboutActivity;
import edu.neu.madcourse.mad_goer.InterestActivity;
import edu.neu.madcourse.mad_goer.LoginActivity;
import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.databinding.Fragment4SettingBinding;
import edu.neu.madcourse.mad_goer.ui.go.GoFragment;

public class SettingFragment extends Fragment {
    public static final String ACTION_DIAL = "6147353654";
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

        button_accountSec = binding.buttonAccountSecSetting;
        button_accountSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Thanks for your interest! This function will be enabled soon.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        button_emergencyCont = binding.buttonEmergencyContSetting;
        button_emergencyCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q= ACTION_DIAL;
                Uri u=Uri.parse("tel:"+q);
                Intent i=new Intent(Intent.ACTION_VIEW,u);
                startActivity(i);
            }
        });
        button_language = binding.buttonLanguageSetting;
        button_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Thanks for your interest! This function will be enabled soon.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        button_aboutGoer = binding.buttonAboutGoerSetting;
        button_aboutGoer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
      //  button_switchAccount = binding.buttonSwitchAccountSetting;
        button_logOut = binding.buttonLogOutSetting;
        button_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
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

//    public void emergencyCall(){
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        intent.setData(Uri.parse("tel:"+""));
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

//    public void clickInterest(View view){
//        Intent intent = new Intent(getActivity(), InterestActivity.class);
//        intent.putExtra("nameTxt", curUserName );
//        startActivity(intent);
//    }


}