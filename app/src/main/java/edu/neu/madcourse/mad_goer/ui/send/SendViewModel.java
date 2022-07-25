package edu.neu.madcourse.mad_goer.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
}