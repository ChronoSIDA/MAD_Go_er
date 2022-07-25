package edu.neu.madcourse.mad_goer.ui.receive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.neu.madcourse.mad_goer.MainActivity;
import edu.neu.madcourse.mad_goer.R;
import edu.neu.madcourse.mad_goer.databinding.FragmentReceiveBinding;

public class ReceiveFragment extends Fragment {

    private FragmentReceiveBinding binding;
    private String timePattern = "yyyy-MM-dd HH:mm:ss z";
    private DateFormat df = new SimpleDateFormat(timePattern);



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReceiveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView senderTextView = binding.idSender;
        TextView receiverTextView = binding.idReceiver;
        ImageView stickerImageView = binding.imageViewReceiver;
        TextView timestampTextView = binding.idTimestamp;
        TextView title_hey = binding.titleHey;
        TextView title_sendyouthis = binding.titleSendyouthis;
        TextView emptyMessage = binding.idEmptyMsg;

        MainActivity activity = (MainActivity) getActivity();
        ArrayList msgData = activity.getMessageData();

        if(msgData.size() != 0){
            senderTextView.setVisibility(View.VISIBLE);
            receiverTextView.setVisibility(View.VISIBLE);
            timestampTextView.setVisibility(View.VISIBLE);
            stickerImageView.setVisibility(View.VISIBLE);
            title_hey.setVisibility(View.VISIBLE);
            title_sendyouthis.setVisibility(View.VISIBLE);
            emptyMessage.setVisibility(View.INVISIBLE);

            if(msgData.get(1).equals(msgData.get(0))){
                //message displays current user sent a sticker to sb else
                //TODO: FIX this. sendertextview is null
                senderTextView.setText("You");
                receiverTextView.setText(msgData.get(2).toString());

            }else{
                senderTextView.setText(msgData.get(2).toString());
                receiverTextView.setText("you");
            }


            if(msgData.get(3).toString() == "5520a8s01"){
                stickerImageView.setImageResource(R.drawable.sticker_01);
            } else if(msgData.get(3).toString() == "5520a8s02"){
                stickerImageView.setImageResource(R.drawable.sticker_02);
            } else if(msgData.get(3).toString() == "5520a8s03"){
                stickerImageView.setImageResource(R.drawable.sticker_03);
            } else if(msgData.get(3).toString() == "5520a8s04"){
                stickerImageView.setImageResource(R.drawable.sticker_04);
            } else if(msgData.get(3).toString() == "5520a8s05"){
                stickerImageView.setImageResource(R.drawable.sticker_05);
            } else if(msgData.get(3).toString() == "5520a8s06"){
                stickerImageView.setImageResource(R.drawable.sticker_06);
            } else if(msgData.get(3).toString() == "5520a8s07"){
                stickerImageView.setImageResource(R.drawable.sticker_07);
            } else if(msgData.get(3).toString() == "5520a8s08"){
                stickerImageView.setImageResource(R.drawable.sticker_08);
            } else if(msgData.get(3).toString() == "5520a8s09"){
                stickerImageView.setImageResource(R.drawable.sticker_09);
            }

            timestampTextView.setText(msgData.get(4).toString());
        } else {
            senderTextView.setVisibility(View.INVISIBLE);
            receiverTextView.setVisibility(View.INVISIBLE);
            timestampTextView.setVisibility(View.INVISIBLE);
            stickerImageView.setVisibility(View.INVISIBLE);
            title_hey.setVisibility(View.INVISIBLE);
            title_sendyouthis.setVisibility(View.INVISIBLE);
            emptyMessage.setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}